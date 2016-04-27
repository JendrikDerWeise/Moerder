package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Weapon;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 22.03.2016.
 */
public class Indict extends Fragment {
    private View fragLayoutV;
    private Spinner spinnerPlayer;
    private Spinner spinnerRoom;
    private Spinner spinnerWeapon;
    private Bundle extras;
    private Game game;
    private boolean personChosen;
    private boolean roomChosen;
    private boolean weaponChosen;
    private Button btn;
    private LayoutInflater inflater;
    private PopupWindow popupWindow;
    private ViewGroup container;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        fragLayoutV = inflater.inflate(R.layout.indict_fragment, container, false);

        extras = getActivity().getIntent().getExtras();
        game = (Game) extras.get("GAME");

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillSpinner();
        btn = (Button) fragLayoutV.findViewById(R.id.btn_indict);
        btn.setEnabled(false);
    }

    public void fillSpinner(){
        Activity activity = getActivity();

        ArrayList<String> players = new ArrayList<>();
        players.add(getResources().getString(R.string.spinner_default_player));
        for(Player p: game.getPlayers()){
            players.add(p.getName());
        }

        ArrayList<String> rooms = new ArrayList<>();
        rooms.add(getResources().getString(R.string.spinner_default_room));
        for(Room r: game.getRooms()){
            rooms.add(r.getName());
        }

        ArrayList<String> weapons = new ArrayList<>();
        weapons.add(getResources().getString(R.string.spinner_default_weapon));
        for(Weapon w: game.getWeapons()){
            weapons.add(w.getName());
        }

        ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, players);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, rooms);
        ArrayAdapter<String> weaponAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, weapons);

        spinnerPlayer = (Spinner)fragLayoutV.findViewById(R.id.spinner_indict_person);
        spinnerPlayer.setAdapter(playerAdapter);

        spinnerRoom = (Spinner)fragLayoutV.findViewById(R.id.spinner_indict_room);
        spinnerRoom.setAdapter(roomAdapter);

        spinnerWeapon = (Spinner)fragLayoutV.findViewById(R.id.spinner_indict_weapon);
        spinnerWeapon.setAdapter(weaponAdapter);

        ArrayList<Spinner> spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add(spinnerPlayer);
        spinnerArrayList.add(spinnerRoom);
        spinnerArrayList.add(spinnerWeapon);
        putListenerOnSpinners(spinnerArrayList);
    }

    public void putListenerOnSpinners(ArrayList<Spinner> spinnerArrayList){
        for(Spinner s : spinnerArrayList){
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //activate - deactivate Button when change value of player-spinner
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (parent.getId()) {
                        case R.id.spinner_indict_person:
                            personChosen = chooseChoser(parent);
                            break;
                        case R.id.spinner_indict_room:
                            roomChosen = chooseChoser(parent);
                            break;
                        case R.id.spinner_indict_weapon:
                            weaponChosen = chooseChoser(parent);
                            break;
                    }
                    checkChosen();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public boolean chooseChoser(AdapterView<?> parent){
        boolean chosen;
        chosen = parent.getSelectedItemPosition() > 0;

        return chosen;
    }

    public void checkChosen(){
        if(personChosen && roomChosen && weaponChosen)
            btn.setEnabled(true);
        else
            btn.setEnabled(false);
    }

    public void onClickIndict(View anchorView){
        View popUp = inflater.inflate(R.layout.popup_are_you_sure,container,false);
        popupWindow = new PopupWindow(popUp, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);

        Button btn_yes = (Button)fragLayoutV.findViewById(R.id.btn_yes);
        Button btn_no = (Button)fragLayoutV.findViewById(R.id.btn_no);

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorView.getHeight());

    }

    public void onClickYes(){
        String player= (String)spinnerPlayer.getSelectedItem();
        String room= (String)spinnerRoom.getSelectedItem();
        String weapon= (String)spinnerWeapon.getSelectedItem();
        boolean playerWins = game.compareSolution(player, room, weapon);

        if(playerWins){
            Log.d("BLA","bist bu dir wirklich sicher? ja, nein, vielleicht! jendrik ist doof. da bin ich mir sicher!");
            Log.d("BLA","das habe ich ich programmiert. jetzt ist das in der welt. und es stimmt!");
            Intent intent = new Intent(getActivity(), WinScreen.class);
            startActivity(intent);
            getActivity().finish();
        }else{
            game.killPlayer(game.getActivePlayer().getpNumber());
            Intent intent = new Intent(getActivity(), LooseScreen.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public void onClickNo(){
        popupWindow.dismiss();
    }
}