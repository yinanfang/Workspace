import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {

	static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	static String input = null;
	static FileProcess test = null;

	public enum State_Machine {
		FROM, TO, DATA, END
	};

	static State_Machine state = State_Machine.FROM;
	static String message = null;

	public static void main(String[] args) {
		try {
			test = new FileProcess("input.txt", true);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
		// FileProcess test = new FileProcess(args[0], true);
		while (true) {


			message = test.readLine();
			parse_message();
			System.out.println(message);

			try {
				if (!(state == State_Machine.DATA)){	
					input = buffer.readLine();
					System.err.println(input);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (!cmdIsValid()) {
				System.out.println("QUIT");
				System.exit(0);
			}
		}
	}

	private static boolean cmdIsValid() {
//		String pattern_FROM = "^MAIL FROM:[\\s].+";
//		String pattern_TO = "^RCPT TO:[\\s].+";
		String responese_for_FROM_TO_and_END = "^250[\\s].+";

		if (state == State_Machine.FROM) {
			if (input.matches(responese_for_FROM_TO_and_END)) {
				state = State_Machine.TO;
				return true;
			}
		} else if (state == State_Machine.TO) {
			if (input.matches(responese_for_FROM_TO_and_END)){
				state = State_Machine.DATA;
				return true;
			}
		} else if (state == State_Machine.DATA) {
			return true;
		} else if (state == State_Machine.END) {
			if (input.matches(responese_for_FROM_TO_and_END)){
				state = State_Machine.FROM;
				return true;
			}
		}
		
		return false;
		
//		else 
//		
//		if (state==State_Machine.FROM || state==State_Machine.TO){
//			if (message.matches(pattern_FROM_and_TO)) {
//				
//			}
//		}
//			
//		 if (input.matches(pattern_cmd)) {
//		
//		 }
//		
//		 System.out.println("valid cmd");
//		 }
//		
//		
//		 if (!input.matches(pattern_cmd + pattern_local_part + "[\\@]" +
//		 pattern_domain)){
//		 // Check if it's in a pattern of
//		 "MAIL FROM: <start and end with non space char>"
//		 System.out.println("501 Syntax error in parameters or arguments");
//		 return false;
//		 }else{
//		 System.out.println("250 OK");
//		 }
//		
	}
	
	private static void parse_message() {
		String echo = null;

		if (state == State_Machine.FROM) {
			echo = message.replace("From", "MAIL FROM");
		} else if (state.equals("to")) {
			echo = message.replace("To", "RCPT TO");
		} else if (state.equals("DATA")) {
			echo = message;
			if (message.equals(".")){
				state = State_Machine.END;
			}
		}
		message = echo;
	}


}
