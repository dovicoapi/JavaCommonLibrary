package com.dovico.commonlibrary.data;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.dovico.commonlibrary.APIRequestResult;
import com.dovico.commonlibrary.CRESTAPIHelper;
import com.dovico.commonlibrary.CXMLHelper;


//NOTE:	There are more Time Entry properties available than are currently defined in this class. The current state of this class is simply a starting point
//		and will be added upon.
//
//		For a list of all Time Entry properties see the following: http://apideveloper.dovico.com/TimeEntries


public class CTimeEntry {
	// Member variables
	protected String m_sTimeEntryID = ""; // Could be a Guid or a Long. I don't need the actual value right now so I left it as a string
	protected Long m_lSheetID = Constants.NONE_ID;
	protected String m_sSheetStatus = "";
	protected String m_sSheetRejectedReason = "";
	protected Long m_lClientID = Constants.NONE_ID;
	protected String m_sClientName = "";
	protected Long m_lProjectID = Constants.NONE_ID;
	protected String m_sProjectName = "";
	protected Long m_lTaskID = Constants.NONE_ID;
	protected String m_sTaskName = "";
	protected Long m_lEmployeeID = Constants.NONE_ID;
	protected String m_sEmployeeName = "";
	protected Date m_dtDate = null;
	protected String m_sStartTime = ""; //HHMM format
	protected String m_sStopTime = ""; //HHMM format
	protected double m_dTotalHours = 0.0;
	protected String m_sDescription = "";


	// Overloaded constructor
	// NOTE: Specify Start/Stop time in HHMM format
	public CTimeEntry(String sTimeEntryID, Long lSheetID, String sSheetStatus, String sSheetRejectedReason, Long lClientID, String sClientName, Long lProjectID,
			String sProjectName, Long lTaskID, String sTaskName, Long lEmployeeID, String sEmployeeName, Date dtDate, String sStartTime, String sStopTime,
			double dTotalHours, String sDescription) {
		m_sTimeEntryID = sTimeEntryID;
		m_lSheetID = lSheetID;
		m_sSheetStatus = sSheetStatus;
		m_sSheetRejectedReason = sSheetRejectedReason;
		m_lClientID = lClientID;
		m_sClientName = sClientName;
		m_lProjectID = lProjectID;
		m_sProjectName = sProjectName;		
		m_lTaskID = lTaskID;
		m_sTaskName = sTaskName;
		m_lEmployeeID = lEmployeeID;
		m_sEmployeeName = sEmployeeName;		
		m_dtDate = dtDate;
		m_sStartTime = sStartTime; //HHMM format
		m_sStopTime = sStopTime; //HHMM format
		m_dTotalHours = dTotalHours;
		m_sDescription = sDescription;
	}
	
	
	// Getters for the member variables
	public String getTimeEntryID() { return m_sTimeEntryID; }
	public Long getSheetID() { return m_lSheetID; }
	public String getSheetStatus() { return m_sSheetStatus; }
	public String getSheetRejectedReason() { return m_sSheetRejectedReason; }
	public Long getClientID() { return m_lClientID; }
	public String getClientName() { return m_sClientName; }
	public Long getProjectID() { return m_lProjectID; }
	public String getProjectName() { return m_sProjectName; }		
	public Long getTaskID() { return m_lTaskID; }
	public String getTaskName() { return m_sTaskName; }
	public Long getEmployeeID() { return m_lEmployeeID; }
	public String getEmployeeName() { return m_sEmployeeName; }		
	public Date getDate() { return m_dtDate; }
	public String getStartTime() { return m_sStartTime; }
	public String getStopTime() { return m_sStopTime; }
	public double getTotalHours() { return m_dTotalHours; }
	public String getDescription() { return m_sDescription; }
	
	//fix_me...still need setters for the member variables

	
	
	
	//=====================================================================
	// STATIC METHODS: For use when interacting with the DOVICO Hosted API
	//---------------------------------------------------------------------

	//-------------------------------------------------
	// ALL TIME (no filters)
	//-------------------------------------------------
	// Returns the first page of data for all time entries regardless of date range (this could be a lot so you will likely need to page through the data - it is 
	// recommended to use the date range version of this function or to filter for entries for a specific Employee, Project, Sheet, or Status which can also have a 
	// date range applied). 
	//	If there is a next page, sReturn_NextPageUri will contain a URI that you can use to call getListForPrevOrNextPage
	//	If there is no next page, sReturn_NextPageUri will hold "N/A"
	//
	//	NOTE: If there was an error the return will be null
	public static ArrayList<CTimeEntry> getList(APIRequestResult aRequestResult) {
		// If the Request URI has not been set then set it to return the first page of time entries (if it's already set we may have been called to get a next/previous
		// page)
		if(aRequestResult.getRequestURI().isEmpty()) { aRequestResult.setRequestURI("TimeEntries/", ""); }		
		return processRequest(aRequestResult);
	}
	//-------------------------------------------------
	// ALL TIME (filtered by date range)
	//-------------------------------------------------
	public static ArrayList<CTimeEntry> getList(Date dtDateRangeStart, Date dtDateRangeEnd, APIRequestResult aRequestResult) {
		// If the Request URI has not been set then set it to return the first page of time entries (if it's already set we may have been called to get a next/previous
		// page)
		if(aRequestResult.getRequestURI().isEmpty()) { aRequestResult.setRequestURI("TimeEntries/", buildDateRangeQueryString(dtDateRangeStart, dtDateRangeEnd)); }
		return processRequest(aRequestResult);
	}
	
	
	//-------------------------------------------------
	// ALL TIME by EMPLOYEE
	//-------------------------------------------------
	public static ArrayList<CTimeEntry> getListForEmployee(Long lEmployeeID, APIRequestResult aRequestResult) {
		// If the Request URI has not been set then set it to return the first page of time entries (if it's already set we may have been called to get a next/previous
		// page)
		if(aRequestResult.getRequestURI().isEmpty()) { aRequestResult.setRequestURI(("TimeEntries/Employee/" + lEmployeeID.toString() + "/"), ""); }	
		return processRequest(aRequestResult);
	}	
	//-------------------------------------------------
	// ALL TIME by EMPLOYEE (filtered by date range)
	//-------------------------------------------------
	public static ArrayList<CTimeEntry> getListForEmployee(Long lEmployeeID, Date dtDateRangeStart, Date dtDateRangeEnd, APIRequestResult aRequestResult) 
	{
		// If the Request URI has not been set then set it to return the first page of time entries (if it's already set we may have been called to get a next/previous
		// page)
		if(aRequestResult.getRequestURI().isEmpty()) { aRequestResult.setRequestURI(("TimeEntries/Employee/" + lEmployeeID.toString() + "/"), buildDateRangeQueryString(dtDateRangeStart, dtDateRangeEnd)); }
		return processRequest(aRequestResult);
	}
	
	
	//FUTURE methods:
	// GETs for Project, Status, and Sheet filters - including date range versions
		
		
	
	//FUTURE methods: 
	//	public static CTimeEntry Insert(CTimeEntry tTimeEntry)
	//	public static ArrayList<CTimeEntry> Insert(ArrayList<CTimeEntry> lstTimeEntries)

	//	public static CTimeEntry Update(CTimeEntry tTimeEntry)
	//	public static ArrayList<CTimeEntry> Update(ArrayList<CTimeEntry> lstTimeEntries)
	
	
	
	// Handles the work of making a request and pulling Time Entries from the result of a request
	//
	//	NOTE: If there was an error the return will be null
	private static ArrayList<CTimeEntry> processRequest(APIRequestResult aRequestResult) {
		// Make sure the Prev/Next Page URI, if there was an error, result XML document, etc are reset in the event the user is re-using an object that has already 
		// been used for a different call (don't want a previous call's results giving the caller false information)
		aRequestResult.resetResultData();
				
		
		// Pass the request on to the REST API. If there was an error then exit now
		CRESTAPIHelper.makeAPIRequest(aRequestResult);
		if(aRequestResult.getHadRequestError()) { return null; }
		
		
		// Will hold the list of Time Entries that will be returned to the calling function
		ArrayList<CTimeEntry> lstTimeEntries = new ArrayList<CTimeEntry>();
		
		// Grab the root element and get the Previous/Next Page URIs from it (when requesting a specific time entry there will be no paging information returned since a 
		// single record is all that is ever returned. If that's the case we want our Previous/Next Page URIs to hold 'N/A' rather than "" which is why we pass in 
		// the URI_NOT_AVAILABLE constant)
		Element xeDocElement = aRequestResult.getResultDocument().getDocumentElement();
		aRequestResult.setResultPrevPageURI(CXMLHelper.getChildNodeValue(xeDocElement, Constants.PREV_PAGE_URI, Constants.URI_NOT_AVAILABLE));
		aRequestResult.setResultNextPageURI(CXMLHelper.getChildNodeValue(xeDocElement, Constants.NEXT_PAGE_URI, Constants.URI_NOT_AVAILABLE));
		
		Format fFormatter = new SimpleDateFormat(Constants.XML_DATE_FORMAT);
		Element xeTimeEntry = null, xeSheet = null, xeClient = null, xeProject = null, xeTask = null, xeEmployee = null;
		Date dtDate = null;
		
		// Grab the list of Time Entry nodes and loop through the elements... 
		NodeList xnlTimeEntries = xeDocElement.getElementsByTagName("TimeEntry");
		int iCount = xnlTimeEntries.getLength();
		for(int iIndex = 0; iIndex < iCount; iIndex++) {
			// Grab the current element and the required sub-elements
			xeTimeEntry = (Element)xnlTimeEntries.item(iIndex);
			xeSheet = (Element)xeTimeEntry.getElementsByTagName("Sheet").item(0);
			xeClient = (Element)xeTimeEntry.getElementsByTagName("Client").item(0);
			xeProject = (Element)xeTimeEntry.getElementsByTagName("Project").item(0);
			xeTask = (Element)xeTimeEntry.getElementsByTagName("Task").item(0);
			xeEmployee = (Element)xeTimeEntry.getElementsByTagName("Employee").item(0);
			
			// Convert the date string into a Date object			
			try { dtDate = (Date)fFormatter.parseObject(CXMLHelper.getChildNodeValue(xeTimeEntry, "Date")); } 
			catch (ParseException e) { e.printStackTrace(); } //fix_me...set dtDate to some default value instead? display an alert?
						
			// Add the current item to our list
			lstTimeEntries.add(new CTimeEntry(
					CXMLHelper.getChildNodeValue(xeTimeEntry, "ID"), 
					Long.valueOf(CXMLHelper.getChildNodeValue(xeSheet, "ID")), 
					CXMLHelper.getChildNodeValue(xeSheet, "Status"), 
					CXMLHelper.getChildNodeValue(xeSheet, "RejectedReason"), 
					Long.valueOf(CXMLHelper.getChildNodeValue(xeClient, "ID")), 
					CXMLHelper.getChildNodeValue(xeClient, "Name"), 
					Long.valueOf(CXMLHelper.getChildNodeValue(xeProject, "ID")), 
					CXMLHelper.getChildNodeValue(xeProject, "Name"), 
					Long.valueOf(CXMLHelper.getChildNodeValue(xeTask, "ID")), 
					CXMLHelper.getChildNodeValue(xeTask, "Name"), 
					Long.valueOf(CXMLHelper.getChildNodeValue(xeEmployee, "ID")), 
					CXMLHelper.getChildNodeValue(xeEmployee, "Name"), 
					dtDate, 
					CXMLHelper.getChildNodeValue(xeTimeEntry, "StartTime"), 
					CXMLHelper.getChildNodeValue(xeTimeEntry, "StopTime"), 
					Double.valueOf(CXMLHelper.getChildNodeValue(xeTimeEntry, "TotalHours")), 
					CXMLHelper.getChildNodeValue(xeTimeEntry, "Description")
					));
		} // End of the for(int iIndex = 0; iIndex < iCount; iIndex++) loop.
		
		
		// Return the list of Employees to the caller
		return lstTimeEntries;
	}
	
	
	// Helper to return a Date Range query string
	private static String buildDateRangeQueryString(Date dtDateRangeStart, Date dtDateRangeEnd){
		// Create a Date formatter object that will turn a date into the XML Date Format string expected by the API
		SimpleDateFormat fFormatter = new SimpleDateFormat(Constants.XML_DATE_FORMAT);
		
		// Return the date range query string with an encoded space between both dates
		return ("daterange=" + fFormatter.format(dtDateRangeStart) + "%20" + fFormatter.format(dtDateRangeEnd));
	}
	


	//-------------------------------------------------
	// Submit all of an Employee's time, that is within the specified date range, that can be submitted (non-submitted time and rejected time are the only two statuses
	// that can be submitted)
	//-------------------------------------------------	
	public static boolean submitTime(Long lEmployeeID, Date dtDateRangeStart, Date dtDateRangeEnd, APIRequestResult aRequestResult){
		// Set the URI needed to submit an employee's time that falls within the specified date range
		aRequestResult.setRequestURI(("TimeEntries/Employee/" + lEmployeeID.toString() + "/Submit/"), buildDateRangeQueryString(dtDateRangeStart, dtDateRangeEnd));
				
		// Indicate that we're doing a POST (by default it's set to GET) and specify the POST data
		aRequestResult.setRequestHttpMethod("POST");
		aRequestResult.setRequestPostPutXmlData("<SubmitTime></SubmitTime>");
		
		// Pass the request on to the REST API and then tell the caller if this call was successful or not
		CRESTAPIHelper.makeAPIRequest(aRequestResult);
		return (aRequestResult.getHadRequestError() ? false : true);
	}
	
	//---------------------------------------------------------------------
	// End of the Static Methods use when interacting with the DOVICO Hosted API
	//=====================================================================	
}
