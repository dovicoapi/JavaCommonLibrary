package com.dovico.commonlibrary;

import java.awt.*;
import javax.swing.*;


public class CPanel_Settings extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField m_txtConsumerSecret = null;
	private JTextField m_txtDataAccessToken = null;
	
	
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
	public boolean validateSettingsData() {		
		// Make sure that each text box has a value provided
		if(!validateTextBoxHasValue(m_txtConsumerSecret, "Please provide the Consumer Secret.")) { return false; }
		if(!validateTextBoxHasValue(m_txtDataAccessToken, "Please provide the Data Access Token.")) { return false; }
		
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
	
		
	// Called by the default constructor to have the values placed into the controls
	public void setSettingsData(String sConsumerSecret, String sDataAccessToken) {
		m_txtConsumerSecret.setText(sConsumerSecret);
		m_txtDataAccessToken.setText(sDataAccessToken);
	}

	
	// Methods returning the setting values (should only be called if the call to validateSettingsData returns 'true')
	public String getConsumerSecret() { return m_txtConsumerSecret.getText(); }
	public String getDataAccessToken() { return m_txtDataAccessToken.getText(); }
}
