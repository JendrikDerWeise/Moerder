package com.example.jendrik.moerder.Manager;

import com.example.jendrik.moerder.GameObjekts.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class WeaponManager implements Serializable {

    public ArrayList<Weapon> weaponList;

    public WeaponManager(){

    }

    public void createWeapon(String name){
        int qrCode = weaponList.size() + 9;
        weaponList.add(new Weapon(name,qrCode));
    }

    public ArrayList<Weapon> getWeaponList() {
        return weaponList;
    }

}
