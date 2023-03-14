package com.codecanyon.percentage.Calculation;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codecanyon.percentage.Backend.percentageTsgList;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;
import com.codecanyon.percentage.Supporting.PercentageAdapter;

public class ChangeinPercent extends Fragment {
    EditText enteringValue,changeInPercent;
    TextView percentToogle,incrementToogle,changeToogle,incrementedValue,incrementValue;
    Button reset,calculate;
    RecyclerView recyclerView;
    ImageView add;
    PercentageAdapter percentageAdapter;
    GridLayoutManager gridLayoutManager1;
    percentageTsgList pList;
    boolean incrementBool=true;

    public ChangeinPercent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_changein_percent, container, false);
        enteringValue=view.findViewById(R.id.value);
        changeInPercent=view.findViewById(R.id.percentage);
        percentToogle=view.findViewById(R.id.percentToggle);
        incrementToogle=view.findViewById(R.id.toggleIncrement);
        changeToogle=view.findViewById(R.id.toggleChange);
        NestedScrollView nestedScrollView=view.findViewById(R.id.scroller);
        incrementedValue=view.findViewById(R.id.incrementedValue);
        incrementValue=view.findViewById(R.id.incrementValue);
        reset=view.findViewById(R.id.reset);

        calculate=view.findViewById(R.id.calculate);

        add=view.findViewById(R.id.add);
        recyclerView=view.findViewById(R.id.percentageList);

        pList=new percentageTsgList(getContext());
        updateList();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteringValue.setText("");
                changeInPercent.setText("");
                incrementedValue.setText("");
                incrementValue.setText("");
            }
        });
        percentToogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incrementBool){
                    incrementBool=false;
                    percentToogle.setText("% Decrement");
                }else{
                    incrementBool=true;
                    percentToogle.setText("% Increment");
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enteringValue.getText().toString().isEmpty()&&!changeInPercent.getText().toString().isEmpty()){
                    nestedScrollView.smoothScrollTo(0,1800);
                    Decimals decimals=new Decimals();
                    double eValue=Double.parseDouble(enteringValue.getText().toString());
                    double difPer=Double.parseDouble(changeInPercent.getText().toString());

                    double differenceValue=difPer*eValue/100;
                    incrementValue.setText(decimals.roundOfToTwo(differenceValue)+"");
                    if(incrementBool){
                        incrementToogle.setText("Incremented Value");
                        changeToogle.setText("Value that is Incremented");
                        incrementedValue.setText(""+decimals.roundOfToTwo(eValue+differenceValue));
                    }else{
                        incrementToogle.setText("Decreased Value");
                        changeToogle.setText("Value that is Decreased");
                        incrementedValue.setText(""+decimals.roundOfToTwo(eValue-differenceValue));
                    }
                }else{
                    Toast.makeText(getActivity(),"Enter the Value",Toast.LENGTH_SHORT).show();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!changeInPercent.getText().toString().isEmpty()){
                    if(pList.addList(Double.parseDouble(changeInPercent.getText().toString()))) {
                       updateList();
                    }
                }else{
                    Toast.makeText(getActivity(),"Enter Change in Percentage",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void updateList(){

        percentageAdapter = new PercentageAdapter(getContext(), percentageTsgList.TAG, new PercentageAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Double object) {
                changeInPercent.setText(String.valueOf(object));
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