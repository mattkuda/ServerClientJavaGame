// File: UDPTalk
// UDP Java Client Example

import java.net.*;
import java.util.*;
import java.io.*;

public class UDPGameClient {
  static String hostname;
  static InetAddress inetaddr;
  static DatagramSocket socket;
  static int port;
  static final int bufSize = 5000;  //this has to be big enough to hold the input
  static String name = "";

  // Set up the socket
  public static void comm() {
    try {
      hostname = "127.0.0.1";
      port = 8007;
      inetaddr = InetAddress.getByName(hostname);
      socket = new DatagramSocket();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void action() {
    String text = "";
    BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
    
    try
    {
    	System.out.println("Please enter your name: ");
		name = cin.readLine();
    } 
    catch (Exception e) 
    {
      System.exit(1);
    }
    
    text = "addplayer " + name;
    
    while (text.length() > 0) 
    { //loop until quit is entered
			
      // Send data over socket
      byte[] data = new byte[bufSize]; //was 50
      
      for (int idx = 0; idx < text.length(); idx++)
        data[idx] = (byte)text.charAt(idx);
      
      DatagramPacket outgoing =
          new DatagramPacket(data, bufSize, inetaddr, 8007);
      
      // Send text to server
      try {
        socket.send(outgoing);
      } catch (Exception e) {
        System.out.println("Client: Send Error");
      }
      
      // Receive text from server
      try {
        DatagramPacket incoming = new DatagramPacket(data, bufSize);
        socket.receive(incoming);
        String line = new String(data);
				System.out.println("From Server: " + line);
      } catch (IOException e) {
        System.out.println("********** Read failed *********");
        System.exit(1);
      }
      try {
        System.out.print("Enter data to send. Enter quit to stop >");
        text = "guess " + name + " " + cin.readLine();
      } catch (Exception e) {
        System.exit(0);
      }
    }
    
    socket.close();
  }

  public static void main(String[] args) {
    comm();
    action();
  }

} // class
