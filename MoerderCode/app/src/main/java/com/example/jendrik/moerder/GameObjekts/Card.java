package com.example.jendrik.moerder.GameObjekts;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class Card implements Serializable {

    private String name;
    private int id; //0=player, 1=room, 2=weapon --> für Bildzuweisung

    public Card(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int id() {
        return id;
    }

    public String getName() {
        return name;
    }
}
