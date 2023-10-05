package com.orion.cepsearch.core.model.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cep")
public class Cep {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "cep")
    private String cep;
    @ColumnInfo(name = "address")

    private String address = null;

    @ColumnInfo(name = "district")
    private String district = null;

    @ColumnInfo(name = "city")
    private String city = null;

    @ColumnInfo(name = "compl")
    private String compl = null;

    @ColumnInfo(name = "srcApiRef")
    private String srcApiRef = null;

    @ColumnInfo(name = "lat")
    private String lat = null;

    @ColumnInfo(name = "lng")
    private String lng = null;

    // getters and setters
    public Cep(String cep, String address, String district, String city, String compl, String srcApiRef, String lat, String lng) {
        this.cep = cep;
        this.address = address;
        this.district = district;
        this.city = city;
        this.compl = compl;
        this.srcApiRef = srcApiRef;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public String getAddress() {
        return address;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getCompl() {
        return compl;
    }

    public String getSrcApiRef() {
        return srcApiRef;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
