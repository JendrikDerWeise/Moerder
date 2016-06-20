package com.example.jendrik.moerder.Manager;

import android.util.Log;

import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Weapon;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Jendrik on 21.02.2016.
 */
@Getter
@Setter
@NoArgsConstructor
@IgnoreExtraProperties
public class PlayerManager implements Serializable {

    private List<Player> playerList;
    private double aktivePlayer;
    private SendToDatabase stb;

    public PlayerManager(boolean bool){
        playerList = new ArrayList<>();

    }


    @Exclude
    public void addPlayer(String name, int numberOfThings){
        int qrCode = playerList.size()+1;
        playerList.add(new Player(name,qrCode, numberOfThings, playerList.size()));
    }

    /**
     * Methode legt fest, welcher Spieler an der Reihe ist. Dazu muss der Name des aktiven Spielers übergeben werden.
     */
    @Exclude
    public void setActive(Player player) {
        //playerList.get((int)player.getpNumber()).setActive(false);

        int index = (int)player.getPNumber();
        int newIndex = 0;
        if(index+1 != playerList.size())
            newIndex = index+1;

        for (Player p : playerList) {
            if (p.getName().equals(playerList.get(newIndex).getName())) {
                p.setActive(true);
                aktivePlayer = p.getPNumber();
            }else
                p.setActive(false);
        }
    }


    @Exclude
    public void giveClue(Clue clue, int playerNo){
        playerList.get(playerNo).giveGivenClues(clue);
    }

    @Exclude
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
    @Exclude
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
    @Exclude
    public void updatePlayer(Player player){
        for(int i = 0; i  < playerList.size(); i++){
            if(playerList.get(i).getQrCode() == player.getQrCode()){
                playerList.remove(i);
                playerList.set(i, player); //TODO Stimmt die reihenfolge?
            }
        }
    }
}
