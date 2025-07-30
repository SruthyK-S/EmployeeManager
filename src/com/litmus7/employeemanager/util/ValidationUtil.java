package com.litmus7.employeemanager.util;
import java.time.LocalDate;
import java.util.HashSet;
import java.io.*;

import com.litmus7.employeemanager.dto.Employee;

public class ValidationUtil 
{

	String outputFilePath;
	
	public ValidationUtil(String outputFilePath)
	{
		this.outputFilePath = outputFilePath;
		
	}
	
	
	public boolean isDuplicateID(int newID, String outputFilePath) 
	{
		
	    try (BufferedReader reader = new BufferedReader(new FileReader(outputFilePath))) 
	    {
	        String line;
	        reader.readLine();

	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length > 0) {
	                int existingID = Integer.parseInt(parts[0].trim());
	                if (existingID == newID) 
	                {
	                    return true; 
	                }
	            }
	        }

	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}
	
	public boolean isDuplicateMobile(String mobile) 
	{
	    try (BufferedReader reader = new BufferedReader(new FileReader(this.outputFilePath))) 
	    {
	        String line;
	        reader.readLine();

	        while ((line = reader.readLine()) != null) {
	            
	            String[] parts = line.split(",");
	            if (parts.length > 0) {
	                String existingNumber = parts[3];
	                if (existingNumber.equals(mobile)) {
	                    return true; 
	                }
	            }
	        }

	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}
	
	public boolean isDuplicateEmail(String email) 
	{
	    try (BufferedReader reader = new BufferedReader(new FileReader(this.outputFilePath))) 
	    {
	        String line;
	        reader.readLine();

	        while ((line = reader.readLine()) != null) {

	            String[] parts = line.split(",");
	            if (parts.length > 0) {
	                String existingEmail = parts[4];
	                if (existingEmail.equals(email)) {
	                    return true; 
	                }
	            }
	        }

	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}
	
	
	public String Validate(Employee emp)
	{
		String message = "valid";
		
		int ID = emp.getID();
		if (isDuplicateID(ID, outputFilePath)) 
		{
		    message = "Duplicate ID! This ID already exists";
		    return message;
		    
		} 
	
		String firstName = emp.getFirstName();
		if(firstName.isEmpty())
		{
			message = "First Name cannot be empty";
			return message;
		}
		
		
		String lastName = emp.getLastName();
		if(lastName.isEmpty())
		{
			message = "Last Name cannot be empty";
			return message;
		}
		
		String mobile = emp.getMobileNumber();
		if (!mobile.matches("\\d{10}"))
		{
		    message = "Invalid mobile number. Must be 10 digits.";
		    return message;
		}
		else if(isDuplicateMobile(mobile))
		{
			message = "Duplicate mobile number.";
		}
		
		String email = emp.getEmail();
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}"))
		{
			message = "Invalid email address.";
			return message;
		}
		else if(isDuplicateEmail(email))
		{
			message = "Duplicate email address";
		}
		
		boolean active = emp.isActive();
		
		LocalDate date = emp.getJoiningDate();
		LocalDate today = LocalDate.now();
		LocalDate establishedDate = LocalDate.of(2009, 6, 30);
		if(date.isAfter(today) || date.isBefore(establishedDate))
		{
			message = "Invalid joining date.";
			return message;
		}
		
		return message;
	}
}
