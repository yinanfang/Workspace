import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SMTP1 {

	static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	static String input = null;
	// Set the state machine to accept RCPT FROM. It could be "from", "to", "data", "end"
	static String state_machine = "from";
	static String cmd_input = "none";
	static ArrayList<String> email_message = new ArrayList<String>();
	static ArrayList<String> rcpt_list = new ArrayList<String>();
	static boolean has_rcpt = false;
	static boolean has_bad_sequence_alert = false;
	
	public static void main(String[] args) {
		
		try {
			while (true) {
				
				// Reset variables
				has_bad_sequence_alert = false;
				
				// Start reading lines
				input = buffer.readLine();
				
				// If ctrl+D is Press the content "in" will be null and will not be printed
				if (input == null) {
					break;
				}
				
				// Echo the input line
				System.out.println(input);
								
				if (state_machine.equals("from")){
					// Reset variables
					email_message = new ArrayList<String>();
					rcpt_list = new ArrayList<String>();
					
					// Parse and find out the command type
					if(CMDTypeIsCorrect()){
						if(cmd_inputEquals("from")){
							if(correct_CMD_parameters()){
								recordMessage("from");
								updataStateMachine("to");
							}
						}
					}	
				}else if (state_machine.equals("to")) {
					if(CMDTypeIsCorrect()){
						if(cmd_inputEquals("to")){
							if(correct_CMD_parameters()){
								recordRCPT();
								recordMessage("to");
							}
						}else if (has_rcpt && cmd_inputEquals("data")){
							updataStateMachine("data");
						}
					}
				}else if (state_machine.equals("data")) {
					// If user start inputing data
					if (!isEndOfEmail()) {
						recordMessage("data");
					}else{
						writeToFile();
						System.out.println("250 OK");
						updataStateMachine("from");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile () {
		for (int i = 0; i < rcpt_list.size(); i++ ) {
			String rcpt = rcpt_list.get(i);
			String path = "forward/" + rcpt;
			FileProcess file = new FileProcess(path, true);
			file.writeFile(email_message);
		}
		
	}

	public static boolean isEndOfEmail () {
		if ( input.equals(".")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void updataStateMachine(String state){
		state_machine = state;
		if (state_machine.equals("data")){
			System.out.println("354 Start mail input; end with <CRLF>.<CRLF>");
		}
	}
	
	static void recordRCPT () {
		has_rcpt = true;
		String str01 = input.replaceAll("^.+[\\<]", "");
		String str02 = str01.replaceAll("[\\>].*", "");
		rcpt_list.add(str02);
	}
	
	static void recordMessage(String state) {
		String content = null;
		if (state.equals("from")){
			content = input.replace("MAIL FROM", "FROM");
		}else if (state.equals("to")){
			content = input.replace("RCPT TO", "TO");
		}else if (state.equals("data")){
			content = input;
		}
		email_message.add(content);
	}
	
	public static boolean cmd_inputEquals (String cmd) {
		if (cmd_input.equals(cmd)){
			return true;
		}else{
			// So that user can jump into the data entering mode
			if (has_rcpt && cmd_input.equals("data")){
				return false;
			}
			if (has_bad_sequence_alert == false){
				System.out.println("503 Bad sequence of commands");
				has_bad_sequence_alert = true;
			}
			return false;
		}
	}
	
	
	public static boolean CMDTypeIsCorrect () {
		if(input.matches("^MAIL FROM:[\\s]*.+")){
			cmd_input = "from";
		}else if (input.matches("^RCPT TO:[\\s]*.+")){
			cmd_input = "to";
		}else if (input.matches("^DATA[\\s]*")){
			cmd_input = "data";
		}else {
			System.out.println("500 Syntax error: command unrecognized");
			return false;
		}
		return true;
	}
	public static boolean correct_CMD_parameters () {
		String pattern_cmd = "^((MAIL FROM)|(RCPT TO)):[\\s]*";
		String pattern_local_part = "[\\<][^\\s][A-Za-z0-9._%+-]+";
		String pattern_domain = "[A-Za-z0-9]{2,}([\\.][A-Za-z0-9]{2,})*([\\.][A-Za-z]{2,})?[^\\s][\\>][\\s]*";
		
		if (!input.matches(pattern_cmd + pattern_local_part + "[\\@]" + pattern_domain)){
			// Check if it's in a pattern of "MAIL FROM: <start and end with non space char>"
			System.out.println("501 Syntax error in parameters or arguments");
			return false;
		}else{
			System.out.println("250 OK");
		}
		return true;
	}
}
