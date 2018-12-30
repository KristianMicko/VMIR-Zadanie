package com.example.user.app1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecord extends AppCompatActivity {
    private EditText nazov,miesto,popis;
    private Button url, submit;
    private String link;

    private Intent main;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_record:nahraj();
                                  return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
    //private MenuItem potvrd;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        nazov = (EditText)findViewById(R.id.Nazov1);
        miesto = (EditText)findViewById(R.id.Miesto1);
        popis = (EditText)findViewById(R.id.Popis1);
        submit = (Button)findViewById(R.id.Submit);
        url = (Button) findViewById(R.id.Url1);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddRecord.this,Url.class);
                startActivity(i);
            }
        });
        //final AddRecord self = this;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ukaz();
            }
        });


        main = getIntent();

        if (main.hasExtra("id")){
            setTitle("Edit Record");
            nazov.setText(main.getStringExtra("name"));
            miesto.setText(main.getStringExtra("place"));
            popis.setText(main.getStringExtra("description"));
        }else {
            setTitle("Add Record");
        }

        //potvrd.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {

        //    }
        //});



    }


    public void ukaz(){

        try {
            FileInputStream fileInputStream = openFileInput("example.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String vstup;
            while ((vstup = bufferedReader.readLine()) != null){
                stringBuffer.append(vstup+"\n");
            }
            link = stringBuffer.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void nahraj(){
        final Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(nazov.getText()) && TextUtils.isEmpty(miesto.getText()) &&TextUtils.isEmpty(popis.getText()) && TextUtils.isEmpty(url.getText())){
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String name = nazov.getText().toString();
            String place = miesto.getText().toString();
            String description = popis.getText().toString();




            replyIntent.putExtra("name", name);
            replyIntent.putExtra("place", place);
            replyIntent.putExtra("description",description);

            replyIntent.putExtra("uri",link);

            int id = main.getIntExtra("id", -1);
            if (id != -1){
                replyIntent.putExtra("id", id);
            }

            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

}
