package com.example.artanti.historylokasi.Model;

/**
 * Created by root on 12/25/17.
 */

public class Foto {
    int id_foto, id_histori;
    String nama_file;

    public int getId_foto() {
        return id_foto;
    }

    public void setId_foto(int id_foto) {
        this.id_foto = id_foto;
    }

    public int getId_histori() {
        return id_histori;
    }

    public void setId_histori(int id_histori) {
        this.id_histori = id_histori;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }
}

