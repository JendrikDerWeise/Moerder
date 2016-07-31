package com.example.jendrik.moerder.GUI.Host;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jendrik.moerder.GUI.textfieldHelper;
import com.example.jendrik.moerder.R;

import java.util.ArrayList;
import java.util.List;


public class RoomNameList extends Activity {
  //  public static ArrayList ROOM_LIST = "room list";
    private List<EditText> roomNames;
    private Bundle extras;

    /**
     * Die Klasse wurde erstellt, als ich die entsprechende Listenfunktion noch nicht kannte.
     * Daher werden erst die Eingabefelder erstellt und alle nicht benötigten unsichtbar geschaltet.
     * Abhängig davon, wieviele Räume der Spieler eingestellt hat.
     */
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.roomlist_activity);

       extras = getIntent().getExtras();

       roomNames = new ArrayList<>();

       roomNames.add((EditText) findViewById(R.id.editText3));
       roomNames.add((EditText) findViewById(R.id.editText4));
       roomNames.add((EditText) findViewById(R.id.editText5));
       roomNames.add((EditText) findViewById(R.id.editText6));
       roomNames.add((EditText) findViewById(R.id.editText7));
       roomNames.add((EditText) findViewById(R.id.editText8));
       roomNames.add((EditText) findViewById(R.id.editText9));
       roomNames.add((EditText) findViewById(R.id.editText10));
       roomNames.add((EditText) findViewById(R.id.editText11));

       int a=1;
       for(EditText r:roomNames) {
           r.setText("DUMMY ROOM " + a); //TODO löschen bei Endversion
           a++;
       }

       for(int i=extras.getInt(CreateGame.ROOM_COUNT); i < roomNames.size(); i++){
           roomNames.get(i).setVisibility(View.INVISIBLE);
       }
   }

    public void onClickNextButtonR(View button){
        final ArrayList<String> roomList = new ArrayList<>();
        boolean noEmptyFields = true;
        for(int i=0; i < extras.getInt(CreateGame.ROOM_COUNT); i++) {
            if(textfieldHelper.stringIsEmpty(roomNames.get(i).getText().toString())){
                roomNames.get(i).setError( getText(R.string.error_empty_single_textfield ));
                noEmptyFields = false;
            }
        }
        if(noEmptyFields){
            for(int i=0; i < extras.getInt(CreateGame.ROOM_COUNT); i++) {
                roomList.add(roomNames.get(i).getText().toString());
            }
            final Intent intent = new Intent(this, WeaponNameList.class);
            intent.putExtras(extras);
            intent.putExtra("room list", roomList);

            startActivity(intent);
        }

    }
}
