package com.example.jendrik.moerder.GUI.LittleHelpers.ProsecutionHelpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.jendrik.moerder.GUI.OnGamingClasses.GameIsRunningCallback;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.06.2016.
 */
public class PopUpIndictPlayerBroadcast extends DialogFragment {
    private static final int VALUE = 42;
    GameIsRunningCallback mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.popup_indict_broadcast_message)
                .setTitle(R.string.popup_indict_broadcast_title)
                .setPositiveButton("Scan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.startScanForGrpRoom(PopUpIndictPlayerBroadcast.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (GameIsRunningCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement GameIsRunnungCallback");
        }
    }
}
