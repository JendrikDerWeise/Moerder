package com.example.jendrik.moerder.GUI.Host;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jendrik.moerder.FCM.MyFcmListenerService;
import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.GUI.textfieldHelper;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameHandler;
import com.example.jendrik.moerder.GameObjekts.Solution;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jendrik on 29.02.2016.
 */
public class WeaponNameList extends Activity {

    public static final String WEAPON_LIST = "weapon list";
    private List<EditText> weaponNames;
    private Bundle extras;

    /**
     * Es gilt das gleiche wie bei RoomNameList
     * @param savedInstanceState
     */
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

        int a=1;
        for(EditText r:weaponNames){
            r.setText("DUMMY WEAPON" + " "+a); //TODO löschen bei Endversion
            a++;
        }

        for(int i=extras.getInt(CreateGame.ROOM_COUNT); i < weaponNames.size(); i++){
            weaponNames.get(i).setText("DUMMY ROOM");
            weaponNames.get(i).setVisibility(View.INVISIBLE);
        }
    }

    public void onClickNextButtonW(View button){
        String gameName = extras.getString(CreateGame.NAME, "Game 1");
        String pass = extras.getString(CreateGame.PASS, "none");
        final ArrayList<String> weaponList = new ArrayList<>();

        boolean noEmptyFields = true;
        for(int i=0; i < extras.getInt(CreateGame.ROOM_COUNT); i++){
            if(textfieldHelper.stringIsEmpty(weaponNames.get(i).getText().toString())){
                weaponNames.get(i).setError(getText(R.string.error_empty_single_textfield));
                noEmptyFields = false;
            }
        }

        if(noEmptyFields){
            for(int i=0; i < extras.getInt(CreateGame.ROOM_COUNT); i++)
                weaponList.add(weaponNames.get(i).getText().toString());
            getIntent().putExtra(WEAPON_LIST, weaponList);
            final Intent intent = new Intent(this, GivenQrCodes.class);
            intent.putExtras(extras);

            int min = extras.getInt(CreateGame.COUNTER_MIN);
            int sec = extras.getInt(CreateGame.COUNTER_SEC);
            int playerAmount = extras.getInt(CreateGame.PLAYER_COUNT);
            Game game = new Game(gameName, pass, extras.getStringArrayList("room list"), weaponList, min, sec, playerAmount);
            intent.putExtra("GAME", game);

            boolean isSecret = extras.getBoolean(CreateGame.SECRET_CHECKED);
            SendToDatabase sendToDatabase = new SendToDatabase(gameName);
            sendToDatabase.createGame();
            sendToDatabase.sendData("isSecret", isSecret);
            sendToDatabase.sendData("countP",game.getPlayerAmount());
            sendToDatabase.sendData("countR", (double)game.getRooms().size());
            sendToDatabase.sendData("min",game.getMin());
            sendToDatabase.sendData("sec",game.getSec());
            if(isSecret)
                sendToDatabase.sendData("pass", game.getPwd());

            sendToDatabase.sendData("roomList", extras.getStringArrayList("room list"));
            sendToDatabase.sendData("weaponlist",weaponList);
            List<String> connectedPlayers = new ArrayList<>();
            connectedPlayers.add("DUMMY");
            sendToDatabase.sendData("connectedPlayers", connectedPlayers);
            sendToDatabase.sendData("running", false);

            intent.putExtra("host", true);
            GameHandler.saveGame(game);
            startActivity(intent);

        }
    }
}
