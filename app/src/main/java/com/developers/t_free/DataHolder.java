package com.developers.t_free;

/**
 * Created by Amanjeet Singh on 17-Jul-16.
 */
public class DataHolder {
    private static String m;
    private static String rfid;
    public static void setMail(String m){
        DataHolder.m=m;
    }
    public static String getMail(){
       return m;
    }
    public static void setRf(String r){
        DataHolder.rfid=r;
    }
    public static String getRf(){
        return rfid;
    }
}
