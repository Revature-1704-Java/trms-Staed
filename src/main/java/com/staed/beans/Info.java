package com.staed.beans;

/**
 * A BEAN containing information related to a request. It has a One-to-One 
 * relationship with requests.
 */
public class Info {
	private int requestId;
	private String description;
	private String location;
	private String justification;
	
	public Info(int requestId, String description, String location, String justification) {
		this.requestId = requestId;
		this.description = description;
		this.location = location;
		this.justification = justification;
	}
	
	@Override
	public String toString() {
		return "RequestInfo [requestId=" + requestId + ", description=" + description + ", location="
				+ location + ", justification=" + justification + "]";
	}
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}
}
