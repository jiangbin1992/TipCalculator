package com.codecanyon.percentage.Backend;

public class App {
    String playstoreLink,icon,shortDiscription,appName;
    public App(String appName,String playstoreLink, String icon,String shortDiscription){
        this.playstoreLink=playstoreLink;
        this.appName=appName;
        this.icon=icon;
        this.shortDiscription=shortDiscription;
    }

    public String getIcon() {
        return icon;
    }
    public String getPlaystoreLink(){
        return playstoreLink;
    }
    public String getShortDiscription(){
        return shortDiscription;
    }
    public String getAppName(){
        return appName;
    }
}
