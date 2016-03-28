package com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.ShowClues;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class ClueAdapterClass extends RecyclerView.Adapter<ClueAdapterClass.ViewHolderClass>{

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView clueName;
        ImageView itemImageView;

        public ViewHolderClass(View itemView) {
            super(itemView);
            clueName = (TextView) itemView.findViewById(R.id.txt_clue_name);
            itemImageView = (ImageView) itemView.findViewById(R.id.img_kind_of_clue);
        }
    }

    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.clue_row, null);

        return new ViewHolderClass(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {

        viewHolderClass.clueName.setText(ShowClues.clues.get(i).getName());
        switch (ShowClues.clues.get(i).id()){
            case 0:
                viewHolderClass.itemImageView.setImageResource(R.drawable.pfeil);
                break;
            case 1:
                viewHolderClass.itemImageView.setImageResource(R.drawable.sende_position);
                break;
            case 2:
                viewHolderClass.itemImageView.setImageResource(R.drawable.snackbar_background);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return ShowClues.clues.size();
    }
}
