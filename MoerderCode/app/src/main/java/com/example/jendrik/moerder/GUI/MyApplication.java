package com.example.jendrik.moerder.GUI;

import android.content.Intent;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Jendrik on 03.06.2016.
 */
public class MyApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //ReplaceFont.replaceDefaultFont(this, "DEFAULT", "fonts/wcRoughTrad.ttf");
        Firebase.setAndroidContext(this.getApplicationContext());

    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
