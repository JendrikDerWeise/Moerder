package com.example.jendrik.moerder.GUI.Host;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jendrik.moerder.R;

/**
 * Aufruf des Starbildschirms
 */
public class Startscreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide(); //macht das Ding oben weg
        setContentView(R.layout.activity_startscreen); //legt das Layout fest

    }

    public void onClickCreateGame(View button){
        final Intent intent = new Intent(this, CreateGame.class); //legt nächste, zu startende Activity fest

        startActivity(intent); //startet nächste Activity
    }

}
