package com.example.jendrik.moerder.GUI.Host;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.GUI.Join.PopUpEnterName;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;

import android.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jendrik on 19.03.2016.
 */
public class WaitForPlayers extends Activity {

    private Bundle extras;
    private static Game game;
    private ObservableList.OnListChangedCallback<ObservableList<String>> onListChangedCallback;
    public static ObservableArrayList<String> pNameList = new ObservableArrayList();
    public static TableLayout table;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitscreen);

        extras = getIntent().getExtras();
        game = (Game) extras.get("GAME");
        TextView gameName = (TextView) findViewById(R.id.game_name);
        gameName.setText(game.getGameName());


        extras = getIntent().getExtras();
        String ownName = extras.getString(PopUpEnterName.PNAME);
        pNameList.add(ownName);

        platzhalterSpielerListeFuellen();

        //makeActivity();
    }

    private void platzhalterSpielerListeFuellen(){
        /*
        Erzeugung von DUMMY-Playern --> TODO löschen! UND makePlayerObjects(); in BTN AKTIVIEREN!!!
         */
        ArrayList<String> playerList = new ArrayList<>();

        for(int i=0; i < extras.getInt(CreateGame.PLAYER_COUNT); i++)
            playerList.add("Dummy " + i);

        game.startGame(playerList);

        /*
        Ende der Platzhalterfunktion
         */

        TableLayout table = (TableLayout)findViewById(R.id.player_table);
        fillTable(game.getPlayers(), table);
    }

    private void makeActivity(){
        listBinder();
        makeTable();
    }

    private void fillTable(List<Player> players, TableLayout table){
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

    /**
     * Fügt dem WaitScreen einen neuen Spieler hinzu
     * @param pName
     */
    public static void addPlayer(String pName){
        pNameList.add(pName);
    }

    /**
     * Gibt dem Gameobject den Auftrag, die Spielerobjekte zu erstellen.
     */
    private void makePlayerObjects(){
        game.startGame(pNameList);
    }

    /**
     * erstellt eine Tabelle mit allen Spieler in der Liste
     */
    private void makeTable(){
        table = (TableLayout)findViewById(R.id.player_table);
        for(int i=0; i<pNameList.size(); i++){
            TableRow row = new TableRow(this);
            TextView tv = new TextView(this);
            String txt = getResources().getString(R.string.txt_player);
            txt =txt+ " "+(i+1) + ": " + pNameList.get(i);
            tv.setText(txt);
            row.addView(tv);
            table.addView(row);
        }

    }

    /**
     * ChangeListener fuer die SpielerListe. Soll den Waitscreen refreshen und die aktuell angemeldeten Spieler anzeigen.
     */
    private void listBinder(){
        onListChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<String>>() {
            @Override public void onChanged(ObservableList<String> sender) {
                makeTable();
            }

            @Override public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {}

            @Override public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {}

            @Override public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {}

            @Override public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {}
        };
        pNameList.addOnListChangedCallback(onListChangedCallback);
    }

    public void onClickStartGame(View button){

        //TODO prüfen ob Spieleranzahl==Spieler in Liste

        //makePlayerObjects();
        final Intent intent = new Intent(this,MenueDrawer.class);
        intent.putExtra("GAME", game);

        startActivity(intent);
    }
}
