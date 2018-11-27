package com.example.user.apli9;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecord extends AppCompatActivity {
    private EditText nazov,miesto,popis, url;
    private Button potvrd;
    private Call<JsonArray> call;
    private Call<String> nieco;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        nazov = (EditText)findViewById(R.id.Nazov1);
        miesto = (EditText)findViewById(R.id.Miesto1);
        popis = (EditText)findViewById(R.id.Popis1);
        url = (EditText)findViewById(R.id.Url1);
        potvrd = (Button)findViewById(R.id.Potvrd);

        potvrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(nazov.getText()) && TextUtils.isEmpty(miesto.getText()) &&TextUtils.isEmpty(popis.getText()) && TextUtils.isEmpty(url.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = nazov.getText().toString();
                    String place = miesto.getText().toString();
                    String description = popis.getText().toString();
                    String uri = url.getText().toString();
                    String intent;
                    call = RetrofitTools.getApi().getData(uri);
                    call.enqueue(new Callback<JsonArray>() {
                        @Override
                        public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                           JsonArray data = response.body();
                           String url = data.get(0).getAsJsonObject().get("Images").getAsString();

                        }

                        @Override
                        public void onFailure(Call<JsonArray> call, Throwable t) {

                        }
                    });


                    replyIntent.putExtra("name", name);
                    replyIntent.putExtra("place", place);
                    replyIntent.putExtra("description",description);
                    replyIntent.putExtra("uri",link);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });



    }
}
