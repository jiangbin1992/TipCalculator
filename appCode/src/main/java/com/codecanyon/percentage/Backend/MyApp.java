package com.codecanyon.percentage.Backend;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.codecanyon.percentage.Constants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MyApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        //offline activation
        FirebaseDatabase.getInstance(Constants.FIREBASE_LINK).setPersistenceEnabled(true);
    }
}

