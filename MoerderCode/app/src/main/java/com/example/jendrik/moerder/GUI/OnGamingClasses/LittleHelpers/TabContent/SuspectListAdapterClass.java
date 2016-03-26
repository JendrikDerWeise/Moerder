package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.TabContent;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MapOverview;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class SuspectListAdapterClass extends RecyclerView.Adapter<SuspectListAdapterClass.ViewHolderClass>{

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        private TextView colorField;
        private TextView nameOfThing;

        public ViewHolderClass(View itemView) {
            super(itemView);
            colorField = (TextView) itemView.findViewById(R.id.txt_color_field);
            nameOfThing = (TextView) itemView.findViewById(R.id.txt_name_of_thing);
        }
    }

    private Bundle extras;
    private Game game;

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.suspect_list_row, null);

        return new ViewHolderClass(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {
        int playerCount = MenueDrawer.game.getPlayers().size();
        int roomCount = MenueDrawer.game.getRooms().size();
        int weaponCount = MenueDrawer.game.getWeapons().size();

        if(i<playerCount){
            viewHolderClass.nameOfThing.setText(MapOverview.weaponNames.get(i));
        }
        else if(i<playerCount+roomCount){

        }
        else{

        }

        viewHolderClass.colorField.setText(MapOverview.roomNames.get(i));


        /*viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MapOverview.tv.setText(MapOverview.itemTexte.get(i));

/*
                switch (i){
                    case 0: //deinCode
                        break;
                    case 1: //deinCode
                        break;  *
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return MapOverview.roomNames.size();
    }
}
