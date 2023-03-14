package com.codecanyon.percentage.Backend;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpgaList {
    public static List<String> SUBJECT_NAME=new ArrayList<>();
    public static List<Integer> CREDIT=new ArrayList<>();
    public static List<Double> OBTAINED_MARKS=new ArrayList<>();
    public static List<Double> TOTAL_MARKS=new ArrayList<>();
    Context context;

    public SpgaList(Context context){
        this.context=context;
    }


    public boolean addValue(String itemName,int credit,double obtained, double totalMarks){
        if(SUBJECT_NAME.size()<14){
            SUBJECT_NAME.add(itemName);
            CREDIT.add(credit);
            OBTAINED_MARKS.add(obtained);
            TOTAL_MARKS.add(totalMarks);
            return true;
        }else{
            Toast.makeText(context,"Only 15 Semesters are allowed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void removeValue(int position){
        if (SUBJECT_NAME.size()>position){
            SUBJECT_NAME.remove(position);
            CREDIT.remove(position);
            OBTAINED_MARKS.remove(position);
            TOTAL_MARKS.remove(position);
        }
    }
}
