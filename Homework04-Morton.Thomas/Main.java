import java.util.Scanner;
import java.util.regex.*;
import java.util.Stack;
import java.util.ArrayList;

public class Main {
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Section A ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\

	public static boolean checkFormat(String input) {
		//I originally had a (poorly functioning) regular expression here - in hindsight I should have kept it

		boolean endsWithOperator = false;
		boolean beginsWithNumber = false;

		for (int i = 0; i < input.length(); ++i) {
			char val = input.charAt(i);

			//Iterate through each character in the input string
			//While the input matches an operator, parenthesis, or number, the input is valid
			//If the last read symbol is an operator, flag the boolean endsWithOperator as true
			switch(val) {
				case '+':
				case '-':
				case '*':
				case '/':
					//If the last character was an operator, or a valid number/expression does not precede the operator, the expression is not valid
					if (endsWithOperator || !beginsWithNumber) return false;
					
					endsWithOperator = true;
					break;

				//Flag beginsWithNumber false since the sub-expression no longer starts with a number
				case '(':
					beginsWithNumber = false;
					break;

				case ')':
					break;

				default:
					//If the last read character is a valid digit, flag the endsWithOperator boolean false, flag beginsWithNumber true
					if (Character.isDigit(val)) {
						beginsWithNumber = true;
						endsWithOperator = false;
						break;
					}
					return false;
			}
		}

		//If we did not return false, check if the expression ends with an operator 
		if (endsWithOperator) {
			return false;
		}
		else {
			return true;
		}
	}

	//I've opted to use an arraylist with this rather than write a series of methods to extend
	//an array each time an element is added -- the efficiency of an arraylist is better
	public static ArrayList<String> stringToArray(String input) {
		ArrayList<String> stringArray = new ArrayList<String>();
		String buffer = "";

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            
			switch(current) {
				case '+': case '-':	case '*':
				case '/': case '(':	case ')':

					if (buffer.equals("")) {
						String add = "" + current;
						stringArray.add(add);
					}
					//Otherwise, add the buffer & clear it, then add the last read character
					else {
						stringArray.add(buffer);
						buffer = "";

						String add = "" + current;
						stringArray.add(add);
					}
					break;

				default:
					buffer = buffer + current;
					break;
			}
		}
		//If the buffer still has a non-null value, append it to the array
		if (!buffer.equals("")) {
			stringArray.add(buffer);
		}
		/* 
		System.out.print("\nInput string as array: ");
		for (int i = 0; i < stringArray.size(); ++i) {
			System.out.print(stringArray.get(i) + " ");
		}
		*/
		return stringArray;
	}
	
	public static boolean checkParentheses(String input) {
		Stack<Character> stack = new Stack<>();

		//loop through the entire input
		for (int i = 0; i < input.length(); ++i) {
			//for each open parenthesis, push it to the stack
			if (input.charAt(i) == '(') {
				stack.push('(');
			}
			//for each close parenthesis, pop the stack - if it is empty then the input is invalid
			else if (input.charAt(i) == ')') {
				if (stack.isEmpty()) {
					return false;
				}
				else {
					stack.pop();
				}
			}
		}
		//if the stack is not empty after traversing the input, the input is invalid
		if (!stack.isEmpty()) return false;

		return true;
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		//Using prexisting test cases
		String test0 = "100";
		String test1 = "(100 + 200)";
		String test2 = "(100 * (200 - 300))";
		String test3 = "((100 * (200 + 300)) * ( (400 - 200) / (100 + 100) ))";

		//Prompt for user input for test case
		System.out.print("\nPlease enter an arithmetic expression: ");
		String test4 = scnr.nextLine();

		//Build an array to store the test cases
		ArrayList<String> tests = new ArrayList<String>();
		tests.add(test0);
		tests.add(test1);
		tests.add(test2);
		tests.add(test3);
		tests.add(test4);


		//####Begin Testing####
		for (int i = 0; i < tests.size(); ++i) {
			System.out.println("\n\n\n######################### Testing Test Case # " + i + " ########################");
			
			//Fetch the current test case
			String input = tests.get(i);

			//remove whitespace and display the expression
			input = input.replaceAll("\s", "");
			System.out.println("Read: " + input);

			//Check the parenthesis and operator formatting
			if (!checkParentheses(input)) { 
				System.out.print("Invalid format: Bad parenthesis formatting!");
				System.out.println("\n############################# End Test #################################");
				continue;
			}
			else if (!checkFormat(input)) { 
				System.out.print("Invalid format: Invalid expression!");
				System.out.println("\n############################# End Test #################################");
				continue;
			}

			//After validation, build an expression tree from the current test case
			TreePostfixOperations tree = new TreePostfixOperations();
			ArrayList<String> testCase = stringToArray(input);
			tree.constructTree(testCase);

			//Convert the tree to a postorder notation
			System.out.print("\nPostorder tree traversal: ");
			tree.treeToPostfix(tree.root);

			//Print the postfix representation of the tree
			System.out.print("\nIn postfix notation: ");
			for (int j = 0; j < tree.postfix.size(); ++j) {
				System.out.print(tree.postfix.get(j) + " ");
			}
			System.out.println();
		
			//Evaluate the postfix notation and evaluate the tree directly through postorder traversal
			System.out.printf("\nResult of expression by postfix evaluation: %.4f", tree.evaluatePostfix());
			System.out.print("\n\nEvaluating by postorder traversal: ");
			System.out.printf("\nResult of expression by postorder traversal: %.4f", tree.evaluateTree(tree.root));
			
			System.out.println("\n############################# End Test #################################");
		}
		
	}
}