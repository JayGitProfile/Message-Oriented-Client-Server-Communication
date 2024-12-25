package operations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

public class Rename {
	
	DataInputStream input;
	DataOutputStream output;
	String path;

	public Rename() {}

	public Rename(DataInputStream input, DataOutputStream output, String path) {
		this.input = input;
		this.output = output;
		this.path = path;
	}
	
	public void initiateRename() {
		try {
			File[] folder = new File(path).listFiles();
			output.writeUTF("ok");
			System.out.println("Available files: ");
			for(int i=0;i<folder.length;i++) {
				System.out.println((i+1)+". "+folder[i].getName());
				output.writeUTF("> "+(i+1)+". "+folder[i].getName());
			}
			output.writeUTF("> Choose file option number: ");
			output.writeUTF("*EOI*");
			rename(folder[input.readInt()-1]);
		}
		catch(Exception e) {
			System.out.println("Error in file rename: "+e.getLocalizedMessage());
		}
	}

	private void rename(File file) {
		try {
			System.out.println("renaming file "+file.getName());
			output.writeUTF("ok");
			String fileName=input.readUTF();
			boolean renameResult = file.renameTo(new File(path+"/"+fileName));
			if(renameResult) {
				output.writeUTF("done");
			}
		}
		catch(Exception e) {
			System.out.println("Error in file deletion: "+e.getLocalizedMessage());
		}
	}
}
