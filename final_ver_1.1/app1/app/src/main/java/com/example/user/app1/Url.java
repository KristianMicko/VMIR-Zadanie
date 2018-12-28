package com.example.user.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Url extends AppCompatActivity {
    private EditText editText;
    private Button nahraj;
    private TextView zoznamNahravok;
    private List<ZazitkyModel> obrazky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        editText = (EditText)findViewById(R.id.editText2);
        zoznamNahravok = findViewById(R.id.zoznamNahravok);
        Call<List<ZazitkyModel>> call = RetrofitTools.getApi().getJSON();
        call.enqueue(new Callback<List<ZazitkyModel>>() {
            @Override
            public void onResponse(Call<List<ZazitkyModel>> call, Response<List<ZazitkyModel>> response) {
                obrazky = response.body();
                for (ZazitkyModel obrazok: obrazky){
                    zoznamNahravok.append(obrazok.getID() + " : " + obrazok.getImages()+"\n");
                }
            }

            @Override
            public void onFailure(Call<List<ZazitkyModel>> call, Throwable t) {

            }
        });


        nahraj = (Button)findViewById(R.id.nahraj2);
        nahraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nacitaj(editText.getText().toString());
                finish();
            }
        });



    }
    private void nacitaj(String vstup){
        Call<JsonArray> call = RetrofitTools.getApi().getData(vstup);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray data = response.body();
                String url = data.get(0).getAsJsonObject().get("Images").getAsString();
                try{
                    FileOutputStream fileOutputStream = openFileOutput("example.txt",MODE_PRIVATE);
                    fileOutputStream.write(url.getBytes());
                    editText.setText("");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

}
