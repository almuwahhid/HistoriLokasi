package com.example.artanti.historylokasi.Utility.Volley;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by gueone on 8/14/2017.
 */

public interface VolleyCallback {
    public void notifySuccess(String requestType, JSONObject response);
    public void notifyError(String requestType, VolleyError error);
}
