package com.dovico.commonlibrary.data;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.dovico.commonlibrary.APIRequestResult;
import com.dovico.commonlibrary.CRESTAPIHelper;
import com.dovico.commonlibrary.CXMLHelper;

// NOTE:	There are more Employee properties available than are currently defined in this class. The current state of this class is simply a starting point
// 			and will be added upon.
//
//			For a list of all Employee properties see the following: http://apideveloper.dovico.com/Employees

public class CEmployee {
	// Member variables
	private Long m_lID = 0L;
	private String m_sLastName = "";
	private String m_sFirstName = "";
	
	// Default constructor
	public CEmployee(){}
	
	// Overloaded constructor
	public CEmployee(Long lID, String sLastName, String sFirstName) {
		m_lID = lID;
		m_sLastName = sLastName;
		m_sFirstName = sFirstName;
	}	
	
	// Getters for the member variables
	public Long getID() { return m_lID; }
	public String getLastName() { return m_sLastName; }
	public String getFirstName() { return m_sFirstName; }
	
	// Setters for the member variables
	public void setID(Long lID) { m_lID = lID; }
	public void setLastName(String sLastName) { m_sLastName = sLastName; }
	public void setFirstName(String sFirstName) { m_sFirstName = sFirstName; }
	
	
	
	// Helpful function so that you can use this object in things like a list (this will be the text displayed)
	@Override
	public String toString() { return (m_sLastName + ", " + m_sFirstName); }
	
	
	
	
	//=====================================================================
	// STATIC METHODS: For use when interacting with the DOVICO Hosted API
	//---------------------------------------------------------------------
	
	// A request for a specific employee by ID (If there was an error the return value will be null)
	public static CEmployee getInfo(Long lID, APIRequestResult aRequestResult) {
		// Set the URI for the Employee/{ID}/ request. Process the request and if the returned list is not null (no errors) then return the first item in the list
		// (there should only ever be the one item) 
		aRequestResult.setRequestURI(("Employees/" + lID.toString() + "/"), "");
		ArrayList<CEmployee> lstEmployees = processRequest(aRequestResult); 
		if(lstEmployees != null) { return lstEmployees.get(0); }
		
		// An error happened so just return null.
		return null;	
	}
	
	
	// A request for the logged in Employee's info (this information is quite limited but may be the only employee information you can obtain if the logged in user
	// has no employee access permissions. this will return the employee's ID, Last Name, and First Name - with the ID, you can call getInfo and try to get the rest
	// of the info if desired)
	//
	// If there was an error the return value will be null
	public static CEmployee getInfoMe(APIRequestResult aRequestResult) {
		// Set the URI for the Employee/Me/ request. Process the request and if the returned list is not null (no errors) then return the first item in the list (there
		// should only ever be the one item)
		aRequestResult.setRequestURI("Employees/Me/", "");
		ArrayList<CEmployee> lstEmployees = processRequest(aRequestResult); 
		if(lstEmployees != null) { return lstEmployees.get(0); }
		
		// An error happened so just return null.
		return null;
	}
	
	// Returns the first page of data if the Request URI is empty (not set). Otherwise, returns the page of employees indicated by the request URI that has been set. 
	//
	// If there was an error the return value will be null
	public static ArrayList<CEmployee> getList(APIRequestResult aRequestResult) {
		// If the Request URI has not been set then set it to return the first page of employees (if it's already set we may have been called to get a next/previous
		// page of employees)
		if(aRequestResult.getRequestURI().isEmpty()) { aRequestResult.setRequestURI("Employees/", ""); } 
		return processRequest(aRequestResult);
	}	
	
		
	//FUTURE methods: 
	//	public static CEmployee Insert(CEmployee eEmployee)
	//	public static ArrayList<CEmployee> Insert(ArrayList<CEmployee> lstEmployees)

	//	public static CEmployee Update(CEmployee eEmployee)
	//	public static ArrayList<CEmployee> Update(ArrayList<CEmployee> lstEmployees)
	
	
	
	// Handles the work of making a request and pulling Employee(s) from the result of a request (if there was an error the return value will be null)
	private static ArrayList<CEmployee> processRequest(APIRequestResult aRequestResult) {
		// Make sure the Prev/Next Page URI, if there was an error, result XML document, etc are reset in the event the user is re-using an object that has already 
		// been used for a different call (don't want a previous call's results giving the caller false information)
		aRequestResult.resetResultData();
		
		
		// Pass the request on to the REST API. If there was an error then exit now
		CRESTAPIHelper.makeAPIRequest(aRequestResult);
		if(aRequestResult.getHadRequestError()) { return null; }
					
		
		// Will hold the list of employees that will be returned to the calling function
		ArrayList<CEmployee> lstEmployees = new ArrayList<CEmployee>();
		
		// Grab the root element and get the Previous/Next Page URIs from it (when requesting a specific employee there will be no paging information returned since a 
		// single record is all that is ever returned. If that's the case we want our Previous/Next Page URIs to hold 'N/A' rather than "" which is why we pass in 
		// the URI_NOT_AVAILABLE constant)
		Element xeDocElement = aRequestResult.getResultDocument().getDocumentElement();
		aRequestResult.setResultPrevPageURI(CXMLHelper.getChildNodeValue(xeDocElement, Constants.PREV_PAGE_URI, Constants.URI_NOT_AVAILABLE));
		aRequestResult.setResultNextPageURI(CXMLHelper.getChildNodeValue(xeDocElement, Constants.NEXT_PAGE_URI, Constants.URI_NOT_AVAILABLE));
		
		// Grab the list of Employee nodes
		NodeList xnlEmployees = xeDocElement.getElementsByTagName("Employee");
		Element xeEmployee = null;
				
		// Loop through the elements...
		int iCount = xnlEmployees.getLength();
		for(int iIndex = 0; iIndex < iCount; iIndex++) {
			// Grab the current element
			xeEmployee = (Element)xnlEmployees.item(iIndex);
						
			// Add the current item to our list
			lstEmployees.add(new CEmployee(
					Long.valueOf(CXMLHelper.getChildNodeValue(xeEmployee, "ID")), 
					CXMLHelper.getChildNodeValue(xeEmployee, "LastName"),
					CXMLHelper.getChildNodeValue(xeEmployee, "FirstName")					
					// NOTE: If this is an Employee/Me/ request, the rest of the fields may not be available
					));
		} // End of the for(int iIndex = 0; iIndex < iCount; iIndex++) loop.
		
		
		// Return the list of Employees to the caller
		return lstEmployees;
	}	
	
	//---------------------------------------------------------------------
	// End of the Static Methods use when interacting with the DOVICO Hosted API
	//=====================================================================
}
