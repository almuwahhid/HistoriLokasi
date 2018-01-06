package com.example.artanti.historylokasi.Preferences;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 12/25/17.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "histori.db";
    private static final String TABLE_NAME = "histori";
    private static final String UID = "id_histori";
    private static final String NAMA_TEMPAT = "nama_tempat";
    private static final String EVENT = "event";
    private static final String DESK = "desk_singkat";
    private static final String LAT = "latitude";
    private static final String LONGIT = "longitude";
    private static final String TIME = "jam";
    private static final String DATE = "tanggal";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME2 = "foto";
    private static final String IDF = "id_foto";
    private static final String IDH = "id_histori";
    private static final String NAMA_FOTO = "nama_file";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMA_TEMPAT + " VARCHAR(50), " + EVENT + " VARCHAR (30), " + DESK + " VARCHAR (160),"+ TIME + " VARCHAR (160),"+ DATE + " VARCHAR (160),"+LAT+" VARCHAR (100) ,"+LONGIT+" VARCHAR (100));";
    private static final String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + "(" + IDF + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IDH + " INTEGER, " + NAMA_FOTO + " VARCHAR (50));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME;
    private Context context;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE2);

        } catch (SQLiteException e) {
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS histori");
        db.execSQL("DROP TABLE IF EXISTS foto");
        onCreate(db);
    }
}
