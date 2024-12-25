package operations;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;

public class Create {

	DataInputStream input;
	DataOutputStream output;
	String path;

	public Create() {}

	public Create(DataInputStream input, DataOutputStream output, String path) {
		this.input = input;
		this.output = output;
		this.path = path;
	}
	
	public void CreateFile() {
		try {
			output.writeUTF("ok");
			String fileName = input.readUTF();
			String inputString;		
			System.out.println(fileName+" ***************");
			File file = new File(path,fileName);
			if(!file.exists() && file.createNewFile()) {
				FileWriter fWriter = new FileWriter(file);
				BufferedWriter bWriter = new BufferedWriter(fWriter);
				while(true) {
					inputString = input.readUTF();
					if(inputString.equals("*EOF*"))
						break;
					else {
						bWriter.write(inputString);
						bWriter.newLine();
					}
				}
				bWriter.close();
				fWriter.close();
			}
			output.flush();
			System.out.println("FILE created");
		} 
		catch(Exception e) {
			System.out.println("Error in file creation: "+e.getLocalizedMessage());
		}		
	}
}
