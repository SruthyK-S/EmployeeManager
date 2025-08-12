package com.litmus7.employeemanager.dao;
import com.litmus7.employeemanager.constants.SqlConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exceptions.EmployeeDaoException;
import com.litmus7.employeemanager.exceptions.EmployeeNotFoundException;
import com.litmus7.employeemanager.util.DBConnectionUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAO{
	Connection conn = null;
		
	public int saveEmployee(Employee  emp) throws EmployeeDaoException 
	{
		
		try (
				Connection conn = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SQL_INSERT_EMPLOYEE)
	        ) {
				pstmt.setInt(1, Integer.parseInt(emp.getID()));   
				pstmt.setString(2, emp.getFirstName());                     
				pstmt.setString(3, emp.getLastName());                      
				pstmt.setString(4, emp.getMobileNumber());                  
				pstmt.setString(5, emp.getEmail());                         
				pstmt.setDate(6, java.sql.Date.valueOf(emp.getJoiningDate())); 
				pstmt.setBoolean(7, Boolean.parseBoolean(emp.getActiveStatus())); 

	            return pstmt.executeUpdate();
	            
	        } catch (SQLException e) {
	            throw new EmployeeDaoException("Database error while saving employee", e);
	        } 
			
	}
	
	
	public List<Employee> getAllEmployees() throws EmployeeDaoException 
	{
		List<Employee> employees = new ArrayList<Employee>();
		ResultSet myRs = null;
		
		try (
				Connection conn = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SQL_GET_ALL_EMPLOYEES)
	        ) {
				myRs = pstmt.executeQuery();
				while (myRs.next())
				{
					Employee emp = new Employee(myRs.getString(SqlConstants.COL_ID), myRs.getString(SqlConstants.COL_FIRST_NAME), 
												myRs.getString(SqlConstants.COL_LAST_NAME), myRs.getString(SqlConstants.COL_MOBILE), 
												myRs.getString(SqlConstants.COL_EMAIL), myRs.getString(SqlConstants.COL_JOINING_DATE),
												myRs.getString(SqlConstants.COL_ACTIVE_STATUS));
					employees.add(emp);
				}
			
				return employees;
					
	        } catch (SQLException e) {
	            throw new EmployeeDaoException("Database error while fetching employee.", e);
	        } 
			finally {
				try { if (myRs != null) myRs.close(); } catch (SQLException ignored) {}
		}
		
	}
	
	
	public Employee getEmployeeById(int empId) throws EmployeeDaoException, EmployeeNotFoundException
	{	
		ResultSet myRs = null;
		try (
				Connection conn = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SQL_GET_AN_EMPLOYEE)
	        ) {
				pstmt.setInt(1, empId);
				myRs = pstmt.executeQuery();
				if (myRs.next())

					return new Employee(myRs.getString("id"), myRs.getString("first_name"), myRs.getString("last_name"), 
							myRs.getString("mobile"), myRs.getString("email"), myRs.getString("joining_date"),
							myRs.getString("active_status"));
				else {
	                throw new EmployeeNotFoundException("Employee with ID " + empId + " not found.");
	            }

	        } catch (SQLException e) {
	            throw new EmployeeDaoException("Database error while fetching employee.", e);
	        } 
			finally {
	            try { if (myRs != null) myRs.close(); } catch (SQLException ignored) {}
			}
	}
	
	
	public int deleteEmployeeData(int empId) throws EmployeeDaoException, EmployeeNotFoundException
	{
		
		
		try (
				Connection conn = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SQL_DELETE_EMPLOYEE)
	        ) {
				pstmt.setDouble(1, empId);
				int rows =  pstmt.executeUpdate();
	            if(rows > 0)
	            	return rows;
	            else
	            	throw new EmployeeNotFoundException("Employee with ID " + empId + " not found.");
	            	
	        } catch (SQLException e) {
	            throw new EmployeeDaoException("Database error while fetching employee.", e);
	        } 
	}
	
	
	public int updateEmployee(Employee emp) throws EmployeeDaoException, EmployeeNotFoundException
	{
		

        try (
        		Connection conn = DBConnectionUtil.getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SQL_UPDATE_EMPLOYEE)
        		) {
            pstmt.setString(1, emp.getFirstName());
            pstmt.setString(2, emp.getLastName());
            pstmt.setString(3, emp.getMobileNumber());
            pstmt.setString(4, emp.getEmail());
            pstmt.setDate(5, java.sql.Date.valueOf(emp.getJoiningDate())); 
            pstmt.setBoolean(6, Boolean.parseBoolean(emp.getActiveStatus())); 
            pstmt.setInt(7, Integer.parseInt(emp.getID()));   
          
            int rows = pstmt.executeUpdate();
            if(rows > 0)
            	return rows;
            else
            	throw new EmployeeNotFoundException("Employee with ID " + emp.getID() + " not found.");
        	} 
        catch (SQLException e) {
            throw new EmployeeDaoException("Database error while fetching employee.", e);
        } 
        
	}
	
	public List<Integer> getAllIds()
	{
		List<Integer> result = new ArrayList<>();
		try (
        		Connection conn = DBConnectionUtil.getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SQL_GET_ALL_IDS)
        		) 
			{
				ResultSet rs = pstmt.executeQuery();

			    while (rs.next()) {
			        result.add(rs.getInt(SqlConstants.COL_ID)); 
			    }
			return result;
        } catch (SQLException e) {
            return null;
        }
	}

}
