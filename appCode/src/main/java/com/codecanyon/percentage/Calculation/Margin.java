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

public class Margin extends Fragment {
    EditText cost,margin,sale;
    Button reset,calculate;
    TextView markUp,netPrice,grossPrice,profit,costText;
    Decimals decimals=new Decimals();
    NestedScrollView nestedScrollView;


    public Margin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_margin, container, false);
        cost=view.findViewById(R.id.cost);
        margin=view.findViewById(R.id.margin);
        sale=view.findViewById(R.id.salesTax);
        costText=view.findViewById(R.id.costText);
        reset=view.findViewById(R.id.reset);
        calculate=view.findViewById(R.id.calculate);
        markUp=view.findViewById(R.id.markUp);
        netPrice=view.findViewById(R.id.netPrice);
        grossPrice=view.findViewById(R.id.grossPrice);
        profit=view.findViewById(R.id.profit);
        nestedScrollView=view.findViewById(R.id.scroller);
        costText.setText("Cost "+Constants.CURRENCY_VALUE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cost.setText("");
                margin.setText("");
                sale.setText("");
                netPrice.setText("");
                grossPrice.setText("");
                profit.setText("");
                markUp.setText("");
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String costString=cost.getText().toString();
                String marginString=margin.getText().toString();
                String saleString=sale.getText().toString();

                if (costString.isEmpty()||marginString.isEmpty()||saleString.isEmpty()){
                    Toast.makeText(getActivity(),"Enter all the value",Toast.LENGTH_SHORT).show();
                }else{
                    nestedScrollView.smoothScrollBy(0,800);
                    double markUpValue=100/(100/Double.valueOf(marginString)-1);
                    double profitValue=Double.parseDouble(costString)*markUpValue/100;
                    double netPriceValue=Double.parseDouble(costString)+profitValue;
                    double grossPriceValue=netPriceValue+netPriceValue*Double.valueOf(saleString)/100;

                    markUp.setText(decimals.roundOfToTwo(markUpValue)+" %");
                    profit.setText(decimals.roundOfToTwo(profitValue)+" "+ Constants.CURRENCY_VALUE);
                    netPrice.setText(decimals.roundOfToTwo(netPriceValue)+" "+Constants.CURRENCY_VALUE);
                    grossPrice.setText(decimals.roundOfToTwo(grossPriceValue)+" "+Constants.CURRENCY_VALUE);

                }
            }
        });


        return view;
    }
}