package com.codecanyon.percentage.Backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.codecanyon.percentage.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class percentageTsgList {
    public static List<Double>TAG=new ArrayList<>();
    Context context;
    SharedPreferences sharedPreferences;

    public percentageTsgList(Context context){
        this.context=context;

        sharedPreferences=context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if(!sharedPreferences.getString(Constants.PERCENT_TAG, "null").equals("null")){
            String jsonText = sharedPreferences.getString(Constants.PERCENT_TAG, null);
            double[] text = gson.fromJson(jsonText, double[].class);
            TAG.clear();
            for(int i=0;i<text.length;i++) {
                TAG.add(text[i]);
            }
        }else {
            TAG.add(18.0);
            TAG.add(25.0);
            TAG.add(50.0);
            TAG.add(75.0);
            Gson gson1 = new Gson();
            String jsonText = gson1.toJson(TAG);
            sharedPreferences.edit().putString(Constants.PERCENT_TAG, jsonText).apply();
        }
    }

    public boolean addList(double valueToAdd){
        if(TAG.contains(valueToAdd)){

            return false;
        }else if(TAG.size()>=6) {
            Toast.makeText(context,"Only 6 tags are allowed",Toast.LENGTH_SHORT).show();
            return  false;
        }else{
            TAG.add(valueToAdd);
            Gson gson1 = new Gson();
            String jsonText = gson1.toJson(TAG);
            sharedPreferences.edit().putString(Constants.PERCENT_TAG, jsonText).apply();
            return true;
        }
    }
    public void removeValue(double valueToRemove){
        if(TAG.contains(valueToRemove)){
            int i=TAG.indexOf(valueToRemove);
            TAG.remove(i);
            Gson gson1 = new Gson();
            String jsonText = gson1.toJson(TAG);
            sharedPreferences.edit().putString(Constants.PERCENT_TAG, jsonText).apply();
        }
    }
}
