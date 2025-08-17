package com.litmus7.employeemanager.controller;
import static com.litmus7.employeemanager.constants.MessageConstants.*;
import static com.litmus7.employeemanager.constants.SuccessCodesConstants.*;
import static com.litmus7.employeemanager.constants.ErrorCodeConstants.ERROR_CODES;


import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
import com.litmus7.employeemanager.exceptions.EmployeeServiceException;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.TextFileUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeController {
	

	private final EmployeeService employeeService = new EmployeeService();	
	private static final Logger logger = LogManager.getLogger(EmployeeController.class);
	
	public Response<Integer> createEmployee(Employee employee)
	{
		
		logger.trace("Preparing to create employee: {}", employee.getID());
		try {
			int rows = 0;		

	    	logger.trace("Validating employee data: {}", employee);
	    	employeeService.checkIfDataValid(employee);
	    	logger.info("Validation Successfull");
	    	int result = employeeService.createEmployee(employee);	
	    	rows += result;
	    	if(result == 0)
	    	{
	    		logger.error("Failed to save employee: {}", employee.getID());
	    		return new Response<>(INTERNAL_ERROR, FAILED_TO_PROCESS, 0);
	    	}
	    	logger.info("Employee data with employee id: {} saved successfully.", employee.getID());
		    return new Response<>(SUCCESS, "\nInserted " + rows + " record", rows);

		} catch (EmployeeServiceException e) {
            return new Response<>(INTERNAL_ERROR, ERROR_CODES.get(e.getErrorCode())+ ": " + e.getMessage(), null);
        } catch (Exception e) {
            return new Response<>(INTERNAL_ERROR, "\nSystem error: " + e.getMessage(), null);
        }
	
	}
	
	public Response<String> getAllEmployees()
	{
		logger.trace("Preparing to get all employee data");
		try {
			StringBuilder result = new StringBuilder();
			result.append("\n-----------Employee Records-----------\n");
			List<Employee> employees = new ArrayList<>();
			employees = employeeService.getAllEmployees();
			if(employees == null)
			{
				logger.error("Failed to fetch data");
				return new Response<>(INTERNAL_ERROR, FAILED_TO_PROCESS, null);
			}
			else
			{
				for(Employee emp: employees)
				{
					result.append(TextFileUtil.displayData(emp));
				}
				logger.info("Successfully fetched all employee data");
				return new Response<>(SUCCESS, DATA_RETRIEVED, result.toString());
			}
        } catch (EmployeeServiceException e) {
            return new Response<>(INTERNAL_ERROR, "\nService Error: " + e.getMessage(), null);
        } catch (Exception e) {
            return new Response<>(INTERNAL_ERROR, "\nSystem error: " + e.getMessage(), null);
        }
			
	}
	
	public Response<String> getEmployeeById(String employeeId)
	{
		logger.trace("Preparing to get employee data: {}", employeeId);
		try {
			if (employeeId == null || employeeId.trim().isEmpty()) 
			{
	            return new Response<>(BAD_REQUEST, EMPTY_ID, null);
	        }
			StringBuilder result = new StringBuilder();
			result.append("\n-----------Employee Details-----------\n");
			logger.debug("Calling service to fetch data");
			Employee employee = employeeService.findEmployeeById(Integer.parseInt(employeeId));
			if(employee == null)
			{
				logger.error("Failed to fetched employee data: {}", employeeId);
				return new Response<>(INTERNAL_ERROR, FAILED_TO_PROCESS, null);
			}
			else
			{
				result.append(TextFileUtil.displayData(employee));
				logger.info("Successfully fetche employee data: {}", employeeId);
				return new Response<>(SUCCESS, DATA_RETRIEVED, result.toString());
			}
		}catch (EmployeeNotFoundException e) {
            return new Response<>(NOT_FOUND, e.getMessage(), null);
        } catch (EmployeeServiceException e) {
            return new Response<>(INTERNAL_ERROR, "\nService Error: " + e.getMessage(), null);
        } catch(NumberFormatException e) {
        	return new Response<>(BAD_REQUEST, "\nEnter a valid id!", null);
        } catch (Exception e) {
            return new Response<>(INTERNAL_ERROR, "\nSystem error: " + e.getMessage(), null);
        }

	}
		
	
	
	public Response<Integer> deleteEmployeeById(String employeeId)
	{
		logger.trace("Preparing to delete employee data: {}", employeeId);
		try {
			int result = employeeService.deleteEmployeeById(Integer.parseInt(employeeId));
			if (result > 0)
			{
				logger.info("Successfully deleted employee data: {}", employeeId);
				return new Response<>(SUCCESS, "\nDeleted " + result + " record", result);
			}
			else
			{
				logger.error("Failed to delete employee data: {}", employeeId);
				return new Response<>(INTERNAL_ERROR, FAILED_TO_PROCESS, result);
			}
		} catch (EmployeeNotFoundException e) {
            return new Response<>(NOT_FOUND, e.getMessage(), null);
        } catch (EmployeeServiceException e) {
            return new Response<>(INTERNAL_ERROR, "\nService Error: " + e.getMessage(), null);
        } catch(NumberFormatException e) {
        	return new Response<>(BAD_REQUEST, "\nEnter a valid id!", null);
        } catch (Exception e) {
            return new Response<>(INTERNAL_ERROR, "\nSystem error: " + e.getMessage(), null);
        }
	}
	
	
	public Response<Integer> updateEmployee(Employee employee) 
	{
		logger.trace("Preparing to update employee data: {}", employee.getID());
		try {
	    	employeeService.checkIfDataValid(employee);
			
			int result = employeeService.updateEmployee(employee);
			if (result > 0)
			{
				logger.info("Successfully updated employee data: {}", employee.getID());
				return new Response<>(SUCCESS, "\nUpdated " + result + " record", result);
			}
			else
			{
				logger.error("Failed to update employee data: {}", employee.getID());
				return new Response<>(INTERNAL_ERROR, FAILED_TO_PROCESS, result);
			}
		}catch (EmployeeNotFoundException e) {
            return new Response<>(NOT_FOUND, e.getMessage(), null);
        } catch (EmployeeServiceException e) {
            return new Response<>(INTERNAL_ERROR, "\nService Error: " + e.getMessage(), null);
        } catch (Exception e) {
            return new Response<>(INTERNAL_ERROR, "\nSystem error: " + e.getMessage(), null);
        }
	}
	
	
	public Response<Void> isDuplicateId(String empId)
	{
		List<Integer> ids;
		logger.trace("Checking if the ID exists in database");
		try {
			ids = employeeService.getAllIds();
			if(!ids.contains(Integer.parseInt(empId)))
			{
				logger.info("The id does not exist in the database");
				return new Response<>(BAD_REQUEST, "\nEmployee with ID " + empId + " not found" , null);
			}
			logger.info("The id exists in database");
			return new Response<>(SUCCESS, "Employee with ID " + empId + " already exists" , null);
		} catch (EmployeeServiceException e) {
            return new Response<>(INTERNAL_ERROR, "\nService Error: " + e.getMessage(), null);
        } catch (Exception e) {
            return new Response<>(INTERNAL_ERROR, "\nSystem error: " + e.getMessage(), null);
        }
    		
	}
	
}
