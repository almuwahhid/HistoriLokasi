package com.example.artanti.historylokasi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.artanti.historylokasi.Model.Foto;
import com.example.artanti.historylokasi.Model.Histori;
import com.example.artanti.historylokasi.MyHistori.SliderAdapter;
import com.example.artanti.historylokasi.Utility.DBUtil.FotoDB;
import com.example.artanti.historylokasi.Utility.DBUtil.HistoriDB;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Artanti on 4/17/2017.
 */

public class DetailHistori extends AppCompatActivity {
    @BindView(R.id.floating_map) protected FloatingActionButton floating_map;
    @BindView(R.id.tv_keterangan) protected TextView tv_keterangan;
    @BindView(R.id.tv_nama_event) protected TextView tv_nama_event;
    @BindView(R.id.tv_nama_tempat) protected TextView tv_nama_tempat;

    @BindView(R.id.viewpager) protected ViewPager viewpager;
    @BindView(R.id.landing_page_indicator) protected CircleIndicator landing_page_indicator;

    int id_histori;
    Histori histori;
    HistoriDB historiDB;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    ArrayList<Foto> fotos;
    FotoDB fotoDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_histori);
        ButterKnife.bind(this);

        id_histori = getIntent().getIntExtra("id_histori", 0);
        Log.d("asd", "onCreate: "+id_histori);
        historiDB = new HistoriDB(this);
        fotoDB = new FotoDB(this);
        histori = historiDB.getDetail(id_histori);
        fotos = fotoDB.getFoto(id_histori);
        initSliders();
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        getSupportActionBar().setTitle("Detail Histori");


        setComponent();
    }

    private void initSliders() {
        viewpager.setAdapter(new SliderAdapter(fotos, this));
        landing_page_indicator.setViewPager(viewpager);

        final float density = getResources().getDisplayMetrics().density;

        NUM_PAGES = fotos.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewpager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    private void setComponent() {
        tv_nama_event.setText(histori.getEvent());
        tv_nama_tempat.setText(histori.getNama_tempat());
        tv_keterangan.setText(histori.getDesk_singkat());

        floating_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailHistori.this, MapsActivity.class);
                intent.putExtra("lat", Double.valueOf(histori.getLatitude()));
                intent.putExtra("lng", Double.valueOf(histori.getLongitude()));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
