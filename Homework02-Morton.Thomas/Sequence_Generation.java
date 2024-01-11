//Thomas Morton
//A11353689
//Dr. HK Dai - CS 3353-24287

import java.util.Scanner;

public class Sequence_Generation {

	//Requires an input of a linked list containing at least one element for the first arg
	//and 3 additional linked lists containing any number of elements for the following args
	//last 3 linked lists are cleared before operation, so their contents are irrelevant before and during the method call
	public static void fillL2L3L5(LinkedList tempList, LinkedList twosList, LinkedList threesList, LinkedList fivesList) {
		// Retrieves the element at the ith index in tempList and multiplies it by 2,3,5	
		// assigns each product to its respective list
		
		// Clear old values from 2,3,5 list
		LinkedList.clearList(twosList);
		LinkedList.clearList(threesList);
		LinkedList.clearList(fivesList);

		//Precondition: We have 3 empty linked lists and one temp list containing values 2,3,5
		//Each element in the temp list will be multiplied by 2, 3, or 5 for its respective list
		for (int i = 0; i < LinkedList.getListSize(tempList); ++i) {
			//Accesses the temp list's ith element
			int tempListValue = LinkedList.retrieveData(tempList, i);

			//Multiplies the ith element by 2,3,5 respectively and inserts into its proper list
			twosList = (LinkedList.insert(twosList, tempListValue * 2));
			threesList = (LinkedList.insert(threesList, tempListValue * 3));
			fivesList = (LinkedList.insert(fivesList, tempListValue * 5));
		}
		//Postcondition: twos-, threes-, and fives- lists contain the product of all elements in temp List
		//multiplied by 2, 3, or 5 respectively

		//Termination is given by each iteration incrementing i until i equals the number of elements in the List
		//Thus each list element in temp list has been accessed for our lists generation
	}

	//Requires a scanner object - prompts for user input
	//Error handling ensures that only a valid positive integer is accepted for input
	public static int getInput(Scanner scnr){
		//Verifies user input is an integer and valid
		int userInput;

		try {
			//Prompt for a valid integer input
			System.out.print("\nGenerate how many terms?: ");
			userInput = scnr.nextInt();

			if (userInput < 0) {
				System.out.println("\nInvalid input!\n");
			}
		}
		catch (Exception e) {
			//Catch and consume invalid input
			System.out.println("\nInvalid input!\n");
			scnr.nextLine();
			userInput = -1;
		}

		return userInput;
	}

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		int userInput = -1;

		// While the user input is negative or invalid, prompt for a positive input
		// Precondition: We have an integer that holds an invalid value while it is less than 0
		while (userInput < 0){
			userInput = getInput(scnr);
		}
		// Postcondition: The integer userInput either holds an invalid value or the loop terminates
		
		// Termination: a valid positive integer is returned by the getInput() function through Scanner @ System.in


		// Instantiate lists
		LinkedList twosList = new LinkedList();
		LinkedList threesList = new LinkedList();
		LinkedList fivesList = new LinkedList();
		LinkedList tempList = new LinkedList();
		LinkedList finalList = new LinkedList();


		// Populate temp list with 2,3,5
		tempList = LinkedList.insert(tempList,2);
		tempList = LinkedList.insert(tempList,3);
		tempList = LinkedList.insert(tempList,5);

		
		//As long as user input is greater than the size of temp list, populate temp list with more values
		//  Precondition: We have an ascending sorted linked list of size i,
		//  three linked lists of size j, a final list of size k, and a user input of size n
		while (LinkedList.getListSize(finalList) < userInput * 2) {
			
			// Fill twos, threes, and fives lists
			fillL2L3L5(tempList, twosList, threesList, fivesList);
				
			// Merge values to tempList
			tempList = LinkedList.merge(tempList, twosList);
			tempList = LinkedList.merge(tempList, threesList);
			tempList = LinkedList.merge(tempList, fivesList);
			finalList = LinkedList.merge(finalList, tempList);

		}
		// Postcondition: the temp linked list is merged with the final linked list, which now is of size i
		
		// Termination: the final linked list of size i is greater than or equal to user input of n
		// thus the temporary list and final list contain a sufficient number of unique elements to print
		// the first n elements in series
		



		//Print the first N terms of the final list

		//PrintNTerms will print a valid number of elements in a sequence as follows:
		//	UserInput defines the number of elements to print, which will be a valid positive integer
		//  The above while loop ensures that the size of finalList contains enough elements to satisfy the output
		//  Because the loop will not contain sufficient accurate terms given an input of n, the userInput is multiplied by 2 for the loop sentinel
		//	If it is not satisfied, fillL2L3L5() will generate additional terms, which then will be merged to tempList
		//	tempList is then merged to finalList thus containing the same number of elements
		//	When finalList is of sufficient size, the loop then exits and the correct sequence is output
		//	PrintNTerms will begin a for loop to iterate through finalList a number of times equal to UserInput
		System.out.println("\nFirst " + userInput + " terms in final List: ");
		LinkedList.printNTerms(finalList, userInput);
	}
}
