package com.staed.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.staed.stores.ColumnNames;
import com.staed.stores.FieldValueWrapper;

/**
 * A BEAN containing information tacked onto a Request. It has a Many-to-One
 * relationship with Requests. They contain a reference to the request
 * associated with it, a string with the contents of the note, the date it
 * was added, and optionally, the new cost for the Request's reimbursement
 * value. 
 */
public class Note implements Bean {
	private int id;
	private int requestId;
	private String managerEmail;
	private LocalDate timeActedOn;
	private float newAmount;
	private String reason;
	
	public Note(int id, int requestId, String managerEmail, LocalDate timeActedOn, float newAmount, String reason) {
		this.id = id;
		this.requestId = requestId;
		this.managerEmail = managerEmail;
		this.timeActedOn = timeActedOn;
		this.newAmount = newAmount;
		this.reason = reason;
	}

	public Note(int id, int requestId, String managerEmail, LocalDate timeActedOn, String reason) {
		this.id = id;
		this.requestId = requestId;
		this.managerEmail = managerEmail;
		this.timeActedOn = timeActedOn;
		this.newAmount = -1;
		this.reason = reason;
	}
	
	@Override
	public List<FieldValueWrapper> toFieldValueWrappers() {
    	List<FieldValueWrapper> list = new ArrayList<>();
    	list.add(new FieldValueWrapper(ColumnNames.NOTEIDENTIFIER, id));
    	list.add(new FieldValueWrapper(ColumnNames.REQUESTIDENTIFIER, requestId));
    	list.add(new FieldValueWrapper(ColumnNames.MANAGEREMAIL, managerEmail));
    	list.add(new FieldValueWrapper(ColumnNames.TIMEACTEDON, timeActedOn));
    	list.add(new FieldValueWrapper(ColumnNames.NEWAMOUNT, newAmount));
    	list.add(new FieldValueWrapper(ColumnNames.NOTEREASON, reason));
    	return list;
    }

	@Override
	public String toString() {
		return "Note [id=" + id + ", requestId=" + requestId + ", managerEmail=" + managerEmail + ", timeActedOn="
				+ timeActedOn + ", newAmount=" + newAmount + ", reason=" + reason + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public LocalDate getTimeActedOn() {
		return timeActedOn;
	}

	public void setTimeActedOn(LocalDate timeActedOn) {
		this.timeActedOn = timeActedOn;
	}

	public float getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(float newAmount) {
		this.newAmount = newAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
