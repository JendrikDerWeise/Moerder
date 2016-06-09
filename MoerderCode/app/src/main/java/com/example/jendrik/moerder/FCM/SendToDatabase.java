package com.example.jendrik.moerder.FCM;

import android.util.Log;
import android.widget.EditText;

import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameHandler;
import com.example.jendrik.moerder.GameObjekts.Solution;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jendrik on 06.06.2016.
 */
public class SendToDatabase<T> {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    private String gameName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public SendToDatabase(String gameName) {
        this.gameName = gameName;

    }


    public void sendData(String typOfObject, T object) {
        /*DatabaseReference myRef = database.getReference(typOfObject);

        myRef.setValue(object, new Firebase.CompletionListener(){
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.d("FBfehler","Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.d("FBfehler","Data saved successfully.");
                }
            }
        });*/

        DatabaseReference myRef = database.getReference();
        myRef.child("games").child(gameName).child(typOfObject).push();

        Map<String, Object>update = new HashMap<>();
        update.put("games/"+gameName+"/"+typOfObject, object);
        myRef.updateChildren(update);
    }

    public void createGame(){
        DatabaseReference myRef = database.getReference();
        myRef.child("games").child(gameName).push();
    }
}
