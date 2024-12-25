package operations;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Download {

	DataInputStream input;
	DataOutputStream output;
	String path;
	
	public Download() {}
	
	public Download(DataInputStream input, DataOutputStream output, String path) {
		this.input = input;
		this.output = output;
		this.path = path;
	}
	
	public void requestFile() {
		try {
			output.writeUTF("request");
			Scanner sc = new Scanner(System.in);
			String inputString = "";
			System.out.println("Incoming message from server:");
			if(input.readUTF().equals("ok")) {
				while(true) {
					inputString = input.readUTF();
					if(inputString.equals("*EOI*"))
						break;
					else {
						System.out.println(inputString);
					}
				}
				int option = sc.nextInt();
				output.writeInt(option);
				if(input.readUTF().equals("ok")) {
					createFile();
				}
			}
		} 
		catch(Exception e) {
			System.out.println("Error in file download: "+e.getLocalizedMessage());
		}
	}

	private void createFile() {
		try {
			String inputString;	
			File file = new File(path,input.readUTF());
			if(!file.exists() && file.createNewFile()) {
				FileWriter fWriter = new FileWriter(file);
				BufferedWriter bWriter = new BufferedWriter(fWriter);
				while(true) {
					inputString = input.readUTF();
					if(inputString.equals("*EOF*"))
						break;
					else {
						System.out.println(inputString);
						bWriter.write(inputString);
						bWriter.newLine();
					}
				}
				bWriter.close();
				fWriter.close();
			}		
		}
		catch(Exception e) {
			System.out.println("Error in file download: "+e.getLocalizedMessage());
		}
	}
	
	
}
