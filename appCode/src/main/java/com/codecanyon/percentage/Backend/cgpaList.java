package com.codecanyon.percentage.Backend;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class cgpaList {
    public static List<String> SEMESTER_NAME=new ArrayList<>();
    public static List<Double> SGPA=new ArrayList<>();
    Context context;

    public cgpaList(Context context){
        this.context=context;
    }


    public boolean addValue(String itemName,double spga){
        if(SEMESTER_NAME.size()<14){
            SEMESTER_NAME.add(itemName);
            SGPA.add(spga);
            return true;
        }else{
            Toast.makeText(context,"Only 15 Semesters are allowed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void removeValue(int position){
        if (SEMESTER_NAME.size()>position){
           SEMESTER_NAME.remove(position);
           SGPA.remove(position);
        }
    }
}
