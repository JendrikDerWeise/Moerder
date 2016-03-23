package com.example.jendrik.moerder.QR;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jendrik.moerder.R; //HIER FEHLER

import java.util.Scanner;

/**
 * Created by Sophia on 21.03.2016.
 */
public class QRHandler extends Activity{

    public static int qrnr;
    public static String RESULT = "scan result";
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stub_qr); //HIER AUCH FEHLER

    }

    public void onClickScan(View button){
        qrnr = -1;
        try {
            //alertDialog("juhu");
            //Toast toast = Toast.makeText(this, "Juhu", Toast.LENGTH_LONG);
            //toast.show();

            Intent intent = new Intent(ACTION_SCAN);
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //alertDialog("Installiere den Zxing QR Scanner");
            // Toast toast = Toast.makeText(this, "Installiere den Zxing QR
           // Scanner ", Toast.LENGTH_LONG);
            //toast.show();
            //showDialog(AndroidBarcodeQrExample.this, "No Scanner Found",
            //"Download a scanner code activity?", "Yes", "No").show();
        }
    }

    /**@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    if (requestCode == 0) {
    if (resultCode == RESULT_OK) {
    this.intent = intent;
    qrnr = Integer.parseInt(intent.getStringExtra("SCAN_RESULT"));
    String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    //Hier evtl lieber Name des Gescannten Objects mit angeben.
    // alertDialog("QR-Nr." + qrnr + " " + format);
    Toast toast = Toast.makeText(this, "QR-Nr." + qrnr + " " +
    format, Toast.LENGTH_LONG);
    toast.show();
    }
    }
    }

    public Intent getIntent(){
    if(qrnr != -1){
    return intent;
    }
    return null;
    }**/

}