package com.example.user.app1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fifth extends AppCompatActivity implements UploadCallBacks{
    private ImageView back;
    private Button upload;
    private ImageView uploader;
    public static final String BASE_URL = "https://kristiancoolrex.000webhostapp.com/";
    private static final int PICK_REQUEST_FILE = 1000;

    Uri selectedFileUri;
    ProgressDialog dialog;
    IUploadAPI mService;

    private IUploadAPI getAPIUpload(){
        return RetrofitClient.getClient(BASE_URL).create(IUploadAPI.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);


        upload = (Button)findViewById(R.id.upload_image);
        uploader = (ImageView)findViewById(R.id.nahravaci_obrazok);
        mService = this.getAPIUpload();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });


        uploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseFile();
            }
        });

    }


    private void uploadFile() {
        if (selectedFileUri != null){
            dialog = new ProgressDialog(fifth.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Uploading...");
            dialog.setIndeterminate(false);
            dialog.setMax(100);
            dialog.setCancelable(false);
            dialog.show();

            File file = FileUtils.getFile(getApplicationContext(),selectedFileUri);
            ProgessRequestBody progessRequestBody = new ProgessRequestBody(file,this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),progessRequestBody);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mService.file(body).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();

        }
    }

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
                    uploader.setImageURI(selectedFileUri);
                }else {
                    Toast.makeText(getApplicationContext(),"Cannot upload file",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        dialog.setProgress(percentage);
    }


}
