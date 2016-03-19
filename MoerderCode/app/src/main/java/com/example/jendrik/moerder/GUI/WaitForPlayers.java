package com.example.jendrik.moerder.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 19.03.2016.
 */
public class WaitForPlayers extends Activity {

    private Bundle extras;
    private Game game;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitscreen);

        extras = getIntent().getExtras();
        game = (Game) extras.get("GAME");
        TextView gameName = (TextView) findViewById(R.id.game_name);
        gameName.setText(game.getGameName());

        /*
        Erzeugung von DUMMY-Playern --> TODO löschen!
         */
        ArrayList<String> playerList = new ArrayList<>();

        for(int i=0; i < extras.getInt(CreateGame.PLAYER_COUNT); i++)
            playerList.add("Dummy " + i);

        game.startGame(playerList); //TODO produziert INFloop
        //game.createPlayer(playerList) //funktioniert, wenn Methode public

        /*
        Ende der Platzhalterfunktion
        TODO Aktualisierung der Player-Liste zur Laufzeit, bis alle Spieler eingecheckt
         */
        TableLayout table = (TableLayout)findViewById(R.id.player_table);
        for(Player p: game.getPlayers())
            Log.d("PLAYERNAMES", p.getName());
        fillTable(game.getPlayers(), table);
    }

    public void fillTable(ArrayList<Player> players, TableLayout table){
        for(int i=0; i<players.size(); i++){
            TableRow row = new TableRow(this);
            TextView tv = new TextView(this);
            String txt = getResources().getString(R.string.txt_player);
            txt =txt+ " "+(i+1) + ": " + players.get(i).getName();
            tv.setText(txt);
            row.addView(tv);
            table.addView(row);
        }
    }

    public void onClickStartGame(View button){
        final Intent intent = new Intent(this,STUB.class);
        intent.putExtra("GAME",game);
        startActivity(intent);
    }
}
