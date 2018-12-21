package com.example.user.apli5;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import id.zelory.compressor.Compressor;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends IntentService implements UploadCallBacks{
    private Notification notification, notification2, notification3;
    private NotificationManager mNotifyManager, mNotifyManager2, mNotifyManager3;
    private Notification.Builder mBuilder, mBuilder2, mBuilder3;
    private Uri selectedFileUri;
    private String imageUrl;
    private Bitmap compressor;
    private byte[] bytesArray;
    int percentage;
    public static final String BASE_URL = "https://kristiancoolrex.000webhostapp.com/";

    IUploadAPI mService;
    ProgressDialog dialog;


    private IUploadAPI getAPIUpload(){
        return RetrofitClient.getClient(BASE_URL).create(IUploadAPI.class);
    }

    public MyService() {
        super("UploadServiceBackround");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        mService = getAPIUpload();
        imageUrl = intent.getStringExtra(MainActivity.SEND);

        selectedFileUri = Uri.parse(imageUrl);
        if (selectedFileUri != null) {

            File file = FileUtils.getFile(this, selectedFileUri);
            RequestBody progessRequestBody = RequestBody.create(MediaType.parse("image/*"),file);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),progessRequestBody);


            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new Notification.Builder(MyService.this);
            notification=mBuilder.setContentTitle("Upload")
                    .setContentText("Upload in progress")
                    .setSmallIcon(R.drawable.ic_notification_24dp)
                    .build();
            mNotifyManager.notify(0,notification);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    mService.file(body).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            //dialog.dismiss();
                            mNotifyManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mBuilder2 = new Notification.Builder(MyService.this);
                            notification2=mBuilder.setContentTitle("Upload")
                                    .setContentText("Upload is done")
                                    .setSmallIcon(R.drawable.ic_notification_24dp)
                                    .build();
                            mNotifyManager2.notify(1,notification2);
                            Toast.makeText(MyService.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            mNotifyManager3 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mBuilder3 = new Notification.Builder(MyService.this);
                            notification3=mBuilder.setContentTitle("Upload")
                                    .setContentText("Upload is failed")
                                    .setSmallIcon(R.drawable.ic_notification_24dp)
                                    .build();
                            mNotifyManager3.notify(2,notification3);

                            Toast.makeText(MyService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();

        }


        uploadFile();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onProgressUpdate(int percentage) {
        this.percentage = percentage;

        //mBuilder.setProgress(100, percentage, false);

    }

    private void uploadFile() {
        /*if (selectedFileUri != null){
            dialog = new ProgressDialog(MyService.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Uploading...");
            dialog.setIndeterminate(false);
            dialog.setMax(100);
            dialog.setCancelable(false);
//            dialog.show();

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
                            Toast.makeText(MyService.this,"Uploaded",Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(MyService.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();*/

            onDestroy();

        }

        public void nahravanie(Uri uri){
            File original = FileUtils.getFile(this, uri);
            RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)),original);
            final MultipartBody.Part file = MultipartBody.Part.createFormData("file",original.getName(),filePart);
            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new Notification.Builder(MyService.this);
            notification=mBuilder.setContentTitle("Upload")
                    .setContentText("Upload in progress")
                    .setSmallIcon(R.drawable.ic_notification_24dp)
                    .build();
            mNotifyManager.notify(0,notification);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mService.file(file).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();
                            mNotifyManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mBuilder2 = new Notification.Builder(MyService.this);
                            notification2=mBuilder.setContentTitle("Upload")
                                    .setContentText("Upload is done")
                                    .setSmallIcon(R.drawable.ic_notification_24dp)
                                    .build();
                            mNotifyManager2.notify(1,notification2);
                            Toast.makeText(MyService.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            mNotifyManager3 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mBuilder3 = new Notification.Builder(MyService.this);
                            notification3=mBuilder.setContentTitle("Upload")
                                    .setContentText("Upload is failed")
                                    .setSmallIcon(R.drawable.ic_notification_24dp)
                                    .build();
                            mNotifyManager3.notify(2,notification3);

                            Toast.makeText(MyService.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        }

    }



