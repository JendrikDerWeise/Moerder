package com.example.jendrik.moerder.GameObjekts;

import java.io.Serializable;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class Weapon implements Serializable {

    private String name;
    private double qrCode;

    public Weapon(){}

    public Weapon(String name, int qrCode){
        this.name=name;
        this.qrCode=qrCode;
    }

    public String getName(){
        return name;
    }

    public double getQrCode() {
        return qrCode;
    }

}
