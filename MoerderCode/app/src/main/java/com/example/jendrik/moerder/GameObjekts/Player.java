package com.example.jendrik.moerder.GameObjekts;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jendrik on 21.02.2016.
 */
public class Player implements Serializable{
    private String name;
    private boolean active;
    private boolean dead;
    private double qrCode;
    private List<Weapon> actualWeapon;
    private List<Room> actualRoom;
    private List<Clue> givenClues;
    private char[] suspectList;
    private double pNumber;

    public Player(String name, int qrCode, int numberOfThings, int pNumber){
        active=false;
        this.qrCode=qrCode;
        this.name=name;
        this.pNumber = pNumber;
        suspectList = new char[numberOfThings];
        givenClues = new ArrayList<>();
        dead = false;
    }

    public Player(){}

    public String getName(){
        return name;
    }

    private void clear(){ //oder doch public? wer fuehrt das spaeter aus?
        actualRoom = null;
        actualWeapon = null;
    }

    public double getQrCode() {
        return qrCode;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Weapon getActualWeapon() {
        return actualWeapon.get(0);
    }

    public void setActualWeapon(Weapon actualWeapon) {
        this.actualWeapon.clear();
        this.actualWeapon.add(actualWeapon);
    }
    //TODO ggf removeWeapon() einbauen weapon = null

    public Room getActualRoom() {
        return actualRoom.get(0);
    }

    public void setActualRoom(Room room){
        this.actualRoom.clear();
        this.actualRoom.add(room);
    }

    public List<Clue> getGivenClues() {
        return givenClues;
    }

    public void setGivenClues(Clue clue){ givenClues.add(clue); }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public double getpNumber(){ return pNumber; }

    public void suspectOnList(int position, char character){ // zu deutsch verdaechtigen auf der Liste
        //different to suspect, as this is only the internal list for that human
        if(position < suspectList.length && (character == 'n' || character == 'y' || character == 'm')) {
            //n = no, y = yes, m = maybe
            suspectList[position] = character;
        }
    }

    public char getSuspectOnList(int position){
        return suspectList[position];
    }

    public char[] getSuspectList() { return suspectList; }


}
