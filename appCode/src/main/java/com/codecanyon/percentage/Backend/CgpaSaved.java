package com.codecanyon.percentage.Backend;

public class CgpaSaved {
    String fileName,semesterName,sgpa;
    Boolean cgpaOutOf;
    int id;
    public CgpaSaved(String fileName,String semesterName, String sgpa,boolean cgpaOutOf,int id){
        this.fileName=fileName;
        this.semesterName=semesterName;
        this.id=id;
        this.sgpa=sgpa;
        this.cgpaOutOf=cgpaOutOf;
    }

    public int getId(){
        return id;
    }
    public String getFileName() {
        return fileName;
    }
    public String getSemesterName(){
        return semesterName;
    }
    public String getSgpa(){
        return sgpa;
    }
    public boolean getCgpaOutOf(){
        return cgpaOutOf;
    }
}
