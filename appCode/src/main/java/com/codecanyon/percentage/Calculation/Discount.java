package com.codecanyon.percentage.Calculation;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codecanyon.percentage.Backend.DiscountList;
import com.codecanyon.percentage.Backend.DiscountSaved;
import com.codecanyon.percentage.Constants;
import com.codecanyon.percentage.R;
import com.codecanyon.percentage.Supporting.Decimals;
import com.codecanyon.percentage.Supporting.DiscountAddAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Discount extends Fragment implements DiscountAddAdapter.AdapterCallback {

    EditText itemName,qty,actualValue,discountValue,overAllDiscount;
    TextView percentToggle,currencyToggle,priceOfSell,totalPrice,discountAmount,discountPercent,currency1,currency2;
    LinearLayout addItem,addDiscount;
    Button calculate,save,reset;
    Decimals decimals;
    GridLayoutManager gridLayoutManager1;
    RecyclerView recyclerView;
    DiscountAddAdapter discountAddAdapter;
    SQLiteDatabase myDatabase;
    DiscountList discount;
    SwitchCompat removeDiscount;
    boolean removeDiscountBoolean=false,percentageToggleBool=true;
    NestedScrollView nestedScrollView;

    boolean openSaved,percentageBool,addOverallDiscount;
    List<String> itemList=new ArrayList<>();
    List<String> actualList=new ArrayList<>();
    List<String>discountList=new ArrayList<>();
    String overallDiscount;
    public Discount(boolean openSaved,List<String> itemList,List<String> actualList,List<String> discountList
            ,boolean addOverallDiscount,String overallDiscount,boolean percentageBool) {
        // Required empty public constructor
        this.openSaved=openSaved;
        if(openSaved) {
            this.itemList = itemList;
            this.actualList = actualList;
            this.discountList = discountList;
            this.addOverallDiscount = addOverallDiscount;
            this.overallDiscount = overallDiscount;
            this.percentageBool = percentageBool;
        }
    }

    private void resetActivity(){
        DiscountList.discountValueList.clear();
        DiscountList.actualValueList.clear();
        DiscountList.quantityList.clear();
        DiscountList.itemNameList.clear();
        itemName.setText("");
        qty.setText("1");
        removeDiscount.setChecked(false);
        removeDiscountBoolean=false;
        actualValue.setText("");
        discountValue.setText("");
        overAllDiscount.setText("");
        updateList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_discount, container, false);
        initialization(view);
        resetActivity();
        nestedScrollView=view.findViewById(R.id.scroller);
        discount=new DiscountList(getContext());
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               resetActivity();
            }
        });
        if(openSaved){
            reloadingData();
        }
        myDatabase= getActivity().openOrCreateDatabase("Discount",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS discountTable(itemNameArray TEXT,actualValueArray TEXT,discountValueArray TEXT,addOverAllDiscountBoolean INTEGER,overAllDiscount TEXT,percentageBoolean INTEGER,fileName TEXT,id INTEGER PRIMARY KEY)");

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculation();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qty.getText().toString().isEmpty()){
                    qty.setText("1");
                }
                if(!actualValue.getText().toString().isEmpty()&&!discountValue.getText().toString().isEmpty()){
                    fileName();
                }else{
                    Toast.makeText(getActivity(),"enter the necessary value",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(getString(R.string.version).equals("v.0.1")){
            save.setVisibility(View.GONE);

        }

        percentToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                percentToggle.setTextColor(Color.parseColor("#ffffff"));
                currencyToggle.setTextColor(Color.parseColor(("#000000")));
                percentToggle.setBackgroundResource(R.drawable.toggle_on);
                currencyToggle.setBackground(null);
                percentageToggleBool=true;
            }
        });
        currencyToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                percentToggle.setTextColor(Color.parseColor("#000000"));
                currencyToggle.setTextColor(Color.parseColor("#ffffff"));
                percentToggle.setBackground(null);
                currencyToggle.setBackgroundResource(R.drawable.toggle_on);
                percentageToggleBool=false;
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(discount.addValue("",1,0.0,0.0)){
                  updateList();
              }
            }
        });

        removeDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                removeDiscountBoolean=b;
                if(removeDiscountBoolean){
                    addDiscount.setVisibility(View.VISIBLE);
                }else {
                    addDiscount.setVisibility(View.GONE);
                }
                updateList();
            }
        });

        return view;
    }

    private void reloadingData() {
        itemName.setText(itemList.get(0));
        actualValue.setText(actualList.get(0));
        discountValue.setText(discountList.get(0));
        removeDiscountBoolean=addOverallDiscount;
        if(addOverallDiscount){
            overAllDiscount.setVisibility(View.VISIBLE);
            overAllDiscount.setText(overallDiscount);
            removeDiscount.setChecked(true);
        }else{
            removeDiscount.setChecked(false);
            overAllDiscount.setVisibility(View.GONE);
        }
        for(int i=1;i<itemList.size();i++){
            if(discount.addValue(itemList.get(i),1,Double.parseDouble(discountList.get(i)),Double.parseDouble(actualList.get(i)))){
                updateList();
            }
        }
    }


    private void fileName(){
        final Dialog dialog2 = new Dialog(getContext());
        dialog2.setContentView(R.layout.file_name);
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCanceledOnTouchOutside(true);
        dialog2.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        dialog2.show();

        EditText fileNameEditText=dialog2.findViewById(R.id.fileName);
        Button save=dialog2.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileNameString= fileNameEditText.getText().toString();
                if(fileNameString.trim().isEmpty()){
                    Toast.makeText(getActivity(),"Add file Name",Toast.LENGTH_SHORT).show();
                }else{
                    int i=0;
                    String newFileName=fileNameString;
                    while(findSavedName(newFileName)){
                        i++;
                        newFileName=fileNameString+" ("+String.valueOf(i)+")";
                    }
                    saveTheCal(newFileName);
                    dialog2.dismiss();
                }
            }
        });

    }

    private boolean findSavedName(String fileName){
        ///return true if file exit
        for(DiscountSaved discount:Constants.DISCOUNT_SAVED_LIST){
            if(discount.getFileName().equals(fileName)){
                return true;
            }
        }
        return false;
    }

    private void saveTheCal(String fileName) {
        // Create a new user with a first, middle, and last name
        String itemArrayName;
        if(itemName.getText().toString().isEmpty()){
            itemArrayName="Item Name"+Constants.DIVIDER;
        }else{
            itemArrayName=itemName.getText().toString()+Constants.DIVIDER;
        }

        for(String item:DiscountList.itemNameList){
            if(item.isEmpty()){
                itemArrayName += "item"+Constants.DIVIDER;
            }else {
                itemArrayName += item+Constants.DIVIDER;
            }

        }
        String actualArray=actualValue.getText().toString()+Constants.DIVIDER;
        for(double actual:DiscountList.actualValueList){
            actualArray += String.valueOf(actual)+Constants.DIVIDER;
        }
        String totalArray=discountValue.getText().toString()+Constants.DIVIDER;
        for(double total:DiscountList.discountValueList) {
            totalArray += String.valueOf(total) + Constants.DIVIDER;
        }
        int addOverAllDiscount=0;
        String overAllDiscountString="";
        if (removeDiscountBoolean){
            addOverAllDiscount=1;
            overAllDiscountString =overAllDiscount.getText().toString();
        }
        int percentageBoolean=percentageToggleBool?1:0;

        myDatabase.execSQL("INSERT INTO discountTable(itemNameArray,actualValueArray,discountValueArray,addOverAllDiscountBoolean,overAllDiscount,percentageBoolean,fileName) VALUES ('"
                + itemArrayName + "','" + actualArray + "','" + totalArray +
                "','" + addOverAllDiscount+ "','" + overAllDiscountString +"','" + percentageBoolean +"','"+fileName+ "')");

        DiscountSaved discountSaved=new DiscountSaved(fileName,actualArray,totalArray,itemArrayName,overAllDiscountString,percentageToggleBool,removeDiscountBoolean,1);
        Constants.DISCOUNT_SAVED_LIST.add(discountSaved);

    }


    private void calculation() {
        if(qty.getText().toString().isEmpty()){
            qty.setText("1");
        }
        if(!actualValue.getText().toString().isEmpty()&&!discountValue.getText().toString().isEmpty()){
            double totalPriceValue=Double.parseDouble(actualValue.getText().toString())*Double.parseDouble(qty.getText().toString());
            int i=0;
            for(double s: DiscountList.actualValueList){
                totalPriceValue+=DiscountList.quantityList.get(i)*s;
                i++;
            }
            nestedScrollView.smoothScrollBy(0,1800);
            if(removeDiscountBoolean){
                if (!overAllDiscount.getText().toString().isEmpty()) {
                    double d=Double.parseDouble(overAllDiscount.getText().toString());
                    if(percentageToggleBool){
                        double dv=d*totalPriceValue/100;
                        discountAmount.setText(""+decimals.roundOfToTwo(dv)+" "+Constants.CURRENCY_VALUE);
                        discountPercent.setText(""+decimals.roundOfToTwo(d)+" %");
                        priceOfSell.setText(""+decimals.roundOfToTwo(totalPriceValue-dv)+" "+Constants.CURRENCY_VALUE);
                    }else {

                        discountAmount.setText(""+decimals.roundOfToTwo(d)+" "+Constants.CURRENCY_VALUE);
                        discountPercent.setText(""+decimals.roundOfToTwo(d/totalPriceValue*100)+" %");
                        priceOfSell.setText(""+decimals.roundOfToTwo(totalPriceValue-d)+" "+Constants.CURRENCY_VALUE);
                    }
                }else {
                    Toast.makeText(getContext(),"Add the value of OverAll Discount",Toast.LENGTH_SHORT).show();
                }
            }else{


                i=0;
                double discountPriceValue=Double.parseDouble(discountValue.getText().toString());
                for(double s: DiscountList.discountValueList){
                    discountPriceValue+=s;
                    i++;
                }
                discountAmount.setText(""+decimals.roundOfToTwo(discountPriceValue)+" "+Constants.CURRENCY_VALUE);

                discountPercent.setText(""+decimals.roundOfToTwo(discountPriceValue/totalPriceValue*100)+" %");
                priceOfSell.setText(""+decimals.roundOfToTwo(totalPriceValue-discountPriceValue)+" "+Constants.CURRENCY_VALUE);
            }
            totalPrice.setText(""+decimals.roundOfToTwo(totalPriceValue)+" "+Constants.CURRENCY_VALUE);
        }
    }

    private void updateList() {
        discountAddAdapter =new DiscountAddAdapter(getContext(), DiscountList.itemNameList,false,this);
        gridLayoutManager1=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,removeDiscountBoolean);
        recyclerView.setLayoutManager(gridLayoutManager1);
        recyclerView.setAdapter(discountAddAdapter);
    }

    private void initialization(View view) {
        decimals=new Decimals();
        reset=view.findViewById(R.id.reset);
        calculate=view.findViewById(R.id.calculate);
        save=view.findViewById(R.id.save);
        addItem=view.findViewById(R.id.add);
        itemName=view.findViewById(R.id.itemName);
        qty=view.findViewById(R.id.qty);
        currency2=view.findViewById(R.id.currency2);
        currency1=view.findViewById(R.id.currency1);
        addDiscount=view.findViewById(R.id.addDiscount);
        overAllDiscount=view.findViewById(R.id.overallDiscount);
        actualValue=view.findViewById(R.id.autualValue);
        discountValue=view.findViewById(R.id.discountValue);
        percentToggle=view.findViewById(R.id.percentToggle);
        currencyToggle=view.findViewById(R.id.currencyToggle);
        priceOfSell=view.findViewById(R.id.priceOfSell);
        totalPrice=view.findViewById(R.id.totalPrice);
        discountAmount=view.findViewById(R.id.discountAmount);
        discountPercent=view.findViewById(R.id.discountPercent);
        recyclerView=view.findViewById(R.id.recycler);
        removeDiscount=view.findViewById(R.id.removeDiscount);

        addDiscount.setVisibility(View.INVISIBLE);
        currency1.setText(Constants.CURRENCY_VALUE);
        currency2.setText(Constants.CURRENCY_VALUE);
        currencyToggle.setText(Constants.CURRENCY_VALUE);
    }

    @Override
    public void onMethodCallback() {
        updateList();
    }
}