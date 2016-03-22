package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 22.03.2016.
 */
public class Suspect extends Fragment {

    private View fragLayoutV;
    private Spinner spinnerPlayer;
    private Button btn;
    private Bundle extras;
    private Game game;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.suspect_fragment, container, false);

        return fragLayoutV;
    }

    @Override
    public void onStart(){
        super.onStart();
        setRoom();
        setWeapon();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        extras = getActivity().getIntent().getExtras();
        game = (Game) extras.get("GAME");

        fillSpinner();
        //setRoom();
        //setWeapon();

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

    private void setRoom(){
        if(game.getActivePlayer().getActualRoom() != null) {
            TextView tv = (TextView) fragLayoutV.findViewById(R.id.txt_suspect_room);
            String str = game.getActivePlayer().getActualRoom();
            tv.setText(str);
            Log.d("BLA", "blabla");
        }
    }

    private void setWeapon(){
        TextView tv = (TextView)fragLayoutV.findViewById(R.id.txt_suspect_weapon);
        tv.setText(game.getActivePlayer().getActualWeapon());
    }

    public void onClickSuspect(View btn){
        //TODO
    }
}


