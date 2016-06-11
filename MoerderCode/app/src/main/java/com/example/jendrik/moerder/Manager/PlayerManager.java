package com.example.jendrik.moerder.Manager;

import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class PlayerManager implements Serializable {



    private List<Player> playerList;


    public PlayerManager(boolean bool){
        playerList = new ArrayList<>();
    }

    public PlayerManager(){}

    public void addPlayer(String name, int numberOfThings){
        int qrCode = playerList.size()+1;
        playerList.add(new Player(name,qrCode, numberOfThings, playerList.size()));
    }

    /*
    Methode legt fest, welcher Spieler an der Reihe ist. Dazu muss der Name des aktiven Spielers übergeben werden.
     */
    public void setActive(String name) {
        for (Player p : playerList) {
            if (p.getName().equals(name))
                p.setActive(true);
            else
                p.setActive(false);
        }
    }

    public void giveClue(Clue clue, int playerNo){
        playerList.get(playerNo).setGivenClues(clue);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public String getNameByNumber(int qrnr){
        for(Player p:playerList){
            if(p.getQrCode() == qrnr){ return p.getName();}
        }
        return "error";
    }

    /*
    Methode füllt die Notizen des Spielers mit den passenden Namen der Spieler, Waffen und Räume. Diese werden alle mit 'n' markiert.
    Der Spieler sieht dann ein rotes Viereck neben dem Namen in der Activity.
     */
    public void setSuspectList(List<Room> rooms, List<Weapon> weapons){

        for(Player p: playerList){
            int i=0;
            for(Player x: playerList){
                p.suspectOnList(i,"n");
                i++;
            }
            for(Room r : rooms){
                p.suspectOnList(i,"n");
                i++;
            }
            for(Weapon w : weapons){
                p.suspectOnList(i,"n");
                i++;
            }
        }
    }

    /*
    Ich habe keinen blassen Schimmer mehr, welchen Zweck diese Methode hat.
     */
    public void updatePlayer(Player player){
        for(int i = 0; i  < playerList.size(); i++){
            if(playerList.get(i).getQrCode() == player.getQrCode()){
                playerList.remove(i);
                playerList.set(i, player); //TODO Stimmt die reihenfolge?
            }
        }
    }
}
