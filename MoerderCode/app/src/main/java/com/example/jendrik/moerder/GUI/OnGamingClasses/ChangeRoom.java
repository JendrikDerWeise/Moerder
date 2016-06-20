package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class ChangeRoom extends Fragment {
    private View fragLayoutV;
    public static String SCAN_ROOM = "room";
    private static final int VALUE = 503;
    private Game game;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.fragment_change_room, null);
        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = MenueDrawer.game;
        startRoomScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //hier kommen die Daten des Scanners an. Methode gehört zur Super-Klasse, Name somit fest vorgegeben
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {  //Switch case ist vermutlich unnötig
            case (VALUE) : {
                if (resultCode == Activity.RESULT_OK) { //wenn Activity korrekt zuende geführt wurde
                    int qrCode = data.getIntExtra(STUB_SCANNER.RESULT, 0); //Übergabe des Intents (data), dort ist unter dem String RESULT der INT gespeichert... klingt unsinnig, läuft aber so. Die 0 ist Unsinn
                    if(qrCode>19 && qrCode<29){
                        for(Room r : game.getRooms()){
                            if(r.getName().equals(game.getNameByNumber(qrCode))) {
                                game.getActivePlayer().setActualRoom(r);
                                String pNumberString = "" + MenueDrawer.whoAmI;
                                SendToDatabase stb = new SendToDatabase(game.getGameName(),pNumberString);
                                stb.updateData("playerList", game.getPlayerManager().getPlayerList().get(MenueDrawer.whoAmI));
                                TextView textView = (TextView)getActivity().findViewById(R.id.change_room_name);
                                textView.setText(r.getName());
                            }
                        }
                    }
                    else if(qrCode==29){
                        game.getActivePlayer().setActualRoom(game.getGrpRoom());
                        TextView textView = (TextView)getActivity().findViewById(R.id.change_room_name);
                        textView.setText(getActivity().getResources().getText(R.string.grp_room));

                    }else{
                        //TODO Fehlermeldung Toast? Popup?
                        startRoomScan();
                    }

                }
                break;
            }
        }
    }

    private void startRoomScan(){
        final Intent intent = new Intent(getActivity(), STUB_SCANNER.class); //Vorbereitung der neuen Activity, STUB SCANNER ist der "QR-Code Leser"
        //final int kindOfObject = 0;
        //intent.putExtra(SCAN_WEAPON,kindOfObject);
        startActivityForResult(intent, VALUE); //Starten der Activity. Methodenaufruf "...ForResult" impliziert, das die Activity etwas zurück liefert
    }

}