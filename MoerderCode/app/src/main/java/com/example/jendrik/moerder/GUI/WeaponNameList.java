package com.example.jendrik.moerder.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 29.02.2016.
 */
public class WeaponNameList extends Activity {

    public static final String WEAPON_LIST = "weapon list";
    private ArrayList<EditText> weaponNames;
    private Bundle extras;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weaponlist_activity);

        extras = getIntent().getExtras();

        weaponNames = new ArrayList<>();

        weaponNames.add((EditText) findViewById(R.id.editText12));
        weaponNames.add((EditText) findViewById(R.id.editText13));
        weaponNames.add((EditText) findViewById(R.id.editText14));
        weaponNames.add((EditText) findViewById(R.id.editText15));
        weaponNames.add((EditText) findViewById(R.id.editText16));
        weaponNames.add((EditText) findViewById(R.id.editText17));
        weaponNames.add((EditText) findViewById(R.id.editText18));
        weaponNames.add((EditText) findViewById(R.id.editText19));
        weaponNames.add((EditText) findViewById(R.id.editText20));

        for(int i=extras.getInt(CreateGame.ROOM_COUNT); i < weaponNames.size(); i++){
            weaponNames.get(i).setText("DUMMY ROOM");
            weaponNames.get(i).setVisibility(View.INVISIBLE);
        }
    }

    public void onClickNextButtonW(View button){
        final ArrayList<String> weaponList = new ArrayList<>();

        for(int i=0; i < extras.getInt(CreateGame.ROOM_COUNT); i++)
            weaponList.add(weaponNames.get(i).getText().toString());

        getIntent().putExtra(WEAPON_LIST, weaponList);
        final Intent intent = new Intent(this, STUB.class);
        intent.putExtras(extras);
        Game game = new Game("jh", "kdfhv", extras.getStringArrayList("room list"), weaponList);

        startActivity(intent);
    }
}