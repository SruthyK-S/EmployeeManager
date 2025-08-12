package com.litmus7.employeemanager.util;
import com.litmus7.employeemanager.dto.Employee;

import java.io.*;
import java.util.List;



public class TextFileUtil 
{
	public static void convertDataToCSV(List<Employee> employees, PrintWriter writer) 
	{
		
		
	    for(Employee emp: employees)
	    {
	    	StringBuilder csv = new StringBuilder();
	    	csv.append(emp.getID()).append(",");
	    	csv.append(emp.getFirstName()).append(",");
	    	csv.append(emp.getLastName()).append(",");
	    	csv.append(emp.getMobileNumber()).append(",");
	    	csv.append(emp.getEmail()).append(",");
	    	csv.append(emp.getJoiningDate()).append(",");
	    	csv.append(emp.getActiveStatus()).append(",");
	    	
	    	writer.println(csv.toString());	
	    }  
	}
	
	
	public static String displayData(Employee emp)
	{
		StringBuilder result = new StringBuilder(); 
		result.append("--------------------------------------\n");
		result.append("ID           : ").append(emp.getID()).append("\n");
        result.append("First Name   : ").append(emp.getFirstName()).append("\n");
        result.append("Last Name    : ").append(emp.getLastName()).append("\n");
        result.append("Mobile       : ").append(emp.getMobileNumber()).append("\n");
        result.append("Email        : ").append(emp.getEmail()).append("\n");
        result.append("Joining Date : ").append(emp.getJoiningDate()).append("\n");
        result.append("Active       : ").append(emp.getActiveStatus()).append("\n");
        result.append("--------------------------------------\n");
        
        return result.toString();
	}
	
	public static String[] splitByDelimiter(String delimiter, String data)
	{
		return data.split("\\$");
	}
}
