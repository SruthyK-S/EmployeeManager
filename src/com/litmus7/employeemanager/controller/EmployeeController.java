package com.litmus7.employeemanager.controller;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.ValidationUtil;

import java.io.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeController {
	
	
	String option;
	String inputFilePath ;
	String outputFilePath ;
	
	public EmployeeController(String inputFilePath, String outputFilePath)
	{
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		
		File file = new File(outputFilePath);

	    if (!file.exists() || file.length() == 0) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
	            writer.println("ID,First Name,Last Name,Mobile,Email,Joining Date,Active");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	ValidationUtil val = new ValidationUtil();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	
	public String dataIngestion()
	{
		System.out.println("");
		String data = "";
		String result = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) 
		{
            String line;

            while ((line = reader.readLine()) != null) {
            	result = line;
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
                
                String message = val.Validate(emp);
        		if(!message.equals("valid"))
        		{
        			return message;
        		}
                
                String csvFormat = String.join(",", parts);
                data = data.concat(csvFormat + "\n");
                dataTransformation(csvFormat, outputFilePath);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return data;
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
		
		String message = val.Validate(emp);
		if(!message.equals("valid"))
		{
			return message;
		}
		String csvFormat = String.format("%d,%s,%s,%s,%s,%s,%b", ID, firstName, lastName, mobile, email, date, active);
		System.out.println(csvFormat);
		dataTransformation(csvFormat, outputFilePath);
		
		return "Data entered successfully";
	}
}
