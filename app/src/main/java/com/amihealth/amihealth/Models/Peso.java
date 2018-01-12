package com.amihealth.amihealth.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nielsen29 on 07/09/17.
 */

public class Peso extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("id_paciente")
    @Expose
    private  String id_paciente;

    @SerializedName("peso")
    @Expose
    private double peso;

    @SerializedName("imc")
    @Expose
    private double imc;

    @SerializedName("descrip")
    @Expose
    private String descrip;

    @SerializedName("rgb")
    @Expose
    private String rgb;

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


    public Peso() {
    }

    public Peso(String id, String id_paciente, double peso) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.peso = peso;
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = Double.valueOf(peso);
    }

    public double getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = Double.valueOf(imc);
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

    public Creado getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Creado created_at) {
        this.created_at = created_at;
    }

    public static Creado parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Creado created_at = gson.fromJson(response,Creado.class);
        return created_at;
    }
}
