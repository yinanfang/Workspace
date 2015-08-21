import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class MS20150212_Palindrome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> input = new ArrayList<String>(); 
		try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/compass/Downloads/PracticeInput.txt"), "Cp1252"));         

	        String line;
	        while ((line = br.readLine()) != null) {
	            // process the line.
	        	System.out.println(line);
	        	System.out.println();
	        	input.add(line);
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		List<String> list = ArrayList<String>();
		for (String )
		
		System.out.println("++++++++++++++++++++++");
		System.out.println();

		// Start
		String max = "";
		for (String item : list) {
			int len = item.length();
			boolean[][] record = new boolean[len][len];
			max = item.substring(len-1, len);
			for (int i = len-1; i >= 0; i--) {
				for (int j = i; j <len; j++) {
					record[i][j] = true;
					char iii = item.charAt(i);
					char jjj = item.charAt(j);
					int ii = i + 1;
					int jj = j - 1;
					if (j<len) {
						if (i+1==j && item.charAt(i)==item.charAt(j)) {
							record[i][j] = true;
							String fresh = item.substring(i, j+1);
							if (fresh.length()>max.length()) {
								max = fresh;
							}
						} else if (j-i<2) {
							continue;
						} else if (record[ii][jj]==true && item.charAt(i)==item.charAt(j)) {
							record[i][j] = true;
							String fresh = item.substring(i, j+1);
							if (fresh.length()>max.length()) {
								max = fresh;
							}
						} else {
							break;
						}
					} else {
						break;
					}
				}
			}
			System.out.println(max);
			max = "";
			
		}
		
		
	}
	
	public boolean isValid(String item) {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean hasSpecialChar = p.matcher(item).find();
		return !hasSpecialChar;
	}

}
