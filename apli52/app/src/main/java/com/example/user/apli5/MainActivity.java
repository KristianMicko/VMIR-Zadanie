package com.example.user.apli5;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://kristiancoolrex.000webhostapp.com/";
    private static final int PICK_REQUEST_FILE = 1000;
    public static final int GET_EXTERNAL_ALLOW = 500;
    public static final int GET_INTERNET_ALLOW = 600;
    public static final int GET_EXTERNAL_ALLOW_2 = 700;
    public static final int GET_ACCESS_NETWORK_STATE = 800;
    public static final String SEND = "send";
    IUploadAPI mService;

    Button button;
    Button btnUpload;
    ImageView imageView;
    Uri selectedFileUri;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mService = getAPIUpload();
        btnUpload = (Button)findViewById(R.id.btn_upload);
        imageView = (ImageView)findViewById(R.id.image_view);
        //button = (Button) findViewById(R.id.button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GET_EXTERNAL_ALLOW);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},GET_INTERNET_ALLOW);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},GET_EXTERNAL_ALLOW_2);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},GET_ACCESS_NETWORK_STATE);
            return;
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadFile();
                startMyService();
            }
        });


    }

    /*private void uploadFile() {
        if (selectedFileUri != null){
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Uploading...");
            dialog.setIndeterminate(false);
            dialog.setMax(100);
            dialog.setCancelable(false);
            dialog.show();

            File file = FileUtils.getFile(this,selectedFileUri);
            ProgessRequestBody progessRequestBody = new ProgessRequestBody(file,this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),progessRequestBody);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mService.file(body).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();

        }
    }*/

    private void chooseFile() {
        Intent intent = Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file");
        startActivityForResult(intent,PICK_REQUEST_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_REQUEST_FILE){
            if (data != null){
                selectedFileUri = data.getData();
                if (selectedFileUri != null && !selectedFileUri.getPath().isEmpty()){
                    imageView.setImageURI(selectedFileUri);
                }else {
                    Toast.makeText(getApplicationContext(),"Cannot upload file",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    private void startMyService(){
        Intent intentToSend = new Intent(this,MyService.class);
        intentToSend.putExtra(SEND,selectedFileUri.toString());
        startService(intentToSend);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case GET_INTERNET_ALLOW:{
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
            case GET_EXTERNAL_ALLOW:{
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
            case GET_EXTERNAL_ALLOW_2:{
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
        }
    }
}
