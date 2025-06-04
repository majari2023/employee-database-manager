
//********************************************************************
//
// Developer:     Mauricio Rivas
//
// Program #:     Five
//
// File Name:     Program5Controller
//
// Course:        COSC 4301 Modern Programming
//
// Due Date:      5/12/2025
//
// Instructor:    Prof. Fred Kumi
//
// Java Version:  11
//
// Description:   Handles the user interface logic and routes user
//                selections to appropriate methods in the
//                EmployeeDatabaseHandler class.
//
//********************************************************************

import java.util.Scanner;

public class Program5Controller {

    private final Scanner scanner;
    private final EmployeeDatabaseHandler dbHandler;

    //***************************************************************
    //
    // Constructor:  Program5Controller
    //
    // Description:  Initializes the Scanner and database handler.
    //
    // Parameters:   None
    //
    // Returns:      N/A
    //
    //***************************************************************
    
    public Program5Controller() {
        scanner = new Scanner(System.in);
        dbHandler = new EmployeeDatabaseHandler(scanner);
    }

    //***************************************************************
    //
    // Method:      run
    //
    // Description: Main loop to display menu, get user choice,
    //              and invoke the corresponding operation.
    //
    // Parameters:  None
    //
    // Returns:     None
    //
    //***************************************************************
    
    public void run() {
        int choice;

        do {
            displayMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1 -> dbHandler.addEmployee();
                case 2 -> dbHandler.addPayrollInfo();
                case 3 -> dbHandler.increaseBaseSalary();
                case 4 -> dbHandler.addBirthdayBonus();
                case 5 -> dbHandler.addCommissionBonus();
                case 6 -> dbHandler.displayEmployeeInfo();
                case 7 -> dbHandler.searchByLastName();
                case 8 -> dbHandler.searchByBirthMonth();
                case 9 -> dbHandler.searchByLastNameRange();
                case 10 -> {
                    dbHandler.closeConnection();
                    System.out.println("Exiting program. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 10);
    }

    //***************************************************************
    //
    // Method:      displayMenu
    //
    // Description: Displays the main menu of options for the user.
    //
    // Parameters:  None
    //
    // Returns:     None
    //
    //***************************************************************
    
    private void displayMenu() {
        System.out.println("""
            \n--- Employee Database Menu ---
            1. Add an employee to the employee table.
            2. Add payroll information for an employee.
            3. Increase base salary by 15% for all base-plus-commission employees.
            4. If the employee’s birthday is in the current month, add a $250.00 bonus.
            5. For all commission employees with gross sales over $10,500.00, add a $210.00 bonus.
            6. Display information about an employee.
            7. Display the records matching a last name sorted by first name.
            8. Display the records of all employees whose birthdays are in a given month.
            9. Display the records of all employees between two given last names.
            10. Exit
        """);
    }

    //***************************************************************
    //
    // Method:      getUserChoice
    //
    // Description: Prompts the user to enter a menu option and
    //              validates that the input is an integer.
    //
    // Parameters:  None
    //
    // Returns:     int - the validated menu choice
    //
    //***************************************************************
    
    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        return choice;
    }
}