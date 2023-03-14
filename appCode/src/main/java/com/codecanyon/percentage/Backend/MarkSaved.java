package com.codecanyon.percentage.Backend;

public class MarkSaved {
    String fileName,marksObtained,totalMarks,subjectName;
    int id;
    public MarkSaved(String fileName,String marksObtained, String totalMarks,String subjectName,int id){
        this.fileName=fileName;
        this.marksObtained=marksObtained;
        this.totalMarks=totalMarks;
        this.subjectName=subjectName;
        this.id=id;
    }

    public int getId(){
        return id;
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
