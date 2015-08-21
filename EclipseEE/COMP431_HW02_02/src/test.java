import java.util.ArrayList;


public class test {

	public static ArrayList<String> email_message = new ArrayList<String>();
	public static ArrayList<String> rcpt_list = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		email_message.add("111111");
		email_message.add("xcxvbxcghgfh");
		rcpt_list.add("jeffay@cs.unc.edu");
		rcpt_list.add("jefasdfay@cs.unc.edu");

		writeToFile();
		
	}
	
	public static void writeToFile () {
		for (int i = 0; i < rcpt_list.size(); i++ ) {
			String rcpt = rcpt_list.get(i);
			String path = "forward/" + rcpt;
			FileProcess file = new FileProcess(path, true);
			file.writeFile(email_message);
		}
	}

}
