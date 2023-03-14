package com.codecanyon.percentage.Calculation;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecanyon.percentage.Backend.percentageTsgList;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;
import com.codecanyon.percentage.Supporting.PercentageAdapter;


public class SalesTax extends Fragment {
    Button reset;
    EditText salePercentage,netPrice,grossPrice,taxAmount;
    RecyclerView recyclerView;
    ImageView add,lock1,lock2,lock3,lock4;
    TextView netText,taxText, grossText;
    boolean lockPressed=false;
    percentageTsgList pList;
    PercentageAdapter percentageAdapter;
    GridLayoutManager gridLayoutManager1;
    int calculatingPart=0,whichLock=0;
    Decimals decimals=new Decimals();

    //calculatingPart 0-> empty 1-> sale&Net 2-> sale$gross 3->sale&tax 4->net&gross 5-> net&tax 6->tax&gross
    public SalesTax() {
        // Required empty public constructor
    }


    private void calculation1() {
        try {
            double n = Double.parseDouble(grossPrice.getText().toString()) - Double.parseDouble(taxAmount.getText().toString());
            double s = 100 * Double.parseDouble(taxAmount.getText().toString()) / n;
            netPrice.setText(String.valueOf(decimals.roundOfTo(n)));
            salePercentage.setText(String.valueOf(decimals.roundOfTo(s)));
        }catch (Exception e){

        }
    }
    private void calculation2() {
        try {
            double g = Double.parseDouble(taxAmount.getText().toString()) + Double.parseDouble(netPrice.getText().toString());
            double s = 100 * Double.parseDouble(taxAmount.getText().toString()) / Double.parseDouble(netPrice.getText().toString());
            salePercentage.setText(String.valueOf(decimals.roundOfTo(s)));
            grossPrice.setText(String.valueOf(decimals.roundOfTo(g)));
        }catch (Exception e){

        }
    }
    private void calculation3(){
        try {
            double t = Double.parseDouble(grossPrice.getText().toString()) - Double.parseDouble(netPrice.getText().toString());
            double s = 100 * t / Double.parseDouble(netPrice.getText().toString());
            salePercentage.setText(String.valueOf(decimals.roundOfTo(s)));
            taxAmount.setText(String.valueOf(decimals.roundOfTo(t)));
        }catch (Exception e){

        }
    }
    private void calculation4(){
        try {
            double n = 100 * Double.parseDouble(taxAmount.getText().toString()) / Double.parseDouble(salePercentage.getText().toString());
            double g = Double.parseDouble(taxAmount.getText().toString()) + n;
            netPrice.setText(String.valueOf(decimals.roundOfTo(n)));
            grossPrice.setText(String.valueOf(decimals.roundOfTo(g)));
        }catch (Exception e){

        }
    }
    private void calculation5(){
        try {
            double n=(Double.parseDouble(grossPrice.getText().toString())*100)/(100+Double.parseDouble(salePercentage.getText().toString()));
            double t=Double.parseDouble(grossPrice.getText().toString())-n;
            netPrice.setText(String.valueOf(decimals.roundOfTo(n)));
            taxAmount.setText(String.valueOf(decimals.roundOfTo(t)));
        } catch (Exception e){

        }
    }
    private void calculation6(){
        try {
            double t=Double.parseDouble(netPrice.getText().toString())*Double.parseDouble(salePercentage.getText().toString())/100;
            double g=Double.parseDouble(netPrice.getText().toString())+t;
            taxAmount.setText(String.valueOf(decimals.roundOfTo(t)));
            grossPrice.setText(String.valueOf(decimals.roundOfTo(g)));
        }catch (Exception e){

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_sales_tax, container, false);
        reset=view.findViewById(R.id.reset);
        salePercentage=view.findViewById(R.id.salePercentage);
        netPrice=view.findViewById(R.id.netPrice);
        grossPrice=view.findViewById(R.id.grossPrice);
        taxAmount=view.findViewById(R.id.taxAmount);
        recyclerView=view.findViewById(R.id.percentageList);
        add=view.findViewById(R.id.add);
        netText=view.findViewById(R.id.netPriceText);
        grossText=view.findViewById(R.id.grossPriceText);
        taxText=view.findViewById(R.id.taxAmountText);
        lock1=view.findViewById(R.id.lock1);
        lock2=view.findViewById(R.id.lock2);
        lock3=view.findViewById(R.id.lock3);
        lock4=view.findViewById(R.id.lock4);
        pList=new percentageTsgList(getContext());
        netText.setText("Net Price "+ Constants.CURRENCY_VALUE);
        grossText.setText("Gross Price "+ Constants.CURRENCY_VALUE);
        taxText.setText("Tax Amount "+ Constants.CURRENCY_VALUE);
        updateList();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //locking system
        lock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(whichLock==1){
                    lockPressed=false;
                    whichLock=0;
                    salePercentage.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    Drawable buttonDrawable = lock1.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock1. setBackground(buttonDrawable);
                }else{
                    whichLock=1;
                    if(salePercentage.getText().toString().isEmpty()){
                        salePercentage.setText("0");
                    }
                    netPrice.setEnabled(true);
                    grossPrice.setEnabled(true);
                    taxAmount.setEnabled(true);
                    lockPressed=true;
                    salePercentage.setEnabled(false);
                    recyclerView.setVisibility(View.GONE);
                    add.setVisibility(View.GONE);
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
                    netPrice.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    Drawable buttonDrawable = lock2.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock2. setBackground(buttonDrawable);
                }else{
                    whichLock=2;
                    if(netPrice.getText().toString().isEmpty()){
                        netPrice.setText("0");
                    }
                    salePercentage.setEnabled(true);
                    grossPrice.setEnabled(true);
                    taxAmount.setEnabled(true);
                    lockPressed=true;
                    netPrice.setEnabled(false);
                    recyclerView.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
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
                    grossPrice.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    Drawable buttonDrawable = lock3.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock3. setBackground(buttonDrawable);
                }else{
                    whichLock=3;
                    if(grossPrice.getText().toString().isEmpty()){
                        grossPrice.setText("0");
                    }
                    salePercentage.setEnabled(true);
                    netPrice.setEnabled(true);
                    taxAmount.setEnabled(true);
                    lockPressed=true;
                    grossPrice.setEnabled(false);
                    recyclerView.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    lockBackGround(3);
                }

            }
        });
        lock4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(whichLock==4){
                    lockPressed=false;
                    whichLock=0;
                    taxAmount.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    Drawable buttonDrawable = lock4.getBackground();
                    buttonDrawable = DrawableCompat. wrap(buttonDrawable);
                    DrawableCompat. setTint(buttonDrawable, Color.parseColor("#c3c3c3"));
                    lock4. setBackground(buttonDrawable);
                }else{
                    whichLock=4;
                    if(taxAmount.getText().toString().isEmpty()){
                        taxAmount.setText("0");
                    }
                    salePercentage.setEnabled(true);
                    grossPrice.setEnabled(true);
                    netPrice.setEnabled(true);
                    lockPressed=true;
                    taxAmount.setEnabled(false);
                    recyclerView.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    lockBackGround(4);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockBackGround(0);
                lockPressed=false;
                netPrice.setText("");
                salePercentage.setText("");
                grossPrice.setText("");
                taxAmount.setText("");
                netPrice.setEnabled(true);
                salePercentage.setEnabled(true);
                grossPrice.setEnabled(true);
                taxAmount.setEnabled(true);
                add.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        //Calculating system
        salePercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(salePercentage.hasFocus()){
                    if(!salePercentage.getText().toString().isEmpty()&&(!netPrice.getText().toString().isEmpty()||!grossPrice.getText().toString().isEmpty()||!taxAmount.getText().toString().isEmpty())) {
                        if(lockPressed){
                            if(whichLock==2){
                                calculatingPart=6;
                                calculation6();
                            }else if(whichLock==3){
                                calculatingPart=5;
                                calculation5();
                            }else if(whichLock==4){
                                calculatingPart=4;
                                calculation4();
                            }
                        }
                        else{
                            if(calculatingPart==0){
                                if(!netPrice.getText().toString().isEmpty()){
                                    calculatingPart=6;
                                    calculation6();
                                }else if(!grossPrice.getText().toString().isEmpty()){
                                    calculatingPart=5;
                                    calculation5();
                                }else if(!taxAmount.getText().toString().isEmpty()){
                                    calculatingPart=4;
                                    calculation4();
                                }
                            }else if(calculatingPart==6){
                                if(!netPrice.getText().toString().isEmpty()) {
                                    calculatingPart = 6;
                                    calculation6();
                                }else {
                                    calculateForEmpty(1);
                                }
                            }else if(calculatingPart==5){
                                if(!grossPrice.getText().toString().isEmpty()) {
                                    calculatingPart = 5;
                                    calculation5();
                                }else{
                                    calculateForEmpty(1);
                                }
                            }else if(calculatingPart==4){
                                if(!taxAmount.getText().toString().isEmpty()) {
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
        netPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                double s,g,t;
                if(netPrice.hasFocus()){
                    if(!netPrice.getText().toString().isEmpty()&&(!salePercentage.getText().toString().isEmpty()||!grossPrice.getText().toString().isEmpty()||!taxAmount.getText().toString().isEmpty())) {
                        if(lockPressed){
                            ///lock is pressed
                            if(whichLock==1){
                                calculatingPart=6;
                                calculation6();
                            }else if(whichLock==3){
                                calculatingPart=3;
                                calculation3();
                            }else if(whichLock==4){
                                calculatingPart=2;
                                calculation2();
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!salePercentage.getText().toString().isEmpty()){
                                    calculatingPart=6;
                                    calculation6();
                                }else if(!grossPrice.getText().toString().isEmpty()){
                                    calculatingPart=3;
                                    calculation3();
                                }else if(!taxAmount.getText().toString().isEmpty()){
                                    calculatingPart=2;
                                    calculation2();
                                }else{
                                    calculateForEmpty(2);
                                }
                            }else if(calculatingPart==6){
                                if(!salePercentage.getText().toString().isEmpty()){
                                    calculatingPart=6;
                                    calculation6();
                                }else{
                                    calculateForEmpty(2);
                                }

                            }else if(calculatingPart==3){
                                if(!grossPrice.getText().toString().isEmpty()){
                                    calculatingPart=3;
                                    calculation3();
                                }else{
                                    calculateForEmpty(2);
                                }

                            }else if(calculatingPart==2){
                                if(!taxAmount.getText().toString().isEmpty()){
                                    calculatingPart=2;
                                    calculation2();
                                }else{
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
        taxAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                double s,g,n;
                if(taxAmount.hasFocus()){
                    if(!taxAmount.getText().toString().isEmpty()&&(!salePercentage.getText().toString().isEmpty()||!grossPrice.getText().toString().isEmpty()||!netPrice.getText().toString().isEmpty())) {
                        if(lockPressed){
                            if(whichLock==1){
                                calculatingPart=4;
                                calculation4();
                            }else if(whichLock==2){
                                calculatingPart=2;
                                calculation2();
                            }else if(whichLock==3){
                                calculatingPart=1;
                                calculation1();
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!salePercentage.getText().toString().isEmpty()){
                                    calculatingPart=4;
                                    calculation4();
                                }else if(!grossPrice.getText().toString().isEmpty()){
                                    calculatingPart=1;
                                    calculation1();
                                }else if(!netPrice.getText().toString().isEmpty()){
                                    calculatingPart=2;
                                    calculation2();
                                }else{
                                    calculateForEmpty(4);
                                }
                            }else if(calculatingPart==4){
                                if(!salePercentage.getText().toString().isEmpty()){
                                    calculatingPart=4;
                                    calculation4();
                                }else{
                                    calculateForEmpty(4);
                                }

                            }else if(calculatingPart==1){
                                if(!grossPrice.getText().toString().isEmpty()){
                                    calculatingPart=1;
                                    calculation1();
                                }else{
                                    calculateForEmpty(4);
                                }

                            }else if(calculatingPart==2){
                                if(!netPrice.getText().toString().isEmpty()){
                                    calculatingPart=2;
                                    calculation2();
                                }else{
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
        grossPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                double s,t,n;
                if(grossPrice.hasFocus()){
                    if(!grossPrice.getText().toString().isEmpty()&&(!salePercentage.getText().toString().isEmpty()||!taxAmount.getText().toString().isEmpty()||!netPrice.getText().toString().isEmpty())) {
                        if(lockPressed){
                            if(whichLock==1){
                                calculatingPart=5;
                                calculation5();
                            }else if(whichLock==2){
                                calculatingPart=3;
                                calculation3();
                            }else if(whichLock==4){
                                calculatingPart=1;
                                calculation1();
                            }
                        }else{
                            if(calculatingPart==0){
                                if(!salePercentage.getText().toString().isEmpty()){
                                    calculatingPart=5;
                                    calculation5();
                                }else if(!taxAmount.getText().toString().isEmpty()){
                                    calculatingPart=1;
                                    calculation1();
                                }else if(!netPrice.getText().toString().isEmpty()){
                                    calculatingPart=3;
                                    calculation3();
                                }else {
                                    calculateForEmpty(3);
                                }
                            }else if(calculatingPart==5){
                                if(!salePercentage.getText().toString().isEmpty()){
                                    calculatingPart=5;
                                    calculation5();
                                }else{
                                    calculateForEmpty(3);
                                }

                            }else if(calculatingPart==1){
                                if(!taxAmount.getText().toString().isEmpty()){
                                    calculatingPart=1;
                                    calculation1();
                                }else{
                                    calculateForEmpty(3);
                                }

                            }else if(calculatingPart==3){
                                if(!netPrice.getText().toString().isEmpty()){
                                    calculatingPart=3;
                                    calculation3();
                                }else{
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


        return view;
    }

    private void calculateForEmpty(int position) {
        boolean s=salePercentage.getText().toString().isEmpty();
        boolean n=netPrice.getText().toString().isEmpty();
        boolean g=grossPrice.getText().toString().isEmpty();
        boolean t=taxAmount.getText().toString().isEmpty();

        if(position==1){
            if(!n){
                calculatingPart=6;
                calculation6();
            }else if(!g){
                calculatingPart=5;
                calculation5();
            }else if(!t){
                calculatingPart=4;
                calculation4();
            }else {
                calculatingPart=0;
            }
        }else if(position==2){
            if(!s){
                calculatingPart=6;
                calculation6();
            }else if(!g){
                calculatingPart=3;
                calculation3();
            }else if(!t){
                calculatingPart=2;
                calculation2();
            }else {
                calculatingPart=0;
            }
        }else if(position==3){

            if(!n){
                calculatingPart=3;
                calculation3();
            }else if(!s){
                calculatingPart=5;
                calculation5();
            }else if(!t){
                calculatingPart=1;
                calculation1();
            }else {
                calculatingPart=0;
            }
        }else if (position==4){

            if(!n){
                calculatingPart=2;
                calculation2();
            }else if(!g){
                calculatingPart=1;
                calculation1();
            }else if(!s){
                calculatingPart=4;
                calculation4();
            }else {
                calculatingPart=0;
            }
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
    }

    public void updateList(){

        percentageAdapter = new PercentageAdapter(getContext(), percentageTsgList.TAG, new PercentageAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Double object) {
                salePercentage.setText(String.valueOf(object));
            }

            @Override
            public void update() {
                updateList();
            }
        });
        gridLayoutManager1 = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager1);
        recyclerView.setAdapter(percentageAdapter);

    }
}