package com.dovico.commonlibrary;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.dovico.commonlibrary.data.CAssignment;
import com.dovico.commonlibrary.data.Constants;


// An Assignment tree picker control
public class CAssignmentPicker {
	JDialog m_dlgSelf = null;
	private Long m_lEmployeeID = 0L;
	private boolean m_bIncludeForTimeEntryQueryString = false;
	private String m_sConsumerSecret = "";
	private String m_sDataAccessToken = "";
	private String m_sApiVersionTargeted = "";
	
	private JTree m_tTree = null;
	private CAssignment m_aSelectedItem = null;
	private ActionListener m_alListener = null;
	
	
	// Overloaded constructor. The action listener will be told of the tree selection if the user clicks on a task item.
	public CAssignmentPicker(Component cParent, ActionListener alListener){
		// Remember the listener
		m_alListener = alListener;
		
		// Create our Assignment tree picker window, give it the specified title and indicate that it is to be a modal window
		m_dlgSelf = new JDialog();
		m_dlgSelf.setTitle("Project/Task Picker");
		m_dlgSelf.setSize(330, 290);//default width and height respectively but the dialog is resizable so the user can change it
		m_dlgSelf.setResizable(true);
		m_dlgSelf.setModal(true);
		
		
		// Create the tree control
	    m_tTree = new JTree();
	    m_tTree.setRootVisible(false); // Prevent our root node (when we create it in the loadAssignmentsForEmployee method) from being displayed
	    m_tTree.addTreeWillExpandListener(new TreeWillExpandListener() {// Called before a node is expanded or collapsed 
	    	// We're not concerned with the collapse event
	    	public void treeWillCollapse(TreeExpansionEvent treeExpansionEvent) throws ExpandVetoException {}

	    	// If we are about to expand a branch then....
            public void treeWillExpand(TreeExpansionEvent treeExpansionEvent) throws ExpandVetoException {
                TreePath path = treeExpansionEvent.getPath();
                DefaultMutableTreeNode tnBranch =  (DefaultMutableTreeNode)path.getLastPathComponent();
                
                // If the current node does not yet have its children loaded then...(holds only one child that is a string "Processing...")
                if(doesBranchHaveProcessingChild(tnBranch))
	    		{
	            	// Grab the current item's Assignment object
		            CAssignment aAssignment = (CAssignment)tnBranch.getUserObject();
		           
		            // Request the children of the item that is being expanded
		            APIRequestResult aRequestResult = new APIRequestResult(m_sConsumerSecret, m_sDataAccessToken, m_sApiVersionTargeted, true);
		            aRequestResult.setRequestURI(aAssignment.getChildAssignmentsURI());
		            getAssignments(tnBranch, aRequestResult);
	    		} // End if(doesBranchHaveProcessingChild(tnBranch))
              }
	    });
	    m_tTree.addTreeSelectionListener(new TreeSelectionListener() { // Called when the user clicks/selects an item in the tree
	        public void valueChanged(TreeSelectionEvent e) {
	            // Grab the selected node. If nothing is selected then exit now.
	        	DefaultMutableTreeNode tnBranch = (DefaultMutableTreeNode)m_tTree.getLastSelectedPathComponent();
	            if (tnBranch == null) return;
	            
	            
	            // If the current assignment item is a bottom-level (task) item then...
	            CAssignment aAssignment = (CAssignment)tnBranch.getUserObject();
	            if(!aAssignment.getTaskID().equals(Constants.NONE_ID)){	            	
	            	// Create a new Selected Item object initialized with the selected Task's information
	            	m_aSelectedItem = new CAssignment(0L, "", 0L, "", 0L, "", aAssignment.getTaskID(), aAssignment.getTaskName(), 0L, "");
	            	
	            	Object objUserObject = null;
	            	CAssignment aParent = null;	            	
	            	
	            	// Loop up through the parents looking for the Project and Client information	            		            	
	            	TreeNode tnParent = tnBranch.getParent();
	            	while(tnParent != null){
	            		// Grab the parent user object. If we are not looking at an Assignment item then exit the loop.
	            		objUserObject = ((DefaultMutableTreeNode)tnParent).getUserObject();
	            		if(!(objUserObject instanceof CAssignment)) { break; }
	            		
	            		// If we are looking at a Project item then...
	            		aParent = (CAssignment)objUserObject;
	            		if(!aParent.getProjectID().equals(Constants.NONE_ID)){
	            			m_aSelectedItem.setProjectID(aParent.getProjectID());
	            			m_aSelectedItem.setProjectName(aParent.getProjectName());
	            		}
	            		// If we are looking at a Client item then...
	            		else if(!aParent.getClientID().equals(Constants.NONE_ID)){
	            			m_aSelectedItem.setClientID(aParent.getClientID());
	            			m_aSelectedItem.setClientName(aParent.getClientName());	
	            		} // End if
	            		
	            		// Grab the next parent up
	            		tnParent = tnParent.getParent();
	            	} // End of the while(tnParent != null) loop.            	
	            	
	            	 
	            	// Tell the caller that a selection was made and then hide this dialog
	            	if(m_alListener != null){ m_alListener.actionPerformed(new ActionEvent(this, 0, "Selection Made")); }	            	
	            	m_dlgSelf.setVisible(false);
	            		            	
	            	// Make sure nothing is selected in the tree for when the tree is next displayed
	            	m_tTree.clearSelection();
	            } // End if(!aAssignment.getTaskID().equals(Constants.NONE_ID))
	        }
	    });
    	    
	    JScrollPane treeView = new JScrollPane(m_tTree);
	    m_dlgSelf.add(treeView);
	    
	    
		// Adjust where this dialog is displayed
        m_dlgSelf.setLocationRelativeTo(cParent);
	}
	
	
	// Helper to find out if the branch has the 'Processing...' child node (don't need to load anything if not)
    private boolean doesBranchHaveProcessingChild(DefaultMutableTreeNode tnBranch){
        // If the current branch does not yet have its children loaded then...(holds only one child that is a string "Processing...")
        if(tnBranch.getChildCount() == 1 && ((DefaultMutableTreeNode)tnBranch.getFirstChild()).getUserObject() instanceof String) { return true; }
        
        // Either the current branch has more than 1 child (or no children) or the child is not a string  
        return false;    	
    }
                

	// Helper to cause this dialog to be shown	
	public void setVisible(boolean bShow) { m_dlgSelf.setVisible(bShow); }
	
	
	// Causes the tree to load in a new employee's assignments (so that you can re-use a single class instance).
	// If the employee is already set, will not reload.
	public void loadAssignmentsForEmployee(Long lEmployeeID, boolean bIncludeForTimeEntryQueryString, String sConsumerSecret, 
			String sDataAccessToken, String sApiVersionTargeted){
		// If the employee specified is the one we already have data loaded for then exit now (no sense in wasting processing by trying to load something we already
		// have)		
		if(m_lEmployeeID.equals(lEmployeeID)){ return; }
		
		// Remember the values passed in
		m_lEmployeeID = lEmployeeID;
		m_bIncludeForTimeEntryQueryString = bIncludeForTimeEntryQueryString; // a querystring flag that can be passed to a GET Assignments call to get all assignments for a user regardless of limited access settings so that it's possible to track time on any assignment that's assigned to you. This should only be set to 'True' when dealing with time entries belonging to the logged in user. 		
		m_sConsumerSecret = sConsumerSecret;
		m_sDataAccessToken = sDataAccessToken;
		m_sApiVersionTargeted = sApiVersionTargeted;
		
		// Clear the selected item
		m_aSelectedItem = null;
		
		
		// Load in the root assignments (Clients and any Projects that are not assigned to any clients). The root node will not be displayed to the user (we've tool
		// the tree not to show it in the constructor)
		APIRequestResult aRequestResult = new APIRequestResult(m_sConsumerSecret, m_sDataAccessToken, m_sApiVersionTargeted, true);
		DefaultMutableTreeNode tnRoot = new DefaultMutableTreeNode("Assignments");
	    getAssignments(tnRoot, aRequestResult);

	    // Add the root node to the tree
	    m_tTree.setModel(new DefaultTreeModel(tnRoot));
	}
	
	
	// Helper that loads in the branch of assignments for the parent node
	private void getAssignments(DefaultMutableTreeNode tnParent, APIRequestResult aRequestResult) {
		// Request the assignments
		ArrayList<CAssignment> lstAssignments = CAssignment.getList(m_lEmployeeID, m_bIncludeForTimeEntryQueryString, aRequestResult);

		// If we are loading children into a branch that holds the 'Processing...' text then remove the text before adding in our new items
		if(doesBranchHaveProcessingChild(tnParent)) { tnParent.removeAllChildren(); } 
		
		
		CAssignment aAssignment = null;
		DefaultMutableTreeNode tnCurItem = null;
		
		// Loop through the list of assignments returned...		
		int iCount = lstAssignments.size();
		for(int iIndex = 0; iIndex < iCount; iIndex++){ 
			// Grab the current assignment item, tell it that it's being used in a tree control and then create a new Tree item
			aAssignment = lstAssignments.get(iIndex);
			aAssignment.setUsedInTreeCtrl(true);
			tnCurItem = new DefaultMutableTreeNode(aAssignment);

			// Add the tree item to the parent
			tnParent.add(tnCurItem); 
			
			// If the current assignment has children AND we are not yet at a Task node (we don't want to display the Employee nodes since the assignments displayed
			// are for a specific employee already) then...
			if(!aAssignment.getChildAssignmentsURI().equals(Constants.URI_NOT_AVAILABLE) && aAssignment.getTaskID().equals(Constants.NONE_ID)){
				tnCurItem.add(new DefaultMutableTreeNode("Processing..."));
			} // End if
		} // End of the for(int iIndex = 0; iIndex < iCount; iIndex++) loop.
		
		
		// If the next page URI is not 'N/A' then we have more Assignments to grab...
		String sNextPageURI = aRequestResult.getResultNextPageURI(); 
		if(!sNextPageURI.equals(Constants.URI_NOT_AVAILABLE)) {
			// Set the URI for the next call and then call this method again for the next page of assignment records
			aRequestResult.setRequestURI(sNextPageURI);
			getAssignments(tnParent, aRequestResult);
		} // End if(!sNextPageURI.equals(Constants.URI_NOT_AVAILABLE))
	}
	
	
	// Returns the selected assignment item (NOTE: Might be null)
	public CAssignment getSelectedItem() { return m_aSelectedItem; }
}
