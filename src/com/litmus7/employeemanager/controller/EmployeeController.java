package com.litmus7.employeemanager.controller;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.TextFileUtil;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
	
	private String inputFilePath ;
	private String outputFilePath ;

	EmployeeService service = new EmployeeService();
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	HashSet<String> idSet = new HashSet<>();
	
	List<Employee> employees = new ArrayList<>();
	
	
	public EmployeeController(String inputFilePath, String outputFilePath) throws IOException
	{
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;		
		
		File file = new File(outputFilePath);

	    if (!file.exists() || file.length() == 0) 
	    	TextFileUtil.createHeader(file);
	    else  
	    	this.getAllIDsFromCSV();
	    
	}
	
	
	
	public Response<String> getDataFromTextFile()
	{
	
		StringBuilder result = new StringBuilder();
		result.append("------- Records From Text File -------\n");
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) 
		{
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
            	count++;
            	String[] parts = line.split("\\$");
            	
            	
            	Response<Integer> valid = service.checkIfDataValid(parts[0], parts[1], parts[2], 
            							   parts[3], parts[4], parts[5], parts[6], idSet);
            	if(!valid.isSuccess())
            		return new Response<>(false, valid.getMessage() + " line " + count, null);

            	idSet.add(parts[0]); // ID
                
                Employee emp = new Employee(parts[0], parts[1], parts[2], 
						   					parts[3], parts[4], parts[5], parts[6]);
                employees.add(emp);
                
                result.append(TextFileUtil.displayData(emp));
                
            }
            reader.close();

        } catch (IOException e) {
            return new Response<>(false, "Invalid input file path!", null);
        }
		
		return new Response<>(true, "Success", result.toString());
	}
	
	
	
	public Response<Integer> writeDataToCSV() 
	{
		PrintWriter writer = null;
		int rows = 0;
		try {
			writer = new PrintWriter(new FileWriter(outputFilePath, true));
			TextFileUtil.convertDataToCSV(employees, writer);
		    for(Employee emp: employees)
		    {
		    	int result = service.createEmployee(emp);	
		    	rows += result;
		    	if(result == 0)
		    		return new Response<>(true, "Insertion Failed!", 0);
		    }
		    
		    return new Response<>(true, "Inserted " + rows + " records", rows);
		} catch (IOException e) {
			return new Response<>(false, "Invalid file paths", 0);
		}
		finally
		{
			employees.clear();
			writer.close();
		}
	
	}
	
	
	public Response<Integer> getSingleDataFromUser(String ID, String firstName, String lastName, 
										String mobile, String email, String joiningDate, 
										String active)
	{
		Response<Integer> result ;
		Response<Integer> valid = service.checkIfDataValid(ID, firstName, lastName, mobile, email, joiningDate, active, idSet);
		
		if(!valid.isSuccess())
			return new Response<>(false, "Invalid data! " + valid.getMessage(), 0);
		
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
		List<Employee> employees = new ArrayList<>();
		employees = service.getAllEmployees();
		if(employees == null)
			return new Response<>(false, "Failed to load the data", "");
		else
		{
			for(Employee emp: employees)
			{
				result.append(TextFileUtil.displayData(emp));
			}
			return new Response<>(true, "Loaded data", result.toString());
		}
			
	}
	
	public Response<String> getEmployeeById(int empId)
	{
		if (!idSet.contains(Integer.toString(empId)))
			return new Response<>(false, "ID doesn't exist in records", "");
		StringBuilder result = new StringBuilder();
		result.append("-----------Employee Details-----------\n");
		Employee emp = service.getEmployeeById(empId);
		if(emp == null)
			return new Response<>(false, "Failed to load the data", "");
		else
		{
			result.append(TextFileUtil.displayData(emp));
			return new Response<>(true, "Loaded data", result.toString());
		}
		
	}
	
	
	public Response<Integer> deleteEmployeeById(int empId)
	{
		if (!idSet.contains(Integer.toString(empId)))
			return new Response<>(false, "ID doesn't exist in records", 0);
		int result = service.deleteEmployeeById(empId);
		if (result > 0)
		{
			idSet.remove(Integer.toString(empId));
			return new Response<>(true, "Deleted " + result + " record", result);
		}
			
		else
			return new Response<>(false, "Deletion Failed!", result);
	}
	
	
	public Response<Integer> updateEmployee(String ID, String firstName, String lastName, 
			String mobile, String email, String joiningDate, 
			String active) 
	{
		if (!idSet.contains(ID))
			return new Response<>(false, "ID doesn't exist in records", 0);
		idSet.remove(ID);
		Response<Integer> valid = service.checkIfDataValid(ID, firstName, lastName, mobile, email, joiningDate, active, idSet);
		idSet.add(ID);
		if(!valid.isSuccess())
			return new Response<>(false, "Invalid data! " + valid.getMessage(), 0);
		
		Employee emp = new Employee(ID, firstName, lastName, mobile, email, joiningDate, active);
		int result = service.updateEmployee(emp);
		if (result > 0)
			return new Response<>(true, "Updated " + result + " record", result);
			
		else
			return new Response<>(false, "Updation Failed!", result);
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
	
	
	
}
