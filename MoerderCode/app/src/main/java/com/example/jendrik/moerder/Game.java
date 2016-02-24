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
    private Solution solution = null;
    private RoomManager roomManager = new RoomManager();
    private WeaponManager weaponManager = new WeaponManager();
    private PlayerManager playerManager = new PlayerManager();
    private ArrayList<Card> cardList = new ArrayList<Card>();

    public Game(ArrayList<String> rooms, ArrayList<String> weapons){
        createRooms(rooms);
        createWeapons(weapons);
    }

    public Game(){} //nur für das Laden verwendet

    private void createCards(){
        for (Player p:playerManager.getPlayerList())
            cardList.add(new Card(p.getName(), 0));

        for (Room r:roomManager.showMap())
            cardList.add(new Card(r.getName(),1));

        for (Weapon w:weaponManager.getWeaponList())
            cardList.add(new Card(w.getName(), 2));

        createSolution();
    }

    private void createPlayer(ArrayList<String> players){
        for(String s:players)
            playerManager.addPlayer(s);
    }

    private void createSolution(){
        int playerCount = playerManager.getPlayerList().size();
        int roomCount = roomManager.showMap().size();
        int weaponCount = weaponManager.getWeaponList().size();
        Random random = new Random();
        String pName = playerManager.getPlayerList().get(random.nextInt(playerCount)).getName();
        String rName = roomManager.showMap().get(random.nextInt(roomCount)).getName();
        String wName = weaponManager.getWeaponList().get(random.nextInt(weaponCount)).getName();
        solution = new Solution(pName, rName,wName);
    }

    private void createRooms(ArrayList<String> rooms){
        for (String s:rooms)
            roomManager.createRoom(s);
    }

    private void createWeapons(ArrayList<String> weapons){
        for (String s:weapons)
            weaponManager.createWeapon(s);

        for (int i = 0; i < weaponManager.getWeaponList().size();i++){ //Zuteilung der Waffen auf die Räume. Vorraussetzung:  Anzahl Waffen <=> Anzahl Räume
            roomManager.showMap().get(i).addWeapon(weaponManager.getWeaponList().get(i));
        }
    }

    public boolean compareSolution(String murderer, String room, String weapon){
        if(solution != null && solution.getMurderer()==murderer && solution.getRoom()==room && solution.getWeapon()==weapon)
            return true;
        else
            return false;
    }

    public ArrayList<Room> getRooms(){
        return roomManager.showMap();
    }

    private void giveCardsToPlayer(){
        ArrayList<Card> copyCardList = new ArrayList<Card>();//duplicates Cardlist
        for(int i = 0; i < cardList.size(); i++){
            copyCardList.add(i, cardList.get(i));
        }

        int playerCount = playerManager.getPlayerList().size();
        Random random = new Random();
        int cardPosition = 0;
        boolean given = false;
        while(!copyCardList.isEmpty()){ //until copied List is not Empty
            for(int i = 0; i < playerCount; i++){
                while(!given) {
                    cardPosition = random.nextInt(copyCardList.size());
                    given = false;
                    if (copyCardList.get(cardPosition).getName() == solution.getMurderer() ||
                            copyCardList.get(cardPosition).getName() == solution.getWeapon() ||
                            copyCardList.get(cardPosition).getName() == solution.getRoom()) {
                        //when the card is part of the solution
                        //card is deleted from Copied List
                        copyCardList.remove(cardPosition);
                        //loop repeats
                    }else { //if card is not part of solution
                        //card is given
                        playerManager.giveCard(copyCardList.get(cardPosition), i);
                        given = true;
                        //Card is removed from copied List
                        copyCardList.remove(cardPosition);
                    }
                }
            }
        }

    }

    public void killPlayer(int pNumber){
        playerManager.getPlayerList().get(pNumber).setDead(true);
    }

    public void startGame(ArrayList<String> players){
        createPlayer(players);
        createCards();
        giveCardsToPlayer();
        //Spiel Speichern über GUI
        //Auslöser zum Senden des Savegames -->gehört in ServerClass
    }

}
