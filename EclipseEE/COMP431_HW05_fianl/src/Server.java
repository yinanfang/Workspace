import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Server {
	static String receivedMessageLine;
	static String outgoingMessageLine;
	static ServerSocket welcomeSocket;
	static Socket connectionSocket;
	
	static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	static String input = null;
	// Set the state machine to accept RCPT FROM. It could be "from", "to", "data", "end"
	static String state_machine = "from";
	static String cmd_input = "none";
	static ArrayList<String> email_message = new ArrayList<String>();
	static ArrayList<String> rcpt_list = new ArrayList<String>();
	static boolean has_rcpt = false;
	static boolean has_bad_sequence_alert = false;
	static BufferedReader inFromClient;
	
	public static void main(String argv[]) throws Exception {

		establishServer();

		runServer();
	} // end main

	static void establishServer() {
		try {
			// Create welcoming socket using port 14851
			welcomeSocket = new ServerSocket(14851);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String receiveClientData(){
		try {
			receivedMessageLine = inFromClient.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection broken");
			e.printStackTrace();
		}
		return receivedMessageLine;
	}
	
	static void sendClientData(String data){
		DataOutputStream outToClient;
		try {
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeBytes(data + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void makeConnection(){
		try {
			connectionSocket = welcomeSocket.accept();
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			
			String hostname_server = InetAddress.getLocalHost().getHostName();
			sendClientData("220 " + hostname_server);
			
			receivedMessageLine = receiveClientData();
			String hostname_client = receivedMessageLine.replaceAll("^.+ ", "");
			
			sendClientData("250 Hello " + hostname_client + " , pleased to meet you");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	static void runServer() {
		while (true) {
			try {

				makeConnection();
				updataStateMachine("from");
								
				while(true) {
					
					// Reset variables
					has_bad_sequence_alert = false;
					
					// Start reading lines
					input = receiveClientData();
					
					if (input.equals("QUIT")) {
						String hostname_server = InetAddress.getLocalHost().getHostName();
						sendClientData("221 " + hostname_server + " closing connection");
//						connectionSocket.close();
						break;
					}
														
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
							sendClientData("250 OK");
							updataStateMachine("from");
						}
					}				
				}			
			} catch (Exception e) {
				e.printStackTrace();
				try {
					connectionSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Error from client data");
				updataStateMachine("from");
			}
		}
	}

	public static void writeToFile () {
		for (int i = 0; i < rcpt_list.size(); i++ ) {
			String rcpt = rcpt_list.get(i);
			String path = "forward/" + rcpt;
			FileProcess_Server file = new FileProcess_Server(path, true);
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
			sendClientData("354 Start mail input; end with <CRLF>.<CRLF>");
		}
	}
	
	static void recordRCPT () {
		has_rcpt = true;
		String str01 = input.replaceAll("^.+[\\@]", "");
		String str02 = str01.replaceAll("[\\>].*", "");
		if (!rcpt_list.contains(str02)){
			rcpt_list.add(str02);
		}
	}
	
	static void recordMessage(String state) {
		String content = null;
		if (state.equals("from")){
			content = input.replace("MAIL FROM", "From");
		}else if (state.equals("to")){
			content = input.replace("RCPT TO", "To");
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
				sendClientData("503 Bad sequence of commands");
				has_bad_sequence_alert = true;
			}
			return false;
		}
	}
	
	
	public static boolean CMDTypeIsCorrect () {
		if(input.matches("^((MAIL FROM)|(Mail From)):[\\s]*.+")){
			cmd_input = "from";
		}else if (input.matches("^((RCPT TO)|(Rcpt To)):[\\s]*.+")){
			cmd_input = "to";
		}else if (input.matches("^DATA[\\s]*")){
			cmd_input = "data";
		}else {
			sendClientData("500 Syntax error: command unrecognized");
			return false;
		}
		return true;
	}
	public static boolean correct_CMD_parameters () {
		String pattern_cmd = "^((MAIL FROM)|(Mail From)|(RCPT TO)|(Rcpt To)):[\\s]*";
		String pattern_local_part = "[\\<][^\\s][A-Za-z0-9._%+-]+";
		String pattern_domain = "[A-Za-z0-9]{2,}([\\.][A-Za-z0-9]{2,})*([\\.][A-Za-z]{2,})?[^\\s][\\>][\\s]*";
		
		if (!input.matches(pattern_cmd + pattern_local_part + "[\\@]" + pattern_domain)){
			// Check if it's in a pattern of "MAIL FROM: <start and end with non space char>"
			sendClientData("501 Syntax error in parameters or arguments");
			return false;
		}else{
			sendClientData("250 OK");
		}
		return true;
	}
} // end class



class FileProcess_Server {
	
	private String path;
	private boolean appen_to_file = true;
	FileReader fr;
	BufferedReader bf;
	int numberOfLines = 0;

	public FileProcess_Server (String file_path, boolean append_value) {
		this.path = file_path;
		this.appen_to_file = append_value;
	}
	
	public void readFile () {
		try{
			fr = new FileReader(path);
			bf = new BufferedReader(fr);
			
			String aLine;
			while ((aLine = bf.readLine()) != null) {
				numberOfLines++;
			}
			bf.close();	
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printFile () {
		try {
			fr = new FileReader(path);
			bf = new BufferedReader(fr);
			
			String[] arryLines = new String[numberOfLines];
			for (int i = 0; i < numberOfLines; i++ ) {
				try {
					arryLines[i] = bf.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < numberOfLines; i++) {
				System.out.println(arryLines[i]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public void writeFile(ArrayList<String> data) {
		File theDir = new File("forward");
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    boolean result = theDir.mkdir();  
		}
		try {
			FileWriter fw = new FileWriter(path, appen_to_file);
			PrintWriter pw = new PrintWriter(fw);
			for (int i = 0; i < data.size(); i++ ) {
				pw.printf("%s" + "%n", data.get(i));
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}