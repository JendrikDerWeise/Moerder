package com.example.jendrik.moerder.GUI.LittleHelpers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.R;

/**
 * Created by bulk on 31.07.2016.
 */
public class NotificationBuilder {
    private NotificationCompat.Builder mBuilder;
    private Context mContext;

    public NotificationBuilder(Context context){
        mBuilder = new NotificationCompat.Builder(context);
        mContext = context;
    }

    public void setBuilder(String title, String txt){
        mBuilder.setSmallIcon(R.drawable.pistol);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(txt);
        mBuilder.setVibrate(new long[]{500,500});
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        //Intent intent = new Intent(mContext, MenueDrawer.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_FROM_BACKGROUND);
        //PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0 /* Request code */, intent,
          //      PendingIntent.FLAG_ONE_SHOT);
        //mBuilder.setContentIntent(pendingIntent);
    }

    public NotificationCompat.Builder getBuilder(){
        return mBuilder;
    }
}
