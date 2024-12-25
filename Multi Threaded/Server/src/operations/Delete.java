package operations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

public class Delete {
	
	DataInputStream input;
	DataOutputStream output;
	String path;

	public Delete() {}

	public Delete(DataInputStream input, DataOutputStream output, String path) {
		this.input = input;
		this.output = output;
		this.path = path;
	}
	
	public void initiateDelete() {
		try {
			File[] folder = new File(path).listFiles();
			output.writeUTF("ok");
			for(int i=0;i<folder.length;i++) {
				output.writeUTF("> "+(i+1)+". "+folder[i].getName());
			}
			output.writeUTF("> Choose file option number: ");
			output.writeUTF("*EOI*");
			delete(folder[input.readInt()-1]);
		}
		catch(Exception e) {
			System.out.println("Error in file deletion: "+e.getLocalizedMessage());
		}
	}

	private void delete(File file) {
		try {
			System.out.println("deleting file "+file.getName());
			if(file.delete() && !file.exists()) {
				output.writeUTF("ok");
			}
		}
		catch(Exception e) {
			System.out.println("Error in file deletion: "+e.getLocalizedMessage());
		}
	}
}
