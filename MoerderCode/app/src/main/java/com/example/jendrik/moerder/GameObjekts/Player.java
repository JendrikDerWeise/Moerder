package com.example.jendrik.moerder.GameObjekts;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Jendrik on 21.02.2016.
 */
public class Player implements Serializable{
    private String name;
    private boolean active;
    private boolean dead;
    private int qrCode;
    private String actualWeapon;
    private String actualRoom;
    private ArrayList<Card> handCards;
    private char[] suspectList;

    public Player(String name, int qrCode, int numberOfThings){
        active=false;
        this.qrCode=qrCode;
        this.name=name;
        suspectList = new char[numberOfThings];
        handCards = new ArrayList<>();
    }

    public Player(){}

    public String getName(){
        return name;
    }

    private void clear(){ //oder doch public? wer fuehrt das spaeter aus?
        actualRoom = null;
        actualWeapon = null;
    }

    public int getQrCode() {
        return qrCode;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public String getActualWeapon() {
        return actualWeapon;
    }

    public void setActualWeapon(String actualWeapon) {
        this.actualWeapon = actualWeapon;
    }
    //TODO ggf removeWeapon() einbauen weapon = null

    public String getActualRoom() {
        return actualRoom;
    }

    public void setActualRoom(String room){
        this.actualRoom=room;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public void setHandCard(Card card){
        handCards.add(card);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void suspectOnList(int position, char character){ // zu deutsch verdaechtigen auf der Liste
        //different to suspect, as this is only the internal list for that human
        if(position < suspectList.length && (character == 'n' || character == 'y' || character == 'm')) {
            //n = no, y = yes, m = maybe
            suspectList[position] = character;
        }
    }


}
