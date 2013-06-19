import java.io.*;
import java.net.*;
public class UDPServer {

	
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendSocket, receiveSocket;
	
	public UDPServer(){
		try {
	      	//Bind a datagram socket to any available port on the local host machine
	 	 			//This socket will be used to // send UDP Datagram packets.
	      sendSocket = new DatagramSocket();

      	// Bind a datagram socket to port 4980 on the local host machine. 
				// This socket will be used to receive UDP Datagram packets.
      	receiveSocket = new DatagramSocket(5000);
   } catch (SocketException se) {
      se.printStackTrace();
      System.exit(1);
   }
		
	}
	
	public void receive(){
		
	// Construct a DatagramPacket for receiving packets up 
    // to 100 bytes long (the length of the byte array).
		String receivedString;

    byte data[] = new byte[100];
    for(;;){
      receivePacket = new DatagramPacket(data, data.length);

      // Block until a datagram packet is received from receiveSocket.
      try {         
         receiveSocket.receive(receivePacket);
      } catch (IOException e) {  e.printStackTrace(); System.exit(1); }

      // Process the received datagram.
      System.out.println("Server: Received from "+  receivePacket.getAddress() + ":" +
      	receivePacket.getPort() + " - " + 
      	new String(receivePacket.getData(),0,receivePacket.getLength()) + 
      	"(" + receivePacket.getLength() + ")" );
      
      System.out.println("Server: receiveSocket info:" + receiveSocket.getPort() + ", Inet:" +receiveSocket.getInetAddress());
     
      receivedString = new String(receivePacket.getData());
      
      receivedString = receivedString.toUpperCase();
      data = receivedString.getBytes();
      
      sendPacket = new DatagramPacket(data, receivePacket.getLength(),
                               receivePacket.getAddress(), receivePacket.getPort());
      
      System.out.println( "Server: Sending to " + sendPacket.getAddress() + ":" +
    		  sendPacket.getPort() + " - " + 
    		  new String(sendPacket.getData(),0,receivePacket.getLength()) + 
    		  "(" +sendPacket.getLength() + ")" );

      try {
         sendSocket.send(sendPacket);
         
      } catch (IOException e) { e.printStackTrace(); System.exit(1);
      }
      System.out.println("Server: sendSocket info:" + sendSocket.getPort() + ", Inet:" +sendSocket.getInetAddress());
      System.out.println("Server: packet sent");

      

    }
		
		
	}
	public static void main(String[] args) {
		UDPServer udp = new UDPServer();
		udp.receive();

	}

}
