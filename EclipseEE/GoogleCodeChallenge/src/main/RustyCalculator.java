package main;
import java.util.*;

public class RustyCalculator {
	public static String answer(String str) {
		ArrayList<String> origin = new ArrayList<String>(Arrays.asList(str.split("")));
		ArrayList<String> buffer = new ArrayList<String>();
		for (int i = 0; i < origin.size(); i++) {
			System.out.println("===========");
			System.out.println("Before: i: " + i + "; Origin size: " + origin.size() + "; Origin content: " + getString(origin));
			if (origin.get(i).equals("*")) {
				buffer.add(origin.remove(i));
			} else if (!buffer.isEmpty()) {
				origin.addAll(i, buffer);
				i += buffer.size()-1;
				buffer.clear();
			}
			System.out.println("After: i: " + i + "; Origin size: " + origin.size() + "; Origin content: " + getString(origin));
		}
		if (!buffer.isEmpty()) {
			origin.addAll(buffer);
			buffer.clear();
		}

		System.out.println("+++++++++");

		for (int i = 0; i < origin.size(); i++) {
			if (origin.get(i).equals("+")) {
				buffer.add(origin.remove(i));
			}
		}
		if (!buffer.isEmpty()) {
			origin.addAll(buffer);
		}
		System.out.println("+++++++++");
		return getString(origin);
	}
	
	static String getString(ArrayList<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String item : list) {
			sb.append(item);
		}
		return sb.toString();
	}
}

/*
readme.txt 
Rusty calculator
================

Lab minion Rusty works for Professor Boolean, a mad scientist. He's been stuck in this dead-end job crunching numbers all day since 1969. And it's not even the cool type of number-crunching - all he does is addition and multiplication. To make matters worse, under strict orders from Professor Boolean, the only kind of calculator minions are allowed to touch is the Unix dc utility, which uses reverse Polish notation.

Recall that reverse Polish calculators such as dc push numbers from the input onto a stack. When a binary operator (like "+" or "*") is encountered, they pop the top two numbers, and then push the result of applying the operator to them.

For example:
2 3 * => 6
4 9 + 2 * 3 + => 13 2 * 3 + => 26 3 + => 29

Each day, Professor Boolean sends the minions some strings representing equations, which take the form of single digits separated by "+" or "*", without parentheses. To make Rusty's work easier, write function called answer(str) that takes such a string and returns the lexicographically largest string representing the same equation but in reverse Polish notation.

All numbers in the output must appear in the same order as they did in the input. So, even though "32+" is lexicographically larger than "23+", the expected answer for "2+3" is "23+".

Note that all numbers are single-digit, so no spaces are required in the answer. Further, only the characters [0-9+*] are permitted in the input and output.

The number of digits in the input to answer will not exceed 100.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (string) str = "2+3*2"
Output:
    (string) "232*+"

Inputs:
    (string) str = "2*4*3+9*3+5"
Output:
    (string) "243**93*5++"

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.


constraints.txt 
Java
====

Your code will be compiled using standard Java 7. It must implement the answer() method in the solution stub.

Execution time is limited. Some classes are restricted (e.g. java.lang.ClassLoader). You will see a notice if you use a restricted class when you verify your solution.

Third-party libraries, input/output operations, spawning threads or processes and changes to the execution environment are not allowed.

Python
======

Your code will run inside a Python 2.7.6 sandbox.

Standard libraries are supported except for bz2, crypt, fcntl, mmap, pwd, pyexpat, select, signal, termios, thread, time, unicodedata, zipimport, zlib.



*/

