package com.example.jendrik.moerder.GUI.Join;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jendrik.moerder.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bulk on 09.06.2016.
 */
public class JoinGame extends Activity {
    private FirebaseDatabase database;
    private ListView lv;
    private List<String> games;
    private boolean isSecret;
    private DatabaseReference myRef;
    private ChildEventListener el;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        lv = (ListView)findViewById(R.id.JoinListView);
        games = new ArrayList();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        el = addEventListener();
        myRef.addChildEventListener(el);

        setTouchListener();
    }

    private ChildEventListener addEventListener(){
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
    }

    private void  getUpdate(DataSnapshot snapshot){
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

    private void setTouchListener(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String gameName = games.get(position);

                database.getReference().child("games").child(gameName).child("isSecret").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        isSecret = dataSnapshot.getValue(Boolean.class);
                       if(isSecret) {
                            final Intent intent = new Intent(JoinGame.this, PopUpEnterPassword.class);
                            intent.putExtra("gameName", gameName);
                            intent.putExtra("host",false);
                            startActivity(intent);
                        }else{
                           final Intent intent = new Intent(JoinGame.this, PopUpEnterName.class);
                           intent.putExtra("host",false);
                           intent.putExtra("gameName", gameName);
                           startActivity(intent);
                       }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                myRef.removeEventListener(el);
                finish();
            }
        });
    }
}


