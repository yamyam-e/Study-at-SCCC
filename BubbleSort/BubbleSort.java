import java.util.Arrays;
import java.util.Random;
/**
 * Implement the Iterative and Recursive Versions of Bubble Sort
 * <p>
 * Assumptions:
 * <p>
 * <ul>
 * <li>The main method will create an array of 100 elements</li>
 * <li>Random integer values in the range of 0 ~ 499 will be filled in the array.</li>
 * <li>The array will be sorted using all four versions of the algorithm.</li>
 * <li>Each version of the method named BubbleIter, BubbleRec, BubbleIterMod, and BubbleRecMod.</li>
 * <p>
 * The following classes from the <tt>java.util</tt> package are used:
 * <p>
 * <li><tt>Random</tt> - To generate random numbers in the valid range.</li>
 * <li><tt>Arrays</tt> - To print out elements in the array.</li>
 * </ul>
 * <p>
 * @author Seongkwon Lee
 * @version 2014.03.29
 */

public class BubbleSort {

	public static void main(String[] args) {
		
		int arr[] = new int [100];
		Random rand = new Random();
		
		for(int i = 0; i<arr.length; ++i) {
			arr[i] = rand.nextInt(500);
		}
		
		//int arr[] = { 1,2,3,0,5,6,7,8,9,4};
		
		int temp[] = arr.clone();
		
		System.out.println("Original Array");
		System.out.println(Arrays.toString(arr));
		
		BubbleIter(arr);
		System.out.println("Iterative Bubble Sort: " + checkSorted(arr, 0, 1));
		System.out.println(Arrays.toString(arr));

		
		arr = temp.clone();
		BubbleRec(arr, 0, arr.length-1);
		System.out.println("Recursive Bubble Sort: " + checkSorted(arr, 0, 1));
		System.out.println(Arrays.toString(arr));
	
		arr = temp.clone();
		BubbleIterMod(arr);
		System.out.println("Iterative Modified Bubble Sort: " + checkSorted(arr, 0, 1));
		System.out.println(Arrays.toString(arr));
		
		arr = temp.clone();
		BubbleRecMod(arr, 0, arr.length-1);
		System.out.println("Recursive Modified Bubble Sort: " + checkSorted(arr, 0, 1));
		System.out.println(Arrays.toString(arr));
	}
	
	/**
	 * The implement of iterative version of Bubble Sort using the modifications
	 * No swaps occur during the n passes then skip unnecessary passes
	 * @param arr
	 * @param first
	 * @param last
	 */
	private static void BubbleRecMod(int[] arr, int first, int last) {
		
		boolean pass = true;
		if (first<last) {
			pass = false; //assume this is last pass over array
			if (arr[first]>arr[first+1]) {
				swap(arr, first);
				pass = true; //after an exchange, must look again
			}
			//Sort the biggest element to last index
			BubbleRecMod(arr, first+1, last); 
			// Prohibit calling n+1 pass before finishing n pass
			// Start n+1 pass
			if (first==0) BubbleRecMod(arr, first, last-1);
		}
	}
	
	/**
	 * The implement of iterative version of Bubble Sort using the modifications
	 * No swaps occur during the n passes then skip unnecessary passes
	 * @param arr
	 */
	private static void BubbleIterMod(int[] arr) {
		
		boolean pass = true;
	    while (pass) {
	        pass = false; //assume this is last pass over array
	        for (int j=0; j<arr.length-1; ++j) {
	            if (arr[j] > arr[j+1]) { 
	            	swap(arr, j);
	            	pass = true; //after an exchange, must look again
	            }
	        }
	    }
	}
	
	/**
	 * The implement of recursive version of Bubble Sort
	 * @param arr
	 * @param first
	 * @param last
	 */
	private static void BubbleRec(int[] arr, int first, int last) {
		
		if(first<last) {
			if (arr[first]>arr[first+1]) swap(arr, first);
			
			//Sort the biggest element to last index
			BubbleRec(arr, first+1, last); 
			
			// Prohibit calling n+1 pass before finishing n pass
			// Start n+1 pass
			if (first==0) BubbleRec(arr, first, last-1);
		}
	}
	
	/**
	 * The implement of iterative version of Bubble Sort
	 * @param arr
	 */
	private static void BubbleIter(int[] arr) {
		
		int size = arr.length;
	    for (int i=1; i<size; ++i) {
	        for (int j=0; j<size-i; ++j) {
	        	// Sorts the array arr[first] through arr[last] iteratively.
	        	// Last index changes by i pass
	            if (arr[j]>arr[j+1]) swap(arr, j);
	        }
	    }
	}

	/**
	 * Swap elements in the array
	 * @param arr An array
	 * @param j A target element for swapping
	 */
	private static void swap(int[] arr, int j) {
		
		int temp = arr[j];
		arr[j] = arr[j+1];
		arr[j+1] = temp;
	}

	/**
	 * Check whether the array is sorted
	 * @param arr
	 * @param from 
	 * @param to
	 * @return true if the array is sorted, or false if not
	 */
	private static boolean checkSorted(int[] arr, int from, int to) {
		
		boolean result = true;
		if (to<arr.length) {
			if (arr[from]>arr[to]) return result = false;
			// Calling same method to check next elements
			if (!checkSorted(arr, from+1, to+1)) return result = false;
		}
		return result;
	}
}
