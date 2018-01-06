package com.example.artanti.historylokasi.Utility.DBUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.artanti.historylokasi.Model.Histori;
import com.example.artanti.historylokasi.Preferences.Database;

import java.util.ArrayList;

/**
 * Created by root on 12/25/17.
 */

public class HistoriDB {
    Cursor csr;
    Database db;
    SQLiteDatabase database;

    private ArrayList<Histori> historis;
    public HistoriDB(Context ctx) {
        this.db = new Database(ctx);
    }
    Histori histori;

    public HistoriDB() {

    }

    public Boolean isHistori(){
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM histori";
            csr = database.rawQuery(query, null);
            if(csr.getCount() != 0){
                return true;
            }
            return false;
        }catch (Exception e){
            Log.e("error", "check_user: "+e );
            return false;
        }
    }

    public int getCount(){
        int count = 0;
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM histori";
            csr = database.rawQuery(query, null);
            count = csr.getCount();
        }catch (Exception a){
            Log.e("err", "getAllData saran: "+a);
        }
        return count;
    }

    public Histori getLastHistori(){
        histori = new Histori();
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM histori";
            csr = database.rawQuery(query, null);
            while (csr.moveToNext()){
                histori.setId_histori(csr.getInt(csr.getColumnIndex("id_histori")));
                histori.setNama_tempat(csr.getString(csr.getColumnIndex("nama_tempat")));
                histori.setEvent(csr.getString(csr.getColumnIndex("event")));
                histori.setDesk_singkat(csr.getString(csr.getColumnIndex("desk_singkat")));
                histori.setTanggal(csr.getString(csr.getColumnIndex("tanggal")));
                histori.setJam(csr.getString(csr.getColumnIndex("jam")));
                histori.setLatitude(csr.getString(csr.getColumnIndex("latitude")));
                histori.setLongitude(csr.getString(csr.getColumnIndex("longitude")));
            }
        }catch (Exception a){
            Log.e("err", "getAllData saran: "+a);
        }
        return this.histori;
    }

    public void setHistori(Histori histori){
        int newCount = getCount();
        newCount = newCount+1;
        database = db.getWritableDatabase();
        String query =
                "INSERT INTO histori ('nama_tempat', " +
                        "'event', " +
                        "'desk_singkat', " +
                        "'tanggal', " +
                        "'jam', " +
                        "'latitude', " +
                        "'longitude') " +
                        "VALUES ('"+ histori.getNama_tempat() +"'"+
                        ", '"+histori.getEvent()+"'"+
                        ", '"+histori.getDesk_singkat()+"'"+
                        ", '"+histori.getTanggal()+"'"+
                        ", '"+histori.getJam()+"'"+
                        ", '"+histori.getLatitude()+"'"+
                        ", '"+histori.getLongitude()+"')";
        database.execSQL(query);
    }

    public Histori getDetail(int id_histori){
        histori = new Histori();
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM histori WHERE id_histori = "+id_histori;
            csr = database.rawQuery(query, null);
            while (csr.moveToNext()){
                histori.setId_histori(csr.getInt(csr.getColumnIndex("id_histori")));
                histori.setNama_tempat(csr.getString(csr.getColumnIndex("nama_tempat")));
                histori.setEvent(csr.getString(csr.getColumnIndex("event")));
                histori.setDesk_singkat(csr.getString(csr.getColumnIndex("desk_singkat")));
                histori.setTanggal(csr.getString(csr.getColumnIndex("tanggal")));
                histori.setJam(csr.getString(csr.getColumnIndex("jam")));
                histori.setLatitude(csr.getString(csr.getColumnIndex("latitude")));
                histori.setLongitude(csr.getString(csr.getColumnIndex("longitude")));
            }
        }catch (Exception a){
            Log.e("err", "getAllData saran: "+a);
        }
        return this.histori;
    }

    public ArrayList getHistori(){
        historis = new ArrayList<>();
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM histori ORDER BY tanggal AND jam ASC";
            csr = database.rawQuery(query, null);
            while (csr.moveToNext()){
                Histori histori = new Histori();
                histori.setId_histori(csr.getInt(csr.getColumnIndex("id_histori")));
                histori.setNama_tempat(csr.getString(csr.getColumnIndex("nama_tempat")));
                histori.setEvent(csr.getString(csr.getColumnIndex("event")));
                histori.setDesk_singkat(csr.getString(csr.getColumnIndex("desk_singkat")));
                histori.setTanggal(csr.getString(csr.getColumnIndex("tanggal")));
                histori.setJam(csr.getString(csr.getColumnIndex("jam")));
                histori.setLatitude(csr.getString(csr.getColumnIndex("latitude")));
                histori.setLongitude(csr.getString(csr.getColumnIndex("longitude")));
                this.historis.add(histori);
            }
        }catch (Exception a){
            Log.e("err", "getAllData saran: "+a);
        }
        return historis;
    }
    public ArrayList getHistori(String key){
        historis = new ArrayList<>();
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM histori WHERE event LIKE '%"+key+"%' ORDER BY tanggal AND jam ASC";
            csr = database.rawQuery(query, null);
            while (csr.moveToNext()){
                Histori histori = new Histori();
                histori.setId_histori(csr.getInt(csr.getColumnIndex("id_histori")));
                histori.setNama_tempat(csr.getString(csr.getColumnIndex("nama_tempat")));
                histori.setEvent(csr.getString(csr.getColumnIndex("event")));
                histori.setDesk_singkat(csr.getString(csr.getColumnIndex("desk_singkat")));
                histori.setTanggal(csr.getString(csr.getColumnIndex("tanggal")));
                histori.setJam(csr.getString(csr.getColumnIndex("jam")));
                histori.setLatitude(csr.getString(csr.getColumnIndex("latitude")));
                histori.setLongitude(csr.getString(csr.getColumnIndex("longitude")));
                this.historis.add(histori);
            }
        }catch (Exception a){
            Log.e("err", "getAllData saran: "+a);
        }
        return historis;
    }
}
