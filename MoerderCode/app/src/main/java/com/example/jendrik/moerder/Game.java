package com.example.jendrik.moerder;

import android.util.Log;

import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Solution;
import com.example.jendrik.moerder.GameObjekts.Weapon;
import com.example.jendrik.moerder.Manager.PlayerManager;
import com.example.jendrik.moerder.Manager.RoomManager;
import com.example.jendrik.moerder.Manager.WeaponManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private List<Clue> clueList;
    private double numberOfThings;
    private String gameName;
    private String pwd;
    private double min;
    private double sec;
    private double justScannedQR;
    private double playerAmount;
    private boolean gameOver;

    public Game(String gameName, String pwd, ArrayList<String> rooms, ArrayList<String> weapons, int min, int sec, int playerAmount){
        this.gameName = gameName;
        this.pwd = pwd;
        this.min = min;
        this.sec = sec;
        solution = null;
        clueList = new ArrayList<>();
        playerManager = new PlayerManager(false);
        weaponManager = new WeaponManager(false);
        roomManager = new RoomManager(false);
        createRooms(rooms);
        createWeapons(weapons);
        numberOfThings = rooms.size() + weapons.size();
        justScannedQR = 0;
        this.playerAmount = playerAmount;
        gameOver = false;
    }

    public Game(){} //nur für das Laden verwendet

    /**
    *Methode soll prüfen ob die Anklage des Spielers korrekt ist.
    *Gibt true zurück, wenn dies der Fall ist.
     */
    public boolean compareSolution(String murderer, String room, String weapon){
        return solution != null && solution.getMurderer().equals(murderer) && solution.getRoom().equals(room) && solution.getWeapon().equals(weapon);
    }

    public Player getActivePlayer(){
        Player player = new Player();
        for(Player p : playerManager.getPlayerList()){
            if(p.isActive())
                player = p;
        }
        return player;
    }

    /**
    *Setzt eine Liste aus Clue-Objekten zusammen.
     */
    private void createClues(){
        for (Player p:playerManager.getPlayerList())
            clueList.add(new Clue(p.getName(), 0));

        for (Room r:roomManager.showMap())
            clueList.add(new Clue(r.getName(),1));

        for (Weapon w:weaponManager.getWeaponList())
            clueList.add(new Clue(w.getName(), 2));

        createSolution();
    }

    /**
    *Füllt ein String-Array mit den Namen aller bisher angemeldeten Spieler und setzt sie in den Gruppenraum (Startposition).
    *Zum Ende wird Spieler 0 als aktiv markiert.
     */
    private void createPlayer(List<String> players){
        numberOfThings += players.size();
        for(String s:players) {
            playerManager.addPlayer(s,(int) numberOfThings);
            playerManager.getPlayerList().get(playerManager.getPlayerList().size()-1).setActualRoom(roomManager.getGrpRoom());
        }
        playerManager.getPlayerList().get(0).setActive(true);  //first Player has to be active for starting the game
    }

    /**
    *Hat Sophia geschrieben
     */
    private void createSolution(){
        int playerCount = playerManager.getPlayerList().size();
        int roomCount = roomManager.showMap().size();
        int weaponCount = weaponManager.getWeaponList().size();
        Random random = new Random();
        String pName = playerManager.getPlayerList().get(random.nextInt(playerCount)).getName();
        String rName = roomManager.showMap().get(random.nextInt(roomCount)).getName();
        String wName = weaponManager.getWeaponList().get(random.nextInt(weaponCount)).getName();
        solution = new Solution(pName, rName,wName);
        Log.e("SOLUTION", solution.getMurderer() + solution.getRoom() + solution.getWeapon());
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


    public String getGameName() {
        return gameName;
    }

    public double getMin() { return min;}

    public double getSec(){return sec;}

    public Room getGrpRoom(){
        return roomManager.getGrpRoom(); }

    public double getPlayerAmount(){
        return this.playerAmount;
    }

    public List<Room> getRooms(){
        return roomManager.showMap();
    }

    public List<Weapon> getWeapons(){
        return weaponManager.getWeaponList();
    }

    public List<Player> getPlayers(){
        return playerManager.getPlayerList();
    }

    public boolean passwordSecured(){
        return pwd != "";
    }

    public boolean checkPwd(String pwd){
        return this.pwd == pwd;
    }

    /**
    *Methode gibt zu QR-Code zugehörigen Namen zurück. Kann dabei Spieler, Raum oder Waffe sein.
     */
    public String getNameByNumber(int qrnr){
        if(qrnr > 0  && qrnr < 30){
            if(qrnr < 10) {
                return playerManager.getNameByNumber(qrnr);
            }else if(qrnr < 20){
                return weaponManager.getNameByNumber(qrnr);
            }else {
                return roomManager.getNameByNumber(qrnr);
            }
        }else{
            return "error";
        }
    }



    /**
    *Verteilung der Spielkarten (Hinweise) an die Spieler. (Sophia)
     */
    private void giveCluesToPlayer(){
        ArrayList<Clue> copyClueList = new ArrayList<Clue>();//duplicates Cardlist
        for(int i = 0; i < clueList.size(); i++){
            copyClueList.add(i, clueList.get(i));
            Log.d("COPYCLUELIST", clueList.get(i).getName());
        }

        int playerCount = playerManager.getPlayerList().size();
        Random random = new Random();
        int cluePosition = 0;
        boolean given = false;
        while(!copyClueList.isEmpty()){ //until copied List is not Empty
            //Log.d("ERSTE", "hier");
            for(int i = 0; i < playerCount; i++){
                given = false;
                while(!given) {
                    if(copyClueList.size() !=0)
                        cluePosition = random.nextInt(copyClueList.size());
                    else
                        break;
                    if (copyClueList.get(cluePosition).getName() == solution.getMurderer() ||
                            copyClueList.get(cluePosition).getName() == solution.getWeapon() ||
                            copyClueList.get(cluePosition).getName() == solution.getRoom()) {
                        //when the clue is part of the solution
                        //clue is deleted from Copied List
                        copyClueList.remove(cluePosition);
                        //loop repeats
                    }else if(!copyClueList.isEmpty()) { //if card is not part of solution
                        //clue is given
                        playerManager.giveClue(copyClueList.get(cluePosition), i);
                        given = true;
                        //Clue is removed from copied List
                        copyClueList.remove(cluePosition);

                    }
                }
            }
        }

    }

    public void killPlayer(int pNumber){
        playerManager.getPlayerList().get(pNumber).setDead(true);
    }

    public void setJustScannedQR(int qrnr){
        justScannedQR = qrnr;
    }

    public void setNextActivePlayer(){
        for(Player p : playerManager.getPlayerList()){
            if(p.isActive())
                playerManager.setActive(p);
        }
/*
        for(int i = 0; i < playerManager.getPlayerList().size(); i++){
            if(playerManager.getPlayerList().get(i).isActive()){
                if(i + 1 < playerManager.getPlayerList().size()){
                    playerManager.setActive(playerManager.getPlayerList().get(i+1).getName());
                }else{
                    playerManager.setActive(playerManager.getPlayerList().get(0).getName());
                }
            }
        }*/
    }

    /**
    *Methode stupst das Spiel los. Erst wenn alle Daten vorliegen (Spieler, Räume, Waffen) kann die Methode sinnvoll
    *verwendet werden. Die Reihenfolge muss so bestehn bleiben - glaub ich.
     */
    public void startGame(List<String> players){
        createPlayer(players);
        createClues();
        giveCluesToPlayer();
        playerManager.setSuspectList(getRooms(),getWeapons());
        //Spiel Speichern über GUI
        //Auslöser zum Senden des Savegames -->gehört in ServerClass
    }

    public void updatePlayer(Player player){
        playerManager.updatePlayer(player);
    }

    /**
     * Viele Getter fuer Firebase-Gedoens
     */

    public boolean isGameOver() {
        return gameOver;
    }

    public Solution getSolution() {
        return solution;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public WeaponManager getWeaponManager() {
        return weaponManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public List<Clue> getClueList() {
        return clueList;
    }

    public double getNumberOfThings() {
        return numberOfThings;
    }

    public String getPwd() {
        return pwd;
    }

    public double getJustScannedQR() {
        return justScannedQR;
    }
}
