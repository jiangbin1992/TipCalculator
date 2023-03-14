package com.codecanyon.percentage.Backend;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.codecanyon.percentage.Constants;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

public class RewardAds extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

    }



    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                SharedPreferences purchased=getApplicationContext().getSharedPreferences( Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
                purchased.edit().putBoolean("PURCHASE",false);
                Constants.PURCHASE_STATUS=false;
                stoptimertask();
                Log.e("mjjm","gggg");
            }
        };
        timer.schedule(timerTask, 10000, 10000); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            stopSelf();
        }
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
