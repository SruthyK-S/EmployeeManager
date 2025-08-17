package com.litmus7.employeemanager.app;
import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;

import java.util.Scanner;


public class EmployeeManagerApp {

	public static void main(String[] args) 
	{
		
		Scanner scn = new Scanner(System.in);  
		EmployeeController employeeController = new EmployeeController();
		
		String option;
		System.out.println("Employee Management App");
		boolean exit = false;
		
		while(!exit)
		{
			System.out.println("\nChoose one service");
			System.out.println(	  "\n1 - Create an employee"
								+ "\n2 - Get all employee data"
								+ "\n3 - Get an employee data"
								+ "\n4 - Delete an employee data"
								+ "\n5 - Update an employee data"
								+ "\n6 - Quit"
								);
			
		    option = scn.next();  
		    switch(option) {
		    
		    
			    case "1":
			    {
			    	System.out.println("Enter ID: ");
			    	String ID = scn.next();
			    	Response<Void> idValid = employeeController.isDuplicateId(ID);
			    	if(idValid.getStatus() == 200)
			    	{
			    		System.out.println(idValid.getMessage());
			    		break;
			    	}
			    	
			    	System.out.println("Enter the employee details: ");
			    	System.out.println("First Name : ");
			    	String firstName = scn.next();
			    
			    	
			    	System.out.println("Last Name : ");
			    	String lastName = scn.next();
			    	
			    	System.out.println("Mobile Number : ");
			    	String mobile = scn.next();
			    	
			    	System.out.println("Email address : ");
			    	String email = scn.next();
			    	
			    	System.out.println("Joining date(yyyy-MM-dd) : ");
			    	String date = scn.next();

			        
			    	
			    	System.out.println("Active Status(true/false) : ");
			    	String activeStatus = scn.next();

			    	Employee emp = new Employee(ID, firstName, lastName, mobile, email, date, activeStatus);
			    	Response<Integer> result = employeeController.createEmployee(emp);
			    	
		    		System.out.println(result.getMessage());
		    		break;
			    	
			    }
			    case "2": 
			    {
			    	Response<String> result = employeeController.getAllEmployees();
			    	if(result.getStatus() == 200)
			    		System.out.println(result.getData());
			    	else
			    		System.out.println(result.getMessage());
			    	break;
			    }
			    case "3":
		    	{
		    		System.out.println("Enter the ID: ");
			    	String ID = scn.next();
			    	Response<String> result = employeeController.getEmployeeById(ID);
			    	if(result.getStatus() == 200)
			    		System.out.println(result.getData());
			    	else
			    		System.out.println(result.getMessage());
			    	break;
		    	}
			    case "4":
			    {
			    	System.out.println("Enter the ID of employee to be deleted: ");
			    	String ID = scn.next();
			    	Response<Integer> result = employeeController.deleteEmployeeById(ID);
			    	System.out.println(result.getMessage());	
			    	break;
			    }
			    case "5":
			    {
			    	
			    	System.out.println("Enter ID of employee to be updated: ");
			    	String ID = scn.next();
			    	Response<Void> idValid = employeeController.isDuplicateId(ID);
			    	if(idValid.getStatus() != 200)
			    	{
			    		System.out.println(idValid.getMessage());
			    		break;
			    	}
			    	System.out.println("Enter the employee details: ");
			    	System.out.println("First Name : ");
			    	String firstName = scn.next();
			    
			    	
			    	System.out.println("Last Name : ");
			    	String lastName = scn.next();
			    	
			    	System.out.println("Mobile Number : ");
			    	String mobile = scn.next();
			    	
			    	System.out.println("Email address : ");
			    	String email = scn.next();
			    	
			    	System.out.println("Joining date(yyyy-MM-dd) : ");
			    	String date = scn.next();

			        
			    	
			    	System.out.println("Active Status(true/false) : ");
			    	String activeStatus = scn.next();
			    	
			    	Employee emp = new Employee(ID, firstName, lastName, mobile, email, date, activeStatus);
			    	
			    	Response<Integer> result = employeeController.updateEmployee(emp);
			    	
			    	System.out.println(result.getMessage());	
			    	break;
			    }
			    default: 
			    {
			    	System.out.println("Exited the application");
			    	exit = true;
			    }
		    }
		    
		}
		
		
		scn.close();	    
		    
		}
	    
}
