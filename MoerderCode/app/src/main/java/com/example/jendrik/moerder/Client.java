package com.example.jendrik.moerder;

import com.example.jendrik.moerder.GameObjekts.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

/**
 * Created by Sophia on 30.03.2016.
 */
public class Client {
    private Socket clientSocket = null;
    private ObjectInputStream inO;
    private ObjectOutputStream outO;
    static Client client = null;
    private Game game;


    private Client(String host, int port) { //TODO wo wird der Client erstellt
        try {
            clientSocket = new Socket(host, port);
            inO = new ObjectInputStream(clientSocket.getInputStream());
            outO = new ObjectOutputStream(clientSocket.getOutputStream());
            outO.flush();
        } catch (IOException e) {
            System.err.println("Fehler beim Socket-Stream öffnen: " + e);
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException ioe) { /* Fehlerbehandlung */ }
            }
            System.exit(1);
        }
    }

    /**
     * Game gets sent back to Server
     */
    public void done(){
        try {
            outO.reset();
            outO.writeObject(game);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO zurück zum Wartebildschirm/Karte
    }



    public void getGameList(){
        try {
            outO.reset();
            outO.writeObject((String) "search"); //TODO suche mit suchstring
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Set<String> gameList = (Set<String>) inO.readObject();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void getThisPartyStarted(){
        //TODO Visualisierung von es hat geklappt, du bist dabei
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

            if(inputO.equals((String) "end")){}

            else if(inputO.equals((String) "next")){
                //TODO Spielzug machen Screen oeffnen
                //TODO veraendertes Game Objekt speichern
                //TODO was, wenn zwischendurch noch einmal player gesendet wird, der upgedatet werden muss
                sendGame(game);
            }

            else if(inputO.equals((String) "player")){
                game.updatePlayer(readPlayer());
            }

            else if(inputO.equals((String) "playerCall")){
                int room = Integer.getInteger(readString());
                //TODO POPUP "begib dich in den raum room"
            }

            else if(inputO.equals((String) "prosecution")){
                //TODO POPUP "begib dich in den Gruppenraum"
            }

            else if(inputO.equals((String) "dead")){
                //TODO was passiert hier eigentlich
            }

            else{}


        } while (!(inputO.equals((String) "end")));
    }

    public void callPlayer(int qrnr){
        try {
            outO.reset();
            outO.writeObject((String) "playerCall");
            outO.flush();
            outO.reset();
            outO.write(qrnr);
            outO.flush();
            outO.reset();
            outO.write(17); //TODO wo bekomme ich die Raumnummer her?
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinGame(String name){
        try {
            outO.reset();
            outO.writeObject((String) name);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(readString() == "welcome"){
            getThisPartyStarted();
        }else if(readString() == "sorry"){
            //TODO FEHLER
        }
    }

    public void joinGame(String name, String password){
        try {
            outO.reset();
            outO.writeObject((String) name);
            outO.writeObject((String) password);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(readString() == "welcome"){
            getThisPartyStarted();
        }else if(readString() == "sorry"){
            //TODO FEHLER
        }
    }

    public void newGame(Game game){
        //TODO wo kriege ich das Game übergeben
        this.game = game;
        try {
            outO.reset();
            outO.writeObject((String)game.getGameName());
            outO.flush();
            outO.reset();
            outO.writeObject(game);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO in den Wartebildschirm
    }

    public void playerUpdate(Player player){
        game.updatePlayer(player);
        try {
            outO.reset();
            outO.writeObject("player");
            outO.flush();
            outO.reset();
            outO.writeObject(player);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readString(){
        try{
            return (String) inO.readUTF();
        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }

    private Player readPlayer(){
        try{
            Object object = inO.readObject();
            if(object.getClass() == Player.class)
                return (Player) object;
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException c){
            c.printStackTrace();
        }
        return null;
    }

    public void sendGame(Game game){
        //TODO wo kriege ich das Game übergeben
        this.game = game;
        try {
            outO.reset();
            outO.writeObject((String) "next");
            outO.flush();
            outO.reset();
            outO.writeObject(game);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO in den Wartebildschirm
    }

    public void sendName(String string){
        try {
            outO.reset();
            outO.writeObject((String) string);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void yourTurn(){
        try {
            game = (Game) inO.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO irgendein Fenster öffenen oder so
    }
}
