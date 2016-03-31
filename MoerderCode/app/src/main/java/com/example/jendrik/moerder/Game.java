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
    private ArrayList<Clue> clueList;
    private int numberOfThings;
    private String gameName;
    private String pwd;
    private int min;
    private int sec;
    private int justScannedQR;
    private int currentPlayerAmount;


    public Game(String gameName, String pwd, ArrayList<String> rooms, ArrayList<String> weapons, int min, int sec){
        this.gameName = gameName;
        this.pwd = pwd;
        this.min = min;
        this.sec = sec;
        solution = null;
        clueList = new ArrayList<Clue>();
        playerManager = new PlayerManager();
        weaponManager = new WeaponManager();
        roomManager = new RoomManager();
        createRooms(rooms);
        createWeapons(weapons);
        numberOfThings = rooms.size() + weapons.size();
        justScannedQR = 0;
        currentPlayerAmount = 0;
    }

    public Game(){} //nur für das Laden verwendet

    public String getGameName() {
        return gameName;
    }

    public int getMin() { return min;}

    public int getSec(){return sec;}

    public Room getGrpRoom(){ return roomManager.getGrpRoom(); }

    private void createClues(){
        for (Player p:playerManager.getPlayerList())
            clueList.add(new Clue(p.getName(), 0));

        for (Room r:roomManager.showMap())
            clueList.add(new Clue(r.getName(),1));

        for (Weapon w:weaponManager.getWeaponList())
            clueList.add(new Clue(w.getName(), 2));

        createSolution();
    }

    private void createPlayer(ArrayList<String> players){
        numberOfThings += players.size();
        for(String s:players) {
            playerManager.addPlayer(s, numberOfThings);
            playerManager.getPlayerList().get(playerManager.getPlayerList().size()-1).setActualRoom(roomManager.getGrpRoom());
        }
        playerManager.getPlayerList().get(0).setActive(true);  //first Player has to be active for starting the game
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

    /**
     *
     * @param playerName Name of the Player that is to be added
     * @return true for it worked, false for it didn't
     */
    public boolean addPlayer(String playerName){
        boolean added = false;
        //TODO
        return added;
    }

    public boolean compareSolution(String murderer, String room, String weapon){
        return solution != null && solution.getMurderer().equals(murderer) && solution.getRoom().equals(room) && solution.getWeapon().equals(weapon);
    }

    public ArrayList<Room> getRooms(){
        return roomManager.showMap();
    }

    public ArrayList<Weapon> getWeapons(){
        return weaponManager.getWeaponList();
    }

    public ArrayList<Player> getPlayers(){
        return playerManager.getPlayerList();
    }

    public boolean getPwd(){
        return pwd != "";
    }

    public boolean checkPwd(String pwd){
        return this.pwd == pwd;
    }

    public Player getActivePlayer(){
        Player player = new Player();
        for(Player p : playerManager.getPlayerList()){
            if(p.isActive())
                player = p;
        }
        return player;
    }

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

    public void startGame(ArrayList<String> players){
        createPlayer(players);
        createClues();
        giveCluesToPlayer();
        playerManager.setSuspectList(getRooms(),getWeapons());
        Log.d("KARTEN", "verteilt");
        //Spiel Speichern über GUI
        //Auslöser zum Senden des Savegames -->gehört in ServerClass
    }
}
