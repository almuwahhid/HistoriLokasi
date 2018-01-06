package com.example.artanti.historylokasi.Utility.Rute;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.artanti.historylokasi.Preferences.Konstanta;
import com.example.artanti.historylokasi.Utility.DirectionsJSONParser;
import com.example.artanti.historylokasi.Utility.Volley.VolleyCallback;
import com.example.artanti.historylokasi.Utility.Volley.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 12/25/17.
 */

public class RuteService {
    Context context;

    RuteCallback ruteCallback;
    String jarak;
    List<List<HashMap<String, String>>> routes;

    VolleyCallback callback;
    VolleyService volley;

    public String getJarak() {
        return jarak;
    }

    public RuteService(Context context, RuteCallback ruteCallback, Double lat1, Double lng1, Double lat2, Double lng2) {
        this.context = context;
        this.ruteCallback = ruteCallback;
        setVolleyCallback(ruteCallback);
        getDatas(lat1, lat2, lng1, lng2);
    }

    public void setJarak(JSONObject data) throws JSONException {
        JSONArray array_item = data.getJSONArray("routes");
        JSONObject object_routes = array_item.getJSONObject(0);
        JSONArray legs = object_routes.getJSONArray("legs");
        JSONObject object_legs = legs.getJSONObject(0);
        JSONObject distance = object_legs.getJSONObject("distance");
        this.jarak = distance.getString("text");
    }

    public List<List<HashMap<String, String>>> getRoutes() {
        return routes;
    }

    public void setRoutes(JSONObject data) throws JSONException {
        DirectionsJSONParser parser = new DirectionsJSONParser();
        this.routes = parser.parse(data);
    }

    private void getDatas(Double lat1, Double lat2, Double lng1, Double lng2) {
        volley = new VolleyService(callback, context);
        volley.getDataVolley("GETCALL", Konstanta.URL_DIRECTION(lat1, lat2, lng1, lng2));
    }

    private void setVolleyCallback(final RuteCallback directionRuteCallback) {
        callback = new VolleyCallback() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                try {
                    Log.d("asd", "notifySuccess: sukses ambil direction");
                    setJarak(response);
                    setRoutes(response);
                    directionRuteCallback.setMarker(getJarak(), getRoutes(), 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(String requestType, VolleyError error) {
                directionRuteCallback.setMarker(null, null, 0);
            }
        };
    }

}
