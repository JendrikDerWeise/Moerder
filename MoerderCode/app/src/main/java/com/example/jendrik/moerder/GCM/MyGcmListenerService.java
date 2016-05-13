/**
 * Copyright 2016 Moerder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jendrik.moerder.GCM;

/**
 * Created by Sophia on 10.05.2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.Suspection;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameHandler;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private static final String SENDER_ID = " ";
    private static final GoogleCloudMessaging gcm = new GoogleCloudMessaging();

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        if(message.equals("end")){
            //TODO was passiert hier
        }

        else if(message.equals("next")){
            Game game = (Game) data.get("game"); //TODO irgendwie unschoen
            GameHandler.saveGame(game);
            //TODO Spielzug machen Screen oeffnen
            //TODO erst nach 'fertiger' runde hierher zurueck
            game = GameHandler.loadGame();

            if(game.getGameOver()){
                sendString("end");
            }else{
                sendGame(game);
            }
        }

        else if(message.equals("player")){
            Game game = GameHandler.loadGame();
            game.updatePlayer((Player)data.get("player"));
            GameHandler.saveGame(game);
        }

        else if(message.equals( "playerCall")){
            int room = data.getInt("playerCall");
            //TODO POPUP "begib dich in den raum room"
        }

        else if(message.equals("prosecution")){
            //TODO POPUP "begib dich in den Gruppenraum"
        }

        else if(message.equals("dead")){
            //TODO was passiert hier eigentlich
        }

        else if(message.equals("suspection")){
            String[] s = data.getStringArray("suspection");
            /**
             * [0] suspector
             * [1] suspected player
             * [2] suspected room
             * [3] suspected weapon
             * [4] cardholder / the player that showed a card
             */
            //TODO Popup mit den daten aus der Suspection (durch getter)
        }

        else if(message.equals("pause")){
            String playername = data.getString("pause");
            //TODO Popup mit name und hat pause gedrueckt
        }

        else if(message.equals("update")){
            Game game = (Game) data.get("game");
            GameHandler.saveGame(game);
        }

        else if(message.equals("welcome")){
            //TODO
        }

        else if(message.equals("newerror")){
            //TODO popup "der Name ist schon vergeben"
        }

        else if(message.equals("joinerror")){
            //TODO popup "etwas ist schief gelaufen, evtl stimmt das passwort nicht"
        }

        else if(message.equals("sorry")){
            //TODO
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    public static void callPlayer(int qrnr){
        Bundle data = new Bundle();
        data.putString("message", "playerCall");
        data.putInt("qrnr", qrnr);
        data.putInt("room", 17);//TODO wo bekomme ich die Raumnummer her?
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void joinGame(String name){
        Bundle data = new Bundle();
        data.putString("message", "join");
        data.putString("gameName", name);
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void joinGame(String name, String password){
        Bundle data = new Bundle();
        data.putString("message", "join");
        data.putString("gameName", name);
        data.putString("password", password);
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newGame(Game game){
        //TODO wo kriege ich das Game übergeben

        Bundle data = new Bundle();
        data.putString("message", "new");
        data.putString("gameName", game.getGameName());
        data.putSerializable("game", game);
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO in den Wartebildschirm
    }

    public static void pause(){
        Bundle data = new Bundle();
        data.putString("message", "pause");
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prosecution(){
        Bundle data = new Bundle();
        data.putString("message", "prosecution");
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGame(Game game){
        Bundle data = new Bundle();
        data.putString("message", "next");
        data.putSerializable("game", game);
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendName(String string){
        Bundle data = new Bundle();
        data.putString("message", "name");
        data.putString("name", string);
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendSuspection(Suspection suspection){
        Game game = GameHandler.loadGame();
        Bundle data = new Bundle();
        data.putString("message", "suspection");
        String suspector = game.getNameByNumber(suspection.getSuspector()); //TODO auf jendriks antwort warten
        data.putStringArray("suspection", new String[]{suspector, suspection.getPlayer(), suspection.getRoom(), suspection.getWeapon(), suspection.getCardOwner()});
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayer(Player player){
        Game game = GameHandler.loadGame();
        game.updatePlayer(player);
        GameHandler.saveGame(game);
        Bundle data = new Bundle();
        data.putString("message", "player");
        data.putSerializable("player", player);
        String id = "1"; //TODO sinnvoll
        try {
            gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        //TODO wie baue ich die jeweilige Activity ein?
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
