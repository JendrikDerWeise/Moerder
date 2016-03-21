package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class MapAdapterClass extends RecyclerView.Adapter<MapAdapterClass.ViewHolderClass>{

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView tvRoomName;
        TextView tvWeaponName;
        ImageView itemImageView;

        public ViewHolderClass(View itemView) {
            super(itemView);
            tvRoomName = (TextView) itemView.findViewById(R.id.txt_roomname_map);
            tvWeaponName = (TextView) itemView.findViewById(R.id.txt_weapons_in_room_map);
            itemImageView = (ImageView) itemView.findViewById(R.id.imageViewItem);

        }
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.map_row, null);

        return new ViewHolderClass(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {

        viewHolderClass.tvRoomName.setText(MapOverview.roomNames.get(i));
        viewHolderClass.tvWeaponName.setText(MapOverview.weaponNames.get(i));

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
