import java.io.*;
import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MIMEBuild {

	static FileProcess_MIME outgoingTextFile = null;
	static FileProcess_MIME outgoingImageFile = null;
	static ArrayList<String> email_message = new ArrayList<String>();
	static ArrayList<String> rcpt_list = new ArrayList<String>();
	static String[] rcpt_array = null;
	static String my_email = "yinanf@live.unc.edu";
	static String textContent = "";
	static String textFileName;
	static String imageFileName;
	
	public static void main(String[] args) {
		processOutgoingFile(args);
		System.out.println("...Found file outgoing");

		readRecipient();
		
		buil_MIME();
		
		addBraketToEmailAddress();
	}

	static void addBraketToEmailAddress(){
		try {
			outgoingTextFile = new FileProcess_MIME("outgoing_mime", true);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
		String pattern_CMD = "^((From)|(To)):[\\s]*";
		String pattern_From = "^(From):[\\s]*";
		String pattern_To = "^(To):[\\s]*";
		String pattern_local_part = "[^\\s][A-Za-z0-9._%+-]+";
		String pattern_domain = "[A-Za-z0-9]{2,}([\\.][A-Za-z0-9]{2,})*([\\.][A-Za-z]{2,})?[^\\s][\\s]*";
		String line = null;
		String input = "";
		
		System.out.println("Start rewriting file");
		while ((line = outgoingTextFile.readLine()) != null){
//			System.out.println(line);
			if(line.matches(pattern_From + pattern_local_part + "[\\@]" + pattern_domain)) {
				System.out.println(line);  
				String temp1 = line.replace("From: ", "From: <");
				temp1 += ">";
				System.out.println("temp1: " + temp1); 
				input += temp1 + '\n';
			}else if(line.matches(pattern_To + pattern_local_part + "[\\@]" + pattern_domain)) {
				System.out.println(line);  
				String temp1 = line.replace("To: ", "To: <");
				temp1 += ">";
				System.out.println("temp1: " + temp1); 
				input += temp1 + '\n';
			}else {
				input += line + '\n';
			}
			
	        
		}
		
		FileOutputStream File;
		try {
			File = new FileOutputStream("outgoing_mime");
			File.write(input.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	public static void buil_MIME() {
	    try {
	        Message message = new MimeMessage(Session.getInstance(System.getProperties()));
	        message.setFrom(new InternetAddress(my_email));
	        InternetAddress[] addressTo = new InternetAddress[rcpt_array.length];
            for (int i = 0; i < rcpt_array.length; i++)
            {
                addressTo[i] = new InternetAddress(rcpt_array[i]);
            }
            message.setRecipients(Message.RecipientType.TO, addressTo); 
	        message.setSubject("COMP431 Exrea Credit MIME multipart from Yinan Fang(720346851)");
	        // create the message part 
	        MimeBodyPart content = new MimeBodyPart();
	        // fill message
	        content.setText(textContent);
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(content);
	        // add attachments
	        File file = new File(imageFileName);
	        MimeBodyPart attachment = new MimeBodyPart();
	        DataSource source = new FileDataSource(file);
	        attachment.setDataHandler(new DataHandler(source));
	        attachment.setFileName(file.getName());
	        multipart.addBodyPart(attachment);
	        // integration
	        message.setContent(multipart);
	        // store file
	        message.writeTo(new FileOutputStream(new File("outgoing_mime")));
	        System.out.println("File written");
	    } catch (Exception ex) {
	        System.out.println("Errrr!!");
	    }
	}
	
	static void recordRCPT (String rcpt) {
		rcpt = rcpt + "@cs.unc.edu";
		System.out.println(rcpt);
		if (!rcpt_list.contains(rcpt)){
			rcpt_list.add(rcpt);
		}
	}
	
	static void readRecipient(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line=null;
        try {
			while( ((line=br.readLine()) != null) && (!line.equals(""))) {
			       System.out.println(line);  
			       recordRCPT(line);
			}
			rcpt_array = (String[]) rcpt_list.toArray(new String[rcpt_list.size()]);
			System.out.println(rcpt_array);  
			for (String item : rcpt_array){
				System.out.println(item);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void processOutgoingFile(String[] args){
		textFileName = args[0];
		imageFileName = args[1];

		System.out.println(textFileName);
		
		try {
			outgoingTextFile = new FileProcess_MIME(textFileName, false);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
		
		String line = null;
		while ( (line=outgoingTextFile.readLine()) != null) {
//			System.out.println(line);
			textContent = textContent + line + "\n";
		}	
		System.out.println(textContent);
		
		try {
			outgoingImageFile = new FileProcess_MIME(imageFileName, false);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
	}
	
	
}

class FileProcess_MIME {
	
	static String path;
	static boolean appen_to_file = true;
	static FileReader fr;
	static BufferedReader bf;
	static int numberOfLines = 0;

	public FileProcess_MIME (String file_path, boolean append_value) throws FileNotFoundException {
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