package com.example.jendrik.moerder.FCM;

import com.example.jendrik.moerder.GUI.OnGamingClasses.GameIsRunningCallback;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Jendrik on 19.06.2016.
 */
public class FCMRunningGameListener {

    private String gameName;
    private GameIsRunningCallback callback;
    private DatabaseReference gameReference;
    private ValueEventListener roomListener;

    public FCMRunningGameListener(String gameName, GameIsRunningCallback callback){
        this.gameName = gameName;
        this.callback = callback;
        this.gameReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameName);
    }

    public void roomListListener(){
        roomListener = makeRoomListener();
        gameReference.child("roomManager").child("roomList").addValueEventListener(roomListener);
    }

    private ValueEventListener makeRoomListener(){
        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Room>> t = new GenericTypeIndicator<List<Room>>() {};
                List<Room> roomList = dataSnapshot.getValue(t);
                callback.roomListChanged(roomList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return ve;
    }
}
