package com.litmus7.employeemanager.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.litmus7.employeemanager.exceptions.ValidationException;



public class ValidationUtil 
{

	
	public static boolean isValidID(String newID) throws ValidationException
	{
		try {
	        int ID = Integer.parseInt(newID);
	        if (ID <= 0)
	        	throw new ValidationException("Invalid ID! Please enter a positive integer greater than zero.");
	        
	    } catch (NumberFormatException e) {
	    	throw new ValidationException ("Invalid ID! Please enter a valid numeric integer.");
	    }
		return true;
	}
	
	public static boolean isValidName(String name) throws ValidationException
	{
        if( name.isEmpty())
        	throw new ValidationException("Name cannot be empty");
        if (!name.matches("[a-zA-Z\\-' ]+"))
        	throw new ValidationException("Name should only contain letters!");
       
		return true;
	}
	
	public static boolean isValidNumber(String mobileNumber) throws ValidationException
	{
	    if (mobileNumber == null || mobileNumber.isEmpty())
	    	throw new ValidationException("Mobile Number cannot be empty");

	    if (!mobileNumber.matches("\\d{10}"))
	    	throw new ValidationException("Invalid mobile number. Must be 10 digits.");

	    return true;
	}
	
	public static boolean isValidEmail(String email) throws ValidationException
	{
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}"))
			throw new ValidationException("Invalid email address.");
	    return true;
	}
	
	public static boolean isValidJoiningDate(String dateStr) throws ValidationException
	{
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    try {
	        LocalDate date = LocalDate.parse(dateStr, formatter);
	        LocalDate establishedDate = LocalDate.of(2009, 6, 30);
	        LocalDate today = LocalDate.now();
	        if(date.isAfter(today) || date.isBefore(establishedDate))
	        	throw new ValidationException("Invalid joining date.");
			
	    } catch (DateTimeParseException e) {
	    	throw new ValidationException("Invalid date format. Please use YYYY-MM-DD.");
	    }
	    return true;
		
	}
	
	public static boolean isValidActiveStatus(String active) throws ValidationException
	{
		active = active.toLowerCase();
		if(!active.equals("true") && !active.equals("false"))
			throw new ValidationException("Enter valid active status(true/false)");
		return true;
	}
	
}
