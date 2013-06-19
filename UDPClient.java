
import java.net.*;
import java.io.*;

public class UDPClient {

	
	//Hold the port# and IP address of the client
	private int clientPort;
	private int serverPort;
	private InetAddress ip;
	
	//initialize the port# and ip address
	public UDPClient(int clientPort, int serverPort){
		this.clientPort=clientPort;
		this.serverPort=serverPort;
		try{
			ip = InetAddress.getLocalHost();
			//clientSocket = new DatagramSocket();

		}
		catch(UnknownHostException e){System.out.println("Client: can't get local address");System.exit(1);}
		//catch(SocketException e1){System.out.println("Client: can't make receiving socket");System.exit(1);}
	}

	public int getClientPort(){
		return this.clientPort;
	}
	public InetAddress getClientIP(){
		return this.ip;
	}
	public int getServerPort(){
		return this.serverPort;
	}
	
	
	//Create a threaded client so the client may send and receive without any blocking
	//this is done by setting the client to send all messages to the server port
	//and receiving all messages from the server on its own constant port
	public void initiateSendReceiveThreads(){
		//initialize all sending from client to server to head to server port
		ClientSendThread send = new ClientSendThread(this.getClientIP(), this.getClientPort(), this.getServerPort());
		send.start();//start the thread
		//initialize all sending from the server to come to the clients port
	
		ClientReceiveThread receive = new ClientReceiveThread(send.getClientSocket());
		receive.start();//start thread
		
	}
	
	public static void main(String[] args) {
		int cPort;
		int sPort;
		if(args.length!=1){
			cPort = 4980; //default port
			sPort = 5000;
		}
		else{
			cPort = Integer.parseInt(args[0]); //get port from commandline
			sPort = Integer.parseInt(args[1]);
		}
		UDPClient client = new UDPClient(cPort, sPort);
		client.initiateSendReceiveThreads();

	}

}
