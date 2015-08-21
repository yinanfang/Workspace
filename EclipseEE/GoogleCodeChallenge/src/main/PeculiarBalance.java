package main;
import java.util.*;

public class PeculiarBalance {

	public static String[] answer(int x) {
		System.out.println("Input x: " + x);
		
		if (x==0) {
			return new String[]{};
		}
		ArrayList<String> ternary = new ArrayList<String>();
		double input = x;
		
		
		//0		1		2		3		4		5		6		7		8		9		10		11
		//000	001		002		010		011		012		020		021		022		100		101		102
		// Get the number of ternary digits
		double numberOfTernaryDigits = 0;
		while (input / Math.pow(3, numberOfTernaryDigits) >= 1) numberOfTernaryDigits++;
		System.out.println("numberOfTernaryDigits: " + numberOfTernaryDigits);
		
		// Turn decimal to ternary
		double index = numberOfTernaryDigits;
		while (index>=0) {
			System.out.println("Input remains input: " + input);
			double powerOfThree = (int) Math.pow(3, index);
			if (2*powerOfThree<=input) {
				ternary.add("2");
				input -= 2*powerOfThree;
			} else if (powerOfThree<=input) {
				ternary.add("1");
				input -= powerOfThree;
			} else {
				ternary.add("0");
			}
			index--;
		}
		// Take out preceeding 0 if there's one
		if (ternary.get(0).equals("0")) {
			ternary.remove(0);
		}
		System.out.println("ternary: " + ternary);
		
		// Reverse the ternary list
		Collections.reverse(ternary);

		System.out.println("Reverse ternary: " + ternary);
		
		// Generate a strategy for our rabbit
		ArrayList<String> strategy = new ArrayList<String>();
		int carry = 0;
		for (int i = 0; i < ternary.size(); i++) {
			System.out.println("In for loop");
			int digit = Integer.parseInt(ternary.get(i)) + carry;
			System.out.println("This digit: " + digit);
			switch (digit) {
			case 0:
				strategy.add("-");
				carry = 0;
				break;
			case 1:
				strategy.add("R");
				carry = 0;
				break;
			case 2:
				strategy.add("L");
				carry = 1;
				break;
			case 3:
				strategy.add("-");
				carry = 1;
				break;
			}
		}
		if (carry==1) {
			strategy.add("R");
		}
		
		System.out.println("Finished strategy: " + strategy);
		
		return strategy.toArray(new String[strategy.size()]);
	}	
	
}

/*
readme.txt 
Peculiar balance
================

Can we save them? Beta Rabbit is trying to break into a lab that contains the only known zombie cure - but there's an obstacle. The door will only open if a challenge is solved correctly. The future of the zombified rabbit population is at stake, so Beta reads the challenge: There is a scale with an object on the left-hand side, whose mass is given in some number of units. Predictably, the task is to balance the two sides. But there is a catch: You only have this peculiar weight set, having masses 1, 3, 9, 27, ... units. That is, one for each power of 3. Being a brilliant mathematician, Beta Rabbit quickly discovers that any number of units of mass can be balanced exactly using this set.

To help Beta get into the room, write a method called answer(x), which outputs a list of strings representing where the weights should be placed, in order for the two sides to be balanced, assuming that weight on the left has mass x units.

The first element of the output list should correspond to the 1-unit weight, the second element to the 3-unit weight, and so on. Each string is one of: 

"L" : put weight on left-hand side 
"R" : put weight on right-hand side 
"-" : do not use weight 

To ensure that the output is the smallest possible, the last element of the list must not be "-".

x will always be a positive integer, no larger than 1000000000.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (int) x = 2
Output:
    (string list) ["L", "R"]

Inputs:
    (int) x = 8
Output:
    (string list) ["L", "-", "R"]

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.
*/