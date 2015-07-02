package com.dovico.commonlibrary;

import java.awt.event.ItemEvent;

import javax.swing.*;

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
    private javax.swing.JRadioButton optCompany;
    private javax.swing.JRadioButton optToken;
    private javax.swing.JPanel tokenPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    
	// Part of the validation does an 'Employee/Me' call to verify the tokens and since we're making the call, we might as well grab the
    // data if the application wishes to make use of it.
	private Long m_lEmployeeID = null;
	private String m_sEmployeeFirstName = "";
	private String m_sEmployeeLastName = "";
	
	private String m_sApiVersionTargeted = "";
	
	private String m_sCompanyName = "";
	private String m_sUserName = ""; 
	
	private String m_sConsumerSecret = "";
	private String m_sDataToken = "";
	

	// Default constructor
	public CPanel_Settings(){
		// Using Absolute layout
		setLayout(null);

		initComponents();
	}
	
	
	private void initComponents() {
        btnGroupLogin = new javax.swing.ButtonGroup();
        optCompany = new javax.swing.JRadioButton();
        optToken = new javax.swing.JRadioButton();
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

        btnGroupLogin.add(optCompany);
        optCompany.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ItemStateChanged_optCompany(evt);
            }
        });

        btnGroupLogin.add(optToken);
        optToken.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ItemStateChanged_optToken(evt);
            }
        });

        tokenPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        m_lblAdminToken.setFont(m_lblAdminToken.getFont());
        m_lblAdminToken.setText("Data Access Token:");

        m_txtAdminToken.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_txtAdminToken.setPreferredSize(new java.awt.Dimension(309, 24));

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
        m_txtCompanyName.setPreferredSize(new java.awt.Dimension(309, 24));

        m_lblUserName.setFont(m_lblUserName.getFont());
        m_lblUserName.setText("Username:");

        m_txtUserName.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_txtUserName.setPreferredSize(new java.awt.Dimension(309, 24));

        m_lblPassword.setFont(m_lblPassword.getFont());
        m_lblPassword.setText("Password:");

        m_txtPassword.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_txtPassword.setPreferredSize(new java.awt.Dimension(309, 24));

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
                        .addComponent(optCompany)
                        .addGap(18, 18, 18)
                        .addComponent(companyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(optToken)
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
                    .addComponent(optCompany)
                    .addComponent(companyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optToken)
                    .addComponent(tokenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
    }// </editor-fold>

	
    private void ItemStateChanged_optCompany(ItemEvent evt) { setCompanyPanelState((evt.getStateChange() == ItemEvent.SELECTED)); }

	protected void setCompanyPanelState(boolean bEnabled) {
		companyPanel.setEnabled(bEnabled);
        m_txtCompanyName.setEnabled(bEnabled);
        m_txtPassword.setEnabled(bEnabled);
        m_txtUserName.setEnabled(bEnabled);
        
        if (!bEnabled) {
        	m_txtCompanyName.setText(null);
        	m_txtPassword.setText(null);
        	m_txtUserName.setText(null);

			m_sCompanyName = "";
			m_sUserName = "";
		}
	}

    private void ItemStateChanged_optToken(ItemEvent evt) { setTokenPanelState((evt.getStateChange() == ItemEvent.SELECTED)); }

	protected void setTokenPanelState(boolean bEnabled) {
		tokenPanel.setEnabled(bEnabled);
        m_txtAdminToken.setEnabled(bEnabled);
        
        if (!bEnabled) {
        	m_txtAdminToken.setText(null);
        	m_sDataToken = "";
        }
	}

	
	// Helper that checks to see if all fields, on the Settings tab, have a value, etc
	/// <history>
    /// <modified author="C. Gerard Gallant" date="2011-12-15" reason="Added a call to the new queryEmployeeMeData function."/>
    /// </history>
	public boolean validateSettingsData() {		
		if (optCompany.isSelected()) {
			if (!validateTextBoxHasValue(m_txtCompanyName, "Please enter a company name.")) { return false; }
			if (!validateTextBoxHasValue(m_txtUserName, "Please enter a user name.")) { return false; }
			if (!validateTextBoxHasValue(m_txtPassword, "Please enter a password.")) { return false; }
			
			m_sCompanyName = m_txtCompanyName.getText();
			m_sUserName = m_txtUserName.getText();
		
		
			String sUserInfoXml = "<UserInfo><CompanyName>" + CXMLHelper.encodeTextForElement(m_sCompanyName) + "</CompanyName>";
			sUserInfoXml += "<UserName>" + CXMLHelper.encodeTextForElement(m_sUserName) + "</UserName>";
			sUserInfoXml += "<Password>" + CXMLHelper.encodeTextForElement(m_txtPassword.getText()) + "</Password></UserInfo>";
			
			APIRequestResult aRequestResult = new APIRequestResult(m_sConsumerSecret, "", m_sApiVersionTargeted, true);
			aRequestResult.setRequestHttpMethod("POST");
			aRequestResult.setRequestURI(CRESTAPIHelper.buildURI("Authenticate/", "", m_sApiVersionTargeted));
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
		
		if (optToken.isSelected()) {
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
	public void setSettingsData(String sConsumerSecret, String sDataAccessToken, String sCompanyName, String sUserName, String sPassword, 
			String sApiVersionTargeted, Long lEmployeeID, String sEmployeeFirstName, String sEmployeeLastName) {
		
		m_sConsumerSecret = sConsumerSecret;
		
		m_txtCompanyName.setText(sCompanyName);
		m_txtUserName.setText(sUserName);
		m_txtPassword.setText(sPassword);
		
		if (sCompanyName != null && !sCompanyName.isEmpty()
				&& sUserName != null && !sUserName.isEmpty()) {
			
			m_sCompanyName = sCompanyName;
			m_sUserName = sUserName;
			optCompany.setSelected(true);
			setTokenPanelState(false);
		} else {
			setCompanyPanelState(false);
			if (sDataAccessToken != null && !sDataAccessToken.isEmpty()) {
				m_sDataToken = sDataAccessToken;
				optToken.setSelected(true);
				m_txtAdminToken.setText(sDataAccessToken);
			} else {
				setTokenPanelState(false);
				optCompany.setSelected(true);
			}
		}
		
		m_sApiVersionTargeted = sApiVersionTargeted;
		m_lEmployeeID = lEmployeeID; 
		m_sEmployeeFirstName = sEmployeeFirstName;
		m_sEmployeeLastName = sEmployeeLastName;
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
