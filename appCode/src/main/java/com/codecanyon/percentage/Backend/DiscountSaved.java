package com.codecanyon.percentage.Backend;

public class DiscountSaved {
    String fileName,actualValue,totalValue,itemName,overAllDiscount;
    boolean percentageBoolean,addOverAllDiscount;
    int id;
    public DiscountSaved(String fileName,String actualValue, String totalValue,String itemName,String overAllDiscount,boolean percentageBoolean,boolean addOverAllDiscount,int id){
        this.fileName=fileName;
        this.actualValue=actualValue;
        this.totalValue=totalValue;
        this.addOverAllDiscount=addOverAllDiscount;
        this.itemName=itemName;
        this.overAllDiscount=overAllDiscount;
        this.percentageBoolean=percentageBoolean;
        this.id=id;
    }

    public int getId(){
        return id;
    }
    public String getOverAllDiscount(){
        return overAllDiscount;
    }
    public boolean getAddOverAllDiscount(){
        return addOverAllDiscount;
    }
    public boolean getPercentageBoolean(){
        return percentageBoolean;
    }
    public String getFileName() {
        return fileName;
    }
    public String getActualValue(){
        return actualValue;
    }
    public String getTotalValue(){
        return totalValue;
    }
    public String getItemName(){
        return itemName;
    }
}
