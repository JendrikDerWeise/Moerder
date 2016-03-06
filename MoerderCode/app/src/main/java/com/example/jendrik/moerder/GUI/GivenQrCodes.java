package com.example.jendrik.moerder.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.R;

/**
 * Created by bulk on 01.03.2016.
 */
public class GivenQrCodes extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.stub_activity);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        TableLayout tbl = new TableLayout(this);

        Bundle extras = getIntent().getExtras();
        Game game = (Game) extras.get("GAME");



        for(Room r : game.getRooms()) {
            TableRow row = new TableRow(this);
            TableRow row2 = new TableRow(this);
            TextView tv_roomName = new TextView(this);
            TextView tv_qrCodeR = new TextView(this);
            TextView tv_weaponName = new TextView(this);
            TextView tv_qrCodeW = new TextView(this);

            tv_roomName.setText("Raumname: " + r.getName()); //idee zum erzeugen der QR-Verteilungliste
            tv_qrCodeR.setText("QR-Code Raum: " + r.getQrCode());
            tv_weaponName.setText("Waffenname: " + r.getWeaponList().get(0).getName());
            tv_qrCodeW.setText("QR-Code Waffe " + r.getWeaponList().get(0).getQrCode());

            row.addView(tv_roomName);
            row.addView(tv_qrCodeR);
            row2.addView(tv_weaponName);
            row2.addView(tv_qrCodeW);

            tbl.addView(row);
            tbl.addView(row2);
        }
        ll.addView(tbl);

        Button btn = new Button(this);
        btn.setText("Create Game");
        ll.addView(btn);
        setContentView(ll);

        startActivity(new Intent(GivenQrCodes.this, popupGivenQrCodes.class));

    }

}
