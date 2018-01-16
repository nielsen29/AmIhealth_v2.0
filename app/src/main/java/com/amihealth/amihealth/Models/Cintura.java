package com.amihealth.amihealth.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GITCE on 01/15/18.
 */

public class Cintura extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("id_paciente")
    @Expose
    private  String id_paciente;

    @SerializedName("cintura")
    @Expose
    private double cintura;

    @SerializedName("ica")
    @Expose
    private double ica;

    @SerializedName("descrip")
    @Expose
    private String descrip;

    @SerializedName("rgb")
    @Expose
    private String rgb;

    @SerializedName("sync")
    @Expose
    private int sync;

    @SerializedName("created_at")
    @Expose
    private Creado created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("month")
    @Expose
    private int month;

    @SerializedName("week")
    @Expose
    private int week;

    @SerializedName("day")
    @Expose
    private int day;

    @SerializedName("dayOfWeek")
    @Expose
    private int dayOfWeek;

    @SerializedName("datetime")
    @Expose
    private String datetime;

    public Cintura() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(String id_paciente) {
        this.id_paciente = id_paciente;
    }

    public double getCintura() {
        return cintura;
    }

    public void setCintura(String cintura) {
        this.cintura = Double.valueOf(cintura);
    }

    public double getIca() {
        return ica;
    }

    public void setIca(String ica) {
        this.ica = Double.valueOf(ica);
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public Creado getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Creado created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
