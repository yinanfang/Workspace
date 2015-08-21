import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class test02 {

	static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	static String input = null;
	static FileProcess test = null;
	static boolean has_confirm_data = false;
	public enum State_Machine {
		FROM, TO, DATA, END
	};

	static State_Machine state_machine = State_Machine.FROM;
	static String message = null;

	public static void main(String[] args) {
		try {
			// FileProcess test = new FileProcess(args[0], true);
			test = new FileProcess("input.txt", true);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
		
		while (true) {
			System.out.println("=============");
			
			message = test.readLine();
			if (!(state_machine==State_Machine.END)){
				parse_message();
			}
			System.out.println("==>" + message);

			try {
				if (!(state_machine == State_Machine.DATA && has_confirm_data)){	
					input = buffer.readLine();
					System.out.println(input);
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
		String responese_for_FROM_TO_and_END = "^250[\\s].+";
		String responese_for_DATA = "^354[\\s].+";

		if (state_machine == State_Machine.FROM) {
			if (input.matches(responese_for_FROM_TO_and_END)) {
				state_machine = State_Machine.TO;
				return true;
			}
		} else if (state_machine == State_Machine.TO) {
			if (input.matches(responese_for_FROM_TO_and_END)){
				System.out.println("DATA");
				state_machine = State_Machine.DATA;
				return true;
			}
		} else if (state_machine == State_Machine.DATA) {
			if (!has_confirm_data && !input.matches(responese_for_DATA)){
				return false;
			}
			
			return true;
		} else if (state_machine == State_Machine.END) {
			if (input.matches(responese_for_FROM_TO_and_END)){
				state_machine = State_Machine.FROM;
				return true;
			}
		}
		return false;
	}
	
	private static void parse_message() {
		String pattern_FROM = "^From:[\\s].+";
		String echo = message;

		if (state_machine == State_Machine.FROM) {
			echo = message.replace("From", "MAIL FROM");
		} else if (state_machine == State_Machine.TO) {
			echo = message.replace("To", "RCPT TO");
		} else if (state_machine == State_Machine.DATA) {
			echo = message;
			if (message.matches(pattern_FROM)){
				System.out.println(".");
				state_machine = State_Machine.END;
			}
		}
		message = echo;
	}


}
