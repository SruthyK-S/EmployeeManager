package com.litmus7.employeemanager.constants;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeConstants {

	public static final Map<Integer, String> ERROR_CODES = new HashMap<>();

    static {
        ERROR_CODES.put(404, "Database connection error");
        ERROR_CODES.put(102, "SQL execution error");
        ERROR_CODES.put(103, "Invalid input data format");
        ERROR_CODES.put(104, "Duplicate employee ID");
        ERROR_CODES.put(201, "Validation Error");
        ERROR_CODES.put(202, "Error processing retrieved data");
    }
}