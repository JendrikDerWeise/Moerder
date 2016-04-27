package com.example.jendrik.moerder.GUI.Host;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 27.02.2016.
 */
public class CreateGame extends Activity {

    public static final String NAME = "game name";
    public static final String SECRET_CHECKED = "checked";
    public static final String PASS = "password";
    public static final String PLAYER_COUNT = "players";
    public static final String ROOM_COUNT = "rooms";
    public static final String COUNTER_MIN = "minutes";
    public static final String COUNTER_SEC = "seconds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategame);
    }

    public void onClickNextScreen(View button){
        final EditText et = (EditText) findViewById(R.id.editText);
        final String gameName = et.getText().toString();

        final CheckBox cb = (CheckBox) findViewById(R.id.cb_password);
        final boolean isSecret = cb.isActivated();

        final Spinner spinner=(Spinner) findViewById(R.id.spinner_player);
        final int pos = spinner.getSelectedItemPosition();
        final int[] cPlayer = getResources().getIntArray(R.array.players);
        final int countP = cPlayer[pos];

        final Spinner spinnerR=(Spinner) findViewById(R.id.spinner_room);
        final int p = spinnerR.getSelectedItemPosition();
        final int[] cRooms = getResources().getIntArray(R.array.rooms);
        final int countR = cRooms[p];
        final EditText etMin = (EditText) findViewById(R.id.et_minutes);
        final String str_min = etMin.getText().toString();
        final int min = Integer.parseInt(str_min);
        final EditText etSec = (EditText) findViewById(R.id.et_seconds);
        final String str_sec= etSec.getText().toString();
        final int sec = Integer.parseInt(str_sec);


        final Intent intent = new Intent(this, RoomNameList.class);
        intent.putExtra(NAME, gameName);
        intent.putExtra(SECRET_CHECKED, isSecret);
        if(isSecret){
            final EditText et2 = (EditText) findViewById(R.id.editText2);
            final String passw = et2.getText().toString();
            intent.putExtra(PASS, passw);
        }
        intent.putExtra(PLAYER_COUNT, countP);
        intent.putExtra(ROOM_COUNT, countR);
        intent.putExtra(COUNTER_MIN, min);
        intent.putExtra(COUNTER_SEC, sec);

        startActivity(intent);
    }


}
