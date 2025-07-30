package com.litmus7.employeemanager.controller;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.ValidationUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeController {
	
	
	String option;
	String inputFilePath ;
	String outputFilePath ;
	ValidationUtil val;
	
	public EmployeeController(String inputFilePath, String outputFilePath)
	{
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.val = new ValidationUtil(this.outputFilePath);
		
		File file = new File(outputFilePath);

	    if (!file.exists() || file.length() == 0) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
	            writer.println("ID,First Name,Last Name,Mobile,Email,Joining Date,Active");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	
	public String dataIngestion()
	{
	
		StringBuilder result = new StringBuilder();
		result.append("---------- Entered Records ----------\n");
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) 
		{
            String line;
//            int count = 0;

            while ((line = reader.readLine()) != null) {
//            	count++;
            	String[] parts = line.split("\\$");

                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                String mobile = parts[3];
                String email = parts[4];
                String dateStr = parts[5]; 
                LocalDate joiningDate = LocalDate.parse(dateStr, formatter);
                boolean active = Boolean.parseBoolean(parts[6]);
                
                Employee emp = new Employee(id, firstName, lastName, mobile, email, joiningDate, active);
                
                String message = this.val.Validate(emp);
        		if(!message.equals("valid"))
        		{
//        			message = message.concat(" - in line " + count);
        			return message;
        		}
        		
        		result.append("--------------------------------------\n");
        		result.append("ID           : ").append(parts[0]).append("\n");
                result.append("First Name   : ").append(parts[1]).append("\n");
                result.append("Last Name    : ").append(parts[2]).append("\n");
                result.append("Mobile       : ").append(parts[3]).append("\n");
                result.append("Email        : ").append(parts[4]).append("\n");
                result.append("Joining Date : ").append(parts[5]).append("\n");
                result.append("Active       : ").append(parts[6]).append("\n");
                result.append("--------------------------------------\n");
                
                String csvFormat = String.join(",", parts);
                dataTransformation(csvFormat, outputFilePath);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return result.toString();
	}
	
	public String loadData() {
	        StringBuilder result = new StringBuilder();
	
	        try (BufferedReader reader = new BufferedReader(new FileReader(outputFilePath))) {
	            String line;
	
	            if ((line = reader.readLine()) != null) {
	                result.append("---------- EMPLOYEE RECORDS ----------\n");
	                result.append("--------------------------------------\n");
	            }
	
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(",");
	
	                if (parts.length >= 7) {
	                    result.append("ID           : ").append(parts[0]).append("\n");
	                    result.append("First Name   : ").append(parts[1]).append("\n");
	                    result.append("Last Name    : ").append(parts[2]).append("\n");
	                    result.append("Mobile       : ").append(parts[3]).append("\n");
	                    result.append("Email        : ").append(parts[4]).append("\n");
	                    result.append("Joining Date : ").append(parts[5]).append("\n");
	                    result.append("Active       : ").append(parts[6]).append("\n");
	                    result.append("--------------------------------------\n");
	                } else {
	                    result.append("Invalid line: ").append(line).append("\n");
	                }
	            }
	        } catch (IOException e) {
	            result.append("Error reading file: ").append(e.getMessage()).append("\n");
	        }
	
	        return result.toString();
    	}	
	
	
	public void dataTransformation(String employeeData, String outputFilePath) throws IOException {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath, true))) {
	        writer.println(employeeData);
	    }
	}
	
	
	public String dataEntry(Employee emp, String outputFilePath) throws IOException
	{
		int ID = emp.getID();
		String firstName = emp.getFirstName();
		String lastName = emp.getLastName();
		String mobile = emp.getMobileNumber();
		String email = emp.getEmail();
		LocalDate date = emp.getJoiningDate();
		boolean active = emp.isActive();
		
		String message = this.val.Validate(emp);
		if(!message.equals("valid"))
		{
			return message;
		}
		String csvFormat = String.format("%d,%s,%s,%s,%s,%s,%b", ID, firstName, lastName, mobile, email, date, active);
//		System.out.println(csvFormat);
		dataTransformation(csvFormat, outputFilePath);
		
		return "Data entered successfully";
	}
	
	
}
