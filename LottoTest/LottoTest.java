import java.util.*;

/**
 * Test the number of matches in an instant Lotto game.
 * <p>
 * Assumptions:
 * <p>
 * <ul>
 * <li>The balls available are numbered from 1 to 80.</li>
 * <li>The user will pick 20 numbers between 1 and 80(inclusive).</li>
 * <li>The text will generate one million sample picks of 20 numbers each per generation.</li>
 * <p>
 * The following classes from the <tt>java.util</tt> package are used:
 * <p>
 * <li><tt>Random</tt> - To generate random numbers in the valid range.</li>
 * <li><tt>Set</tt> - Interface defining a set of data with no duplicates and no order.</li>
 * <li><tt>TreeSet</tt> - Concrete class defining a sorted set of data with no duplicates but in order.</li>
 * </ul>
 * <p>
 *  Member variables:
 * <p>
 * <tt>rand: Random</tt>
 * <br>
 * <tt>userChoices: {@code Set<Integer>}</tt>
 * <p>
 * @author Seongkwon Lee
 * @version 2014.02.01
 */
public class LottoTest {
	public static final int NBALLS=80;
	public static final int NGEN=1000000;
	public static final int NPICKS=20;
	private Random rand;
	private Set<Integer> userChoices;
	
	/**
	 * Class constructor.
	 * <p>
	 * Initializes the member fields.
	 * <br>
	 * <tt>userChoices</tt> is initialized by invoking the method <tt>generatePicks()</tt>.
	 * <p>
	 */
	public LottoTest() {
		this.rand = new Random();
	}
	
	/**
	 * Create a random set of <tt>NPICKS</tt> numbers.
	 * 
	 * @return a set of integer values.
	 */
	public Set<Integer> generatePicks() {
		this.userChoices = new TreeSet<>();
		while (userChoices.size()<NPICKS) {
			// Generate userChoices 20 numbers.
			// Add them to a set of userChoices.
			// Set does not accept a duplicated number.
			// So, we do not need to check it.
			userChoices.add(rand.nextInt(NBALLS-1)+1);
		}
		
		Set<Integer> picks = new TreeSet<>();
		while (picks.size()<NPICKS) {
			// Generate picks 20 numbers.
			// Add them to a set of picks.
			// Set does not allow a duplicated number.
			// So, we do not need to check it.
			picks.add(rand.nextInt(NBALLS-1)+1);
		}	
		return picks;
	}
	
	/**
	 * Count the number of matches between the user choices and the picks.
	 * 
	 * @param picks the set of integers that represent the current game.
	 * @return the number of elements in the picks that matches the user choice numbers.
	 */
	public int countMatches(Set<Integer> picks) {
		int count = 0;
		Iterator<Integer> it1 = picks.iterator();
		while(it1.hasNext()) {
			// This loop is multiplex while loop to compare with "picks" and "userChoices".
			// Call each numbers in a set of picks with Iterator "it1"
			// Assign them in declared variable "i"
			int i = it1.next();
			Iterator<Integer> it2 = userChoices.iterator();
			while(it2.hasNext()){
				// Call each numbers in a set of userChoices with Iterator "it2"
				// Assign them in declared variable "h"
				// Compare with "i" and "h"
				// Count if they are same
				int h = it2.next();
				if(i==h) count++;	
			}
		}
		return count;	
	}
	
	/**
	 * Main method.
	 * <p>
	 * <ol>
	 * <li>Instantiates a new <tt>LottoTest</tt> object.</li>
	 * <li>Declares a set of <tt>picks</tt>.</li>
	 * <li>Allocates an integer array to contain the <tt>frequency</tt> count of the number of time we get various match counts between the user choices and the random picks.</li>
	 * <li>Iterate through the desires generations of random picks and update the appropriate  <tt>frequency</tt> counts.</li>
	 * <li>Display the list of  <tt>frequency</tt> counts.</li>
	 * </ol>
	 * 
	 * @param args Command line argument strings.
	 */
	public static void main(String[] args) {
		
		LottoTest lt = new LottoTest();
	
		Set<Integer> picks;
	
		int frequency[] = new int [21];// 0 ~ 20
			
		for(int i=0; i<NGEN; ++i) {
			// Generate random picks 1000000 times
			// Update the appropriate frequency counts
			picks = new TreeSet<>(lt.generatePicks());
			int m = lt.countMatches(picks);
			++frequency[m];
		}
		
		int h =0;
		for(int o: frequency) {
			// Display the list of frequency counts.
			System.out.println("[" + h + "]" + "\t" + o);
			++h;
		}
	}
	
}
