package com.example.jendrik.moerder.GameObjekts;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Jendrik on 22.02.2016.
 */
@IgnoreExtraProperties
public class Solution implements Serializable {

    private String murderer, weapon, room;

    public Solution(){}

    public Solution(String murderer , String room, String weapon){
        this.murderer=murderer;
        this.weapon=weapon;
        this.room=room;
    }

    public String getMurderer() {
        return murderer;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getRoom() {
        return room;
    }
}
