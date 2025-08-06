package com.litmus7.employeemanager.dao;
import com.litmus7.employeemanager.dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAO{
	
	private static final String URL = "jdbc:mysql://localhost:3306/employeedatabase";
	private static final String USER = "";
	private static final String PASSWORD = "";
	Statement myStmt = null;
	PreparedStatement preStmt = null ;
	StringBuilder result = new StringBuilder();
	Connection conn;
	
	public EmployeeDAO()
	{
		try 
		{
			this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertEmployeeDetailsToDatabase(Employee  emp)
	{
		String sqlQueryToInsertData = "INSERT INTO employee (id, first_name, last_name, mobile, email, joining_date, active_status) VALUES (?, ?, ?, ?, ?, ?, ?);";
		int rows;
		
		try (
	            PreparedStatement pstmt = conn.prepareStatement(sqlQueryToInsertData)
	        ) {
				pstmt.setInt(1, Integer.parseInt(emp.getID()));   
				pstmt.setString(2, emp.getFirstName());                     
				pstmt.setString(3, emp.getLastName());                      
				pstmt.setString(4, emp.getMobileNumber());                  
				pstmt.setString(5, emp.getEmail());                         
				pstmt.setDate(6, java.sql.Date.valueOf(emp.getJoiningDate())); 
				pstmt.setBoolean(7, Boolean.parseBoolean(emp.getActiveStatus())); 

	            rows = pstmt.executeUpdate();
	            return rows;
	            
	        } catch (SQLException e) {
	            return 0;
	        }
	}
	
	
	public List<Employee> getAllEmployeeData() 
	{
		List<Employee> employees = new ArrayList<>();
		String sqlQueryToGetAllEmployee = "select id, first_name, last_name, mobile, email, joining_date, active_status from employee;";
		try (
	            PreparedStatement pstmt = conn.prepareStatement(sqlQueryToGetAllEmployee)
	        ) {
				ResultSet myRs = pstmt.executeQuery();
				while (myRs.next())
				{
					Employee emp = new Employee(myRs.getString("id"), myRs.getString("first_name"), myRs.getString("last_name"), 
												myRs.getString("mobile"), myRs.getString("email"), myRs.getString("joining_date"),
												myRs.getString("active_status"));
					employees.add(emp);
				}
				return employees;
					
	        } catch (SQLException e) {
	            return null;
	        }
		
	}
	
	
	public Employee getEmployeeData(int empId)
	{	

		String sqlQueryToGetAnEmployee = "select id, first_name, last_name, mobile, email, joining_date, active_status from employee WHERE ID = ?;";
		Employee emp = null;
		try (
	            PreparedStatement pstmt = conn.prepareStatement(sqlQueryToGetAnEmployee)
	        ) {
				pstmt.setInt(1, empId);
				ResultSet myRs = pstmt.executeQuery();
				while (myRs.next())
				{
					emp = new Employee(myRs.getString("id"), myRs.getString("first_name"), myRs.getString("last_name"), 
							myRs.getString("mobile"), myRs.getString("email"), myRs.getString("joining_date"),
							myRs.getString("active_status"));
				}
					
				return emp;
                	
	        } catch (SQLException e) {
	            return null;
	        }
		
	}
	
	
	public int deleteEmployeeData(int empId) 
	{
		String sqlQueryToDeleteData = "DELETE FROM EMPLOYEE WHERE ID = ?";
		
		try (
	            PreparedStatement pstmt = conn.prepareStatement(sqlQueryToDeleteData)
	        ) {
				pstmt.setDouble(1, empId);
	            return pstmt.executeUpdate();
	        } catch (SQLException e) {

	            return 0;
	        }
		
	}
	
	
	public int updateEmployee(Employee emp) 
	{
		String sqlQueryToUpdate = "UPDATE employee SET first_name = ?, last_name = ?, mobile = ?, email = ?, "
									+ "joining_date = ?, active_status = ? WHERE id = ?";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sqlQueryToUpdate)
        ) {
            pstmt.setString(1, emp.getFirstName());
            pstmt.setString(2, emp.getLastName());
            pstmt.setString(3, emp.getMobileNumber());
            pstmt.setString(4, emp.getEmail());
            pstmt.setDate(5, java.sql.Date.valueOf(emp.getJoiningDate())); 
            pstmt.setBoolean(6, Boolean.parseBoolean(emp.getActiveStatus())); 
            pstmt.setInt(7, Integer.parseInt(emp.getID()));   
          
            int result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
	}

}
