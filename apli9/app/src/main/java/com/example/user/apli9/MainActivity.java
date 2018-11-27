package com.example.user.apli9;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ZazitkyViewModel mZazitkyViewModel;
    public static final int ADDRECORDACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecord.class);
                startActivityForResult(intent,ADDRECORDACTIVITY );
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final ZazitkyListAdapter adapter = new ZazitkyListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mZazitkyViewModel = ViewModelProviders.of(this).get(ZazitkyViewModel.class);
        mZazitkyViewModel.getGetAll().observe(this, new Observer<List<Zazitky>>() {
            @Override
            public void onChanged(List<Zazitky> zazitkies) {
                adapter.setmZazitky(zazitkies);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRECORDACTIVITY && resultCode == RESULT_OK) {
            Bundle odkaz = data.getExtras();
            String nazov = odkaz.getString("name");
            String miesto = odkaz.getString("place");
            String popis = odkaz.getString("description");
            String uri = odkaz.getString("uri");
            Zazitky zazitky = new Zazitky(nazov,miesto,popis,uri);
            mZazitkyViewModel.insert(zazitky);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Neulozilo sa to",
                    Toast.LENGTH_LONG).show();
        }
    }
}
