import static org.junit.Assert.*;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A Calculator Using Stack and Applying PEMDAS Rule
 * <p>
 * <ul>
 * <li>It will accept arithmetic expressions and execute them, one by one, to produce a final result.</li>
 * <li>The expressions can contain assignment statements.</li>
 * <li>The expression will be evaluated.</li>
 * <li>The value of the expression, once evaluated, will be displayed to the user and another prompt will be presented.</li>
 * <li>Numeric literals and variable names, alone, may also be used as expressions and their values will be displayed.</li>
 * <li>If, in response to the prompt, the user just enters a return (or new line) character the program will terminate.</li>
 * </ul>
 * <p>
 * @author Seongkwon Lee
 * @version 2014.05.14
 */

public class Compiler {
	
	//  Declare and define Operator Priority map.
    public static final Map<Character, Integer> OPR_PRIO;
    private static boolean DEBUG = false;
    private static Stack<Token> operands = new Stack<>();
    private static Stack<OprToken> operators = new Stack<>();

    static {
        OPR_PRIO = new HashMap<>();
        OPR_PRIO.put(')', 5);
        OPR_PRIO.put('^', 4);
        OPR_PRIO.put('*', 3);
        OPR_PRIO.put('/', 3);
        OPR_PRIO.put('%', 3);
        OPR_PRIO.put('+', 2);
        OPR_PRIO.put('-', 2);
        OPR_PRIO.put('=', 1);
        OPR_PRIO.put('(', 0);
    }
    
    /**
	 * Main method.
	 * <p>
	 * <ol>
	 * <li>The input will consist of a series of text lines containing expressions written to the specified standards.</li>
	 * <li>The lines will contain tokens (numeric literals, variable names, operators, punctuation characters – in this case parentheses used for grouping).</li>
	 * <li>The main method process the input line to replace each operator with the character sequence: space operator space.</li>
	 * <li>After replacing each operator, this method call pushSatck which has string array argument.
	 * </ol>
	 * 
	 * @param args Command line argument strings.
	 */
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//Scanner sc = new Scanner(System.in);
		String orig, line;
	    
	    while((orig=br.readLine())!=null) {
	    	
	    	if (orig.length()==0) break;//This program is ended by enter key
	    	
    		line = orig.replaceAll("([-+*/%^()=])", " $1 ");
    		String[] tokens = line.split("\\s+");
    		
    		pushStack(tokens);
    		
	    }//while
	    
	    DEBUG(">>>  orig. line: %s%n", orig);
	}
	
	/**
	 * From an array of tokens stack operands and operators.
	 * @param tokens  An array of Strings containing operands and operators.
	 */
	private static void pushStack(String[] tokens) {
		
		for(int i = 0;i<tokens.length;i++) {
			char first = tokens[i].charAt(0);
			char topOpr = '\0';
			
			 if (!operators.empty()) {//push or calculate from third token to the end
		          topOpr = (char)operators.peek().eval();
		          
		          if (OPR_PRIO.containsKey(first)) {
			          if (OPR_PRIO.get(first) > OPR_PRIO.get(topOpr)) {
			        	  
			        	  //performing all pending operations until the open parenthesis is found.
			        	  //push operators in the stack except ')'
			        	  //pop '(' from the stack after calculate between '(' and ')'
			        	  if (OPR_PRIO.get(first)!=5) pushOperator(first);
			        	  else {
			        		  doCalculate();
			        		  if (operators.peek().eval()=='(') operators.pop();
			        	  }
			          }
			          else if (OPR_PRIO.get(first)==0){//push '(' without doing anything
			        	  pushOperator(first);
			          }
			          else {
			        	  doCalculate();
			        	  //push new operator in the stack after calculating
			        	  pushOperator(first);			        	  
			          }
		          }
		          //push the name of variable in the stack
		          else if (tokens[i].matches("[a-zA-z]*$"))	pushOperand(tokens[i]);
		          //push the value of variable in the stack
		          else										pushOperand(Integer.parseInt(tokens[i]));
			 }//end if
			 else {//push first and second token in the stack
				 if (OPR_PRIO.containsKey(first)) 		   pushOperator(first);
				 else if (tokens[i].matches("[a-zA-z]*$")) pushOperand(tokens[i]);
				 else									   pushOperand(Integer.parseInt(tokens[i]));
			 }//end else		
		}//end for
		
		//final result print and assign
		//empty operator case:
		//example '>a => 0' if 'a' has no value
		//        '123 => 123'
		//'=' operator case:
		//assign value in left variable 
		//print result
		if (operators.isEmpty()) {
			if (operands.peek().toString().matches("(\\d+)")) REPORT(operands.pop().toString());
			else 											  REPORT(operands.pop().toString());
		}
		else while (!operators.isEmpty()) doCalculate();
		
		System.out.println();
	}
	
	/**
	 * This method pushes the name of variable in the operand stack.
	 * @param var  the name of variable.
	 */
	private static void pushOperand(String var) {
		Token tk = new VarToken(var);
		operands.push(tk);
	}
	/**
	 * This method pushes the value of variable in the operand stack.
	 * @param lit  the value of variable.
	 */
	private static void pushOperand(int lit) {
		Token tk = new LitToken(lit);
		operands.push(tk);
	}
	/**
	 * This method pushes an operator in the operator stack.
	 * @param opr  the binary character
	 */
	private static void pushOperator(char opr) {
		 OprToken ot = new OprToken(opr);
		 operators.push(ot);		
	}
	/**
	 * This method calculate and assign with operand and operator.
	 * If operator character is '=' then assign value to the name and show it.
	 * If operator character is not '=' then push value in the operand stack.
	 */
	private static void doCalculate() {
		Token right = operands.pop();
 	  	char opr = (char)operators.pop().eval();
 	  	Token left = operands.pop();
 	  	ExprToken et = new ExprToken(left, opr, right);
 	  	
 	  	if (opr == '=') {//final result assign and print
 	  		et.assign(right.eval());
 	  		REPORT(et.toString());
 	  	}
 	  	else {//push calculating result in the stack
 	  		int value = et.apply(left.eval(), opr, right.eval());
 	  		pushOperand(value);
 	  	}
	}

	private static void DEBUG(String fmt, Object... args)
    { if (DEBUG) System.err.printf(fmt, args); }
    
	private static void REPORT(String fmt, Object... args)
    { System.out.printf(fmt, args); }
}

abstract class Token { public abstract int eval(); }

class LitToken extends Token {
	
	private int num;
	
	@Test
	public void main () {
		LitToken unitTest = new LitToken(1);
		int resultEval = unitTest.eval();
		String resultToString = unitTest.toString();
		assertEquals(resultEval, unitTest.eval());
		assertEquals(resultToString, unitTest.toString());
	}
	
	public LitToken(int first) { this.num = first; }

	@Override
	public int eval() { return this.num; }

	@Override
	public String toString() { return " => " + eval(); }	
}

class VarToken extends Token {
	
	private String name;
	
	@Test
	public void main () {
		VarToken unitTest = new VarToken("a");
		int resultEval = unitTest.eval();
		String resultToString = unitTest.toString();
		assertEquals(resultEval, unitTest.eval());
		assertEquals(resultToString, unitTest.toString());
	}
	
	public VarToken(String name) { this.name = name; }
	
	@Override
	public int eval() {
		if (ExprToken.SYMTAB.containsKey(this.name))
			 return ExprToken.SYMTAB.get(this.name);
		else return 0;
	}

	@Override
	public String toString() { return this.name + " => " + eval(); }	
}

class OprToken extends Token {
	
	private char oper;
	
	@Test
	public void main () {
		OprToken unitTest = new OprToken('+');
		int resultEval = unitTest.eval();
		String resultToString = unitTest.toString();
		assertEquals(resultEval, unitTest.eval());
		assertEquals(resultToString, unitTest.toString());
	}
	
	public OprToken(char ch) { this.oper = ch; }

	@Override
	public int eval() { return (int)this.oper; }

	@Override
	public String toString() { return this.oper+""; }
}

class ExprToken extends Token {
	
	public static final Map<String, Integer> SYMTAB = new HashMap<>();
	private Token left;
	private char opr;
	private Token right;
	
	@Test
	public void main () {
		Token left = new LitToken(2);
		Token right = new LitToken(2);
		ExprToken unitTest = new ExprToken(left, '^', right);
		int resultApply = unitTest.apply(left.eval(), '^', right.eval());
		int resultEval = unitTest.eval();
		String resultToString = unitTest.toString();
		assertEquals(resultApply, unitTest.apply(left.eval(), '^', right.eval()));
		assertEquals(resultEval, unitTest.eval());
		assertEquals(resultToString, unitTest.toString());
	}
	
	public ExprToken() { }
	
	public ExprToken(Token left, char opr, Token right) {
		this.left = left;
		this.opr = opr;
		this.right = right;
	}

	public int apply(int left, char opr, int right) {
		int result = 0;
		switch(opr) {
			case '-':	result = left - right;
						break;
			case '+':	result = left + right;
						break;
			case '/':	result = left / right;
						break;
			case '%':	result = left % right;
						break;
			case '*':	result = left * right;
						break;
			case '^':	result = ipow(left, right);
						break;
			case '=':	result = right;
						assign(result);
						break;
			default :	break;
		}
		return result;
	}
	
	private int ipow(int base, int exp)
    {
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) result *= base;
            exp >>= 1;
            base *= base;
        }
        return result;
    }
	
	public void assign(int value) {
		String name = this.left.toString();
		String[] tokens = name.split("\\s+");
		SYMTAB.put(tokens[0], value);
	}
	
	@Override
	public int eval() {
		String name = this.left.toString();
		String[] tokens = name.split("\\s+");
		return SYMTAB.get(tokens[0]);
	}

	@Override
	public String toString() { return " => " + eval(); }
}
