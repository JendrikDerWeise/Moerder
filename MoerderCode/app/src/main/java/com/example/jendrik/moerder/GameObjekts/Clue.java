package com.example.jendrik.moerder.GameObjekts;

import android.media.Image;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by Jendrik on 21.02.2016.
 */
@Getter
@Setter
@NoArgsConstructor
@IgnoreExtraProperties
public class Clue implements Serializable {

    private String name;
    private double id; //0=player, 1=room, 2=weapon --> für Bildzuweisung

    public Clue(String name, int id) {
        this.name = name;
        this.id = id;
    }

}
