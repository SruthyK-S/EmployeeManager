package com.litmus7.employeemanager.exceptions;

public class EmployeeNotFoundException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(String message) {
        super(message);
    }
}
