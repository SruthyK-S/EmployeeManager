package com.litmus7.employeemanager.dao;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAO{
	Connection conn = null;
		
	public int insertEmployeeDetailsToDatabase(Employee  emp)
	{
		String sqlQueryToInsertData = "INSERT INTO employee (id, first_name, last_name, mobile, email, joining_date, active_status) VALUES (?, ?, ?, ?, ?, ?, ?);";
		int rows;
		
		try (
				Connection conn = DBConnectionUtil.getConnection();
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
	            conn.close();
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
				Connection conn = DBConnectionUtil.getConnection();
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
				conn.close();
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
				Connection conn = DBConnectionUtil.getConnection();
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
				conn.close();
				return emp;
                	
	        } catch (SQLException e) {
	            return null;
	        }
		
	}
	
	
	public int deleteEmployeeData(int empId) 
	{
		String sqlQueryToDeleteData = "DELETE FROM EMPLOYEE WHERE ID = ?";
		
		try (
				Connection conn = DBConnectionUtil.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sqlQueryToDeleteData)
	        ) {
				pstmt.setDouble(1, empId);
				conn.close();
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
        		Connection conn = DBConnectionUtil.getConnection();
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
            conn.close();
            return result;
        } catch (SQLException e) {
            return 0;
        }
	}

}
