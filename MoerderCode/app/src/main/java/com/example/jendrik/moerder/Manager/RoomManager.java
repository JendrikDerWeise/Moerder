package com.example.jendrik.moerder.Manager;

import com.example.jendrik.moerder.GameObjekts.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jendrik on 21.02.2016.
 */
public class RoomManager implements Serializable{
    private List<Room> roomList;
    private Room grpRoom;

    public RoomManager(){
        roomList = new ArrayList<>();
        grpRoom = new Room("grp_room", 29);
    }

    public void createRoom(String name){
        int qrCode=roomList.size() + 20;
        roomList.add(new Room(name,qrCode));
    }

    public List<Room> showMap(){
        return roomList;
    }

    public List<Room> getRoomList() {return roomList; }

    public String getNameByNumber(int qrnr){
        for(Room r: roomList){
            if(r.getQrCode() == qrnr){ return r.getName(); }
        }
        return "error";
    }

    public Room getGrpRoom(){
        return grpRoom;
    }

}
