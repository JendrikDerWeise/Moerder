package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.GUI.Startscreen;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 24.03.2016.
 */
public class LooseScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_looses);
/*
        SendToDatabase stb = new SendToDatabase(getIntent().getExtras().getString("gameName"));
        stb.sendData("playerWins", null);
        stb.updateData("prosecutionIsPlaced", null);*/
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, Startscreen.class);
        startActivity(intent);
        this.finish();
    }
}
