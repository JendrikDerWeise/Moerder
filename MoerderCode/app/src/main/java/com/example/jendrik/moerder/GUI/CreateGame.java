package com.example.jendrik.moerder.GUI;
import android.content.Intent;
import android.content.res.*;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import android.app.Activity;
import android.widget.TextView;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 27.02.2016.
 */
public class CreateGame extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategame);
    }

    void onClickNextScreen(View button){
        final EditText et = (EditText) findViewById(R.id.editText);
        final String gameName = et.getText().toString();

        final CheckBox cb = (CheckBox) findViewById(R.id.cb_password);
        final boolean isSecret = cb.isActivated();

        if(isSecret){
            final EditText et2 = (EditText) findViewById(R.id.editText2);
            final String passw = et2.getText().toString();
        }

        final Spinner spinner=(Spinner) findViewById(R.id.spinner_player);
        final int pos = spinner.getSelectedItemPosition();
        final int[] cPlayer = getResources().getIntArray(R.array.players);
        final int countP = cPlayer[pos];


       // final Intent intent = new Intent(this, RoomNames.class);

      //  startActivity(intent);
    }


}
