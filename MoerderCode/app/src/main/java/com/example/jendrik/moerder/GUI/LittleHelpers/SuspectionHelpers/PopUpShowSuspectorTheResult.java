package com.example.jendrik.moerder.GUI.LittleHelpers.SuspectionHelpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 22.06.2016.
 */
public class PopUpShowSuspectorTheResult extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        String clueOwner = args.getString("clueOwner");
        String clue = args.getString("clue");

        builder.setTitle(clueOwner + " " + R.string.popup_suspection_result_to_suspector_title)
                .setMessage(clue);

        return builder.create();
    }
}
