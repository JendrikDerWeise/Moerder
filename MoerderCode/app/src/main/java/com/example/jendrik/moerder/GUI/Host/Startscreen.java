package com.example.jendrik.moerder.GUI.Host;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jendrik.moerder.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Startscreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_startscreen);
        //TODO VERBINDUNG AUFBAUEN

    }

    public void onClickCreateGame(View button){
        final Intent intent = new Intent(this, CreateGame.class);

        startActivity(intent);
    }

}
