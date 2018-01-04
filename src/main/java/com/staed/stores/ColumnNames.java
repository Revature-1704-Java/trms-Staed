package com.staed.stores;

/**
 * A container of current column names. If you change the name of a table's
 * column without changing it's functionality, just change the name once here
 */
public class ColumnNames {
	private ColumnNames() {
		throw new IllegalStateException("ColumnNames class");
	}

	/* Request Table */
	public static final String REQUESTIDENTIFIER = "REQUESTID";
	// 					EMAILIDENTIFER
	public static final String EVENTTYPEID = "EVENTTYPEID";
	public static final String FORMATID = "GRADINGFORMATID";
	public static final String STATE = "STATE";
	public static final String COST = "COST";
	public static final String EVENTDATE = "EVENTDATE";
	public static final String WORKMISSED = "WORKTIMEMISSED";
	public static final String LASTREVIEWED = "LASTREVIEWED";
	
	/* EventType Table */
	//					EVENTTYPEID
	public static final String EVENTTYPENAME = "EVENTTYPENAME";
	public static final String COMPENSATION = "COMPENSATION";

	/* GradingFormat Table */
	//					FORMATID
	public static final String FORMATTYPE = "TYPE";
	public static final String CUTOFF = "CUTOFF";

	/* Note Table */
	public static final String NOTEIDENTIFIER = "NOTEID";
	// 					REQUESTIDENTIFIER
	public static final String MANAGEREMAIL = "MANAGEREMAIL";
	public static final String TIMEACTEDON = "TIMEACTEDON";
	public static final String NEWAMOUNT = "NEWAMOUNT";
	public static final String NOTEREASON = "REASON";
	
	/* Info Table */
	//					REQUESTIDENTIFIER
	public static final String INFODESC = "DESCRIPTION";
	public static final String LOCATION = "LOCATION";
	public static final String JUSTIFICATION = "JUSTIFICATION";
	
	/* Employee Table */
	public static final String EMPLOYEEIDENTIFIER = "EMAIL";
	public static final String PASS = "PASSWORD";
	public static final String NAME = "NAME";
	public static final String EMPLOYEETYPEID = "EMPLOYEETYPEID";
	public static final String SUPERVISOR = "SUPER";
	public static final String DEPTHEAD = "HEAD";
	public static final String BENCO = "BENCO";

	/* EmployeeType Table */
	//					EMPLOYEETYPEID
	//					NAME
	//					POWER
	
	/* Attachment Table */
	public static final String ATTACHMENTIDENTIFIER = "FILENAME";
	//					REQUESTIDENTIFIER
	public static final String APPROVEDATSTATE = "APPROVEDATSTATE";
	public static final String ATTACHMENTDESC = "DESCRIPTION";
}
