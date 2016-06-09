package com.example.jendrik.moerder.GUI.Join;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jendrik.moerder.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulk on 09.06.2016.
 */
public class JoinGame extends Activity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ListView lv;
    List<String> games;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        lv = (ListView)findViewById(R.id.JoinListView);
        games = new ArrayList();

        receiveData();
    }

    public void receiveData(){
        DatabaseReference myRef = database.getReference();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdate(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdate(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                getUpdate(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void  getUpdate(DataSnapshot snapshot){
        String str = String.valueOf(snapshot.child("games").getChildrenCount());
        Log.d("childs",str);
        Log.d("bla",snapshot.toString());
        games.clear();
        for (DataSnapshot ds : snapshot.getChildren()){
            String gameName = ds.getKey();
            games.add(gameName);
        }

        if(games.size()>0){
            ArrayAdapter adapter = new ArrayAdapter(JoinGame.this,android.R.layout.simple_list_item_1,games);
            lv.setAdapter(adapter);
        }
        else
            Toast.makeText(this,"There are no games on the list!",Toast.LENGTH_SHORT);
    }
}
