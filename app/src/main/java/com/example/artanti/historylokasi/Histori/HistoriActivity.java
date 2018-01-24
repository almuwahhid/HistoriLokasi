package com.example.artanti.historylokasi.Histori;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artanti.historylokasi.BuildConfig;
import com.example.artanti.historylokasi.Model.Foto;
import com.example.artanti.historylokasi.Model.Histori;
import com.example.artanti.historylokasi.MyHistori.MyHistoriActivity;
import com.example.artanti.historylokasi.Preferences.Konstanta;
import com.example.artanti.historylokasi.R;
import com.example.artanti.historylokasi.Utility.DBUtil.FotoDB;
import com.example.artanti.historylokasi.Utility.DBUtil.HistoriDB;
import com.example.artanti.historylokasi.Utility.FilePath;
import com.example.artanti.historylokasi.Utility.StringUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Artanti on 4/3/2017.
 */

public class HistoriActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    @BindView(R.id.btn_foto) protected FloatingActionButton btn_foto;
    @BindView(R.id.btn_simpan) protected Button btn_simpan;
    @BindView(R.id.edt_nama_tempat) protected EditText edt_nama_tempat;
    @BindView(R.id.edt_nama_event) protected EditText edt_nama_event;
    @BindView(R.id.edt_deskripsi) protected EditText edt_deskripsi;
    @BindView(R.id.recyclerView) protected RecyclerView recyclerView;

    private Location myLocation;

    private GoogleApiClient mGoogleApiClient;
    HistoriDB historiDB;
    FotoDB fotoDB;

    Histori histori;
    Foto foto;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;

    List<Uri> uris;
    FotoAdapter fotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        getSupportActionBar().setTitle("Tambah Histori");

        buildGoogleApiClient();
        historiDB = new HistoriDB(this);
        fotoDB = new FotoDB(this);
        uris = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        fotoAdapter = new FotoAdapter(this, uris);
        recyclerView.setAdapter(fotoAdapter);

        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dispatchTakePictureIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_simpan.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_simpan:
                if(validateData()){
                    saveToObject();
                    historiDB.setHistori(histori);
                    if(!uris.isEmpty()){
                        for(int x=0; x<uris.size();x++){
                            foto = new Foto();
                            String uri_file = "";
                            if(FilePath.isFile(uris.get(x))){
                                uri_file = uris.get(x).getPath();
                            }else{
                                uri_file = FilePath.getPath(HistoriActivity.this, uris.get(x));
                            }
                            foto.setId_histori(historiDB.getLastHistori().getId_histori());
                            foto.setNama_file(uri_file);
                            fotoDB.setFoto(foto);
                        }
                    }
                    Toast.makeText(HistoriActivity.this, "Berhasil tambah histori", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HistoriActivity.this, MyHistoriActivity.class));
                    finish();
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("asd", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void setMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        this.myLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        Log.d("asd", "setMyLocation: "+this.myLocation.getLatitude()+", "+this.myLocation.getLongitude());
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(HistoriActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void saveToObject(){
        histori = new Histori();
        histori.setEvent(edt_nama_event.getText().toString());
        histori.setTanggal(StringUtils.datenow());
        histori.setJam(StringUtils.timenow());
        histori.setLatitude(String.valueOf(myLocation.getLatitude()));
        histori.setLongitude(String.valueOf(myLocation.getLongitude()));
        histori.setNama_tempat(edt_nama_tempat.getText().toString());
        histori.setDesk_singkat(edt_deskripsi.getText().toString());
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/"+ Konstanta.folder_main);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("asd", "onActivityResult: "+resultCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Log.d("asd", "onActivityResult: "+data.getData().toString());
            Uri selectedFileUri = Uri.parse(mCurrentPhotoPath);
            Log.d("asd", "onActivityResult: "+selectedFileUri.getPath());
            uris.add(selectedFileUri);
            fotoAdapter.notifyDataSetChanged();
        }

    }

    private boolean validateData(){
        if(edt_nama_event.getText().toString().trim().equals("")){
            edt_nama_event.setError("Event tidak boleh kosong");
            return false;
        }
        if(edt_deskripsi.getText().toString().trim().equals("")){
            edt_deskripsi.setError("Deskripsi tidak boleh kosong");
            return false;
        }
        if(edt_nama_tempat.getText().toString().trim().equals("")){
            edt_nama_tempat.setError("Nama Tempat tidak boleh kosong");
            return false;
        }
        return true;
    }

}