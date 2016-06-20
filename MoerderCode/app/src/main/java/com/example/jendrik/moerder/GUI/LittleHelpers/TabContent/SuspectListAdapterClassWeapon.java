package com.example.jendrik.moerder.GUI.LittleHelpers.TabContent;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class SuspectListAdapterClassWeapon extends RecyclerView.Adapter<SuspectListAdapterClassWeapon.ViewHolderClass>{

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        private TextView colorField;
        private TextView nameOfThing;

        public ViewHolderClass(View itemView) {
            super(itemView);
            colorField = (TextView) itemView.findViewById(R.id.txt_color_field);
            nameOfThing = (TextView) itemView.findViewById(R.id.txt_name_of_thing);
        }
    }


    @Override
    public ViewHolderClass onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.suspect_list_row, null);


        return new ViewHolderClass(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderClass viewHolderClass, final int i) {
        int playerCount = MenueDrawer.game.getPlayers().size();
        int roomCount = MenueDrawer.game.getRooms().size();

        viewHolderClass.nameOfThing.setText(PersonList.namesOfThings.get(i + playerCount + roomCount));
        setSuspectColor(MenueDrawer.game.getActivePlayer().getSuspectOnList(i + playerCount + roomCount), viewHolderClass.colorField);


        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (MenueDrawer.game.getActivePlayer().getSuspectOnList(i)) {
                    case "y":
                        MenueDrawer.game.getActivePlayer().suspectOnList(i, "n");
                        setSuspectColor(MenueDrawer.game.getActivePlayer().getSuspectOnList(i), (TextView) v.findViewById(R.id.txt_color_field));
                        break;
                    case "n":
                        MenueDrawer.game.getActivePlayer().suspectOnList(i, "m");
                        setSuspectColor(MenueDrawer.game.getActivePlayer().getSuspectOnList(i), (TextView) v.findViewById(R.id.txt_color_field));
                        break;
                    case "m":
                        MenueDrawer.game.getActivePlayer().suspectOnList(i, "y");
                        setSuspectColor(MenueDrawer.game.getActivePlayer().getSuspectOnList(i), (TextView) v.findViewById(R.id.txt_color_field));
                        break;
                }
                String pNumberString = "" + MenueDrawer.whoAmI;
                SendToDatabase stb = new SendToDatabase(MenueDrawer.game.getGameName(),pNumberString);
                stb.updateData("playerList", MenueDrawer.game.getPlayerManager().getPlayerList().get(MenueDrawer.whoAmI));
            }
        });
    }

    @Override
    public int getItemCount() {
        return MenueDrawer.game.getWeapons().size();
    }

    private void setSuspectColor(String s, TextView tv){
        switch (s){
            case "y":
                tv.setBackgroundColor(ContextCompat.getColor(MenueDrawer.cont, R.color.suspect_yes));
                break;
            case "n":
                tv.setBackgroundColor(ContextCompat.getColor(MenueDrawer.cont, R.color.suspect_no));
                break;
            case "m":
                tv.setBackgroundColor(ContextCompat.getColor(MenueDrawer.cont, R.color.suspect_maybe));
                break;
        }
    }
}