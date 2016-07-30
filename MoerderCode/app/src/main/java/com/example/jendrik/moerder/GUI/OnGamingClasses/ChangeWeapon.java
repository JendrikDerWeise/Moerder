package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jendrik.moerder.GUI.OnGamingClasses.GameIsRunningCallback;

import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameObjekts.Room;
import com.example.jendrik.moerder.GameObjekts.Weapon;
import com.example.jendrik.moerder.R;

import java.lang.reflect.Field;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class ChangeWeapon extends Fragment{
    public static String SCAN_WEAPON = "weapon";
    private static final int VALUE = 503;
    private Game game;
    private String actualRoom;
    private SendToDatabase stb;
    private GameIsRunningCallback callback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();

        try {
            this.callback = (GameIsRunningCallback)activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnItemSelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragLayoutV = inflater.inflate(R.layout.stub_activity, null);
        game = MenueDrawer.game;
        actualRoom = MenueDrawer.game.getActivePlayer().getActualRoom().getName();

        String pNumberString = "" + MenueDrawer.whoAmI;
        stb = new SendToDatabase(game.getGameName(),pNumberString);

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        startWeaponScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //hier kommen die Daten des Scanners an. Methode gehört zur Super-Klasse, Name somit fest vorgegeben
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VALUE){
                Room room = new Room("",88);
                if (resultCode == Activity.RESULT_OK) { //wenn Activity korrekt zuende geführt wurde
                    //bei QR CodeScanner einkommentieren
                    //int qrCode = Integer.parseInt(data.getStringExtra("SCAN_RESULT"));
                    //String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                    //bei qr scanner auskommentieren
                    int qrCode = data.getIntExtra(STUB_SCANNER.RESULT, 0); //Übergabe des Intents (data), dort ist unter dem String RESULT der INT gespeichert... klingt unsinnig, läuft aber so. Die 0 ist Unsinn
                    if(qrCode>9 && qrCode<20){
                        for(Room r : MenueDrawer.game.getRooms()){
                            if(r.getName().equals(actualRoom)) { //hier böse
                                if(MenueDrawer.game.getPlayerManager().getPlayerList().get(MenueDrawer.whoAmI).getActualWeapon()!= null)
                                    r.addWeapon(MenueDrawer.game.getPlayerManager().getPlayerList().get(MenueDrawer.whoAmI).getActualWeapon());
                                room = r;
                            }
                        }
                        //MenueDrawer.game.getActivePlayer().getActualRoom().addWeapon(MenueDrawer.game.getActivePlayer().getActualWeapon());

                        String weapon;
                        weapon = MenueDrawer.game.getNameByNumber(qrCode);
                        for (Weapon w : MenueDrawer.game.getWeapons()){
                            if(w.getName().equals(weapon)) {
                                MenueDrawer.game.getActivePlayer().setActualWeapon(w);
                                stb.updateData("playerList", game.getPlayerManager().getPlayerList().get(MenueDrawer.whoAmI));
                                room.removeWeapon(w);
                            }
                        }
                        endTurn();

                    }else{
                        //TODO Fehlermeldung Toast? Popup?
                        startWeaponScan();
                    }

                }

        }
    }

    private void startWeaponScan(){
        //bei QR CodeScanner auskommentieren
        final Intent intent = new Intent(getActivity(), STUB_SCANNER.class); //Vorbereitung der neuen Activity, STUB SCANNER ist der "QR-Code Leser"
        //bei QR CodeScanner einkommentieren
        //final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
        //final Intent intent = new Intent(ACTION_SCAN);
        startActivityForResult(intent, VALUE); //Starten der Activity. Methodenaufruf "...ForResult" impliziert, das die Activity etwas zurück liefert
    }

    private void endTurn(){

       // MenueDrawer.game.setNextActivePlayer();
       // stb.updateData("playerManager", MenueDrawer.game.getPlayerManager());
       // stb.updateData("aktivePlayer", game.getPlayerManager().getAktivePlayer());
       // stb.updateData("roomList", game.getRoomManager().getRoomList());
        getActivity().getIntent().putExtra("myTurn", false);
        getActivity().getIntent().putExtra("GAME",MenueDrawer.game);
       // getActivity().finish();
       // startActivity(getActivity().getIntent());

        callback.stopTimer();
        callback.endTurn();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}