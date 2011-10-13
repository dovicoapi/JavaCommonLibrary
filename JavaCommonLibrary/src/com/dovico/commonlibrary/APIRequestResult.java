package com.dovico.commonlibrary;

import org.w3c.dom.Document;

// Helper class for the return value from the CRESTAPIHelper.makeAPIRequest method (I needed a way to know if there was an error in the method and also be able to
// receive the XML Document object at the same time)
public class APIRequestResult {
	private boolean m_bDisplayedError = false;
	private Document m_xdResult = null;
	
	// Flag to know if the makeAPIRequest function displayed an error to the user
	public void setDisplayedError(boolean bDisplayedError) { m_bDisplayedError = bDisplayedError; }
	public boolean getDisplayedError() { return m_bDisplayedError; }
	
	// The XML Document object from the results of our call to the REST API (will be null if getDisplayedError is true)
	public void setResultDocument(Document xdResult) { m_xdResult = xdResult; }
	public Document getResultDocument() { return m_xdResult; }	
}
