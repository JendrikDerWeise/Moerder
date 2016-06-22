package com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 22.06.2016.
 */
public class PopupSuspectionShowPlayersTheResult extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        String suspector = args.getString("suspector");
        String clueOwner = args.getString("clueOwner");
        String name = args.getString("name");
        String room = args.getString("room");
        String weapon = args.getString("weapon");

        builder.setTitle(suspector + " " + R.string.popup_suspection_result_broadcast_title)
                .setMessage(name + "/n" + room + "/n" + weapon +"/n/n" + clueOwner + " " + R.string.popup_suspection_result_broadcast_message);

        return builder.create();
    }
}