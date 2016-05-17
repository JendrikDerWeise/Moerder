package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.management.ObjectInstance;
import javax.swing.JOptionPane;

import GameObjekts.Player;
import other.Game;

public class ClientRequestProcessor implements Runnable {
	
	private Socket clientSocket;
	private BufferedReader in;
	private PrintStream out;
	private ObjectInputStream inO;
	private ObjectOutputStream outO;
	private ArrayList<ClientRequestProcessor> allPlayers;
	private HashMap<Integer, Player> updatePlayers;
	private String name;
	private int qrCode;
	private Game game;
	private MoerderServer server;
	private Object inputO = "";
	
	
	/**
	 * Zum Anlegen des Prozessors werden On- und Output Streams erstellt. Sämtliche Kommunikation läuft über das Versenden/Empfangen von Objekten, die 
	 * passend gecastet werden.
	 * Nach erfolgreichem Verbinden mit dem Client verschickt der Prozessor eine Willkommensnachricht an den Client.
	 * @param host
	 * @param port
	 */
	public ClientRequestProcessor(Socket socket, MoerderServer server){
		this.clientSocket = socket;
		this.game = null;
		this.server = server;
		
		try { //Empfangsbereitschaft herstellen
			
			outO = new ObjectOutputStream(clientSocket.getOutputStream());
			outO.flush();
			inO = new ObjectInputStream(clientSocket.getInputStream());

			System.out.println("Prozessor erstellt");
		
		}catch (IOException e) {
			try { //Verbindung zu Client beenden, wenn was schief läuft
				
				clientSocket.close();
				System.out.println("close");
			}catch (IOException e2) {}
			
			System.err.println("Ausnahme bei Bereitstellung des Streams: " + e);
			return;
		}
	}
	
	@Override
	public void run() {
		
		do{
			//auf Spieler warten
		}while(game == null); //TODO testen, ob son scheiss funktioniert
		
		
		//Hauptschleife zum ständigen Abfragen von Clientaktionen
		do {
			
			
			
			try {
				inputO = inO.readObject();
				
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}
			
			if(inputO.equals((String) "end")){
				for(int i = 0; i < allPlayers.size(); i++){
					allPlayers.get(i).setOut("end");
				}
			}
			
			else if(inputO.equals((String) "next")){
				try{
					this.game = (Game) inO.readObject();
				}catch(IOException e){
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			else if(inputO.equals((String) "player")){
				Player player = getPlayer();
				sendPlayerToActive(player);
				game.updatePlayer(player);
			}
			
			else if(inputO.equals((String) "playerCall")){
				int playerQR = readInt();
				int roomQR = readInt();
				callPlayer(playerQR, roomQR);
			}
			
			else if(inputO.equals((String) "prosecution")){
				setOutToAllButMe("prosecution");
			}
			
			else if(inputO.equals((String) "suspection")){
				suspection();
			}
			
			else if(inputO.equals((String) "dead")){
				//TODO was passiert hier eigentlich
			}
			
			else if(inputO.equals((String) "pause")){
				
			}
			
			else{}
			
			oneRound(); //Wenn ich dran bin, mache ich nen Spielzug, wenn nicht passiert nichts
			
		} while (!(inputO.equals((String) "end")));
		this.close();
		server.deleteGame(game.getGameName());
		
	}
	
	public void accept(boolean ac){
		if(ac){
			setOut("welcome");
		}else{
			setOut("sorry");
		}
	}
	
	public void addPlayers(ArrayList<ClientRequestProcessor> arrayList) {
		this.allPlayers = arrayList;
	}
	

	private void callPlayer(int playerQR, int roomQR) {
		setOutToAllButMe("playerCall");
		setOutToAllButMe(Integer.toString(roomQR));
	}
	
	private void close(){
		try{
			in.close();
			out.close();
			clientSocket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	public String getName(){
		if(this.name == null){
			name =  readString();
		}
		return this.name;
	}
	
	public Game getGame(){
		Game game;
		try {
			game = (Game) inO.readObject();
			this.game = game;
			return game;
		} catch (IOException | ClassNotFoundException e) {				
			e.printStackTrace();
		}	
		return null;
	}
	
	public Player getPlayer(){
		Player player;
		try {
			player = (Player) inO.readObject();
			return player;
		} catch (IOException | ClassNotFoundException e) {				
			e.printStackTrace();
		}	
		return null;
	}
	
	public String getPwd(){
		String code = "";
		try {
			code = inO.readUTF();
			return code;
		} catch (IOException e) {				
			e.printStackTrace();
		}	
		return null;
	}

	public int getQR(){
		return this.qrCode;
	}
	
	public void getSearchResult(Set searchGame) {
		try{
			outO.reset();
			outO.writeObject(searchGame);
			outO.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	

	private void oneRound(){
		if(game.getActivePlayer().getQrCode() == this.qrCode && !game.getActivePlayer().isDead()){
			setOut("next");
			sendGame(game);
			boolean done = false;
			do{
				Object object = readObject();
				if(object.equals((String) "next")){
					game = getGame();
					int code;
					if(!updatePlayers.isEmpty()){
						for(int i = 0; i < game.getPlayerAmount(); i++){
							code = game.getPlayers().get(i).getQrCode();
							if(updatePlayers.containsKey(code)){
								game.updatePlayer(updatePlayers.get(code));
							}
						}

						updatePlayers.clear();
					}
					game.setActivePlayer();	
					setGameForAll();
					
					//TODO Spiel an alle schicken
					done = true;
				}else if(object.equals((String) "player")){
					Player object2 = (Player) readObject();
					setUpdatedPlayer(object2);
				}
			}while(!done);
			
		}
		
	}

	private int readInt(){
		return Integer.parseInt(readString());
	}
	
	private String readString(){
		try{
			return (String) inO.readUTF();
		}catch(IOException e){
			e.printStackTrace();
		}
		return "";
		
	}
	
	private Object readObject(){
		try{
			return inO.readObject();
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException c){
			c.printStackTrace();
		}
		return null;
		
	}

	public void setGame(Game game){
		this.game = game;
		setOut("update");
		sendGame(this.game);	
	}

	public String getGameName() {
		String code = "";
		try {
			code = inO.readUTF();
			return code;
		} catch (IOException e) {				
			e.printStackTrace();
		}	
		return null;
	}

	private void setGameForAll() {
		for(int i = 0; i < allPlayers.size(); i++){
			allPlayers.get(i).setGame(this.game);
		}
		
	}

	public void setNumber(int num){
		this.qrCode = num;
	}

	/**
	 * das eigentliche "Senden" des Strings aus anAlle()
	 * @param string
	 */
	public void setOut(String string){
		try {
			outO.reset();
			outO.writeObject((String) string);
			outO.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setOutToAllButMe(String string){
		for(int i = 0; i < allPlayers.size(); i++){
			if(allPlayers.get(i).getQR() != qrCode){
				allPlayers.get(i).setOut(string);
			}
		}
	}

	public void sendGame(Game game){
		this.game = game;
		try{
			outO.reset();
			outO.writeObject(game);
			outO.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendPlayer(Player player){
		game.updatePlayer(player);
		try{
			outO.reset();
			outO.writeObject(player);
			outO.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendPlayerToActive(Player player){
		game.updatePlayer(player);
		for(int i = 0; i < allPlayers.size(); i++){
			if(allPlayers.get(i).getQR() == game.getActivePlayer().getQrCode()){
				allPlayers.get(i).setUpdatedPlayer(player);
			}
		}
	}
	
	public void setUpdatedPlayer(Player player) {
		updatePlayers.put(player.getQrCode(), player);
	}

	public void sendSuspection(String suspection){
		try{
			outO.reset();
			outO.writeUTF("suspection");
			outO.flush();
			outO.reset();
			outO.writeObject(suspection);
			outO.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void suspection() {
		//TODO das ist kaka so, das muss anders sein, inkompatible suspection klassen
		try{
            String suspection =  inO.readUTF();
            
            for(int i = 0; i < allPlayers.size(); i++){
    			if(allPlayers.get(i).getQR() != qrCode){
    				allPlayers.get(i).sendSuspection(suspection);
    			}
    		}
        }catch(IOException e){
            e.printStackTrace();
        }
		
	}

	public void sendBoolean(boolean b) {
		try{
			outO.reset();
			outO.writeBoolean(b);
			outO.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	


}
