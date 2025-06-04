# employee-database-manager
Java program that connects to an Oracle database and manages employee records, payroll data, bonuses, and queries using JDBC and a console interface.
This Java project connects to an Oracle database and allows users to manage employee records through a command-line interface. Features include adding employees, managing payroll, assigning bonuses, and searching employee records.

## 📌 Features

- Connects to local or ACC Oracle databases using JDBC
- Add employees and their payroll information
- Apply birthday or commission bonuses
- Search and display employee details by name or birth month

## 🧪 Files Overview

- `ConnectToOracleDB.java`: Handles JDBC connection logic
- `EmployeeDatabaseHandler.java`: Manages CRUD operations
- `Program5Controller.java`: Handles the user interface menu logic
- `Program5.java`: Main application entry point

- --- Employee Database Menu ---
1. Add an employee
2. Add payroll information
3. Increase base salary by 15%
4. Add $250 birthday bonus
5. Add $210 commission bonus
6. Display employee info
7. Search by last name
8. Search by birth month
9. Search by last name range
10. Exit
