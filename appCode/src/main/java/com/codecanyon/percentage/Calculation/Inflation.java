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

import java.util.Calendar;

public class Inflation extends Fragment {

    EditText inflation,currentYear,endYear,currentPrice,finalPrice;
    Button reset;
    ImageView lock1,lock2,lock3,lock4,lock5;
    Boolean lockPressed=false;
    int whichLock=0,calculatingPart=0;
    Decimals decimals=new Decimals();

    /*Calculation Part  1->finalPrice 2->endYear 3->currentYear 4->currentPrice 5->inflation*/

    public Inflation() {
        // Required empty public constructor
    }

    public void calculation1(){
        try {

            double inflationDouble = Double.parseDouble(inflation.getText().toString());
            double currentPriceDouble = Double.parseDouble(currentPrice.getText().toString());
            int currentYearInt = Integer.parseInt(currentYear.getText().toString());
            int endYearInt = Integer.parseInt(endYear.getText().toString());
            int year = endYearInt - currentYearInt;
            double endPriceDouble = currentPriceDouble * Math.pow(1 + inflationDouble / 100, year);
            finalPrice.setText("" + decimals.roundOfTo(endPriceDouble));
        }catch (Exception e){

        }
    }
    public void calculation2(){
        try {

            double inflationDouble = Double.parseDouble(inflation.getText().toString());
            double currentPriceDouble = Double.parseDouble(currentPrice.getText().toString());
            int currentYearInt = Integer.parseInt(currentYear.getText().toString());
            double finalPriceDouble = Double.parseDouble(finalPrice.getText().toString());
            int year = (int) (Math.log(finalPriceDouble / currentPriceDouble) / Math.log(inflationDouble / 100));
            int endYearInt = year + currentYearInt;
            endYear.setText("" + (endYearInt));
        }catch (Exception e){

        }
    }
    public void calculation3(){
        try {
            double inflationDouble = Double.parseDouble(inflation.getText().toString());
            double currentPriceDouble = Double.parseDouble(currentPrice.getText().toString());
            int endYearInt = Integer.parseInt(endYear.getText().toString());
            double finalPriceDouble = Double.parseDouble(finalPrice.getText().toString());
            int year = (int) (Math.log(finalPriceDouble / currentPriceDouble) / Math.log(inflationDouble / 100));
            int currentYearInt = year - endYearInt;
            currentYear.setText("" + (currentYearInt));
        }catch (Exception e){

        }
    }
    public void calculation4(){
        try {
            double inflationDouble = Double.parseDouble(inflation.getText().toString());
            int endYearInt = Integer.parseInt(endYear.getText().toString());
            int currentYearInt = Integer.parseInt(currentYear.getText().toString());
            double finalPriceDouble = Double.parseDouble(finalPrice.getText().toString());
            int year = endYearInt - currentYearInt;
            double currentPriceDouble = finalPriceDouble * Math.pow(1 + inflationDouble / 100, -year);
            currentPrice.setText("" + String.valueOf(decimals.roundOfTo(currentPriceDouble)));
        }catch (Exception e){

        }
    }
    public void calculation5(){
        try {
            double currentPriceDouble = Double.parseDouble(currentPrice.getText().toString());
            int endYearInt = Integer.parseInt(endYear.getText().toString());
            int currentYearInt = Integer.parseInt(currentYear.getText().toString());
            double finalPriceDouble = Double.parseDouble(finalPrice.getText().toString());
            int year = endYearInt - currentYearInt;
            double inflationDouble = Math.pow(10, Math.log(finalPriceDouble / currentPriceDouble) / year + 2);
            inflation.setText("" + String.valueOf(decimals.roundOfTo(inflationDouble)));
        }catch (Exception e){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inflation, container, false);
        reset=view.findViewById(R.id.reset);
        inflation=view.findViewById(R.id.inflation);
        currentYear=view.findViewById(R.id.currentYear);
        endYear=view.findViewById(R.id.comparedYear);
        currentPrice=view.findViewById(R.id.currentPrice);
        finalPrice=view.findViewById(R.id.finalPrice);
        lock1=view.findViewById(R.id.lock1);
        lock2=view.findViewById(R.id.lock2);
        lock3=view.findViewById(R.id.lock3);
        lock4=view.findViewById(R.id.lock4);
        lock5=view.findViewById(R.id.lock5);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year= Calendar.getInstance().get(Calendar.YEAR);
                inflation.setText("");
                currentPrice.setText("");
                finalPrice.setText("");
                currentYear.setText(year+"");
                inflation.setEnabled(true);
                currentPrice.setEnabled(true);
                finalPrice.setEnabled(true);
                endYear.setEnabled(true);
                currentYear.setEnabled(true);
                int i=year+1;
                calculatingPart=0;
                whichLock=0;
                endYear.setText(i+"");
                lockBackGround(0);
                lockPressed=false;
            }
        });

        lock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(whichLock==1){
                    lockPressed=false;
                    whichLock=0;
                    inflation.setEnabled(true);
                    Drawable buttonDrawable = lock1.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock1. setBackground(buttonDrawable);
                }else{
                    whichLock=1;
                    if(inflation.getText().toString().isEmpty()){
                        inflation.setText("0");
                    }
                    currentYear.setEnabled(true);
                    currentPrice.setEnabled(true);
                    endYear.setEnabled(true);
                    finalPrice.setEnabled(true);
                    lockPressed=true;
                    inflation.setEnabled(false);
                    lockBackGround(1);
                }
            }
        });
        lock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(whichLock==2){
                    lockPressed=false;
                    whichLock=0;
                    currentPrice.setEnabled(true);
                    Drawable buttonDrawable = lock2.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock2. setBackground(buttonDrawable);
                }else{
                    whichLock=2;
                    if(currentPrice.getText().toString().isEmpty()){
                        currentPrice.setText("0");
                    }
                    inflation.setEnabled(true);
                    currentYear.setEnabled(true);
                    endYear.setEnabled(true);
                    finalPrice.setEnabled(true);
                    lockPressed=true;
                    currentPrice.setEnabled(false);
                    lockBackGround(2);
                }
            }
        });
        lock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(whichLock==3){
                    lockPressed=false;
                    whichLock=0;
                    currentYear.setEnabled(true);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock3. setBackground(buttonDrawable);
                }else{
                    whichLock=3;
                    if(currentYear.getText().toString().isEmpty()){
                        currentYear.setText("0");
                    }
                    inflation.setEnabled(true);
                    currentPrice.setEnabled(true);
                    endYear.setEnabled(true);
                    finalPrice.setEnabled(true);
                    lockPressed=true;
                    currentYear.setEnabled(false);
                    lockBackGround(3);
                }

            }
        });
        lock4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(whichLock==4){
                    whichLock=0;
                    lockPressed=false;
                    endYear.setEnabled(true);
                    Drawable buttonDrawable = lock4.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock4. setBackground(buttonDrawable);
                }else{
                    whichLock=4;
                    if(endYear.getText().toString().isEmpty()){
                        endYear.setText("0");
                    }
                    inflation.setEnabled(true);
                    currentYear.setEnabled(true);
                    currentPrice.setEnabled(true);
                    finalPrice.setEnabled(true);
                    lockPressed=true;
                    endYear.setEnabled(false);
                    lockBackGround(4);
                }
            }
        });
        lock5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(whichLock==5){
                    whichLock=0;
                    lockPressed=false;
                    finalPrice.setEnabled(true);
                    Drawable buttonDrawable = lock5.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock5. setBackground(buttonDrawable);
                }else{
                    whichLock=5;
                    if(finalPrice.getText().toString().isEmpty()){
                        finalPrice.setText("0");
                    }
                    inflation.setEnabled(true);
                    currentYear.setEnabled(true);
                    endYear.setEnabled(true);
                    currentPrice.setEnabled(true);
                    lockPressed=true;
                    finalPrice.setEnabled(false);
                    lockBackGround(5);
                }
            }
        });

        inflation.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(inflation.hasFocus()){
                    int currentPriceInt=currentPrice.getText().toString().isEmpty()? 0:1;
                    int currentYearInt=currentYear.getText().toString().isEmpty()? 0:1;
                    int endYearInt=endYear.getText().toString().isEmpty()? 0:1;
                    int finalPriceInt=finalPrice.getText().toString().isEmpty()? 0:1;
                    int add=currentPriceInt+currentYearInt+endYearInt+finalPriceInt;
                    if(!inflation.getText().toString().isEmpty()&&(add>=3)){
                        if(lockPressed){
                            if(whichLock==3){
                                calculatingPart=3;
                                calculation3();
                            }else if (whichLock==4){
                                calculatingPart=4;
                                calculation4();
                            }else if(whichLock==5){
                                calculatingPart=5;
                                calculation1();
                            }else if(whichLock==2){
                                calculatingPart=2;
                                calculation4();
                            }
                        }else if(calculatingPart==0){
                            if(currentYearInt==0){
                                calculatingPart=3;
                                calculation3();
                            }else if(endYearInt==0){
                                calculatingPart=4;
                                calculation2();
                            }else if(currentPriceInt==0){
                                calculatingPart=2;
                                calculation4();
                            }else if(finalPriceInt==0){
                                calculatingPart=5;
                                calculation1();
                            }
                        } else if(calculatingPart==2){
                            calculation4();
                        }else if (calculatingPart==3){
                            calculation3();
                        }else if(calculatingPart==4){
                            calculation2();
                        }else if(calculatingPart==5){
                            calculation1();
                        }else{
                            calculatingPart=5;
                            calculation1();
                        }
                    }



                }

            }
        });
        currentPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(currentPrice.hasFocus()){
                    int inflationInt=inflation.getText().toString().isEmpty()? 0:1;
                    int currentYearInt=currentYear.getText().toString().isEmpty()? 0:1;
                    int endYearInt=endYear.getText().toString().isEmpty()? 0:1;
                    int finalPriceInt=finalPrice.getText().toString().isEmpty()? 0:1;
                    int add=inflationInt+currentYearInt+endYearInt+finalPriceInt;
                    if(!currentPrice.getText().toString().isEmpty()&&(add>=3)) {
                        if(lockPressed){
                            if(whichLock==3){
                                calculatingPart=3;
                                calculation3();
                            }else if (whichLock==4){
                                calculatingPart=4;
                                calculation2();
                            }else if(whichLock==1){
                                calculatingPart=1;
                                calculation5();
                            }else if(whichLock==5){
                                calculatingPart=5;
                                calculation1();
                            }
                        }else if(calculatingPart==0){
                            if(currentYearInt==0){
                                calculatingPart=3;
                                calculation3();
                            }else if(endYearInt==0){
                                calculatingPart=4;
                                calculation2();
                            }else if(inflationInt==0){
                                calculatingPart=1;
                                calculation5();
                            }else if(finalPriceInt==0){
                                calculatingPart=5;
                                calculation1();
                            }
                        } else {
                            if(calculatingPart==4){
                                calculation2();
                            }else if (calculatingPart==3){
                                calculation3();
                            }else if(calculatingPart==1){
                                calculation5();
                            }else if(calculatingPart==5){
                                calculation1();
                            }else{
                                calculatingPart=5;
                                calculation1();
                            }
                        }
                    }


                }
            }
        });
        currentYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if(currentYear.hasFocus()){
                    int inflationInt=inflation.getText().toString().isEmpty()? 0:1;
                    int currentPriceInt=currentYear.getText().toString().isEmpty()? 0:1;
                    int endYearInt=endYear.getText().toString().isEmpty()? 0:1;
                    int finalPriceInt=finalPrice.getText().toString().isEmpty()? 0:1;
                    int add=inflationInt+currentPriceInt+endYearInt+finalPriceInt;
                    if(!currentPrice.getText().toString().isEmpty()&&(add>=3)) {
                        if(lockPressed){
                            if(whichLock==2){
                                calculatingPart=2;
                                calculation4();
                            }else if (whichLock==4){
                                calculatingPart=4;
                                calculation2();
                            }else if(whichLock==1){
                                calculatingPart=1;
                                calculation5();
                            }else if(whichLock==5){
                                calculatingPart=5;
                                calculation1();
                            }
                        }else if(calculatingPart==0){
                            if(currentPriceInt==0){
                                calculatingPart=2;
                                calculation4();
                            }else if(endYearInt==0){
                                calculatingPart=4;
                                calculation2();
                            }else if(inflationInt==0){
                                calculatingPart=1;
                                calculation5();
                            }else if(finalPriceInt==0){
                                calculatingPart=5;
                                calculation1();
                            }
                        }else {
                            if(calculatingPart==4){
                                calculation2();
                            }else if (calculatingPart==2){
                                calculation4();
                            }else if(calculatingPart==1){
                                calculation5();
                            }else if(calculatingPart==5){
                                calculation1();
                            }else{
                                calculatingPart=5;
                                calculation1();
                            }
                        }
                    }


                }

            }
        });
        endYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(endYear.hasFocus()){
                    int inflationInt=inflation.getText().toString().isEmpty()? 0:1;
                    int currentYearInt=currentYear.getText().toString().isEmpty()? 0:1;
                    int currentPriceInt=currentPrice.getText().toString().isEmpty()? 0:1;
                    int finalPriceInt=finalPrice.getText().toString().isEmpty()? 0:1;
                    int add=inflationInt+currentYearInt+currentPriceInt+finalPriceInt;
                    if(!currentPrice.getText().toString().isEmpty()&&(add>=3)) {
                        if(lockPressed){
                            if(whichLock==3){
                                calculatingPart=3;
                                calculation3();
                            }else if (whichLock==1){
                                calculatingPart=1;
                                calculation5();
                            }else if(whichLock==5){
                                calculatingPart=5;
                                calculation1();
                            }else if(whichLock==2){
                                calculatingPart=2;
                                calculation4();
                            }
                        }else if(calculatingPart==0){
                            if(currentYearInt==0){
                                calculatingPart=3;
                                calculation3();
                            }else if(currentPriceInt==0){
                                calculatingPart=2;
                                calculation4();
                            }else if(inflationInt==0){
                                calculatingPart=1;
                                calculation5();
                            }else if(finalPriceInt==0){
                                calculatingPart=5;
                                calculation1();
                            }
                        } else {
                            if(calculatingPart==2){
                                calculation4();
                            }else if (calculatingPart==3){
                                calculation3();
                            }else if(calculatingPart==1){
                                calculation5();
                            }else if(calculatingPart==5){
                                calculation1();
                            }else{
                                calculatingPart=5;
                                calculation1();
                            }
                        }
                    }


                }

            }
        });
        finalPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if(finalPrice.hasFocus()){
                    int inflationInt=inflation.getText().toString().isEmpty()? 0:1;
                    int currentYearInt=currentYear.getText().toString().isEmpty()? 0:1;
                    int currentPriceInt=currentPrice.getText().toString().isEmpty()? 0:1;
                    int endYearInt=endYear.getText().toString().isEmpty()? 0:1;
                    int add=inflationInt+currentYearInt+currentPriceInt+endYearInt;
                    if(!currentPrice.getText().toString().isEmpty()&&(add>=3)) {
                        if(lockPressed){
                            if(whichLock==3){
                                calculatingPart=3;
                                calculation3();
                            }else if (whichLock==4){
                                calculatingPart=4;
                                calculation4();
                            }else if(whichLock==1){
                                calculatingPart=1;
                                calculation5();
                            }else if(whichLock==2){
                                calculatingPart=2;
                                calculation4();
                            }
                        }else if(calculatingPart==0){
                            if(currentYearInt==0){
                                calculatingPart=3;
                                calculation3();
                            }else if(currentPriceInt==0){
                                calculatingPart=2;
                                calculation4();
                            }else if(inflationInt==0){
                                calculatingPart=1;
                                calculation5();
                            }else if(endYearInt==0){
                                calculatingPart=4;
                                calculation2();
                            }
                        } else {
                            if(calculatingPart==2){
                                calculation4();
                            }else if (calculatingPart==3){
                                calculation3();
                            }else if(calculatingPart==1){
                                calculation5();
                            }else if(calculatingPart==4){
                                calculation2();
                            }else{
                                calculatingPart=1;
                                calculation5();
                            }
                        }
                    }


                }
            }
        });
        return view;
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
        //4
        if(i==4) {
            Drawable buttonDrawable = lock4.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            DrawableCompat.setTint(buttonDrawable, Color.RED);
            lock4.setBackground(buttonDrawable);
        }else{
            Drawable buttonDrawable = lock4.getBackground();
            buttonDrawable = DrawableCompat. wrap(buttonDrawable);
            DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
            lock4. setBackground(buttonDrawable);
        }
        //5
        if(i==5) {
            Drawable buttonDrawable = lock5.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            DrawableCompat.setTint(buttonDrawable, Color.RED);
            lock5.setBackground(buttonDrawable);
        }else{
            Drawable buttonDrawable = lock5.getBackground();
            buttonDrawable = DrawableCompat. wrap(buttonDrawable);
            DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
            lock5. setBackground(buttonDrawable);
        }
    }
}