package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import other.Game;


public class MoerderServer {

	public final static int DEFAULT_PORT = 2342;
	protected int port;
	protected ServerSocket serverSocket;
	private LinkedList<ClientRequestProcessor> alleSpieler = new LinkedList<ClientRequestProcessor>();
	private LinkedList<ClientRequestProcessor> alleObjectSpieler = new LinkedList<ClientRequestProcessor>();
	private int spielerAnzahl = 0;
	private HashMap<String, Game> games;
	private HashMap<String, ArrayList<ClientRequestProcessor>> playerClients;
	
	
	public MoerderServer(int port){
		if (port == 0)
			port = DEFAULT_PORT;
		this.port = port;
		
		try {
			serverSocket = new ServerSocket(port);
			
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println("Host: " + ia.getHostName());
			System.out.println("Server-Adresse " + ia.getHostAddress()	+ " an Port " + port);
		} catch (IOException e) {
			System.err.println("Das hat nicht geklappt: " + e);
			System.exit(1);
		}
	}
	
	
	/**
	 * Dauerschleife zum Herstellen der Verbindungen mit den Clients. Lauscht auf Clientanfragen.
	 */
	public void waitForClient(){
		
		try {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				if(clientSocket != null){
					addClient(clientSocket);
					
				}
			}
		} catch (IOException e) {
			System.err.println("Verbindungsprobleme: " + e);
			System.exit(1);
		}
	}
	
	
	/**
	 * 
	 * @param clientSocket
	 */
	public void addClient(Socket clientSocket){
		ClientRequestProcessor spieler = new ClientRequestProcessor(clientSocket);
		String name = spieler.getName();
		String gameName = spieler.setGame();
		if(games.containsKey(gameName)){
			Game game = games.get(gameName);
			game.addPlayer(name); 
			games.remove(gameName);
			games.put(gameName, game); //TODO ist das ueberfluessig?
			ArrayList<ClientRequestProcessor> players = playerClients.get(gameName);
			players.add(spieler);
			playerClients.remove(gameName);
			playerClients.put(gameName, players);
		}else{
			if(gameName.substring(0, 5) == "search"){ //TODO ist substring inclusive oder exclusive?
				spieler.getSearchResult(searchGame(gameName.substring(6, gameName.length()-1)));
			}else{
				Game game = spieler.getGame();
				if(game != null){
					//TODO ich gehe davon aus, dass dann der Spieler schon hinzugefuegt ist
					games.put(gameName, game);
					ArrayList<ClientRequestProcessor> players = new ArrayList<ClientRequestProcessor>();
					players.add(spieler);
					playerClients.put(gameName, players);
				}else{
					//TODO error
				}
			}
			
		}
		
			
		//BIS HIER
		Thread t = new Thread(alleSpieler.get(spielerAnzahl));
        t.start();  
       
        spielerAnzahl++; 
        for(ClientRequestProcessor s : alleSpieler){
        	s.setSpielerAnzahl(spielerAnzahl);
        	s.setSpielerListe(alleSpieler);
       }
	}
	
	public Set searchGame(String searchString){
		return games.keySet();
	}
	
	
	public static void main(String[] args){
		MoerderServer server = new MoerderServer(0);
	}
}
