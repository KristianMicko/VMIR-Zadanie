package com.example.user.app1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class sixth extends AppCompatActivity {
    private ZazitkyViewModel mZazitkyViewModel;
    public static final int ADDRECORDACTIVITY = 1;
    public static final int EDITRECORDACTIVITY = 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vymaz_vsetko:
                mZazitkyViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sixth.this, AddRecord.class);
                startActivityForResult(intent, ADDRECORDACTIVITY);
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
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                mZazitkyViewModel.delete(adapter.getZazitkyAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new ZazitkyListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Zazitky zazitky) {
                Intent intent = new Intent(sixth.this, AddRecord.class);
                intent.putExtra("id", zazitky.getId());
                intent.putExtra("name", zazitky.getNazov());
                intent.putExtra("place", zazitky.getMiesto());
                intent.putExtra("description", zazitky.getPopis());
                intent.putExtra("uri", zazitky.getUrl());
                startActivityForResult(intent, EDITRECORDACTIVITY);
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
            Zazitky zazitky = new Zazitky(nazov, miesto, popis, uri);
            mZazitkyViewModel.insert(zazitky);
        } else if (requestCode == EDITRECORDACTIVITY && resultCode == RESULT_OK) {
            int id = data.getIntExtra("id", -1);
            if (id == -1){
                Toast.makeText(this,"Nahravka nemoze byt ulozena",Toast.LENGTH_LONG).show();
                return;
            }
            Bundle odkaz = data.getExtras();
            String nazov = odkaz.getString("name");
            String miesto = odkaz.getString("place");
            String popis = odkaz.getString("description");
            String uri = odkaz.getString("uri");
            Zazitky zazitky = new Zazitky(nazov, miesto, popis, uri);
            zazitky.setId(id);
            mZazitkyViewModel.update(zazitky);
            Toast.makeText(this,"Nahravka je upravena",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Neulozilo sa to",
                    Toast.LENGTH_LONG).show();
        }
    }
}
