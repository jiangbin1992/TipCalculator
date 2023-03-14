package com.codecanyon.percentage.Calculation;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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

public class Ratio extends Fragment {
    EditText numerator,denominator,percentage;
    Decimals decimals;
    ImageView lock1,lock2,lock3;
    boolean lock1Bool=false,lock2Bool=false,lock3Bol=false;
    String sectionCalculating="";


    public Ratio() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ratio, container, false);
        numerator=view.findViewById(R.id.num);
        denominator=view.findViewById(R.id.denominator);
        percentage=view.findViewById(R.id.percent);
        Button reset=view.findViewById(R.id.reset);
        decimals=new Decimals();
        reset=view.findViewById(R.id.reset);
        lock1=view.findViewById(R.id.lock1);
        lock2=view.findViewById(R.id.lock2);
        lock3=view.findViewById(R.id.lock3);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sectionCalculating="";
                numerator.setText("");
                denominator.setText("");
                percentage.setText("");
                percentage.setEnabled(true);
                numerator.setEnabled(true);
                denominator.setEnabled(true);
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

        lock1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (lock1Bool){
                    numerator.setEnabled(true);
                    lock1Bool=false;
                    lock2.setEnabled(true);
                    lock3.setEnabled(true);
                    Drawable buttonDrawable = lock1.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock1. setBackground(buttonDrawable);

                }else {
                    if(numerator.getText().toString().isEmpty()){
                        numerator.setText("0");
                    }
                    percentage.setEnabled(true);
                    denominator.setEnabled(true);
                    lock1Bool=true;
                    numerator.setEnabled(false);
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
                    denominator.setEnabled(true);
                    lock1.setEnabled(true);
                    lock3.setEnabled(true);
                    Drawable buttonDrawable = lock2.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#C3C3C3"));
                    lock2. setBackground(buttonDrawable);

                }else {
                    if(denominator.getText().toString().isEmpty()){
                        denominator.setText("0");
                    }
                    numerator.setEnabled(true);
                    percentage.setEnabled(true);
                    lock2Bool=true;
                    denominator.setEnabled(false);
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
                    percentage.setEnabled(true);
                    lock2.setEnabled(true);
                    lock1.setEnabled(true);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable,Color.parseColor("#c3c3c3"));
                    lock3. setBackground(buttonDrawable);
                }else {
                    if(percentage.getText().toString().isEmpty()){
                        percentage.setText("0");
                    }
                    numerator.setEnabled(true);
                    denominator.setEnabled(true);
                    lock3Bol=true;
                    percentage.setEnabled(false);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.RED);
                    lock3. setBackground(buttonDrawable);
                    lockBackGround(3);
                }

            }
        });

        percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock1Bool||!lock2Bool){
                    if(percentage.hasFocus()){
                        if(!percentage.getText().toString().isEmpty()&&(!numerator.getText().toString().isEmpty()||!denominator.getText().toString().isEmpty())){

                            if(lock2Bool){
                                sectionCalculating="Numerator";
                                calculateNumerator();
                            }else if(lock1Bool){
                                sectionCalculating="Denominator";
                                calculateDenominator();
                            }else{

                                if (sectionCalculating.isEmpty()){
                                    if(numerator.getText().toString().isEmpty()){
                                        sectionCalculating="Numerator";
                                        calculateNumerator();
                                    }else {
                                        sectionCalculating="Denominator";
                                        calculateDenominator();
                                    }
                                }else if(sectionCalculating.equals("Numerator")){
                                    if(!denominator.getText().toString().isEmpty()) {
                                        sectionCalculating = "Numerator";
                                        calculateNumerator();
                                    }else {
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!numerator.getText().toString().isEmpty()) {
                                        sectionCalculating = "Denominator";
                                        calculateDenominator();
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

        denominator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock1Bool||!lock3Bol){
                    if(denominator.hasFocus()){
                        if(!denominator.getText().toString().isEmpty()&&(!percentage.getText().toString().isEmpty()||!numerator.getText().toString().isEmpty())){

                            if(lock1Bool){
                                sectionCalculating="Percentage";
                                calculatePercentage();
                            }else if(lock3Bol){
                                sectionCalculating="Numerator";
                                calculateNumerator();
                            }else{

                                if (sectionCalculating.isEmpty()){
                                    if(numerator.getText().toString().isEmpty()){
                                        sectionCalculating="Numerator";
                                        calculateNumerator();
                                    }else {
                                        sectionCalculating="Percentage";
                                        calculatePercentage();
                                    }
                                }else if(sectionCalculating.equals("Numerator")){
                                    if(!percentage.getText().toString().isEmpty()) {
                                        sectionCalculating = "Numerator";
                                        calculateNumerator();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!numerator.getText().toString().isEmpty()) {
                                        sectionCalculating = "Percentage";
                                        calculatePercentage();
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

        numerator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock2Bool||!lock3Bol){
                    if(numerator.hasFocus()){
                        if(!numerator.getText().toString().isEmpty()&&(!percentage.getText().toString().isEmpty()||!denominator.getText().toString().isEmpty())){

                            if(lock2Bool){
                                sectionCalculating="Percentage";
                                calculatePercentage();
                            }else if(lock3Bol){
                                sectionCalculating="Denominator";
                                calculateDenominator();
                            }else{
                                if (sectionCalculating.isEmpty()){
                                    if(denominator.getText().toString().isEmpty()){
                                        sectionCalculating="Denominator";
                                        calculateDenominator();
                                    }else {
                                        sectionCalculating="Percentage";
                                        calculatePercentage();
                                    }
                                }else if(sectionCalculating.equals("Percentage")){
                                    if(!denominator.getText().toString().isEmpty()) {
                                        sectionCalculating = "Percentage";
                                        calculatePercentage();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!percentage.getText().toString().isEmpty()) {
                                        sectionCalculating = "Denominator";
                                        calculateDenominator();
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


        // Inflate the layout for this fragment
        return view;
    }

    private void calculatePercentage() {
        try {
            double per= Double.parseDouble(numerator.getText().toString())/Double.parseDouble(denominator.getText().toString())*100;
            percentage.setText(String.valueOf(decimals.roundOfTo(per)));
        }catch (Exception e){

        }
    }

    private void calculateNumerator() {
        try {
            double obt = Double.parseDouble(percentage.getText().toString()) * Double.parseDouble(denominator.getText().toString()) / 100;
            numerator.setText(String.valueOf(decimals.roundOfTo(obt)));
        }catch (Exception e){

        }
    }

    private void calculateDenominator() {
        try {
            double out = Double.parseDouble(numerator.getText().toString()) / Double.parseDouble(percentage.getText().toString()) * 100;
            denominator.setText(String.valueOf(decimals.roundOfTo(out)));
        }catch(Exception e){

        }
    }
}