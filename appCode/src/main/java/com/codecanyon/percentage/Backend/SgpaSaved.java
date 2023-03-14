package com.codecanyon.percentage.Backend;

public class SgpaSaved {
    String fileName,marksObtained,credit,totalMarks,subjectName;
    boolean calculationRule,sgpaOutOf;
    int id;
    public SgpaSaved(String fileName,String marksObtained, String totalMarks,String credit,String subjectName,boolean calculationRule,boolean sgpaOutOf,int id){
        this.fileName=fileName;
        this.marksObtained=marksObtained;
        this.totalMarks=totalMarks;
        this.credit=credit;
        this.calculationRule=calculationRule;
        this.sgpaOutOf=sgpaOutOf;
        this.subjectName=subjectName;
        this.id=id;
    }

    public int getId(){
        return id;
    }
    public String getCredit(){
        return credit;
    }
    public boolean getCalculationRule(){
        return calculationRule;
    }
    public boolean getSgpaRule(){
        return sgpaOutOf;
    }
    public String getFileName() {
        return fileName;
    }
    public String getMarksObtained(){
        return marksObtained;
    }
    public String getTotalMarks(){
        return totalMarks;
    }
    public String getSubjectName(){
        return subjectName;
    }
}
