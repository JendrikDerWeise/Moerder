package com.example.jendrik.moerder.FCM;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.jendrik.moerder.GUI.MyApplication;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.GameObjekts.Solution;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jendrik on 11.06.2016.
 */
public class FCMListeners extends MyApplication{
    private String gameName;
    private Game game;

    private FirebaseDatabase database;
    private ChildEventListener ce;
    private double number;
    private Solution solution;
    private ArrayList<String> rooms;
    private ArrayList<String> weapons;
    private ArrayList<String> players;
    private double min;
    private double sec;

    private DatabaseReference listReference;
    private ValueEventListener veList;

    public FCMListeners(String gameName, Game game){

        this.gameName=gameName;
        database = FirebaseDatabase.getInstance();
       // Log.e("testen",database.child("games").child(gameName).getKey());

        this.game=game;
        ce = bindListener();
        database.getReference().child("games").addChildEventListener(ce);
       // database.child("games").child(gameName).keepSynced(true);

    }

    public FCMListeners(String gameName){
        this.gameName=gameName;
        database = FirebaseDatabase.getInstance();
        /*stringListListener("roomList");
        stringListListener("weaponlist");
        stringListListener("connectedPlayers");
        solutionListener();
        doubleListener("min");
        doubleListener("sec");*/

        listReference = FirebaseDatabase.getInstance().getReference().child("games").child(gameName).child("roomList");
        veList = stringListListener("roomList");
        listReference.addListenerForSingleValueEvent(veList);
    }


    public ChildEventListener bindListener(){

        ChildEventListener ce = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("hurra","listener!");
                updateGame(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("hurra","listener!");
                updateGame(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                updateGame(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                updateGame(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };/*

*/
        return ce;
    }


    private ValueEventListener stringListListener(String list){
        final String listName=list;
        ValueEventListener ve =
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("fuellen","listen listener"+dataSnapshot.getKey());
                        fillList(listName,dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
        return ve;
    }

    private void doubleListener(final String doubleName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("games").child(gameName).child(doubleName).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("fuellen","double listener"+dataSnapshot.getKey());
                        fillDouble(doubleName, dataSnapshot);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void fillDouble(String doubleName, DataSnapshot dataSnapshot){
        switch (doubleName){
            case "min":
                min = dataSnapshot.getValue(Double.class);
                break;
            case "sec":
                sec = dataSnapshot.getValue(Double.class);
                break;
        }
    }

    public void  solutionListener(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("games").child(gameName).child("solution").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        fillSolution(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void fillList(String listName, DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            final String name = ds.getKey();
            Log.d("fuellen","fill list: " + listName + " "+name);
            switch (listName) {
                case "roomList":
                    rooms.add(name);
                    break;
                case "weaponlist":
                    weapons.add(name);
                    break;
                case "connectedPlayers":
                    players.add(name);
                    break;
            }
        }
    }

    private void fillSolution(DataSnapshot dataSnapshot){
        solution = dataSnapshot.getValue(Solution.class);
    }

    public Game makeGameObjectForClient(){



        String str = ""+min;

        Log.d("solution",rooms.get(1));

        Game game = new Game(gameName, "",rooms,weapons,(int)min,(int)sec,players.size()+1);
        game.setSolution(solution);
        game.startGame(players);
        return game;
    }


    private void updateGame(DataSnapshot snapshot){
        Log.e("hurra","updateGame");
       // for(DataSnapshot ds : snapshot.getChildren()){
         //   if(ds.getKey() == gameName){
                Game game1=snapshot.child(gameName).getValue(Game.class);
               // Log.e("hurra",game.getGameName());
                if(game==null)
                    Log.e("testen", "game is null");
                else
                    game=game1;
           // }
        //}

    }

    public void unbindListeners(){
        database.getReference().removeEventListener(ce);
    }


}
