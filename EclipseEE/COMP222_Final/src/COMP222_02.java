import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class COMP222_02 {

//	static HashMap<String, String> map = new HashMap<String, String>();
	static List<Character> listVowel = new ArrayList<Character>();
	static List<String> listConsonantTogether = new ArrayList<String>();
	
	public static void main(String[] args) {

		// Read inputs
		List<String> inputs = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try {
			while ((str = reader.readLine()) != null) {
				inputs.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		inputs.add("Word processors often split a word across lines using hyphenation, a technique requiring some knowledge of where the syllables in the word are divided.");
		inputs.add("The rules given in this problem are a bit crude. But they represent a good starting point.");
		
		configureMaps();
		
		List<String> outputs = addHyphen(inputs);
		
		System.out.println("=========================== Answer: ===========================");
		for (String item : outputs) {
			System.out.println(item);
		}
	}

	static List<String> addHyphen(List<String> inputs) {		
		List<String> words = new ArrayList<String>();
		List<String> outputs = new ArrayList<String>();
		for (String sentence : inputs) {
			words.addAll(Arrays.asList(sentence.split(" ")));
		}
		
		StringBuilder sb;
		Structure structure;
		for (String word : words) {
//			System.out.println(word);
			// Add hyphen
			sb = new StringBuilder(80);
			for (int i = 0; i < word.length(); i++) {
				structure = nextStructure(word.substring(i));
				if (structure.type.equals("VCCV")) {				// VCCV pattern +4
					sb.append(structure.V1);
					sb.append(structure.C1);
					sb.append("-");
					sb.append(structure.C2);
					i += structure.C1.length() + structure.C2.length();	//+1 for the loop
				} else if (structure.type.equals("VCV")) {			// VCV pattern +3
					sb.append(structure.V1);
					sb.append("-");
					sb.append(structure.C1);
					i += structure.C1.length();	//+1 for the loop
				} else {											// Nothing +1
					sb.append(word.charAt(i));
				}
			}
			outputs.add(sb.toString());
		}
		return outputs;
	}
	
	static Structure nextStructure(String remaining) {
//		System.out.println("-->" + remaining);

		Structure structure = new Structure(null, null, null, null, "none");
		if (!remaining.isEmpty() && isVowel(remaining.charAt(0))) {							// V1
			structure.V1 = "" + remaining.charAt(0);
			remaining = remaining.substring(1);
			if (!remaining.isEmpty() && hasConsonant(remaining)) {							// C1
				String C1 = getConsonant(remaining);
				structure.C1 = C1;
				remaining = remaining.substring(C1.length());
				if (!remaining.isEmpty() && hasConsonant(remaining)) {						// C2
					String C2 = getConsonant(remaining);
					structure.C2 = C2;
					remaining = remaining.substring(C2.length());
				}
//				System.out.println(">>" + remaining + "<<");
				if (!remaining.isEmpty() && isVowel(remaining.charAt(0))) {					// V2					
					structure.V2 = "" + remaining.charAt(0);
					remaining = remaining.substring(1);
				}
			}
		}
		if (structure.V1!=null && structure.C1!=null && structure.V2!=null) {
			structure.type = "VCV";													// set VCV
			if (structure.C2!=null) {
				structure.type = "VCCV";												// set VCCV
			}
		}
		if (remaining.isEmpty() && structure.type.equals("VCV")) {
			structure.type = "none";														// word end with e
		}
//		System.out.println("return structure: " + structure.V1 + "; " + structure.C1 + "; " + structure.C2 + "; " + structure.V2);
		return structure;

	}
	
	static void configureMaps() {
		listVowel.add('a');
		listVowel.add('A');
		listVowel.add('e');
		listVowel.add('E');
		listVowel.add('i');
		listVowel.add('I');
		listVowel.add('o');
		listVowel.add('O');
		listVowel.add('u');
		listVowel.add('U');
		listVowel.add('y');
		listVowel.add('Y');
		
		listConsonantTogether.add("qu");
		listConsonantTogether.add("tr");
		listConsonantTogether.add("br");
		listConsonantTogether.add("str");
		listConsonantTogether.add("st");
		listConsonantTogether.add("sl");
		listConsonantTogether.add("bl");
		listConsonantTogether.add("cr");
		listConsonantTogether.add("ph");
		listConsonantTogether.add("ch");
	}
	
	static boolean isVowel(Character item) {
		if (listVowel.contains(item)) {
			return true;
		} else {
			return false;
		}
	}
	
	static boolean hasConsonant(String remaining) {
		for (String str : listConsonantTogether) {
			if (remaining.startsWith(str)) {
				return true;
			}
		}
		char item = remaining.charAt(0);
		if (((item > 64 && item < 91) || (item > 96 && item < 123)) && !isVowel(item)) {
			return true;
		}
		return false;
	}
	
	static String getConsonant(String remaining) {
		for (String str : listConsonantTogether) {
			if (remaining.startsWith(str)) {
				return str;
			}
		}
		char item = remaining.charAt(0);
		if (((item > 64 && item < 91) || (item > 96 && item < 123)) && !isVowel(item)) {
			return ""+item;
		}
		return "";
	}
}

class Structure {
	public String V1;
	public String C1;
	public String C2;
	public String V2;
	public String type;				// VCCV, VCV, none
	public Structure (String v1, String c1, String c2, String v2, String type) {
		this.V1 = v1;
		this.C1 = c1;
		this.C2 = c2;
		this.V2 = v2;
		this.type = type;
	}
}