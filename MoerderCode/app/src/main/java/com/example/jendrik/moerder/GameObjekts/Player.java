package com.example.jendrik.moerder.GameObjekts;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jendrik on 21.02.2016.
 */
@IgnoreExtraProperties
public class Player implements Serializable{
    private String name;
    private boolean active;
    private boolean dead;
    private double qrCode;
    private Weapon actualWeapon;
    private Room actualRoom;
    private List<Clue> givenClues;
    private List<String> suspectList;
    private double pNumber;

    public Player(String name, int qrCode, int numberOfThings, int pNumber){
        active=false;
        this.qrCode=qrCode;
        this.name=name;
        this.pNumber = pNumber;
        suspectList = new ArrayList<>();
        givenClues = new ArrayList<>();
        dead = false;

        for(int i=0; i<numberOfThings;i++)
            suspectList.add(i,"n");
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
        return actualWeapon;
    }

    public void setActualWeapon(Weapon actualWeapon) {
        this.actualWeapon=actualWeapon;
    }
    //TODO ggf removeWeapon() einbauen weapon = null

    public Room getActualRoom() {
        return actualRoom;
    }

    public void setActualRoom(Room room){
        this.actualRoom = room;
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

    @Exclude
    public void suspectOnList(int position, String string){ // zu deutsch verdaechtigen auf der Liste
        //different to suspect, as this is only the internal list for that human
        if(position < suspectList.size() && (string == "n" || string == "y" || string == "m")) {
            //n = no, y = yes, m = maybe
            suspectList.remove(position);
            suspectList.add(position,string);
        }
    }

    public String getSuspectOnList(int position){
        return suspectList.get(position);
    }

    public List<String> getSuspectList() { return suspectList; }


}
