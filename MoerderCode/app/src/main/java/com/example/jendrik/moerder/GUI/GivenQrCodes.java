package com.example.jendrik.moerder.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jendrik.moerder.Game;

/**
 * Created by bulk on 01.03.2016.
 */
public class GivenQrCodes extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.stub_activity);
        LinearLayout ll = new LinearLayout(this);

        Bundle extras = getIntent().getExtras();
        TextView tv_roomName = new TextView(this);
        TextView tv_qrCodeR = new TextView(this);
        TextView tv_weaponName = new TextView(this);
        TextView tv_qrCodeW = new TextView(this);
        Game game = (Game) extras.get("GAME");
        tv_roomName.setText("Raumname: " + game.getRooms().get(1).getName()); //idee zum erzeugen der QR-Verteilungliste
        tv_qrCodeR.setText("QR-Code Raum: " + game.getRooms().get(1).getQrCode());
        tv_weaponName.setText("Waffenname: " + game.getRooms().get(1).getWeaponList().get(0).getName());
        tv_qrCodeW.setText("QR-Code Waffe " + game.getRooms().get(1).getWeaponList().get(0).getQrCode());

        ll.addView(tv_roomName);
        ll.addView(tv_qrCodeR);
        ll.addView(tv_weaponName);
        ll.addView(tv_qrCodeW);
        setContentView(ll);

    }

}
