package main;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Parse {

	public static void main(String[] args) {
		BufferedReader buffer;
		String command = null;
		try{
			while(true){
				buffer = new BufferedReader(new InputStreamReader(System.in));
				command = buffer.readLine();
				
				// If ctrl+D is Press the content "in" will be null and will not be printed	
				if (command == null){
					break;
				}
				System.out.println(command);	
				if(!command.matches("^MAIL FROM:[\\s]*.+")){
					// Check if it's in a pattern of "MAIL FROM: <at least one character>"
					System.out.println("ERROR -- mail-from-cmd");
				}else if (!command.matches("^MAIL FROM:[\\s]*[\\<][^\\s].+[^\\s][\\>][\\s]*")){
					// Check if it's in a pattern of "MAIL FROM: <start and end with non space char>"
					System.out.println("ERROR -- path");
				}else if (!command.matches("^.+[\\<][^\\s@]+[\\@][^\\s@]+[\\>][\\s]*")){
					// Since "MAIL FROM: " is checked already. It will be ignore from now on
					// Check if it's in a pattern of "<.+> <any char but space on both sides of @>"
					System.out.println("ERROR -- mailbox");
				}else if (!command.matches("^.+[\\@][A-Za-z0-9]{2,}([\\.][A-Za-z0-9]{2,})*([\\.][A-Za-z]{2,})?[\\>][\\s]*")){
					// Check domain, the part between "@" and ">"
					// Check if it's in a pattern of "<.+> <element[.element]*>"
					System.out.println("ERROR -- domain");
				}else if (!command.matches("^[^\\<]+[\\<][A-Za-z0-9]+[\\@][^\\@]+")) {
					// Check if local-part contains A-Za-z0-9
					System.out.println("ERROR -- local-part");
				}else{
					System.out.println("Sender ok");
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
	}
}
