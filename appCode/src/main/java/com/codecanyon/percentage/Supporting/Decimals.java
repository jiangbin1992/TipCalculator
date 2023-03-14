package com.codecanyon.percentage.Supporting;

import java.math.BigDecimal;

public class Decimals {
    public Decimals(){

    }

    public double roundOfToTwo(double number){
        BigDecimal a = new BigDecimal(number);
        BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return roundOff.doubleValue();
    }
    public double roundOfTo(double number){
        BigDecimal a = new BigDecimal(number);
        BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return roundOff.doubleValue();

    }
    public boolean isInteger(double number){
        return number==(int)number;
    }


}
