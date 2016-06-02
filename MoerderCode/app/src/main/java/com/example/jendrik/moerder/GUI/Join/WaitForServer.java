package com.example.jendrik.moerder.GUI.Join;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;

import android.databinding.ObservableArrayList;

import java.util.ArrayList;

/**
 * Klasse noch nicht fertig!
 * Es muss noch das Game-Objekt empfangen und MenueDrawer aufgerufen werden
 */
public class WaitForServer extends Activity {

    private ObservableList.OnListChangedCallback<ObservableList<String>> onListChangedCallback;
    public static ObservableArrayList<String> pNameList = new ObservableArrayList();
    public static TableLayout table;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitscreen);
        Button startBtn = (Button) findViewById(R.id.button5);
        startBtn.setVisibility(View.INVISIBLE);

        //TODO GameName empfangen/abspeichern
        String nameOfGame = "TEST";
        TextView gameName = (TextView) findViewById(R.id.game_name);
        gameName.setText(nameOfGame);

        makeActivity();
    }

    private void makeActivity(){
        listBinder();
        makeTable();
    }

    /**
     * Fügt dem WaitScreen einen neuen Spieler hinzu
     * @param pName
     */
    public static void addPlayer(String pName){
        pNameList.add(pName);
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
}
