package com.example.jendrik.moerder.FCM;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Jendrik on 11.06.2016.
 */
public class FCMListeners {

    public FCMListeners(){

    }

   /* private ChildEventListener addEventListener(){
        ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) { getUpdate(dataSnapshot); }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { getUpdate(dataSnapshot);}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                getUpdate(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        return eventListener;
    }*/
}
