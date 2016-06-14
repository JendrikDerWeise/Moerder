package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.LittleHelpers.Suspection;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Activity wird nur aufgerufen, wenn Spieler eine Waffe bei sich traegt und in einem anderen Raum als der Gruppenraum ist
 */
public class Suspect extends Fragment {

    private View fragLayoutV;
    private Spinner spinnerPlayer;
    private Button btn;
    private Game game;
    private String room;
    private String weapon;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.suspect_fragment, container, false);

        return fragLayoutV;
    }

    /**
     * Wird beim Erstellen ausgefuehrt
     */
    @Override
    public void onStart(){
        super.onStart();
        setRoom();
        setWeapon();
    }

    /**
     * wird ausgefuehrt wenn Activity erstellt ist.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = MenueDrawer.game;

        fillSpinner();
        btnControl();

    }

    /**
     * Erstellt ein Spinner mit den Spielernamen (StringArrayList)
     * Adapter wird benoetigt, um mit dem (ausgewaehlten) Inhalt des Spinners etwas anfangen zu koennen
     * Also das Speichern in einer Variablen
     */
    private void fillSpinner(){
        ArrayList<String> players = new ArrayList<>();
        players.add(getResources().getString(R.string.spinner_default_player));
        for(Player p: game.getPlayers()){
            players.add(p.getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, players);

        spinnerPlayer = (Spinner)fragLayoutV.findViewById(R.id.spinner_suspect_person);
        spinnerPlayer.setAdapter(spinnerAdapter);
    }

    /**
     * Setzt immer den Raum in var room ein, in dem sich der Spieler befindet.
     * Damit wird der Raum in der Activity angezeigt und an das Suspection-Objekt uebergeben.
     */
    private void setRoom(){
        TextView tv = (TextView) fragLayoutV.findViewById(R.id.txt_suspect_room);
        room = game.getActivePlayer().getActualRoom().getName(); //TODO ActivePlayer anpassen!
        tv.setText(room);
    }

    /**
     * Setzt immer die Waffe in var weapon ein, die der Spieler bei sich hat.
     * Damit wird die Waffe in der Activity angezeigt und an das Suspection-Objekt uebergeben.
     */
    private void setWeapon(){
        TextView tv = (TextView)fragLayoutV.findViewById(R.id.txt_suspect_weapon);
        weapon = game.getActivePlayer().getActualWeapon().getName();//TODO ActivePlayer anpassen!
        tv.setText(weapon);
    }

    /**
     * schaltet den Btn nur an, wenn ein Spieler ausgewaehlt wurde.
     * Andernfalls schaltet es den Btn aus.
     */
    private void btnControl(){
        btn = (Button) fragLayoutV.findViewById(R.id.btn_suspect);
        btn.setEnabled(false);

        spinnerPlayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //activate - deactivate Button when change value of player-spinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItemPosition() > 0)
                    btn.setEnabled(true);
                else
                    btn.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Methode produziert eine Suspection
     */
    public void onClickSuspect(){
        String player= (String)spinnerPlayer.getSelectedItem();
        Suspection sus = new Suspection(getActivity(), player, room, weapon, game, (int)game.getActivePlayer().getpNumber());
    }


}


