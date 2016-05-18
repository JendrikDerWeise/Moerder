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
	private HashMap<String, ArrayList<ClientRequestProcessor>> playerClients;
	private HashMap<String, ArrayList<String>> players;
	public static final String API_KEY = "AIzaSyC6CeB3mHAPZM15ka0P24Q5P4VFfyxwFeo";
	
	
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
	
	
	
	public static void main(String[] args) throws JSONException{
		MoerderServer server = new MoerderServer(0);
		if (args.length < 1 || args.length > 2 || args[0] == null) {
            System.err.println("usage: ./gradlew run -Pmsg=\"MESSAGE\" [-Pto=\"DEVICE_TOKEN\"]");
            System.err.println("");
            System.err.println("Specify a test message to broadcast via GCM. If a device's GCM registration token is\n" +
                    "specified, the message will only be sent to that device. Otherwise, the message \n" +
                    "will be sent to all devices subscribed to the \"global\" topic.");
            System.err.println("");
            System.err.println("Example (Broadcast):\n" +
                    "On Windows:   .\\gradlew.bat run -Pmsg=\"<Your_Message>\"\n" +
                    "On Linux/Mac: ./gradlew run -Pmsg=\"<Your_Message>\"");
            System.err.println("");
            System.err.println("Example (Unicast):\n" +
                    "On Windows:   .\\gradlew.bat run -Pmsg=\"<Your_Message>\" -Pto=\"<Your_Token>\"\n" +
                    "On Linux/Mac: ./gradlew run -Pmsg=\"<Your_Message>\" -Pto=\"<Your_Token>\"");
            System.exit(1);
        }
        try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
            jData.put("message", args[0].trim());
            // Where to send GCM message.
            if (args.length > 1 && args[1] != null) {
                jGcmData.put("to", args[1].trim());
            } else {
                jGcmData.put("to", "/topics/global");
            }
            // What to send in GCM message.
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            System.out.println("Check your device/emulator for notification or logcat for " +
                    "confirmation of the receipt of the GCM message.");
        } catch (IOException e) {
            System.out.println("Unable to send GCM message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        }
	}
}
