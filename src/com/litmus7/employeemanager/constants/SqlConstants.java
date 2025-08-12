package com.litmus7.employeemanager.constants;

public class SqlConstants {
	public static final String COL_ID = "id";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_EMAIL = "email";
    public static final String COL_JOINING_DATE = "joining_date";
    public static final String COL_ACTIVE_STATUS = "active_status";


    public static final String SQL_INSERT_EMPLOYEE =
        "INSERT INTO employee (" + COL_ID + ", " + COL_FIRST_NAME + ", " + COL_LAST_NAME + ", " +
        COL_MOBILE + ", " + COL_EMAIL + ", " + COL_JOINING_DATE + ", " + COL_ACTIVE_STATUS + 
        ") VALUES (?, ?, ?, ?, ?, ?, ?);";

    public static final String SQL_GET_ALL_EMPLOYEES =
        "SELECT " + COL_ID + ", " + COL_FIRST_NAME + ", " + COL_LAST_NAME + ", " + 
        COL_MOBILE + ", " + COL_EMAIL + ", " + COL_JOINING_DATE + ", " + COL_ACTIVE_STATUS + 
        " FROM employee;";

    public static final String SQL_GET_AN_EMPLOYEE =
        "SELECT " + COL_ID + ", " + COL_FIRST_NAME + ", " + COL_LAST_NAME + ", " +
        COL_MOBILE + ", " + COL_EMAIL + ", " + COL_JOINING_DATE + ", " + COL_ACTIVE_STATUS +
        " FROM employee WHERE " + COL_ID + " = ?;";

    public static final String SQL_DELETE_EMPLOYEE =
        "DELETE FROM employee WHERE " + COL_ID + " = ?";

    public static final String SQL_UPDATE_EMPLOYEE =
        "UPDATE employee SET " + COL_FIRST_NAME + " = ?, " + COL_LAST_NAME + " = ?, " +
        COL_MOBILE + " = ?, " + COL_EMAIL + " = ?, " + COL_JOINING_DATE + " = ?, " +
        COL_ACTIVE_STATUS + " = ? WHERE " + COL_ID + " = ?";

    public static final String SQL_GET_ALL_IDS =
        "SELECT " + COL_ID + " FROM employee";
}