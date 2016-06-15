package com.example.jendrik.moerder.Manager;

import com.example.jendrik.moerder.GameObjekts.Weapon;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jendrik on 21.02.2016.
 */
@IgnoreExtraProperties
public class WeaponManager implements Serializable {

    public List<Weapon> weaponList;

    public WeaponManager(boolean bool){
        weaponList = new ArrayList<>();
    }

    public WeaponManager(){}

    @Exclude
    public void createWeapon(String name){
        int qrCode = weaponList.size() + 10;
        weaponList.add(new Weapon(name,qrCode));
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    @Exclude
    public String getNameByNumber(int qrnr){
        for(Weapon w: weaponList){
            if(w.getQrCode() == qrnr){ return w.getName();}
        }
        return "error";
    }
}
