package com.example.jendrik.moerder.FCM;

import android.support.design.widget.Snackbar;
import android.util.Log;

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
public class FCMListeners {
    private String gameName;
    private Game game;

    private DatabaseReference database;
    private ChildEventListener ce;
    private double number;
    private Solution solution;

    public FCMListeners(String gameName, Game game){

        this.gameName=gameName;
        database = FirebaseDatabase.getInstance().getReference();
       // Log.e("testen",database.child("games").child(gameName).getKey());

        this.game=game;
        ce = bindListener();
        database.child("games").addChildEventListener(ce);
       // database.child("games").child(gameName).keepSynced(true);

    }

    public FCMListeners(){}

    public DatabaseReference getDatabaseWithSingleEventListener(){
        database = FirebaseDatabase.getInstance().getReference();


        return database;
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


    private List<String> getStringLists(String list){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final List<String> readyList = new ArrayList<>();
        database.getReference().child("games").child(gameName).child(list).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            final String name = ds.getKey();
                            readyList.add(name);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return readyList;
    }

    private double getDoublesFromDB(String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("games").child(gameName).child(value).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        number = dataSnapshot.getValue(Double.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return number;
    }

    public Solution getSolutionFromDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("games").child(gameName).child("solution").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        solution = dataSnapshot.getValue(Solution.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return solution;
    }

    public Game makeGameObjectForClient(){
        ArrayList<String> rooms = (ArrayList)getStringLists("roomList");
        ArrayList<String> weapons = (ArrayList)getStringLists("weaponlist");
        ArrayList<String> players = (ArrayList)getStringLists("connectedPlayers");
        Solution solution = getSolutionFromDB();
        double min = getDoublesFromDB("min");
        double sec = getDoublesFromDB("sec");


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
        database.removeEventListener(ce);
    }


}
