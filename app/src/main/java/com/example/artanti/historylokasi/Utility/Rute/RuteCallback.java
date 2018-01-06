package com.example.artanti.historylokasi.Utility.Rute;

import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 12/25/17.
 */

public interface RuteCallback {
    public void setMarker(String jarak, List<List<HashMap<String, String>>> routes, int status);
}