package com.staed.stores;

/**
 * A container of current column names. If you change the name of a table's
 * column without changing it's functionality, just change the name once here
 */
public class ColumnNames {
	/* Request Table */
	public final String requestIdentifier = "REQUESTID";
	// 					emailIdentifer
	public final String eventTypeId = "EVENTTYPEID";
	public final String formatId = "GRADEFORMATID";
	public final String state = "STATE";
	public final String cost = "COST";
	public final String eventDate = "EVENTDATE";
	public final String workMissed = "WORKTIMEMISSED";
	public final String lastReviewed = "LASTREVIEWED";
	
	/* EventType Table */
	//					eventTypeId
	//					eventTypeName
	//					compensation

	/* GradingFormat Table */
	//					gradingFormatId
	//					type
	//					cutoff

	/* Note Table */
	public final String noteIdentifier = "NOTEID";
	// 					requestIdentifier
	public final String managerEmail = "MANAGEREMAIL";
	public final String timeActedOn = "TIMEACTEDON";
	public final String newAmount = "NEWAMOUNT";
	public final String noteReason = "REASON";
	
	/* Info Table */
	//					requestIdentifier
	public final String infoDesc = "DESCRIPTION";
	public final String location = "LOCATION";
	public final String justification = "JUSTIFICATION";
	
	/* Employee Table */
	public final String employeeIdentifier = "EMAIL";
	public final String pass = "PASSWORD";
	public final String name = "NAME";
	public final String employeeTypeId = "EMPLOYEETYPEID";
	public final String supervisor = "SUPER";
	public final String deptHead = "HEAD";
	public final String benCo = "BENCO";

	/* EmployeeType Table */
	//					employeeTypeId
	//					name
	//					power
	
	/* Attachment Table */
	public final String attachmentIdentifier = "FILENAME";
	//					requestIdentifier
	public final String approvedAtState = "APPROVEDATSTATE";
	public final String attachmentDesc = "DESCRIPTION";
}
