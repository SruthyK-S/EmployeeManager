package com.litmus7.employeemanager.dao;
import static com.litmus7.employeemanager.constants.SqlConstants.*;
import static com.litmus7.employeemanager.constants.ErrorCodeConstants.ERROR_CODES;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exceptions.EmployeeDaoException;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
import com.litmus7.employeemanager.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmployeeDAO{
	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);
	private static final int ERR_DB_CONNECTION = 404;
	private static final int ERR_PARAMETER_BIND = 103;
	private static final int ERR_QUERY_EXECUTION = 102;


		
	public int saveEmployee(Employee  emp) throws EmployeeDaoException 
	{
		logger.trace("Preparing to save employee: {}", emp.getID());
		int errorCode = ERR_DB_CONNECTION;
		try (
				Connection connection = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT_EMPLOYEE)
	        ) {
				errorCode = ERR_PARAMETER_BIND;
				pstmt.setInt(1, Integer.parseInt(emp.getID()));   
				pstmt.setString(2, emp.getFirstName());                     
				pstmt.setString(3, emp.getLastName());                      
				pstmt.setString(4, emp.getMobileNumber());                  
				pstmt.setString(5, emp.getEmail());                         
				pstmt.setDate(6, java.sql.Date.valueOf(emp.getJoiningDate())); 
				pstmt.setBoolean(7, Boolean.parseBoolean(emp.getActiveStatus())); 
				logger.debug("Executing SQL Query to insert employee: {} ", emp.getID());
				errorCode = ERR_QUERY_EXECUTION;
	            return pstmt.executeUpdate();
	            
	        } catch (SQLException e) {
	        	logger.error("SQL error: {}", e.getMessage());
	        	throw new EmployeeDaoException(ERROR_CODES.get(errorCode), errorCode, e);
	        }
			
	}
	
	
	public List<Employee> getAllEmployees() throws EmployeeDaoException 
	{
		List<Employee> employees = new ArrayList<Employee>();
		ResultSet myRs = null;
		logger.trace("Preparing to get all employee data");
		int errorCode = ERR_DB_CONNECTION;
		try (
				Connection connection = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = connection.prepareStatement(SQL_GET_ALL_EMPLOYEES)
	        ) {
				errorCode = ERR_QUERY_EXECUTION;
				myRs = pstmt.executeQuery();
				while (myRs.next())
				{
					Employee emp = new Employee(myRs.getString(COL_ID), myRs.getString(COL_FIRST_NAME), 
												myRs.getString(COL_LAST_NAME), myRs.getString(COL_MOBILE), 
												myRs.getString(COL_EMAIL), myRs.getString(COL_JOINING_DATE),
												myRs.getString(COL_ACTIVE_STATUS));
					employees.add(emp);
				}
				logger.info("Successfully fetched all employee data");
				return employees;
					
	        } catch (SQLException e) {
	        	logger.error("SQL error: {}", e.getMessage());
	        	throw new EmployeeDaoException(ERROR_CODES.get(errorCode), errorCode, e);
	        }
			finally {
				try { if (myRs != null) myRs.close(); } catch (SQLException ignored) {}
		}
		
	}
	
	
	public Employee getEmployeeById(int empId) throws EmployeeDaoException, EmployeeNotFoundException
	{	
		ResultSet myRs = null;
		logger.trace("Preparing to get employee data: {}", empId);
		int errorCode = ERR_DB_CONNECTION;
		try (
				Connection connection = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = connection.prepareStatement(SQL_GET_AN_EMPLOYEE)
	        ) {
				errorCode = ERR_PARAMETER_BIND;
				pstmt.setInt(1, empId);
				errorCode = ERR_QUERY_EXECUTION;
				myRs = pstmt.executeQuery();
				if (myRs.next())
				{
					logger.info("Successfully fetched employee data: {}", empId);
					return new Employee(myRs.getString(COL_ID), myRs.getString(COL_FIRST_NAME), 
							myRs.getString(COL_LAST_NAME), myRs.getString(COL_MOBILE), 
							myRs.getString(COL_EMAIL), myRs.getString(COL_JOINING_DATE),
							myRs.getString(COL_ACTIVE_STATUS));
				}
				else {
					logger.error("failed to fetch employee: {}", empId);
	                throw new EmployeeNotFoundException("Employee with ID " + empId + " not found.");
	            }

	        } catch (SQLException e) {
	        	logger.error("SQL error: {}", e.getMessage());
	        	throw new EmployeeDaoException(ERROR_CODES.get(errorCode), errorCode, e);
	        }
			finally {
	            try { if (myRs != null) myRs.close(); } catch (SQLException ignored) {}
			}
	}
	
	
	public int deleteEmployeeData(int empId) throws EmployeeDaoException, EmployeeNotFoundException
	{
		
		logger.trace("preparing to delete employee: {}", empId);
		int errorCode = ERR_DB_CONNECTION;
		try (
				Connection connection = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_EMPLOYEE)
	        ) {
				errorCode = ERR_PARAMETER_BIND;
				pstmt.setDouble(1, empId);
				errorCode = ERR_QUERY_EXECUTION;
				int rows =  pstmt.executeUpdate();
	            if(rows > 0)
	            {
	            	logger.info("Successfully deleted employee data: {}", empId);
	            	return rows;
	            }
	            else
	            {
	            	logger.error("failed to delete employee data: {}", empId);
	            	throw new EmployeeNotFoundException("Employee with ID " + empId + " not found.");
	            }
	        } catch (SQLException e) {
	        	logger.error("SQL error: {}", e.getMessage());
	        	throw new EmployeeDaoException(ERROR_CODES.get(errorCode), errorCode, e);
	        }
	}
	
	
	public int updateEmployee(Employee emp) throws EmployeeDaoException, EmployeeNotFoundException
	{
		
		logger.trace("preparing to update Employee: {}", emp.getID());
		int errorCode = ERR_DB_CONNECTION;
        try (
        		Connection connection = DBConnectionUtil.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_EMPLOYEE)
        		) {
        	errorCode = ERR_PARAMETER_BIND;
            pstmt.setString(1, emp.getFirstName());
            pstmt.setString(2, emp.getLastName());
            pstmt.setString(3, emp.getMobileNumber());
            pstmt.setString(4, emp.getEmail());
            pstmt.setDate(5, java.sql.Date.valueOf(emp.getJoiningDate())); 
            pstmt.setBoolean(6, Boolean.parseBoolean(emp.getActiveStatus())); 
            pstmt.setInt(7, Integer.parseInt(emp.getID()));   
            
            errorCode = ERR_QUERY_EXECUTION;
            int rows = pstmt.executeUpdate();
            if(rows > 0)
            {
            	logger.info("Successfully updated employee data: {}", emp.getID());
            	return rows;
            }
            else
            {
            	logger.error("failed in updating employee data: {}", emp.getID());
            	throw new EmployeeNotFoundException("Employee with ID " + emp.getID() + " not found.");
            }
        } 
        catch (SQLException e) {
        	logger.error("SQL error: {}", e.getMessage());
        	throw new EmployeeDaoException(ERROR_CODES.get(errorCode), errorCode, e);
        }
        
	}
	
	public List<Integer> getAllIds() throws EmployeeDaoException
	{
		List<Integer> result = new ArrayList<>();
		logger.trace("Getting all employee ids..");
		int errorCode = ERR_DB_CONNECTION;
		try (
        		Connection connection = DBConnectionUtil.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(SQL_GET_ALL_IDS)
        		) 
			{
				errorCode = ERR_QUERY_EXECUTION;
				ResultSet rs = pstmt.executeQuery();

			    while (rs.next()) {
			        result.add(rs.getInt(COL_ID)); 
			    }
			return result;
        } catch (SQLException e) {
        	logger.error("SQL error: {}", e.getMessage());
        	throw new EmployeeDaoException(ERROR_CODES.get(errorCode), errorCode, e);
        }
	}

}
