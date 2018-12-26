package com.example.user.aplyt1;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    public static final int GET_ACCESS_NETWORK_STATE = 800;
    public static final int GET_INTERNET = 900;
    private ExampleBroadcastReceiver exampleBroadcastReceiver = new ExampleBroadcastReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},GET_ACCESS_NETWORK_STATE);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},GET_INTERNET);
        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case GET_INTERNET:{
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
            case GET_ACCESS_NETWORK_STATE:{
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
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(this.exampleBroadcastReceiver,intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(this.exampleBroadcastReceiver);
    }



}
