package com.codecanyon.percentage.Calculation;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codecanyon.percentage.Backend.MarkSaved;
import com.codecanyon.percentage.Backend.MarksList;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;
import com.codecanyon.percentage.Supporting.MarksAddAddapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Marks extends Fragment implements MarksAddAddapter.AdapterCallback {

    LinearLayout addSubject;
    Button calculate,save,reset;
    EditText subjectName,obtainedMarks,totalMarks;
    TextView marksObtainedAns,totalMarksAns,percentageAns;
    List<String> savedName=new ArrayList<>();
    RecyclerView recyclerView;
    MarksAddAddapter marksAddAddapter;
    Decimals decimals;
    SQLiteDatabase myDatabase;
    GridLayoutManager gridLayoutManager1;
    NestedScrollView nestedScrollView;
    boolean openSaved;
    List<String>obtainedList=new ArrayList<>();
    List<String>totalList=new ArrayList<>();
    List<String>subjectList=new ArrayList<>();
    MarksList marksList;

    public Marks(boolean openSaved,List<String>obtainedList,List<String>totalList,List<String>subjectList) {
        // Required empty public constructor
        this.openSaved=openSaved;
        if(openSaved) {
            this.obtainedList = obtainedList;
            this.totalList = totalList;
            this.subjectList = subjectList;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_marks, container, false);

        reset=view.findViewById(R.id.reset);
        addSubject=view.findViewById(R.id.add);
        calculate=view.findViewById(R.id.calculate);
        save=view.findViewById(R.id.save);
        subjectName=view.findViewById(R.id.subjectName);
        nestedScrollView =view.findViewById(R.id.scroller);
        obtainedMarks=view.findViewById(R.id.markObtained);
        totalMarks=view.findViewById(R.id.totalMark);
        marksObtainedAns=view.findViewById(R.id.markObtainedAns);
        totalMarksAns=view.findViewById(R.id.totalMarkAns);
        percentageAns=view.findViewById(R.id.percentage);
        recyclerView=view.findViewById(R.id.marks);
        decimals=new Decimals();

        marksList=new MarksList(getContext());

        if(getString(R.string.version).equals("v.0.1")){
            save.setVisibility(View.GONE);
        }
        resetActivity();
        if (openSaved) {
            reloadingData();
        }
        myDatabase= getActivity().openOrCreateDatabase("Marks",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS marksTable(subjectArray TEXT,markObtainedArray TEXT,totalMarksArray TEXT,fileName TEXT,id INTEGER PRIMARY KEY)");


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetActivity();
            }
        });
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(marksList.addValue("",0.0,0)){
                    updateList();
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculation();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!obtainedMarks.getText().toString().isEmpty()&&!totalMarks.getText().toString().isEmpty()) {
                    fileName();
                }else{
                    Toast.makeText(getActivity(),"enter the necessary value",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void resetActivity() {
        MarksList.subjectList.clear();
        MarksList.marksObtainedList.clear();
        MarksList.totalMarksList.clear();
        updateList();
        totalMarksAns.setText("");
        percentageAns.setText("");
        marksObtainedAns.setText("");
        subjectName.setText("");
        obtainedMarks.setText("");
        totalMarks.setText("");
    }

    private void reloadingData() {
        subjectName.setText(subjectList.get(0));
        obtainedMarks.setText(obtainedList.get(0));
        totalMarks.setText(totalList.get(0));
        for(int i=1;i<subjectList.size();i++){
            if(marksList.addValue(subjectList.get(i),Double.parseDouble(obtainedList.get(i)),(int)Double.parseDouble(totalList.get(i)))){
                updateList();
            }
        }
    }

    private void fileName(){
        final Dialog dialog2 = new Dialog(getContext());
        dialog2.setContentView(R.layout.file_name);
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCanceledOnTouchOutside(true);
        dialog2.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        dialog2.show();

        EditText fileNameEditText=dialog2.findViewById(R.id.fileName);
        Button save=dialog2.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileNameString= fileNameEditText.getText().toString();
                if(fileNameString.trim().isEmpty()){
                    Toast.makeText(getActivity(),"Add file Name",Toast.LENGTH_SHORT).show();
                }else{
                    int i=0;
                    String newFileName=fileNameString;
                    while(findSavedName(newFileName)){
                        i++;
                        newFileName=fileNameString+" ("+String.valueOf(i)+")";
                    }
                    dialog2.dismiss();
                    saveTheCal(newFileName);
                }
            }
        });

    }

    private boolean findSavedName(String fileName){
        ///return true if file exit
        for(MarkSaved markSaved:Constants.MARKS_SAVED_LIST){
            if(markSaved.getFileName().equals(fileName)){
                return true;
            }
        }
        return false;
    }

    private void saveTheCal(String fileName)  {
        // Create a new user with a first, middle, and last name
        String subjectArrayName;
        if(subjectName.getText().toString().isEmpty()){
            subjectArrayName="Subject"+Constants.DIVIDER;
        }else{
            subjectArrayName=subjectName.getText().toString()+Constants.DIVIDER;
        }

        for(String subject:MarksList.subjectList){
            if(subject.isEmpty()){
                subjectArrayName += "subject"+Constants.DIVIDER;
            }else {
                subjectArrayName += subject+Constants.DIVIDER;
            }

        }
        String marksObtainedArray;
        if(obtainedMarks.getText().toString().isEmpty()) {
            marksObtainedArray = "0" + Constants.DIVIDER;
        }else {
            marksObtainedArray = obtainedMarks.getText().toString() + Constants.DIVIDER;
        }
        for(double obtained:MarksList.marksObtainedList){
            marksObtainedArray += String.valueOf(obtained)+Constants.DIVIDER;
        }
        String totalArray=totalMarks.getText().toString()+Constants.DIVIDER;
        for(int total:MarksList.totalMarksList) {
            totalArray += String.valueOf(total) + Constants.DIVIDER;
        }
        myDatabase.execSQL("INSERT INTO marksTable(subjectArray,markObtainedArray,totalMarksArray,fileName) VALUES ('" + subjectArrayName + "','" + marksObtainedArray + "','" + totalArray + "','"+fileName+ "')");
        MarkSaved markSaved=new MarkSaved(fileName,marksObtainedArray,totalArray,subjectArrayName,1);
        Constants.MARKS_SAVED_LIST.add(markSaved);
    }


    private void calculation() {

        if(!obtainedMarks.getText().toString().isEmpty()&&!totalMarks.getText().toString().isEmpty()){
            double totalObtainedMarks=0.0,percentage;
            int totalTotalMarks=0;
            nestedScrollView.smoothScrollBy(0,1800);
            for(int i:MarksList.totalMarksList ){
                totalTotalMarks+=i;
            }
            for(double i:MarksList.marksObtainedList){
                totalObtainedMarks+=i;
            }
            totalObtainedMarks+= Double.parseDouble(obtainedMarks.getText().toString());
            totalTotalMarks+=Integer.parseInt(totalMarks.getText().toString());
            percentage=totalObtainedMarks/totalTotalMarks*100;

            marksObtainedAns.setText((totalObtainedMarks)+"");
            totalMarksAns.setText(totalTotalMarks+"");
            percentageAns.setText(decimals.roundOfTo(percentage)+" %");
        }

    }

    private void updateList() {
        marksAddAddapter=new MarksAddAddapter(getContext(),MarksList.subjectList ,this);
        gridLayoutManager1=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager1);
        recyclerView.setAdapter(marksAddAddapter);
    }

    @Override
    public void onMethodCallback() {
        updateList ();
    }
}