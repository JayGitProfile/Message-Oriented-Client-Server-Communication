package serverside;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import operations.Create;
import operations.Delete;
import operations.Rename;
import operations.SendFile;

public class MultiThreadHandler implements Runnable{

	public Socket socket;
	
	public MultiThreadHandler() {};
	
	public MultiThreadHandler(Socket socket) {
		this.socket = socket;
		
	}
	
	public static void operate(String command, DataInputStream input, DataOutputStream output, String path) {
		switch(command) {
		case "create":
			Create create = new Create(input, output, path);
			create.CreateFile();
			break;
		case "request":
			SendFile send = new SendFile(input, output, path);
			send.initiateTransfer();
			break;
		case "delete":
			Delete delete = new Delete(input, output, path);
			delete.initiateDelete();
			break;
		case "rename":
			Rename rename = new Rename(input, output, path);
			rename.initiateRename();
			break;
		}
	}
	
	public void run() {
		try {
			System.out.println("Client connected "+ ++Server.client);
			System.out.println("-----------------------------------------------");
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			String inputString = ""; 
			while(true) { 
				inputString = input.readUTF();
				System.out.println("Incoming message: "+inputString);
				if(inputString.equals("exit")) 
					break; 
				else {
					String path = System.getProperty("user.home")+"/Desktop/files/server";
					operate(inputString, input, output, path); 
				}
			} 
			input.close(); 
			socket.close();
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getLocalizedMessage());
		}
	}
}
