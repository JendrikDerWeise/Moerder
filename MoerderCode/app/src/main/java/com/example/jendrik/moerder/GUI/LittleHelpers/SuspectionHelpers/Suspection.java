package com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers;

import android.content.Context;
import android.content.Intent;

import com.example.jendrik.moerder.GUI.OnGamingClasses.GameIsRunningCallback;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.GUI.OnGamingClasses.Suspect;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Jendrik on 22.03.2016.
 */
@Getter
@Setter
@NoArgsConstructor
@IgnoreExtraProperties
public class Suspection {
    private String player;
    private String room;
    private String weapon;
    private String suspector;
    private String clueOwner;
    private String clue;
    private Double suspectionNextPlayer;
    private boolean playerCalled;
    private boolean clueShwon;
    @Exclude
    private GameIsRunningCallback callback;


    /**
     * Urspruenglich mal gedacht als versendbares Objekt gedacht.
     * Es wird reih-um an jeden Spieler geschickt, wo jeweils die Methoden des Objektes genutzt werden koennen.
     *
     * @param player verdächtigter Spieler
     * @param room verdächtigter Raum
     * @param weapon verdächtigte Waffe
     * @param suspector Spieler der Verdacht ausspricht
     */
    public Suspection(String player, String room, String weapon, String suspector){
        this.player=player;
        this.room=room;
        this.weapon=weapon;
        this.suspector=suspector;
        this.playerCalled = false;
        this.clueShwon = false;
        this.suspectionNextPlayer = 0.0;
    }


    @Exclude
    public void whoHasClues(Player player){
        ArrayList<String> existendClues = new ArrayList<>();
        for(Clue c : player.getGivenClues()){
            if(c.getName().equals(this.player))
                existendClues.add(this.player);
            else if(c.getName().equals(room))
                existendClues.add(this.room);
            else if(c.getName().equals(weapon))
                existendClues.add(this.weapon);
        }

        if(existendClues.isEmpty()) {
            if(suspectionNextPlayer+1 == MenueDrawer.game.getPlayerManager().getPlayerList().size())
                suspectionNextPlayer = 0.0;
            else
                suspectionNextPlayer++;
            callback.suspectionNextPlayer();
        }else
            callback.informPlayerWhoHasClue(existendClues);
    }

    /**
     * Methode informiert Spieler darueber, dass er einen Clue besitzt und fordert auf, aus den Clues einen zu zeigen.
     * Diese Info wird an den aktiven Spieler, der den Verdacht geaeussert hat gesendet.
     * Sus-Objekt muss jetzt an aktiven Spieler gesendet werden.
     *
     */
    @Exclude
    public void informPlayer(Player p){
        if(p.getName().equals(suspector))
            callback.informSuspector();
        else
            callback.showSuspectionResultBroadcast();
    }

}
