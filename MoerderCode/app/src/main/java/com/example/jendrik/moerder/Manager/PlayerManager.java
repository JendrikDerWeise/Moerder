package com.example.jendrik.moerder.Manager;

import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.GameObjekts.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class PlayerManager implements Serializable {



    private ArrayList<Player> playerList;

    public PlayerManager(){
        playerList = new ArrayList<>();
    }

    public void addPlayer(String name, int numberOfThings){
        int qrCode = playerList.size()+1;
        playerList.add(new Player(name,qrCode, numberOfThings, playerList.size()));
    }

    public void setActive(String name) {
        for (Player p : playerList) {
            if (p.getName().equals(name))
                p.setActive(true);
            else
                p.setActive(false);
        }
    }

    public void giveClue(Clue clue, int playerNo){ //ich bin mir unsicher wo die Karten verteilt werden. Da muss jedenfalls eine Rechnung hin, damit alle Spieler die gleiche Menge Karten bekommen
        playerList.get(playerNo).setGivenClues(clue);
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public String getNameByNumber(int qrnr){
        for(Player p:playerList){
            if(p.getQrCode() == qrnr){ return p.getName();}
        }
        return "error";
    }
}
