package com.orion.cepsearch.core.model.remote;

import com.google.gson.annotations.SerializedName;

public class APICepJson extends CepJson {
    @SerializedName("state")
    private String state;

    @SerializedName("city")
    private String city;

    @SerializedName("district")
    private String district;

    @SerializedName("address")
    private String address;

    @SerializedName("status")
    private int status;

    @SerializedName("ok")
    private boolean ok;

    @SerializedName("statusText")
    private String statusText;


    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public int getStatus() {
        return status;
    }

    public boolean isOk() {
        return ok;
    }

    public String getStatusText() {
        return statusText;
    }
}

