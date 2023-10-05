package com.orion.cepsearch.core.model.local;

public class CepResultItem {
    private String Cep = null;
    private String address = null;
    private String district = null;
    private String city = null;
    private String compl = null;
    private String srcApiRef = null;
    private String lat = null;
    private String lng = null;
    private Integer id = null;

    public CepResultItem(String cep, String address, String district, String city, String compl, String srcApiRef, String lat, String lng, Integer id) {
        Cep = cep;
        this.address = address;
        this.district = district;
        this.city = city;
        this.compl = compl;
        this.srcApiRef = srcApiRef;
        this.lat = lat;
        this.lng = lng;
        this.id = id;
    }

    public String getCep() {
        return Cep;
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

    public int getId() {
        return id;
    }
}
