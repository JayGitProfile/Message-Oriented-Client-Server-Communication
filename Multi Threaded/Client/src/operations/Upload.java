package operations;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Upload {

	DataInputStream input;
	DataOutputStream output;
	String path;

	public Upload() {}

	public Upload(DataInputStream input, DataOutputStream output, String path) {
		this.input = input;
		this.output = output;
		this.path = path;
	}
	
	public void initiateUpload() {
		try {
			Scanner sc = new Scanner(System.in);
			output.writeUTF("create");
			if(input.readUTF().equals("ok")) {
				File[] folder = new File(path).listFiles();
				System.out.println("Available files: ");
				for(int i=0;i<folder.length;i++) {
					System.out.println((i+1)+". "+folder[i].getName());
				}
				System.out.println("Input option number to choose file:");
				String option = sc.next();
				if(option.matches("[0-9]+")) {
					int optionNum = Integer.parseInt(option);
					if(optionNum>=1 && optionNum<=folder.length)
						uploadFile(folder[optionNum-1]);
				}
			}
		}
		catch(Exception e) {
			System.out.println("Error in uploading file: "+e.getLocalizedMessage());
		}
	}

	public void uploadFile(File file) {
		try {
			output.writeUTF(file.getName());
			System.out.println("Uploading file "+file.getName());
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			String line;  
			while((line=bReader.readLine())!=null) {
				output.writeUTF(line);
			}  
			fReader.close();
			output.writeUTF("*EOF*");
		}
		catch(Exception e) {
			System.out.println("Error in uploading file: "+e.getLocalizedMessage());
		}
	}

}
