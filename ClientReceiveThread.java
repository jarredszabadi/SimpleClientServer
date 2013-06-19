
import java.net.*;
import java.io.*;
//this thread waits to receive all packet incoming to the client port
public class ClientReceiveThread extends Thread{
	private DatagramSocket clientSocket;

	
	//set up client socket to bound to client port #
	
	public ClientReceiveThread(DatagramSocket clientSocket){
		this.clientSocket = clientSocket;
	}

	public DatagramSocket getClientSocket(){
		return clientSocket;
	}
	//continuously waits for an incoming packet
	public void run(){
		byte data[] = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(data, data.length);
    
		while(true){
			try{
				clientSocket.receive(receivePacket);
				System.out.println("Received from Server on port #: "+receivePacket.getPort());

        // Extract the reply from the DatagramPacket      
        String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());

        // print to the screen
        System.out.println("" + serverReply + "\n");
        data = new byte[1024];
        Thread.yield();
				
			}catch(IOException e){System.out.println("RecThread: can't receive packet"); e.printStackTrace(); System.exit(1);}
			
			
			
		}
	}

}
