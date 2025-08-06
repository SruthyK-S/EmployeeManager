package com.litmus7.employeemanager.controller;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.util.ValidationUtil;
import com.litmus7.employeemanager.util.TextFileUtil;
import com.litmus7.employeemanager.util.ServiceUtil;
import com.litmus7.employeemanager.dao.EmployeeDAO;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
	
	
	String option;
	String inputFilePath ;
	String outputFilePath ;
	EmployeeDAO dao = null;
	ValidationUtil val = new ValidationUtil();
	ServiceUtil service = new ServiceUtil();
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	HashSet<String> idSet = new HashSet<>();
	
	List<Employee> employees = new ArrayList<>();
	
	
	public EmployeeController(String inputFilePath, String outputFilePath) throws IOException
	{
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.dao = new EmployeeDAO();
		
		
		File file = new File(outputFilePath);

	    if (!file.exists() || file.length() == 0) 
	    	TextFileUtil.createHeader(file);
	    else  
	    	this.getAllIDsFromCSV();
	    
	}
	
	
	
	public String getDataFromTextFile()
	{
	
		StringBuilder result = new StringBuilder();
		result.append("------- Records From Text File -------\n");
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) 
		{
            String line;
            String message;
            int count = 0;

            while ((line = reader.readLine()) != null) {
            	count++;
            	String[] parts = line.split("\\$");
            	
            	
            	message = checkIfDataValid(parts[0], parts[1], parts[2], 
            							   parts[3], parts[4], parts[5], parts[6]);
            	
            	if(!message.equals("valid"))
            		return (message + " line " + count);

            	idSet.add(parts[0]); // ID
                
                Employee emp = new Employee(parts[0], parts[1], parts[2], 
						   					parts[3], parts[4], parts[5], parts[6]);
                employees.add(emp);
                
                result.append(TextFileUtil.displayData(emp));
                
            }
            reader.close();

        } catch (IOException e) {
            return "Invalid input file path!";
        }
		
		return result.toString();
	}
	
	
	
	public Response<Integer> writeDataToCSV() 
	{
		PrintWriter writer;
		Response<Integer> result;
		int rows = 0;
		try {
			writer = new PrintWriter(new FileWriter(outputFilePath, true));
			TextFileUtil.convertDataToCSV(employees, writer);
		    for(Employee emp: employees)
		    {
		    	result = service.createEmployee(emp);	
		    	rows +=  (int) result.getData();
		    	if(!result.isSuccess())
		    		return result;
		    }
		    employees.clear();
		    writer.close();
		    return new Response<>(true, "Inserted " + rows + " records", rows);
		} catch (IOException e) {
			return new Response<>(false, "Invalid file paths", 0);
		}
	
	}
	
	
	public Response<Integer> getSingleDataFromUser(String ID, String firstName, String lastName, 
										String mobile, String email, String joiningDate, 
										String active)
	{
		Response<Integer> result ;
		String message = checkIfDataValid(ID, firstName, lastName, mobile, email, joiningDate, active);
		
		if(!message.equals("valid"))
			return new Response<>(false, "Invalid data! " + message, 0);
		
		idSet.add(ID);
		
		Employee emp = new Employee(ID, firstName, lastName, mobile, email, joiningDate, active);
		employees.add(emp);
		result = writeDataToCSV();
		return result;
		
	}
	
	public Response<String> getAllEmployees()
	{
		StringBuilder result = new StringBuilder();
		result.append("-----------Employee Records-----------\n");
		Response<List<Employee>> employees = service.getAllEmployees();
		if(employees.getData() == null)
			return new Response<>(false, "Failed to load the data", "");
		else
		{
			for(Employee emp: employees.getData())
			{
				result.append(TextFileUtil.displayData(emp));
			}
			return new Response<>(true, "Loaded data", result.toString());
		}
			
	}
	
	public Response<String> getEmployeeById(int empId)
	{
		StringBuilder result = new StringBuilder();
		result.append("-----------Employee Details-----------\n");
		Response<Employee> emp = service.getEmployeeById(empId);
		if(emp.getData() == null)
			return new Response<>(false, "Failed to load the data", "");
		else
		{
			result.append(TextFileUtil.displayData(emp.getData()));
			return new Response<>(true, "Loaded data", result.toString());
		}
		
	}
	
	
	public Response<Integer> deleteEmployeeById(int empId)
	{
		if (!idSet.contains(Integer.toString(empId)))
			return new Response<>(false, "ID doesn't exist in records", 0);
		Response<Integer> result = service.deleteEmployeeById(empId);
		if(result.isSuccess())
			idSet.remove(Integer.toString(empId));
		return result;
	}
	
	
	public Response<Integer> updateEmployee(String ID, String firstName, String lastName, 
			String mobile, String email, String joiningDate, 
			String active) 
	{
		if (!idSet.contains(ID))
			return new Response<>(false, "ID doesn't exist in records", 0);
		idSet.remove(ID);
		String message = checkIfDataValid(ID, firstName, lastName, mobile, email, joiningDate, active);
		idSet.add(ID);
		if(!message.equals("valid"))
			return new Response<>(false, "Invalid data! " + message, 0);
		
		Employee emp = new Employee(ID, firstName, lastName, mobile, email, joiningDate, active);
		Response<Integer> result = service.updateEmployee(emp);
		return result;
	}
	
	

	private void getAllIDsFromCSV() throws IOException 
	{
	
		    BufferedReader reader = new BufferedReader(new FileReader(outputFilePath));
		    
	        String line;
	        reader.readLine();
	
	        while ((line = reader.readLine()) != null) 
	        {
	            String[] parts = line.split(",");
	            if (parts.length > 0) 
	            {
	                String ID = parts[0].trim();
	                idSet.add(ID);
	            }
	        }
	        reader.close();
	}
	
	private String checkIfDataValid(String ID, String firstName, String lastName, 
							String mobile, String email, String dateStr, String active)
	{
		String message;
		
        if( idSet.contains(ID) )
        	return "Duplicate ID!";
        message = val.isValidID(ID);
        if(!message.equals("valid"))
        	return message;
        
   
        message = val.isValidName(firstName);
        if(!message.equals("valid"))
        	return (message);
        
        
        message = val.isValidName(lastName);
        if(!message.equals("valid"))
        	return (message);
        
        
        message = val.isValidNumber(mobile);
        if(!message.equals("valid"))
        	return (message);
        
       
        message = val.isValidEmail(email);
        if(!message.equals("valid"))
        	return (message);
        
        
        message = val.isValidJoiningDate(dateStr);
        if(!message.equals("valid"))
        	return (message);
        
        message = val.isValidActiveStatus(active);
        if(!message.equals("valid"))
        	return (message);
        
        return "valid";
        
	}
	
}
