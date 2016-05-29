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

package com.example.jendrik.moerder.FCM;

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


import com.example.jendrik.moerder.GUI.OnGamingClasses.ChangeRoom;
import com.example.jendrik.moerder.GUI.OnGamingClasses.LittleHelpers.Suspection;
import com.example.jendrik.moerder.Game;
import com.example.jendrik.moerder.GameHandler;
import com.example.jendrik.moerder.GameObjekts.Player;
import com.example.jendrik.moerder.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyFcmListenerService extends FirebaseMessagingService {

    private static final String TAG = "MyGcmListenerService";
    private static final String SENDER_ID = " ";

    @Override
    public void onMessageReceived(RemoteMessage message){
        String from = message.getFrom();
        Map data = message.getData();


        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }
        Game game;
        String keyword = (String) data.get("keyword"); //TODO keyword am anderen Ende einbauen
        switch(keyword){
            case "end": //TODO was passiert hier
                break;
            case"next":
                game = (Game) data.get("game");
                GameHandler.saveGame(game);
                //TODO hier menu drawer aufrufen
                break;
            case "player":
                game = GameHandler.loadGame();
                game.updatePlayer((Player)data.get("player"));
                GameHandler.saveGame(game);
                break;
            case "playerCall":
                int room = (Integer)data.get("roomQR");
                //TODO POPUP "begib dich in den raum room"
                break;
            case "prosecution":
                //TODO POPUP "begib dich in den Gruppenraum"
                break;
            case "dead":
                //TODO was passiert hier, gibt es das?
                break;
            case "suspection":
                String[] s = (String[]) data.get("suspection");
               /**
                 * [0] suspector
                 * [1] suspected player
                 * [2] suspected room
                 * [3] suspected weapon
                 * [4] cardholder / the player that showed a card
                 **/
                //TODO Popup mit den daten aus der Suspection (durch getter)
                break;
            case "pause":
                String playername = (String) data.get("pause");
                //TODO Popup mit name und hat pause gedrueckt
                break;
            case "update":
                game = (Game) data.get("game");
                GameHandler.saveGame(game);
                break;
            case "welcome":
                break;
            case "newerror":
                //TODO popup "name schon vegeben, ueberleg dir einen Neuen" /fuer spiel
                break;
            case "joinerror":
                //TODO popup "leider ist etwas schief gelaufen, waehle ein anderes spiel oder gib das richtige pw ein"
                break;
            case "sorry":
                break;


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
        //sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    public static void callPlayer(int qrnr, int roomnr, AtomicInteger msgId){
        /**Bundle data = new Bundle();
        data.putString("message", "playerCall");
        data.putInt("qrnr", qrnr);
        data.putInt("roomQR", roomnr);**/

        JSONObject json = new JSONObject();
        try{
            json.put("qrnr", qrnr);
            json.put("roomQR", roomnr);
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "playerCall");

        //TODO sinnvolle msgId uebergeben
    }

    public static void joinGame(String name){
        /**Bundle data = new Bundle();
        data.putString("message", "join");
        data.putString("gameName", name);**/
        JSONObject json = new JSONObject();
        try{
            json.put("gameName", name);
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "join");
    }

    public static void joinGame(String name, String password){
        /**Bundle data = new Bundle();
        data.putString("message", "join");
        data.putString("gameName", name);
        data.putString("password", password);**/
        JSONObject json = new JSONObject();
        try{
            json.put("gameName", name);
            json.put("password", password);
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "join");
    }

    public static void newGame(Game game){
        //TODO wo kriege ich das Game übergeben

        /**Bundle data = new Bundle();
        data.putString("message", "new");
        data.putString("gameName", game.getGameName());
        data.putSerializable("game", game);**/
        JSONObject json = new JSONObject();
        try{
            json.put("gameName", game.getGameName());
            json.put("game", game);
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "new");

        //TODO in den Wartebildschirm
    }

    public static void pause(int playerQR){
        /**Bundle data = new Bundle();
        data.putString("message", "pause");**/
        JSONObject json = new JSONObject();
        try{
            json.put("player", playerQR);
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "pause");
    }

    public static void prosecution(){
        /**Bundle data = new Bundle();
        data.putString("message", "prosecution");**/
        JSONObject json = new JSONObject();

        sendData(json, new AtomicInteger(123456789), "prosecution");
    }

    public void sendGame(){
        Game game = GameHandler.loadGame();

        if(game.getGameOver()){
            //sendString("end");
        }else{
            /**Bundle data = new Bundle();
            data.putString("message", "next");
            data.putSerializable("game", game);**/
            JSONObject json = new JSONObject();
            try{
                json.put("game", game);
            }catch(JSONException e){
                e.printStackTrace();
            }
            sendData(json, new AtomicInteger(123456789), "next");
        }

    }

    public void sendName(String string){
        /**Bundle data = new Bundle();
        data.putString("message", "name");
        data.putString("name", string);**/
        JSONObject json = new JSONObject();
        try{
            json.put("name", string);
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "name");
    }


    public void sendSuspection(Suspection suspection){
        Game game = GameHandler.loadGame();
        /**Bundle data = new Bundle();
        data.putString("message", "suspection");
        String suspector = game.getNameByNumber(suspection.getSuspector()+1); //Suspector Nummer = QRNR -1
        data.putStringArray("suspection", new String[]{suspector, suspection.getPlayer(), suspection.getRoom(), suspection.getWeapon(), suspection.getCardOwner()});**/
        JSONObject json = new JSONObject();
        try{
            String suspector = game.getNameByNumber(suspection.getSuspector()+1); //Suspector Nummer = QRNR -1
            json.put("suspection", new String[]{suspector, suspection.getPlayer(), suspection.getRoom(), suspection.getWeapon(), suspection.getCardOwner()});
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "suspection");
    }

    public void updatePlayer(Player player){
        Game game = GameHandler.loadGame();
        game.updatePlayer(player);
        GameHandler.saveGame(game);
        /**Bundle data = new Bundle();
        data.putString("message", "player");
        data.putSerializable("player", player);**/
        JSONObject json = new JSONObject();
        try{
            json.put("player", player);
        }catch(JSONException e){
            e.printStackTrace();
        }
        sendData(json, new AtomicInteger(123456789), "player");
    }

    private static void sendData(JSONObject json, AtomicInteger msgId, String code){
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(msgId.incrementAndGet()))
                .addData("message", code)
                .addData("data",json.toString())
                .build());
    }


    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        //TODO wie baue ich die jeweilige Activity ein?
        //ist so schon richtig. Nur eben mit der richtigen Klasse. Anschließend den Intent starten "startActivity(intent);"
        //Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
             //   PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
          //      .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
          //      .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
