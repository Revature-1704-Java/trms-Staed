package com.staed.beans;

import java.util.ArrayList;
import java.util.List;

import com.staed.stores.FieldValueWrapper;

/**
 * A BEAN containing information related to a request. It has a One-to-One 
 * relationship with requests.
 */
public class Info extends Bean {
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

	public Info(String description, String location, String justification) {
		this(0, description, location, justification);
	}
	
	@Override
	public List<FieldValueWrapper> toFieldValueWrappers() {
    	List<FieldValueWrapper> list = new ArrayList<>();
    	list.add(new FieldValueWrapper(names.requestIdentifier, requestId));
    	list.add(new FieldValueWrapper(names.infoDesc, description));
    	list.add(new FieldValueWrapper(names.location, location));
    	list.add(new FieldValueWrapper(names.justification, justification));
    	return list;
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
