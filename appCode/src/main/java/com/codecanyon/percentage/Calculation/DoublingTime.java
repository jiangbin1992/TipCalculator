package com.codecanyon.percentage.Calculation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;

public class DoublingTime extends Fragment {

    EditText doublingTime,increase;
    Button reset;

    public DoublingTime() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_doubling_time, container, false);
        doublingTime=view.findViewById(R.id.period);
        increase=view.findViewById(R.id.increase);
        reset=view.findViewById(R.id.reset);
        Decimals decimals=new Decimals();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doublingTime.setText("");
                increase.setText("");
            }
        });
        increase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {

                    String increasePercentage=increase.getText().toString();
                    if(increase.hasFocus()&&!increasePercentage.isEmpty()){
                        double doublingPeriod=Math.log(2)/(Math.log(1+Double.parseDouble(increasePercentage)/100));
                        doublingTime.setText(""+decimals.roundOfTo(doublingPeriod));
                    }
                }catch (Exception e){

                }
            }
        });
        doublingTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    String doublingPeriod = doublingTime.getText().toString();
                    if (doublingTime.hasFocus() && !doublingPeriod.isEmpty()) {
                        double increasePercentage = Math.pow(2, -Double.parseDouble(doublingPeriod)) - 1;
                        increase.setText("" + decimals.roundOfTo(increasePercentage));
                    }
                }catch (Exception e){

                }
            }
        });

        return view;
    }
}