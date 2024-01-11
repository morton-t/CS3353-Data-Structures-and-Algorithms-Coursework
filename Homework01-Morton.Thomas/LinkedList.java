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
	
	public static void clearList(LinkedList list){
		list.head = null;
	}

	public static int retrieveData(LinkedList list, int listIndex) {
		// Takes the passed index and retrieves the data stored at that index in the list
		
		Node currentNode = list.head;

		for (int i = 0; i <= listIndex; ++i){

			// if we're not at the correct node, grab the next node
			if (i != listIndex) {
				currentNode = currentNode.nextPtr;
				continue;
			}
			//otherwise return the data stored in the node
			else {
				return currentNode.data;
			}
		}
		return -1;
	}

	public static int getListSize(LinkedList list) {
		//Retrieves the number of elements in the list
		int size = 0;

		Node currentNode = list.head;

		// While the current node exists add one to size count
		while(currentNode != null) {
			size++;

			// Retrieve the next node in the list
			currentNode = currentNode.nextPtr;
		}
		return size;
	}

	public static void printAll(LinkedList list) {
		// Prints the all terms in a list - This is an artifact from testing

		Node currentNode = list.head;

		// While the current node exists, print the data contained in the node
		while(currentNode != null) {
			System.out.print(currentNode.data + " ");

			// Retrieve the next node in the list
			currentNode = currentNode.nextPtr;
		}
		System.out.println();
	}

	public static void printNTerms(LinkedList list, int userInput) {
		// Prints the first N terms in a list given by the user input
		
		Node currentNode = list.head;

		// While our index value is less than the user input, print the node data
		for (int i = 0; i < userInput; ++i) {
			System.out.print(currentNode.data + " ");

			// Retrieve the next node in the list
			currentNode = currentNode.nextPtr;
		}
		System.out.println();
	}



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
					list1Node.nextPtr = list2Node;
					break;
				}
			}
			// Post-Condition: List1 contains the merged result in ascending order of both lists
		}
		return list1;

	}

	public static LinkedList insert(LinkedList list, int data) {
		// Inserts a value into the linked list at the tail
		
		Node nextNode = new Node(data);

		// If the head is null, the list is empty - set head to reference the new node
		if (list.head == null) {
				list.head = nextNode;
		}

		// Otherwise assign current node with data from head
		else {
			Node currentNode = list.head;

			// if nextPtr is null, we are at the tail
			while (currentNode.nextPtr != null){
				currentNode = currentNode.nextPtr;
			}

			// Set nextPtr to reference the new node's data
			currentNode.nextPtr = nextNode;
		}
			
		return list;
	}
}