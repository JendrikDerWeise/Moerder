package com.example.jendrik.moerder.GUI.LittleHelpers;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.jendrik.moerder.GUI.OnGamingClasses.MenueDrawer;
import com.example.jendrik.moerder.R;

import java.util.concurrent.TimeUnit;


public class CountDownClass{

    CounterClass timer;
    TextView textViewTime;
    Activity activity;
    long millisOnPause;
    long millis;

    /**
     * Verantwortlich fuer das herunterzaehlen einer Rundendauer
     * Klasse rechnet "echte" Minuten und Sekundenangaben intern in Millisekunden um, weil sonst nix geht
     *
     * @param activity this
     * @param min Anzahl Minuten
     * @param sec Anzahl Sekunden
     */
   public CountDownClass(Activity activity, int min, int sec){

        this.activity = activity;
        textViewTime = (TextView) activity.findViewById(R.id.timer);
        int count = (min*60000) + (sec*1000);
        timer = new CounterClass(count, 1000);
    }



    public class CounterClass extends CountDownTimer {


        boolean wasPaused = false;

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            millis = millisUntilFinished;
            String hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            textViewTime.setText(hms);
            wasPaused = false;
        }

        @Override
        public void onFinish() {
            activity.getIntent().putExtra("GAME", MenueDrawer.game);
            activity.finish();
            activity.startActivity(activity.getIntent());
        }


    }

    public CounterClass getTimer(){
        return timer;
    }

    public void pause(){
        millisOnPause = millis;
        timer.cancel();
    }

    public void resume(){
        timer = new CounterClass(millisOnPause,1000);
        timer.start();
    }
}
