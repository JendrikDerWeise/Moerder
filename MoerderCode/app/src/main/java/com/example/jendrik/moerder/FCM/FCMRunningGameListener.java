package com.example.jendrik.moerder.FCM;

import com.example.jendrik.moerder.GUI.OnGamingClasses.GameIsRunningCallback;
import com.example.jendrik.moerder.GameObjekts.Player;
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
    private DatabaseReference roomReference;
    private DatabaseReference playerReference;
    private ValueEventListener roomListener;
    private ValueEventListener playerListener;
    private DatabaseReference activePlayerReference;
    private ValueEventListener activePlayerListener;

    public FCMRunningGameListener(String gameName, GameIsRunningCallback callback){
        this.gameName = gameName;
        this.callback = callback;
    }

    public void roomListListener(){
        roomListener = makeRoomListener();
        roomReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameName);
        roomReference.child("roomManager").child("roomList").addValueEventListener(roomListener);
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

    public void playerListListener(){
        playerListener = makePlayerListener();
        playerReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameName);
        playerReference.child("playerManager").child("playerList").addValueEventListener(playerListener);
    }

    private ValueEventListener makePlayerListener(){
        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Player>> t = new GenericTypeIndicator<List<Player>>() {};
                List<Player> playerList = dataSnapshot.getValue(t);
                callback.playerListChanged(playerList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return ve;
    }

    public void activePlayerListener(){
        activePlayerListener = makeAktivePlayerListener();
        activePlayerReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameName);
        activePlayerReference.child("playerManager").child("aktivePlayer").addValueEventListener(activePlayerListener);
    }

    private ValueEventListener makeAktivePlayerListener(){
        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double aktivePlayer = dataSnapshot.getValue(Double.class);
                callback.aktivePlayerChanged(aktivePlayer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return ve;
    }

    public void unbindListeners(){
        roomReference.removeEventListener(roomListener);
        playerReference.removeEventListener(playerListener);
        activePlayerReference.removeEventListener(activePlayerListener);
    }
}
