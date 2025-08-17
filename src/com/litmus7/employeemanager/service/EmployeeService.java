package com.litmus7.employeemanager.service;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exceptions.EmployeeDaoException;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
//import static com.litmus7.employeemanager.constants.ErrorCodeConstants.ERROR_CODES;
import com.litmus7.employeemanager.exceptions.EmployeeServiceException;
import com.litmus7.employeemanager.exceptions.ValidationException;
import com.litmus7.employeemanager.util.ValidationUtil;
import com.litmus7.employeemanager.dao.EmployeeDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmployeeService {
	private static final Logger logger = LogManager.getLogger(EmployeeService.class);
	
	private EmployeeDAO employeeDao = new EmployeeDAO();
	
	public int createEmployee(Employee  emp) throws EmployeeServiceException
	{
		logger.trace("Entering service layer createEmployee() with ID: {}", emp.getID());
		try {
			return employeeDao.saveEmployee(emp);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException(e.getMessage(), e.getErrorCode(), e);
        }
		
			
	}
	
	public List<Employee> getAllEmployees() throws EmployeeServiceException
	{
		try {
			logger.trace("Entering service layer getAllEmployees()");
            return employeeDao.getAllEmployees();
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException(e.getMessage(), e.getErrorCode(), e);
        }
	}
	
	public Employee findEmployeeById(int id) throws EmployeeServiceException, EmployeeNotFoundException {
        try {
            return employeeDao.getEmployeeById(id);
        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException(e.getMessage(), e.getErrorCode(), e);
        }
    }


	
	
	public int deleteEmployeeById(int empId) throws EmployeeNotFoundException, EmployeeServiceException
	{

		try{
			return employeeDao.deleteEmployeeData(empId);
		} catch (EmployeeDaoException e) {
            throw new EmployeeServiceException(e.getMessage(), e.getErrorCode(), e);
        }
		
	}
	
	
	public int updateEmployee(Employee emp) throws EmployeeNotFoundException, EmployeeServiceException
	{

		try{
			return employeeDao.updateEmployee(emp);
		} catch (EmployeeDaoException e) {
            throw new EmployeeServiceException(e.getMessage(), e.getErrorCode(), e);
        }
	}
	
	
	public List<Integer> getAllIds() throws EmployeeServiceException 
	{
		try{
			return employeeDao.getAllIds();
		} catch (EmployeeDaoException e) {
            throw new EmployeeServiceException(e.getMessage(), e.getErrorCode(), e);
        }
			
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
			throw new EmployeeServiceException(e.getMessage(), 201, e);
		}
		
		
	}
	
}
