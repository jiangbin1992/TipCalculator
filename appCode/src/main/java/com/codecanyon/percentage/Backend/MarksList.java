package com.codecanyon.percentage.Backend;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MarksList {
    public static List<String> subjectList=new ArrayList<>();
    public static List<Double> marksObtainedList=new ArrayList<>();
    public static List<Integer> totalMarksList=new ArrayList<>();

    Context context;

    public MarksList(Context context){
        this.context=context;
    }

    public boolean addValue(String subjectName,double obtainedMarks,int totalMarks){
        if(subjectList.size()<14){
            subjectList.add(subjectName);
            marksObtainedList.add(obtainedMarks);
            totalMarksList.add(totalMarks);
            return true;
        }else{
            Toast.makeText(context,"Only 15 subjects are allowed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void removeValue(int position){
        subjectList.remove(position);
        marksObtainedList.remove(position);
        totalMarksList.remove(position);
    }
}
