package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BClass {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("ShortestJobFirst.txt"));
		for (String line = sc.next(); sc.hasNext(); line = sc.next()) {
			System.out.println(line);
		}

	}

}
