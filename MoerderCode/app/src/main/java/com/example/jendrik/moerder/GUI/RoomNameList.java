package com.example.jendrik.moerder.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jendrik.moerder.R;

import java.util.ArrayList;

/**
 * Created by Jendrik on 29.02.2016.
 */
public class RoomNameList extends Activity {
  //  public static ArrayList ROOM_LIST = "room list";
    private ArrayList<EditText> roomNames;
    private Bundle extras;

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

       for(int i=extras.getInt(CreateGame.ROOM_COUNT); i < roomNames.size(); i++) {
           roomNames.get(i).setText("DUMMY ROOM");
           roomNames.get(i).setVisibility(View.INVISIBLE);
       }
   }

    public void onClickNextButtonR(View button){
        final ArrayList<String> roomList = new ArrayList<>();

        for(int i=0; i < extras.getInt(CreateGame.ROOM_COUNT); i++)
            roomList.add(roomNames.get(i).getText().toString());

        final Intent intent = new Intent(this, WeaponNameList.class);
        intent.putExtras(extras);
        intent.putExtra("room list", roomList);

        startActivity(intent);
    }
}
