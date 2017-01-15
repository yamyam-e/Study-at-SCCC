/**
 *	Calculator
 *	@author Seongkwon Lee
 *	@version 15 May 2012
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Calculator
	extends JFrame
	implements ActionListener
{
	private final JTextField tf, mSign;
	private String memoryNum = "0.";
	private boolean point, operator, memory;
	private static final ArrayList<String> inputStr = new ArrayList<String>(5);

	public Calculator(String title)
	{
		super(title);
		JFrame f = new JFrame();
		this.setSize(450,250);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton bt[] = new JButton[27];

		String[] str = {"MC","MR","MS","M+",
						"Backspace","CE","C",
						"7","8","9","/","sqrt",
		   	   		    "4","5","6","*","%",
					    "1","2","3","-","1/x",
					    "0","+/-",".","+","="};

		JPanel group = new JPanel(new BorderLayout());

		// TextField
		JPanel group1 = new JPanel(new BorderLayout());
			tf = new JTextField("0.", 45);
				tf.setEnabled(false);
				tf.setHorizontalAlignment(tf.RIGHT);
		group1.add(tf, BorderLayout.NORTH);

		// All buttons & M sign
		JPanel group2 = new JPanel(new BorderLayout());

			// M sign and MC, MR, MS, M+
			JPanel westOfGroup2 = new JPanel(new GridLayout(5,1,5,5));

				// M sign
				mSign = new JTextField();
					mSign.setEnabled(false);
					mSign.setHorizontalAlignment(tf.CENTER);
					mSign.setBackground(Color.WHITE);
					westOfGroup2.add(mSign);

				// MC, MR, MS, M+
				for(int i=0; i<4; i++) {
					bt[i] = new JButton(str[i]);
					westOfGroup2.add(bt[i]);
				}
		group2.add(westOfGroup2,"West");

			// Space between westOfGrop2 and centerOfGroup2 on group2
			JPanel base = new JPanel(new BorderLayout());
				JPanel space = new JPanel();
				base.add(space,	BorderLayout.WEST);

				// Backspace, CE, C, numbers, operators
				JPanel centerOfGroup2 = new JPanel(new GridLayout(5,1,5,5));

					// Backspace, CE, C
					JPanel pl1 = new JPanel(new GridLayout(1,3,5,5));
						for(int i=4; i<7; i++) {
							bt[i] = new JButton(str[i]);
							pl1.add(bt[i]);
						}
					centerOfGroup2.add(pl1);

					// 7, 8, 9, /, sqrt
					JPanel pl2 = new JPanel(new GridLayout(1,5,5,5));
						for(int i=7; i<12; i++) {
							bt[i] = new JButton(str[i]);
							pl2.add(bt[i]);
						}
					centerOfGroup2.add(pl2);

					// 4, 5, 6, *, %
					JPanel pl3 = new JPanel(new GridLayout(1,5,5,5));
						for(int i=12; i<17; i++) {
							bt[i] = new JButton(str[i]);
							pl3.add(bt[i]);
						}
					centerOfGroup2.add(pl3);

					// 1, 2, 3, -, 1/x
					JPanel pl4 = new JPanel(new GridLayout(1,5,5,5));
						for(int i=17; i<22; i++) {
							bt[i] = new JButton(str[i]);
							pl4.add(bt[i]);
						}
					centerOfGroup2.add(pl4);

					// 0, +/-, ., +, =
					JPanel pl5 = new JPanel(new GridLayout(1,5,5,5));
						for(int i=22; i<str.length; i++) {
							bt[i] = new JButton(str[i]);
							pl5.add(bt[i]);
						}
					centerOfGroup2.add(pl5);

			base.add(centerOfGroup2);
		group2.add(base);

		group.add(group1, BorderLayout.NORTH);
		group.add(group2);

		// set bt Color and set bt Action
		for (int i=0; i<bt.length; i++) {

			if (("BackspaceCECMCMRMSM+".indexOf(str[i])) >= 0)
				bt[i].setForeground(Color.RED);
			else if (("+-*/=".indexOf(str[i])) >= 0)
				bt[i].setForeground(Color.RED);
			else
				bt[i].setForeground(Color.BLUE);

			bt[i].setActionCommand(str[i]);
			bt[i].addActionListener(this);
		}

		this.add(group);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		String cmd = ae.getActionCommand();

		if ((tf.getText().equals("Invalid input for function."))||
			(tf.getText().equals("Cannot divide by zero."))||
			(tf.getText().equals("Result of function is undefined."))){
			tf.setText("0.");
			inputStr.clear();
		}

		if (isNumber(cmd)) {
			tf.setText(addValue(cmd));
			operator = false;
		}
		else if (cmd.equals("Backspace")) {
			Backspace();
		}
		else if (cmd.equals("CE")) {
			tf.setText("0.");
			point = false;
			operator = true;
		}
		else if (cmd.equals("C")) {
			tf.setText("0.");
			inputStr.clear();
			point = false;
		}
		else if (cmd.equals("MC")) {
			mSign.setText("");
			memoryNum = "0.";
			inputStr.clear();
		}
		else if (cmd.equals("MR")) {
			if (hasMemory()) tf.setText(hideAfterPoint(memoryNum));
			operator = true;
			point = false;
		}
		else if (cmd.equals("MS")) {
			memoryNum = tf.getText();
			if (hasMemory())	mSign.setText("M");
			else 				mSign.setText("");
			operator = true;
			point = false;
			inputStr.clear();
		}
		else if (cmd.equals("M+")) {
			if (inputStr.isEmpty()) {
				if (!hasMemory())	memoryNum = tf.getText();
				else 				memoryNum = getString(getNumber(memoryNum)
												+ getNumber(tf.getText()));
			}
			else {
				inputStr.set(2,tf.getText());

				String temp = null;
				if (inputStr.get(1).equals("+"))
					temp = addition(inputStr.get(0), inputStr.get(2));
				if (inputStr.get(1).equals("-"))
					temp = substraction(inputStr.get(0), inputStr.get(2));
				if (inputStr.get(1).equals("*"))
					temp = multiplication(inputStr.get(0), inputStr.get(2));
				if (inputStr.get(1).equals("/"))
					temp = division(inputStr.get(0), inputStr.get(2));

				tf.setText(temp);

				memoryNum = getString(getNumber(memoryNum) + getNumber(tf.getText()));

				inputStr.clear();
			}

			if (hasMemory()) mSign.setText("M");
			else 		     mSign.setText("");
			operator = true;
			point = false;
		}
		else if (cmd.equals("+/-")) {
			tf.setText(negative());
			operator = true;
		}
		else if (cmd.equals("sqrt")) {
			tf.setText(sqrt());
			operator = true;
		}
		else if (cmd.equals("%")) {
			if (!inputStr.isEmpty()) {
				inputStr.set(2,tf.getText());
				if (!inputStr.get(3).equals("%")) {
					inputStr.set(3,"%");
					tf.setText(percent());
				}
			}
			operator = true;
		}
		else if (cmd.equals("1/x")) {
			tf.setText(divisionX());
			operator = true;
		}
		else if (cmd.equals("/")) {
			process(cmd);
			operator = true;
			point = false;
		}
		else if (cmd.equals("*")) {
			process(cmd);
			operator = true;
			point = false;
		}
		else if (cmd.equals("-")) {
			process(cmd);
			operator = true;
			point = false;
		}
		else if (cmd.equals("+")) {
			process(cmd);
			operator = true;
			point = false;
		}
		else if (cmd.equals("=")) {

			if (!inputStr.isEmpty()) {
				// The first time equal
				if (inputStr.get(2).equals("empty")) {
					inputStr.set(2, tf.getText());
					inputStr.set(3,"=");
				}
				// The second time equal
				// If user input a equal continously, it will calculate number with same 2st number
				// and operator
				if (inputStr.get(1).equals("+"))
					inputStr.set(0,addition       (inputStr.get(0), inputStr.get(2)));
				if (inputStr.get(1).equals("-"))
					inputStr.set(0,substraction   (inputStr.get(0), inputStr.get(2)));
				if (inputStr.get(1).equals("*"))
					inputStr.set(0,multiplication (inputStr.get(0), inputStr.get(2)));
				if (inputStr.get(1).equals("/"))
					inputStr.set(0,division       (inputStr.get(0), inputStr.get(2)));

				tf.setText(inputStr.get(0));
			}
			point = false;
		}
	}

	/**
	 *	This method erases a number on Textfield.
	 */
	public void Backspace()
	{
		String result = tf.getText();

		if (result.length()>1) {
			if (result.lastIndexOf(".")==result.length()-1) {
				result = result.substring(0,result.length()-2) + ".";
				point = false;
				if ((result.length()==1)&&(result.equals(".")))
					result = "0.";
			}
			else {
				result = result.substring(0,result.length()-1);
			}
			tf.setText(result);
		}
		else tf.setText("0.");
	}
	
	/**
	 *	This method selects a operator.
	 *	
	 *	If ArrayList inputStr is empty, it saves first number and operator
	 *	If user inputs a operator countinously, it will change the operator into new operaotr 
	 *  and will not calculator
	 *	If ArrayList inputStr has a equal, it will erase the equal and set new operator
	 */	
	public void process(String a)
	{
		if (inputStr.isEmpty()) {
			tf.setText(hideAfterPoint(tf.getText()));
			inputStr.add(0,tf.getText());

			if (a.equals("+"))	inputStr.add(1,"+");
			if (a.equals("-"))	inputStr.add(1,"-");
			if (a.equals("*"))	inputStr.add(1,"*");
			if (a.equals("/"))	inputStr.add(1,"/");

			inputStr.add(2,"empty");
			inputStr.add(3,"operator");
		}
		else if (operator) inputStr.set(1, a);
		else {
			if (inputStr.get(3).equals("=")) {
				inputStr.set(2,"empty");
				inputStr.set(3,"operator");
				inputStr.set(1,a);
			}
			else {
				inputStr.set(2,tf.getText());

				if (inputStr.get(1).equals("+"))
					inputStr.set(0,addition       (inputStr.get(0), inputStr.get(2)));
				if (inputStr.get(1).equals("-"))
					inputStr.set(0,substraction   (inputStr.get(0), inputStr.get(2)));
				if (inputStr.get(1).equals("*"))
					inputStr.set(0,multiplication (inputStr.get(0), inputStr.get(2)));
				if (inputStr.get(1).equals("/"))
					inputStr.set(0,division       (inputStr.get(0), inputStr.get(2)));

				inputStr.set(1,a);
				tf.setText(inputStr.get(0));

				inputStr.set(2,"empty");
				inputStr.set(3,"operator");
			}
		}
	}

	/**
	 *	This method shows a number on the Textfield.
	 *	
	 *	Before user inputs a operator
	 *	If user input a point before a number, it will show "0." 
	 *	
	 *	After user inputs a operator
	 *	If user input a point before a number, it will show "0." 
	 *
	 *	Before user inputs a point
	 *	If user input a number, it will multiply Textfield by 10 then add String a
	 *
	 * 	After user inputs a point
	 *	If user input a number, it will add String a to Textfield.
	 */
	public String addValue(String a)
	{
		String result = null;

		if ((operator)&&(point)) {
			if (a.equals(".")) {	result = "0.";		}
			else 			   {	result = a + ".";	}
		}
		else if ((operator)&&(!point)) {
			result = a + ".";
		}
		else if ((!operator)&&(point)) {
			if (a.equals(".")) {	result = tf.getText();		}
			else 			   {	result = tf.getText() + a;	}
		}
		else {
			String temp = tf.getText();
			int tempInt
				= Integer.parseInt(temp.substring(0,temp.length()-1)) * 10 + Integer.parseInt(a);
			result =  Integer.toString(tempInt) + ".";
		}

		return result;
	}

	/**
	 *	This method changes String a into Double.
	 *
	 *	If String a is integer, this method will erase the point and change String a into Double.
	 */
	public double getNumber(String a) {
		if (a.indexOf(".") == a.length()-1)	a = a.substring(0,a.length()-1);
		return Double.parseDouble(a);
	}

	/**
	 *	This method returns Double a into String
	 */
	public String getString(Double a) { return Double.toString(a); }

	/**
	 *	This method hides 0 if a String is integer
	 *
	 *	If String a is not Double, it is erase 0 after the point
	 */
	public String hideAfterPoint(String a)
	{
		String result = a;
		if (!isDouble(a)) result = a.substring(0,a.indexOf(".")+1);
		return result;
	}
	
	/**
	 *	This method is "+/-" operator
	 */
	public String negative()
	{
		double temp = getNumber(tf.getText());
		if (temp != 0)	temp = -temp;
		return hideAfterPoint(getString(temp));
	}

	/**
	 *	This method is "sqrt" operator
	 */
	public String sqrt()
	{
		double temp = getNumber(tf.getText());

		try {
			if (temp < 0)	throw new Exception("Invalid input for function.");
			temp = Math.sqrt(temp);
			return hideAfterPoint(getString(temp));
		}
		catch (Exception e) { return e.getMessage(); }
	}

	/**
	 *	This method is "%" operator
	 */
	public String percent()
	{
		double temp = getNumber(tf.getText());//num2
		double num1 = getNumber(inputStr.get(0));

		if (inputStr.get(1).equals("+")) temp = num1 + num1*temp/100;
		if (inputStr.get(1).equals("-")) temp = num1 - num1*temp/100;
		if (inputStr.get(1).equals("*")) temp = num1 * num1*temp/100;
		if (inputStr.get(1).equals("/")) temp = num1 / (num1*temp/100);

		return hideAfterPoint(getString(temp));
	}

	/**
	 *	This method is "1/X" operator
	 */
	public String divisionX()
	{
		double temp = getNumber(tf.getText());

		try {
			if (temp == 0)	throw new Exception("Cannot divide by zero.");
			temp = 1/temp;
			return hideAfterPoint(getString(temp));
		}
		catch (Exception e) { return e.getMessage(); }
	}

	/**
	 *	This method is "/" operator
	 */
	public String division(String a, String b)
	{
		try {
			if ((getNumber(a)!=0)&&(getNumber(b)==0)) 
				throw new Exception("Cannot divide by zero.");
			if ((getNumber(a)==0)&&(getNumber(b)==0)) 
				throw new Exception("Result of function is undefined.");
				
			return hideAfterPoint(getString(getNumber(a)/getNumber(b)));
		}
		catch (Exception e) { return e.getMessage(); }
	}
	
	/**
	 *	This method is "X" operator
	 */
	public String multiplication(String a, String b)
	{
		return hideAfterPoint(getString(getNumber(a) * getNumber(b)));
	}

	/**
	 *	This method is "-" operator
	 */
	public String substraction(String a, String b)
	{
		return hideAfterPoint(getString(getNumber(a) - getNumber(b)));
	}
	
	/**
	 *	This method is "+" operator
	 */
	public String addition(String a, String b)
	{
		return hideAfterPoint(getString(getNumber(a) + getNumber(b)));
	}

	/**
	 *	This method confirms a memoryNum whether or not it is 0
	 *
	 *	return value is true then memoryNum is not 0
	  *	return value is false then memoryNum is 0
	 */
	public boolean hasMemory()
	{
		boolean result = false;
		if (getNumber(memoryNum) != 0) result = true;
		return result;
	}

	/**
	 *  This method checks a String whether or not it is number
	 *
	 *	return value is true then a is Number
	 *	If String a is point then point flag is on
	 */
	public boolean isNumber(String a)
	{
		boolean result = false;

		if (("0123456789.".indexOf(a)) != -1)	result = true;
		if (a.equals(".")) 					  	point  = true;

		return result;
	}
	
	/**
	 *	This method distinguishes between Double and Integer
	 *
	 *	return value is true, String a is Double
	 *	return value is false, String a is Integer
	 */
	public boolean isDouble(String a)
	{
		boolean result = false;
		if ((getNumber(a)%1) != 0) result = true;
		return result;
	}
	
	/**
	 *	This is a main method for this class
	 */
	public static void main(String[] args)
	{
		new Calculator("Calculator").setVisible(true);
	}
}