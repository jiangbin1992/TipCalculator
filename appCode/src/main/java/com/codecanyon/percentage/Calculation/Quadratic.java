package com.codecanyon.percentage.Calculation;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.codecanyon.percentage.Backend.QudraticClass;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;

public class Quadratic extends Fragment {

    boolean savedOpened=false;
    String aV,bV,cV,x1,x2;
    EditText a,b,c;
    Button reset,calculate,save;
    TextView x1Text,x2Text,equation;
    Decimals decimals=new Decimals();
    NestedScrollView nestedScrollView;
    SQLiteDatabase myDatabase;

    public Quadratic(String a,String b, String c) {
        // Required empty public constructor
        savedOpened=true;
        this.aV=a;
        this.bV=b;
        this.cV=c;

    }
    public Quadratic() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_quadratic, container, false);
        intialize(view);
        equation.setVisibility(View.GONE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.setText("");
                b.setText("");
                c.setText("");
                x1Text.setText("");
                x2Text.setText("");
                equation.setVisibility(View.GONE);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aValue=a.getText().toString();
                String bValue=b.getText().toString();
                String cValue=c.getText().toString();


                if (aValue.isEmpty()||bValue.isEmpty()||cValue.isEmpty()){
                    Toast.makeText(getActivity(),"Enter all the value",Toast.LENGTH_SHORT).show();
                }else{
                    calculateTheValue(Double.parseDouble(aValue),Double.parseDouble(bValue),Double.parseDouble(cValue));



                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aValue=a.getText().toString();
                String bValue=b.getText().toString();
                String cValue=c.getText().toString();


                if (aValue.isEmpty()||bValue.isEmpty()||cValue.isEmpty()){
                    Toast.makeText(getActivity(),"Enter all the value",Toast.LENGTH_SHORT).show();
                }else{
                    fileName();
                }
            }
        });

        return view;
    }

    private void calculateTheValue(double a, double b, double c) {

        if((Math.pow(b, 2) - 4*a*c) >= 0) {
            double root1,root2;
            root1 = ((-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a));
            root2 = ((-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a));
            x1=String.valueOf(decimals.roundOfToTwo(root1));
            x2=String.valueOf(decimals.roundOfToTwo(root2));

        }else{
            double realPart=decimals.roundOfToTwo(-b/(2*a));
            double imgPart=decimals.roundOfToTwo((Math.sqrt(-(Math.pow(b, 2) - 4 * a * c))/(2*a)));

            x1=(realPart)+" + i"+imgPart;
            x2=realPart+" - i"+imgPart;

        }
        equation.setVisibility(View.VISIBLE);
        equation.setText("Equation : "+a+getString(R.string.x2)+" + "+b+"x + "+c+" = 0 ");
        x1Text.setText(x1);
        x2Text.setText(x2);

    }

    private void intialize(View view) {
        a=view.findViewById(R.id.a);
        b=view.findViewById(R.id.b);
        c=view.findViewById(R.id.c);

        x1Text=view.findViewById(R.id.x1);
        x2Text=view.findViewById(R.id.x2);
        equation=view.findViewById(R.id.equation);

        reset=view.findViewById(R.id.reset);
        save=view.findViewById(R.id.save);
        calculate=view.findViewById(R.id.calculate);

        myDatabase= getActivity().openOrCreateDatabase("Quadratic",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS quadraticTable(a TEXT,b TEXT,c TEXT,fileName TEXT,id INTEGER PRIMARY KEY)");


        if(savedOpened){
            a.setText(aV);
            b.setText(bV);
            c.setText(cV);

            calculateTheValue(Double.valueOf(aV),Double.valueOf(bV),Double.valueOf(cV));
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
        for(QudraticClass quadratic: Constants.QUADRATIC_SAVED_LIST){
            if(quadratic.getFileName().equals(fileName)){
                return true;
            }
        }
        return false;
    }

    private void saveTheCal(String fileName) {
        // Create a new user with a first, middle, and last name

        String aValue=a.getText().toString();
        String bValue=b.getText().toString();
        String cValue=c.getText().toString();



        myDatabase.execSQL("INSERT INTO quadraticTable(a ,b , c ,fileName  ) VALUES ('"
                + aValue + "','" + bValue + "','" + cValue +
                "','" + fileName+  "')");

        QudraticClass saved=new QudraticClass(fileName,aValue,bValue,cValue);
        Constants.QUADRATIC_SAVED_LIST.add(saved);

    }

}