package com.litmus7.employeemanager.service;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.dao.EmployeeDAO;


public class EmployeeService {
	EmployeeDAO dao = new EmployeeDAO();
	
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
	
}
