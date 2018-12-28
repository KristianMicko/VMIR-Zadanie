package com.example.user.app1;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.IBinder;

public class MyService extends Service {
    private Camera camera;
    private Camera.Parameters parameters;
    private boolean isFlash= false;
    private boolean isOn=false;
    private boolean bezi = true;
    private RunnerAlg runner;
    private Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getBaseContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            isFlash = true;
        }
        runner = RunnerAlg.getInstance();
        runner.controller = this;

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (isFlash){
            thread = new Thread(runner);
            thread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        runner.requestStop = true;


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
