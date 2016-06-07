package com.example.jendrik.moerder.FCM;

import android.widget.EditText;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameHandler;
import com.example.jendrik.moerder.GameObjekts.Solution;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jendrik on 06.06.2016.
 */
public class SendToDatabase<T> {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    public SendToDatabase(){


    }



    public void sendData(String typOfObject, T object){
        DatabaseReference myRef = database.getReference("games/tolles_spiel/" + typOfObject);

        myRef.setValue(object);



    }



}