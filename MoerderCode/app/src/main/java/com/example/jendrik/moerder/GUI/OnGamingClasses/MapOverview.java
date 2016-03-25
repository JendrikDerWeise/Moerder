package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.MapAdapterClass;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Weapon;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 20.03.2016.
 */
public class MapOverview extends Fragment {
    private Bundle extras;
    private View fragLayoutV;
    private Game game;

    private  RecyclerView recyclerView;
    private  RecyclerView.Adapter rvadapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    public static ArrayList<String> roomNames;
    public static ArrayList<String> weaponNames;

    static TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.fragment_map, container, false);

        extras = getActivity().getIntent().getExtras();
        game = (Game) extras.get("GAME");

        roomNames = new ArrayList<>();
        weaponNames = new ArrayList<>();
       //int i=0;
        for(Room r: game.getRooms()) {
            roomNames.add(r.getName());
           // r.addWeapon(new Weapon("Testwaffe" + i, (i + 22)));
            //i++;
            String str_weaponNames="";
            for(Weapon w: r.getWeaponList())
                str_weaponNames += w.getName()+"\n";
            weaponNames.add(str_weaponNames);
        }



        final Activity fA = getActivity();
        recyclerView = (RecyclerView) fragLayoutV.findViewById(R.id.recyclerview);
        rvLayoutManager = new LinearLayoutManager(fA);
        recyclerView.setLayoutManager(rvLayoutManager);

        rvadapter = new MapAdapterClass();
        recyclerView.setAdapter(rvadapter);


        tv = (TextView) fA.findViewById(R.id.textView);

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*FrameLayout frame = (FrameLayout) fragLayoutV.findViewById(R.id.complete_map_layout);

        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        for (Room r : game.getRooms()) {
            View view = inflater.inflate(R.layout.test, frame, false);
            // Do whatever you want with view

            frame.addView(view);
        }*/
    }
}