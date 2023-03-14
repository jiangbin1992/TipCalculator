package com.codecanyon.percentage.Calculation;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;

public class Tip extends Fragment {

    EditText bill,tipPercent,numberOfPeople;
    Button reset,calculate;
    TextView tipPerPerson,totalPerPerson,tipAmount,totalAmount,billText;
    Decimals decimals;
    NestedScrollView nestedScrollView;

    public Tip() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tip, container, false);
        bill=view.findViewById(R.id.bill);
        tipPercent=view.findViewById(R.id.tip);
        numberOfPeople=view.findViewById(R.id.numberOfPeople);
        tipPerPerson=view.findViewById(R.id.tipPerPerson);
        totalPerPerson=view.findViewById(R.id.totalPerPerson);
        totalAmount=view.findViewById(R.id.total);
        tipAmount=view.findViewById(R.id.tipAmount);
        reset=view.findViewById(R.id.reset);
        billText=view.findViewById(R.id.billText);
        decimals=new Decimals();
        calculate=view.findViewById(R.id.calculate);
        nestedScrollView=view.findViewById(R.id.scroller);
        billText.setText("Bill "+Constants.CURRENCY_VALUE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String billString=bill.getText().toString();
                String tipString=tipPercent.getText().toString();
                String numberOfPeopleString=numberOfPeople.getText().toString();
                if(billString.isEmpty()||tipString.isEmpty()||numberOfPeopleString.isEmpty()){
                    Toast.makeText(getActivity(),"Enter all required values",Toast.LENGTH_SHORT).show();
                }else {
                    nestedScrollView.smoothScrollBy(0,800);
                    double tipAmountValue=Double.parseDouble(tipString)*Double.parseDouble(billString)/100;
                    double totalValue=tipAmountValue+Double.parseDouble(billString);
                    double tipPerPersonValue=tipAmountValue/Double.parseDouble(numberOfPeopleString);
                    double totalPerPersonValue=totalValue/Double.parseDouble(numberOfPeopleString);
                    tipAmount.setText(decimals.roundOfTo(tipAmountValue)+" "+ Constants.CURRENCY_VALUE);
                    totalAmount.setText(decimals.roundOfTo(totalValue)+" "+ Constants.CURRENCY_VALUE);
                    tipPerPerson.setText(decimals.roundOfTo(tipPerPersonValue)+" "+Constants.CURRENCY_VALUE);
                    totalPerPerson.setText(decimals.roundOfTo(totalPerPersonValue)+" "+Constants.CURRENCY_VALUE);
                }
            }
        });
        return view;
    }
}