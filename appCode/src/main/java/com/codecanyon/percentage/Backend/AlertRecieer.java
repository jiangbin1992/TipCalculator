package com.codecanyon.percentage.Backend;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.MainActivity;
import com.codecanyon.percentage.R;

public class AlertRecieer extends BroadcastReceiver {
    SharedPreferences purchase;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i=new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,i,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"PercentageCalculator")
                .setSmallIcon(R.drawable.ic_percent_icon)
                .setContentTitle("Percentage Calculator")
                .setAutoCancel(true)
                .setContentText("Your Ads Free hss expired")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
        purchase=context.getSharedPreferences( Constants.PACKAGE_NAME, Context.MODE_PRIVATE);

        purchase.edit().putBoolean("PURCHASE",false).apply();
        Constants.PURCHASE_STATUS=false;
    }
}
