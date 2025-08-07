package com.litmus7.employeemanager.service;
import java.util.HashSet;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.util.ValidationUtil;
import com.litmus7.employeemanager.dao.EmployeeDAO;


public class EmployeeService {
	EmployeeDAO dao = new EmployeeDAO();
	ValidationUtil val = new ValidationUtil();
	
	public int createEmployee(Employee  emp)
	{
		return dao.insertEmployeeDetailsToDatabase(emp);
			
	}
	
	public List<Employee> getAllEmployees()
	{

		return dao.getAllEmployeeData();

	}
	
	public Employee getEmployeeById(int empId)
	{
		
		return dao.getEmployeeData(empId);

	}
	
	
	public int deleteEmployeeById(int empId)
	{

		return dao.deleteEmployeeData(empId);
	}
	
	
	public int updateEmployee(Employee emp) 
	{

		return dao.updateEmployee(emp);
	}
	
	
	public Response<Integer> checkIfDataValid(String ID, String firstName, String lastName, 
			String mobile, String email, String dateStr, String active, HashSet<String> idSet)
	{
		String message;
		if( idSet.contains(ID) )
			return new Response<>(false,  "Duplicate ID!", 0);
		message = val.isValidID(ID);
		if(!message.equals("valid"))
			return new Response<>(false, message, 0);
		
		
		message = val.isValidName(firstName);
		if(!message.equals("valid"))
		return new Response<>(false, message, 0);
		
		
		message = val.isValidName(lastName);
		if(!message.equals("valid"))
		return new Response<>(false, message, 0);
		
		
		message = val.isValidNumber(mobile);
		if(!message.equals("valid"))
		return new Response<>(false, message, 0);
		
		
		message = val.isValidEmail(email);
		if(!message.equals("valid"))
		return new Response<>(false, message, 0);
		
		
		message = val.isValidJoiningDate(dateStr);
		if(!message.equals("valid"))
		return new Response<>(false, message, 0);
		
		message = val.isValidActiveStatus(active);
		if(!message.equals("valid"))
		return new Response<>(false, message, 0);
		
		return new Response<>(true, "valid" , 1);
		
	}
	
}
