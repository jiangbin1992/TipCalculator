package com.codecanyon.percentage.Calculation;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.codecanyon.percentage.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ScientificCalculator extends Fragment {

    Button one,two,three,four,five,six,seven,eight,nine,zero,allClear,cancel,braket,trigonometry,constant,percentage,factorial,loge,log,raiseTo,sqrt,
            add,subtract,multiplication,division,equal,dot,degButton;
    TextView text,ans;
    String number="",total="",errormsg="",appLink;
    String [] terms=new String[100];
    String [] copy=new String[100];
    int t=0;
    String digit="";
    double store=0;
    boolean error=false;
    int deg=1;
    int braces=0;


    public ScientificCalculator() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scientific_calculator, container, false);
        initialize(view);
        buttonOnClickListner();

        return view;
    }

    private void buttonOnClickListner() {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("1");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("2");}
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("3");
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("4");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("5");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("6");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("9");
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed(".");
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equal();
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("0");
            }
        });
        braket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced("()");
            }
        });
        allClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                longclick();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
        cancel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longclick();
                return false;
            }
        });
        trigonometry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listing("trigo");
            }
        });
        constant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listing("constant");
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solve("/");
            }
        });
        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solve("*");
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitPressed("-");
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solve("+");
            }
        });


        degButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deg==1) {
                    deg=0;
                    degButton.setText("RAD");
                }
                else{
                    deg=1;
                    degButton.setText("DEG");
                }
                if(t>0&&!total.isEmpty()){
                    for(int i=0;i<t+1;i++){
                        copy[i]=terms[i];
                    }
                    calculation(t+1);
                }
            }
        });

        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced("%");
            }
        });

        factorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced("!");
            }
        });
        loge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced("ln");
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced("log");
            }
        });
        raiseTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced("^");
            }
        });

        sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advanced("sqrt");
            }
        });

    }

    private void initialize(View view) {
        one=view.findViewById(R.id.di1Button);
        two=view.findViewById(R.id.di2Button);
        three=view.findViewById(R.id.di3Button);
        four=view.findViewById(R.id.di4Button);
        five=view.findViewById(R.id.di5Button);
        six=view.findViewById(R.id.di6Button);
        seven=view.findViewById(R.id.di7Button);
        eight=view.findViewById(R.id.di8Button);
        nine=view.findViewById(R.id.di9Button);

        sqrt=view.findViewById(R.id.sqrtButton);
        trigonometry=view.findViewById(R.id.trigoButton);
        cancel=view.findViewById(R.id.cancelButton);
        allClear=view.findViewById(R.id.allClear);
        dot=view.findViewById(R.id.didotButton);
        zero=view.findViewById(R.id.di0Button);
        equal=view.findViewById(R.id.diequalButton);
        add=view.findViewById(R.id.addButton);
        subtract=view.findViewById(R.id.subButton);
        division=view.findViewById(R.id.divButton);
        multiplication=view.findViewById(R.id.mulButton);
        constant=view.findViewById(R.id.constantButton);
        degButton=view.findViewById(R.id.degButton);
        braket=view.findViewById(R.id.fbraceButton);

        percentage=view.findViewById(R.id.invButton);
        factorial=view.findViewById(R.id.factButton);
        loge=view.findViewById(R.id.lnButton);
        raiseTo=view.findViewById(R.id.raiseButton);
        log=view.findViewById(R.id.logButton);

        ans=view.findViewById(R.id.ansTextView);
        text=view.findViewById(R.id.solveTextView);
    }

    public void digitPressed(String digit){
        ans.setTextColor(Color.parseColor("#504F4F"));
        ans.setText(String.valueOf(""));
        char l= ' ';
        if(!(total.isEmpty())) {
            l = total.charAt(total.length() - 1);
        }
        if(!(total.isEmpty()) && (l != '*')&& (l != '(')&& (l != '^') && (l != '/') && (l != '-') && (l!='+')&& digit.contentEquals("-")){
            t++;
            terms[t]="+";
            number="";
            t++;
        }
        // h,R,G,F,g,k,m
        else if(l==')'||l=='e'||l=='h'||l=='R'||l=='G'||l=='F'||l=='g'||l=='k'||l=='m'||l=='%'||l=='!'){
            t++;
            terms[t]="*";
            t++;
        }
        if(l=='^'){
            t++;
            if(braces==0){
                error=false;
                errormsg="";
            }
        }
        if((number.isEmpty()&&digit.contentEquals("."))||(number.contentEquals("-")&&digit.contentEquals("."))){
            number="0";
        }
        number += digit;
        terms[t]=number;
        if(digit.charAt(0)!='-') {
            try {
                double value=Double.parseDouble(number);
            } catch (NumberFormatException e) {
                error = true;
                errormsg="Bad text";
            }
            if(t>0){
                for(int i=0;i<t+1;i++){
                    copy[i]=terms[i];
                }
                calculation(t+1);
            }
        }
        total +=digit;
        text.setText(String.valueOf(total));
    }



    public void calculation(int dl){
        String [] copydigit=new String[100];
        for(int i=0;i<dl;i++){
            copydigit[i]=copy[i];
        }
        if(!error) {
            //for braces

            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("(")) {
                    for (int y = z + 1; y < dl; y++) {
                        if (copydigit[y].contentEquals(")")) {
                            int ip = 0;
                            for (int xc = z + 1; xc < y; xc++) {
                                copy[ip] = copydigit[xc];
                                ip++;
                            }
                            calculation(y - (z + 1));
                            copydigit = reverse(z + 1, y, copydigit, dl);
                            copydigit[z] = String.valueOf(store);
                            if(z!=0){
                                if(copydigit[z-1].contentEquals("-")){
                                    copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                                }
                            }
                            dl = dl - (y - z);
                            break;
                        }
                    }
                }
            }

            for(int z=0;z<dl;z++) {
                if (copydigit[z].contentEquals("%")) {
                    copydigit[z - 1] = String.valueOf(Double.parseDouble(copydigit[z - 1]) * 0.01);
                    copydigit = reverse(z,z, copydigit, dl);
                    dl -= 1;
                    z--;
                }
                else if(copydigit[z].contentEquals("^")) {
                    copydigit[z - 1] = String.valueOf(Math.pow(Double.parseDouble(copydigit[z - 1]),Double.parseDouble(copydigit[z + 1])));
                    copydigit = reverse(z,z+1, copydigit, dl);

                    dl -= 2;
                    z--;
                }
                else if(copydigit[z].contentEquals("!")){
                    int num=Integer.parseInt(copydigit[z-1]);
                    if(num==Double.parseDouble(copydigit[z-1])){
                        int numerical=Integer.parseInt(copydigit[z-1]);
                        if(numerical<0){
                            numerical*=-1;
                        }
                        double fact=1;
                        for(int i=1;i<=numerical;i++){
                            fact=fact*i;
                        }
                        copydigit[z-1]=String.valueOf(fact);
                        copydigit = reverse(z,z, copydigit, dl);
                        dl -= 1;
                        z--;
                    }
                    else{
                        errormsg="Factorial error";
                        error=true;
                    }
                }
            }
        }

        //sin cos sin- cos- tan- sinh cosh tanh

        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("sin")) {
                    if(deg==0){
                        copydigit[z] = String.valueOf(Math.sin(Double.parseDouble(copydigit[z+1])));
                    }
                    else{
                        copydigit[z] = String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(copydigit[z+1]))));
                    }
                    copydigit = reverse(z+1, z+1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                        }
                    }
                    dl -=1;
                    z--;

                }else if(copydigit[z].contentEquals("cos")){
                    if(deg==0){
                        copydigit[z] = String.valueOf(Math.cos(Double.parseDouble(copydigit[z+1])));
                    }
                    else{
                        copydigit[z] = String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(copydigit[z+1]))));
                    }
                    copydigit = reverse(z+1, z+1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                        }
                    }
                    dl -=1;
                    z--;
                }
                else if (copydigit[z].contentEquals("tan-1")) {
                    if(deg==0){
                        copydigit[z] = String.valueOf(Math.atan(Double.parseDouble(copydigit[z+1])));
                    }
                    else{
                        copydigit[z] = String.valueOf(Math.atan(Double.parseDouble(copydigit[z + 1])));
                        copydigit[z]=String.valueOf(Math.toDegrees(Double.parseDouble(copydigit[z])));
                    }
                    copydigit = reverse(z+1, z+1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                        }
                    }
                    dl -=1;
                    z--;

                }
            }
        }


        if(!error) {
            for (int z = 0; z < dl; z++) {
                 if (copydigit[z].contentEquals("tan")) {
                    if (deg == 0) {
                        copydigit[z] = String.valueOf(Math.tan(Double.parseDouble(copydigit[z + 1])));
                    } else {
                        copydigit[z] = String.valueOf(Math.tan(Math.toRadians(Double.parseDouble(copydigit[z + 1]))));
                    }
                    copydigit = reverse(z + 1, z + 1, copydigit, dl);
                    if(z!=0){
                        if(copydigit[z-1].contentEquals("-")){
                            copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                        }
                    }
                    dl -= 1;
                    z--;
                }
            }
        }
        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("sin-1")) {
                    if((Double.parseDouble(copydigit[z + 1])>=-1)&&(Double.parseDouble(copydigit[z + 1])<=1)) {
                        if (deg == 0) {
                            copydigit[z] = String.valueOf(Math.asin(Double.parseDouble(copydigit[z + 1])));
                        } else {
                            copydigit[z] = String.valueOf(Math.asin(Double.parseDouble(copydigit[z + 1])));
                            copydigit[z]=String.valueOf(Math.toDegrees(Double.parseDouble(copydigit[z])));
                        }
                        copydigit = reverse(z + 1, z + 1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                            }
                        }
                        dl -= 1;
                        z--;
                    }else{
                        errormsg="domain error";
                        error=true;
                    }
                }
            }
        }
        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("cos-1")) {
                    if((Double.parseDouble(copydigit[z + 1])>=-1)&&(Double.parseDouble(copydigit[z + 1])<=1)) {
                        if (deg == 0) {
                            copydigit[z] = String.valueOf(Math.acos(Double.parseDouble(copydigit[z + 1])));
                        } else {
                            copydigit[z] = String.valueOf(Math.acos(Double.parseDouble(copydigit[z + 1])));
                            copydigit[z]=String.valueOf(Math.toDegrees(Double.parseDouble(copydigit[z])));
                        }
                        copydigit = reverse(z + 1, z + 1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                            }
                        }
                        dl -= 1;
                        z--;
                    }
                    else{
                        errormsg="domain error";
                        error=true;
                    }

                }
            }
        }

        if(!error) {
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("ln")) {
                    if(Double.parseDouble(copydigit[z+1])>0){
                        copydigit[z] = String.valueOf(Math.log(Double.parseDouble(copydigit[z+1])));
                        copydigit = reverse(z+1, z+1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                            }
                        }
                        dl -=1;
                        z--;
                    }
                    else{
                        error=true;
                        errormsg="domain error";
                    }
                }
            }
        }
        if(!error) {
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("sqrt")) {
                    if(Double.parseDouble(copydigit[z+1])>0){
                        copydigit[z] = String.valueOf(Math.sqrt(Double.parseDouble(copydigit[z+1])));
                        copydigit = reverse(z+1, z+1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                            }
                        }
                        dl -=1;
                        z--;
                    }
                    else{
                        error=true;
                        errormsg="domain error";
                    }
                }
            }
        }

        if(!error){
            for (int z = 0; z < dl; z++) {
                if (copydigit[z].contentEquals("log")) {
                    if(Double.parseDouble(copydigit[z+1])>0){
                        copydigit[z] = String.valueOf(Math.log10(Double.parseDouble(copydigit[z+1])));
                        copydigit = reverse(z+1, z+1, copydigit, dl);
                        if(z!=0){
                            if(copydigit[z-1].contentEquals("-")){
                                copydigit[z]=String.valueOf(-Double.parseDouble(copydigit[z]));
                            }
                        }
                        dl -=1;
                        z--;
                    }
                    else{
                        error=true;
                        errormsg="domain error";
                    }
                }
            }
        }

        if(!error){
            //for multiplication division
            for(int z=0;z<dl;z++){
                if(copydigit[z].contentEquals("*")){
                    copydigit[z-1]=String.valueOf(Double.parseDouble(copydigit[z-1]) * Double.parseDouble(copydigit[z+1]));
                    copydigit= reverse(z,z+1,copydigit,dl);
                    dl-=2;
                    z--;
                }
                else if(copydigit[z].contentEquals("/")){
                    if(copydigit[z+1].contentEquals("0")){
                        error=true;
                        errormsg="Not defined";
                        break;
                    }
                    else{
                        copydigit[z-1]=String.valueOf(Double.parseDouble(copydigit[z-1]) / Double.parseDouble(copydigit[z+1]));
                        copydigit= reverse(z,z+1,copydigit,dl);
                        dl-=2;
                        z--;
                    }
                }
            }
        }
        if(!error){
            //for addition
            for(int z=0;z<dl;z++){
                if(copydigit[z].contentEquals("+")){
                    copydigit[z-1]=String.valueOf(Double.parseDouble(copydigit[z-1]) + Double.parseDouble(copydigit[z+1]));
                    copydigit= reverse(z,z+1,copydigit,dl);
                    dl-=2;
                    z--;
                }
            }
            store = Double.parseDouble(copydigit[0]);
            if ((int) store == store) {
                int store = (int) this.store;
                ans.setText(String.valueOf(store));
            } else {
                if((int) store==0){
                    ans.setText(String.valueOf(store));
                }
                else{
                    BigDecimal bd = new BigDecimal(store).setScale(12, RoundingMode.HALF_UP);
                    store = bd.doubleValue();
                    ans.setText(String.valueOf(store));}
            }
        }
    }

    public String[] reverse(int s,int m,String a[],int l){
        m++;
        for(int r=s;m<l;r++){
            a[r]=a[m];
            m++;
        }
        return a;
    }

    public void equal(){
        if(!(error)) {
            if (!total.isEmpty()) {
                if(t>0) {
                    if ((int) store == store) {
                        int store = (int) this.store;
                        text.setText(String.valueOf(store));
                        number = Integer.toString(store);
                        total = Integer.toString(store);
                    }
                    else {
                        number = Double.toString(store);
                        text.setText(String.valueOf(store));
                        total = String.valueOf(store);
                    }
                    ans.setText(String.valueOf(""));
                    for(int b=0;b<t+1;b++){
                        terms[b]="n";
                    }
                    t = 0;
                    terms[t] = number;
                }
            }
        }
        else{
            ans.setTextColor(Color.parseColor("#ffcc0000"));
            ans.setText(String.valueOf(errormsg));
        }
    }

    public void clear(){
        if(!total.isEmpty()){
            char l = total.charAt(total.length() - 1);
            // h,R,G,F,g,k,m
            if(l!= 'e' && l != ')' && l != '('&& l != 'h' && l != 'R' && l != 'G' && l !='F' &&l != 'g' && l != 'k' && l != 'm' ) {
                if (l != '*' && l != '+' && l != '/' &&l != '%'&&l != '^'&&l != '!' && (l != '-' || total.length() == 1)) {
                     if (terms[t].length() >= 2) {
                        terms[t] = terms[t].substring(0, terms[t].length() - 1);
                        number = number.substring(0, number.length() - 1);
                        total=total.substring(0,total.length()-1);
                        if (terms[t].charAt(terms[t].length() - 1) != '-') {
                            if (t > 0) {
                                for (int i = 0; i < t + 1; i++) {
                                    copy[i] = terms[i];
                                }
                                calculation(t + 1);
                            }
                        } else {
                            ans.setText("");
                        }
                    } else {
                        total=total.substring(0,total.length()-1);
                        terms[t] = "n";
                        if(t!=0){
                            String la=terms[t-1];
                            if(la.contentEquals("e")||la.contentEquals(")")||la.contentEquals("h")||la.contentEquals("R")
                                    ||la.contentEquals("G")||la.contentEquals("F")||la.contentEquals("g")||la.contentEquals("k")||la.contentEquals("atm")){
                                t--;
                                terms[t]="n";
                                t--;
                            }
                            else if(la.contentEquals("^")){
                                error=true;
                                errormsg="Domain error";
                            }
                        }
                        number = "";
                        ans.setText("");
                    }
                } else {
                    if(l=='^'){
                        error=false;
                        errormsg="";
                    }
                    if (t >= 2) {
                        t--;
                        terms[t] = "n";
                        t--;
                    }
                    number = terms[t];
                    total=total.substring(0,total.length()-1);
                    if (t > 0) {
                        for (int i = 0; i < t + 1; i++) {
                            copy[i] = terms[i];
                        }
                        calculation(t + 1);
                    }
                }
            }
            else if (l == '(') {
                error=false;
                errormsg="";
                t--;
                braces--;
                total=total.substring(0,total.length()-1);
                terms[t]="n";
                if(t!=0) {
                    t--;
                    if (terms[t].contentEquals("sin") || terms[t].contentEquals("log") || terms[t].contentEquals("ln") || terms[t].contentEquals("sqrt")
                            || terms[t].contentEquals("cos") || terms[t].contentEquals("tan") || terms[t].contentEquals("sin-1") || terms[t].contentEquals("cos-1") || terms[t].contentEquals("tan-1")) {
                        if (terms[t].contentEquals("sqrt")) {
                            total = total.substring(0, total.length() - 4);
                        } else if (terms[t].contentEquals("ln")) {
                            total = total.substring(0, total.length() - 2);
                        } else if (terms[t].contentEquals("tan-1") || terms[t].contentEquals("cos-1") || terms[t].contentEquals("sin-1")) {
                            total = total.substring(0, total.length() - 5);
                        } else {
                            total = total.substring(0, total.length() - 3);
                        }
                        terms[t] = "n";
                        //
                        if (t != 0) {
                            t--;
                        }
                        //+/%^!-
                    }
                    char last = ' ';
                    if (!total.isEmpty()) {
                        last = total.charAt(total.length() - 1);
                    }
                    //*+/^%!-
                    if (last == '*' || last == '+' || last == '/' || last == '^' || last == '%' || last == '!' || last == '-') {
                        t++;
                    }else if (t >= 2) {
                        t--;
                        terms[t] = "n";
                        t--;
                        number = terms[t];
                    }
                }
            }
            else if (l == ')') {
                error=true;
                braces++;
                errormsg="Inappropriate braces";
                total = total.substring(0, total.length() - 1);
                terms[t]="n";
                t--;
                number=terms[t];
            }
            // h,R,G,F,g,k,m-101325
            else if(l=='e'||l=='R'||l=='G'||l=='F'||l=='g'||l=='k'||l=='m'||l=='h'){
                if(terms[t].contentEquals("3.141592653589")||terms[t].contentEquals("-3.141592653589")
                        ||terms[t].contentEquals("-101325")||terms[t].contentEquals("101325")){
                    total = total.substring(0, total.length() - 3);
                }else{
                    total = total.substring(0, total.length() - 1);
                }
                terms[t]="n";

                char last=' ';
                if(!total.isEmpty()) {
                    last = total.charAt(total.length() - 1);
                }
                //*+-(^
                if(last=='*'|| last=='+'||last=='/' ||last=='^'||last=='(' || last=='-') {
                    t--;
                    terms[t] = "n";
                    t--;
                    number=terms[t];
                }
            }
            text.setText(total);
            if(total.isEmpty()){
                ans.setText("");
            }
        }
        else{
            text.setText("");
            ans.setText("");
            error=false;
            braces=0;
            errormsg="";
        }
    }

    public void longclick(){
        text.setText(String.valueOf(""));
        ans.setText(String.valueOf(""));
        for(int b=0;b<t+1;b++){
            terms[b]="n";
        }
        number="";
        total="";
        braces=0;
        error=false;
        store = 0 ;

        t=0;
    }

    public void trigo(String data[]){
        char l = ' ';
        if(!total.isEmpty()){
            l = total.charAt(total.length()-1);
        }


        if(braces!=0){
            if( l != '*' && l != '/' && l != '-' && l != '+') {
            }
            else {
                terms[t]="0";
                t++;
                total+="0";
            }
            terms[t] = ")";
            total+= ")";
            braces--;
            t++;
        }
        else {
            if (!total.isEmpty() && l != '*' && l != '/' && l != '-' && l != '+') {
                t++;
                terms[t] = "*";
                number = "";
                t++;
            }
        }
        number="";
        terms[t]= data[0];
        total+= data[0] +"(";
        braces++;
        t++;
        terms[t]="(";
        t++;
        error=true;
        errormsg="Inappropriate braces";

        ans.setTextColor(Color.parseColor("#504F4F"));
        text.setText(total);

    }

    public void listing(String tag){
        try {
            final String[] data = new String[1];
            if (tag.contentEquals("trigo")) {
                final Dialog history = new Dialog(getContext());
                history.setContentView(R.layout.extra);
                history.setCancelable(true);
                history.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                history.setCanceledOnTouchOutside(true);
                history.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
                history.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                history.show();
                Button sin = history.findViewById(R.id.sin);
                Button cos = history.findViewById(R.id.cos);
                Button tan = history.findViewById(R.id.tan);
                Button sinin = history.findViewById(R.id.sinin);
                Button cosin = history.findViewById(R.id.cosin);
                Button tanin = history.findViewById(R.id.tanin);
                sin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = sin.getTag().toString();
                        trigo(data);
                        history.dismiss();

                    }
                });
                cos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        trigo(data);
                        history.dismiss();

                    }
                });
                tan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        trigo(data);
                        history.dismiss();

                    }
                });
                sinin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        trigo(data);
                        history.dismiss();


                    }
                });
                cosin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        trigo(data);
                        history.dismiss();

                    }
                });
                tanin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        trigo(data);
                        history.dismiss();
                    }
                });
                Button cancel = history.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        history.dismiss();
                    }
                });


            } else {

                final Dialog history = new Dialog(getContext());
                history.setContentView(R.layout.constant);
                history.setCancelable(true);
                history.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                history.setCanceledOnTouchOutside(true);
                history.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
                history.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                history.show();
                Button e = history.findViewById(R.id.e);
                Button pie = history.findViewById(R.id.pie);
                Button h = history.findViewById(R.id.h);
                Button r = history.findViewById(R.id.r);
                Button gcapital = history.findViewById(R.id.gcaptal);
                Button f = history.findViewById(R.id.f);
                Button g = history.findViewById(R.id.g);
                Button k = history.findViewById(R.id.k);
                Button atm = history.findViewById(R.id.atm);
                e.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();

                    }
                });
                pie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();

                    }
                });
                h.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();

                    }
                });
                r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();


                    }
                });
                gcapital.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();

                    }
                });
                f.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();
                    }
                });
                g.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();
                    }
                });
                k.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();
                    }
                });
                atm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = v.getTag().toString();
                        constant(data);
                        history.dismiss();
                    }
                });
                Button cancel = history.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        history.dismiss();
                    }
                });

            }

            ans.setTextColor(Color.parseColor("#504F4F"));
            text.setText(total);
        }catch(Exception e){
            Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void constant(String data[]){
        char l = ' ';
        if(!total.isEmpty()){
            l = total.charAt(total.length()-1);
        }

        if(!total.isEmpty()&&l!='*'&&l!='/'&&l!='-'&&l!='+'&&l!='('){
            t++;
            terms[t]="*";
            t++;
        }
        number="";
        if(l=='-'){
            if (data[0].contentEquals("h")) {
                terms[t] = "-6.62606E-34";
            }else if(data[0].contentEquals("e")){
                terms[t]="-2.718281828459";
            }
            else if(data[0].contentEquals("pie")){
                terms[t]="-3.141592653589";
            }
            else if (data[0].contentEquals("R")) {
                terms[t] = "-8.314472";
            } else if (data[0].contentEquals("G")) {
                terms[t] = ("-6.62606E-11");
            } else if (data[0].contentEquals("F")) {
                terms[t] = "-96485.3399";
            } else if (data[0].contentEquals("g")) {
                terms[t] = "-9.81";
            } else if (data[0].contentEquals("k")) {
                terms[t] = ("-1.3806E-23");
            } else if (data[0].contentEquals("atm")) {
                terms[t] = "-101325";
            }
        }
        else {
            if (data[0].contentEquals("h")) {
                terms[t] = ("6.62606E-34");
            }else if(data[0].contentEquals("e")){
                terms[t]="2.718281828459";
            }
            else if(data[0].contentEquals("pie")){
                terms[t]="3.141592653589";
            }
            else if (data[0].contentEquals("R")) {
                terms[t] = "8.314472";
            } else if (data[0].contentEquals("G")) {
                terms[t] = ("6.62606E-11");
            } else if (data[0].contentEquals("F")) {
                terms[t] = "96485.3399";
            } else if (data[0].contentEquals("g")) {
                terms[t] = "9.81";
            } else if (data[0].contentEquals("k")) {
                terms[t] = ("1.3806E-23");
            } else if (data[0].contentEquals("atm")) {
                terms[t] = "101325";
            }
        }
        total+= data[0];
        if(braces==0) {
            for (int i = 0; i < t + 1; i++) {
                copy[i] = terms[i];
            }
            calculation(t + 1);
        }

        ans.setTextColor(Color.parseColor("#504F4F"));
        text.setText(total);

    }

    public void advanced(String data){
        ans.setTextColor(Color.parseColor("#504F4F"));
        char l = ' ';
        if(!total.isEmpty()){
            l = total.charAt(total.length()-1);
        }

        //braces
        if(data.contentEquals("()")){
            if(braces==0) {
                if (!total.isEmpty() && l != '*' && l != '/' && l != '-' && l != '+') {
                    t++;
                    terms[t] = "*";
                    t++;
                }
                terms[t]="(";
                total+="(";
                braces++;
                t++;
                number="";
                error=true;
                errormsg="Inappropriate braces";
            }
            else{
                if (l!='(' && l != '*' && l != '/' && l != '-' && l != '+') {
                    error=false;
                    errormsg="";
                    t++;
                    number="";
                    terms[t] = ")";
                    total += ")";
                    braces--;
                    for (int i = 0; i < t + 1; i++) {
                        copy[i] = terms[i];
                    }
                    calculation(t + 1);
                }
            }
        }
        //% !
        else if(data.contentEquals("%")||data.contentEquals("!")||data.contentEquals("^")){
            if(!total.isEmpty()&&l!='*'&&l!='/'&&l!='-'&&l!='+') {
                t++;
                terms[t]=data;
                total += data;
                number = "";
                if(braces==0&&!data.contentEquals("^")) {
                    for (int i = 0; i < t + 1; i++) {
                        copy[i] = terms[i];
                    }
                    calculation(t + 1);
                }

            }
        }
        else if(data.contentEquals("ln")||data.contentEquals("log")||data.contentEquals("sqrt")){
            if(braces!=0){
                if( l != '*' && l != '/' && l != '-' && l != '+') {
                }
                else {
                    terms[t]="0";
                    t++;
                    total+="0";
                }
                terms[t] = ")";
                total+= ")";
                braces--;
                t++;
            }
            else {
                if (!total.isEmpty() && l != '*' && l != '/' && l != '-' && l != '+') {
                    t++;
                    terms[t] = "*";
                    number = "";
                    t++;
                }
            }
            number="";
            terms[t]=data;
            total+=data+"(";
            braces++;
            t++;
            terms[t]="(";
            t++;
            error=true;
            errormsg="Inappropriate braces";
        }
        text.setText(total);
    }


    public void solve(String a){
        if(!total.isEmpty()){
            char l = total.charAt(total.length()-1);
            //last update l!='!'&&l!='%'&&
            if(l!='^'&&l!='(') {
                if (l != '*' && l != '/' && l != '+' && l != '-') {
                    t++;
                    terms[t] = a;
                    total += a;
                    text.setText(total);
                    number = "";
                    t++;
                } else {
                    if (l != '-') {
                        t--;
                        terms[t] = a;
                        total = total.substring(0, total.length() - 1);
                        total += a;
                        text.setText(total);
                        t++;
                    }
                }
            }
        }
    }

}