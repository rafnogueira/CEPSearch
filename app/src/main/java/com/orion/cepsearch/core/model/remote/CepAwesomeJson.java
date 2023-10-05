package com.orion.cepsearch.core.model.remote;

import com.google.gson.annotations.SerializedName;

public class CepAwesomeJson  extends  CepJson{
    @SerializedName("address_type")
    private String addressType;
    @SerializedName("address_name")
    private String addressName;
    @SerializedName("address")
    private String address;
    @SerializedName("state")
    private String state;
    @SerializedName("district")
    private String district;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;
    @SerializedName("city")
    private String city;
    @SerializedName("city_ibge")
    private String cityIbge;

    public String getAddressType() {
        return addressType;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public String getDistrict() {
        return district;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getCity() {
        return city;
    }

    public String getCityIbge() {
        return cityIbge;
    }
}
