package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class ChangeWeapon extends Fragment {
    private View fragLayoutV;
    private Bundle extras;
    public static String SCAN_WEAPON = "weapon";
    private static final int VALUE = 503;
    private Game game;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       fragLayoutV = inflater.inflate(R.layout.stub_activity, null);

        extras = getActivity().getIntent().getExtras();


        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = (Game) extras.get("GAME"); //so bekommst du das GAME objekt

        final Intent intent = new Intent(getActivity(), STUB_SCANNER.class); //Vorbereitung der neuen Activity, STUB SCANNER ist der "QR-Code Leser"
        //final int kindOfObject = 0;
        //intent.putExtra(SCAN_WEAPON,kindOfObject);

        startActivityForResult(intent,VALUE); //Starten der Activity. Methodenaufruf "...ForResult" impliziert, das die Activity etwas zurück liefert
        //TODO prüfen ob RESULT ein Weapon-Object ist, sonst neu scannen - oder was auch immer
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //hier kommen die Daten des Scanners an. Methode gehört zur Super-Klasse, Name somit fest vorgegeben
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {  //Switch case ist vermutlich unnötig
            case (VALUE) : {
                if (resultCode == Activity.RESULT_OK) { //wenn Activity korrekt zuende geführt wurde
                    int qrCode = data.getIntExtra(STUB_SCANNER.RESULT, 0); //Übergabe des Intents (data), dort ist unter dem String RESULT der INT gespeichert... klingt unsinnig, läuft aber so. Die 0 ist Unsinn
                    Log.d("QRCODE", ""+qrCode); //Konsolenausgabe
                }
                break;
            }
        }
    }
}