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
	private LinkedList<ClientRequestProcessor> alleSpieler;
	private ArrayList<ClientRequestProcessor> allPlayers;
	private String name;
	private int qrCode;
	private Game game;
	private Object inputO = "";
	
	
	/**
	 * Zum Anlegen des Prozessors werden On- und Output Streams erstellt. Sämtliche Kommunikation läuft über das Versenden/Empfangen von Objekten, die 
	 * passend gecastet werden.
	 * Nach erfolgreichem Verbinden mit dem Client verschickt der Prozessor eine Willkommensnachricht an den Client.
	 * @param host
	 * @param port
	 */
	public ClientRequestProcessor(Socket socket){
		this.clientSocket = socket;
		
		
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
			
		}while(inputO != "go"); //TODO testen, ob son scheiss funktioniert
		
		
		//Hauptschleife zum ständigen Abfragen von Clientaktionen
		do {
			
			try {
				inputO = inO.readObject();
				
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}
			
			if(inputO.equals((String) "end")){}
			
			else if(inputO.equals((String) "next")){
				this.game = getGame();
				oneRound();
			}
			
			else if(inputO.equals((String) "player")){
				Player player = getPlayer();
				sendPlayerToAll(player);
				game.updatePlayer(player);
			}
			
			else if(inputO.equals((String) "playerCall")){
				int playerQR = readInt();
				int roomQR = readInt();
				callPlayer(playerQR, roomQR);
			}
			
			else if(inputO.equals((String) "prosecution")){
				prosecution();
			}
			
			else if(inputO.equals((String) "dead")){
				//TODO was passiert hier eigentlich
			}
			
			else{}
			
			
		} while (!(inputO.equals((String) "end")));
		
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
		for(int i = 0; i < allPlayers.size(); i++){
			if(allPlayers.get(i).getQR() == playerQR){
				allPlayers.get(i).setOut("playerCall");
				allPlayers.get(i).setOut("" + roomQR);
			}
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
	
	private void prosecution() {
		for(int i = 0; i < allPlayers.size(); i++){
			if(i != qrCode-1){
				allPlayers.get(i).setOut("prosecution");
			}
		}
		
	}

	private void oneRound(){
		if(game.getActivePlayer().getQrCode() == this.qrCode && !game.getActivePlayer().isDead()){
			sendGame(game);
			Object object = readObject();
			if(object.getClass() == Game.class){
				game = getGame();
				game.setActivePlayer();	
				allPlayers.get(game.getActivePlayer().getQrCode()).sendGame(game);
			}else if(object.getClass() == String.class){
				if(object.equals("player")){
					Object object2 = readObject();
					if(object2.getClass() == Player.class){
						sendPlayerToAll((Player) object2);
					}
				}
			}
			
		} //TODO passiert hier was
		
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
	}

	public String setGame() {
		String code = "";
		try {
			code = inO.readUTF();
			return code;
		} catch (IOException e) {				
			e.printStackTrace();
		}	
		return null;
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
	
	public void setInputO(String string){
		this.inputO = string;
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
	
	public void sendGameToAll(){
		for(int i = 0; i < allPlayers.size(); i++){
			if(i != qrCode-1){
				allPlayers.get(i).setOut("next");
				allPlayers.get(i).sendGame(game);
			}
		}
	}
	
	public void sendPlayerToAll(Player player){
		game.updatePlayer(player);
		for(int i = 0; i < allPlayers.size(); i++){
			if(i != qrCode-1){
				allPlayers.get(i).setOut("player");
				allPlayers.get(i).sendPlayer(player);
			}
		}
	}

}
