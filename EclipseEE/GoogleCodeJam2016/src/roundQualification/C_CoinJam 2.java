package roundQualification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class C_CoinJam {

  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    in.nextInt();
    int length = in.nextInt();
    int numberOfCase = in.nextInt();
    String jameCointOrigin = "1" + new String(new char[length-2]).replace("\0", "0") + "1";
//    System.out.println(length);
//    System.out.println(numberOfCase);
//    System.out.println(jameCointOrigin);
    HashSet<String> pool = new HashSet<String>();
    HashSet<String> pooltmp = new HashSet<String>();
    pool.add(jameCointOrigin);
    for (int position = 1; position < length-1; position++) {
      for (String str : pool) {
        pooltmp.add(str);
        StringBuilder strBuilding = new StringBuilder(str);
        strBuilding.setCharAt(position, '1');
        pooltmp.add(strBuilding.toString());
      }
      pool = pooltmp;
      pooltmp = new HashSet<String>();
    }
//    System.out.println(pool);
    System.out.println("Case #1:");
    int currentCount = 0;
    for (String str : pool) {
//      System.out.println("dealing with " + str);
      ArrayList<Integer> divisors = new ArrayList<Integer>();
      for (int base = 2; base <= 10; base++) {
        int divisor = divisorInBase(base, str);
        if (divisor > 1) {
          divisors.add(divisor);
        } else {
//          System.out.println("breaking... ");
          break;
        }
      }
      if (divisors.size()==9) {
        System.out.println(str + " " + joinList(divisors, " "));
        if (++currentCount == numberOfCase) {
          break;
        }
      }
    }
    in.close();
  }

  static int divisorInBase(int base, String str) {
    // Convert to base
    int sum = 0;
    for (int i = 0; i < str.length(); i++) {
      int digit = Character.getNumericValue(str.charAt(i));
      sum += digit * (int) Math.pow(base, str.length()-i-1);
    }
//    System.out.println("Sum: " + sum);
    // find divisor
    for (int num = 2; num <= Math.sqrt(sum); num++) {
      if (sum % num == 0) {
//        System.out.println("divisor: " + num);
        return num;
      }
    }
    return -1;
  }
  
  static String joinList(ArrayList<Integer> list, String literal){
    return list.toString().replaceAll(",", literal).replaceAll("[\\[.\\].]", "");
 }
}
