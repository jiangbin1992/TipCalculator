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
import android.widget.TextView;

import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;

public class MarkUp extends Fragment {
    EditText cost,markUp,margin,totalAmount,profit;
    Button reset;
    ImageView lock1,lock2,lock3,lock4,lock5;
    TextView costText,totalText,profitText;
    boolean lockPressed=false;
    Decimals decimals=new Decimals();
    int calculatingPart=0,whichLock=0;

    //calculatingPart 0-> empty 1-> cost&Markup 2-> cost$Margin 3->cost&Total 4->cost&profit 5-> markup&Margin 6->markup&total
    // 7->markup&Profit 8->Margin&total 9->margin&profit 10->total&profit

    private void calculation1(){
        double costValue= Double.parseDouble(cost.getText().toString());
        double markUpValue= Double.parseDouble(markUp.getText().toString());
        double profitValue= costValue*markUpValue/100;
        double totalValue=profitValue+costValue;
        double marginValue=profitValue/totalValue*100;

        profit.setText(""+decimals.roundOfTo(profitValue));
        totalAmount.setText(""+decimals.roundOfTo(totalValue));
        margin.setText(""+decimals.roundOfTo(marginValue));

    }
    private void calculation2(){
        double costValue= Double.parseDouble(cost.getText().toString());
        double marginValue= Double.parseDouble(margin.getText().toString());
        double profitValue=costValue/(100/marginValue-1);
        double totalValue=profitValue+costValue;
        double markUpValue=profitValue/costValue*100;

        markUp.setText(""+decimals.roundOfTo(markUpValue));
        totalAmount.setText(""+decimals.roundOfTo(totalValue));
        profit.setText(""+decimals.roundOfTo(profitValue));
    }
    private void calculation3(){
        double costValue= Double.parseDouble(cost.getText().toString());
        double totalValue= Double.parseDouble(totalAmount.getText().toString());
        double profitValue=totalValue-costValue;
        double markupValue=profitValue/costValue*100;
        double marginValue=profitValue/totalValue*100;

        markUp.setText(""+decimals.roundOfTo(markupValue));
        margin.setText(""+decimals.roundOfTo(marginValue));
        profit.setText(""+decimals.roundOfTo(profitValue));
    }
    private void calculation4(){
        double costValue= Double.parseDouble(cost.getText().toString());
        double profitValue= Double.parseDouble(profit.getText().toString());
        double totalValue=profitValue+costValue;
        double markupValue=profitValue/costValue*100;
        double marginValue=profitValue/totalValue*100;

        markUp.setText(""+decimals.roundOfTo(markupValue));
        margin.setText(""+decimals.roundOfTo(marginValue));
        totalAmount.setText(""+decimals.roundOfTo(totalValue));
    }
    private void calculation5(boolean focusOnMarkup){

        if(focusOnMarkup){
            if(!cost.getText().toString().isEmpty()){
                calculation1();
            }else if(!totalAmount.getText().toString().isEmpty()){
                calculation6();
            }else if(!profitText.getText().toString().isEmpty()){
                calculation7();
            }else{
                double markUpValue=Double.parseDouble(markUp.getText().toString());
                double marginValue=100/(100/markUpValue+1);

                margin.setText(""+decimals.roundOfTo(marginValue));
            }

        }else{
            if(!cost.getText().toString().isEmpty()){
                calculation2();
            }else if(!totalAmount.getText().toString().isEmpty()){
                calculation8();
            }else if(!profitText.getText().toString().isEmpty()){
                calculation9();
            }else{
                double marginValue=Double.parseDouble(markUp.getText().toString());
                double markUpValue=100/(100/marginValue-1);

                markUp.setText(""+decimals.roundOfTo(markUpValue));

            }
        }
    }
    private void calculation6(){
        double markUpValue= Double.parseDouble(markUp.getText().toString());
        double totalValue= Double.parseDouble(totalAmount.getText().toString());
        double costValue=totalValue*100/(markUpValue+100);
        double profitValue=totalValue-costValue;
        double marginValue=profitValue/totalValue*100;

        cost.setText(""+decimals.roundOfTo(costValue));
        margin.setText(""+decimals.roundOfTo(marginValue));
        profit.setText(""+decimals.roundOfTo(profitValue));
    }
    private void calculation7(){
        double markupValue= Double.parseDouble(markUp.getText().toString());
        double profitValue= Double.parseDouble(profit.getText().toString());
        double costValue=100*profitValue/markupValue;
        double totalValue=profitValue+costValue;
        double marginValue=profitValue/totalValue*100;

        cost.setText(""+decimals.roundOfTo(costValue));
        totalAmount.setText(""+decimals.roundOfTo(totalValue));
        margin.setText(""+decimals.roundOfTo(marginValue));
    }
    private void calculation8(){
        double marginValue= Double.parseDouble(margin.getText().toString());
        double totalValue= Double.parseDouble(totalAmount.getText().toString());
        double profitValue=totalValue*marginValue/100;
        double costValue=totalValue-profitValue;
        double markupValue=profitValue/costValue*100;

        markUp.setText(""+decimals.roundOfTo(markupValue));
        cost.setText(""+decimals.roundOfTo(costValue));
        profit.setText(""+decimals.roundOfTo(profitValue));
    }
    private void calculation9(){
        double marginValue= Double.parseDouble(margin.getText().toString());
        double profitValue= Double.parseDouble(profit.getText().toString());
        double costValue=profitValue/marginValue*100;
        double totalValue=profitValue+costValue;
        double markupValue=profitValue/costValue*100;


        markUp.setText(""+decimals.roundOfTo(markupValue));
        cost.setText(""+decimals.roundOfTo(costValue));
        totalAmount.setText(""+decimals.roundOfTo(totalValue));
    }
    private void calculation10(){
        double totalValue= Double.parseDouble(cost.getText().toString());
        double profitValue= Double.parseDouble(totalAmount.getText().toString());
        double costValue=totalValue-profitValue;
        double markupValue=profitValue/costValue*100;
        double marginValue=profitValue/totalValue*100;

        cost.setText(""+decimals.roundOfTo(costValue));
        margin.setText(""+decimals.roundOfTo(marginValue));
        markUp.setText(""+decimals.roundOfTo(markupValue));
    }

    public MarkUp() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mark_up, container, false);
        cost=view.findViewById(R.id.cost);
        markUp=view.findViewById(R.id.markUp);
        margin=view.findViewById(R.id.margin);
        totalAmount=view.findViewById(R.id.total);
        profit=view.findViewById(R.id.profit);
        costText=view.findViewById(R.id.costText);
        totalText=view.findViewById(R.id.totalText);
        profitText=view.findViewById(R.id.profitText);
        lock1=view.findViewById(R.id.lock1);
        lock2=view.findViewById(R.id.lock2);
        lock3=view.findViewById(R.id.lock3);
        lock4=view.findViewById(R.id.lock4);
        lock5=view.findViewById(R.id.lock5);
        reset=view.findViewById(R.id.reset);

        profitText.setText("Profit in "+ Constants.CURRENCY_VALUE);
        totalText.setText("Total Amount to pay in "+ Constants.CURRENCY_VALUE);
        costText.setText("Cost "+ Constants.CURRENCY_VALUE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cost.setText("");
                margin.setText("");
                markUp.setText("");
                totalAmount.setText("");
                cost.setEnabled(true);
                margin.setEnabled(true);
                markUp.setEnabled(true);
                calculatingPart=0;
                whichLock=0;
                totalAmount.setEnabled(true);
                profit.setEnabled(true);
                lockBackGround(0);
                lockPressed=false;
                profit.setText("");
            }
        });

        //lock system
        lock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(whichLock==1){
                    lockPressed=false;
                    whichLock=0;
                    cost.setEnabled(true);
                    Drawable buttonDrawable = lock1.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock1. setBackground(buttonDrawable);
                }else{
                    whichLock=1;
                    if(cost.getText().toString().isEmpty()){
                        cost.setText("0");
                    }
                    markUp.setEnabled(true);
                    margin.setEnabled(true);
                    profit.setEnabled(true);
                    totalAmount.setEnabled(true);
                    lockPressed=true;
                    cost.setEnabled(false);

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
                    markUp.setEnabled(true);
                    Drawable buttonDrawable = lock2.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock2. setBackground(buttonDrawable);
                }else{
                    whichLock=2;
                    if(markUp.getText().toString().isEmpty()){
                        markUp.setText("0");
                    }
                    cost.setEnabled(true);
                    margin.setEnabled(true);
                    profit.setEnabled(true);
                    totalAmount.setEnabled(true);
                    lockPressed=true;
                    markUp.setEnabled(false);
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
                    margin.setEnabled(true);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock3. setBackground(buttonDrawable);
                }else{
                    whichLock=3;
                    if(margin.getText().toString().isEmpty()){
                        margin.setText("0");
                    }
                    cost.setEnabled(true);
                    markUp.setEnabled(true);
                    profit.setEnabled(true);
                    totalAmount.setEnabled(true);
                    lockPressed=true;
                    margin.setEnabled(false);
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
                    totalAmount.setEnabled(true);
                    Drawable buttonDrawable = lock4.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock4. setBackground(buttonDrawable);
                }else{
                    whichLock=4;
                    if(totalAmount.getText().toString().isEmpty()){
                        totalAmount.setText("0");
                    }
                    cost.setEnabled(true);
                    markUp.setEnabled(true);
                    margin.setEnabled(true);
                    profit.setEnabled(true);
                    lockPressed=true;
                    totalAmount.setEnabled(false);
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
                    profit.setEnabled(true);
                    Drawable buttonDrawable = lock5.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock5. setBackground(buttonDrawable);
                }else{
                    whichLock=5;
                    if(profit.getText().toString().isEmpty()){
                        profit.setText("0");
                    }
                    cost.setEnabled(true);
                    margin.setEnabled(true);
                    totalAmount.setEnabled(true);
                    markUp.setEnabled(true);
                    lockPressed=true;
                    profit.setEnabled(false);
                    lockBackGround(5);
                }
            }
        });

        //calculating system
        cost.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(cost.hasFocus()){
                    if(!cost.getText().toString().isEmpty()&&(!margin.getText().toString().isEmpty()||!markUp.getText().toString().isEmpty()||!profit.getText().toString().isEmpty()||!totalAmount.getText().toString().isEmpty())) {
                        if(lockPressed){
                            switch (whichLock){
                                case 2:
                                    calculatingPart=1;
                                    calculation1();
                                    break;
                                case 3:
                                    calculatingPart=2;
                                    calculation2();
                                    break;
                                case 4:
                                    calculatingPart=3;
                                    calculation3();
                                    break;
                                case 5:
                                    calculatingPart=4;
                                    calculation4();
                                    break;
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!markUp.getText().toString().isEmpty()){
                                    calculatingPart=1;
                                    calculation1();
                                }else if(!margin.getText().toString().isEmpty()){
                                    calculatingPart=2;
                                    calculation2();
                                }else if(!totalAmount.getText().toString().isEmpty()){
                                    calculatingPart=3;
                                    calculation3();
                                }else if(!profit.getText().toString().isEmpty()){
                                    calculatingPart=4;
                                    calculation4();
                                }
                            }else if(calculatingPart==1){
                                if(!markUp.getText().toString().isEmpty()) {
                                    calculatingPart = 1;
                                    calculation1();
                                }else {
                                    calculateForEmpty(1);
                                }
                            }else if(calculatingPart==2){
                                if(!margin.getText().toString().isEmpty()) {
                                    calculatingPart = 2;
                                    calculation2();
                                }else{
                                    calculateForEmpty(1);
                                }
                            }else if(calculatingPart==3){
                                if(!totalAmount.getText().toString().isEmpty()) {
                                    calculatingPart = 3;
                                    calculation3();
                                }else {
                                    calculateForEmpty(1);
                                }
                            }else if(calculatingPart==4){
                                if(!profit.getText().toString().isEmpty()) {
                                    calculatingPart = 4;
                                    calculation4();
                                }else {
                                    calculateForEmpty(1);
                                }
                            }else{
                                calculateForEmpty(1);
                            }


                        }
                    }

                }

            }
        });
        markUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(markUp.hasFocus()){
                    if(!markUp.getText().toString().isEmpty()&&(!margin.getText().toString().isEmpty()||!cost.getText().toString().isEmpty()||!profit.getText().toString().isEmpty()||!totalAmount.getText().toString().isEmpty())) {
                        if(lockPressed){
                            switch (whichLock){
                                case 1:
                                    calculatingPart=1;
                                    calculation1();
                                    break;
                                case 3:
                                    calculatingPart=5;
                                    calculation5(true);
                                    break;
                                case 4:
                                    calculatingPart=6;
                                    calculation6();
                                    break;
                                case 5:
                                    calculatingPart=7;
                                    calculation7();
                                    break;
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!cost.getText().toString().isEmpty()){
                                    calculatingPart=1;
                                    calculation1();
                                }else if(!totalAmount.getText().toString().isEmpty()){
                                    calculatingPart=6;
                                    calculation6();
                                }else if(!profit.getText().toString().isEmpty()){
                                    calculatingPart=7;
                                    calculation7();
                                }else if(!margin.getText().toString().isEmpty()){
                                    calculatingPart=5;
                                    calculation5(true);
                                }
                            }else if(calculatingPart==1){
                                if(!cost.getText().toString().isEmpty()) {
                                    calculatingPart = 1;
                                    calculation1();
                                }else {
                                    calculateForEmpty(2);
                                }
                            }else if(calculatingPart==5){
                                if(!margin.getText().toString().isEmpty()) {
                                    calculatingPart = 5;
                                    calculation5(true);
                                }else{
                                    calculateForEmpty(2);
                                }
                            }else if(calculatingPart==6){
                                if(!totalAmount.getText().toString().isEmpty()) {
                                    calculatingPart = 6;
                                    calculation6();
                                }else {
                                    calculateForEmpty(2);
                                }
                            }else if(calculatingPart==7){
                                if(!profit.getText().toString().isEmpty()) {
                                    calculatingPart = 7;
                                    calculation7();
                                }else {
                                    calculateForEmpty(2);
                                }
                            }else{
                                calculateForEmpty(2);
                            }

                        }
                    }

                }

            }
        });
        margin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(margin.hasFocus()){
                    if(!margin.getText().toString().isEmpty()&&(!markUp.getText().toString().isEmpty()||!cost.getText().toString().isEmpty()||!profit.getText().toString().isEmpty()||!totalAmount.getText().toString().isEmpty())) {
                        if(lockPressed){
                            switch (whichLock){
                                case 1:
                                    calculatingPart=2;
                                    calculation2();
                                    break;
                                case 2:
                                    calculatingPart=5;
                                    calculation5(false);
                                    break;
                                case 4:
                                    calculatingPart=8;
                                    calculation8();
                                    break;
                                case 5:
                                    calculatingPart=9;
                                    calculation9();
                                    break;
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!cost.getText().toString().isEmpty()){
                                    calculatingPart=2;
                                    calculation2();
                                }else if(!totalAmount.getText().toString().isEmpty()){
                                    calculatingPart=8;
                                    calculation8();
                                }else if(!profit.getText().toString().isEmpty()){
                                    calculatingPart=9;
                                    calculation9();
                                }else if(!markUp.getText().toString().isEmpty()){
                                    calculatingPart=5;
                                    calculation5(false);
                                }
                            }else if(calculatingPart==2){
                                if(!cost.getText().toString().isEmpty()) {
                                    calculatingPart = 2;
                                    calculation2();
                                }else {
                                    calculateForEmpty(3);
                                }
                            }else if(calculatingPart==5){
                                if(!markUp.getText().toString().isEmpty()) {
                                    calculatingPart = 5;
                                    calculation5(true);
                                }else{
                                    calculateForEmpty(3);
                                }
                            }else if(calculatingPart==8){
                                if(!totalAmount.getText().toString().isEmpty()) {
                                    calculatingPart = 8;
                                    calculation8();
                                }else {
                                    calculateForEmpty(3);
                                }
                            }else if(calculatingPart==9){
                                if(!profit.getText().toString().isEmpty()) {
                                    calculatingPart = 9;
                                    calculation9();
                                }else {
                                    calculateForEmpty(3);
                                }
                            }else{
                                calculateForEmpty(3);
                            }

                        }
                    }

                }

            }
        });
        totalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(totalAmount.hasFocus()){
                    if(!totalAmount.getText().toString().isEmpty()&&(!margin.getText().toString().isEmpty()||!cost.getText().toString().isEmpty()||!profit.getText().toString().isEmpty()||!markUp.getText().toString().isEmpty())) {
                        if(lockPressed){
                            switch (whichLock){
                                case 1:
                                    calculatingPart=3;
                                    calculation3();
                                    break;
                                case 2:
                                    calculatingPart=6;
                                    calculation6();
                                    break;
                                case 3:
                                    calculatingPart=8;
                                    calculation8();
                                    break;
                                case 5:
                                    calculatingPart=10;
                                    calculation10();
                                    break;
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!cost.getText().toString().isEmpty()){
                                    calculatingPart=3;
                                    calculation3();
                                }else if(!margin.getText().toString().isEmpty()){
                                    calculatingPart=8;
                                    calculation8();
                                }else if(!markUp.getText().toString().isEmpty()){
                                    calculatingPart=6;
                                    calculation6();
                                }else if(!profit.getText().toString().isEmpty()){
                                    calculatingPart=10;
                                    calculation10();
                                }
                            }else if(calculatingPart==3){
                                if(!cost.getText().toString().isEmpty()) {
                                    calculatingPart = 3;
                                    calculation3();
                                }else {
                                    calculateForEmpty(4);
                                }
                            }else if(calculatingPart==8){
                                if(!margin.getText().toString().isEmpty()) {
                                    calculatingPart = 8;
                                    calculation8();
                                }else{
                                    calculateForEmpty(4);
                                }
                            }else if(calculatingPart==6){
                                if(!markUp.getText().toString().isEmpty()) {
                                    calculatingPart = 6;
                                    calculation6();
                                }else {
                                    calculateForEmpty(4);
                                }
                            }else if(calculatingPart==10){
                                if(!profit.getText().toString().isEmpty()) {
                                    calculatingPart = 10;
                                    calculation10();
                                }else {
                                    calculateForEmpty(4);
                                }
                            }else{
                                calculateForEmpty(4);
                            }

                        }
                    }

                }

            }
        });
        profit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(profit.hasFocus()){
                    if(!profit.getText().toString().isEmpty()&&(!margin.getText().toString().isEmpty()||!cost.getText().toString().isEmpty()||!markUp.getText().toString().isEmpty()||!totalAmount.getText().toString().isEmpty())) {
                        if(lockPressed){
                            switch (whichLock){
                                case 1:
                                    calculatingPart=4;
                                    calculation4();
                                    break;
                                case 2:
                                    calculatingPart=7;
                                    calculation7();
                                    break;
                                case 3:
                                    calculatingPart=9;
                                    calculation9();
                                    break;
                                case 4:
                                    calculatingPart=10;
                                    calculation10();
                                    break;
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!cost.getText().toString().isEmpty()){
                                    calculatingPart=4;
                                    calculation4();
                                }else if(!margin.getText().toString().isEmpty()){
                                    calculatingPart=9;
                                    calculation9();
                                }else if(!totalAmount.getText().toString().isEmpty()){
                                    calculatingPart=10;
                                    calculation10();
                                }else if(!markUp.getText().toString().isEmpty()){
                                    calculatingPart=7;
                                    calculation7();
                                }
                            }else if(calculatingPart==4){
                                if(!cost.getText().toString().isEmpty()) {
                                    calculatingPart = 4;
                                    calculation4();
                                }else {
                                    calculateForEmpty(5);
                                }
                            }else if(calculatingPart==9){
                                if(!margin.getText().toString().isEmpty()) {
                                    calculatingPart = 9;
                                    calculation9();
                                }else{
                                    calculateForEmpty(5);
                                }
                            }else if(calculatingPart==10){
                                if(!totalAmount.getText().toString().isEmpty()) {
                                    calculatingPart = 10;
                                    calculation10();
                                }else {
                                    calculateForEmpty(5);
                                }
                            }else if(calculatingPart==7){
                                if(!markUp.getText().toString().isEmpty()) {
                                    calculatingPart = 7;
                                    calculation7();
                                }else {
                                    calculateForEmpty(5);
                                }
                            }else{
                                calculateForEmpty(5);
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

    private void calculateForEmpty(int position) {
        boolean c=cost.getText().toString().isEmpty();
        boolean mu=markUp.getText().toString().isEmpty();
        boolean mg=margin.getText().toString().isEmpty();
        boolean t=totalAmount.getText().toString().isEmpty();
        boolean p=profit.getText().toString().isEmpty();

        if(position==1){
            if(!mu){
                calculatingPart=1;
                calculation1();
            }else if(!mg){
                calculatingPart=2;
                calculation2();
            }else if(!t){
                calculatingPart=3;
                calculation3();
            }else if(!p){
                calculatingPart=4;
                calculation4();
            } else {
                calculatingPart=0;
            }
        }else if(position==2){
            if(!c){
                calculatingPart=1;
                calculation1();
            }else if(!mg){
                calculatingPart=5;
                calculation5(true);
            }else if(!t){
                calculatingPart=6;
                calculation6();
            }else if(!p){
                calculatingPart=7;
                calculation7();
            }else {
                calculatingPart=0;
            }
        }else if(position==3){

            if(!c){
                calculatingPart=2;
                calculation2();
            }else if(!mu){
                calculatingPart=5;
                calculation5(false);
            }else if(!t){
                calculatingPart=8;
                calculation8();
            }else if(!p){
                calculatingPart=9;
                calculation9();
            }
            else {
                calculatingPart=0;
            }
        }else if (position==4){

            if(!c){
                calculatingPart=3;
                calculation3();
            }else if(!mu){
                calculatingPart=6;
                calculation6();
            }else if(!mg){
                calculatingPart=8;
                calculation8();
            }else if (!p){
                calculatingPart=10;
                calculation10();
            }
            else {
                calculatingPart=0;
            }
        }else if(position==5){

            if(!c){
                calculatingPart=4;
                calculation4();
            }else if(!mu){
                calculatingPart=7;
                calculation7();
            }else if(!mg){
                calculatingPart=9;
                calculation9();
            }else if (!t){
                calculatingPart=10;
                calculation10();
            }
            else {
                calculatingPart=0;
            }
        }

    }
}