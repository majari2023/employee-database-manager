//********************************************************************
//
// Developer:     Mauricio Rivas
//
// Program #:     Five
//
// File Name:     EmployeeDatabaseHandler
//
// Course:        COSC 4301 Modern Programming
//
// Due Date:      5/12/2025
//
// Instructor:    Prof. Fred Kumi
//
// Java Version:  11
//
// Description:   Handles employee-related database operations such as 
//                adding employees, payroll, bonuses, and search functionality.
//
//********************************************************************

import java.sql.*;
import java.util.*;
import java.time.LocalDate;

public class EmployeeDatabaseHandler {

    private ConnectToOracleDB db;
    private final Scanner scanner;
    private Connection connection;
    
    //***************************************************************
    //
    // Constructor:  EmployeeDatabaseHandler
    //
    // Description:  Initializes scanner and connects to the database.
    //
    // Parameters:   scanner - the Scanner object for user input
    //
    // Returns:      N/A
    //
    //***************************************************************
    
    public EmployeeDatabaseHandler(Scanner scanner) {
        this.scanner = scanner;
        db = new ConnectToOracleDB();
        db.loadDrivers();
        try {
            this.connection = db.connectDriver();
        } catch (Exception e) {
            System.out.println("Failed to connect: " + e.getMessage());
        }
    }
    
    //***************************************************************
    //
    // Method:      addEmployee
    //
    // Description: Adds a new employee record to the EMPLOYEES table.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void addEmployee() {
        try {
            System.out.print("SSN: ");
            String ssn = scanner.nextLine();
            System.out.print("First Name: ");
            String first = scanner.nextLine();
            System.out.print("Last Name: ");
            String last = scanner.nextLine();
            System.out.print("Birthday (YYYY-MM-DD): ");
            String birthday = scanner.nextLine();
            System.out.print("Employee Type: ");
            String type = scanner.nextLine();
            System.out.print("Department: ");
            String dept = scanner.nextLine();

            String sql = "INSERT INTO EMPLOYEES VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ssn);
            ps.setString(2, first);
            ps.setString(3, last);
            ps.setString(4, birthday);
            ps.setString(5, type);
            ps.setString(6, dept);
            ps.executeUpdate();
            System.out.println("Employee added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //***************************************************************
    //
    // Method:      addPayrollInfo
    //
    // Description: Adds payroll information for an employee based on type.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void addPayrollInfo() {
        try {
            System.out.print("SSN: ");
            String ssn = scanner.nextLine();
            System.out.print("Week #: ");
            int week = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Type (salaried, hourly, commission, base, piece): ");
            String type = scanner.nextLine().toLowerCase();

            String sql = "";
            PreparedStatement ps;
            switch (type) {
                case "salaried" -> {
                    System.out.print("Salary: ");
                    double salary = scanner.nextDouble();
                    sql = "INSERT INTO SALARIED_EMPLOYEES VALUES (?, ?, ?, 0)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, ssn);
                    ps.setInt(2, week);
                    ps.setDouble(3, salary);
                }
                case "hourly" -> {
                    System.out.print("Hours worked: ");
                    double hours = scanner.nextDouble();
                    System.out.print("Pay rate: ");
                    double rate = scanner.nextDouble();
                    sql = "INSERT INTO HOURLY_EMPLOYEES VALUES (?, ?, ?, ?, 0)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, ssn);
                    ps.setInt(2, week);
                    ps.setDouble(3, hours);
                    ps.setDouble(4, rate);
                }
                case "commission" -> {
                    System.out.print("Gross Sales: ");
                    double sales = scanner.nextDouble();
                    System.out.print("Commission Rate: ");
                    double rate = scanner.nextDouble();
                    sql = "INSERT INTO COMMISSION_EMPLOYEES VALUES (?, ?, ?, ?, 0)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, ssn);
                    ps.setInt(2, week);
                    ps.setDouble(3, sales);
                    ps.setDouble(4, rate);
                }
                case "base" -> {
                    System.out.print("Base Salary: ");
                    double base = scanner.nextDouble();
                    System.out.print("Gross Sales: ");
                    double sales = scanner.nextDouble();
                    System.out.print("Commission Rate: ");
                    double rate = scanner.nextDouble();
                    sql = "INSERT INTO PLUS_COMMISSION_EMPLOYEES VALUES (?, ?, ?, ?, ?, 0)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, ssn);
                    ps.setInt(2, week);
                    ps.setDouble(3, base);
                    ps.setDouble(4, sales);
                    ps.setDouble(5, rate);
                }
                case "piece" -> {
                    System.out.print("Piece rate: ");
                    double piece = scanner.nextDouble();
                    System.out.print("Pieces made: ");
                    int pieces = scanner.nextInt();
                    sql = "INSERT INTO PIECE_EMPLOYEES VALUES (?, ?, ?, ?, 0)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, ssn);
                    ps.setInt(2, week);
                    ps.setDouble(3, piece);
                    ps.setInt(4, pieces);
                }
                default -> throw new IllegalArgumentException("Invalid employee type");
            }
            ps.executeUpdate();
            System.out.println("Payroll added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //***************************************************************
    //
    // Method:      increaseBaseSalary
    //
    // Description: Increases base salary of all base-plus-commission employees by 15%.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void increaseBaseSalary() {
        try {
            String sql = "UPDATE PLUS_COMMISSION_EMPLOYEES SET Base_Salary = Base_Salary * 1.15";
            PreparedStatement ps = connection.prepareStatement(sql);
            int updated = ps.executeUpdate();
            System.out.println("Updated base salary for " + updated + " records.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //***************************************************************
    //
    // Method:      addBirthdayBonus
    //
    // Description: Adds a $250 bonus to employees whose birthday is in the current month.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void addBirthdayBonus() {
        try {
            int currentMonth = LocalDate.now().getMonthValue();
            String getSSNsSQL = "SELECT SSN FROM EMPLOYEES WHERE EXTRACT(MONTH FROM BIRTHDAY) = ?";
            PreparedStatement getSSNsStmt = connection.prepareStatement(getSSNsSQL);
            getSSNsStmt.setInt(1, currentMonth);
            ResultSet rs = getSSNsStmt.executeQuery();

            List<String> ssns = new ArrayList<>();
            while (rs.next()) {
                ssns.add(rs.getString("SSN"));
            }

            for (String ssn : ssns) {
                updateBonus(connection, ssn, "SALARIED_EMPLOYEES");
                updateBonus(connection, ssn, "HOURLY_EMPLOYEES");
                updateBonus(connection, ssn, "COMMISSION_EMPLOYEES");
                updateBonus(connection, ssn, "PLUS_COMMISSION_EMPLOYEES");
                updateBonus(connection, ssn, "PIECE_EMPLOYEES");
            }

            System.out.println("Birthday bonus applied to eligible employees.");
            
        } catch (Exception e) {
            System.out.println("Error in addBirthdayBonus: " + e.getMessage());
        }
    }
    
    //***************************************************************
    //
    // Method:      updateBonus
    //
    // Description: Helper method to update bonus in a specific employee table.
    //
    // Parameters:  conn - database connection
    //              ssn - social security number of employee
    //              tableName - name of the employee table
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    private void updateBonus(Connection conn, String ssn, String tableName) {
        try {
            String updateSQL = "UPDATE " + tableName +
                               " SET Bonus = NVL(Bonus, 0) + 250 " +
                               "WHERE SSN = ? AND Week_Number = (" +
                               "  SELECT MAX(Week_Number) FROM " + tableName + " WHERE SSN = ?)";
            PreparedStatement stmt = conn.prepareStatement(updateSQL);
            stmt.setString(1, ssn);
            stmt.setString(2, ssn);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // silently ignore
        }
    }

    //***************************************************************
    //
    // Method:      addCommissionBonus
    //
    // Description: Adds a $210 bonus for commission-based employees with sales over $10,500.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void addCommissionBonus() {
        try {
            String[] tables = {"COMMISSION_EMPLOYEES", "PLUS_COMMISSION_EMPLOYEES"};
            for (String table : tables) {
                String sql = "UPDATE " + table + " SET Bonus = NVL(Bonus, 0) + 210 WHERE Gross_Sales > 10500";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.executeUpdate();
            }
            System.out.println("Commission bonuses applied.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //***************************************************************
    //
    // Method:      displayEmployeeInfo
    //
    // Description: Displays all details of a specific employee.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void displayEmployeeInfo() {
        try  {
        	Connection conn = this.connection;
            System.out.print("Enter SSN: ");
            String ssn = scanner.nextLine();

            String sql = "SELECT * FROM EMPLOYEES WHERE SSN = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ssn);
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    System.out.println(meta.getColumnName(i) + ": " + rs.getString(i));
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //***************************************************************
    //
    // Method:      searchByLastName
    //
    // Description: Searches for employees by exact last name.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void searchByLastName() {
        try  {
        	Connection conn = this.connection;
            System.out.print("Enter last name: ");
            String last = scanner.nextLine();

            String sql = "SELECT * FROM EMPLOYEES WHERE LOWER(Last_Name) = LOWER(?) ORDER BY First_Name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, last);
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    System.out.println(meta.getColumnName(i) + ": " + rs.getString(i));
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //***************************************************************
    //
    // Method:      searchByBirthMonth
    //
    // Description: Lists all employees whose birthday is in a given month.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void searchByBirthMonth() {
        try {
            System.out.print("Enter birth month number (1-12): ");
            int month = scanner.nextInt();
            String sql = "SELECT * FROM EMPLOYEES WHERE EXTRACT(MONTH FROM Birthday) = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("First_Name") + " " + rs.getString("Last_Name"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //***************************************************************
    //
    // Method:      searchByLastNameRange
    //
    // Description: Lists all employees whose last name falls between two values.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void searchByLastNameRange() {
        try {
            System.out.print("Enter first last name (start of range): ");
            String start = scanner.nextLine();
            System.out.print("Enter second last name (end of range): ");
            String end = scanner.nextLine();

            String sql = """
                SELECT * FROM EMPLOYEES 
                WHERE LOWER(Last_Name) BETWEEN LOWER(?) AND LOWER(?) 
                ORDER BY Last_Name
            """;

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, start);
            ps.setString(2, end);
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    System.out.println(meta.getColumnName(i) + ": " + rs.getString(i));
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //***************************************************************
    //
    // Method:      closeConnection
    //
    // Description: Closes the database connection if open.
    //
    // Parameters:  None
    //
    // Returns:     N/A
    //
    //***************************************************************
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
