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
		
		Object inputO = "";
		//Hauptschleife zum ständigen Abfragen von Clientaktionen
		do {
			
			try {
				inputO = inO.readObject();
				
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}
			
			if(inputO.equals((String) "ende")){}
			
			else if(inputO.equals((String) "weiter")){
				this.game = getGame();
				oneRound();
			}
			
			else if(inputO.equals((String) "spieler")){
				Player player = getPlayer();
				sendPlayerToAll(player);
				game.updatePlayer(player);
			}
			
			else if(inputO.equals((String) "spielerU")){
				Player player = getPlayer();
				game.updatePlayer(player);
			}
			
			else if(inputO.equals((String) "spielerR")){
				int spielerQR = readInt();
				callPlayer(spielerQR);
			}
		
			else if(inputO.equals((String) "spielerR2")){
				callPlayer();
			}
			
			else if(inputO.equals((String) "anklage")){
				indict();
			}
			
			else if(inputO.equals((String) "anklage2")){
				indictTwo();
			}
			
			else if(inputO.equals((String) "tot")){
				//TODO was passiert hier eigentlich
			}
			
			else{}
			
			
		} while (!(inputO.equals((String) "ende")));
		
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
	
	private void callPlayer(){
		setOut("spielerR2");
	}

	private void callPlayer(int spielerQR) {
		for(int i = 0; i < allPlayers.size(); i++){
			if(allPlayers.get(i).getQR() == spielerQR){
				allPlayers.get(i).setOut("spielerR2");
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
	
	private void indict() {
		for(int i = 0; i < allPlayers.size(); i++){
			if(i != qrCode-1){
				allPlayers.get(i).setOut("anklage2");
			}
		}
		
	}

	private void indictTwo(){
		try{
			outO.reset();
			outO.writeObject("anklage2");
			outO.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void oneRound(){
		if(game.getActivePlayer().getQrCode() == this.qrCode){
			sendGame(game);
			game = getGame();
			game.setActivePlayer();	
			allPlayers.get(game.getActivePlayer().getQrCode()).sendGame(game);
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
				allPlayers.get(i).setOut("weiter");
				allPlayers.get(i).sendGame(game);
			}
		}
	}
	
	public void sendPlayerToAll(Player player){
		for(int i = 0; i < allPlayers.size(); i++){
			if(i != qrCode-1){
				allPlayers.get(i).setOut("spielerU");
				allPlayers.get(i).sendPlayer(player);
			}
		}
	}

}
