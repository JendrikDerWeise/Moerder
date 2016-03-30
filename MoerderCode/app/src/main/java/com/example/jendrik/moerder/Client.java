package com.example.jendrik.moerder;

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

    public void newGame(Game game){
        //TODO wo kriege ich das Game übergeben
        this.game = game;
        try {
            outO.reset();
            outO.writeObject((String)game.getGameName());
            outO.writeObject(game);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO in den Wartebildschirm
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

    public void joinGame(String name){
        try {
            outO.reset();
            outO.writeObject((String) name);
            outO.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO irgendein JUHU du bist dabei scheiß
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


}
