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
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Weapon;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 20.03.2016.
 */
public class MapOverview extends Fragment {
    private View fragLayoutV;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvadapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    public static ArrayList<String> roomNames;
    public static ArrayList<String> weaponNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Diese Activity stellt nur das Geruest fuer die Spielkarte her. Die eigentliche Arbeit uebernehmen die Adapter
     * siehe LittleHelpers->MapAdapterClass
     * roomNames und weaponNames muessen statisch sein, damit die Adapter auf diese zugreifen und darstellen koennen.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.fragment_map, container, false);

        roomNames = new ArrayList<>();
        weaponNames = new ArrayList<>();

        //Jeder Raum braucht seine eigene Waffenliste.
        //Leider funktioniert es nicht mit einzelnen Waffennamen, daher ist ein Element in der Waffenliste
        //mit einer neuen Zeile vom naechsten WAffen-Element getrennt. (String-Liste)
        //Beispiel: Raum1 enthaelt -> Waffe1\nWaffe2\n
        //          Raum2 enthaelt -> \n
        //          Raum3 enthaelt -> Waffe3\n
        //alles nach "->" ist ein Listeneintrag in der Arrayliste.
        for(Room r: MenueDrawer.game.getRooms()) {
            roomNames.add(r.getName());
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

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}