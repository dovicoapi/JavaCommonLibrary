package com.dovico.commonlibrary;

import org.w3c.dom.Document;

import com.dovico.commonlibrary.data.Constants;

// Helper class for the requests/return values with the CRESTAPIHelper.makeAPIRequest method 
public class APIRequestResult {
	// Member variables		
	private String m_sConsumerSecret = "";
	private String m_sDataAccessToken = "";
	private String m_sApiVersionTargeted = "";
	private String m_sRequestURI = "";
	private String m_sRequestHttpMethod = "GET"; // Most requests will be GETs so this is the default so that the caller doesn't have to explicitly set it every time  
	private String m_sRequestPostPutXmlData = "";		
	private boolean m_bShowErrorsToUser = true; // By default we show REST API errors to the user
	
	private boolean m_bHadRequestError = false;
	private String m_sRequestErrorMessage = "";	
	private Document m_xdRequestResult = null;
	private String m_sResultPrevPageURI = Constants.URI_NOT_AVAILABLE;
	private String m_sResultNextPageURI = Constants.URI_NOT_AVAILABLE;
		
	// Overloaded constructor
	public APIRequestResult(String sConsumerSecret, String sDataAccessToken, String sApiVersionTargeted, boolean bShowErrorsToUser)
	{
		m_sConsumerSecret = sConsumerSecret;
		m_sDataAccessToken = sDataAccessToken;
		m_sApiVersionTargeted = sApiVersionTargeted;
		m_bShowErrorsToUser = bShowErrorsToUser;
	}
	
	
	// Helper to reset all result data
	public void resetResultData(){ 
		m_bHadRequestError = false;
		m_sRequestErrorMessage = "";		
		m_xdRequestResult = null;
		m_sResultPrevPageURI = Constants.URI_NOT_AVAILABLE;
		m_sResultNextPageURI = Constants.URI_NOT_AVAILABLE;
	}
		
	
	
	//------------------
	// Getters
	//------------------
	public String getConsumerSecret() { return m_sConsumerSecret; }
	public String getDataAccessToken() { return m_sDataAccessToken; }
	public String getApiVersionTargeted() { return m_sApiVersionTargeted; }
	public String getRequestURI() { return m_sRequestURI; }
	public String getRequestHttpMethod() { return m_sRequestHttpMethod; }
	
	public boolean getHaveRequestPostPutXmlData() { return (m_sRequestPostPutXmlData.isEmpty() ? false : true); } 
	public String getRequestPostPutXmlData() { return m_sRequestPostPutXmlData; }
	
	// Returns if we are to show errors to the user, if there was an error and what the error message was
	public boolean getShowErrorsToUser() { return m_bShowErrorsToUser; }
	public boolean getHadRequestError() { return m_bHadRequestError; }
	public boolean getDisplayedError() { return getHadRequestError(); } // Legacy. For backwards compatibility with existing code. Use getHadRequestError instead!
	public String getRequestErrorMessage() { return m_sRequestErrorMessage; }
	
	// Returns the XML Document object from the result of our call to the REST API (NOTE: will be 'null' if getHadRequestError is true!)
	public Document getResultDocument() { return m_xdRequestResult; }	
	
	public String getResultPrevPageURI() { return m_sResultPrevPageURI; }
	public String getResultNextPageURI() { return m_sResultNextPageURI; }
	
	
	//------------------
	// Setters
	//------------------
	// Used in the event the caller wants to target a version of the API that is not the most current
	public void setApiVersionTargeted(String sApiVersionTargeted) { m_sApiVersionTargeted = sApiVersionTargeted; }
	
	// Sets the URI that will be called
	public void setRequestURI(String sURIPart, String sOptionalQueryStrings) { m_sRequestURI = CRESTAPIHelper.buildURI(sURIPart, sOptionalQueryStrings, m_sApiVersionTargeted); }
	public void setRequestURI(String sURI) { m_sRequestURI = sURI; }
	
	public void setRequestHttpMethod(String sRequestHttpMethod) { m_sRequestHttpMethod = sRequestHttpMethod; }
	public void setRequestPostPutXmlData(String sRequestPostPutXmlData) { m_sRequestPostPutXmlData = sRequestPostPutXmlData; }
	
	// Helper to set the error message and a flag indicating that there was an error
	public void setRequestErrorMessage(String sRequestErrorMessage) {
		m_sRequestErrorMessage = sRequestErrorMessage;
		m_bHadRequestError = true;
	}
	
	// The XML Document object from the results of our call to the REST API
	public void setResultDocument(Document xdRequestResult) { m_xdRequestResult = xdRequestResult; }
	
	public void setResultPrevPageURI(String sResultPrevPageURI) { m_sResultPrevPageURI = sResultPrevPageURI; }
	public void setResultNextPageURI(String sResultNextPageURI) { m_sResultNextPageURI = sResultNextPageURI; }
}
