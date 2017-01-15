import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Counting the occurrences of all of the words in the file.
 * <p>
 * This program will use the two different implementations of Map: HashMap and TreeMap.
 * This program will need to edit the contents of what you read in to facilitate the counting process.
 * WordCount.out – the display of the hashMap and treeMap objects and the display of a list of words ordered by usage from high to low.
 * WordCount.err – the display of the performance statistics that you should gather and display.
 * This program will recreate these files as output files.
 * <p>
 * @author Seongkwon Lee
 * @version 2014.05.04
 */
public class WordCount {
	
	public static void main(String[] args) throws IOException {
		
		String[] words = (processInputFile("/Users/seongkwon/Documents/workspace/CST246/src/DoI.txt"));
		
		System.out.println("#######################################################");
		System.out.println("#                 HashMap Based Map                   #");
		System.out.println("#######################################################");
		Map<String, Integer> Hmap = new HashMap<String, Integer>();
		System.err.printf("HashMap Build   Time: %6.3f ms %n" , buildMap(words, Hmap)/1000000.0);
		System.err.printf("HashMap Display Time: %6.3f ms %n" , displayMap(Hmap)/1000000.0);
		System.out.println();
		System.out.println("#######################################################");
		System.out.println("#                 TreeMap Based Map                   #");
		System.out.println("#######################################################");
		Map<String, Integer> Tmap = new TreeMap<String, Integer>();
		System.err.printf("TreeMap Build   Time: %6.3f ms %n" , buildMap(words, Tmap)/1000000.0);
		System.err.printf("TreeMap Display Time: %6.3f ms %n" , displayMap(Tmap)/1000000.0);
		System.out.println();
		
		Pair comparator = new Pair(Tmap);
		
		System.out.println("#######################################################");
		System.out.println("#                 UsageMap Based Map                  #");
		System.out.println("#######################################################");
		Map<String, Integer> usageMap = new TreeMap<String, Integer>(comparator);
		usageMap.putAll(Tmap);
		for (Map.Entry<String, Integer> entry: usageMap.entrySet()) {
		    System.out.printf("%-20s %s %n", entry.getKey(), entry.getValue());
		}
	}
	/**
	 * From an array of words build a map of words and usage counts.
     * This is a general methods that works the same for HashMaps and TreeMaps.
	 * @param words  An array of Strings containing the words.
	 * @param map A Map of (word : count) pairs.
	 * @return The time, in nanoseconds, to build the map.
	 */
	private static long buildMap(String[] words, Map<String, Integer> map) {
		
		long startTime = System.nanoTime();
		
		//store words into the map
		for(String word: words) {
			//ignore empty elements in the words
			if(!word.equals("")) {
				//add a number of entry
				if (map.containsKey(word)) map.put(word, map.get(word)+1);
				//make a new entry
				else map.put(word, 1); 
			}
		}
		
		long estimatedTime = System.nanoTime()-startTime;
		
		return estimatedTime;
	}
	
	/**
	 * Display the contents of a map of (word : count) pairs.
	 * @param map The Map of (word : count) pairs.
	 * @return The time, in nanoseconds, to display the map.
	 */
	private static long displayMap(Map<String, Integer> map) {
		
		long startTime = System.nanoTime();
		
		//display the key and value in the map
		for (Map.Entry<String, Integer> entry: map.entrySet()) {
		    System.out.printf("%-20s %s %n", entry.getKey(), entry.getValue());
		}
		
		long estimatedTime = System.nanoTime()-startTime;
		
		return estimatedTime;
	}
	
	/**
	 * Read the named file and edit the contents.
	 * All letters are converted to lower case so that the process is case insensitive.
     * Digits are removed (only words are processed).
     * Punctuation marks are removed.
     * Hyphens (dashes) are replaced with spaces.
     * A String[] is created by splitting the String of words read in from the file.
	 * @param fileName The OS based name of the file to be processed.
	 * @return A String[] of the words in the file.
	 * @throws IOException
	 */
	private static String[] processInputFile(String fileName) throws IOException {
		
		String digits = "0123456789";
		String punctuations = "\"'.,;:!?~!@#$%^&*`()[]{}|<>";
		String dashes ="-_";
		
		//store all lines in the text as a line
		String temp = "";
			
		Scanner br = new Scanner(new FileInputStream(fileName));
		
		// reading line by line from file
		while(br.hasNextLine()) {
			String str = br.nextLine();
			
			//converting all letters to lower case so that the word list is case insensitive
			str = str.toLowerCase();
			for(char ch: str.toCharArray()) {
				//removing all digits (only words are processed)
				if(digits.indexOf(ch)>=0) str = str.replace(ch+"", "");
				
				//removing all punctuation marks (e.g., periods, commas, etc.)
				if(punctuations.indexOf(ch)>=0) str = str.replace(ch+"", "");
				
				//hyphens (dashes) are replaced with spaces making the two parts of 
				//a hyphenation word into separate words.
				if(dashes.indexOf(ch)>=0) str = str.replace(ch+"", " ");
			}
			temp = temp + " " + str;//make one line for lines in the text
		}
		String[] result = temp.split(" ");//split the line with the space
		
		return result;
	}
	
	private static class Pair implements Comparator {
		
		private Map<String, Integer> map;
		
		public Pair(Map<String, Integer> map) {
			this.map = map;
		}

		@Override
		public int compare(Object o1, Object o2) {
			if (map.get(o2)==map.get(o1)) return 1;
			else return ((Integer)map.get(o2)).compareTo((Integer)map.get(o1));
		}
	}
}
