import java.io.*;
import java.net.*;

class Server_test {
	static String clientSentence;
	static String capitalizedSentence;
	static ServerSocket welcomeSocket;
	static Socket connectionSocket;
	static String receivedMessageLine;

	public static void main(String argv[]) throws Exception {

		establishServer();
		System.out.println("Server Ready for Connection");

		connectionSocket = welcomeSocket.accept();
		System.out.println("Client Made Connection");

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

	static void runServer() {
		while (true) {

			// While loop to handle arbitrary sequence of clients making
			// requests
			// Waits for some client to connect and creates new socket for
			// connection

			String sentence = receiveClientData();
			capitalizedSentence = sentence.toUpperCase() + '\n';
			sendClientData(capitalizedSentence);
			
//			
//			BufferedReader inFromClient;
//			try {
//				inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//
//				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//
//				clientSentence = inFromClient.readLine();
//				System.out.println("Client sent: " + clientSentence);
//				capitalizedSentence = clientSentence.toUpperCase() + '\n';
//
//				outToClient.writeBytes(capitalizedSentence);
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				System.out.println("Connection broken");
//				e.printStackTrace();
//			}

			// connectionSocket.close();

		}
	}
	
	static String receiveClientData(){
		BufferedReader inFromClient;
		try {
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			receivedMessageLine = inFromClient.readLine();
			System.out.println("...receivedMessageLine: " + receivedMessageLine);
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
			System.out.println("...ready to send: " + data);
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeBytes(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

} // end class