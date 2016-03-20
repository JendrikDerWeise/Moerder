package com.example.jendrik.moerder.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 06.03.2016.
 */
public class popupGivenQrCodes extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.popup_given_qr_codes);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int) (height*0.6));
    }

    public void onClickOK(View button){
        finish();
    }
}
