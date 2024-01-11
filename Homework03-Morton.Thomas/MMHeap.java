


public class MMHeap {
	int[] minMaxHeap;
	int size = 0;

	public MMHeap() {
		System.out.println("\nEmpty heap initialized with a default maximum size of 10!");
		minMaxHeap = new int[10];

		buildHeap();
	}

	public MMHeap(int[] inputArray, int arraySize) {
		System.out.println("Heap initialized with a size of " + arraySize + "!");
		minMaxHeap = new int[arraySize];

		try {
			for (int i = 0; i < arraySize; ++i) {
				minMaxHeap[i] = inputArray[i];
			}
			size = arraySize;
		}
		catch (Exception e) {
			System.out.println("Something went wrong!\n" + e);
			return;
		}

		buildHeap();
	}
	
	public void printHeap() {
		int level = 0;
		
		//Print each element in the Heap
		//Pre: we have an integer which represents a valid index for the heap
		for (int i = 0; i < size; ++i) {
			
			// If adding 1 to the index would equal 2^level, then our next print statement will be at a new level
			if (i + 1 == Math.pow(2, level)) {
				level++;
				System.out.println();
			}

			//This is a crude spacing loop. As level increases, decrease the spacing between elements to output
			for (int j = size / (level + 1); j > 0; j--) {
				System.out.print(" ");
			}

			System.out.print(minMaxHeap[i]);
		}
		//Post: The element at the valid index is output to console and the integer i is incremented
		System.out.println("\n");
	}

	//takes the element at index a and index b and swaps them
	public void swap(int a, int b) {
		int temp = minMaxHeap[a];
		minMaxHeap[a] = minMaxHeap[b];
		minMaxHeap[b] = temp;
	}
	 
	//Calls the heapify method using 1/2 the size of the arraySize
	//This is sufficient to validate each node as pushdownmin and pushdownmax will cover each element
	public void buildHeap() {
		for (int i = (size - 1) / 2; i >= 0; --i) {
			heapify(i);
		}
	}

	//Finds the current level of a node and returns 0 for min level, 1 for max
	public int findLevel(int currentNode) {
		int level = 0;
		
		//If node is 0, we are at root and level is 0
		if (currentNode == 0) {
			return 0;
		}
		else {
			//Otherwise, while we are not at the root, retrieve the index of the parent
			//Until we are at the root
			//Precondition - Level is 0, we have a valid node which has a parent
			while (currentNode != 0) {
				level++;
				currentNode = getParent(currentNode);
			}
			//Postcondition - for each parent node visited, increment level - exit on finding root
		}
		//Returning level mod2 gives:
		//	0: the level is even, thus a min level
		//	1: the level is odd, thus a max level
		return level % 2;
	}

	//Prints min element
	public void min() {
		//The minumum element is trivially the root of the heap
		System.out.println("\nMinimum node is: " + minMaxHeap[0] + " at index: " + 0);
	}

	//prints max element
	public void max() {
		//If there are no elements, print null
		if (size < 1) {
			System.out.println("null");
		}
		//If there is only one element, trivially it is the maximum
		else if (size == 1) {
			System.out.println("\nMaximum node is: " + minMaxHeap[0] + " at index: " + 0);
		}
		//Otherwise, compare the left and right children
		//Which trivially should be the largest elements
		else {
			if (minMaxHeap[1] > minMaxHeap[2]) {
				System.out.println("\nMaximum node is: " + minMaxHeap[1] + " at index: " + 1);
			}
			else {
				System.out.println("\nMaximum node is: " + minMaxHeap[2] + " at index: " + 2);
			}
		}
	}

	public void insert(int input) {
		int i = size;
		System.out.println("\nInserting: " + input);
		//If adding one element to size exceeds the maximum space in the heap, we cannot insert
		if (size >= minMaxHeap.length) {
			System.out.println("Heap size exceeded!");
			return;
		}

		//If the heap is empty, append the element to the first index
		else if (i == 0){
			minMaxHeap[i] = input;
			size++;
		}
		//Otherwise, check our level and append accordingly
		else {
			// update our index i, insert the element, update our size
			i = size;
			minMaxHeap[i] = input;
			size++;

			int parentIndex = getParent(i);

			//Check if we are on a min level
			if (findLevel(i) == 0) {
				//If so, check if the input is larger than the parent of our input node and swap, if so - call appropriate bubble function for input size
				if (minMaxHeap[i] > minMaxHeap[parentIndex]) {
					swap(i, parentIndex);
					bubbleUpMax(parentIndex); 
				}
				else {
					bubbleUpMin(i);
				}
			}
			else {
				//If we are on a max level, check if the input is smaller than the parent, if so - call appropriate bubble function for input size
				if (minMaxHeap[i] < minMaxHeap[parentIndex]) {
					swap(i, parentIndex);
					bubbleUpMin(parentIndex);
				}
				else {
					bubbleUpMax(i);
				}
			}

		}
	}

	public void extractMin() {
		//The minimum element is trivially the root
		//Thus we replace the root with the last node and push the last node (now root) down until heap property is maintained
		System.out.println("\nRemoving: " + minMaxHeap[0] + " at index: " + 0);
		
		//Replace node and reduce size
		minMaxHeap[0] = minMaxHeap[size - 1];
		size--;

		//push it down
		pushDownMin(0);
	}

	public void extractMax() {
		//The max heap is trivially the left or right child of the root
		//Thus we remove the larger of the two and replace it with the last node
		//Then push it down to maintain the heap property
		int largest;

		if(minMaxHeap[1] > minMaxHeap[2]) {
			largest = 1;
		}
		else {
			largest = 2;
		}

		System.out.println("\nRemoving: " + minMaxHeap[largest] + " at index: " + largest);
		//Replace the largest of the two with the last element in the heap
		minMaxHeap[largest] = minMaxHeap[size - 1];
		size--;

		//push it down
		pushDownMin(largest);
	}

	public void bubbleUpMax(int currentNode) {
		//Check if our index has an ancestor node 
		if (currentNode >= 3 && minMaxHeap[currentNode] > minMaxHeap[getAncestor(currentNode)]) {
			//swap them if so and repeat the process
			swap(currentNode, getAncestor(currentNode));

			bubbleUpMax(getAncestor(currentNode));
		}
	}

	public void bubbleUpMin(int currentNode) {
		//Check if our index has an ancestor node
		if (currentNode >= 3 && minMaxHeap[currentNode] < minMaxHeap[getAncestor(currentNode)]) {
			//swap them if so and repeat the process
			swap(currentNode, getAncestor(currentNode));

			bubbleUpMin(getAncestor(currentNode));
		}
	}

	//Returns the left node, trivially current * 2 + 1
	public int getLeftChild(int currentNode) {
		return currentNode * 2 + 1;
	}
	
	//Returns the right node, trivially current * 2 + 2
	public int getRightChild(int currentNode) {
		return currentNode * 2 + 2;
	}

	//Returns the parent node, trivially (current - 1) / 2
	public int getParent(int currentNode) {
		return (currentNode - 1) / 2;
	}

	//Retrieves the parent, then retrieves the parent of the parent
	public int getAncestor(int currentNode) {
		return getParent( getParent(currentNode) );
	}


	public int getMinChild(int currentNode) {
		int leftChild = getLeftChild(currentNode);
		int rightChild = getRightChild(currentNode);
		int returnVal = -1;

		//If left child exceed size, no valid element may be found
		if (leftChild >= size) {
			return -1;
		}
		//If right child exceed size, no valid element may be found - left child is min
		else if (rightChild >= size) {
			return leftChild;
		}
		else {
			//otherwise compare and assign the min to return value
			if (minMaxHeap[leftChild] < minMaxHeap[rightChild]) {
				returnVal = leftChild;
			}
			else {
				returnVal = rightChild;
			}
		}
		return returnVal;
	}

	public int getMaxChild(int currentNode) {
		int leftChild = getLeftChild(currentNode);
		int rightChild = getRightChild(currentNode);
		int returnVal = -1;

		//If left child exceed size, no valid element may be found
		if (leftChild >= size) {
			return -1;
		}
		//If right child exceed size, no valid element may be found - left child is max
		else if (rightChild >= size) {
			return leftChild;
		}
		else {
			//otherwise compare and assign the max to return value
			if (minMaxHeap[leftChild] > minMaxHeap[rightChild]) {
				returnVal = leftChild;
			}
			else {
				returnVal = rightChild;
			}
		}
		return returnVal;
	}

	public int getMinGrandChild(int currentNode) {
		//Get the left child node of the left child node
        int leftGrandChild = getLeftChild( getLeftChild(currentNode) );
        
		//If it is beyond the size of the heap, no grandchildren exist
		if (leftGrandChild >= size) {
            return -1;
        }

		//Set min equal to the leftmost node
        int minValue = minMaxHeap[leftGrandChild];
		int minNode = leftGrandChild;

		//Precondition - min contains the minimum grandchild
		//i + leftGrandChild are possible indices for the grandchildren of currentNode
        for (int i = 1; i <= 3; i++) {

			//As long as i + leftGrandChild is less than size, there are valid elements to access
            if (i + leftGrandChild < size) {

				//If the element at leftGrandChild + i is less than min, it is the min so far
				if (minMaxHeap[leftGrandChild + i] < minValue) {
					minNode = leftGrandChild + i;
					minValue = minMaxHeap[leftGrandChild + i];
				}
			}
        }
		//Postcondition - all grandchildren nodes have been iterated through, with min assigned the min value
        return minNode;
    }
 
    public int getMaxGrandChild(int currentNode) {
		//Get the left child node of the left child node
        int leftGrandChild = getLeftChild( getLeftChild(currentNode) );
        
		//If it is beyond the size of the heap, no grandchildren exist
		if (leftGrandChild >= size) {
            return -1;
        }

		//Set max equal to the leftmost node
        int maxValue = minMaxHeap[leftGrandChild];
		int maxNode = leftGrandChild;

		//Precondition - max contains the maximum grandchild seen so far
		//i + leftGrandChild are possible indices for the grandchildren of currentNode
        for (int i = 1; i <= 3; i++) {

			//As long as i + leftGrandChild is less than size, there are valid elements to access
            if (i + leftGrandChild < size) {

				//If the element at leftGrandChild + i is greater than max, it is the max so far
				if (minMaxHeap[leftGrandChild + i] > maxValue) {
					maxValue = minMaxHeap[leftGrandChild + i];
					maxNode = leftGrandChild + i;
				}
			}
        }
		//Postcondition - all grandchildren nodes have been iterated through, with max assigned the min value
        return maxNode;
    }

	public void pushDownMin(int currentNode) {
        int minChild = getMinChild(currentNode);
		int minGrandChild = getMinGrandChild(currentNode);
		//If there is no valid children, we cannot push down further
		if (minChild != -1) {
			//get the smallest node between children and grandchildren
			int min = (minGrandChild == -1 || minMaxHeap[minChild] < minMaxHeap[minGrandChild]) ? minChild : minGrandChild;

			//If the minimum node was a grandchild node, proceed
			if (getAncestor(min) == currentNode){
				//If a valid grandchild exists, see if it is smaller than the minimum child, swap if so
				if (minMaxHeap[min] < minMaxHeap[currentNode]) {
					swap(min, currentNode);

					//If the grandChild is larger than its parent, swap the two
					if (minMaxHeap[min] > minMaxHeap[getParent(min)]) {
						swap(min, getParent(min));
					}
					//Recurse on the minGrandChild
					pushDownMin(min);
				}
			}
			
			//If the min was a child node, compare it to the current node, swap if smaller
			else if (minMaxHeap[min] < minMaxHeap[currentNode]) {
					swap(min, currentNode);
			}	
		}
	}

	public void pushDownMax(int currentNode) {
        int maxChild = getMaxChild(currentNode);
		int maxGrandChild = getMaxGrandChild(currentNode);
		//If there is no valid children, we cannot push down further
		if (maxChild != -1) {
			//get the largest node between children and grandchildren
			int max = (maxGrandChild == -1 || minMaxHeap[maxChild] > minMaxHeap[maxGrandChild]) ? maxChild : maxGrandChild;
			
			//If the maximum node was a grandchild node, proceed
			if (getAncestor(max) == currentNode){
				//If a valid grandchild exists, see if it is larger than the minimum child, swap if so
				if (minMaxHeap[max] > minMaxHeap[currentNode]) {
					swap(max, currentNode);

					//If the grandChild is smaller than its parent, swap the two
					if (minMaxHeap[max] < minMaxHeap[getParent(max)]) {
						swap(max, getParent(max));
					}
					//Recurse on the maxGrandChild
					pushDownMax(max);
				}
			}
			//If the max was a child node, compare it to the current node, swap if larger
			else if (minMaxHeap[max] > minMaxHeap[currentNode]) {
					swap(max, currentNode);
			}	
		}
	}


	public void heapify(int currentNode) {
		//If we are at a minimum level, call pushDownMin

		if (findLevel(currentNode) == 0) {
			pushDownMin(currentNode);
		}
		//Otherwise call pushDownMax
		else {
			pushDownMax(currentNode);
		}
	}
}