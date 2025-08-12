package com.litmus7.employeemanager.controller;
import com.litmus7.employeemanager.constants.MessageConstants;
import com.litmus7.employeemanager.constants.SuccessCodesConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.TextFileUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
	

	private EmployeeService service = new EmployeeService();
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	
	private List<Employee> employees = new ArrayList<>();
	
	
	public Response<String> getDataFromTextFile(String inputFilePath)
	{
		if (inputFilePath == null || inputFilePath.trim().isEmpty()) {
	        return new Response<>(SuccessCodesConstants.BAD_REQUEST, MessageConstants.INPUT_FILE_PATH_EMPTY, null);
	    }
	
		StringBuilder result = new StringBuilder();
		result.append("------- Records From Text File -------\n");
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) 
		{
			List<String> lines = reader.lines().toList();
            employees = service.getDataFromTextFile(lines);
            for(Employee emp : employees)
            {
            	result.append(TextFileUtil.displayData(emp));
            }
            
            return new Response<>(SuccessCodesConstants.SUCCESS, MessageConstants.DATA_RETRIEVED, result.toString());
        } catch (IOException e) {
            return new Response<>(SuccessCodesConstants.NOT_FOUND, MessageConstants.INVALID_FILE_PATH, null);
        }
		
		
	}
	
	
	
	public Response<Integer> writeDataToDatabase() 
	{
		
		try {
			int rows = 0;		
		    for(Employee emp: employees)
		    {
		    	
		    	if(service.isDuplicateId(emp.getID()))
		    	{
		    		employees.clear();
		    		return new Response<>(SuccessCodesConstants.BAD_REQUEST, "Inserted " + rows + " record\n" + "Duplicate ID!", 0);
		    	}
		    	String message = service.checkIfDataValid(emp);
		    	if(!message.equals("valid"))
		    	{
		    		employees.clear();
		    		return new Response<>(SuccessCodesConstants.BAD_REQUEST, "Inserted " + rows + " record\n" + message, rows);
		    	}
		    	int result = service.createEmployee(emp);	
		    	rows += result;
		    	if(result == 0)
		    		return new Response<>(SuccessCodesConstants.INTERNAL_ERROR, MessageConstants.FAILED_TO_PROCESS, 0);
		    }
		    
		    return new Response<>(SuccessCodesConstants.SUCCESS, "Inserted " + rows + " record", rows);

		} catch(Exception e) {
			return new Response<>(SuccessCodesConstants.BAD_REQUEST, "Error! " + e.getMessage(), 0);
		}
	}
	
	
	public Response<Integer> createEmployee(Employee emp)
	{
		
		employees.add(emp);
		return writeDataToDatabase();
		
	}
	
	public Response<String> getAllEmployees()
	{
		
		StringBuilder result = new StringBuilder();
		result.append("-----------Employee Records-----------\n");
		List<Employee> employees = new ArrayList<>();
		employees = service.getAllEmployees();
		if(employees == null)
			return new Response<>(SuccessCodesConstants.INTERNAL_ERROR, MessageConstants.FAILED_TO_PROCESS, null);
		else
		{
			for(Employee emp: employees)
			{
				result.append(TextFileUtil.displayData(emp));
			}
			return new Response<>(SuccessCodesConstants.SUCCESS, MessageConstants.DATA_RETRIEVED, result.toString());
		}
			
	}
	
	public Response<String> getEmployeeById(String empId)
	{
		try {
			if (empId == null || empId.trim().isEmpty()) {
	            return new Response<>(SuccessCodesConstants.BAD_REQUEST, MessageConstants.EMPTY_ID, null);
	        }
			
			if (!service.isDuplicateId(empId))
				return new Response<>(SuccessCodesConstants.NOT_FOUND, MessageConstants.ID_NOT_FOUND, null);
			StringBuilder result = new StringBuilder();
			result.append("-----------Employee Details-----------\n");
			Employee emp = service.getEmployeeById(Integer.parseInt(empId));
			if(emp == null)
				return new Response<>(SuccessCodesConstants.INTERNAL_ERROR, MessageConstants.FAILED_TO_PROCESS, null);
			else
			{
				result.append(TextFileUtil.displayData(emp));
				return new Response<>(SuccessCodesConstants.SUCCESS, MessageConstants.DATA_RETRIEVED, result.toString());
			}
		}catch (Exception e) {
			return new Response<>(SuccessCodesConstants.BAD_REQUEST, "Error " + e.getMessage() , null);
		}
		
	}
	
	
	public Response<Integer> deleteEmployeeById(String empId)
	{
		try {
			if (!service.isDuplicateId(empId))
				return new Response<>(SuccessCodesConstants.NOT_FOUND, MessageConstants.ID_NOT_FOUND, 0);
			int result = service.deleteEmployeeById(Integer.parseInt(empId));
			if (result > 0)
				return new Response<>(SuccessCodesConstants.SUCCESS, "Deleted " + result + " record", result);
			else
				return new Response<>(SuccessCodesConstants.INTERNAL_ERROR, MessageConstants.FAILED_TO_PROCESS, result);
		} catch(Exception e) {
			return new Response<>(SuccessCodesConstants.BAD_REQUEST, "Error " + e.getMessage(), null);
		}
	}
	
	
	public Response<Integer> updateEmployee(Employee emp) 
	{
		try {
			if(!service.isDuplicateId(emp.getID()))
	    		return new Response<>(SuccessCodesConstants.NOT_FOUND, MessageConstants.ID_NOT_FOUND, 0);
	    	String message = service.checkIfDataValid(emp);
	    	if(!message.equals("valid"))
	    		return new Response<>(SuccessCodesConstants.BAD_REQUEST, message, 0);
			
			int result = service.updateEmployee(emp);
			if (result > 0)
				return new Response<>(SuccessCodesConstants.SUCCESS, "Updated " + result + " record", result);
				
			else
				return new Response<>(SuccessCodesConstants.INTERNAL_ERROR, MessageConstants.FAILED_TO_PROCESS, result);
		}catch(Exception e) {
			return new Response<>(SuccessCodesConstants.BAD_REQUEST, "Error " + e.getMessage(), 0);
		}
	}
	
	
	
	
}
