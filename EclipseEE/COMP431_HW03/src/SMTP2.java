import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SMTP2 {
 
	static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	static String input = null;
	static FileProcess test = null;
	static boolean has_confirm_DATA = false;
	static boolean need_to_parse_FROM = true;
	public enum State_Machine {
		FROM, TO, DATA, END
	};
	static State_Machine state_machine = State_Machine.FROM;
	static String message = null;

	public static void main(String[] args) {
		// Program will terminate if the input file has error
		try {
			test = new FileProcess(args[0], true);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}

		runSMTP();
		
	}
	
	private static void runSMTP() {
		try {
			while (true) {			
				if (state_machine == State_Machine.FROM){
					// Enter process of FROM
					if (need_to_parse_FROM){
						message = test.readLine();		
					}
					parse_message();
					System.out.println(message);
					input = buffer.readLine();
					System.err.println(input);
				} else if (state_machine == State_Machine.TO){
					// Enter process of TO
					message = test.readLine();
					parse_message();
					System.out.println(message);
					input = buffer.readLine();
					System.err.println(input);
				} else if (state_machine == State_Machine.DATA){
					// Enter process of DATA
					if (!has_confirm_DATA){
						System.out.println("DATA");
						input = buffer.readLine();
						System.err.println(input);
					}else{
						if (state_machine == State_Machine.DATA){
							message = test.readLine();
							if (message == null) {
								// Reach the end of file
								System.out.println(".");
								input = buffer.readLine();
								System.err.println(input);
								// Check the last command before QUIT
								if (!cmdIsValid()) {
									System.out.println("QUIT");
									System.exit(0);
								}
								break;
							}	
							parse_message();
						}
						if (state_machine == State_Machine.DATA){	
							// Keep parsing the message without prompting response
							System.out.println(message);
						}else if (state_machine == State_Machine.END){
							// Reach the End of message
							System.out.println(".");
							input = buffer.readLine();
							System.err.println(input);
						}						
					}
				} 

				// Check the validity of Response input 
				if (!cmdIsValid()) {
					System.out.println("QUIT");
					System.exit(0);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean cmdIsValid() {
		// Two possible patterns for response inputs
		String responese_for_FROM_TO_and_END = "^250[\\s].+";
		String responese_for_DATA = "^354[\\s].+";

		if (state_machine == State_Machine.FROM) {
			if (input.matches(responese_for_FROM_TO_and_END)) {
				state_machine = State_Machine.TO;
				return true;
			}
		} else if (state_machine == State_Machine.TO) {
			if (input.matches(responese_for_FROM_TO_and_END)){
				state_machine = State_Machine.DATA;
				return true;
			}
		} else if (state_machine == State_Machine.DATA) {
			if (!has_confirm_DATA && input.matches(responese_for_DATA)){
				has_confirm_DATA = true;
				return true;
			}else if (has_confirm_DATA){
				return true;
			}
		} else if (state_machine == State_Machine.END) {
			if (input.matches(responese_for_FROM_TO_and_END)){
				state_machine = State_Machine.FROM;
				need_to_parse_FROM = false;
				has_confirm_DATA = false;
				return true;
			}
		}
		return false;
	}
	
	private static void parse_message() {
		String pattern_FROM = "^From:[\\s].+";
		String echo = message;

		// Replace the head of the message and prepare for the Stander output
		if (state_machine == State_Machine.FROM) {
			echo = message.replace("From", "MAIL FROM");
		} else if (state_machine == State_Machine.TO) {
			echo = message.replace("To", "RCPT TO");
		} else if (state_machine == State_Machine.DATA) {
			echo = message;
			if (message.matches(pattern_FROM)){
				state_machine = State_Machine.END;
			}
		}
		message = echo;
	}


}
