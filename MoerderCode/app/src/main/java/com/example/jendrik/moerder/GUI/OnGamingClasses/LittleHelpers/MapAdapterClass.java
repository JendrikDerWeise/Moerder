package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MapOverview;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class MapAdapterClass extends RecyclerView.Adapter<MapAdapterClass.ViewHolderClass>{

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView tvRoomName;
        TextView tvWeaponName;
        TextView tvRoomName2;
        TextView tvWeaponName2;


        public ViewHolderClass(View itemView) {
            super(itemView);
            tvRoomName = (TextView) itemView.findViewById(R.id.txt_roomname_map);
            tvWeaponName = (TextView) itemView.findViewById(R.id.txt_weapons_in_room_map);
            tvRoomName2 = (TextView) itemView.findViewById(R.id.txt_roomname_map2);
            tvWeaponName2 = (TextView) itemView.findViewById(R.id.txt_weapons_in_room_map2);


        }
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.map_row, null);

        return new ViewHolderClass(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {

        int count = MapOverview.roomNames.size();
        int pos = i*2;

        if(pos <= count-1) {

            viewHolderClass.tvRoomName.setText(MapOverview.roomNames.get(pos));
            viewHolderClass.tvWeaponName.setText(MapOverview.weaponNames.get(pos));
            if (pos < count - 1) {
                viewHolderClass.tvRoomName2.setText(MapOverview.roomNames.get(pos + 1));
                viewHolderClass.tvWeaponName2.setText(MapOverview.weaponNames.get(pos + 1));
            }else {
                viewHolderClass.tvRoomName2.setVisibility(View.INVISIBLE);
                viewHolderClass.tvWeaponName2.setVisibility(View.INVISIBLE);
            }
        }

        if(count%2==0) {
            if (i >= count / 2) {
                viewHolderClass.tvRoomName.setVisibility(View.INVISIBLE);
                viewHolderClass.tvWeaponName.setVisibility(View.INVISIBLE);
                viewHolderClass.tvRoomName2.setVisibility(View.INVISIBLE);
                viewHolderClass.tvWeaponName2.setVisibility(View.INVISIBLE);
            }
        }else{
            if (i >= (count / 2) + 1) {
                viewHolderClass.tvRoomName.setVisibility(View.INVISIBLE);
                viewHolderClass.tvWeaponName.setVisibility(View.INVISIBLE);
                viewHolderClass.tvRoomName2.setVisibility(View.INVISIBLE);
                viewHolderClass.tvWeaponName2.setVisibility(View.INVISIBLE);
            }
        }



    }

    @Override
    public int getItemCount() {
        return MapOverview.roomNames.size();
    }
}
