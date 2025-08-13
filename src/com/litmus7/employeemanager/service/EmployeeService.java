package com.litmus7.employeemanager.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exceptions.EmployeeDaoException;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
import com.litmus7.employeemanager.exceptions.EmployeeServiceException;
import com.litmus7.employeemanager.exceptions.ValidationException;
import com.litmus7.employeemanager.util.TextFileUtil;
import com.litmus7.employeemanager.util.ValidationUtil;
import com.litmus7.employeemanager.dao.EmployeeDAO;


public class EmployeeService {
	private EmployeeDAO dao = new EmployeeDAO();
	
	public int createEmployee(Employee  emp) throws EmployeeServiceException
	{
		try {
			return dao.saveEmployee(emp);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Service layer failed to save employee details.", e);
        }
		
			
	}
	
	public List<Employee> getAllEmployees() throws EmployeeServiceException
	{
		try {
            return dao.getAllEmployees();
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Service layer failed to fetch employee details.", e);
        }
	}
	
	public Employee findEmployeeById(int id) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            return dao.getEmployeeById(id);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Service layer failed to fetch employee.", e);
        }
    }


	
	
	public int deleteEmployeeById(int empId) throws EmployeeNotFoundException, EmployeeServiceException
	{

		try{
			return dao.deleteEmployeeData(empId);
		} catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Service layer failed to delete employee.", e);
        }
		
	}
	
	
	public int updateEmployee(Employee emp) throws EmployeeNotFoundException, EmployeeServiceException
	{

		try{
			return dao.updateEmployee(emp);
		} catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Service layer failed to update employee details.", e);
        }
	}
	
	
	public List<Employee> getDataFromTextFile(List<String> lines) throws IOException 
	{
		List<Employee> employees = new ArrayList<Employee>();
	
	    for(String line : lines)
	    {
	    	
	    	String[] parts = TextFileUtil.splitByDelimiter("\\$", line);    	
	        Employee emp = new Employee(parts[0], parts[1], parts[2], 
					   					parts[3], parts[4], parts[5], parts[6]);
	        employees.add(emp);
	    }
	    return employees;
	}
	
	private List<Integer> getAllIds() 
	{

		return dao.getAllIds();
	}
	
	
	
	public boolean isDuplicateId(String id) throws NumberFormatException
	{
		List<Integer> ids = getAllIds();
    	return ids.contains(Integer.parseInt(id));
    		
	}
	
	
	public boolean checkIfDataValid(Employee emp) throws EmployeeServiceException
	{
		try
		{
			
			ValidationUtil.isValidID(emp.getID());
			
			
			
			ValidationUtil.isValidName(emp.getFirstName());
			
			
			ValidationUtil.isValidName(emp.getLastName());
			
			
			
			ValidationUtil.isValidNumber(emp.getMobileNumber());
			
			
			ValidationUtil.isValidEmail(emp.getEmail());
			
			
			ValidationUtil.isValidJoiningDate(emp.getJoiningDate());
			
			
			ValidationUtil.isValidActiveStatus(emp.getActiveStatus());

			return true;
		}
		catch(ValidationException e)
		{
			throw new EmployeeServiceException("Service layer failed to insert employee data.", e);
		}
		
		
	}
	
}
