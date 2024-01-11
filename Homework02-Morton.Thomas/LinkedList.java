//Thomas Morton
//A11353689
//Dr. HK Dai - CS 3353-24287

public class LinkedList {
	Node head;

	// Node object and constructors
	public static class Node {
		int data;
		Node nextPtr;

		public Node() {}

		public Node(int inputData) {
			data = inputData;
			nextPtr = null;
		}
	}

	//Requires input of a valid linked list object - sets head node to null
	//Remaining list is erased during garbage collection
	public static void clearList(LinkedList list){
		list.head = null;
	}

	//Requires a valid linked list and an integer input
	//If the element is out of bounds or not found, the method will return -1
	//This should be sufficient for this project as all data are positive integers only
	public static int retrieveData(LinkedList list, int listIndex) {
		// Takes the passed index and retrieves the data stored at that index in the list
		
		Node listNode = list.head;

		for (int i = 0; i <= listIndex; ++i){

			// if we're not at the correct node, grab the next node
			if (i != listIndex) {
				listNode = listNode.nextPtr;
				continue;
			}
			//otherwise return the data stored in the node
			else {
				return listNode.data;
			}
		}
		return -1;
	}

	//Requires input of a valid linked list
	//For each node that is not null in the list, size is incremented
	//Returns final size count on null found
	public static int getListSize(LinkedList list) {
		//Retrieves the number of elements in the list
		int size = 0;

		Node listNode = list.head;

		// While the current node exists add one to size count
		while(listNode != null) {
			size++;

			// Retrieve the next node in the list
			listNode = listNode.nextPtr;
		}
		return size;
	}

	//Requires a valid linked list
	//If data is present at the node, print the data and retrieve the next node
	public static void printAll(LinkedList list) {
		// Prints the all terms in a list - This is an artifact from testing

		Node listNode = list.head;

		// While the current node exists, print the data contained in the node
		while(listNode != null) {
			System.out.print(listNode.data + " ");

			// Retrieve the next node in the list
			listNode = listNode.nextPtr;
		}
		System.out.println();
	}

	//Requires a valid linked list and a positive integer for userInput
	//If data is present at the node, print the data and retrieve the next node
	public static void printNTerms(LinkedList list, int userInput) {
		// Prints the first N terms in a list given by the user input
		
		if (userInput < 0) return;

		Node listNode = list.head;

		// While our index value is less than the user input, print the node data
		for (int i = 0; i < userInput; ++i) {
			System.out.print(listNode.data + " ");

			// Retrieve the next node in the list
			listNode = listNode.nextPtr;
		}
		System.out.println();
	}

	//Requires an input of two valid linked lists
	//Order of the linked lists for args does not matter as the smaller head node will be assigned to list1Node
	public static LinkedList merge(LinkedList list1, LinkedList list2) {
		Node list1Node = list1.head;
		Node list2Node = list2.head;

		Node list1NextNode = null;
		Node list2NextNode = null;

		//Guard against compare to null
		if (list1Node != null && list2Node != null){

			//Compare data in heads of both lists; assign list1Node with the smaller head
			if (list2Node.data < list1Node.data) {
				LinkedList temp = list2;
				list2 = list1;
				list1 = temp;

				list1Node = list1.head;
				list2Node = list2.head;
			}
		}

		// If list1 DNE; set list1 to reference list2
		if (list1Node == null){
			return list2;
			//list1Node = list2Node;
			//list1Node.nextPtr = list2Node.nextPtr;
		}
		//otherwise initialize pointers for next nodes in each list as list1NextNode, list2NextNode
		else {
			list1NextNode = list1Node.nextPtr;
			list2NextNode = list2Node.nextPtr;
		}
		
		//Precondition: We have two linked lists sorted ascending order; list1 has the smaller head value
		//If either list is not null, there are more values to be merged
		while (list1Node != null && list2Node != null) {
			// If list2's data between current list1 data and next list1 data then merge
			if (list2Node.data > list1Node.data && list2Node.data < list1NextNode.data) {
				//list2Node's next node assigned with the value in list2Node's pointer to next element
				list2NextNode = list2Node.nextPtr;

				//list1Node's pointer updated to reference list2's current node
				list1Node.nextPtr = list2Node;

				//list2Node's pointer to next node updated to reference list1's next node in list
				list2Node.nextPtr = list1NextNode;

				//Update list1 current node to list2 current node
				list1Node = list2Node;

				//replace list2's current node with the next node in the list
				list2Node = list2NextNode;
			}

			// If data in list1Node equals list2Node data then traverse to next node in list2
			else if (list1Node.data == list2Node.data) {
				//If list2's next node is null, then the list is at its final node
				if (list2NextNode != null) {
					list2Node = list2Node.nextPtr;
				}
				//If both of the next nodes are null then both lists are at the last node
				//with the same last element; no new nodes should be added - break
				else if (list1NextNode != null) {
					break;
				}
				// If list1 is not at the last node, traverse list1
				else {
					list1Node = list1Node.nextPtr;
				}
			}

			else {
				// If the values are not equal and list2's data is not less than list1's data
				// Then traverse to the next node in list1 if it exists
				if (list1NextNode.nextPtr != null) {
					list1NextNode = list1NextNode.nextPtr;
					list1Node = list1Node.nextPtr;

				}
				// If list1 next node's next node does not exist, and we're in this block
				// then the remaining elements of list2 are larger than the last element
				// of list one and no values equal them from list 1
				// so assign the next node of list 2 to the end of list1 and exit
				else {
					//list1Node = list2Node;
					list1Node = list1Node.nextPtr;
					list1Node.nextPtr = list2Node.nextPtr;
					break;
				}
			}
			// Post-Condition: List1 contains the merged result in ascending order of both lists
		}
		return list1;

	}

	//Requires a valid linked list and any integer for input
	//Insert is not exposed to user, so no error handling has been used
	public static LinkedList insert(LinkedList list, int data) {
		// Inserts a value into the linked list at the tail
		
		Node nextNode = new Node(data);

		// If the head is null, the list is empty - set head to reference the new node
		if (list.head == null) {
				list.head = nextNode;
		}

		// Otherwise assign current node with data from head
		else {
			Node listNode = list.head;

			// if nextPtr is null, we are at the tail
			while (listNode.nextPtr != null){
				listNode = listNode.nextPtr;
			}

			// Set nextPtr to reference the new node's data
			listNode.nextPtr = nextNode;
		}
			
		return list;
	}
}