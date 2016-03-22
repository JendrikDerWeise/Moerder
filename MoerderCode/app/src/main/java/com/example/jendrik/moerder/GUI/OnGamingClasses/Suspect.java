package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.suspect_fragment, container, false);

        fillSpinner();

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void fillSpinner(){
        //TODO
        Bundle extras = getActivity().getIntent().getExtras();
        Game game = (Game) extras.get("GAME");

        ArrayList<String> players = new ArrayList<>();
        for(Player p: game.getPlayers()){
            players.add(p.getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, players);

        spinnerPlayer = (Spinner)fragLayoutV.findViewById(R.id.spinner_suspect_person);
        spinnerPlayer.setAdapter(spinnerAdapter);



    }

    public void onClickIndict(View btn){
        //TODO
    }
}


