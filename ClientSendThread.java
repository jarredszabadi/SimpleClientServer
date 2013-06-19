
import java.net.*;
import java.io.*;

public class ClientSendThread extends Thread{
	//save the server's ip address
	private InetAddress serverIP;
	//save the client's port #
	private int clientPort;
	//save the server's port
	private int serverPort;
	//save the datagramsocket for all sending
  private DatagramSocket clientSocket;
  
  
  //set up the serverSocket to bind to the server's port # and ip
  public ClientSendThread(InetAddress ip, int clientPort, int serverPort){
  	this.serverIP = ip;
  	this.clientPort = clientPort;
  	this.serverPort=serverPort;
		
  	try{
  		this.clientSocket = new DatagramSocket(this.clientPort, serverIP);
  		

  	}catch(SocketException se){se.printStackTrace();System.out.println("SendThread: failed to bind socket"); System.exit(1);}
  	
  }
	
  public DatagramSocket getClientSocket(){
  	return clientSocket;
  	
  }
  //the run method for this thread continuously waits for a terminal window input from the client
  public void run(){
  	//send blank message to initiate the server/client connection
  	//saves the clients port # in the server client list
  	byte[] data = new byte[1024];
  	data = "".getBytes(); //send empty string
  	DatagramPacket blankPacket = new DatagramPacket(data,data.length , serverIP, serverPort);
  	try{
  		clientSocket.send(blankPacket);
  	}catch(IOException e){System.out.println("SendThread: failed to send blank packet"); System.exit(1);}
  	
  	
  	//set up the terminal window input stream
  	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  	String message = null;
  	while(true){
  		try{
  			message = in.readLine();
  		}catch(IOException e){System.out.println("SendThread: Can't read input"); System.exit(1);}
  		data = message.getBytes();
  		DatagramPacket sendPacket = new DatagramPacket(data, data.length, serverIP, 5000);
  		System.out.println("I just sent: "+message); 
      try{
      	clientSocket.send(sendPacket);
      }catch(IOException e){System.out.println("SendThread: failed to send action"); System.exit(1);}
      data = new byte[1024];
       Thread.yield(); //pause thread until a client inpputs something on the terminal window
  		
  	}
  }
  

}
