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

public class CgpaToPercentage extends Fragment {

    EditText percentage,cgpa;
    Button reset;

    public CgpaToPercentage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_cgpa_to_percentage, container, false);
        percentage=view.findViewById(R.id.percentage);
        cgpa=view.findViewById(R.id.cgpa);
        reset=view.findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percentage.setText("");
                cgpa.setText("");
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
                if (percentage.hasFocus()&&!percentage.getText().toString().isEmpty()){
                    Decimals decimals=new Decimals();
                    double cgpaDouble=decimals.roundOfTo(Double.parseDouble(percentage.getText().toString())/9.5);
                    cgpa.setText(cgpaDouble+"");
                }
            }
        });
        cgpa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (cgpa.hasFocus()&&!cgpa.getText().toString().isEmpty()){
                    Decimals decimals=new Decimals();
                    double percentageDouble=decimals.roundOfTo(Double.parseDouble(cgpa.getText().toString())*9.5);
                    percentage.setText(percentageDouble+"");
                }
            }
        });

        return view;
    }
}