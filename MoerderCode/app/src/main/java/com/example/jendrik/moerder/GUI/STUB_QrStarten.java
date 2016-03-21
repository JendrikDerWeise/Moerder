package com.example.jendrik.moerder.GUI;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jendrik.moerder.QRHandler;
import com.example.jendrik.moerder.R;

/**
 * Created by bulk on 15.03.2016.
 */
public class STUB_QrStarten extends Activity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stub_qr);

    }

    public void scanQR() {

        try {
            alertDialog("juhu");
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            alertDialog("Scheisse!");
            //showDialog(AndroidBarcodeQrExample.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }


    public void onClickGrosserRoterKnopf(View button)
    {
        //alertDialog("whoop! whoop!");
        Intent intent = new Intent(this, QRHandler.class);
        //startActivityForResult(intent, "SCAN_RESULT");
        //alertDialog(intent.getStringExtra("SCAN_RESULT"));

    }

    void alertDialog(String str){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(str);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}
