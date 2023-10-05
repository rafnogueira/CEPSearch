package com.orion.cepsearch.core.model.remote;

import com.google.gson.annotations.SerializedName;

public class ErrorJson {

    @SerializedName("status")
    private int status;

    @SerializedName("ok")
    private boolean ok;

    @SerializedName("message")
    private String message;

    @SerializedName("statusText")
    private String statusText;

    // getters e setters (ou apenas getters) para as propriedades acima
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public boolean isOk() { return ok; }
    public void setOk(boolean ok) { this.ok = ok; }
    public String getMessage() { return message;}

    public void setMessage(String message) { this.message = message; }
    public String getStatusText() { return statusText; }
    public void setStatusText(String statusText) { this.statusText = statusText;}


}
