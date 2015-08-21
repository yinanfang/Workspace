package main;

import java.util.*;

public class StringCleaning {
    public static String answer(String chunk, String word) { 
		if (chunk==null || word==null) {
			return "";
		}
		if (word.length()==0 || word.length()>chunk.length()) {
			return chunk;
		}
    				
		Set<String> candidates = new HashSet<String>();
		Set<String> set = new HashSet<String>();
		set.add(chunk);
		while (!set.isEmpty()) {
			String item = set.iterator().next();
			set.remove(item);
			if (!item.contains(word)) {
				candidates.add(item);
			} else {
				for (int i = -1; (i = item.indexOf(word, i + 1)) != -1; ) {
					String truncated = item.substring(0, i) + item.substring(i+word.length(), item.length());
					set.add(truncated);
				} 
			}
		}
    			
    	List<String> result = new ArrayList<String>();
    	result.addAll(candidates);
    	Collections.sort(result);
    	
    	System.out.print("[");
    	for (String item : result) {
    	    System.out.print(item + ", ");
    	}
    	System.out.print("]\n");
    	
		return result.get(0);
	}
}

/*
readme.txt 
String cleaning
===============

Your spy, Beta Rabbit, has managed to infiltrate a lab of mad scientists who are turning rabbits into zombies. He sends a text transmission to you, but it is intercepted by a pirate, who jumbles the message by repeatedly inserting the same word into the text some number of times. At each step, he might have inserted the word anywhere, including at the beginning or end, or even into a copy of the word he inserted in a previous step. By offering the pirate a dubloon, you get him to tell you what that word was. A few bottles of rum later, he also tells you that the original text was the shortest possible string formed by repeated removals of that word, and that the text was actually the lexicographically earliest string from all the possible shortest candidates. Using this information, can you work out what message your spy originally sent?

For example, if the final chunk of text was "lolol," and the inserted word was "lol," the shortest possible strings are "ol" (remove "lol" from the beginning) and "lo" (remove "lol" from the end). The original text therefore must have been "lo," the lexicographically earliest string.

Write a function called answer(chunk, word) that returns the shortest, lexicographically earliest string that can be formed by removing occurrences of word from chunk. Keep in mind that the occurrences may be nested, and that removing one occurrence might result in another. For example, removing "ab" from "aabb" results in another "ab" that was not originally present. Also keep in mind that your spy's original message might have been an empty string.

chunk and word will only consist of lowercase letters [a-z].
chunk will have no more than 20 characters.
word will have at least one character, and no more than the number of characters in chunk.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (string) chunk = "lololololo"
    (string) word = "lol"
Output:
    (string) "looo"

Inputs:
    (string) chunk = "goodgooogoogfogoood"
    (string) word = "goo"
Output:
    (string) "dogfood"

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.
*/