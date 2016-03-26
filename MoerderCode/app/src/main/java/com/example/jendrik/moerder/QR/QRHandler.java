package com.example.jendrik.moerder.QR;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.R;

/**
 * Created by Sophia on 21.03.2016.
 */
public class QRHandler extends Activity{

    public static int qrnr;
    public static String RESULT = "scan result";
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Game game; //TODO wie zuer hoelle kriege ich jetzt das game objekt o.O
    private Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stub_qr); //HIER AUCH FEHLER
        extras = getIntent().getExtras();
        game = (Game) extras.get("GAME");

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                qrnr = Integer.parseInt(intent.getStringExtra("SCAN_RESULT"));
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                //TODO von dem aufgerufenen Fenster qr nummer und name des objekts als popup
                String name = game.getNameByNumber(qrnr);
                if(name != "error"){
                    Toast toast = Toast.makeText(this, "QR " + qrnr + " " + name, Toast.LENGTH_LONG);
                    game.setJustScannedQR(qrnr);
                }else{
                    //TODO fehlerbehandlung bzw erneut scannen, wenn nummer falsch
                }
                Log.d("QR-Nr." + qrnr , " " + format);
            }
        }
    }


}