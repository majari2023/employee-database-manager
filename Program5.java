//********************************************************************
//
// Developer:     Mauricio Rivas
//
// Program #:     Five
//
// File Name:     Program5
//
// Course:        COSC 4301 Modern Programming
//
// Due Date:      5/12/2025
//
// Instructor:    Prof. Fred Kumi
//
// Java Version:  11
//
// Description:   Entry point for Program 5. It launches the controller
//                to handle all user interactions and database operations.
//                All processing is delegated to Program5Controller.
//
//********************************************************************

public class Program5 {

	//***************************************************************
    //
    // Method:      main
    //
    // Description: The main method of the program. It creates an
    //              instance of the controller and runs the application.
    //
    // Parameters:  String[] args - command-line arguments (not used)
    //
    // Returns:     None
    //
    //***************************************************************
	
    public static void main(String[] args) {
        Program5Controller controller = new Program5Controller();
        controller.run();
        DeveloperInfo();
    }
    
    //***************************************************************
    //
    // Method:      DeveloperInfo
    //
    // Description: Displays developer's information.
    //
    // Parameters:  None
    //
    // Returns:     None
    //
    //***************************************************************
    
    public static void DeveloperInfo() {
    	System.out.println("Name:     Mauricio Rivas");
        System.out.println("Course:   COSC 4301 - Modern Programming");
        System.out.println("Program:  Five");
        System.out.println("Due Date: 5/12/2025\n");
    }
}