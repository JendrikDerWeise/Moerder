package com.example.jendrik.moerder.QR;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jendrik.moerder.R;

/**
 * Created by bulk on 15.03.2016.
 */
public class STUB_QrStarten extends Fragment {
    private View fragLayoutV;
    private Bundle extras;

    private static final int VALUE = 503;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragLayoutV = inflater.inflate(R.layout.stub_activity, null);

        extras = getActivity().getIntent().getExtras();

        return fragLayoutV;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            final Intent intent = new Intent(getActivity(), com.example.jendrik.moerder.QR.QRHandler.class);
            startActivityForResult(intent, VALUE);
        } catch (ActivityNotFoundException anfe) {
            //alertDialog("Installiere den Zxing QR Scanner");
            //Toast toast = Toast.makeText(this, "Installiere den Zxing QR Scanner", Toast.LENGTH_LONG);
            //toast.show();
            //showDialog(AndroidBarcodeQrExample.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    /**public void scanQR() {

        try {
            //alertDialog("juhu");
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //alertDialog("Scheisse!");
            //showDialog(AndroidBarcodeQrExample.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }


    public void onClickGrosserRoterKnopf(View button)
    {
        //alertDialog("whoop! whoop!");
        //final Intent intent = new Intent(this, QRHandler.class);
        //intent.putExtra(RESULT, -1);
        //String bla = intent.getStringExtra(RESULT);
        //alertDialog("whoooo");
        //startActivityForResult(intent, "SCAN_RESULT");


    }

    /**void alertDialog(String str){
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


    }**/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (VALUE) : {
                if (resultCode == Activity.RESULT_OK) {
                    int qrCode = Integer.parseInt(data.getStringExtra("SCAN_RESULT"));
                    Log.d("QRCODE", " " + qrCode);
                }
                break;
            }
        }
    }

    /**public void onActivityResultateeee(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                //Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                //toast.show();
            }
        }
    }**/

}
