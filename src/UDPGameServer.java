
// File:UDPGameServer.java
// UDP Server Java Example

import java.io.*;
import java.net.*;
import java.util.*;

class UDPGameServer {
	static final HashMap<String, Player> playerMap = new HashMap<String, Player>();
	static final int bufSize = 50;
	static String lettersRemaining = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String args[]) {
		int port = 8007;
		// Ready; let it begin being a server
		
		try {
			
			// UDP Socket declaration
			DatagramSocket s = new DatagramSocket(port); // java.net.*
			System.out.print("UDPListener Server listening on port:");
			System.out.println(s.getLocalPort());
			byte[] buf = new byte[50];
			DatagramPacket incoming = new DatagramPacket(buf, bufSize);
			
			while (true) {
				// Get packet from a client
				
				System.out.println("Waiting for incoming packet");
				s.receive(incoming);
				System.out.print("Received from inet address: " + incoming.getAddress().toString());
				System.out.print(":");
				System.out.print(incoming.getPort());
				// Obtain a byte input stream to really read the UDP packet
				ByteArrayInputStream bin = new ByteArrayInputStream(incoming.getData(), 0, incoming.getLength());
				// Connect a reader for easier access
				BufferedReader reader = new BufferedReader(new InputStreamReader(bin));
				Scanner received = new Scanner(reader.readLine()); // we can read a string, since
				
				
				String response = "";
				while (received.hasNext()) // true was received.hasNext()
				{
					String word = "BANANA";
					String command = received.next();
					
					switch(command.toLowerCase()) {
						// has 1 piece of data
						case "addplayer":
						{
							String playerName = received.next().trim();
							Player player = new Player(playerName, word, starterWord(word));
							playerMap.put(playerName, player);
							
							response = "Welcome, " + playerName + ".\nThe current board is: " + player.getCurrentWord() + "\nYour current score is: " + player.getScore() + " guesses. \nYour remaining letters are: " + lettersRemaining +"\nEnter a capitalized letter to guess. (Enter quit to stop)";
							break;
						}
						
						// has 2 pieces of data
						case "guess":
						{
							String playerName = received.next().trim();
							Player player = playerMap.get(playerName);

							String guess = received.next();
							
							for (String player1 : playerMap.keySet())
								System.out.println(player1);
							
							processGuess(player, guess);
							
							if (player.isWinner())
								response = "Congrats" + playerName + "! You won with a score of " + player.getScore() + "! The correct word is: " + player.getWord();
							else
								response = "\n" + playerName + ", the current board is: " + player.getCurrentWord() + "\nYour current score is: " + player.getScore() + " guesses \nYour remaining letters are: " + lettersRemaining +"\nEnter a capitalized letter to guess. Enter quit to stop.";
							break;
						}
					}
					// Return data to the client
					DatagramPacket outgoing = new DatagramPacket(response.getBytes(), response.getBytes().length, incoming.getAddress(), incoming.getPort());
					s.send(outgoing);
				}
				
				// Return data to the client
				//DatagramPacket outgoing = new DatagramPacket(response.getBytes(), response.getBytes().length, incoming.getAddress(), incoming.getPort());
				//s.send(outgoing);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Assesses client submitted letter against the current undiscovered word
	private static void processGuess(Player player, String guess)
	{
		//Create a string builder to build the new word "result" composed of correctly guess letters and "-'s
		lettersRemaining = lettersRemaining.replaceAll(guess.trim(),"");
		StringBuilder result = new StringBuilder();
		//Go through every letter in WORD
		for (int count = 0; count < player.getWord().length(); count++)
		{
			//If the letter matches the current guess, reveal it
			if (player.getWord().charAt(count) == guess.charAt(0))
			{
				result.append(guess.charAt(0));
				
				
			}
			else //else leave what is already there, whether it be correctly guessed letters or -'s
				result.append(player.getCurrentWord().charAt(count));
		}
		
		
		//Recreate the letters remaining w/o the current guess
		/*StringBuilder newLettersRemaining = new StringBuilder();
		for (int count = 0; count < lettersRemaining.length(); count++)
		{
			if (guess.charAt(0) != lettersRemaining.charAt(count))
				newLettersRemaining.append(lettersRemaining.charAt(count));
		}
		lettersRemaining = "" + newLettersRemaining;
		*/	
		player.addToScore();
		player.setCurrentWord(result.toString());
	}
	
	/*public static String processName() 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(bin));
		String line = reader.readLine();
		return line;
	*/	
	
	
	
	private static String starterWord(String wordToGuess)
	{
		StringBuilder builder = new StringBuilder();
		for (int count = 0; count < wordToGuess.length(); count++)
			builder.append("-");
		return builder.toString();
	}
}
