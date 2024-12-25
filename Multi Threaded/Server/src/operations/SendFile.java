package operations;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;

public class SendFile {

	DataInputStream input;
	DataOutputStream output;
	String path;

	public SendFile() {}

	public SendFile(DataInputStream input, DataOutputStream output, String path) {
		this.input = input;
		this.output = output;
		this.path = path;
	}
	
	public void initiateTransfer() {
		try {
			File[] folder = new File(path).listFiles();
			output.writeUTF("ok");
			for(int i=0;i<folder.length;i++) {
				output.writeUTF("> "+(i+1)+". "+folder[i].getName());
			}
			output.writeUTF("> Choose file option number: ");
			output.writeUTF("*EOI*");
			transfer(folder[input.readInt()-1]);
		}
		catch(Exception e) {
			System.out.println("Error in file transfer: "+e.getLocalizedMessage());
		}
	}

	private void transfer(File file) {
		try {
			output.writeUTF("ok");
			output.writeUTF(file.getName());
			System.out.println("Transfering file "+file.getName());
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			String line;  
			while((line=bReader.readLine())!=null) {
				output.writeUTF(line);
			}  
			fReader.close();
			output.writeUTF("*EOF*");
			System.out.println("Transfer complete");
		}
		catch(Exception e) {
			System.out.println("Error in file transfer: "+e.getLocalizedMessage());
		}
	}
}
