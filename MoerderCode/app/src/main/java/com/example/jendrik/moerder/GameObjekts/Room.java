package com.example.jendrik.moerder.GameObjekts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class Room implements Serializable {
    private String name;
    private int qrCode;
    private ArrayList<Player> playerList;
    private ArrayList<Weapon> weaponList;

    public Room(String name, int qrCode) {
        this.name = name;
        this.qrCode = qrCode;
    }

    public String getName() {
        return name;
    }

    public int getQrCode() {
        return qrCode;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public ArrayList<Weapon> getWeaponList() {
        return weaponList;
    }

    public void addWeapon(Weapon weapon) {
        weaponList.add(weapon);
    }

}
