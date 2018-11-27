package com.example.user.app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mapa, pocasie, SoS, NahrajObrazok, ZaznamenajZazitok;
    private static final int ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        akcia();
        pogoda();
        pomoc();
        upload();

    }



    public void upload() {
        NahrajObrazok = (Button)findViewById(R.id.Nahraj_Obrazok);
        NahrajObrazok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ijk= new Intent(getApplicationContext(),fifth.class);
                startActivity(ijk);
                onPause();
            }
        });
    }

    public void pomoc() {
        SoS = (Button)findViewById(R.id.SoS);
        SoS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ij = new Intent(getApplicationContext(),fourth.class);
                startActivity(ij);
                onPause();
            }
        });
    }


    public void akcia() {
        mapa = (Button)findViewById(R.id.mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),second.class);
                startActivity(i);

                onPause();
            }
        });


    }

    public void pogoda(){
        pocasie = (Button)findViewById(R.id.pocasie);
        pocasie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),third.class);
                startActivity(i2);
                onPause();
            }
        });
    }


}
