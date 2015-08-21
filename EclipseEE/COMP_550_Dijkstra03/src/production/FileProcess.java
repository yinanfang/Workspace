package production;

import java.io.*;

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
