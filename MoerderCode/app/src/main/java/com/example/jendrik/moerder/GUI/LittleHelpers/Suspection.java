package com.example.jendrik.moerder.GUI.LittleHelpers;

import android.content.Context;
import android.content.Intent;

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
    private Context context;
    private String cardOwner;
    private String card;


    /**
     * Urspruenglich mal gedacht als versendbares Objekt gedacht.
     * Es wird reih-um an jeden Spieler geschickt, wo jeweils die Methoden des Objektes genutzt werden koennen.
     *
     * @param context
     * @param player verdächtigter Spieler
     * @param room verdächtigter Raum
     * @param weapon verdächtigte Waffe
     * @param game Spiel-Objekt
     * @param suspector Spieler der Verdacht ausspricht
     */
    public Suspection(Context context, String player, String room, String weapon, Game game, int suspector){
        this.context = context;
        this.player=player;
        this.room=room;
        this.weapon=weapon;
        this.game=game;
        this.suspector=suspector;
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

    public String getCard(){
        return card;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public void setCard(String card){
        this.card = card;
    }
    /**
     * Methode informiert Spieler darueber, dass er einen Clue besitzt und fordert auf, aus den Clues einen zu zeigen.
     * Diese Info wird an den aktiven Spieler, der den Verdacht geaeussert hat gesendet.
     * Sus-Objekt muss jetzt an aktiven Spieler gesendet werden.
     *
     * @param playerHasCard
     */
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

    /**
     * Methode zeigt das Ergebnis der Verdaechtigung.
     *
     * @param shownCard
     * @param playerHasCard
     */
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
