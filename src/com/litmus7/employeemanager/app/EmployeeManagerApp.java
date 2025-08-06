package com.litmus7.employeemanager.app;
import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Response;

import java.util.Scanner;
import java.io.IOException;


public class EmployeeManagerApp {

	public static void main(String[] args) 
	{
		
		final String inputFilePath = "employees.txt";
		final String outputFilePath = "employees.csv";
		Scanner scn = new Scanner(System.in);  
		EmployeeController controller = null;
		try 
		{
			controller = new EmployeeController(inputFilePath, outputFilePath);
		} catch (IOException e) {
			System.out.println("Invalid file path!");
		}
		
		String option;
		System.out.println("Employee Management App");
		boolean exit = false;
		
		while(!exit)
		{
			System.out.println("\nChoose one service");
			System.out.println("1 - Read data from text file"
					+ "\n2 - Add the read data to database"
					+ "\n3 - Enter one employee data"
					+ "\n4 - Get all employee data"
					+ "\n5 - Get one employee data"
					+ "\n6 - Delete one employee data"
					+ "\n7 - Update one employee data"
					+ "\n8 - Quit"
					);
			
		    option = scn.next();  
		    switch(option) {
		    
		    
			    case "1":
			    {

			    	String result = controller.getDataFromTextFile();
			    	System.out.println(result);
			    	break;
			    	
			    }
			    case "2": 
			    {
			    	Response<Integer> result = controller.writeDataToCSV();
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

			    	
			    	Response<Integer> result = controller.getSingleDataFromUser(ID, firstName, lastName, mobile,
			    										  email, date, activeStatus);
			    	
		    		System.out.println(result.getMessage());
		    		break;
		    	}
			    case "4":
			    {
			    	Response<String> result = controller.getAllEmployees();
			    	if(result.isSuccess())
			    		System.out.println(result.getData());
			    	else
			    		System.out.println(result.getMessage());
			    	break;
			    }
			    case "5":
			    {
			    	System.out.println("Enter the ID: ");
			    	int ID = scn.nextInt();
			    	Response<String> result = controller.getEmployeeById(ID);
			    	if(result.isSuccess())
			    		System.out.println(result.getData());
			    	else
			    		System.out.println(result.getMessage());
			    	break;
			    }
			    case "6":
			    {
			    	System.out.println("Enter the ID of employee to be deleted: ");
			    	int ID = scn.nextInt();
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

			    	
			    	Response<Integer> result = controller.updateEmployee(ID, firstName, lastName, mobile,
			    										  email, date, activeStatus);
			    	
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
