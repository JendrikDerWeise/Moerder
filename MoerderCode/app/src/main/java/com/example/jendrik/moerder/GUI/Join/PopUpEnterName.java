package com.example.jendrik.moerder.GUI.Join;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.example.jendrik.moerder.R;

/**
 * Created by bulk on 01.06.2016.
 */
public class PopUpEnterName extends Activity {
    public static final String NAME = "playerName";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.popup_enter_name);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.6));

        //Intent Name des Spielers
        //irgendwie warten, dass der Server ein okay gibt und dann automatisch WaitForServer aufrufen
        //sonst PopUp immer wieder neu ausführen
    }

    public void onClickOK(View button){
        EditText et = (EditText) findViewById(R.id.enterPlayerName);
        String pName = et.toString();
        boolean nameOK = false;

        //an den Server senden
        //nameOK = true/false
        if(nameOK){
            final Intent intent = new Intent(this,WaitForServer.class);
            intent.putExtra(NAME, pName);
            startActivity(intent);
            finish();
        }else {
            final Intent restartIntent = new Intent(this,PopUpEnterName.class);
            startActivity(restartIntent);
            finish();
        }
    }
}
