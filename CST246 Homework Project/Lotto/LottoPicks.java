package lotto;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Random;

/**
 *  Generate Instant Lotto picks.
 *  <p>
 *  We assume that the Instant Lotto game is played with 80 numbered balls
 *  labeled 1 through 80.
 *  <br>
 *  A game consists of a random selection of 20 out of the 80 possible balls.
 *  <br>
 *  The program will generate a {@code SortedSet<Integer>} containing the 20
 *  randomly selected balls.
 *  <br>
 *  The program will then display the game as an ordered list.
 *
 *  @author Bruce Barton
 *  @version 2014.01.30
 *      <ol>
 *      <li>Use SortedSet.
 *      <li>Tighten main loop.
 *      <li>Rather than use {@code count} use {@code size()} method.
 *      </ol>
 *  @version 2014.01.15 Initial version.
 */
public class LottoPicks
{
    public static final int NBALLS = 80;
    public static final int NPICKS = 20;

    /**
     *  Main method.
     *  <p>
     *  Generate the game balls and display them.
     *  <br>
     *  We use a {@code TreeSet<Integer>} since we really need a {@code
     *  SortedSet} and the <em>binary search tree</em> model provides that
     *  for us with no additional coding necessary.
     *  <br>
     *  We can display the results by using the default {@code toString()}
     *  method of the {@code SortedSet} interface.
     *  This method will produce a comma separated list of value (in numerical
     *  order) enclosed in square brackets.
     *
     *  @param args Command line argument strings (none used).
     */
    public static void main(String[] args)
    {
        Random rand = new Random();
        SortedSet<Integer> picks = new TreeSet<>();

         while (picks.size() < NPICKS) {
            //  The add() method will return true if the element to be added
            //  DOES NOT already exist in the set and false if it DOES
            //  already exist in the set.
            //  Thus, if and only if the add() is successful will it increment
            //  the size().
            picks.add(rand.nextInt(NBALLS) + 1);
        }
        //  Since picks is a SortedSet its toString() method will be ordered.
        System.out.println(picks);
    }
}
