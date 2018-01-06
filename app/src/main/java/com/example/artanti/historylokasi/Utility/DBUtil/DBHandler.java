package com.example.artanti.historylokasi.Utility.DBUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.artanti.historylokasi.Preferences.Database;


/**
 * Created by gueone on 8/15/2017.
 */

public class DBHandler {
    Database db;
    SQLiteDatabase database;
    Cursor csr;

    public DBHandler(Context ctx) {
        this.db = new Database(ctx);
    }

    public DBHandler() {

    }

    public Boolean isDataThere(){
        return false;
    }
}
