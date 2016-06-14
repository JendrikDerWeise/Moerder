package com.example.jendrik.moerder.GameObjekts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class Room implements Serializable {
    private String name;
    private double qrCode;
    private List<Player> playerList;
    private List<Weapon> weaponList;

    public Room(){}

    public Room(String name, int qrCode) {
        this.name = name;
        this.qrCode = qrCode;
        weaponList = new ArrayList<>();
        playerList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getQrCode() {
        return qrCode;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public void addWeapon(Weapon weapon) {
        weaponList.add(weapon);
    }

    public void removeWeapon(Weapon weapon){
        for(int i = 0; i<weaponList.size();i++){
            if(weaponList.get(i).getName().equals(weapon.getName()))
                weaponList.remove(i);
        }
    }

}
