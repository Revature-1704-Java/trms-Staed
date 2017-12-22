package com.staed.beans;

import java.time.LocalDate;

public class Employee {
	private int id;
	private String password;
	
	private String firstname;
	private String lastname;
	private String email;
	private float awarded;
	private LocalDate lastAwarded;
	
	private int titleId;
	private int departmentId;
	private int superId;
	
	// Only used by internals
	public Employee(int id, String password, String firstname, String lastname,
			String email, float awarded, LocalDate lastAwarded, int titleId,
			int departmentId, int superId) {
		this.id = id;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.awarded = awarded;
		this.lastAwarded = lastAwarded;
		this.titleId = titleId;
		this.departmentId = departmentId;
		this.superId = superId;
	}

	public Employee(String password, String firstname, String lastname,
			String email, float awarded, int titleId, int departmentId,
			int superId) {
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.awarded = awarded;
		this.titleId = titleId;
		this.departmentId = departmentId;
		this.superId = superId;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "{EmployeeId=" + id + ", Password="
				+ password + ", FirstName=" + firstname + ", LastName="
				+ lastname + ", Email=" + email + ", Awarded=" + awarded
				+ ", LastAwarded=" + lastAwarded + ", TitleId=" + titleId
				+ ", DepartmentId=" + departmentId + ", SupervisorId="
				+ superId + "}";
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getAwarded() {
		return awarded;
	}

	public void setAwarded(float awarded) {
		this.awarded = awarded;
	}

	public LocalDate getLastAwarded() {
		return lastAwarded;
	}

	public void setLastAwarded(LocalDate lastAwarded) {
		this.lastAwarded = lastAwarded;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getSuperId() {
		return superId;
	}

	public void setSuperId(int superId) {
		this.superId = superId;
	}
}
