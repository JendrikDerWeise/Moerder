package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.TabContent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.SuspectListAdapterClass;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 25.03.2016.
 */
public class PersonList extends Fragment {
    private View contentView;
    private Bundle extras;
    private Game game;
    private ArrayList<String> persons;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvadapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    public static TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.tab_content, null);

        extras = getActivity().getIntent().getExtras();
        game = (Game) extras.get("GAME");


        persons = new ArrayList<>();
        for(Player p : game.getPlayers())
            persons.add(p.getName());


        final Activity fA = getActivity();
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerview);
        rvLayoutManager = new LinearLayoutManager(fA);
        recyclerView.setLayoutManager(rvLayoutManager);

        rvadapter = new SuspectListAdapterClass();
        recyclerView.setAdapter(rvadapter);


        tv = (TextView) fA.findViewById(R.id.textView);

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {




        super.onViewCreated(view, savedInstanceState);
    }
}