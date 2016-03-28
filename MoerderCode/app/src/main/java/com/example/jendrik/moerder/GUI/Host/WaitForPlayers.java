package com.example.jendrik.moerder.GUI.Host;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
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

        game.startGame(playerList);

        /*
        Ende der Platzhalterfunktion
        TODO Aktualisierung der Player-Liste zur Laufzeit, bis alle Spieler eingecheckt
         */

        TableLayout table = (TableLayout)findViewById(R.id.player_table);
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

        //TODO Raum und Waffe bei Spieler 0 setzen um Suspect zu bedienen. Später löschen!
        game.getActivePlayer().setActualRoom(game.getRooms().get(0));
        game.getActivePlayer().setActualWeapon(game.getRooms().get(0).getWeaponList().get(0));
        game.getRooms().get(0).addWeapon(game.getWeapons().get(1));
        game.getRooms().get(0).addWeapon(game.getWeapons().get(2));
        game.getRooms().get(0).removeWeapon(game.getWeapons().get(0));
        game.getRooms().get(1).removeWeapon(game.getWeapons().get(1));
        game.getRooms().get(2).removeWeapon(game.getWeapons().get(2));
        Log.d("HUHU", "mist");


        final Intent intent = new Intent(this,MenueDrawer.class);
        intent.putExtra("GAME", game);
        startActivity(intent);
    }
}
