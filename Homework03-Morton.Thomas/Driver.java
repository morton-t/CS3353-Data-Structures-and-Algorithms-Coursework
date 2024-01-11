import java.util.Random;


public class Driver {

	public static void testCases(MMHeap heap) {
		
		Random rng = new Random();

		//If the heap is empty test all operations
		if (heap.size == 0) {
			System.out.print("\n~~~~~~~~~~~~~~~~Testing empty heap~~~~~~~~~~~~~~~~~~~~\n");
			System.out.println("\nAttempting to fill the heap with 11 numbers:");

			for (int i = 0; i < 11; ++i) {
				// Generate a negative or positive psuedorandom number
				int randNum = rng.nextInt(100) - 50;

				//insert the number to verify insert() works
				heap.insert(randNum);

				//Print after each insert
				heap.printHeap();

			}
		
			//Test print the min and max of the heap
			heap.min();
			heap.max();

			//Extract the min and print the heap
			heap.extractMin();
			heap.printHeap();

			//Extract the max and print the heap
			heap.extractMax();
			heap.printHeap();

			System.out.println("\n~~~~~~~~~~~~~~~~End test empty heap~~~~~~~~~~~~~~~~~~~~\n");
		}
		//If the heap is not empty, verify the heapify works appropriately
		else {

			System.out.print("\n\n~~~~~~~~~~~~~~~~Testing filled heap~~~~~~~~~~~~~~~~~~~~\n");
			heap.printHeap();

			//Test that we cannot insert more elements than the heap can support
			heap.insert(-9999);

			//Delete an element from the heap and insert our test number -9999
			heap.extractMin();
			heap.insert(-9999);
			heap.printHeap();

			//Test print the min and max of the heap
			heap.min();
			heap.max();

			//Extract the min and print the heap
			heap.extractMin();
			heap.printHeap();

			//Extract the max and print the heap
			heap.extractMax();
			heap.printHeap();
			System.out.println("\n~~~~~~~~~~~~~~~~End test filled heap~~~~~~~~~~~~~~~~~~~~\n");
		}
	}

	public static void main(String[] args) {
		int[] testArr = new int[] {16,8,2,151,120,1,33,22,99,85,20,0,-8,88,77,46,13,159,1024};
		
		MMHeap emptyHeap = new MMHeap();
		testCases(emptyHeap);

		MMHeap heap = new MMHeap(testArr, testArr.length);
		testCases(heap);
	}
}