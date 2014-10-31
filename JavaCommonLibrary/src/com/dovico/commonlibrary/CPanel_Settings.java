package com.dovico.commonlibrary;

import java.awt.*;
import java.awt.event.ItemEvent;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import com.dovico.commonlibrary.data.CEmployee;


public class CPanel_Settings extends JPanel {
	private static final long serialVersionUID = 1L;
	
    private javax.swing.ButtonGroup btnGroupLogin;
    private javax.swing.JPanel companyPanel;
    private javax.swing.JLabel m_lblAdminToken;
    private javax.swing.JLabel m_lblCompanyName;
    private javax.swing.JLabel m_lblInstructions;
    private javax.swing.JLabel m_lblPassword;
    private javax.swing.JLabel m_lblUserName;
    private javax.swing.JTextField m_txtAdminToken;
    private javax.swing.JTextField m_txtCompanyName;
    private javax.swing.JTextField m_txtPassword;
    private javax.swing.JTextField m_txtUserName;
    private javax.swing.JRadioButton rdioCompany;
    private javax.swing.JRadioButton rdioToken;
    private javax.swing.JPanel tokenPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    
	// Part of the validation does an 'Employee/Me' call to verify the tokens and since we're making the call, we might as well grab the data if the application
	// wishes to make use of it.
	private Long m_lEmployeeID = null;
	private String m_sEmployeeFirstName = "";
	private String m_sEmployeeLastName = "";
	
	private String m_sApiVersionTargeted = "";
	
	private String m_sCompanyName = "";
	private String m_sUserName = ""; 
	
	private String m_sConsumerSecret = "";
	private String m_sDataToken = "";
	
/*	private int m_iAccessTokenLine1Top = 84; // For apps that need to adjust where the Access Token label text is located call: adjustAccessTokenLabelVerticalPositions
	private JLabel m_lblAccessTokenLine1 = null;
	private JLabel m_lblAccessTokenLine2 = null;
	private JLabel m_lblAccessTokenLine3 = null;
*/
	// Default constructor
	public CPanel_Settings(){
		// Using Absolute layout
		setLayout(null);

		initComponents();
		
/*		btnGroupLogin = new ButtonGroup();
		
		m_lblCompanyName = new JLabel("Company:");
		m_lblCompanyName.setBounds(10, 11, 109, 14);
		m_lblCompanyName.setFont(new Font("Arial", Font.PLAIN, 11));
		this.add(m_lblCompanyName);
		
		m_txtCompanyName = new JTextField();
		m_txtCompanyName.setBounds(131, 8, 309, 20);		
		m_txtCompanyName.setFont(new Font("Arial", Font.PLAIN, 11));
		m_txtCompanyName.setColumns(10);
		this.add(m_txtCompanyName);
		
		m_lblUserName = new JLabel("User name:");
		m_lblUserName.setBounds(10, 42, 109, 14);
		m_lblUserName.setFont(new Font("Arial", Font.PLAIN, 11));
		this.add(m_lblUserName);
		
		m_txtUserName = new JTextField();
		m_txtUserName.setBounds(131, 39, 309, 20);		
		m_txtUserName.setFont(new Font("Arial", Font.PLAIN, 11));
		m_txtUserName.setColumns(10);
		this.add(m_txtUserName);
		
		m_lblPassword = new JLabel("Password:");
		m_lblPassword.setBounds(10, 73, 109, 14);
		m_lblPassword.setFont(new Font("Arial", Font.PLAIN, 11));
		this.add(m_lblPassword);
		
		m_txtPassword = new JPasswordField();
		m_txtPassword.setBounds(131, 70, 309, 20);		
		m_txtPassword.setFont(new Font("Arial", Font.PLAIN, 11));
		m_txtPassword.setColumns(10);
		this.add(m_txtPassword);
		
		
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
		
		
		----------------------
		Section that tells the user where they can find the Data Access Token 
		----------------------
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
*/	}
	
	
	private void initComponents() {
        btnGroupLogin = new javax.swing.ButtonGroup();
        rdioCompany = new javax.swing.JRadioButton();
        rdioToken = new javax.swing.JRadioButton();
        tokenPanel = new javax.swing.JPanel();
        m_lblAdminToken = new javax.swing.JLabel();
        m_txtAdminToken = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        companyPanel = new javax.swing.JPanel();
        m_lblCompanyName = new javax.swing.JLabel();
        m_txtCompanyName = new javax.swing.JTextField();
        m_lblUserName = new javax.swing.JLabel();
        m_txtUserName = new javax.swing.JTextField();
        m_lblPassword = new javax.swing.JLabel();
        m_txtPassword = new javax.swing.JPasswordField();
        m_lblInstructions = new javax.swing.JLabel();

        setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        btnGroupLogin.add(rdioCompany);
        rdioCompany.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdioCompanyItemStateChanged(evt);
            }
        });

        btnGroupLogin.add(rdioToken);
        rdioToken.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdioTokenItemStateChanged(evt);
            }
        });

        tokenPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        m_lblAdminToken.setFont(m_lblAdminToken.getFont());
        m_lblAdminToken.setText("Data Access Token:");

        m_txtAdminToken.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_txtAdminToken.setPreferredSize(new java.awt.Dimension(309, 20));

        jLabel1.setText("This Administrator token may be found by navigating within Dovico");

        jLabel2.setText("Timesheet to Menu/Setup/Database Options/API.");

        javax.swing.GroupLayout tokenPanelLayout = new javax.swing.GroupLayout(tokenPanel);
        tokenPanel.setLayout(tokenPanelLayout);
        tokenPanelLayout.setHorizontalGroup(
            tokenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tokenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tokenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tokenPanelLayout.createSequentialGroup()
                        .addComponent(m_lblAdminToken)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(m_txtAdminToken, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        tokenPanelLayout.setVerticalGroup(
            tokenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tokenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tokenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_lblAdminToken)
                    .addComponent(m_txtAdminToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        companyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        companyPanel.setName("UsernamePasswordPanel"); // NOI18N

        m_lblCompanyName.setText("Company:");

        m_txtCompanyName.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_txtCompanyName.setPreferredSize(new java.awt.Dimension(309, 20));

        m_lblUserName.setFont(m_lblUserName.getFont());
        m_lblUserName.setText("Username:");

        m_txtUserName.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_txtUserName.setPreferredSize(new java.awt.Dimension(309, 20));

        m_lblPassword.setFont(m_lblPassword.getFont());
        m_lblPassword.setText("Password:");

        m_txtPassword.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_txtPassword.setPreferredSize(new java.awt.Dimension(309, 20));

        javax.swing.GroupLayout companyPanelLayout = new javax.swing.GroupLayout(companyPanel);
        companyPanel.setLayout(companyPanelLayout);
        companyPanelLayout.setHorizontalGroup(
            companyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(companyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(companyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_lblCompanyName)
                    .addComponent(m_lblUserName)
                    .addComponent(m_lblPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(companyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m_txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(m_txtCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(m_txtPassword))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        companyPanelLayout.setVerticalGroup(
            companyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(companyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(companyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_lblCompanyName)
                    .addComponent(m_txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(companyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_lblUserName)
                    .addComponent(m_txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(companyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_lblPassword)
                    .addComponent(m_txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        m_lblInstructions.setText("Please select preferred login method:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(m_lblInstructions)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdioCompany)
                        .addGap(18, 18, 18)
                        .addComponent(companyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdioToken)
                        .addGap(18, 18, 18)
                        .addComponent(tokenPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_lblInstructions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdioCompany)
                    .addComponent(companyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdioToken)
                    .addComponent(tokenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void rdioCompanyItemStateChanged(java.awt.event.ItemEvent evt) {
    	boolean enabled = evt.getStateChange() == ItemEvent.SELECTED;
        setCompanyPanelState(enabled);
    }


	protected void setCompanyPanelState(boolean enabled) {
		companyPanel.setEnabled(enabled);
        m_txtCompanyName.setEnabled(enabled);
        m_txtPassword.setEnabled(enabled);
        m_txtUserName.setEnabled(enabled);
        
        if (!enabled) {
        	m_txtCompanyName.setText(null);
        	m_txtPassword.setText(null);
        	m_txtUserName.setText(null);

			m_sCompanyName = "";
			m_sUserName = "";
		}
	}

    private void rdioTokenItemStateChanged(java.awt.event.ItemEvent evt) {
    	boolean enabled = evt.getStateChange() == ItemEvent.SELECTED;
        setTokenPanelState(enabled);
    }


	protected void setTokenPanelState(boolean enabled) {
		tokenPanel.setEnabled(enabled);
        m_txtAdminToken.setEnabled(enabled);
        
        if (!enabled) {
        	m_txtAdminToken.setText(null);
        	m_sDataToken = "";
        }
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
		//m_lblAccessTokenLine1.setBounds(10, (m_iAccessTokenLine1Top + iVerticalAdjustment), 418, 14);
		//m_lblAccessTokenLine2.setBounds(10, ((m_iAccessTokenLine1Top + 17) + iVerticalAdjustment), 418, 14);
		//m_lblAccessTokenLine3.setBounds(10, ((m_iAccessTokenLine1Top + 34) + iVerticalAdjustment), 418, 14); // +34 is simply: m_iAccessTokenLine1Top + 17 + 17
	}

	
	
	// Helper that checks to see if all fields, on the Settings tab, have a value, etc
	/// <history>
    /// <modified author="C. Gerard Gallant" date="2011-12-15" reason="Added a call to the new queryEmployeeMeData function."/>
    /// </history>
	public boolean validateSettingsData() {		
		// Make sure that each text box has a value provided
		//if(!validateTextBoxHasValue(m_txtConsumerSecret, "Please provide the Consumer Secret.")) { return false; }
		//if(!validateTextBoxHasValue(m_txtDataAccessToken, "Please provide the Data Access Token.")) { return false; }
		
		if (rdioCompany.isSelected()) {
			if (!validateTextBoxHasValue(m_txtCompanyName, "Please enter a company name.")) { return false; }
			if (!validateTextBoxHasValue(m_txtUserName, "Please enter a user name.")) { return false; }
			if (!validateTextBoxHasValue(m_txtPassword, "Please enter a password.")) { return false; }
			
			m_sCompanyName = m_txtCompanyName.getText();
			m_sUserName = m_txtUserName.getText();
		
			// At this point we know that the both tokens have been provided but we don't know if they're valid. Let's call the API for the Employee/Me data to test
			// the tokens and at the same time grab the logged in user's ID and Name
			//if(!queryEmployeeMeData()) { return false; }
			
			String sUserInfoXml = "<UserInfo><CompanyName>" + CXMLHelper.encodeTextForElement(m_sCompanyName) + "</CompanyName>";
			sUserInfoXml += "<UserName>" + CXMLHelper.encodeTextForElement(m_sUserName) + "</UserName>";
			sUserInfoXml += "<Password>" + CXMLHelper.encodeTextForElement(m_txtPassword.getText()) + "</Password></UserInfo>";
			
			APIRequestResult aRequestResult = new APIRequestResult( m_sConsumerSecret, "", m_sApiVersionTargeted, true);
			
			aRequestResult.setRequestHttpMethod("POST");
			aRequestResult.setRequestURI(CRESTAPIHelper.buildURI("Authenticate/", "", "2"));
			aRequestResult.setRequestPostPutXmlData(sUserInfoXml);
			
			CRESTAPIHelper.makeAPIRequest(aRequestResult);
			
			Document docResult = aRequestResult.getResultDocument();
			
			if (aRequestResult.getHadRequestError()) 
			{
				JOptionPane.showMessageDialog(null, "Error contacting servers authenticate API.", "Error", JOptionPane.ERROR_MESSAGE);
				
				return false; 
			}
		
			
			NodeList nlError = docResult.getElementsByTagName("Error");
			if (nlError.getLength() > 0) 
			{
				JOptionPane.showMessageDialog(null, "Please check your company name, user name and password.", "Not authenticated", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			NodeList nlToken = docResult.getElementsByTagName("DataAccessToken");
			
			if (nlToken.getLength() > 0)
			{
				Element xeToken = (Element)nlToken.item(0);
				m_sDataToken = xeToken.getFirstChild().getNodeValue();
			}
			else
			{
				return false;
			}
		}
		
		if (rdioToken.isSelected()) {
			if(!validateTextBoxHasValue(m_txtAdminToken, "Please provide the administrative Access Token.")) { return false; }
			m_sDataToken = m_txtAdminToken.getText();
		}
		
		return queryEmployeeMeData();
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
		APIRequestResult aRequestResult = new APIRequestResult(m_sConsumerSecret, m_sDataToken, m_sApiVersionTargeted, true);
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
	public void setSettingsData(String sConsumerSecret, String sDataAccessToken, String sCompanyName, String sUserName, String sPassword, String sApiVersionTargeted, Long lEmployeeID, String sEmployeeFirstName, 
			String sEmployeeLastName) {
		// Call the overloaded function passing 'false' for the bHideConsumerSecretField field
		this.setSettingsData(sConsumerSecret, sDataAccessToken, sCompanyName, sUserName, sPassword, sApiVersionTargeted, lEmployeeID, sEmployeeFirstName, sEmployeeLastName, false);
	}
	
	
	// Overloaded method for setting the panel's values and also to indicate if the Consumer Secret fields should be displayed or not
	/// <history>
	/// <modified author="C. Gerard Gallant" date="2012-11-09" reason="Code added for the Access Token labels to indicate to the user where to find the tokens"/>
	/// </history>
	public void setSettingsData(String sConsumerSecret, String sDataAccessToken, String sCompanyName, String sUserName, String sPassword, String sApiVersionTargeted, Long lEmployeeID, String sEmployeeFirstName, 
			String sEmployeeLastName, boolean bHideConsumerSecretField) {
		
		m_sConsumerSecret = sConsumerSecret;
		
		m_txtCompanyName.setText(sCompanyName);
		m_txtUserName.setText(sUserName);
		m_txtPassword.setText(sPassword);
		
		if (sCompanyName != null && !sCompanyName.isEmpty()
				&& sUserName != null && !sUserName.isEmpty()) {
			
			m_sCompanyName = sCompanyName;
			m_sUserName = sUserName;
			rdioCompany.setSelected(true);
			setTokenPanelState(false);
		} else {
			setCompanyPanelState(false);
			if (sDataAccessToken != null && !sDataAccessToken.isEmpty()) {
				m_sDataToken = sDataAccessToken;
				rdioToken.setSelected(true);
				m_txtAdminToken.setText(sDataAccessToken);
			} else {
				setTokenPanelState(false);
				rdioCompany.setSelected(true);
			}
		}
		
		//m_txtConsumerSecret.setText(sConsumerSecret);
		//m_txtDataAccessToken.setText(sDataAccessToken);
		m_sApiVersionTargeted = sApiVersionTargeted;
		m_lEmployeeID = lEmployeeID; 
		m_sEmployeeFirstName = sEmployeeFirstName;
		m_sEmployeeLastName = sEmployeeLastName;
		
		// If we are to hide the Consumer Secret fields then... 
		if(bHideConsumerSecretField) {
			// Hide the Consumer Secret fields
			//m_lblConsumerSecret.setVisible(false);
			//m_txtConsumerSecret.setVisible(false);
			
			// Adjust the Y position of the Data Access Token controls to be where the Consumer Secret fields are
			//m_lblDataAccessToken.setBounds(10, 11, 109, 14); // y from 42 to 11
			//m_txtDataAccessToken.setBounds(131, 8, 309, 20); // y from 39 to 8
			
						
			// We don't need to tell the user where to find the Admin tokens, we need to tell the user where to find the Data Access Token (we only need to adjust
			// the text in lines 1 and 3 - line 2 is fine)
			//m_lblAccessTokenLine1.setText("This App requires a Data Access Token from DOVICO Timesheet\u2122");
			//m_lblAccessTokenLine3.setText("or via My Time & Expenses / Options (Menu > Views > My Time & Expenses > Options)");

			// Let's also tweak the vertical position of the controls to place them a bit higher on the page since the Consumer Secret field is not present
			//adjustAccessTokenLabelVerticalPositions(-30);
		} // End if(bHideConsumerSecretField)
	}
	
	
	// Methods returning the setting values (should only be called if the call to validateSettingsData returns 'true')
	public String getCompanyName() { return m_sCompanyName; }
	public String getUserName() { return m_sUserName; }
	public String getConsumerSecret() { return m_sConsumerSecret; }
	public String getDataAccessToken() { return m_sDataToken; }
	public Long getEmployeeID() { return m_lEmployeeID; }
	public String getEmployeeFirstName() { return m_sEmployeeFirstName; }
	public String getEmployeeLastName() { return m_sEmployeeLastName; }
}
