package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers;

import android.content.Context;
import android.content.Intent;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.GameObjekts.Player;

/**
 * Created by Jendrik on 22.03.2016.
 */
public class Suspection {
    private String player;
    private String room;
    private String weapon;
    private Game game;
    private int suspector;
    private Context context;
    private String cardOwner;

    public Suspection(Context context, String player, String room, String weapon, Game game, int suspector){
        this.context = context;
        this.player=player;
        this.room=room;
        this.weapon=weapon;
        this.game=game;
        this.suspector=suspector;

        checkPlayerCards();
    }


    public void checkPlayerCards(){
        int playerHasCard = 88;

        for(Player p : game.getPlayers()) {
            if (p.isActive()) {
                if(suspector != p.getpNumber()){
                    for(Clue c : p.getGivenClues()){
                        if(c.getName().equals(player) || c.getName().equals(room) || c.getName().equals(weapon)){
                            playerHasCard = p.getpNumber();
                            cardOwner = p.getName();
                        }
                    }
                }
            }
        }
        informPlayer(playerHasCard);
    }

    public String getPlayer() {
        return player;
    }

    public String getRoom() {
        return room;
    }

    public String getWeapon() {
        return weapon;
    }

    public int getSuspector() {
        return suspector;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void informPlayer(int playerHasCard){
        String shownCard="";
        if(playerHasCard == 88) //if nobody has suspection
            informSuspector(shownCard, 88);
        else {
            //TODO Activity zum Auswählen einer Handkarte
            //Spieler kann mehrere Karten besitzen
            informSuspector(shownCard, playerHasCard);
        }

    }

    public void informSuspector(String shownCard,int playerHasCard){
        //TODO Testdaten löschen
        cardOwner = "Peter";
        shownCard = "Käsebrötchen";
        playerHasCard = 88;
        if(playerHasCard == 88){
            Intent i = new Intent(context, PopUpNobodyHasCards.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, PopUpPlayerShowsCard.class);
            i.putExtra("PLAYER_NAME", cardOwner);
            i.putExtra("CARD", shownCard);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

        //TODO Broadcast-Popup mit Info über Verdacht und wer eine Karte gezeigt hat
    }

}
