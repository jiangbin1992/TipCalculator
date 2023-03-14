package com.codecanyon.percentage.Backend;

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;


public class Fav {
    public static List <String> favList=new ArrayList<>();
    public static List <Integer> favImageList=new ArrayList<>();
    Context context;
    public Fav(Context context){
        this.context=context;
    }

    public boolean addToFavList(String value,int image){
        if(favList.size()<6){
            favList.add(value);
            favImageList.add(image);
            return true;
        }else {

            return false;
        }
    }

    public void removeFromFavList(String value,int image){
        if (!favList.isEmpty()){
            int i= favList.indexOf(value);
            favList.remove(value);
            favImageList.remove(i);
        }
    }

    public String[] inArray(){
        String[] array = new String[favList.size()];
        favList.toArray(array);
        return array;
    }

    public int[] inArrayImsge(){
        return ArrayUtils.toPrimitive(favImageList.toArray(new Integer[favImageList.size()]));
    }
}
