package com.example.artanti.historylokasi.Preferences;

import android.util.Log;

/**
 * Created by root on 12/25/17.
 */

public class Konstanta {
    public static final String URL_DIRECTION(Double lat1, Double lat2, Double lng1, Double lng2){
        String str_origin = "origin="+lat1+","+lng1;
        String str_dest = "destination="+lat2+","+lng2;
        String sensor = "sensor=false";
        String parameters = str_origin+"&"+str_dest+"&"+sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        Log.d("url", "URL_DIRECTION: "+url);
        return url;
    }
    public static final String folder_main = "historilokasi";
}

