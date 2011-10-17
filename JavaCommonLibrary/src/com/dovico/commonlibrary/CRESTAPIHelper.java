package com.dovico.commonlibrary;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;


public class CRESTAPIHelper {
	// The root URI for REST API calls
	private final static String m_sRootURI = "https://api.dovico.com/"; 

	// sURIPart: Clients/
	// sOptionalQueryStrings: some resources like GET 'TimeEntries/' can accept certain query strings (like 'daterange={daterange}')
	// sRESTAPIVersion: the version of the REST API being targeted (e.g. 1)
	public static String buildURI(String sURIPart, String sOptionalQueryStrings, String sRESTAPIVersion) {
		// Check to see if there was a query string passed in. Build up the Query string for the URI
		boolean bHaveQueryString = !sOptionalQueryStrings.isEmpty();
		String sQueryString = ("?" + sOptionalQueryStrings + (bHaveQueryString ? "&": "") + "version=" + sRESTAPIVersion);
		
		// Return the proper URI
		return (m_sRootURI + sURIPart + sQueryString);
	}		
	
	
	// Builds the Authorization header's content
	private static String buildAuthorizationHeaderContent(String sConsumerSecret, String sDataAccessToken) {
		return ("WRAP access_token=\"client="+ sConsumerSecret + "&user_token="+ sDataAccessToken + "\"");
	}
	

	// Returns the result of a GET call (REST API v1 is currently XML-based so an XML Document object is returned by this function) 
	public static APIRequestResult makeAPIRequest(String sURI, String sHttpMethod, String sPostPutXMLData, String sConsumerSecret, String sDataAccessToken) {
		APIRequestResult arResult = new APIRequestResult(); 
		String sErrorMsg = "";

		try {
			// Build up the Request to the REST API
			URL url = new URL(sURI);			
			HttpURLConnection hConn = (HttpURLConnection)url.openConnection();
			hConn.setRequestProperty("Authorization", buildAuthorizationHeaderContent(sConsumerSecret, sDataAccessToken));
			hConn.setRequestProperty("Accept", "text/xml"); // Can also use 'application/xml'			
			hConn.setRequestMethod(sHttpMethod); // e.g. GET, POST, PUT, DELETE
			
			// If data was provided to be sent then...
			if(sPostPutXMLData != null) {
				hConn.setRequestProperty("Content-Type", "text/xml");
				hConn.setDoOutput(true); // Needed if we want to send data
			
				// Write the data to the output stream 
				OutputStreamWriter wrOutput = new OutputStreamWriter(hConn.getOutputStream());
				wrOutput.write(sPostPutXMLData);
				wrOutput.flush();
				wrOutput.close();
			} // End if(sPostPutXMLData != null)
			
			
			DocumentBuilderFactory dbfFactory = DocumentBuilderFactory.newInstance();
			
			// Establish the connection to the REST API and get the response status code. If the response is 300 or above (error condition) then...
			hConn.connect();
			int iResponseCode = hConn.getResponseCode();
			if(iResponseCode >= 300) {
				// Get the XML Document object from the Error stream
				Document xdDocResult = dbfFactory.newDocumentBuilder().parse(hConn.getErrorStream());
				arResult.setResultDocument(xdDocResult);
				
				// Format the error message with the error description (an Error node is returned containing a Status and Description element - we don't need to
				// parse the Status element since we already have it) 
				sErrorMsg = String.format("Error %d\n%s", iResponseCode, CXMLHelper.getChildNodeValue(xdDocResult.getDocumentElement(), "Description"));
			} else { // No error...
				// Get the resulting XML Document from the REST API
				arResult.setResultDocument(dbfFactory.newDocumentBuilder().parse(hConn.getInputStream()));
			} // End f(iResponseCode >= 300)
		}
		catch (MalformedURLException e) { sErrorMsg = e.getMessage(); } 
		catch (IOException e) { sErrorMsg = e.getMessage(); } 
		catch (Exception e) { sErrorMsg = e.getMessage(); }		
		
		
		// If we have an error message then...
		if(!sErrorMsg.isEmpty()) { 
			JOptionPane.showMessageDialog(null, sErrorMsg, "Error", JOptionPane.ERROR_MESSAGE);
			arResult.setDisplayedError(true);
		} // End if(!sErrorMsg.isEmpty())
		
		
		// Return the response that was received from the REST API
		return arResult;
	}
}

