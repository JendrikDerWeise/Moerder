package com.example.jendrik.moerder.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 29.02.2016.
 */
public class STUB extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.stub_activity);
        LinearLayout ll = new LinearLayout(this);

        Bundle extras = getIntent().getExtras();
        TextView tv = new TextView(this);
        Game g1 = (Game) extras.get("GAME");
        tv.setText(g1.getRooms().get(1).getName()); //idee zum erzeugen der QR-Verteilungliste

        ll.addView(tv);
        setContentView(ll);

    }
}
