package com.codecanyon.percentage.Calculation;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;

public class RootCalculator extends Fragment {

    EditText a,b,c;
    Decimals decimals;
    ImageView lock1,lock2,lock3;
    boolean lock1Bool=false,lock2Bool=false,lock3Bol=false;
    String sectionCalculating="";
    Button reset;

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


    public RootCalculator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_root_calculator, container, false);
        intitalize(view);
        decimals=new Decimals();
        
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sectionCalculating="";
                a.setText("");
                b.setText("");
                c.setText("");
                c.setEnabled(true);
                a.setEnabled(true);
                b.setEnabled(true);
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
        
        lockPressed();
        
        textChanger();

        return view;
    }

    private void textChanger() {

        c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock1Bool||!lock2Bool){
                    if(c.hasFocus()){
                        if(!c.getText().toString().isEmpty()&&(!a.getText().toString().isEmpty()||!b.getText().toString().isEmpty())){

                            if(lock2Bool){
                                sectionCalculating="Numerator";
                                calculateA();
                            }else if(lock1Bool){
                                sectionCalculating="Denominator";
                                calculateB();
                            }else{

                                if (sectionCalculating.isEmpty()){
                                    if(a.getText().toString().isEmpty()){
                                        sectionCalculating="Numerator";
                                        calculateA();
                                    }else {
                                        sectionCalculating="Denominator";
                                        calculateB();
                                    }
                                }else if(sectionCalculating.equals("Numerator")){
                                    if(!b.getText().toString().isEmpty()) {
                                        sectionCalculating = "Numerator";
                                        calculateA();
                                    }else {
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!a.getText().toString().isEmpty()) {
                                        sectionCalculating = "Denominator";
                                        calculateB();
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

        b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock1Bool||!lock3Bol){
                    if(b.hasFocus()){
                        if(!b.getText().toString().isEmpty()&&(!c.getText().toString().isEmpty()||!a.getText().toString().isEmpty())){

                            if(lock1Bool){
                                sectionCalculating="Percentage";
                                calculateC();
                            }else if(lock3Bol){
                                sectionCalculating="Numerator";
                                calculateA();
                            }else{

                                if (sectionCalculating.isEmpty()){
                                    if(a.getText().toString().isEmpty()){
                                        sectionCalculating="Numerator";
                                        calculateA();
                                    }else {
                                        sectionCalculating="Percentage";
                                        calculateC();
                                    }
                                }else if(sectionCalculating.equals("Numerator")){
                                    if(!c.getText().toString().isEmpty()) {
                                        sectionCalculating = "Numerator";
                                        calculateA();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!a.getText().toString().isEmpty()) {
                                        sectionCalculating = "Percentage";
                                        calculateC();
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

        a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!lock2Bool||!lock3Bol){
                    if(a.hasFocus()){
                        if(!a.getText().toString().isEmpty()&&(!c.getText().toString().isEmpty()||!b.getText().toString().isEmpty())){

                            if(lock2Bool){
                                sectionCalculating="Percentage";
                                calculateC();
                            }else if(lock3Bol){
                                sectionCalculating="Denominator";
                                calculateB();
                            }else{
                                if (sectionCalculating.isEmpty()){
                                    if(b.getText().toString().isEmpty()){
                                        sectionCalculating="Denominator";
                                        calculateB();
                                    }else {
                                        sectionCalculating="Percentage";
                                        calculateC();
                                    }
                                }else if(sectionCalculating.equals("Percentage")){
                                    if(!b.getText().toString().isEmpty()) {
                                        sectionCalculating = "Percentage";
                                        calculateC();
                                    }else{
                                        sectionCalculating="";
                                    }
                                }else{
                                    if(!c.getText().toString().isEmpty()) {
                                        sectionCalculating = "Denominator";
                                        calculateB();
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

    }

    private void lockPressed() {

        lock1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (lock1Bool){
                    a.setEnabled(true);
                    lock1Bool=false;
                    lock2.setEnabled(true);
                    lock3.setEnabled(true);
                    Drawable buttonDrawable = lock1.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock1. setBackground(buttonDrawable);

                }else {
                    if(a.getText().toString().isEmpty()){
                        a.setText("0");
                    }
                    c.setEnabled(true);
                    b.setEnabled(true);
                    lock1Bool=true;
                    a.setEnabled(false);
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
                    b.setEnabled(true);
                    lock1.setEnabled(true);
                    lock3.setEnabled(true);
                    Drawable buttonDrawable = lock2.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#C3C3C3"));
                    lock2. setBackground(buttonDrawable);

                }else {
                    if(b.getText().toString().isEmpty()){
                        b.setText("0");
                    }
                    a.setEnabled(true);
                    c.setEnabled(true);
                    lock2Bool=true;
                    b.setEnabled(false);
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
                    c.setEnabled(true);
                    lock2.setEnabled(true);
                    lock1.setEnabled(true);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable,Color.parseColor("#c3c3c3"));
                    lock3. setBackground(buttonDrawable);
                }else {
                    if(c.getText().toString().isEmpty()){
                        c.setText("0");
                    }
                    a.setEnabled(true);
                    b.setEnabled(true);
                    lock3Bol=true;
                    c.setEnabled(false);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.RED);
                    lock3. setBackground(buttonDrawable);
                    lockBackGround(3);
                }

            }
        });

    }

    private void intitalize(View view) {
        a=view.findViewById(R.id.a);
        b=view.findViewById(R.id.b);
        c=view.findViewById(R.id.c);
        reset=view.findViewById(R.id.reset);
        lock1=view.findViewById(R.id.lock1);
        lock2=view.findViewById(R.id.lock2);
        lock3=view.findViewById(R.id.lock3);

    }


    private void calculateC() {
        try {
            double out= Math.pow(Double.parseDouble(b.getText().toString()),1/(Double.parseDouble(a.getText().toString())));
            c.setText(String.valueOf(decimals.roundOfTo(out)));
        }catch (Exception e){

        }
    }

    private void calculateA() {
        try {
            double obt = Math.log(Double.parseDouble(c.getText().toString())) / Math.log(Double.parseDouble(b.getText().toString())) ;
            a.setText(String.valueOf(decimals.roundOfTo(obt)));
        }catch (Exception e){

        }
    }

    private void calculateB() {
        try {
            double out = Math.pow(Double.parseDouble(c.getText().toString()) , Double.parseDouble(a.getText().toString()) );
            b.setText(String.valueOf(decimals.roundOfTo(out)));
        }catch(Exception e){

        }
    }
}