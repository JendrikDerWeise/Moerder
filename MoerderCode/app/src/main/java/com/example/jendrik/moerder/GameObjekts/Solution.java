package com.example.jendrik.moerder.GameObjekts;

import java.io.Serializable;

/**
 * Created by Jendrik on 22.02.2016.
 */
public class Solution implements Serializable {

    private String murder, weapon, room;

    public Solution(String m, String w, String r){
        murder=m;
        weapon=w;
        room=r;
    }

    public String getMurder() {
        return murder;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getRoom() {
        return room;
    }
}
