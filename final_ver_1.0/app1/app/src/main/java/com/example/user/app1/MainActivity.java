package com.example.user.app1;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mapa, pocasie, SoS, NahrajObrazok, ZaznamenajZazitok;
    private static final int ID = 1000;
    public static final int READ_EXTERNAL_STORAGE = 100;
    public static final int WRITE_ETXTERNAL_STORAGE = 200;
    public static final int CAMERA = 300;
    public static final int INTERNET = 400;
    public static final int ACCESS_COARSE_LOCATION = 500;
    public static final int ACCESS_FINE_LOCATION = 600;
    public static final int ACCESS_NETWORK_STATE = 700;
    private InternetReceiver internetReceiver = new InternetReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                ){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},ACCESS_NETWORK_STATE);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},INTERNET);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},ACCESS_COARSE_LOCATION);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_ETXTERNAL_STORAGE);
        }



        akcia();
        pogoda();
        pomoc();
        upload();
        zaznamenaj();
    }

    public void zaznamenaj() {
        ZaznamenajZazitok = (Button)findViewById(R.id.ZaznamenajZazitok);
        ZaznamenajZazitok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent poker = new Intent(getApplicationContext(), sixth.class);
                startActivity(poker);
                onPause();
            }
        });
    }


    public void upload() {
        NahrajObrazok = (Button)findViewById(R.id.Nahraj_Obrazok);
        NahrajObrazok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ijk= new Intent(getApplicationContext(),fifth.class);
                startActivity(ijk);
                onPause();
            }
        });
    }

    public void pomoc() {
        SoS = (Button)findViewById(R.id.SoS);
        SoS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ij = new Intent(getApplicationContext(),fourth.class);
                startActivity(ij);
                onPause();
            }
        });
    }


    public void akcia() {
        mapa = (Button)findViewById(R.id.mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),second.class);
                startActivity(i);

                onPause();
            }
        });


    }

    public void pogoda(){
        pocasie = (Button)findViewById(R.id.pocasie);
        pocasie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),third.class);
                startActivity(i2);
                onPause();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case INTERNET:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.INTERNET)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You allowed this permission",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
            }
            case ACCESS_NETWORK_STATE:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.ACCESS_NETWORK_STATE)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You allowed this permission",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
            }
            case WRITE_ETXTERNAL_STORAGE:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You allowed this permission",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
            }
            case READ_EXTERNAL_STORAGE:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You allowed this permission",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
            }
            case CAMERA:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.CAMERA)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You allowed this permission",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
            }
            case ACCESS_FINE_LOCATION:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You allowed this permission",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
            }
            case ACCESS_COARSE_LOCATION:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"You allowed this permission",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(this.internetReceiver);
    }
}
