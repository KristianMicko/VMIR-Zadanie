package com.example.user.app1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Zazitky {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "Nazov")
    private String nazov;
    @ColumnInfo(name = "Miesto")
    private String miesto;
    @ColumnInfo(name = "Popis")
    private String popis;
    @ColumnInfo(name = "Url")
    private String url;

    public Zazitky(String nazov, String miesto, String popis, String url) {
        this.nazov = nazov;
        this.miesto = miesto;
        this.popis = popis;
        this.url = url;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getMiesto() {
        return miesto;
    }

    public void setMiesto(String miesto) {
        this.miesto = miesto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }
}
