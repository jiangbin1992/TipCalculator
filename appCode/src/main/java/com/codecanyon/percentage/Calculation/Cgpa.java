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

import com.codecanyon.percentage.Backend.CgpaSaved;
import com.codecanyon.percentage.Backend.DiscountSaved;
import com.codecanyon.percentage.Backend.cgpaList;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.CgpaAdapter;
import com.codecanyon.percentage.Supporting.Decimals;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Cgpa extends Fragment implements CgpaAdapter.AdapterCallback {
    RecyclerView recyclerView;
    EditText semester1,spga;
    TextView cgpa,percentage,ten,five;
    Button reset,save,calculate;
    LinearLayout add;
    boolean outOfToogle=true;//for 10
    CgpaAdapter cgpaAdapter;
    cgpaList cList;
    GridLayoutManager gridLayoutManager1;
    SQLiteDatabase myDatabase;
    boolean openingSaved,cgpaOutOf;
    List<String>semesterName=new ArrayList<>();
    List<String>sgpalist=new ArrayList<>();

    public Cgpa(boolean openingSaved, List<String> semesterName,List<String> sgpalist, boolean cgpaOutOf) {
        // Required empty public constructor
        this.openingSaved=openingSaved;
        if(openingSaved) {
            this.semesterName = semesterName;
            this.sgpalist = sgpalist;
            this.cgpaOutOf = cgpaOutOf;
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
        String semesterArray;
        if(semester1.getText().toString().isEmpty()){
            semesterArray="semester1"+Constants.DIVIDER;
        }else{
            semesterArray=semester1.getText().toString()+Constants.DIVIDER;
        }

        for(String item:cgpaList.SEMESTER_NAME){

            if(item.isEmpty()){
                semesterArray += "semester"+Constants.DIVIDER;
            }else{
                semesterArray += item+Constants.DIVIDER;
            }
        }
        String sgpaArray=spga.getText().toString()+Constants.DIVIDER;
        for(double actual:cgpaList.SGPA){
            sgpaArray += String.valueOf(actual)+Constants.DIVIDER;
        }


        int cgpaOutOf=outOfToogle?1:0;

        myDatabase.execSQL("INSERT INTO cgpaTable(semesterNameArray ,sgpa ,fileName ,cgpaOutOf ) VALUES ('"
                + semesterArray + "','" + sgpaArray + "','" + fileName +
                "','" + cgpaOutOf+  "')");

        CgpaSaved saved=new CgpaSaved(fileName,semesterArray,sgpaArray,outOfToogle,1);
        Constants.CGPA_SAVED_LIST.add(saved);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cgpa, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        semester1=view.findViewById(R.id.semester1);
        spga=view.findViewById(R.id.spga);
        cgpa=view.findViewById(R.id.cgpa);
        percentage=view.findViewById(R.id.percentage);
        ten=view.findViewById(R.id.ten);
        five=view.findViewById(R.id.five);
        reset=view.findViewById(R.id.reset);
        calculate=view.findViewById(R.id.calculate);
        save=view.findViewById(R.id.save);
        add=view.findViewById(R.id.add);
        cList=new cgpaList(getContext());
        NestedScrollView scrollView=view.findViewById(R.id.scroller);
        if(getString(R.string.version).equals("v.0.1")){
            save.setVisibility(View.GONE);
        }
        resetActivity();
        if(openingSaved){
            reloadingData();
        }
        myDatabase= getActivity().openOrCreateDatabase("CGPA",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS cgpaTable(semesterNameArray TEXT,sgpa TEXT,fileName TEXT,cgpaOutOf INTEGER,id INTEGER PRIMARY KEY)");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!spga.getText().toString().isEmpty()){
                    fileName();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetActivity();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cList.addValue("Semester "+(cgpaList.SGPA.size()+2),0)){
                    updateList();
                }
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outOfToogle=true;
                ten.setTextColor(Color.parseColor("#ffffff"));
                five.setTextColor(Color.parseColor(("#000000")));
                ten.setBackgroundResource(R.drawable.toggle_on);
                five.setBackground(null);
                spga.setHint("Out Of 10");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outOfToogle=false;
                five.setTextColor(Color.parseColor("#ffffff"));
                ten.setTextColor(Color.parseColor(("#000000")));
                five.setBackgroundResource(R.drawable.toggle_on);
                ten.setBackground(null);

                spga.setHint("Out Of 5");
            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spga.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Enter the Value of SPGA",Toast.LENGTH_SHORT).show();
                }else {
                    scrollView.smoothScrollTo(0,1800);
                    Decimals decimals=new Decimals();
                    double cgpaValue=Double.parseDouble(spga.getText().toString());
                    for(int i=0;i<cgpaList.SGPA.size();i++){
                        cgpaValue+=cgpaList.SGPA.get(i);
                    }
                    cgpaValue/=(cgpaList.SGPA.size()+1);
                    if(outOfToogle){
                        cgpa.setText(decimals.roundOfTo(cgpaValue)+" / 10");
                        percentage.setText(decimals.roundOfToTwo(cgpaValue*9.5)+"");
                    }else {
                        cgpa.setText(decimals.roundOfTo(cgpaValue)+" / 5");
                        percentage.setText(decimals.roundOfToTwo(cgpaValue*19)+"");
                    }

                }
            }
        });
        return view;
    }

    private void resetActivity() {
        semester1.setText("");
        spga.setText("0");
        cgpa.setText("");
        percentage.setText("");
        cgpaList.SGPA.clear();
        cgpaList.SEMESTER_NAME.clear();
        updateList();
    }

    private void reloadingData() {
        semester1.setText(semesterName.get(0));
        spga.setText(sgpalist.get(0));
        outOfToogle= cgpaOutOf;
        if(cgpaOutOf){
            ten.setTextColor(Color.parseColor("#ffffff"));
            five.setTextColor(Color.parseColor(("#000000")));
            ten.setBackgroundResource(R.drawable.toggle_on);
            five.setBackground(null);
            spga.setHint("Out Of 10");
        }else{
            five.setTextColor(Color.parseColor("#ffffff"));
            ten.setTextColor(Color.parseColor(("#000000")));
            five.setBackgroundResource(R.drawable.toggle_on);
            ten.setBackground(null);

            spga.setHint("Out Of 5");
        }
        for(int i=1;i<sgpalist.size();i++){
            if(cList.addValue(semesterName.get(i),Double.parseDouble(sgpalist.get(i)))){
                updateList();
            }
        }
    }

    private void updateList() {
        cgpaAdapter =new CgpaAdapter(getContext(), cgpaList.SEMESTER_NAME,outOfToogle,this);
        gridLayoutManager1=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager1);
        recyclerView.setAdapter(cgpaAdapter);
    }

    @Override
    public void onMethodCallback() {
        updateList();

    }
}