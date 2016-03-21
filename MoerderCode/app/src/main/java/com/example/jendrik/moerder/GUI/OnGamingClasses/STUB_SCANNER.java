package com.example.jendrik.moerder.GUI.OnGamingClasses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jendrik.moerder.R;

/**
 * Created by Jendrik on 21.03.2016.
 */
public class STUB_SCANNER extends Activity {
    public static String RESULT = "scan result";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stub_scanner);

    }

    public void onClickGescannt(View button){
        final EditText et_integer = (EditText) findViewById(R.id.et_stub_scanner_int);
        final String no = et_integer.getText().toString();
        final int integer = Integer.parseInt(no);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULT,integer);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }
}
