package com.example.jendrik.moerder.GUI;
import android.content.res.*;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import android.app.Activity;
import android.widget.TextView;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 27.02.2016.
 */
public class CreateGame extends Activity {
    private String gameName;
    private String passw;
    private String[] cPlayers;
    private int countP;
    private Spinner spinner;
    private TextView testTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategame);
        testTXT = (TextView) findViewById(R.id.test);

        cPlayers = getResources().getStringArray(R.array.players);
        spinner = (Spinner) findViewById(R.id.spinner_player);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cPlayers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                countP = (Integer) spinner.getSelectedItemPosition()-1;
                String txt = "" + countP;
                testTXT.setText(txt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
}
