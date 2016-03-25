package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.TabContent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MapOverview;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 25.03.2016.
 */
public class ListAdapterClass extends RecyclerView.Adapter<ListAdapterClass.ViewHolderClass> {

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView tvName;
        //TextView tvWeaponName;
       // ImageView itemImageView;

        public ViewHolderClass(View itemView) {
            super(itemView);
            //tvName = (TextView) itemView.findViewById(R.id.txt_name_of_thing);


        }
    }
    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_content, null);

        return new ViewHolderClass(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {

        viewHolderClass.tvName.setText(MapOverview.roomNames.get(i));


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


