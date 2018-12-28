package com.example.user.app1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class third extends AppCompatActivity implements SensorEventListener {

    private TextView hodnotaTeploty;
    private TextView hodnotaTlaku;
    private TextView hodnotaVlhkosti;
    private SensorManager sensorManagerTeploty;
    private Sensor sensorTeploty;
    private boolean isSensorPresent;
    private SensorManager sensorManagerTlaku;
    private SensorManager sensorManagerVlhkosti;
    private Sensor sensorTlaku;
    private Sensor sensorVlhkosti;
    private boolean isSensorPresent1;
    private boolean isSensorPresent2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);




        sensorManagerTeploty = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sensorManagerTlaku = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        sensorManagerVlhkosti = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        hodnotaTeploty = (TextView)findViewById(R.id.teplotaUdaj);
        hodnotaTlaku = (TextView)findViewById(R.id.tlak);
        hodnotaVlhkosti = (TextView)findViewById(R.id.vlhkost);
        if(sensorManagerTeploty.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            sensorTeploty = sensorManagerTeploty.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isSensorPresent = true;
        } else {
            hodnotaTeploty.setText("Tento senzor na Vašom smartpfóne nie je dostupný");
            isSensorPresent = false;
        }
        if (sensorManagerTlaku.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            sensorTlaku = sensorManagerTlaku.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isSensorPresent1 = true;
        }else {
            hodnotaTlaku.setText("Tento senzor na Vašom smartpfóne nie je dostupný");
            isSensorPresent1 = false;
        }
        if (sensorManagerVlhkosti.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            sensorVlhkosti = sensorManagerVlhkosti.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isSensorPresent2 = true;
        }else {
            hodnotaVlhkosti.setText("Tento senzor na Vašom smartpfóne nie je dostupný");
            isSensorPresent2 = false;
        }



    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            hodnotaTeploty.setText("Momentalna teplota je " + event.values[0] + "°C");
        }
        if (event.sensor.getType()==Sensor.TYPE_PRESSURE){
            hodnotaTlaku.setText("Momentalny tlak je: " + event.values[0]+" hPa");
        }
        if (event.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
            hodnotaVlhkosti.setText("Momentalna relativna vlhkost je: " + event.values[0]+" %");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManagerTeploty = null;
        sensorTeploty = null;
        sensorVlhkosti = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorPresent) {
            sensorManagerTeploty.registerListener(this, sensorTeploty, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (isSensorPresent1){
            sensorManagerTlaku.registerListener(this,sensorTlaku, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (isSensorPresent2){
            sensorManagerVlhkosti.registerListener(this,sensorVlhkosti, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorPresent) {
            sensorManagerTeploty.unregisterListener(this);
        }
        if(isSensorPresent1) {
            sensorManagerTlaku.unregisterListener(this);
        }

        if(isSensorPresent2) {
            sensorManagerVlhkosti.unregisterListener(this);
        }
    }

    /*public void zmerajTeplotu(){
        hodnotaTeploty = (TextView)findViewById(R.id.teplotaUdaj);


    }*/

}
