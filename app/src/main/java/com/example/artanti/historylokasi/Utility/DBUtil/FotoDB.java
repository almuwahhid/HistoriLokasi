package com.example.artanti.historylokasi.Utility.DBUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.artanti.historylokasi.Model.Foto;
import com.example.artanti.historylokasi.Preferences.Database;

import java.util.ArrayList;

/**
 * Created by root on 12/25/17.
 */

public class FotoDB {
    Cursor csr;
    Database db;
    SQLiteDatabase database;

    private ArrayList<Foto> fotos;
    public FotoDB(Context ctx) {
        this.db = new Database(ctx);
    }

    public FotoDB() {
    }

    public boolean isFoto(int id_histori){
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM foto WHERE id_histori = "+id_histori;
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

    public int getCount(int id_histori){
        int count = 0;
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM foto WHERE id_histori = "+id_histori;
            csr = database.rawQuery(query, null);
            count = csr.getCount();
        }catch (Exception a){
            Log.e("err", "getAllData saran: "+a);
        }
        return count;
    }

    public void setFoto(Foto foto){
        database = db.getWritableDatabase();
        String query =
                "INSERT INTO foto ('id_histori', " +
                        "'nama_file') " +
                        "VALUES ("+ foto.getId_histori()+
                        ", '"+foto.getNama_file()+"')";
        database.execSQL(query);
    }

    public ArrayList getFoto(int id){
        fotos = new ArrayList<>();
        try {
            database = db.getWritableDatabase();
            String query = "SELECT * FROM foto WHERE id_histori = "+id;
            csr = database.rawQuery(query, null);
            while (csr.moveToNext()){
                Foto foto = new Foto();
                foto.setId_foto(csr.getInt(csr.getColumnIndex("id_foto")));
                foto.setId_histori(csr.getInt(csr.getColumnIndex("id_histori")));
                foto.setNama_file(csr.getString(csr.getColumnIndex("nama_file")));
                this.fotos.add(foto);
            }
        }catch (Exception a){
            Log.e("err", "getAllData saran: "+a);
        }
        return fotos;
    }

}
