package com.example.user.app1;

import android.hardware.Camera;
import android.os.Build;
import android.widget.Toast;

public class RunnerAlg implements Runnable {
    public static RunnerAlg getInstance(){
        return instance == null ? instance = new RunnerAlg() : instance;

    }

    private static RunnerAlg instance;
    private Camera camera;
    private Camera.Parameters parameters;
    public volatile boolean isRunning = false;
    public volatile boolean requestStop = false;
    public volatile MyService controller;

    @Override
    public void run() {
        if(isRunning)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Toast.makeText(controller,"Nezasvieti",Toast.LENGTH_SHORT ).show();
            return;
        }else {
            camera = Camera.open();
            parameters = camera.getParameters();

            requestStop = false;
            isRunning = true;

            String myString = "01110011 01101111 01110011";
            long blinkDelay = 50;
            do{
                for (int i = 0; i < myString.length(); i++) {
                    if (myString.charAt(i) == '0') {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                    } else {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.startPreview();
                    }
                    try {
                        Thread.sleep(blinkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }while (!requestStop);
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.release();
            isRunning = false;
            requestStop = false;
        }

    }
}
