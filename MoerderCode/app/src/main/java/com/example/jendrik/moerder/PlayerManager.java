package com.example.jendrik.moerder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class PlayerManager implements Serializable {

    private ArrayList<Player> playerList;

    public PlayerManager(){

    }

    public void addPlayer(String name){
        int qrCode = playerList.size()+1;
        playerList.add(new Player(name,qrCode));
    }

    public void setActive(String name) {
        for (Player p : playerList
                ) {
            if (p.getName().equals(name))
                p.setActive(true);
            else
                p.setActive(false);
        }
    }

    public void giveCard(Card card, int playerNo){ //ich bin mir unsicher wo die Karten verteilt werden. Da muss jedenfalls eine Rechnung hin, damit alle Spieler die gleiche Menge Karten bekommen
        playerList.get(playerNo).setHandCard(card);
    }
}
