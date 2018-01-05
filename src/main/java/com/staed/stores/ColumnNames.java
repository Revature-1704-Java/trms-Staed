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
	public static final String COST = "REQUESTCOST";
	public static final String EVENTDATE = "EVENTDATE";
	public static final String WORKMISSED = "WORKMISSED";
	public static final String LASTREVIEWED = "LASTREVIEWED";
	
	/* EventType Table */
	//					EVENTTYPEID
	public static final String EVENTTYPENAME = "EVENTTYPENAME";
	public static final String COMPENSATION = "COMPENSATION";

	/* GradingFormat Table */
	//					FORMATID
	public static final String FORMATTYPE = "FORMATTYPE";
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
	public static final String INFODESC = "INFODESC";
	public static final String LOCATION = "INFOLOCATION";
	public static final String JUSTIFICATION = "JUSTIFICATION";
	
	/* Employee Table */
	public static final String EMPLOYEEIDENTIFIER = "EMAIL";
	public static final String EMPLOYEEPASSWORD = "EMPPASS";
	public static final String NAME = "EMPLOYEENAME";
	public static final String EMPLOYEETYPEID = "EMPLOYEETYPEID";
	public static final String SUPERVISOR = "SUPER";
	public static final String DEPTHEAD = "HEAD";
	public static final String BENCO = "BENCO";

	/* EmployeeType Table */
	//					EMPLOYEETYPEID
	//					EMPLOYEETYPENAME
	//					EMPLOYEETYPEPOWER
	
	/* Attachment Table */
	public static final String ATTACHMENTIDENTIFIER = "FILENAME";
	//					REQUESTIDENTIFIER
	public static final String APPROVEDATSTATE = "APPROVEDATSTATE";
	public static final String ATTACHMENTDESC = "ATTACHMENTDESC";
}
