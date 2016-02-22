package com.example.jendrik.moerder;

import android.media.Image;

import com.example.jendrik.moerder.GameObjekts.*;
import com.example.jendrik.moerder.Manager.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jendrik on 22.02.2016.
 *
 * Hier landet die Spielmechanik. Klassen für hosten und suchen kommen extra
 *
 */
public class Game implements Serializable {
    private Solution solution;
    private RoomManager roomManager;
    private WeaponManager weaponManager;
    private PlayerManager playerManager;
    private ArrayList<Card> cardList;

    public Game(ArrayList<String> rooms, ArrayList<String> weapons){
        createRooms(rooms);
        createWeapons(weapons);
    }

    public Game(){} //nur für das Laden verwendet

    public void startGame(ArrayList<String> players){
        createPlayer(players);
        createCards();
        giveCardsToPlayer();
        //Spiel Speichern über GUI
        //Auslöser zum Senden des Savegames -->gehört in ServerClass
    }

    private void createPlayer(ArrayList<String> players){
        for(String s:players)
            playerManager.addPlayer(s);
    }

    private void createRooms(ArrayList<String> rooms){
        for (String s:rooms)
            roomManager.createRoom(s);
    }

    private void createWeapons(ArrayList<String> weapons){
        for (String s:weapons)
            weaponManager.createWeapon(s);

        for(int i=0; i<weaponManager.getWeaponList().size();i++){ //Zuteilung der Waffen auf die Räume. Vorraussetzung:  Anzahl Waffen <=> Anzahl Räume
            roomManager.showMap().get(i).addWeapon(weaponManager.getWeaponList().get(i));
        }
    }

    private void createCards(){
        for (Player p:playerManager.getPlayerList())
            cardList.add(new Card(p.getName(), 0));

        for (Room r:roomManager.showMap())
            cardList.add(new Card(r.getName(),1));

        for (Weapon w:weaponManager.getWeaponList())
            cardList.add(new Card(w.getName(), 2));

        createSolution();
    }

    private void createSolution(){

        int playerCount = playerManager.getPlayerList().size();
        int roomCount = roomManager.showMap().size();
        int weaponCount = weaponManager.getWeaponList().size();
        Random random = new Random();
        String pName = playerManager.getPlayerList().get(random.nextInt(playerCount)).getName();
        String rName = roomManager.showMap().get(random.nextInt(((playerCount+roomCount) - playerCount) + playerCount)).getName();
        String wName = weaponManager.getWeaponList().get(random.nextInt(((roomCount+playerCount+weaponCount) - roomCount) + roomCount)).getName();
        solution = new Solution(pName, rName,wName);
    }

    public boolean compairSolution(String murder, String room, String weapon){
        if(solution.getMurder()==murder && solution.getRoom()==room && solution.getWeapon()==weapon)
            return true;
        else
            return false;
    }

    public void killPlayer(int pNumber){
        playerManager.getPlayerList().get(pNumber).setDead(true);
    }

    private void giveCardsToPlayer(){
        int i=0;
        int playerCount = playerManager.getPlayerList().size();
        for (Card c:cardList
             ) {
            playerManager.giveCard(c, i);
            if(i+1==playerCount)
                i=0;
            else
                i++;
        }
    }

    public ArrayList<Room> getRooms(){
        return roomManager.showMap();
    }



}
