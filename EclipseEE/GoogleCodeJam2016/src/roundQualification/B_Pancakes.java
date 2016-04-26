package roundQualification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class B_Pancakes {

  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int count = in.nextInt();
    in.nextLine();
    for (int index = 1; index <= count; index++) {
      System.out.println("Case #" + index + ": " + numberOfFlips(in.nextLine()));
    }
    in.close();
  }

  static int numberOfFlips(String line) {
    int index = 0;
    int count = 0;
    char lastChar = line.charAt(index);
    while (index != line.length()) {
      if (line.charAt(index)!=lastChar) {
        count++;
      }
      lastChar = line.charAt(index);
      index++;
    }
    if (lastChar=='-') {
      count++;
    }
    return count;
  }
}
