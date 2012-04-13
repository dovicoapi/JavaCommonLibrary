package com.dovico.commonlibrary.data;

public class Constants {
	public static String PREV_PAGE_URI = "PrevPageURI";
	public static String NEXT_PAGE_URI = "NextPageURI";
	public static String URI_NOT_AVAILABLE = "N/A"; 
	
	// The REST API returns and expects dates in this format
	public static String XML_DATE_FORMAT = "yyyy-MM-dd";
	
	
	public static Long NONE_ID = 0L;
	
	// The available Prefix values for the AssignmentID 
	public static String ASSIGNMENT_TYPE_CLIENT = "C";
	public static String ASSIGNMENT_TYPE_PROJECT = "P";
	public static String ASSIGNMENT_TYPE_TASKGROUP = "G";
	public static String ASSIGNMENT_TYPE_TASK = "T";
	public static String ASSIGNMENT_TYPE_EMPLOYEE = "E";
}
