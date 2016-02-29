package com.example.jendrik.moerder.Manager;

import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class RoomManager implements Serializable{
    private ArrayList<Room> roomList;

    public RoomManager(){
        roomList = new ArrayList<>();
    }

    public void createRoom(String name){
        int qrCode=roomList.size() + 20;
        roomList.add(new Room(name,qrCode));
    }

    public ArrayList<Room> showMap(){
        return roomList;
    }



}
