package com.orion.cepsearch.core.model.remote;

import com.google.gson.annotations.SerializedName;

public class CepJson
{
    @SerializedName("cep")
    private String cep;
    @SerializedName("code")
    private String code;
    @SerializedName("ddd")
    private String ddd;

    public String getCep() {return cep;}
    public void setCep(String cep) {this.cep = cep;}
    public String getCode() { return code;}
    public void setCode(String code) { this.code = code;}
    public String getDdd() {return ddd;}
    public void setDdd(String ddd) {this.ddd = ddd;}
}
