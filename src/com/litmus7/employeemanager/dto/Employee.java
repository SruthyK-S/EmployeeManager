package com.litmus7.employeemanager.dto;


public class Employee {
	
	private String ID;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String email;
	private String joiningDate;
	private String active;

	public Employee(String ID, String firstName, String lastName, String mobileNumber, 
					String email, String joiningDate, String active)
	{
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.joiningDate = joiningDate;
		this.active = active;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getActiveStatus() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	

}
