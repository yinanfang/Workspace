import java.io.*;
import java.net.*;

class Client {
	
	static Socket clientSocket;
	static FileProcess outgoingMessageFile = null;
	static String outgoingMessageLine = null;
	static String receivedMessageLine = null;
	public enum State_Machine {
		FROM, TO, DATA, END
	};
	static State_Machine state_machine = State_Machine.FROM;
	static boolean has_confirm_DATA = false;
	static boolean need_to_parse_FROM = true;
	static boolean has_read_first_data_line = false;
	static boolean need_to_read_second_data_line = false;
	
	
	public static void main(String args[]) throws Exception {
		
		openOutgoingFile();
		
		establishConnection(args);

		runClient();
	} // end main
	
	static void openOutgoingFile(){
		try {
			outgoingMessageFile = new FileProcess("outgoing", true);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
	}
	
	static void establishConnection(String[] args){
		try {
			String hostname_server = args[0];
			int portnumber = Integer.parseInt(args[1]);
			clientSocket = new Socket(hostname_server, portnumber);
			
			justReceiveData();
			
			String hostname_client = InetAddress.getLocalHost().getHostName();
			sendAndReceiveData("HELO " + hostname_client);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot connect to the host");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error exists in hostname or portnumber");
			e.printStackTrace();
		}
	}
	
	static String sendAndReceiveData(String data){
		try {
			
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());

			outToServer.writeBytes(data + '\n');
			
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			receivedMessageLine = inFromServer.readLine();

				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//clientSocket.close();
		}
		return receivedMessageLine;
	}
	
	static void justSendData(String data){
		try {
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			outToServer.writeBytes(data + '\n');
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//clientSocket.close();
		}
	}
	
	static String justReceiveData(){
		try {
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			receivedMessageLine = inFromServer.readLine();

	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//clientSocket.close();
		}
		return receivedMessageLine;
	}
	
	private static void runClient() {
		while (true) {			
						
			if (state_machine == State_Machine.FROM){
				// Enter process of FROM
				if (need_to_parse_FROM){
					outgoingMessageLine = outgoingMessageFile.readLine();		
				}
				parse_message();
				receivedMessageLine = sendAndReceiveData(outgoingMessageLine);
			} else if (state_machine == State_Machine.TO){
				// Enter process of TO
				outgoingMessageLine = outgoingMessageFile.readLine();
				parse_message();
				receivedMessageLine = sendAndReceiveData(outgoingMessageLine);
			} else if (state_machine == State_Machine.DATA){
				// Check if there another RCPT
				if (has_read_first_data_line == false) {
					outgoingMessageLine = outgoingMessageFile.readLine();
					if (correct_RCPT_parameters()) {
						state_machine = State_Machine.TO;
						parse_message();
						receivedMessageLine = sendAndReceiveData(outgoingMessageLine);
					} else {
						has_read_first_data_line = true;
						
						// Do Data process
						// Enter process of DATA
						if (!has_confirm_DATA){
							receivedMessageLine = sendAndReceiveData("DATA");
//							printToStdError(receivedMessageLine);
						}else{
							if (state_machine == State_Machine.DATA){
								if (need_to_read_second_data_line == true){
									outgoingMessageLine = outgoingMessageFile.readLine();
								} else {
									need_to_read_second_data_line = true;
								}
								
								if (outgoingMessageLine == null) {
									// Reach the end of file
									receivedMessageLine = sendAndReceiveData(".");
									// Check the last command before QUIT
										sendAndReceiveData("QUIT");
										try {
											clientSocket.close();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										System.exit(0);
									break;
								}	
								parse_message();
							}
							if (state_machine == State_Machine.DATA){	
								// Keep parsing the message without prompting response
								justSendData(outgoingMessageLine);
							}else if (state_machine == State_Machine.END){
								// Reach the End of message
								receivedMessageLine = sendAndReceiveData(".");
							}						
						}
						
					}
				} else {
					// Do Data process
					if (!has_confirm_DATA){
						receivedMessageLine = sendAndReceiveData("DATA");
					}else{
						if (state_machine == State_Machine.DATA){
							if (need_to_read_second_data_line == true){
								outgoingMessageLine = outgoingMessageFile.readLine();
							} else {
								need_to_read_second_data_line = true;
							}
							
							if (outgoingMessageLine == null) {
								// Reach the end of file
								receivedMessageLine = sendAndReceiveData(".");
								// Check the last command before QUIT
									sendAndReceiveData("QUIT");
									try {
										clientSocket.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.exit(0);
								break;
							}	
							parse_message();
						}
						if (state_machine == State_Machine.DATA){	
							// Keep parsing the message without prompting response
							justSendData(outgoingMessageLine);
						}else if (state_machine == State_Machine.END){
							// Reach the End of message
							receivedMessageLine = sendAndReceiveData(".");
						}						
					}
				}
			} 

			// Check the validity of Response input 
			if (!cmdIsValid()) {
				sendAndReceiveData("QUIT");
				System.exit(0);
			}
		}
	}

	public static boolean correct_RCPT_parameters () {
		String pattern_cmd = "^To:[\\s]*";
		String pattern_local_part = "[\\<][^\\s][A-Za-z0-9._%+-]+";
		String pattern_domain = "[A-Za-z0-9]{2,}([\\.][A-Za-z0-9]{2,})*([\\.][A-Za-z]{2,})?[^\\s][\\>][\\s]*";
		
		if (outgoingMessageLine.matches(pattern_cmd + pattern_local_part + "[\\@]" + pattern_domain)){
			// Check if there is another RCPT
			return true;
		}
		return false;
	}
	
	private static boolean cmdIsValid() {
		// Two possible patterns for response inputs
		String responese_for_FROM_TO_and_END = "^250[\\s].+";
		String responese_for_DATA = "^354[\\s].+";

		if (state_machine == State_Machine.FROM) {
			if (receivedMessageLine.matches(responese_for_FROM_TO_and_END)) {
				state_machine = State_Machine.TO;
				return true;
			}
		} else if (state_machine == State_Machine.TO) {
			if (receivedMessageLine.matches(responese_for_FROM_TO_and_END)){
				state_machine = State_Machine.DATA;
				return true;
			}
		} else if (state_machine == State_Machine.DATA) {
			if (!has_confirm_DATA && receivedMessageLine.matches(responese_for_DATA)){
				has_confirm_DATA = true;
				return true;
			}else if (has_confirm_DATA){
				return true;
			}
		} else if (state_machine == State_Machine.END) {
			if (receivedMessageLine.matches(responese_for_FROM_TO_and_END)){
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
		String echo = outgoingMessageLine;

		// Replace the head of the message and prepare for the Stander output
		if (state_machine == State_Machine.FROM) {
			echo = outgoingMessageLine.replace("From", "MAIL FROM");
		} else if (state_machine == State_Machine.TO) {
			echo = outgoingMessageLine.replace("To", "RCPT TO");
		} else if (state_machine == State_Machine.DATA) {
			echo = outgoingMessageLine;
			if (outgoingMessageLine.matches(pattern_FROM)){
				state_machine = State_Machine.END;
			}
		}
		outgoingMessageLine = echo;
	}
	
	
	
	
} // end class

class FileProcess {
	
	static String path;
	static boolean appen_to_file = true;
	static FileReader fr;
	static BufferedReader bf;
	static int numberOfLines = 0;

	public FileProcess (String file_path, boolean append_value) throws FileNotFoundException {
		path = file_path;
		appen_to_file = append_value;
		fr = new FileReader(path);
		bf = new BufferedReader(fr);
	}
	
	public String readLine () {
		try{
			return bf.readLine();	
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
