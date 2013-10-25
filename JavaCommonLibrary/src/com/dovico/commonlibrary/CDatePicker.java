package com.dovico.commonlibrary;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// Not perfect but it works. We now have a date picker control :)
//
// Future item: Have some way to jump to a Month or Year (drop-downs perhaps). For now, we only have the Previous/Next buttons to cycle through the months
public class CDatePicker {
	private JDialog m_dlgSelf = null;	
	private JLabel m_lblMonthYear = null;
	private JButton[] m_arrMonthDayButtons = new JButton[42];
	
	private Calendar m_calDate = null;
	private String m_sSelectedDay = "";
	private ActionListener m_alListener = null;
	
	
	// The constructor simply lays out the controls
	// NOTE: alListener will be told of the date selection. It is not required and can be set to null if you don't want to pass in a listener
	// NOTE: You can call setDate before showing this control. If you do that, you can set dtDate to null here. If you pass null here and do not call setDate, the
	// 		 current date is used when you call setVisible(true).
	public CDatePicker(Component cParent, ActionListener alListener, String sTitle, Date dtDate){
		// Remember the listener and date (if the date is null, it's ignored by the setDate function)
		m_alListener = alListener;
		this.setDate(dtDate);
		
			
		// Put the width in a variable because it's needed by m_dlgSelf and pMonthDaysRow
		int iWidth = 330;
		
		// Create our date picker window, give it the specified title and indicate that it is to be a modal window
		m_dlgSelf = new JDialog();
		m_dlgSelf.setTitle(sTitle);
		m_dlgSelf.setSize(iWidth, 290);//width, height
		m_dlgSelf.setResizable(false);
		m_dlgSelf.setModal(true);
	
		// Main container within the dialog to allow for us to set up a margin so the controls are not jammed against the edge of the form (setBorder call) 
		JPanel pContent = new JPanel(new BorderLayout(0, 0));
		pContent.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		
		// The Month row (previous month button, label of current month/year, next month button) 
		JPanel pMonthRow = new JPanel(new BorderLayout(0, 0));
		pMonthRow.setBorder(new EmptyBorder(2, 2, 2, 2)); // A bit of a border so the controls on this row have a bit of a margin

		JButton cmdPreviousMonth = new JButton("<");
		cmdPreviousMonth.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ae) { onClick_cmdPreviousMonth(); }
		});
		pMonthRow.add(cmdPreviousMonth, BorderLayout.WEST);
		
		m_lblMonthYear = new JLabel("", JLabel.CENTER);
		m_lblMonthYear.setFont(new Font("Arial", Font.BOLD, 12));// Slightly bigger font than the rest to stand out a bit
		pMonthRow.add(m_lblMonthYear, BorderLayout.CENTER);
		
		JButton cmdNextMonth = new JButton(">");
		cmdNextMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) { onClick_cmdNextMonth(); }
        });
		pMonthRow.add(cmdNextMonth, BorderLayout.EAST);
		
		
		// Day of the Week row (filled with the appropriate labels for each day)		
		JPanel pDayOfWeekRow = new JPanel(new GridLayout(1, 7));
		String[] arrDayOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		int iIndex = 0;
		for(iIndex = 0; iIndex < 7; iIndex++) {
			JLabel lblDay = new JLabel(arrDayOfWeek[iIndex], JLabel.CENTER);
			lblDay.setFont(new Font("Arial", Font.BOLD, 11));
			pDayOfWeekRow.add(lblDay); 
		} // End of the for(iIndex = 0; iIndex < 7; iIndex++) loop.
		
		
		// Month's days
		JPanel pMonthDaysRow = new JPanel(new GridLayout(6, 7));
		pMonthDaysRow.setBorder(new EmptyBorder(2, 2, 2, 2)); // Just so that the controls line up with the Prev/Next buttons above
		pMonthDaysRow.setPreferredSize(new Dimension(iWidth, 200));
		
		// Loop through adding in all of the buttons for the calendar days (no text yet. that gets set in the displayDate function)
		int iButtons = m_arrMonthDayButtons.length;
		for (iIndex = 0; iIndex < iButtons; iIndex++) {
			final int iSelection = iIndex;
			
			// Create the current button and add a click event
	        m_arrMonthDayButtons[iIndex] = new JButton();
	        m_arrMonthDayButtons[iIndex].addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent ae) { onClick_cmdMonth(iSelection); }
	        });
	        
	        // Add the current button to the panel
	        pMonthDaysRow.add(m_arrMonthDayButtons[iIndex]);
		} // End of the for (iIndex = 0; iIndex < iButtons; iIndex++) loop.
		
		
		
		// Add the 3 sections to the content panel and then add the content panel to the dialog
		pContent.add(pMonthRow, BorderLayout.NORTH);
		pContent.add(pDayOfWeekRow, BorderLayout.CENTER);
		pContent.add(pMonthDaysRow, BorderLayout.SOUTH);
		m_dlgSelf.add(pContent, BorderLayout.CENTER);
	
		// Adjust where this dialog is displayed
        m_dlgSelf.setLocationRelativeTo(cParent);
	}
		
	
	// Helper to cause this dialog to be shown	
	public void setVisible(boolean bShow) {
		// If we are to be shown then...
		if(bShow){
			// If the date was not specified the default to the current date
			if(m_calDate == null) { this.setDate(Calendar.getInstance().getTime()); }
			
			// Flag that nothing has been selected yet and then populate the controls with the proper days of the month
			m_sSelectedDay = "";
			displayDate();
		} // End if(bShow)
		
		// Show/hide this dialog
		m_dlgSelf.setVisible(bShow); 
	}
		
	
	// Helper to adjust the title for this control after the constructor has been called (re-use of the dialog)
	public void setTitle(String sTitle) { m_dlgSelf.setTitle(sTitle); }
	
	
	// Helper to adjust the date for this control after the constructor has been called (re-use of the dialog)  
	public void setDate(Date dtDate) {
		// Only do the following code if we have been passed a date (will be called by the constructor which may or may not have been passed a date object)
		if(dtDate == null) { return; }
		
		// Create a Calendar object from the date object passed in (needed for manipulating dates)
		m_calDate = Calendar.getInstance();
		m_calDate.setTime(dtDate);
		m_calDate.set(Calendar.HOUR, 0); // Zero out the Hour
		m_calDate.set(Calendar.MINUTE, 0); // Zero out the Minute
		m_calDate.set(Calendar.SECOND, 0); // Zero out the Second
		m_calDate.set(Calendar.MILLISECOND, 0); // Zero out the Millisecond		
	}
	
	
    // Adjust the controls to reflect the month's days
    public void displayDate() {
    	// Get a Calendar instance for the first day of the month we're to show   	
        Calendar calFirstDayOfMonth = (Calendar)m_calDate.clone();
        calFirstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        
        // Determine which day of the week the first day of the month lands on. Also get the number of days in the current month 
        int iFirstDayOfWeek = (calFirstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1); // -1 because iIndex is 0-based and this was 1-based
        int iDaysInMonth = calFirstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int iIndex = 0, iCurrentDay = 0, iButtons = m_arrMonthDayButtons.length;
      
        // Loop through all of the buttons...        
        for (iIndex = 0; iIndex < iButtons; iIndex++) {
        	String sButtonCaption = "";
        	boolean bShowButton = false;
        	
        	// If the current index is within this month's days and we haven't exceeded the number of days in the month then...
        	if((iIndex >= iFirstDayOfWeek) && (iCurrentDay < iDaysInMonth)) {
        		// This day is to be displayed. Make sure the button is visible in the event the user changed months.
        		iCurrentDay++;
        		sButtonCaption = Integer.toString(iCurrentDay);
        		bShowButton = true;        		
        	} // End if((iIndex >= iFirstDayOfWeek) && (iCurrentDay < iDaysInMonth))
        	        	
        	// Adjust the current button's text and visibility
        	m_arrMonthDayButtons[iIndex].setText(sButtonCaption);
        	m_arrMonthDayButtons[iIndex].setVisible(bShowButton); 
        } // End of the for (iIndex = 0; iCurrentDay < iButtons; iIndex++) loop.
        
        
        // Display the current month and year in the dialog's Month/Year label
        SimpleDateFormat fFormatter = new SimpleDateFormat("MMMM yyyy");
        m_lblMonthYear.setText(fFormatter.format(calFirstDayOfMonth.getTime()));
    }

    
    // User clicked on the Previous month button
 	private void onClick_cmdPreviousMonth(){
 		// Adjust the month index backwards
 		m_calDate.add(Calendar.MONTH, -1);
 		
 		// Display the new month's days
 		displayDate();	
 	}

 	
 	// User clicked on the Next month button
 	private void onClick_cmdNextMonth() {
 		// Adjust the month index forwards
 	 	m_calDate.add(Calendar.MONTH, +1);
 		
 		// Display the new month's days
 	    displayDate();
 	}
 	
 	
    // User clicked on one of the month's Day buttons
    private void onClick_cmdMonth(int iSelection) {
    	m_sSelectedDay = m_arrMonthDayButtons[iSelection].getActionCommand();
    	
    	// Tell the caller that a selection was made and then hide this dialog
    	if(m_alListener != null){ m_alListener.actionPerformed(new ActionEvent(this, 0, "Selection Made")); }	            	
    	m_dlgSelf.setVisible(false);
    }
    
    
    // NOTE: if no selection was made, returns null. Otherwise, returns the selected date. 
    public Date getSelectedDate() {
    	// If no date was selected, just return an empty string
	    if (m_sSelectedDay.equals("")) { return null; }
	    
	    // Set the date object to the selected day of the month and then return the Date object to the caller
	    m_calDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(m_sSelectedDay));
	    return m_calDate.getTime();
    }
}
