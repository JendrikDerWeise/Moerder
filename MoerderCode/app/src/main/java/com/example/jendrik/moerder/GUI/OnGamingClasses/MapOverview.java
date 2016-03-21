package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 20.03.2016.
 */
public class MapOverview extends Fragment {
    Bundle extras;
    View fragLayoutV;
    Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.fragment_map, null);
       /* LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.fragment_map, null);
        for(int i =0; i<3;i++){
            View costum=inflater.inflate(R.layout.test,null);
            parent.addView(costum);
        }
        extras = getActivity().getIntent().getExtras();
        game = (Game) extras.get("GAME");

        getActivity().setContentView(parent);*/

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        FrameLayout frame = (FrameLayout)fragLayoutV.findViewById(R.id.complete_map_layout);
        //LinearLayout roomInflator = (LinearLayout)frame.findViewById(R.id.roomRow);

        Log.e("RAUM", game.getRooms().get(0).getName());
        for(Room r:game.getRooms()){
            frame.addView((LinearLayout)frame.findViewById(R.id.roomRow));
        }
*/
    }
}