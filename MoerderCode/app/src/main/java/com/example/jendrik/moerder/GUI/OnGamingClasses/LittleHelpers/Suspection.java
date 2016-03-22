package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers;

import com.example.jendrik.moerder.Game;

/**
 * Created by Jendrik on 22.03.2016.
 */
public class Suspection {
    private String player;
    private String room;
    private String weapon;
    private Game game;
    private int suspector;

    public Suspection(String player, String room, String weapon, Game game, int suspector){
        this.player=player;
        this.room=room;
        this.weapon=weapon;
        this.game=game;
        this.suspector=suspector;
    }

    public int checkPlayerCards(){
        int playerHasCard = 88;

        return playerHasCard;
    }

    public String informPlayer(int playerHasCard){
        String shownCard = "";
        //Spieler kann mehrere Karten besitzen
        return shownCard;
    }

    public void informSuspector(String shownCard,int playerHasCard){
        //Aufruf einer Methode beim aktuellen Spieler mit entsprechendem Spielernamen und Kartennamen
    }

}
