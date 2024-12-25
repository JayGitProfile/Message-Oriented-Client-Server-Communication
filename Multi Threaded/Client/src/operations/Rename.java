package operations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Rename {

	DataInputStream input;
	DataOutputStream output;
	String path;

	public Rename() {}

	public Rename(DataInputStream input, DataOutputStream output, String path) {
		this.input = input;
		this.output = output;
		this.path=path;
	}

	public void initiateRename() {
		try {
			output.writeUTF("rename");
			Scanner sc = new Scanner(System.in);
			String inputString = "";
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
					System.out.println("Enter new File name with extension(.txt): ");
					String fileName = sc.next();
					output.writeUTF(fileName);
				}
				if(input.readUTF().equals("done")) {
					System.out.println("Rename Done Successfully");
				}
			}
		}
		catch(Exception e) {
			System.out.println("Error in file renaming: "+e.getLocalizedMessage());
		}
	}
}
