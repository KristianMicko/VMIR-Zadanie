package com.example.user.app1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class second extends AppCompatActivity {

    private MapView mapView = null;
    private MyLocationNewOverlay LocationOverlay;
    private Button najdi;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView lokacia;
    private double longitude;
    private double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        mapView = (MapView) findViewById(R.id.mapView);


        this.LocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getBaseContext()),mapView);
        this.LocationOverlay.enableMyLocation();
        mapView.getOverlays().add(this.LocationOverlay);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        najdi = (Button) findViewById(R.id.najdi);
        lokacia = (TextView) findViewById(R.id.lokacia);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lokacia.setText("Lokacia: " + location.getLongitude()+" " + location.getLatitude());
                longitude = Double.parseDouble(String.valueOf(location.getLongitude()));
                latitude = Double.parseDouble(String.valueOf(location.getLatitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET
                },10);
            }

            return;
        }else{
            configureButton();
        }


    }

    private void configureButton() {
        najdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 50, 0, locationListener);
                IMapController controller = mapView.getController();
                controller.setZoom(18.0d);
                controller.setCenter(new GeoPoint(latitude, longitude));
            }
        });

    }




    @Override
    public void onResume(){
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView!=null)
            mapView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView!=null)
            mapView.onPause();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
        switch (requestCode){
            case 10: if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                configureButton();
                return;
        }
    }



}
