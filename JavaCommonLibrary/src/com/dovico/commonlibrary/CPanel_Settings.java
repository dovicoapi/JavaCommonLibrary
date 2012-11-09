package com.dovico.commonlibrary;

import java.awt.*;

import javax.swing.*;

import com.dovico.commonlibrary.data.CEmployee;


public class CPanel_Settings extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel m_lblConsumerSecret = null;
	private JTextField m_txtConsumerSecret = null;
	
	private JLabel m_lblDataAccessToken = null;
	private JTextField m_txtDataAccessToken = null;
	
	// Part of the validation does an 'Employee/Me' call to verify the tokens and since we're making the call, we might as well grab the data if the application
	// wishes to make use of it.
	private Long m_lEmployeeID = null;
	private String m_sEmployeeFirstName = "";
	private String m_sEmployeeLastName = "";
	
	private String m_sApiVersionTargeted = "";
	
	private int m_iAccessTokenLine1Top = 84; // For apps that need to adjust where the Access Token label text is located call: adjustAccessTokenLabelVerticalPositions
	private JLabel m_lblAccessTokenLine1 = null;
	private JLabel m_lblAccessTokenLine2 = null;
	private JLabel m_lblAccessTokenLine3 = null;
		
	
	// Default constructor
	public CPanel_Settings(){
		// Using Absolute layout
		setLayout(null);
		
		// Consumer Secret controls:
		m_lblConsumerSecret = new JLabel("Consumer Secret:");
		m_lblConsumerSecret.setBounds(10, 11, 109, 14);
		m_lblConsumerSecret.setFont(new Font("Arial", Font.PLAIN, 11));
		this.add(m_lblConsumerSecret);
		
		m_txtConsumerSecret = new JTextField();
		m_txtConsumerSecret.setBounds(131, 8, 309, 20);		
		m_txtConsumerSecret.setFont(new Font("Arial", Font.PLAIN, 11));
		m_txtConsumerSecret.setColumns(10);
		this.add(m_txtConsumerSecret);
		
		// Data Access Token controls:
		m_lblDataAccessToken = new JLabel("Data Access Token:");
		m_lblDataAccessToken.setBounds(10, 42, 109, 14);
		m_lblDataAccessToken.setFont(new Font("Arial", Font.PLAIN, 11));
		this.add(m_lblDataAccessToken);
		
		m_txtDataAccessToken = new JTextField();
		m_txtDataAccessToken.setBounds(131, 39, 309, 20);
		m_txtDataAccessToken.setFont(new Font("Arial", Font.PLAIN, 11));
		m_txtDataAccessToken.setColumns(10);
		this.add(m_txtDataAccessToken);
		
		
		/*----------------------
		Section that tells the user where they can find the Data Access Token 
		----------------------*/
		m_lblAccessTokenLine1 = new JLabel("This App requires administrator API tokens from DOVICO Timesheet\u2122");
		m_lblAccessTokenLine1.setHorizontalAlignment(SwingConstants.CENTER);
		m_lblAccessTokenLine1.setFont(new Font("Arial", Font.PLAIN, 11));		
		add(m_lblAccessTokenLine1);
		
		m_lblAccessTokenLine2 = new JLabel("via Database Options (Menu > Setup > Database Options > API)");
		m_lblAccessTokenLine2.setHorizontalAlignment(SwingConstants.CENTER);
		m_lblAccessTokenLine2.setFont(new Font("Arial", Font.PLAIN, 11));		
		add(m_lblAccessTokenLine2);
		
		m_lblAccessTokenLine3 = new JLabel("");
		m_lblAccessTokenLine3.setHorizontalAlignment(SwingConstants.CENTER);
		m_lblAccessTokenLine3.setFont(new Font("Arial", Font.PLAIN, 11));		
		add(m_lblAccessTokenLine3);
		
		// Position the Access Token labels
		adjustAccessTokenLabelVerticalPositions(0);
	}
	
	
	/// <summary>
    /// Helper that adjusts the vertical positions the Access token labels
    /// </summary>
    /// <param name="iVerticalAdjustment" type="int" ref="false" inout="[in]" description="Value that can be used to adjust the vertical positioning of the Access Token labels. Use a negative number to move them up, positive to move them down."/>
    /// <returns>void</returns>
	/// <history>
    /// <modified author="C. Gerard Gallant" date="2012-11-09" reason="Created"/>
    /// </history>
	public void adjustAccessTokenLabelVerticalPositions(int iVerticalAdjustment)
	{
		m_lblAccessTokenLine1.setBounds(10, (m_iAccessTokenLine1Top + iVerticalAdjustment), 418, 14);
		m_lblAccessTokenLine2.setBounds(10, ((m_iAccessTokenLine1Top + 17) + iVerticalAdjustment), 418, 14);
		m_lblAccessTokenLine3.setBounds(10, ((m_iAccessTokenLine1Top + 34) + iVerticalAdjustment), 418, 14); // +34 is simply: m_iAccessTokenLine1Top + 17 + 17
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
	/// <modified author="C. Gerard Gallant" date="2012-04-19" reason="Modified to call the overloaded function. Passes 'false' for the bHideConsumerSecretField parameter"/>
	/// </history>
	public void setSettingsData(String sConsumerSecret, String sDataAccessToken, String sApiVersionTargeted, Long lEmployeeID, String sEmployeeFirstName, 
			String sEmployeeLastName) {
		// Call the overloaded function passing 'false' for the bHideConsumerSecretField field
		this.setSettingsData(sConsumerSecret, sDataAccessToken, sApiVersionTargeted, lEmployeeID, sEmployeeFirstName, sEmployeeLastName, false);
	}
	
	
	// Overloaded method for setting the panel's values and also to indicate if the Consumer Secret fields should be displayed or not
	/// <history>
	/// <modified author="C. Gerard Gallant" date="2012-11-09" reason="Code added for the Access Token labels to indicate to the user where to find the tokens"/>
	/// </history>
	public void setSettingsData(String sConsumerSecret, String sDataAccessToken, String sApiVersionTargeted, Long lEmployeeID, String sEmployeeFirstName, 
			String sEmployeeLastName, boolean bHideConsumerSecretField) {
		m_txtConsumerSecret.setText(sConsumerSecret);
		m_txtDataAccessToken.setText(sDataAccessToken);
		m_sApiVersionTargeted = sApiVersionTargeted;
		m_lEmployeeID = lEmployeeID; 
		m_sEmployeeFirstName = sEmployeeFirstName;
		m_sEmployeeLastName = sEmployeeLastName;
		
		// If we are to hide the Consumer Secret fields then... 
		if(bHideConsumerSecretField) {
			// Hide the Consumer Secret fields
			m_lblConsumerSecret.setVisible(false);
			m_txtConsumerSecret.setVisible(false);
			
			// Adjust the Y position of the Data Access Token controls to be where the Consumer Secret fields are
			m_lblDataAccessToken.setBounds(10, 11, 109, 14); // y from 42 to 11
			m_txtDataAccessToken.setBounds(131, 8, 309, 20); // y from 39 to 8
			
						
			// We don't need to tell the user where to find the Admin tokens, we need to tell the user where to find the Data Access Token (we only need to adjust
			// the text in lines 1 and 3 - line 2 is fine)
			m_lblAccessTokenLine1.setText("This App requires a Data Access Token from DOVICO Timesheet\u2122");
			m_lblAccessTokenLine3.setText("or via My Time & Expenses / Options (Menu > Views > My Time & Expenses > Options)");

			// Let's also tweak the vertical position of the controls to place them a bit higher on the page since the Consumer Secret field is not present
			adjustAccessTokenLabelVerticalPositions(-30);
		} // End if(bHideConsumerSecretField)
	}
	
	
	// Methods returning the setting values (should only be called if the call to validateSettingsData returns 'true')
	public String getConsumerSecret() { return m_txtConsumerSecret.getText(); }
	public String getDataAccessToken() { return m_txtDataAccessToken.getText(); }
	public Long getEmployeeID() { return m_lEmployeeID; }
	public String getEmployeeFirstName() { return m_sEmployeeFirstName; }
	public String getEmployeeLastName() { return m_sEmployeeLastName; }
}
