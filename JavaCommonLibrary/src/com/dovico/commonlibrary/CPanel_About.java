package com.dovico.commonlibrary;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.net.URL;
import java.awt.Color;

public class CPanel_About extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// Defaults for the control values
	private static final String m_sLogoPath = "/com/dovico/commonlibrary/resources/DOVICOLogo.png";
	private static final String m_sContactGroupBoxCaption = "";//"Customer Support"; <-- Doesn't look good with the 'Nimbus' look and feel (personally, I don't even like Nimbus but it wasn't my call)
	private static final String m_sContact1Label = "North America Toll Free:";
	private static final String m_sContact1Value = "1-800-618-8463";
	private static final String m_sContact2Label = "International Free Phone:";
	private static final String m_sContact2Value = "00 800 4618 8463";
	private static final String m_sContact3Label = "E-mail:";
	private static final String m_sContact3Value = "support@dovico.com";
	private static final String m_sCopyrightInfo = "Copyright (C) 2014 DOVICO Software Inc. All rights reserved.";
	
	
	// Default constructor. Simply call the 2 parameter overloaded constructor with default values 
	public CPanel_About()  { this("<Product Name>", "<1.0>"); }
	
	// Overloaded constructor that uses the default logo, contact info, and copyright info (for use by apps written by DOVCIO)
	public CPanel_About(String sProductName, String sVersionNumber) {
		// Call our overloaded constructor that accepts all values and pass in the defaults 
		this(m_sLogoPath, sProductName, sVersionNumber, m_sContactGroupBoxCaption, m_sContact1Label, m_sContact1Value, m_sContact2Label, m_sContact2Value, 
				m_sContact3Label, m_sContact3Value, m_sCopyrightInfo);
	}
	
	// Overloaded constructor for when you want to specify all values
	// sUrlLogoLocation needs to be in the form: "/com/dovico/commonlibrary/resources/DOVICOLogo.png" (the path name to the namespace where the resource file is 
	// stored)
	public CPanel_About(String sUrlLogoLocation, String sProductName, String sVersionNumber, String sContactGroupBoxCaption, String sContact1Label, 
			String sContact1Value, String sContact2Label, String sContact2Value, String sContact3Label, String sContact3Value, String sCopyrightInfo) {
		// Call our helper method to have the controls created and their values set 
		CreateUIControls(CPanel_About.class.getResource(sUrlLogoLocation), sProductName, sVersionNumber, sContactGroupBoxCaption, sContact1Label, sContact1Value, sContact2Label, sContact2Value, 
				sContact3Label, sContact3Value, sCopyrightInfo);
	}
	
	
	protected void CreateUIControls(URL urlLogoLocation, String sProductName, String sVersionNumber, String sContactGroupBoxCaption, String sContact1Label, 
			String sContact1Value, String sContact2Label, String sContact2Value, String sContact3Label, String sContact3Value, String sCopyrightInfo){
		// Absolute layout
		setLayout(null);
		
		// The LOGO image
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(10, 11, 435, 80);
		lblLogo.setIcon(new ImageIcon(urlLogoLocation));
		add(lblLogo);
		
		// Product name
		JLabel lblProductName = new JLabel(sProductName);
		lblProductName.setFont(new Font("Arial", Font.BOLD, 14));
		lblProductName.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductName.setBounds(10, 114, 435, 14);
		add(lblProductName);
		
		// Version #
		JLabel lblVersionNumber = new JLabel(("Version " + sVersionNumber));
		lblVersionNumber.setFont(new Font("Arial", Font.PLAIN, 11));
		lblVersionNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersionNumber.setBounds(10, 135, 435, 14);
		add(lblVersionNumber);
		
		// Group/Control box for the contact information (we add a space on either side of the caption because if you don't the border is right up against the 
		// text - doesn't look good)
		JPanel pContactGroupBox = new JPanel();
		pContactGroupBox.setBounds(10, 161, 435, 95);
		pContactGroupBox.setBorder(new TitledBorder(null, (sContactGroupBoxCaption.isEmpty() ? "" : (" "+ sContactGroupBoxCaption + " ")), TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0))); 
		pContactGroupBox.setLayout(null);
		add(pContactGroupBox);		
		
		
		// Contact # 1 (phone, fax, email, etc)
		JLabel lblContact1Label = new JLabel(sContact1Label);
		lblContact1Label.setFont(new Font("Arial", Font.PLAIN, 11));
		lblContact1Label.setBounds(12, 13, 146, 14);
		pContactGroupBox.add(lblContact1Label);
		
		JLabel lblContact1Value = new JLabel(sContact1Value);
		lblContact1Value.setFont(new Font("Arial", Font.BOLD, 11));
		lblContact1Value.setBounds(168, 13, 259, 14);
		pContactGroupBox.add(lblContact1Value);
		
		// Contact # 2 (phone, fax, email, etc)
		JLabel lblContact2Label = new JLabel(sContact2Label);
		lblContact2Label.setFont(new Font("Arial", Font.PLAIN, 11));
		lblContact2Label.setBounds(12, 38, 146, 14);
		pContactGroupBox.add(lblContact2Label);
		
		JLabel lblContact2Value = new JLabel(sContact2Value);
		lblContact2Value.setFont(new Font("Arial", Font.BOLD, 11));
		lblContact2Value.setBounds(166, 38, 259, 14);
		pContactGroupBox.add(lblContact2Value);
		
		// Contact # 3 (phone, fax, email, etc)
		JLabel lblContact3Label = new JLabel(sContact3Label);
		lblContact3Label.setFont(new Font("Arial", Font.PLAIN, 11));
		lblContact3Label.setBounds(12, 63, 146, 14);
		pContactGroupBox.add(lblContact3Label);
		
		JLabel lblContact3Value = new JLabel(sContact3Value);
		lblContact3Value.setFont(new Font("Arial", Font.BOLD, 11));
		lblContact3Value.setBounds(168, 63, 259, 14);
		pContactGroupBox.add(lblContact3Value);
		
		
		// Copyright line
		JLabel lblCopyrightInfo = new JLabel(sCopyrightInfo);
		lblCopyrightInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyrightInfo.setFont(new Font("Arial", Font.PLAIN, 11));
		lblCopyrightInfo.setBounds(10, 275, 435, 14);
		add(lblCopyrightInfo);
	}

}
