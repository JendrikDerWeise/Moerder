package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import GameObjekts.Player;
import other.Game;


public class MoerderServer {

	public final static int DEFAULT_PORT = 2342;
	protected int port;
	protected ServerSocket serverSocket;
	private HashMap<String, Game> games;
	private HashMap<String, ArrayList<ClientRequestProcessor>> playerClients;
	private HashMap<String, ArrayList<String>> players;
	
	
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
		int playerNumber = 0;
		while(gameName.substring(0, 5) == "search"){//TODO ist substring inclusive oder exclusive?
			spieler.getSearchResult(searchGame(gameName.substring(6, gameName.length()-1)));
			gameName = spieler.setGame();
		}
		if(games.containsKey(gameName)){
			Game game = games.get(gameName);
			boolean add = false;
			if(game.passwordSecured()){ //Schutz durch Passwort pr�fen
				String password = spieler.getPwd();
				if(game.checkPwd(password)){
					add = true;
				}else{
					//TODO Passwort Falsch, probiers nochmal
				}
			}else{
				add = true;
			}
			if(add){
				int number = addPlayer(gameName, name);
				spieler.setNumber(number);
				games.remove(gameName);
				games.put(gameName, game); 
				ArrayList<ClientRequestProcessor> playersCl = playerClients.get(gameName);
				playersCl.add(spieler);
				playerClients.remove(gameName);
				playerClients.put(gameName, playersCl);
				if(playerClients.get(gameName).size() == games.get(gameName).getPlayerAmount()){
					startGame(gameName);
				}
			}
			
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
		
		Thread t = new Thread(playerClients.get(gameName).get(playerClients.get(gameName).size()));
        t.start();  
	}
	
	private int addPlayer(String gameName, String name){
		ArrayList<String> playerNames = players.get(gameName);
		if(playerNames == null || playerNames.size() == 0){
			playerNames = new ArrayList<String>();
			playerNames.add(name);
		}else{
			playerNames.add(name);
			players.remove(gameName);
		}
		players.put(gameName, playerNames);
		return playerNames.size();
	}
	
	
	
	public Set searchGame(String searchString){
		return games.keySet();
	}
	
	public void startGame(String key){
		
		Game game = games.get(key);
		game.startGame(players.get(key));
		players.remove(key);
		games.remove(key);
		games.put(key, game);
		for(int i = 0; i < game.getPlayerAmount(); i++){
			playerClients.get(key).get(i).addPlayers(playerClients.get(key));
			playerClients.get(key).get(i).setGame(game);
		}
	}
	
	
	
	public static void main(String[] args){
		MoerderServer server = new MoerderServer(0);
	}
}