package com.example.jendrik.moerder.GameObjekts;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Jendrik on 22.02.2016.
 */
@Getter
@Setter
@NoArgsConstructor
@IgnoreExtraProperties
public class Solution implements Serializable {

    private String murderer, weapon, room;

    public Solution(String murderer , String room, String weapon){
        this.murderer=murderer;
        this.weapon=weapon;
        this.room=room;
    }
}
