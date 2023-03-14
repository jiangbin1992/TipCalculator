package com.codecanyon.percentage.Backend;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DiscountList {
    public static List<String> itemNameList=new ArrayList<>();
    public static List<Double> actualValueList=new ArrayList<>();
    public static List<Double> discountValueList=new ArrayList<>();
    public static List<Integer> quantityList=new ArrayList<>();

    Context context;

    public DiscountList(Context context){
        this.context=context;
    }

    public boolean addValue(String itemName,int qty,double discountValue,double actualValue){
        if(itemNameList.size()<14){
            itemNameList.add(itemName);
            actualValueList.add(actualValue);
            discountValueList.add(discountValue);
            quantityList.add(qty);
            return true;
        }else{
            Toast.makeText(context,"Only 15 subjects are allowed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void removeValue(int position){
        itemNameList.remove(position);
        actualValueList.remove(position);
        discountValueList.remove(position);
        quantityList.remove(position);
    }
}
