package com.dovico.commonlibrary;

import java.awt.*;
import javax.swing.*;

import com.dovico.commonlibrary.data.CEmployee;


public class CPanel_Settings extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField m_txtConsumerSecret = null;
	private JTextField m_txtDataAccessToken = null;
	
	// Part of the validation does an 'Employee/Me' call to verify the tokens and since we're making the call, we might as well grab the data if the application
	// wishes to make use of it.
	private Long m_lEmployeeID = null;
	private String m_sEmployeeFirstName = "";
	private String m_sEmployeeLastName = "";
	
	private String m_sApiVersionTargeted = ""; 
	
	
	// Default constructor
	public CPanel_Settings(){
		// Using Absolute layout
		setLayout(null);
				
		// Consumer Secret controls:
		JLabel lblConsumerSecret = new JLabel("Consumer Secret:");
		lblConsumerSecret.setBounds(10, 11, 109, 14);		
		lblConsumerSecret.setFont(new Font("Arial", Font.PLAIN, 11));
		this.add(lblConsumerSecret);
		
		m_txtConsumerSecret = new JTextField();
		m_txtConsumerSecret.setBounds(131, 8, 309, 20);		
		m_txtConsumerSecret.setFont(new Font("Arial", Font.PLAIN, 11));
		m_txtConsumerSecret.setColumns(10);
		this.add(m_txtConsumerSecret);
		
		
		// Data Access Token controls:
		JLabel lblDataAccessToken = new JLabel("Data Access Token:");
		lblDataAccessToken.setBounds(10, 42, 109, 14);
		lblDataAccessToken.setFont(new Font("Arial", Font.PLAIN, 11));
		this.add(lblDataAccessToken);
		
		m_txtDataAccessToken = new JTextField();
		m_txtDataAccessToken.setBounds(131, 39, 309, 20);
		m_txtDataAccessToken.setFont(new Font("Arial", Font.PLAIN, 11));
		m_txtDataAccessToken.setColumns(10);
		this.add(m_txtDataAccessToken);
	}
	
	
	// Helper that checks to see if all fields, on the Settings tab, have a value, etc
	/// <history>
    /// <modified author="C. Gerard Gallant" date="2011-12-15" reason="Added a call to the new queryEmployeeMeData function."/>
    /// </history>
	public boolean validateSettingsData() {		
		// Make sure that each text box has a value provided
		if(!validateTextBoxHasValue(m_txtConsumerSecret, "Please provide the Consumer Secret.")) { return false; }
		if(!validateTextBoxHasValue(m_txtDataAccessToken, "Please provide the Data Access Token.")) { return false; }
		
		// At this point we know that the both tokens have been provided but we don't know if they're valid. Let's call the API for the Employee/Me data to test
		// the tokens and at the same time grab the logged in user's ID and Name
		if(!queryEmployeeMeData()) { return false; }
				
		// We made it to this point. All is OK.
		return true;
	}
	
	
	// Validates a text box to ensure it has a value. If not displays the error message to the user.
	private boolean validateTextBoxHasValue(JTextField txtControl, String sValidationMessage) {
		// Get the trimmed version of the string from the field. Put the trimmed string back.
		String sValue = txtControl.getText().trim();
		txtControl.setText(sValue);
		
		// If the string is empty then...
		if(sValue.isEmpty()) 
		{ 
			// Tell the user of the issue, give the control the focus, and tell the calling function that validation failed 
			JOptionPane.showMessageDialog(null, sValidationMessage, "Value Missing", JOptionPane.ERROR_MESSAGE);
			txtControl.grabFocus();
			return false;
		} // End if(sValue.isEmpty()) 
		
		
		// We made it to this point, everything validated 
		return true;
	}		
	
	
	/// <summary>
    /// Queries the API for 'Employee/Me' data to test that the tokens are valid and to get the basic information of the user who is logging in (employee id, first
	/// name, and last name)
    /// </summary>
    /// <param name="" type="" ref="false" inout="N/A" description="N/A"/>
    /// <returns>false if there was an issue, true otherwise.</returns>
    /// <history>
    /// <modified author="C. Gerard Gallant" date="2011-12-15" reason="Created"/>
    /// <modified author="C. Gerard Gallant" date="2012-03-30" reason="Reworked to use the new CEmployee class and logic"/>
	/// </history>
	private boolean queryEmployeeMeData() {
		// Request the Employee/Me record (info of the person logging in)
		APIRequestResult aRequestResult = new APIRequestResult(getConsumerSecret(), getDataAccessToken(), m_sApiVersionTargeted, true);
		CEmployee eEmployee = CEmployee.getInfoMe(aRequestResult);

		// If the employee object is null there was a problem so exit now
		if(eEmployee == null) { return false; }
		
	
		// Pull the Employee ID, First Name, and Last Name from the employee node
		m_lEmployeeID = eEmployee.getID();
		m_sEmployeeFirstName = eEmployee.getFirstName();
		m_sEmployeeLastName = eEmployee.getLastName();

		// We made it to this point, everything is ok
		return true;
	}	
	
	
		
	// Called to have the values placed into the controls and the member variables set properly
	/// <history>
    /// <modified author="C. Gerard Gallant" date="2011-12-15" reason="Added the parameters lEmployeeID, sEmployeeFirstName, and sEmployeeLastName"/>
    /// <modified author="C. Gerard Gallant" date="2012-03-30" reason="Modified to accept the sApiVersionTargeted parameter"/>
    /// </history>
	public void setSettingsData(String sConsumerSecret, String sDataAccessToken, String sApiVersionTargeted, Long lEmployeeID, String sEmployeeFirstName, 
			String sEmployeeLastName) {
		m_txtConsumerSecret.setText(sConsumerSecret);
		m_txtDataAccessToken.setText(sDataAccessToken);
		m_sApiVersionTargeted = sApiVersionTargeted;
		m_lEmployeeID = lEmployeeID; 
		m_sEmployeeFirstName = sEmployeeFirstName;
		m_sEmployeeLastName = sEmployeeLastName;
	}

	
	// Methods returning the setting values (should only be called if the call to validateSettingsData returns 'true')
	public String getConsumerSecret() { return m_txtConsumerSecret.getText(); }
	public String getDataAccessToken() { return m_txtDataAccessToken.getText(); }
	public Long getEmployeeID() { return m_lEmployeeID; }
	public String getEmployeeFirstName() { return m_sEmployeeFirstName; }
	public String getEmployeeLastName() { return m_sEmployeeLastName; }
}
