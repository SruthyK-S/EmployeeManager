package com.litmus7.employeemanager.app;
import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;

import java.util.Scanner;


public class EmployeeManagerApp {

	public static void main(String[] args) 
	{
		
		final String inputFilePath = "employees.txt";
		Scanner scn = new Scanner(System.in);  
		EmployeeController controller = new EmployeeController();
		
		String option;
		System.out.println("Employee Management App");
		boolean exit = false;
		
		while(!exit)
		{
			System.out.println("\nChoose one service");
			System.out.println("1 - Read data from text file"
					+ "\n2 - Add the read data to database"
					+ "\n3 - Create an employee"
					+ "\n4 - Get all employee data"
					+ "\n5 - Get an employee data"
					+ "\n6 - Delete an employee data"
					+ "\n7 - Update an employee data"
					+ "\n8 - Quit"
					);
			
		    option = scn.next();  
		    switch(option) {
		    
		    
			    case "1":
			    {

			    	Response<String> result = controller.getDataFromTextFile(inputFilePath);
			    	if(result.getStatus() != 200)
			    		System.out.println(result.getMessage());
			    	else
			    		System.out.println(result.getData());
			    	break;
			    	
			    }
			    case "2": 
			    {
			    	Response<Integer> result = controller.writeDataToDatabase();
			    	System.out.println(result.getMessage());
			    	break;
			    }
			    case "3":
		    	{
			    	System.out.println("Enter the employee details: ");
			    	System.out.println("ID : ");
			    	String ID = scn.next();
			    	
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
			    	Response<Integer> result = controller.createEmployee(emp);
			    	
		    		System.out.println(result.getMessage());
		    		break;
		    	}
			    case "4":
			    {
			    	Response<String> result = controller.getAllEmployees();
			    	if(result.getStatus() == 200)
			    		System.out.println(result.getData());
			    	else
			    		System.out.println(result.getMessage());
			    	break;
			    }
			    case "5":
			    {
			    	System.out.println("Enter the ID: ");
			    	String ID = scn.next();
			    	Response<String> result = controller.getEmployeeById(ID);
			    	if(result.getStatus() == 200)
			    		System.out.println(result.getData());
			    	else
			    		System.out.println(result.getMessage());
			    	break;
			    }
			    case "6":
			    {
			    	System.out.println("Enter the ID of employee to be deleted: ");
			    	String ID = scn.next();
			    	Response<Integer> result = controller.deleteEmployeeById(ID);
			    	System.out.println(result.getMessage());	
			    	break;
			    	
			    }
			    case "7":
			    {
			    	System.out.println("Enter the employee details: ");
			    	System.out.println("ID : ");
			    	String ID = scn.next();
			    	
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
			    	
			    	Response<Integer> result = controller.updateEmployee(emp);
			    	
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
