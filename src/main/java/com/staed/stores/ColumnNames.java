package com.staed.stores;

/**
 * A container of current column names. If you change the name of a table's
 * column without changing it's functionality, just change the name once here
 */
public class ColumnNames {
	// Request
	public final String requestIdentifier = "REQUESTID";
	public final String eventTypeId = "EVENTTYPEID";
	public final String formatId = "GRADEFORMATID";
	public final String state = "STATE";
	public final String cost = "COST";
	public final String eventDate = "EVENTDATE";
	public final String workMissed = "WORKTIMEMISSED";
	public final String lastReviewed = "LASTREVIEWED";
	
	// Note
	public final String noteIdentifier = "NOTEID";
	public final String managerEmail = "MANAGEREMAIL";
	public final String timeActedOn = "TIMEACTEDON";
	public final String newAmount = "NEWAMOUNT";
	public final String noteReason = "REASON";
	
	// Info
	public final String infoDesc = "DESCRIPTION";
	public final String location = "LOCATION";
	public final String justification = "JUSTIFICATION";
	
	// Employee
	public final String employeeIdentifier = "EMAIL";
	public final String pass = "PASSWORD";
	public final String name = "NAME";
	public final String employeeTypeId = "EMPLOYEETYPEID";
	public final String supervisor = "SUPER";
	public final String deptHead = "HEAD";
	public final String benCo = "BENCO";
	
	// Attachment
	public final String attachmentIdentifier = "FILENAME";
	public final String approvedAtState = "APPROVEDATSTATE";
	public final String attachmentDesc = "DESCRIPTION";
}
