package com.example.jendrik.moerder.GUI.Join;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jendrik.moerder.FCM.FCMListeners;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.databinding.ObservableArrayList;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse noch nicht fertig!
 * Es muss noch das Game-Objekt empfangen und MenueDrawer aufgerufen werden
 */
public class WaitForServer extends Activity {

    private String gameName;
    private ListView lv;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference gamestarter;
    private ValueEventListener el;
    private ValueEventListener elGame;
    private List<String> playerNames;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitscreen);
        Button startBtn = (Button) findViewById(R.id.button5);
        startBtn.setVisibility(View.INVISIBLE);

        TextView tvGameName = (TextView) findViewById(R.id.game_name);

        gameName = getIntent().getExtras().getString("gameName");
        tvGameName.setText(gameName);
        lv = (ListView) findViewById(R.id.lv_player_wait);
        playerNames = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("games").child(gameName).child("connectedPlayers");
        el = setListener();
        myRef.addValueEventListener(el);

        gamestarter = FirebaseDatabase.getInstance().getReference().child("games").child(gameName).child("isRunning");
        elGame = setGameStartedListener();
        gamestarter.addValueEventListener(elGame);

    }

    private ValueEventListener setListener(){

        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playerNames.clear();
                for(DataSnapshot sn:dataSnapshot.getChildren()) {
                    String name = sn.getValue(String.class);
                    playerNames.add(name);
                }
                getUpdate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return ve;
    }

    private ValueEventListener setGameStartedListener(){

        ValueEventListener ve = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playerNames.clear();
                boolean isRunning = dataSnapshot.getValue(Boolean.class);
                if(isRunning)
                startGame();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return ve;
    }


    private void getUpdate(){

        if(playerNames.size()>0){
            ArrayAdapter adapter = new ArrayAdapter(WaitForServer.this,android.R.layout.simple_list_item_1,playerNames);
            lv.setAdapter(adapter);
        }
        else
            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT);
    }

    private void startGame(){
        myRef.removeEventListener(el);
        gamestarter.removeEventListener(elGame);
        Intent intent = new Intent(this,MenueDrawer.class);
        intent.putExtra("gameName", gameName);
        intent.putExtra("whoAmI", checkForPlayerNumber());

        FCMListeners fcmListeners = new FCMListeners();
        Game game = fcmListeners.makeGameObjectForClient();
        intent.putExtra("GAME", game);
        Log.d("start","spiel wird gestartet");

        startActivity(intent);

    }

    private int checkForPlayerNumber() {
        String pName = getIntent().getExtras().getString("pName");
        int pNumber = playerNames.indexOf(pName);

        return pNumber;
    }
}
