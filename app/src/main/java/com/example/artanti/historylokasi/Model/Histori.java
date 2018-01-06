package com.example.artanti.historylokasi.Model;

import java.io.Serializable;

/**
 * Created by root on 12/25/17.
 */

public class Histori implements Serializable{
    int id_histori;
    String nama_tempat, event, desk_singkat, tanggal, jam, latitude, longitude;

    public int getId_histori() {
        return id_histori;
    }

    public void setId_histori(int id_histori) {
        this.id_histori = id_histori;
    }

    public String getNama_tempat() {
        return nama_tempat;
    }

    public void setNama_tempat(String nama_tempat) {
        this.nama_tempat = nama_tempat;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDesk_singkat() {
        return desk_singkat;
    }

    public void setDesk_singkat(String desk_singkat) {
        this.desk_singkat = desk_singkat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
