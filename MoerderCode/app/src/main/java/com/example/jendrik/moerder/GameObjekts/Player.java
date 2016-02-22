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
    private Weapon actualWeapon;
    private Room actualRoom;
    private ArrayList<Card> handCards;

    public Player(String name, int qrCode){
        active=false;
        this.qrCode=qrCode;
        this.name=name;
    }

    public String getName(){
        return name;
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

    public Weapon getActualWeapon() {
        return actualWeapon;
    }

    public void setActualWeapon(Weapon actualWeapon) {
        this.actualWeapon = actualWeapon;
    }

    public Room getActualRoom() {
        return actualRoom;
    }

    public void setActualRoom(Room room){
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

}
