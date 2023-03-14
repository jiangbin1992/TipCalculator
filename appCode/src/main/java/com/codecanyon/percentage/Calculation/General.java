package com.codecanyon.percentage.Calculation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;


public class General extends Fragment {
    EditText percentage,outOf,obtained;
    ImageView lock1, lock2, lock3;
    Button reset;
    boolean lock1Bool=false,lock2Bool=false,lock3Bol=false;
    Decimals decimals;
    String sectionCalculating="";

    public General() {
        // Required empty public constructor

    }



    @Override
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_general, container, false);
        percentage=view.findViewById(R.id.percentage);
        outOf=view.findViewById(R.id.outOf);
        obtained=view.findViewById(R.id.obtained);
        reset=view.findViewById(R.id.reset);
        lock1=view.findViewById(R.id.lock1);
        lock2=view.findViewById(R.id.lock2);
        lock3=view.findViewById(R.id.lock3);

        lock1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (lock1Bool){
                    percentage.setEnabled(true);
                    lock1Bool=false;
                    lock2.setEnabled(true);
                    lock3.setEnabled(true);
                    Drawable buttonDrawable = lock1.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock1. setBackground(buttonDrawable);

                }else {
                    if(percentage.getText().toString().isEmpty()){
                        percentage.setText("0");
                    }
                    lock1Bool=true;
                    outOf.setEnabled(true);
                    obtained.setEnabled(true);
                    percentage.setEnabled(false);
                    Drawable buttonDrawable = lock1.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.RED);
                    lock1. setBackground(buttonDrawable);
                    lockBackGround(1);
                }
            }
        });
        lock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lock2Bool){
                    lock2Bool=false;
                    outOf.setEnabled(true);
                    lock1.setEnabled(true);
                    lock3.setEnabled(true);
                    Drawable buttonDrawable = lock2.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#C3C3C3"));
                    lock2. setBackground(buttonDrawable);

                }else {
                    if(outOf.getText().toString().isEmpty()){
                        outOf.setText("0");
                    }
                    percentage.setEnabled(true);
                    obtained.setEnabled(true);
                    lock2Bool=true;
                    outOf.setEnabled(false);
                    Drawable buttonDrawable = lock2.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.RED);
                    lock2. setBackground(buttonDrawable);
                    lockBackGround(2);
                }

            }
        });
        lock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lock3Bol){
                    lock3Bol=false;
                    obtained.setEnabled(true);
                    lock2.setEnabled(true);
                    lock1.setEnabled(true);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable,Color.parseColor("#c3c3c3"));
                    lock3. setBackground(buttonDrawable);
                }else {

                    if(obtained.getText().toString().isEmpty()){
                        obtained.setText("0");
                    }
                    percentage.setEnabled(true);
                    outOf.setEnabled(true);
                    lock3Bol=true;
                    obtained.setEnabled(false);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.RED);
                    lock3. setBackground(buttonDrawable);
                    lockBackGround(3);

                }

            }
        });

        decimals=new Decimals();

        percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock2Bool||!lock3Bol){
                    if(percentage.hasFocus()){
                        if(!percentage.getText().toString().isEmpty()&&(!outOf.getText().toString().isEmpty()||!obtained.getText().toString().isEmpty())){

                            if(lock2Bool){
                                sectionCalculating="Obtained";
                                calculateObtained();
                            }else if(lock3Bol){
                                sectionCalculating="OutOf";
                                calculateOutOf();
                            }else{

                                if (sectionCalculating.isEmpty()){
                                    if(obtained.getText().toString().isEmpty()){
                                        sectionCalculating="Obtained";
                                        calculateObtained();
                                    }else {
                                        sectionCalculating="OutOf";
                                        calculateOutOf();
                                    }
                                }else if(sectionCalculating.equals("Obtained")){
                                    if(!outOf.getText().toString().isEmpty()) {
                                        sectionCalculating="Obtained";
                                        calculateObtained();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!obtained.getText().toString().isEmpty()) {
                                        sectionCalculating = "OutOf";
                                        calculateOutOf();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }
                            }

                        }
                    }
                }

            }
        });

        outOf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock1Bool||!lock3Bol) {
                    if (outOf.hasFocus()) {
                        if (!outOf.getText().toString().isEmpty() && (!percentage.getText().toString().isEmpty() || !obtained.getText().toString().isEmpty())) {
                            if(lock3Bol){
                                sectionCalculating = "Percentage";
                                calculatePercentage();

                            }else if(lock1Bool){
                                sectionCalculating = "Obtained";
                                calculateObtained();
                            }else{

                                if (sectionCalculating.isEmpty()){
                                    if(percentage.getText().toString().isEmpty()){
                                        sectionCalculating="Percentage";
                                        calculatePercentage();
                                    }else {
                                        sectionCalculating="Obtained";
                                        calculateObtained();
                                    }
                                }else if(sectionCalculating.equals("Percentage")){
                                    if(!obtained.getText().toString().isEmpty()) {
                                        sectionCalculating = "Percentage";
                                        calculatePercentage();
                                    }else{
                                        sectionCalculating="";
                                    }

                                }else{
                                    if(!percentage.getText().toString().isEmpty()) {
                                        sectionCalculating = "Obtained";
                                        calculateObtained();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }


                            }

                        }
                    }
                }

            }
        });

        obtained.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!lock1Bool||!lock2Bool){
                    if(obtained.hasFocus()){
                        if(!obtained.getText().toString().isEmpty()&&(!outOf.getText().toString().isEmpty()||!percentage.getText().toString().isEmpty())){
                            if(lock1Bool){
                                sectionCalculating="OutOf";
                                calculateOutOf();
                            }else if(lock2Bool){
                                sectionCalculating="Percentage";
                                calculatePercentage();
                            }else{
                                if (sectionCalculating.isEmpty()){
                                    if(percentage.getText().toString().isEmpty()){
                                        sectionCalculating="Percentage";
                                        calculatePercentage();
                                    }else {
                                        sectionCalculating="OutOf";
                                        calculateOutOf();
                                    }
                                }else if(sectionCalculating.equals("Percentage")){
                                    if(!outOf.getText().toString().isEmpty()) {
                                        sectionCalculating = "Percentage";
                                        calculatePercentage();
                                    }else {
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!percentage.getText().toString().isEmpty()) {
                                        sectionCalculating = "OutOf";
                                        calculateOutOf();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sectionCalculating="";
                percentage.setText("");
                outOf.setText("");
                obtained.setText("");
                percentage.setEnabled(true);
                outOf.setEnabled(true);
                obtained.setEnabled(true);
                lock1.setEnabled(true);
                lock2.setEnabled(true);
                lock3.setEnabled(true);
                lock3Bol=false;
                lock1Bool=false;
                lock2Bool=false;
                Drawable buttonDrawable1 = lock1.getBackground();
                buttonDrawable1 = DrawableCompat. wrap(buttonDrawable1);
                DrawableCompat. setTint(buttonDrawable1, Color.parseColor("#c3c3c3"));
                Drawable buttonDrawable2 = lock2.getBackground();
                buttonDrawable2 = DrawableCompat. wrap(buttonDrawable2);
                DrawableCompat. setTint(buttonDrawable2, Color.parseColor("#c3c3c3"));
                Drawable buttonDrawable3 = lock3.getBackground();
                buttonDrawable3 = DrawableCompat. wrap(buttonDrawable3);
                DrawableCompat. setTint(buttonDrawable3, Color.parseColor("#c3c3c3"));
                lock1. setBackground(buttonDrawable1);
                lock2.setBackground(buttonDrawable2);
                lock3.setBackground(buttonDrawable3);
            }
        });
        return view;


    }

    private void calculatePercentage() {
        try {
            double per = Double.parseDouble(obtained.getText().toString()) / Double.parseDouble(outOf.getText().toString()) * 100;
            percentage.setText(String.valueOf(decimals.roundOfTo(per)));
        }catch (Exception e){

        }

    }

    private void calculateObtained() {
        try {
            double obt = Double.parseDouble(percentage.getText().toString()) * Double.parseDouble(outOf.getText().toString()) / 100;
            obtained.setText(String.valueOf(decimals.roundOfTo(obt)));
        }catch(Exception e){

        }
    }

    private void calculateOutOf() {
        try {
            double out = Double.parseDouble(obtained.getText().toString()) / Double.parseDouble(percentage.getText().toString()) * 100;
            outOf.setText(String.valueOf(decimals.roundOfTo(out)));
        }catch (Exception e){

        }
    }

    private void lockBackGround(int i) {
        //1
        if(i==1) {
            Drawable buttonDrawable = lock1.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            DrawableCompat.setTint(buttonDrawable, Color.RED);
            lock1.setBackground(buttonDrawable);
        }else{
            Drawable buttonDrawable = lock1.getBackground();
            buttonDrawable = DrawableCompat. wrap(buttonDrawable);
            DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
            lock1. setBackground(buttonDrawable);
        }
        //2
        if(i==2) {
            Drawable buttonDrawable = lock2.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            DrawableCompat.setTint(buttonDrawable, Color.RED);
            lock2.setBackground(buttonDrawable);
        }else{
            Drawable buttonDrawable = lock2.getBackground();
            buttonDrawable = DrawableCompat. wrap(buttonDrawable);
            DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
            lock2. setBackground(buttonDrawable);
        }
        //3
        if(i==3) {
            Drawable buttonDrawable = lock3.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            DrawableCompat.setTint(buttonDrawable, Color.RED);
            lock3.setBackground(buttonDrawable);
        }else{
            Drawable buttonDrawable = lock3.getBackground();
            buttonDrawable = DrawableCompat. wrap(buttonDrawable);
            DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
            lock3. setBackground(buttonDrawable);
        }
    }
}