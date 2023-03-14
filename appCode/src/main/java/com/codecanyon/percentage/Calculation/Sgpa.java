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

import com.codecanyon.percentage.Backend.DiscountSaved;
import com.codecanyon.percentage.Backend.SgpaSaved;
import com.codecanyon.percentage.Backend.SpgaList;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;
import com.codecanyon.percentage.Supporting.SgpaAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Sgpa extends Fragment implements SgpaAdapter.AdapterCallback {
    EditText subjectName,credit,obtainedValue,totalMarks;
    RecyclerView recyclerView;
    LinearLayout add,totalLayout;
    TextView tenToggle,fiveToggle,universityToggle,standardToggle,totalCredits,grandTotal,sgpa,percentage;
    Button reset,calculate,save;
    boolean totalToggle=true,standardsToggle=true;//toggle for ten
    SgpaAdapter cgpaAdapter;
    SpgaList cList;
    NestedScrollView nestedScrollView;
    GridLayoutManager gridLayoutManager1;
    SQLiteDatabase myDatabase;
    boolean openingSaved,calculationRule,sgpaOutOf;
    List<String>subjectList=new ArrayList<>();
    List<String>creditList=new ArrayList<>();
    List<String>obtainedList=new ArrayList<>();
    List<String>totalList=new ArrayList<>();

    public Sgpa(boolean openingSaved, List<String>subjectList,List<String>creditList,List<String>obtainedList,List<String> totalList,boolean calculationRule, boolean sgapOutOf) {
        // Required empty public constructor
        this.openingSaved = openingSaved;
        if(openingSaved) {
            this.subjectList = subjectList;
            this.creditList = creditList;
            this.obtainedList = obtainedList;
            this.totalList = totalList;
            this.calculationRule=calculationRule;
            this.sgpaOutOf=sgapOutOf;

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
                    saveTheCal(newFileName);
                    dialog2.dismiss();
                }
            }
        });

    }

    private boolean findSavedName(String fileName){
        ///return true if file exit
        for(DiscountSaved discount: Constants.DISCOUNT_SAVED_LIST){
            if(discount.getFileName().equals(fileName)){
                return true;
            }
        }
        return false;
    }

    private void saveTheCal(String fileName) {
        // Create a new user with a first, middle, and last name

        String subjectArray;
        if(subjectName.getText().toString().isEmpty()){
            subjectArray="Subject"+Constants.DIVIDER;
        }else{
            subjectArray=subjectName.getText().toString()+Constants.DIVIDER;
        }

        for(String item: SpgaList.SUBJECT_NAME){
            if(item.isEmpty()) {
                subjectArray += "Subject" + Constants.DIVIDER;
            }else{
                subjectArray += item + Constants.DIVIDER;
            }
        }
        String obtainedArray=obtainedValue.getText().toString()+Constants.DIVIDER;
        for(double actual:SpgaList.OBTAINED_MARKS){
            obtainedArray += String.valueOf(actual)+Constants.DIVIDER;
        }
        String creditArray=credit.getText().toString()+Constants.DIVIDER;
        for(double cr:SpgaList.CREDIT) {
            creditArray += String.valueOf(cr) + Constants.DIVIDER;
        }
        String totalArray=totalMarks.getText().toString()+Constants.DIVIDER;
        for(double total:SpgaList.TOTAL_MARKS) {
            totalArray += String.valueOf(total) + Constants.DIVIDER;
        }
        int calculationRule=standardsToggle?1:0;
        int sgpaOutOf=totalToggle?1:0;

        myDatabase.execSQL("INSERT INTO sgpaTable(subjectArray,obtainedMarksArray,totalMarksArray,credit,calculationRule,sgpaOutOf,fileName) VALUES ('"
                + subjectArray + "','" + obtainedArray + "','" + totalArray +"','" + creditArray+
                "','" + calculationRule+ "','" + sgpaOutOf +"','" +fileName+ "')");

        SgpaSaved saved=new SgpaSaved(fileName,obtainedArray,totalArray,creditArray,subjectArray,standardsToggle,totalToggle,1);
        Constants.SGPA_SAVED_LIST.add(saved);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sgpa, container, false);
        subjectName=view.findViewById(R.id.subjectName);
        credit=view.findViewById(R.id.credit);
        totalLayout=view.findViewById(R.id.totalLayout);
        obtainedValue=view.findViewById(R.id.obtainedValue);
        totalMarks=view.findViewById(R.id.totalMarks);
        recyclerView=view.findViewById(R.id.recycler);
        add=view.findViewById(R.id.add);
        fiveToggle=view.findViewById(R.id.five);
        tenToggle=view.findViewById(R.id.ten);
        standardToggle=view.findViewById(R.id.general);
        universityToggle=view.findViewById(R.id.mumbaiUniversity);
        reset=view.findViewById(R.id.reset);
        calculate=view.findViewById(R.id.calculate);
        totalCredits=view.findViewById(R.id.totalCredit);
        grandTotal=view.findViewById(R.id.grandTotal);
        sgpa=view.findViewById(R.id.sgpa);
        percentage=view.findViewById(R.id.percentage);
        save=view.findViewById(R.id.save);
        nestedScrollView=view.findViewById(R.id.scroller);
        cList=new SpgaList(getContext());
        if(getString(R.string.version).equals("v.0.1")){
            save.setVisibility(View.GONE);
        }
        resetFun();
        if(openingSaved){
            reloadData();
        }

        myDatabase= getActivity().openOrCreateDatabase("SGPA",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS sgpaTable(subjectArray TEXT,obtainedMarksArray TEXT,totalMarksArray TEXT,credit TEXT,calculationRule INTEGER,sgpaOutOf INTEGER,fileName TEXT,id INTEGER PRIMARY KEY)");


        universityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                standardsToggle=false;
                universityToggle.setTextColor(Color.parseColor("#ffffff"));
                standardToggle.setTextColor(Color.parseColor(("#000000")));
                universityToggle.setBackgroundResource(R.drawable.toggle_on);
                standardToggle.setBackground(null);
                totalLayout.setVisibility(View.GONE);
            }
        });

        standardToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                standardsToggle=true;
                standardToggle.setTextColor(Color.parseColor("#ffffff"));
                universityToggle.setTextColor(Color.parseColor(("#000000")));
                standardToggle.setBackgroundResource(R.drawable.toggle_on);
                universityToggle.setBackground(null);
                totalLayout.setVisibility(View.VISIBLE);
            }
        });
        tenToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalToggle=true;
                tenToggle.setTextColor(Color.parseColor("#ffffff"));
                fiveToggle.setTextColor(Color.parseColor(("#000000")));
                tenToggle.setBackgroundResource(R.drawable.toggle_on);
                fiveToggle.setBackground(null);
            }
        });

        fiveToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalToggle=false;
                fiveToggle.setTextColor(Color.parseColor("#ffffff"));
                tenToggle.setTextColor(Color.parseColor(("#000000")));
                fiveToggle.setBackgroundResource(R.drawable.toggle_on);
                tenToggle.setBackground(null);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFun();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cList.addValue("Subject "+(SpgaList.SUBJECT_NAME.size()+2),1,0.0,0.0)){
                    updateList();
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(credit.getText().toString().isEmpty()||totalMarks.getText().toString().isEmpty()||obtainedValue.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Enter the Required Values ",Toast.LENGTH_SHORT).show();
                }else {
                    nestedScrollView.smoothScrollBy(0,1800);
                    double obtainedDouble=Double.parseDouble(obtainedValue.getText().toString());
                    double totalMarksDouble=Double.parseDouble(totalMarks.getText().toString());
                    int creditInt=Integer.parseInt(credit.getText().toString());

                    if(standardsToggle){
                        calculateStandardGPA(obtainedDouble,totalMarksDouble,creditInt);
                    }else{
                        calculateUniversityGPA(obtainedDouble,totalMarksDouble,creditInt);
                    }


                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(credit.getText().toString().isEmpty()||totalMarks.getText().toString().isEmpty()||obtainedValue.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Enter the Required Values ",Toast.LENGTH_SHORT).show();
                }else {
                    fileName();
                }
            }
        });

        return view;
    }

    private void resetFun() {
        subjectName.setText("");
        credit.setText("1");
        obtainedValue.setText("");
        totalMarks.setText("");
        SpgaList.SUBJECT_NAME.clear();
        SpgaList.CREDIT.clear();
        SpgaList.OBTAINED_MARKS.clear();
        SpgaList.TOTAL_MARKS.clear();
        grandTotal.setText("");
        sgpa.setText("");
        percentage.setText("");
        totalCredits.setText("");
        updateList();
    }

    private void calculateUniversityGPA(double obtainedDouble, double totalMarksDouble, int creditInt) {
            Decimals decimals=new Decimals();

            double marksRatio=obtainedDouble/totalMarksDouble*100;
            int totalCredit=creditInt;
            double obtainedTotal=obtainedDouble;
            double totalMarksTotal=totalMarksDouble;
            double egp=gpvalue(marksRatio)*creditInt;

            for(int i=0;i<SpgaList.SUBJECT_NAME.size();i++){
                totalCredit+=SpgaList.CREDIT.get(i);
                obtainedTotal+=SpgaList.OBTAINED_MARKS.get(i);
                totalMarksTotal+=SpgaList.TOTAL_MARKS.get(i);
                egp+=gpvalue(SpgaList.OBTAINED_MARKS.get(i)/SpgaList.TOTAL_MARKS.get(i)*100)*SpgaList.CREDIT.get(i);
            }
            grandTotal.setText(obtainedTotal+" / "+totalMarksTotal);
            totalCredits.setText(totalCredit+"");
            double sgpaDouble=egp/totalCredit;
            sgpa.setText(String.valueOf(decimals.roundOfTo(sgpaDouble))+"");
            percentage.setText(String.valueOf(decimals.roundOfTo(obtainedTotal/totalMarksTotal*100))+ " %");


    }

    private int gpvalue(double marksRatio) {
        int gp=0;
        if(marksRatio>=80){
            //10
            gp=10;
        }else if(marksRatio>=75&&marksRatio<79) {
            //9
            gp=9;
        }else if(marksRatio>=70&&marksRatio<74){
            //8
            gp=8;
        }else if(marksRatio>=65&&marksRatio<69){
            //7
            gp=7;
        }else if(marksRatio>=60&&marksRatio<64){
            //6
            gp=6;
        }else if(marksRatio>=55&&marksRatio<59){
            //5
            gp=5;
        }else if(marksRatio>=50&&marksRatio<54){
            //4
            gp=4;
        }else if(marksRatio>=45&&marksRatio<50){
            //3
            gp=3;
        }else if(marksRatio>=40&&marksRatio<45){
            //2
            gp=2;
        }else if(marksRatio>=35&&marksRatio<39){
            //1
            gp=1;
        }else if(marksRatio<30){
            //0
            gp=0;
        }
        return gp;
    }

    private int generalGpValue(double marksRatio) {
        int gp = 0;
        if (marksRatio >= 90) {
            //10
            gp = 10;
        } else if (marksRatio >= 80 && marksRatio <89) {
            //9
            gp = 9;
        } else if (marksRatio >= 70 && marksRatio < 79) {
            //8
            gp = 8;
        } else if (marksRatio >= 60 && marksRatio < 69) {
            //7
            gp = 7;
        } else if (marksRatio >= 50 && marksRatio < 59) {
            //6
            gp = 6;
        } else if (marksRatio >= 40 && marksRatio < 49) {
            //5
            gp = 5;
        } else if ( marksRatio < 39) {
            //0
            gp = 0;
        }
        if(totalToggle) {

            return gp;
        }else {
            return (int)gp/2;
        }
    }

    private void calculateStandardGPA(double obtainedDouble, double totalMarksDouble, int creditInt) {
        Decimals decimals=new Decimals();

        double marksRatio=obtainedDouble/totalMarksDouble;
        int totalCredit=creditInt;
        double obtainedTotal=obtainedDouble;
        double totalMarksTotal=totalMarksDouble;
        double egp=generalGpValue(marksRatio*100)*creditInt;

        for(int i=0;i<SpgaList.SUBJECT_NAME.size();i++){
            totalCredit+=SpgaList.CREDIT.get(i);
            obtainedTotal+=SpgaList.OBTAINED_MARKS.get(i);
            totalMarksTotal+=SpgaList.TOTAL_MARKS.get(i);
            egp+=generalGpValue(SpgaList.OBTAINED_MARKS.get(i)*100/SpgaList.TOTAL_MARKS.get(i))*SpgaList.CREDIT.get(i);
        }
        grandTotal.setText(obtainedTotal+" / "+totalMarksTotal);
        totalCredits.setText(totalCredit+"");
        double sgpaDouble=egp/totalCredit;
        if(totalToggle) {
            sgpa.setText(String.valueOf(decimals.roundOfTo(sgpaDouble)) + " / 10");

        }else{
            sgpa.setText(String.valueOf(decimals.roundOfTo(sgpaDouble)) + " / 5");

        }
        percentage.setText(String.valueOf(decimals.roundOfTo(obtainedTotal*100/ totalMarksTotal))+ " %");

    }

    private void updateList(){
        cgpaAdapter =new SgpaAdapter(getContext(), SpgaList.SUBJECT_NAME,this);
        gridLayoutManager1=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager1);
        recyclerView.setAdapter(cgpaAdapter);
    }

    private void reloadData(){
        subjectName.setText(subjectList.get(0));
        obtainedValue.setText(obtainedList.get(0));
        totalMarks.setText(totalList.get(0));
        credit.setText(creditList.get(0));
        standardsToggle=calculationRule;
        totalToggle=sgpaOutOf;
        if(calculationRule){
            standardToggle.setTextColor(Color.parseColor("#ffffff"));
            universityToggle.setTextColor(Color.parseColor(("#000000")));
            standardToggle.setBackgroundResource(R.drawable.toggle_on);
            universityToggle.setBackground(null);
            totalLayout.setVisibility(View.VISIBLE);
        }else{
            universityToggle.setTextColor(Color.parseColor("#ffffff"));
            standardToggle.setTextColor(Color.parseColor(("#000000")));
            universityToggle.setBackgroundResource(R.drawable.toggle_on);
            standardToggle.setBackground(null);
            totalLayout.setVisibility(View.GONE);
        }
        if(sgpaOutOf){
            tenToggle.setTextColor(Color.parseColor("#ffffff"));
            fiveToggle.setTextColor(Color.parseColor(("#000000")));
            tenToggle.setBackgroundResource(R.drawable.toggle_on);
            fiveToggle.setBackground(null);
        }else{
            fiveToggle.setTextColor(Color.parseColor("#ffffff"));
            tenToggle.setTextColor(Color.parseColor(("#000000")));
            fiveToggle.setBackgroundResource(R.drawable.toggle_on);
            tenToggle.setBackground(null);
        }
        for(int i=1;i<subjectList.size();i++){
            if(cList.addValue(subjectList.get(i),(int)Double.parseDouble(creditList.get(i)),Double.parseDouble(obtainedList.get(i)),Double.parseDouble(totalList.get(i)))){
                updateList();
            }
        }
    }



    @Override
    public void onMethodCallback() {
        updateList();
    }
}