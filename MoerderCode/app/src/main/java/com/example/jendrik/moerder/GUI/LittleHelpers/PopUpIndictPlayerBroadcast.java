package com.example.jendrik.moerder.GUI.LittleHelpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.jendrik.moerder.FCM.SendToDatabase;
import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.GUI.OnGamingClasses.STUB_SCANNER;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.06.2016.
 */
public class PopUpIndictPlayerBroadcast extends DialogFragment {
    private static final int VALUE = 503;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.popup_indict_broadcast_message)
                .setTitle(R.string.popup_indict_broadcast_title)
                .setPositiveButton("Scan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startRoomScan();
                }
        });

        return builder.create();
    }

    private void startRoomScan(){
        final Intent intent = new Intent(getActivity(), STUB_SCANNER.class);
        startActivityForResult(intent, VALUE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            int qrCode = data.getIntExtra(STUB_SCANNER.RESULT, 0);
            if(qrCode==29) {
                MenueDrawer.game.getActivePlayer().setActualRoom(MenueDrawer.game.getGrpRoom());
                toDatabase();
            }else
                startRoomScan();
        }else
            startRoomScan();
    }

    private void toDatabase(){
        String pNumberString = "" + MenueDrawer.whoAmI;
        SendToDatabase stb = new SendToDatabase(MenueDrawer.game.getGameName(),pNumberString);
        stb.updateData("playerList", MenueDrawer.game.getPlayerManager().getPlayerList().get(MenueDrawer.whoAmI));
    }
}
