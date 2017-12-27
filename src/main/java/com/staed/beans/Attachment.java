package com.staed.beans;

public class Attachment {
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