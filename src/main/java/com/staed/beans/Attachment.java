package com.staed.beans;

import java.util.ArrayList;
import java.util.List;
import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

/**
 * A BEAN containing information about files associated with a Request. It has
 * a Many-to-One relationship with Requests. They contain the description of
 * the file as well as it's filename and optionally, an integer representing
 * which stage of the approval process it corresponds to.
 */
public class Attachment implements Bean {
	private String filename;
	private int requestId;
	private int approvedAtState;
	private String description;
	
	public Attachment(String filename, int requestId, int approvedAtState, String description) {
		this.filename = filename;
		this.requestId = requestId;
		this.approvedAtState = approvedAtState;
		this.description = description;
	}
	
	public Attachment(String filename, int requestId, String description) {
		this.filename = filename;
		this.requestId = requestId;
		this.approvedAtState = 0;
		this.description = description;
	}
	
	@Override
	public List<FieldValueWrapper> toFieldValueWrappers() {
    	List<FieldValueWrapper> list = new ArrayList<>();
    	list.add(new FieldValueWrapper(ColumnNames.ATTACHMENTIDENTIFIER, filename));
    	list.add(new FieldValueWrapper(ColumnNames.REQUESTIDENTIFIER, requestId));
    	list.add(new FieldValueWrapper(ColumnNames.APPROVEDATSTATE, approvedAtState));
    	list.add(new FieldValueWrapper(ColumnNames.ATTACHMENTDESC, description));
    	return list;
    }

	@Override
	public String toString() {
		return "Attachment [filename=" + filename + ", requestId=" + requestId + ", approvedAtState=" + approvedAtState
				+ ", description=" + description + "]";
	}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getApprovedAtState() {
		return approvedAtState;
	}
	public void setApprovedAtState(int approvedAtState) {
		this.approvedAtState = approvedAtState;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
