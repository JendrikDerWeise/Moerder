package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import GameObjekts.Player;
import other.Game;


public class MoerderServer {

	public final static int DEFAULT_PORT = 2342;
	protected int port;
	protected ServerSocket serverSocket;
	private HashMap<String, Game> games;
	private HashMap<String,  HashMap<Integer, Player>> updatePlayers;
	public static final String API_KEY = "AIzaSyC6CeB3mHAPZM15ka0P24Q5P4VFfyxwFeo";
	private HttpURLConnection conn;
	private Game game;
	private JSONObject jResponse;
	
	public MoerderServer(){
		try {
			URL url = new URL("https://android.googleapis.com/gcm/send");
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestProperty("Authorization", "key=" + API_KEY);
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
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
		ClientRequestProcessor spieler = new ClientRequestProcessor(clientSocket, this);
		String name = spieler.getName();
		String gameName = spieler.getGameName();
		while(gameName.substring(0, 5) == "search"){
			spieler.getSearchResult(searchGame(gameName.substring(5, gameName.length())));
			gameName = spieler.getGameName();
		}
		if(gameName.substring(0,3) == "new"){
			if(games.containsKey(gameName.substring(3,gameName.length()))){
				spieler.sendBoolean(false); 
			}else{
				Game game = spieler.getGame();
				if(game != null){
					//TODO ich gehe davon aus, dass dann der Spieler schon hinzugefuegt ist
					games.put(gameName, game);
					ArrayList<ClientRequestProcessor> players = new ArrayList<ClientRequestProcessor>();
					players.add(spieler);
					playerClients.put(gameName, players);
					spieler.sendBoolean(false);
				}else{
					//TODO unerreichbarer error :) 
				}
			}
			
		}else if(gameName.substring(0,4) == "join"){
			if(games.containsKey(gameName)){
				Game game = games.get(gameName);
				boolean add = false;
				if(game.passwordSecured()){ //Schutz durch Passwort prüfen
					String password = spieler.getPwd();
					if(game.checkPwd(password)){
						add = true;
						spieler.sendBoolean(true);
					}else{
						spieler.sendBoolean(false);//TODO Passwort Falsch, probiers nochmal
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
				spieler.accept(add);
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
	
	public void deleteGame(String gameName){
		if(games.containsKey(gameName)){
			games.remove(gameName);
			playerClients.remove(gameName);
		}
	}
	
	public Set<String> searchGame(String searchString){
		Set<String> setty = games.keySet();
        for(String s : setty){
            if(!s.contains(searchString)){
                setty.remove(s);
            }
        }
		return setty;
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
	
	public void sendData(JSONObject jData, String game){
		// Prepare JSON containing the GCM message content. What to send and where to send.
        try{
		JSONObject jFcmData = new JSONObject();
        
        jFcmData.put("to", game);
        jFcmData.put("data", jData);

        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(jFcmData.toString().getBytes());

        }catch(IOException e){
        	e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject receiveData(){
		try{
			InputStream inputStream = conn.getInputStream();
			String resp = IOUtils.toString(inputStream);
			//TODO how to convert String back to JSONObject
		}catch(IOException e){
			e.printStackTrace();
		}
		return new JSONObject();
	}
	
	public static void main(String[] args){
		MoerderServer server = new MoerderServer();
		server.run();
	}


	private void run() {
		// TODO ist mein server runable oder muss ich ne do while schleife machen?
		try{
			JSONObject jData = (JSONObject) receiveData().get("data");
			switch((String) jData.get("message")){
			case "end":
				//an alle end senden?
				break;
			case "next":
				game = (Game) jData.get("game");
				game = updateGameWithPlayers(game);
				jResponse = new JSONObject();
				jResponse.put("message", "next");
				jResponse.put("game", game);
				this.sendData(jResponse, game.getGameName());
				break;
			case "player":
				Player player = (Player) jData.get("player");
				addPlayerToHashMap(player, jData.getString("gameName"));
				// alle Playerupdates auf Server in Map von Set speichern
				//wenn next von jeweiligem Spiel aufgerufen wird, dann updaten bevor versenden
				break;
			case "playerCall":
				int playerQR = (Integer) jData.get("playerQR");
				int roomQR = (Integer) jData.get("roomQR");
				//callPlayer(playerQR, roomQR);
				break;
			case "prosecution":
				//setOutToAllButMe("prosecution");
				break;
			case "suspection":
				//suspection();
				break;
			case "dead":
				//TODO was passiert hier eigentlich
				break;
			case "pause":
				break;
			case "join":
				if((boolean) jData.getBoolean("passwordprotected")){ //TODO anlegen
					if(checkPassword((String) jData.getString("gameName"), (String) jData.getString("password"))){
						//TODO join
					}else{ //TODO pwError
						
					}
				}else{
					//TODO join
				}
				break;
			case "newGame": //TODO stimmt dieser Code?
				game = (Game) jData.get("game");
				String message;
				if(games.containsKey(game.getGameName())){
					message = "gameNameError";
				}else{
					games.put(game.getGameName(), game);
					message = "waitForPlayers";
				}
				jResponse = new JSONObject();
				jResponse.put("message", message);
				sendData(jResponse, game.getGameName()); 
				//TODO geht das so?
				break;
			case "search":
				jResponse = new JSONObject();
				jResponse.put("search", searchGame((String) jData.get("searchString")));
				this.sendData(jResponse, "FUCK"); //TODO AN Individuum nicht an Gruppe senden
				break;
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
	}


	private void addPlayerToHashMap(Player player, String gameName) {
		if(updatePlayers.containsKey(gameName)){
			updatePlayers.get(gameName).put(player.getQrCode(), player);
		}else{
			updatePlayers.put(gameName, new HashMap<Integer, Player>());
			updatePlayers.get(gameName).put(player.getQrCode(), player);
		}
		
	}


	private Game updateGameWithPlayers(Game game) {
		if(updatePlayers.containsKey(game.getGameName())){
			HashMap<Integer, Player> players = updatePlayers.get(game.getGameName());
			int code;
			for(int i = 0; i < game.getPlayerAmount(); i++){
				code = game.getPlayers().get(i).getQrCode();
				if(updatePlayers.containsKey(code)){
					game.updatePlayer(players.get(code));
				}
			}
			updatePlayers.remove(game.getGameName());
		}
		return game;
	}


	private boolean checkPassword(String gamename, String password) {
		return games.get(gamename).checkPwd(password);
	}
}
