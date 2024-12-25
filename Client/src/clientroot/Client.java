package clientroot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import operations.Delete;
import operations.Download;
import operations.Rename;
import operations.Upload;

public class Client
{
	public static void operate(int option, DataInputStream input, DataOutputStream output, String path) throws IOException {
		switch(option) {
		case 1:
			Upload up = new Upload(input, output, path);
			up.initiateUpload();
			break;
		case 2:
			Download download = new Download(input, output, path);
			download.requestFile();
			break;
		case 3:
			Delete delete = new Delete(input, output);
			delete.initiateDelete();
			break;
		case 4:
			Rename rename = new Rename(input, output, path);
			rename.initiateRename();
			break;
		case 5:
			output.writeUTF("exit");
			break;
		}
	}

	public static void main(String args[])
	{
		try { 
			Socket socket = new Socket("localhost", 8090);
			System.out.println("Connected with client on port 8090");
			Scanner sc = new Scanner(System.in);
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			String option = "";
			while(!option.equals("5")) {
				System.out.println("Select any operation (input the option number):");
				System.out.println("1. UPLOAD\n2. DOWNLOAD\n3. DELETE\n4. RENAME\n5. EXIT");
				option = sc.next();
				System.out.println("option "+option);
				if(option.matches("[0-9]+")) {
					int optionNum = Integer.parseInt(option);
					if(optionNum<=5 && optionNum>=1) {
						String path = System.getProperty("user.home")+"/Desktop/files/client";
						operate(optionNum, input, output, path);
					}
					else {
						System.out.println("Incorrect Entry");
					}
				}
				else {
					System.out.println("Incorrect Entry");
				}
			}
			socket.close();
			output.close();
			System.out.println("Connection Terminated!!!");
		}
		catch(Exception e) {
			System.out.println("Error occurred: "+e.getLocalizedMessage());
		}
	}
} 
