package operations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Delete {

	DataInputStream input;
	DataOutputStream output;
	
	public Delete() {}
	
	public Delete(DataInputStream input, DataOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	public void initiateDelete() {
		try {
			Scanner sc = new Scanner(System.in);
			output.writeUTF("delete");
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
					//createFile(); 
				}				 
			}
		}
		catch(Exception e) {
			System.out.println("Error in file deletion: "+e.getLocalizedMessage());
		}
	}
}
