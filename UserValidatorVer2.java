package com.hubgitalvin.wk5;

/*
 * Assignment #3
 * User Validation with CSV File
 * 
 * Basic Requirements / Functions: 
 * 
 * The goal for this assignment will be to mimic a user login from a Java 
 * console application.
 * 
 * The program will prompt a user for a username and a password, and then 
 * use the inputs you receive to validate whether or not the username / 
 * password combination is valid.
 * 
 * In order to validate this username / password combination, the program 
 * will need to read this information from a file (called "data.txt") and 
 * import the data into your Java application.
 * 
 * Possible Program Flow: 
 * 
 * - Set up internal data
 * - Get user input
 * - Validate input by comparing stored data with user input
 * - Good result:  Allow "entrance" or show success
 * - Bad result:  Try again, max 5 attempts or go to fail message
 * 
 * Possible Parts: 
 * 
 * - Highly suggested POJO for representing the user data 
 * - separate method for reading and storing data from file 
 * - separate method for collecting user input 
 * - separate method for comparing user input to stored data 
 */

import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.util.Scanner;

public class UserValidatorVer2 {

	static User[] userArray = new User[4]; 
	
	// this block of variables deal with reading and splitting up a line from the file 
	static String tmpLine; 
	static String lineUsername; 
	static String linePassword; 
	static String lineName; 
	
	// this block of variables deal with collecting input from the user 
	static Scanner userInput = new Scanner(System.in); 
	static String inUsername = ""; 
	static String inPassword = ""; 

	// this block of variables deal with the results of going through
	static String goodName = ""; 
	static int    hiddenCount = 0; 
	static boolean doesNameAndPasswordMatch = false; // flag for knowing ultimately if there really is a match; avoiding implicit logic so I don't accidentally hide things from myself 

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		
		// initialize the users array 
		for (int k = 0; k < 4; k ++) { 
			userArray[k] = makeUser ( "username" + k, "password" + k, "name" + k ); 
		}

		// for checking the initial content of the array 

		// now checking contents of array 
		System.out.println("Before reading \"data.txt\" file"); 
		for (int m = 0; m < 4; m ++) { 
			System.out.println("username #" + m + ":" + userArray[m].getUsername()); 
			System.out.println("password #" + m + ":" + userArray[m].getPassword()); 
			System.out.println("name     #" + m + ":" + userArray[m].getName()); 
		} 

		
		// now set up the list from the file 
		createList(); 
		
		// now checking contents of array 
		System.out.println("\nAfter reading \"data.txt\" file"); 
		for (int n = 0; n < 4; n ++) { 
			System.out.println("username #" + n + ":" + userArray[n].getUsername()); 
			System.out.println("password #" + n + ":" + userArray[n].getPassword()); 
			System.out.println("name     #" + n + ":" + userArray[n].getName()); 
		} 
		
		doesNameAndPasswordMatch = collectUserInput(); 

		if (doesNameAndPasswordMatch) { 
			displaySuccess(); 
		} else { 
			displayFailure(); 
		} 
		
	}

	public static User makeUser (
			String newUsername, 
			String newPassword, 
			String newName ) { 
		
		User tmpUser = new User(); 
		
		tmpUser.setUsername(newUsername); 
		tmpUser.setPassword(newPassword); 
		tmpUser.setName(newName); 
		
		return tmpUser; 
	} 
	
	// utility method to get String arrays 
	public static String[] parseLine(String input) {

		String outStringArray[] = input.split(","); 
		
		return outStringArray; 

	}
	
	public static void createList () { 
		
		BufferedReader fileReader = null; 
		String[] tmpStringArray   = new String[3]; 
		String dataFileName       = "data.txt"; 
		String curLine            = "A-A-Ron";     // just need to populate the variable with something non-null to allow entry into the while loop 
		int userListLocation      = 0;             // for knowing which array location to insert the new object in 
		
		try { 
			
			// the following line needs data.txt file in the same file location as this source Java file
			fileReader = new BufferedReader( new FileReader(dataFileName)); 
			
			while ( ( curLine = fileReader.readLine() ) != null) { 
				
				tmpStringArray = parseLine(curLine); 

				userArray[userListLocation].setUsername(tmpStringArray[0]); 
				userArray[userListLocation].setPassword(tmpStringArray[1]); 
				userArray[userListLocation].setName(tmpStringArray[2]); 
				
				userListLocation ++; 
				
			} 

			// System.out.println("Closing file reader."); 
			fileReader.close(); 
		
		} catch (FileNotFoundException e) { 
		
			System.out.println("Oops!  The file \"" + dataFileName + "\" wasn't found!"); 
			e.printStackTrace(); 

		}  catch (IOException e) { 
			
			e.printStackTrace(); 			

		} finally { 
			
			try { 
							
				//System.out.println("Closing file reader."); 
				fileReader.close(); 
				
			} catch (IOException e) { 
				e.printStackTrace(); 
			}
			
		} 

	} 
	
	// method for checking current input with current username and password 
	public static boolean doesItMatch ( 
			String uiUserName, 
			String uiPassword, 
			String storedUserName, 
			String storedPassword ) { 
		
		boolean goodMatch = false; 
		
		if ( (uiUserName.equals(storedUserName)) && (uiPassword.equals(storedPassword)) ) { 
			goodMatch = true; 
		}
		
		return goodMatch; 
	}
	
	// method for collecting input from UI 
	public static boolean collectUserInput() { 
		
		String inUserName = ""; 
		String inPassword = ""; 
		
		int maxTries = 5; 
		boolean doUserNamesAndPasswordsMatch = false; 
		
		for (int curTry = 1; curTry <= maxTries; curTry ++) { 
			
			if (doUserNamesAndPasswordsMatch) break; 
			
			System.out.print("Please enter your Username: "); 
			inUserName = userInput.nextLine(); 
			
			System.out.print("Now enter your Password   : "); 
			inPassword = userInput.nextLine(); 
			
			for (User checkUser : userArray) { 
				
				if (doUserNamesAndPasswordsMatch) break; 
				
				doUserNamesAndPasswordsMatch = doesItMatch(
						inUserName, 
						inPassword, 
						checkUser.getUsername(), 
						checkUser.getPassword()); 
				
				goodName = checkUser.getName(); 
						
			}
			
		}
		
		return doUserNamesAndPasswordsMatch; 
	} 
	
	public static void displaySuccess() { 
		System.out.println("Welcome " + goodName);
	}
	
	public static void displayFailure() { 
		System.out.println("Too many failed login attempts, you are now locked out.");	
	}
	
}
