package com.example.jendrik.moerder.FCM;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.Game;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Jendrik on 11.06.2016.
 */
public class FCMListeners {
    private String gameName;
    private Game game;

    private DatabaseReference database;

    public FCMListeners(String gameName, Game game){

        this.gameName=gameName;
        database = FirebaseDatabase.getInstance().getReference();
       // Log.e("testen",database.child("games").child(gameName).getKey());

        this.game=game;
        database.child("games").addChildEventListener(bindListener());
       // database.child("games").child(gameName).keepSynced(true);

    }

    //public Game getGameStat(){

        //database.getDatabase().getReferenceFromUrl()
       // SendToDatabase sendToDatabase = new SendToDatabase("dummy");
       // sendToDatabase.createGame();

      //  return game;
//    }

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
        ValueEventListener ve =
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("testen",dataSnapshot.getKey());
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            if(ds.getKey() == gameName){
                                Game game=ds.getValue(Game.class);
                                Log.e("hurra",game.getGameName());
                                if(game==null)
                                    Log.e("testen", "game is null");
                                else
                                    MenueDrawer.game=game;
                            }
                        }
                        //game = dataSnapshot.getValue(Game.class);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
*/
        return ce;
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


}
