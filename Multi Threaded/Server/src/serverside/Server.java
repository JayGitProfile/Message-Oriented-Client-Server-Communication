package serverside;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static int client = 0;
	public static void main(String[] args) {
		boolean cleanExit = false;
		while(true) {
			try {
				cleanExit = true;
				ServerSocket serverSckt = new ServerSocket(8090);
				System.out.println("-----------------------------------------------");
				System.out.println("Server started on port 8090");
				System.out.println("Listening for client connection...");  
				Socket socket = serverSckt.accept();
				MultiThreadHandler clientSock  = new MultiThreadHandler(socket);
				new Thread(clientSock).start();
				serverSckt.close();
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