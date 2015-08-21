import java.io.*;
import java.net.*;

class Client_test {
	static String sentence;
	static String modifiedSentence;
	static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	static Socket clientSocket;
	static FileProcess outgoing = null;
	public enum State_Machine {
		FROM, TO, DATA, END
	};
	static State_Machine state_machine = State_Machine.FROM;
	static boolean has_confirm_DATA = false;
	static boolean need_to_parse_FROM = true;
	static String input = null;
	static String receivedMessageLine = null;
	
	public static void main(String args[]) throws Exception {

		openOutgoingFile();
		System.out.println("Found file outgoing");
		
		establishConnection(args);
		System.out.println("established connection with server");
		
		System.out.println("Start running the Clinet");
		processOutgoingMessage();
		
		System.out.println("...Start connection test");
		test();
		

	} // end main
	
	static void test(){
		String testStr = sendAndReceiveData("test");
		System.out.println(testStr);
		
	}
	
	static String sendAndReceiveData(String data){
		try {
			
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());

			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			System.out.println("the sentence is:" + data);
			outToServer.writeBytes(data + '\n');

			receivedMessageLine = inFromServer.readLine();

			System.out.println("FROM SERVER: " + receivedMessageLine);
			

				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//clientSocket.close();
		}
		return receivedMessageLine;
	}
	
	
	static void openOutgoingFile(){
		try {
			outgoing = new FileProcess("outgoing", true);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
	}
	
	static void establishConnection(String[] args){
		try {
			String hostname = args[0];
			int portnumber = Integer.parseInt(args[1]);
			clientSocket = new Socket(hostname, portnumber);
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
	
	
	
	static void processOutgoingMessage() {
		try {
			while ((sentence = outgoing.readLine()) != null) {
				// Create (buffered) input stream using standard input
				// While loop to read and handle multiple input lines
				// Create client socket with connection to server at port 6789

				DataOutputStream outToServer = new DataOutputStream(
						clientSocket.getOutputStream());

				BufferedReader inFromServer = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));

				System.out.println("the sentence is:" + sentence);
				outToServer.writeBytes(sentence + '\n');

				modifiedSentence = inFromServer.readLine();

				System.out.println("FROM SERVER: " + modifiedSentence);
				//clientSocket.close();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("User input error");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
} // end class
// Create output stream attached to socket
// Create (buffered) input stream attached to socket
// Write line to server
// Read line from server
// end while, loop to accept more lines from user


class FileProcess02 {
	
	static String path;
	static boolean appen_to_file = true;
	static FileReader fr;
	static BufferedReader bf;
	static int numberOfLines = 0;

	public FileProcess02 (String file_path, boolean append_value) throws FileNotFoundException {
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
