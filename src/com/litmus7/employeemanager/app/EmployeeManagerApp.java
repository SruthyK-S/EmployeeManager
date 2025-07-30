package com.litmus7.employeemanager.app;
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;

public class EmployeeManagerApp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		final String inputFilePath = "employees.txt";
		final String outputFilePath = "employees.csv";
		Scanner scn = new Scanner(System.in);  
		EmployeeController empCon = new EmployeeController(inputFilePath, outputFilePath);
		
		
		String option = "";
		String result = "";
		System.out.println("Employee Management App");
		
		while(!option.equals("q") && !option.equals("Q"))
		{
			System.out.println("Choose one service");
			System.out.println("1 - Read data from text file and add it to database"
					+ "\n2 - Enter data from console"
					+ "\n3 - Load data from output File"
					+ "\n'q' - quit");
			
		    option = scn.next();  
		    switch(option) {
		    
		    
			    case "1":
			    {
	//		    	System.out.println("");
			    	result = empCon.dataIngestion();
			    	System.out.println(result);
			    	break;
			    	
			    }
			    case "2":
		    	{
			    	System.out.println("Enter the employee details seperated by comma");
			    	System.out.println("ID : ");
			    	int ID = scn.nextInt();
			    	
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
			    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			    	LocalDate joiningDate = LocalDate.parse(date, formatter);
			        
			    	
			    	System.out.println("Active Status(T/F) : ");
			    	String activeStatus = scn.next();
			    	boolean active = activeStatus.equals("T")? true : false;
			    	
			    	
			    	Employee emp = new Employee(ID, firstName, lastName, mobile, email, joiningDate, active);
		    		result = empCon.dataEntry(emp, outputFilePath);
		    		System.out.println(result);
		    		break;
		    	}
			    case "3": 
			    {
			    	result = empCon.loadData();
			    	System.out.println(result);
			    	break;
			    }
			    default: 
			    {
			    	System.out.println("Exited the application");
			    }
		    }
		    
		}
		
		
		scn.close();	    
		    
		}
	    
}
