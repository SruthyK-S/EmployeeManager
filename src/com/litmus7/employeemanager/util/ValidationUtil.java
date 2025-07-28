package com.litmus7.employeemanager.util;
import java.time.LocalDate;
import java.util.HashSet;

import com.litmus7.employeemanager.dto.Employee;

public class ValidationUtil 
{
	HashSet<Integer> idSet = new HashSet<>();
	
	public String Validate(Employee emp)
	{
		String message = "valid";
		
		int ID = emp.getID();
		if (idSet.contains(ID)) 
		{
		    message = "Duplicate ID! This ID already exists.";
		    return message;
		    
		} 
	
		String firstName = emp.getFirstName();
		if(firstName.isEmpty())
		{
			message = "First Name cannot be empty.";
			return message;
		}
		
		
		String lastName = emp.getLastName();
		if(lastName.isEmpty())
		{
			message = "Last Name cannot be empty.";
			return message;
		}
		
		String mobile = emp.getMobileNumber();
		if (!mobile.matches("\\d{10}"))
		{
		    message = "Invalid mobile number. Must be 10 digits.";
		    return message;
		}
		
		String email = emp.getEmail();
		if(!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}"))
		{
			message = "Invalid email address.";
			return message;
		}
		
		boolean active = emp.isActive();
		
		LocalDate date = emp.getJoiningDate();
		LocalDate today = LocalDate.now();
		if(date.isAfter(today))
		{
			message = "Invalid joining date.";
			return message;
		}
		idSet.add(ID);
		return message;
	}
}
