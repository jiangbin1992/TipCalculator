package com.codecanyon.percentage.Backend;

public class SavedData {
    String fileName,category;
    int id;
    public SavedData(String fileName,String category,int id){
        this.fileName=fileName;
        this.category=category;
        this.id=id;
    }
    public String getFileName(){
        return fileName;
    }
    public String getCategory(){
        return category;
    }
    public int getId(){
        return id;
    }

}
