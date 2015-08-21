import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

public class FileProcess {
	
	private String path;
	private boolean appen_to_file = true;
	FileReader fr;
	BufferedReader bf;
	int numberOfLines = 0;

	public FileProcess (String file_path, boolean append_value) {
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
			System.out.println(numberOfLines);
			
			//bf.close();
			

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
			System.out.println("creating directory: " + "forward");
		    boolean result = theDir.mkdir();  
		    if(result) {    
		    	System.out.println("DIR created");  
		    }
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
