package com.codecanyon.percentage.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.MainActivity;
import com.codecanyon.percentage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    String versionString,usingAppVersion;
    boolean internet_connection() {
        //Check if connected to internet, output accordingly
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected()&& activeNetwork.isAvailable();
        return isConnected;
    }
    public void intialization(){
        if(!internet_connection()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openMainActivity();


                }
            }, 3000);
        }else {
            usingAppVersion = getString(R.string.version);
            firebaseDatabase = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
            mDatabase = firebaseDatabase.getReference("version");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    versionString = dataSnapshot.getValue().toString();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!newUpdateAvailable()) {
                                openMainActivity();
                            } else {
                                newerUpdate();
                            }

                        }
                    }, 3000);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    openMainActivity();
                }
            });
        }
    }
    private void newerUpdate(){
        final Dialog dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.update);
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCanceledOnTouchOutside(true);
        dialog2.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        dialog2.show();
        TextView title = dialog2.findViewById(R.id.title);
        TextView version = dialog2.findViewById(R.id.version);
        TextView noThanks = dialog2.findViewById(R.id.noThanks);
        TextView update = dialog2.findViewById(R.id.update);
        title.setText("Update "+getString(R.string.app_name));
        version.setText("You can know get a new update of this app and enjoy the new feature of the app." +
                      "\nThe newer app version of "+versionString+" is available");
        noThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                openMainActivity();
            }
        });
        dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog2.dismiss();
                openMainActivity();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.appLink)));
                startActivity(intent);
            }
        });
    }
    private boolean newUpdateAvailable(){
        if(versionString.equals(usingAppVersion)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences currencyPreference=getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences ui=getApplicationContext().getSharedPreferences( Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences purchase=getApplicationContext().getSharedPreferences( Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        Constants.PURCHASE_STATUS=purchase.getBoolean("PURCHASE",false);
        SharedPreferences appUniqueID=getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        if(!appUniqueID.getString(Constants.APP_UNIQUE_ID_STRING, "null").equals("null")){
            Constants.APP_UNIQUE_ID_VALUE= appUniqueID.getString(Constants.APP_UNIQUE_ID_STRING, null);
        }else{
            String androidId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
            appUniqueID.edit().putString(Constants.APP_UNIQUE_ID_STRING,androidId).apply();
            Constants.APP_UNIQUE_ID_VALUE=androidId;
        }
        Constants.LIGHT_THEME=ui.getBoolean(Constants.THEME,true);
        if(!Constants.LIGHT_THEME) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            intialization();
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            intialization();
        }
        if(!currencyPreference.getString(Constants.CURRENCY, "null").equals("null")){
            Constants.CURRENCY_VALUE= currencyPreference.getString(Constants.CURRENCY, null);
        }

    }
    private void openMainActivity(){
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}