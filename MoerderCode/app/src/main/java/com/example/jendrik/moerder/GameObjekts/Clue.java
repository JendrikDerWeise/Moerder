package com.example.jendrik.moerder.GameObjekts;

import android.media.Image;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Jendrik on 21.02.2016.
 */
@IgnoreExtraProperties
public class Clue implements Serializable {

    private String name;
    private double id; //0=player, 1=room, 2=weapon --> für Bildzuweisung

    public Clue(){}

    public Clue(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
