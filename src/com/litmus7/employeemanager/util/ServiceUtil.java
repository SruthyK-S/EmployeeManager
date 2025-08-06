package com.litmus7.employeemanager.util;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.dao.EmployeeDAO;


public class ServiceUtil {
	EmployeeDAO dao = new EmployeeDAO();
	
	public Response<Integer> createEmployee(Employee  emp)
	{
		int result = dao.insertEmployeeDetailsToDatabase(emp);
		if (result > 0)
			return new Response<>(true, "Inserted " + result + " records", result);
		else
			return new Response<>(false, "Insertion Failed!", result);
		
			
	}
	
	public Response<List<Employee>> getAllEmployees()
	{
		List<Employee> employees = new ArrayList<>();
		employees = dao.getAllEmployeeData();
		if(employees == null)
			return new Response<>(false, "Failed to load the data", null);
		else
			return new Response<>(true, "Loaded data", employees);
	}
	
	public Response<Employee> getEmployeeById(int empId)
	{
		
		Employee emp = dao.getEmployeeData(empId);
		if(emp == null)
			return new Response<>(false, "Failed to load the data", null);
		else
			return new Response<>(true, "Details of employee with employee id " + empId, emp);
	}
	
	
	public Response<Integer> deleteEmployeeById(int empId)
	{
		
		int result = dao.deleteEmployeeData(empId);
		if (result > 0)
			return new Response<>(true, "Deleted " + result + " record", result);
		else
			return new Response<>(false, "Deletion Failed!", result);
	}
	
	
	public Response<Integer> updateEmployee(Employee emp) 
	{
		int result = dao.updateEmployee(emp);
		if (result > 0)
			return new Response<>(true, "Updated " + result + " record", result);
		else
			return new Response<>(false, "Updation Failed!", result);
	}
	
}
