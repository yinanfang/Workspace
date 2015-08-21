package main;

import java.util.*;

public class ZombitMonitoring {

	public static int answer(int[][] intervals) {
		System.out.println("=======================");
		// Input check
		if (intervals==null || intervals.length==0) {
			return 0;
		}
		if (intervals.length==1) {
			return intervals[0][1] - intervals[0][0];
		}
		
		// Sort
		Arrays.sort(intervals, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		
		for(int i = 0; i < intervals.length; i++) {   
		    System.out.print("#" + i + ": [" + intervals[i][0] + ", " + intervals[i][1] + "] ");
		}  
		System.out.println();
		
		// Merge intervals
		int[] prev = intervals[0];
		ArrayList<int[]> merge = new ArrayList<int[]>();
		for (int i = 1; i < intervals.length; i++) {
			int[] current = intervals[i];
			if (current[0]<=prev[1] && current[1]>prev[1]) {
				int[] newPrev = new int[]{prev[0], current[1]};
				prev = newPrev;
			} else if (current[0]<=prev[1] && current[1]<=prev[1]) {
				// Ignore this item
			} else {
				merge.add(prev);
				prev = current;
			}
		}
		merge.add(prev);
		
		for(int i = 0; i < merge.size(); i++) {   
		    System.out.print("#" + i + ": [" + merge.get(i)[0] + ", " + merge.get(i)[1] + "] ");
		}  
		System.out.println();
		
		// Calculate covered range
		int coverage = 0;
		for (int[] item : merge) {
			coverage += item[1] - item[0];
		}
		
		return coverage;
	}
}

/*
readme.txt 
Zombit monitoring
=================

The first successfully created zombit specimen, Dolly the Zombit, needs constant monitoring, and Professor Boolean has tasked the minions with it. Any minion who monitors the zombit records the start and end times of their shifts. However, those minions, they are a bit disorganized: there may be times when multiple minions are monitoring the zombit, and times when there are none!

That's fine, Professor Boolean thinks, one can always hire more minions... Besides, Professor Boolean can at least figure out the total amount of time that Dolly the Zombit was monitored. He has entrusted you, another one of his trusty minions, to do just that. Are you up to the task?

Write a function answer(intervals) that takes a list of pairs [start, end] and returns the total amount of time that Dolly the Zombit was monitored by at least one minion. Each [start, end] pair represents the times when a minion started and finished monitoring the zombit. All values will be positive integers no greater than 2^30 - 1. You will always have end > start for each interval.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (int) intervals = [[1, 3], [3, 6]]
Output:
    (int) 5

Inputs:
    (int) intervals = [[10, 14], [4, 18], [19, 20], [19, 20], [13, 20]]
Output:
    (int) 16

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.
*/