package com.example.jendrik.moerder;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class Card implements Serializable {

    private String name;
    private Image image;

    public Card(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
