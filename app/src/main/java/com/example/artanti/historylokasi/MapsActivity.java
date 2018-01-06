package com.example.artanti.historylokasi;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.artanti.historylokasi.Utility.Rute.RuteCallback;
import com.example.artanti.historylokasi.Utility.Rute.RuteService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    private Location myLocation;
    private Location direction;

    private GoogleApiClient mGoogleApiClient;

    private RuteCallback ruteCallback;
    private RuteService ruteService;

    ProgressDialog progressDialog;

    private void setDirection(){
        this.direction.setLatitude(getIntent().getDoubleExtra("lat", 0));
        this.direction.setLongitude(getIntent().getDoubleExtra("lng", 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        getSupportActionBar().setTitle("Rute Lokasi Histori");
        buildGoogleApiClient();
        direction = new Location("");
        setDirection();

        setCallback();
    }

    private void getDataRute() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ruteService = new RuteService(this, ruteCallback, myLocation.getLatitude(), myLocation.getLongitude(), direction.getLatitude(), direction.getLongitude());
    }

    private void setMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapRoute);
        mapFragment.getMapAsync(this);
    }

    private void setCallback() {
        ruteCallback = new RuteCallback() {
            @Override
            public void setMarker(String jarak, List<List<HashMap<String, String>>> routes, int status) {
                progressDialog.hide();
                if(status==1){
                    initRute(routes);
                }else{
                    Toast.makeText(MapsActivity.this, "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
    }

    private void initRute(List<List<HashMap<String, String>>> routes) {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
//                    MarkerOptions markerOptions = new MarkerOptions();

        for(int i=0;i<routes.size();i++){
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = routes.get(i);

            // Fetching all the points in i-th route
            for(int j=0;j<path.size();j++){
                HashMap<String,String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(12);
            lineOptions.color(Color.GRAY);
        }
        mMap.addPolyline(lineOptions);
    }

    public void setMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        this.myLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        Log.d("map", "setMyLocation: "+myLocation.getLatitude());
    }

    private void setMarker(Location location, int a){
        LatLng lokasi_tujuan = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        if(a==0){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi_tujuan));
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_camera_alt_black_24dp));
            markerOptions.position(lokasi_tujuan).title("Anda");
        }else{
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_balance_black_24dp));
            markerOptions.position(lokasi_tujuan).title("Lokasi Histori");
        }
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMarker(myLocation, 0);
        setMarker(direction, 1);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setMyLocation();
                setMap();
        getDataRute();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
