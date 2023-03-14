package com.codecanyon.percentage.Backend;

public class QudraticClass {
    String fileName,a,b,c;
    public QudraticClass(String fileName, String a, String b, String c){
        this.a=a;
        this.b=b;
        this.c=c;
        this.fileName=fileName;

    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getFileName() {
        return fileName;
    }

    public String getC() {
        return c;
    }
}
