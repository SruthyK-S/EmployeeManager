package com.litmus7.employeemanager.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



public class ValidationUtil 
{

	String message = "valid";
	
	public String isValidID(String newID)
	{
		try {
	        int ID = Integer.parseInt(newID);
	        if (ID <= 0)
	        	return "Invalid ID! Please enter a positive integer greater than zero.";
	        
	    } catch (NumberFormatException e) {
	        return "Invalid ID! Please enter a valid numeric integer.";
	    }
		return "valid";
	}
	
	public String isValidName(String name)
	{
        if( name.isEmpty())
        	return "Name cannot be empty";
        if (!name.matches("[a-zA-Z\\-' ]+"))
        	return "Name should only contain letters!";
       
		return "valid";
	}
	
	public String isValidNumber(String mobileNumber) {
	    if (mobileNumber == null || mobileNumber.isEmpty())
	        return "Mobile Number cannot be empty";

	    if (!mobileNumber.matches("\\d{10}"))
	        return "Invalid mobile number. Must be 10 digits.";
	    	// does not define if it is format error

	    return "valid";
	}
	
	public String isValidEmail(String email) {
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}"))
			return "Invalid email address.";
	    return "valid";
	}
	
	public String isValidJoiningDate(String dateStr)
	{
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    try {
	        LocalDate date = LocalDate.parse(dateStr, formatter);
	        LocalDate establishedDate = LocalDate.of(2009, 6, 30);
	        LocalDate today = LocalDate.now();
	        if(date.isAfter(today) || date.isBefore(establishedDate))
				return "Invalid joining date.";
			
	    } catch (DateTimeParseException e) {
	        return "Invalid date format. Please use YYYY-MM-DD.";
	    }
	    return "valid";
		
	}
	
	public String isValidActiveStatus(String active) {
		active = active.toLowerCase();
		if(!active.equals("true") && !active.equals("false"))
			return "Enter valid active status(true/false)";
		return "valid";
	}
	
}
