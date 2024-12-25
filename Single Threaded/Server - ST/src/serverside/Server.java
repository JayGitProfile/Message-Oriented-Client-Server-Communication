package serverside;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import operations.Create;
import operations.Delete;
import operations.Rename;
import operations.SendFile;

public class Server {

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
	
	public static void main(String[] args) {
		boolean cleanExit = false;
		while(!cleanExit) {
			try {
				cleanExit = true;
				ServerSocket serverSckt = new ServerSocket(8090);
				System.out.println("-----------------------------------------------");
				System.out.println("Server started on port 8090");
				System.out.println("Listening for client connection...");  
				Socket socket = serverSckt.accept();
				System.out.println("Client connected");
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
				serverSckt.close();
				input.close();
				socket.close();
			}
			catch(Exception e) {
				System.out.println("Error occurred: "+e.getLocalizedMessage());
				cleanExit = false;
			}
			finally {
				if(!cleanExit) {
					System.out.println("Connection terminated due to above error."
							+"\nRestarting server...");
				}
				else {
					System.out.println("Connection closed with client");
				}
			}
		}
	}
}