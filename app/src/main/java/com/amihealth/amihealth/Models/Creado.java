package com.amihealth.amihealth.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GITCE on 01/11/18.
 */

 public class Creado extends RealmObject{

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("timezone_type")
    @Expose
    private int timezone_type;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    public Creado() {
    }

    public Creado(String date, int timezone_type, String timezone) {
        this.date = date;
        this.timezone_type = timezone_type;
        this.timezone = timezone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(int timezone_type) {
        this.timezone_type = timezone_type;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
