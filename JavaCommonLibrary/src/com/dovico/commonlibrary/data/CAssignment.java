package com.dovico.commonlibrary.data;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.dovico.commonlibrary.APIRequestResult;
import com.dovico.commonlibrary.CRESTAPIHelper;
import com.dovico.commonlibrary.CXMLHelper;

// A class that holds the assignment information for an Employee's time (the selected Client/Project/Task)

// NOTE: More values are available like StartDate, FinishDate, EstimatedHours, ETC, Wage, Charge, etc but this class is a start
//		(see: http://apideveloper.dovico.com/Assignments)

public class CAssignment {
	// Member variables
	private Long m_lClientID = Constants.NONE_ID;
	private String m_sClientName = "";
	private Long m_lProjectID = Constants.NONE_ID;
	private String m_sProjectName = "";
	private Long m_lTaskGroupID = Constants.NONE_ID;
	private String m_sTaskGroupName = "";	
	private Long m_lTaskID = Constants.NONE_ID;
	private String m_sTaskName = "";
	private Long m_lEmployeeID = Constants.NONE_ID;
	private String m_sEmployeeName = "";

	// Formatting variables
	private boolean m_bDisplayClientNameIfNoneID = false;
	private String m_sClientProjectSep = " - "; // Separator to use between the Client and Project names
	private String m_sProjectTaskSep = " - "; // Separator to use between the Project and Task names
	private boolean m_bUsedInTreeCtrl = false; // When set to 'true' will only allow the toString method to output the text for the IDs that are not the None ID

	// Variable needed by the Assignment Tree Picker so that it knows if there are children to load for the item
	private String m_sGetChildAssignmentsURI = "";
	
	
	// Default constructor
	public CAssignment(){}

	// Overloaded constructor
	public CAssignment(Long lClientID, String sClientName, Long lProjectID, String sProjectName, Long lTaskGroupID, String sTaskGroupName,
			Long lTaskID, String sTaskName, Long lEmployeeID, String sEmployeeName) {
		m_lClientID = lClientID;
		m_sClientName = sClientName;
		m_lProjectID = lProjectID;
		m_sProjectName = sProjectName;
		m_lTaskGroupID = lTaskGroupID;
		m_sTaskGroupName = sTaskGroupName;
		m_lTaskID = lTaskID;
		m_sTaskName = sTaskName;
		m_lEmployeeID = lEmployeeID;
		m_sEmployeeName = sEmployeeName;
	}
	

	// Getters for the member variables
	public Long getClientID() { return m_lClientID; }
	public String getClientName() { return m_sClientName; }
	public Long getProjectID() { return m_lProjectID; }
	public String getProjectName() { return m_sProjectName; }
	public Long getTaskGroupID() { return m_lTaskGroupID; }
	public String getTaskGroupName() { return m_sTaskGroupName; }
	public Long getTaskID() { return m_lTaskID; }
	public String getTaskName() { return m_sTaskName; }
	public Long getEmployeeID() { return m_lEmployeeID; }
	public String getEmployeeName() { return m_sEmployeeName; }
	public String getChildAssignmentsURI() { return m_sGetChildAssignmentsURI; }

	// Setters for the member variables
	public void setClientID(Long lClientID) { m_lClientID = lClientID; }
	public void setClientName(String sClientName) { m_sClientName = sClientName; }
	public void setProjectID(Long lProjectID) { m_lProjectID = lProjectID; }
	public void setProjectName(String sProjectName) { m_sProjectName = sProjectName; }
	public void setTaskGroupID(Long lTaskGroupID) { m_lTaskGroupID = lTaskGroupID; }
	public void setTaskGroupName(String sTaskGroupName) { m_sTaskGroupName = sTaskGroupName; }
	public void setTaskID(Long lTaskID) { m_lTaskID = lTaskID; }
	public void setTaskName(String sTaskName) { m_sTaskName = sTaskName; }
	public void setEmployeeID(Long lEmployeeID) { m_lEmployeeID = lEmployeeID; }
	public void setEmployeeName(String sEmployeeName) { m_sEmployeeName = sEmployeeName; }
	public void setChildAssignmentsURI(String sChildAssignmentsURI) { m_sGetChildAssignmentsURI = sChildAssignmentsURI; }
	
	// Helper to adjust how the assignment is displayed when toString is called
	public void setDisplayOptions(boolean bDisplayClientNameIfNoneID, String sClientProjectSep, String sProjectTaskSep) {
		m_bDisplayClientNameIfNoneID = bDisplayClientNameIfNoneID;
		m_sClientProjectSep = sClientProjectSep;
		m_sProjectTaskSep = sProjectTaskSep;
	}
	
	public void setUsedInTreeCtrl(boolean bUsedInTreeCtrl) { m_bUsedInTreeCtrl = bUsedInTreeCtrl; }

	
	// Helpful function so that you can use this object in things like a list (this will be the text displayed)
	@Override
	public String toString() {
		String sDisplayVal = "";
		
		// If we're being used in a tree control then...
		if(m_bUsedInTreeCtrl){
			// Determine which item we are being used as and return the proper text 
			if (!m_lClientID.equals(Constants.NONE_ID)) { sDisplayVal = m_sClientName; }
			else if(!m_lProjectID.equals(Constants.NONE_ID)) { sDisplayVal = m_sProjectName; }
			else if(!m_lTaskGroupID.equals(Constants.NONE_ID)) { sDisplayVal = m_sTaskGroupName; }
			else if(!m_lTaskID.equals(Constants.NONE_ID)) { sDisplayVal = m_sTaskName; }
			else if(!m_lEmployeeID.equals(Constants.NONE_ID)) { sDisplayVal = m_sEmployeeName; }
		} else { // We're being used in a grid/list...		
			// Start off the display string (Project and Task names are always displayed)
			sDisplayVal = (m_sProjectName + m_sProjectTaskSep + m_sTaskName);
	
			// If the Client ID is not the None ID, OR, it is the None ID but we are to display that anyway then...
			if (!m_lClientID.equals(Constants.NONE_ID) || m_bDisplayClientNameIfNoneID) { sDisplayVal = (m_sClientName + m_sClientProjectSep + sDisplayVal); }
		} // End if(m_bUsedInTreeCtrl)
		
		// Return the display string
		return sDisplayVal;
	}

	
	
	// =====================================================================
	// STATIC METHODS: For use when interacting with the DOVICO Hosted API
	// ---------------------------------------------------------------------

	// lEmployeeID is the employee that we are pulling assignments for
	//
	// Returns the first page of data if the Request URI is empty (not set). Otherwise, returns the page of assignments indicated by the request URI that has been set.
	//
	// If there was an error the return value will be null
	public static ArrayList<CAssignment> getList(Long lEmployeeID, APIRequestResult aRequestResult) {
		// If the Request URI has not been set then set it to return the first page of assignments (the root. if it's already set we may have been called to get a
		// next/previous page of assignments or child assignments)
		if (aRequestResult.getRequestURI().isEmpty()) { aRequestResult.setRequestURI(("Assignments/Employee/" + lEmployeeID.toString() + "/"), ""); }
		return processRequest(aRequestResult);
	}
	

	// Handles the work of making a request and pulling Assignment(s) from the result of a request (if there was an error the return value will be null)
	private static ArrayList<CAssignment> processRequest(APIRequestResult aRequestResult) {
		// Make sure the Prev/Next Page URI, if there was an error, result XML document, etc are reset in the event the user is re-using an object that has already 
		// been used for a different call (don't want a previous call's results giving the caller false information)
		aRequestResult.resetResultData();
		
		
		// Pass the request on to the REST API. If there was an error then exit now
		CRESTAPIHelper.makeAPIRequest(aRequestResult);
		if(aRequestResult.getHadRequestError()) { return null; }
					
		
		// Will hold the list of assignments that will be returned to the calling function
		ArrayList<CAssignment> lstAssignments = new ArrayList<CAssignment>();
		
		// Grab the root element and get the Previous/Next Page URIs from it 
		Element xeDocElement = aRequestResult.getResultDocument().getDocumentElement();
		aRequestResult.setResultPrevPageURI(CXMLHelper.getChildNodeValue(xeDocElement, Constants.PREV_PAGE_URI, Constants.URI_NOT_AVAILABLE));
		aRequestResult.setResultNextPageURI(CXMLHelper.getChildNodeValue(xeDocElement, Constants.NEXT_PAGE_URI, Constants.URI_NOT_AVAILABLE));
		
		// Grab the list of Assignment nodes
		NodeList xnlAssignments = xeDocElement.getElementsByTagName("Assignment");
		Element xeAssignment = null;
		CAssignment aAssignment = null;
		String sAssignmentType = "", sItemName = ""; 
		Long lItemID = 0L;
		
		// Loop through the elements...
		int iCount = xnlAssignments.getLength();
		for(int iIndex = 0; iIndex < iCount; iIndex++) {
			// Grab the current element and create an empty CAssignment object that will hold details about the current record
			xeAssignment = (Element)xnlAssignments.item(iIndex);
			aAssignment = new CAssignment();
			
			// Grab the prefix from the AssignmentID (tells us if this is a Client, Project, Task Group, Task, or Employee record). Grab the ItemID (the actual item's
			// ID - e.g. Employee ID if this is an employee record. The AssignmentID is the ID of the assignment, not the record) and the Name too.
			sAssignmentType = CXMLHelper.getChildNodeValue(xeAssignment, "AssignmentID").substring(0, 1); // Grab the 1st character (C, P, G, T, or E)
			lItemID = Long.valueOf(CXMLHelper.getChildNodeValue(xeAssignment, "ItemID"));
			sItemName = CXMLHelper.getChildNodeValue(xeAssignment, "Name");
			aAssignment.setChildAssignmentsURI(CXMLHelper.getChildNodeValue(xeAssignment, "GetAssignmentsURI"));
			
			// If this is a Client item then...
			if(sAssignmentType.equals(Constants.ASSIGNMENT_TYPE_CLIENT)) {
				// Set the Client information
				aAssignment.setClientID(lItemID);
				aAssignment.setClientName(sItemName);
			} else if(sAssignmentType.equals(Constants.ASSIGNMENT_TYPE_PROJECT)) { // A Project item
				// Set the Project information
				aAssignment.setProjectID(lItemID);
				aAssignment.setProjectName(sItemName);
			} else if(sAssignmentType.equals(Constants.ASSIGNMENT_TYPE_TASKGROUP)) { // A Task Group item (only part of the tree - not part of the time entry data)
				// Set the Task Group information
				aAssignment.setTaskGroupID(lItemID);
				aAssignment.setTaskGroupName(sItemName);
			} else if(sAssignmentType.equals(Constants.ASSIGNMENT_TYPE_TASK)) { // A Task item
				// Set the Task information
				aAssignment.setTaskID(lItemID);
				aAssignment.setTaskName(sItemName);
			} else if(sAssignmentType.equals(Constants.ASSIGNMENT_TYPE_EMPLOYEE)) { // An Employee item
 				// Set the Employee information
				aAssignment.setEmployeeID(lItemID);
				aAssignment.setEmployeeName(sItemName);
			} // End if

			// Add the current item to our list
			lstAssignments.add(aAssignment);
		} // End of the for(int iIndex = 0; iIndex < iCount; iIndex++) loop.
		
		
		// Return the list of Assignments to the caller
		return lstAssignments;
	}
	// ---------------------------------------------------------------------
	// End of the Static Methods use when interacting with the DOVICO Hosted API
	// =====================================================================
}
