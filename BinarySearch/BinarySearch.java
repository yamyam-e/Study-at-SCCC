import java.util.Arrays;
import java.util.Random;
/**
 * Implement the Iterative and Recursive Versions of Binary Search
 * <p>
 * Assumptions:
 * <p>
 * <ul>
 * <li>The main method will create an array of 100 elements and key array of 20 elements</li>
 * <li>Random integer values in the range of 0 ~ 499 will be filled in the arrays.</li>
 * <li>The arrays will be sorted using Arrays Sort.</li>
 * <li>Each version of the method named BinarySearchIter, BinarySearchRec.</li>
 * <li>This program will report a result that is compared with binarySearchIter and binarySearchRec.</li>
 * <p>
 * The following classes from the <tt>java.util</tt> package are used:
 * <p>
 * <li><tt>Random</tt> - To generate random numbers in the valid range.</li>
 * <li><tt>Arrays</tt> - To print out elements in the array.</li>
 * </ul>
 * <p>
 * @author Seongkwon Lee
 * @version 2014.04.16
 */
public class BinarySearch {

	public static void main(String[] args) {
	
		Random rand = new Random();
		//create an array & sort it
		int arr[] = new int [100];
		for(int i = 0; i<arr.length; ++i) {
			arr[i] = rand.nextInt(500);
		}
		Arrays.sort(arr);
		System.out.println("Array"+"\t"+Arrays.toString(arr));
		//create a keyArray & sort it
		int keyArr[] = new int [20];
		for(int i = 0; i<keyArr.length; ++i) {
			keyArr[i] = rand.nextInt(500);
		}
		Arrays.sort(keyArr);
		//report the result
		System.out.println("KeyArray"+Arrays.toString(keyArr));
		System.out.println("---------------------------------------------------------------");
		System.out.println("Key" + "\t\t" + "BiIter"+ "\t\t" + "BiRec"+ "\t\t" + "Matching");
		System.out.println("Value" + "\t\t" + "ShouldBe"+ "\t" + "ShouldBe"+ "\t" + "BiIter");
		System.out.println("     " + "\t\t" + "Index"+ "\t\t" + "Index"+ "\t\t" + "BiRec");
		System.out.println("---------------------------------------------------------------");
		for(int i = 0; i<keyArr.length; ++i) {
			boolean matching = true;
			int bIter = binarySearchIter(arr, 0, arr.length-1, keyArr[i]);
		    int bRec = binarySearchRec(arr, 0, arr.length-1, keyArr[i]);
			if (bIter!=bRec) matching = false;	
			System.out.println(keyArr[i]+"\t\t"+bIter+"\t\t"+bRec+"\t\t"+ matching);
		}
	}
	/**
	 * The implement of iterative version of Binary Search
	 * @param arr  an array of 100 elements
	 * @param from  first element of an array
	 * @param to   last element of an array
	 * @param key  key array of 20 elements
	 * @return shouldBeIndex
	 */
	public static int binarySearchIter(int[] arr, int from, int to, int key) {
		
		int shouldBeIndex;
		while (from<=to){
			int mid=from+(to-from)/2;
			if (arr[mid]<key) from=mid+1;//upper
			else if (arr[mid]>key) to=mid-1;//lower
			else return mid;//found
		}
		//not found
		shouldBeIndex=from;
		return -shouldBeIndex-1;
	}
	/**
	 * The implement of recursive version of Binary Search
	 * @param arr  an array of 100 elements
	 * @param from  first element of an array
	 * @param to   last element of an array
	 * @param key  key array of 20 elements
	 * @return shouldBeIndex
	 */
	public static int binarySearchRec(int[] arr, int from, int to, int key) {
		
		if (from<=to) {
			int mid=from+(to-from)/2;
			if (arr[mid]<key) 		return binarySearchRec(arr, ++mid, to, key);//upper	
			else if (arr[mid]>key) 	return binarySearchRec(arr, from, --mid, key); //lower
			else 					return mid;//found
		}
		else {//not found
			int shouldBeIndex=from;
			return -shouldBeIndex-1;
		}
	}	
}
