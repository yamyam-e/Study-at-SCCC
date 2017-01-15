import java.io.*;
import java.util.*;
/**
 * Validating the brackets in Java Source Code File
 * <p>
 * This class takes a Java source code file (e.g., the program you have just written) 
 * and validates that the brackets (parentheses ( ), brackets [ ], and braces { }) are balanced and match up.
 * <p>
 *  Member variables:
 * <p>
 * <tt>char: QUOTE_CHAR</tt> - reference to character symbolic constants for the quote.
 * <p>
 * <tt>char: APSOT_CHAR</tt> - reference to character symbolic constants for the apostrophe.
 * <p>
 * <tt>String: QUOTE</tt> - reference to string for the quote.
 * <p>
 * <tt>String: APSOT</tt> - reference to string for the apostrophe.
 * <p>
 * <tt>String: OPEN</tt> - reference to string for the open brackets "([{".
 * <p>
 * <tt>String: CLOSE</tt> - reference to string for the close brackets ")]}".
 * <p>
 * <tt>boolean: DEBUG</tt>
 * <p>
 * <tt>{@code Stack<Integer>}: st</tt> - {@code Stack<Integer>} object instance to store the index values of the OPEN characters that are pending matches with the CLOSE characters
 * <p>
 * <tt>StringBuilder: sb</tt>
 * <p>
 * @author Seongkwon Lee
 * @version 2014.03.23
 */
public class Brackets {
	// Define character symbolic constants for the quote and apostrophe.
	// This is needed since we have trouble with embedded single quotes
	// and apostrophes.
	// This is a bit of a kludge, but it works.
    private static final char QUOTE_CHAR = 0x22;
    private static final char APOST_CHAR = 0x27;
    private static final String QUOTE = "" + QUOTE_CHAR;
    private static final String APOST = "" + APOST_CHAR;
    // Define OPEN and CLOSE bracket characters. 
    private static final String OPEN = "([{";
    private static final String CLOSE = ")]}";
    // Define state of DEBUG flag.
    private static final boolean DEBUG = false;
    private Stack<Integer> st;
    private StringBuilder sb;
    private Scanner sr;
    
    /**
     * Inside of string literals (inside paired quotes) in the line will be removed.
     * @param line
     * @return
     */
	private String removeStringLiterals(String line) {
		
		this.sb = new StringBuilder(line);
		String result = sb.toString();
		
		int start = sb.indexOf(QUOTE);
		int end = sb.indexOf(QUOTE, start+1);
		
		if (start != -1 && end != -1) {
			sb.delete(start, end+1);
			removeStringLiterals(sb.toString());
			result = sb.toString();
		}
		
		return result;
	}
	
	/**
	 * Inside of character literals (inside paired apostrophes) in the line will be removed.
	 * @param line
	 * @return
	 */
	private String removeCharacterLiterals(String line) {
		
		this.sb = new StringBuilder(line);
		String result = sb.toString();
		
		int start = sb.indexOf(APOST);
		int end = sb.indexOf(APOST, start+1);
		
		if (start != -1 && end != -1) {
			sb.delete(start, end+1);
			removeCharacterLiterals(sb.toString());
			result = sb.toString();
		}
		
		return result;
	}
	
	/**
	 * Line based comments (started off with the sequence //) will be removed.
	 * @param line
	 * @return
	 */
	private String removeLineComments(String line) {
		this.sb = new StringBuilder(line);
		String result = sb.toString();
		
		int start = sb.indexOf("//");
		
		if (start != -1) {
			sb.delete(start, sb.length());
			result = sb.toString();
		}
		return result;
	}
	
	/**
	 * This method will create a Scanner object instance to read from System.in.
	 * It will also create a {@code Stack<Integer>} object instance to store the index values of the OPEN characters that are pending matches with the CLOSE characters
	 */
	public void processInput() {
	
		this.sr = new Scanner(System.in);
		this.st = new Stack<Integer>();
		String line;
					
		while(sr.hasNextLine()) {
			line = sr.nextLine();// read a line
			line = removeStringLiterals(line);// remove string in the quote
			line = removeCharacterLiterals(line);// remove characters in the apostrophe
			line = removeLineComments(line);// remove line comments
			//line = line.trim();
			if (!line.equals("")) System.out.println(line);// ignore empty line after filtering
			//System.out.println(line);
			
			if (pendingMatches(line)) {
				// the checked file is balanced
				if (!sr.hasNextLine() && (st.isEmpty())) {
					report("%s.\n", "The checked file is balanced");
				}
				else if (!sr.hasNextLine() && (!st.isEmpty())) {
					char ch = OPEN.charAt(st.pop());
					report("%s; Nothing matches openning '%c'\n", 
											"The checked file is NOT balanced", ch);
				}
			}
			else break;
		}
	}
	/**
	 * This method store the index values of the OPEN characters that are pending matches with the CLOSE characters
	 * and return boolean value to decide whether or not the scanner stop reading lines.
	 * @param line
	 * @return
	 */
	private boolean pendingMatches(String line) {
		boolean result = true;
		
		for(int i = 0; i<line.length(); i++) {
			char ch = line.charAt(i);
			
			// storing open brackets
			if (OPEN.indexOf(ch)>=0) st.push(OPEN.indexOf(ch));
			
			// testing open and close bracket
			if (CLOSE.indexOf(ch)>=0) {
				int top = st.empty() ? -1 : st.pop();
				// checking close brackets that have no open bracket
				if (top <= -1) {
					debug("Nothing matches final '%c'.\n", ch);
					return result = false;	
				}
				else if (top > -1 && top != CLOSE.indexOf(ch)) {
					debug("'%c' doesn't match '%s' .\n" , ch, OPEN.charAt(top));
					return result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Debugging message out
	 * @param fmt
	 * @param args
	 */
	private void debug(String fmt, Object... args) { 
		if (!DEBUG) System.out.printf(fmt, args);
	}
	
	/**
	 * Report message out
	 * @param fmt
	 * @param args
	 */
	private void report(String fmt, Object... args) { 
		System.out.printf(fmt, args);
	}
	
	public static void main(String[] args) throws IOException {
		Brackets bk = new Brackets();
		bk.processInput();
	}
}
