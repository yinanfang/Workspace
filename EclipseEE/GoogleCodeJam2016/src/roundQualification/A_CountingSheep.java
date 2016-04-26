package roundQualification;

import java.util.*;
import java.io.*;

public class A_CountingSheep {
  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int count = in.nextInt();
    
    for (int index = 1; index <= count; index++) {
      long input = in.nextLong();
      if (input == 0) {
        System.out.println("Case #" + index +  ": INSOMNIA");
      } else {
        System.out.println("Case #" + index +  ": " + lastNumber(input));
      }
    }
  }
  
  static long lastNumber(long input) {
    HashMap<Long, Boolean> map = new HashMap<Long, Boolean>();
    for (int multiplier = 1; multiplier > 0; multiplier++) {
      long stage = input * multiplier;
      if (hasAllNumbers(stage, map)) {
        return stage;
      }
    }
    return input;
  }
  
  static boolean hasAllNumbers(long stage, HashMap<Long, Boolean> map) {
    while (stage != 0) {
      map.put(stage%10, true);
      if (map.size()==10) {
        return true;
      }
      stage /= 10;
    }
    return false;
  }
}
