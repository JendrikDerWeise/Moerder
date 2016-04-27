package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.ClueAdapterClass;
import com.example.jendrik.moerder.GameObjekts.Clue;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 28.03.2016.
 */
public class ShowClues extends Fragment {
    private View fragLayoutV;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvadapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    public static ArrayList<Clue> clues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.fragment_show_clues, container, false);

        //TODO ActivePlayer ändern in festen Spieler, sobald möglich. Sonst werden die Hinweise der Gegener angezeigt
        clues=MenueDrawer.game.getActivePlayer().getGivenClues();

        final Activity fA = getActivity();
        recyclerView = (RecyclerView) fragLayoutV.findViewById(R.id.recyclerview);
        rvLayoutManager = new LinearLayoutManager(fA);
        recyclerView.setLayoutManager(rvLayoutManager);

        rvadapter = new ClueAdapterClass();
        recyclerView.setAdapter(rvadapter);

        return fragLayoutV;
    }
}