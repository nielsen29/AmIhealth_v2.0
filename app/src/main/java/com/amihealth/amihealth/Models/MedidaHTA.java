package com.amihealth.amihealth.Models;

import android.icu.util.Calendar;

import com.amihealth.amihealth.AppConfig.AppInicio;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by nielsen29 on 06/21/17.
 */

public class MedidaHTA extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("id_paciente")
    @Expose
    private String id_paciente;

    @SerializedName("SYS")
    @Expose
    private int SYS;

    @SerializedName("DIS")
    @Expose
    private int DIS;

    @SerializedName("pulso")
    @Expose
    private int Pulso;

    @SerializedName("descrip")
    @Expose
    private String descripcion;

    @SerializedName("rgb")
    @Expose
    private String rgb;

    @SerializedName("datetime")
    @Expose
    private String Date;

    @SerializedName("week")
    @Expose
    private int week;

    @SerializedName("month")
    @Expose
    private int mes;

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("day")
    @Expose
    private int dia;

    @SerializedName("sync")
    @Expose
    private int sync;


    private Date fecha;


    private String id_on_Server;

    private String UpdateS;
    private Date update_at;


    public MedidaHTA(){

    }

    public MedidaHTA(int SYS, int DIS, int pulso) {

        DateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        this.id     = UUID.randomUUID().toString();
        this.SYS    = SYS;
        this.DIS    = DIS;
        this.Pulso  = pulso;
        this.sync   = 0;
        String date = fd.format(java.util.Calendar.getInstance().getTime());
        this.Date = date;
        fecha = java.util.Calendar.getInstance().getTime();
        this.dia = fecha.getDay();
        this.week = java.util.Calendar.WEEK_OF_YEAR;
        this.mes = java.util.Calendar.MONTH;
        this.rgb ="#d0d0d0";
        getCat(SYS, DIS, pulso);

    }

    public MedidaHTA(String id_on_Server, int SYS, int DIS, int pulso, int sync, String date) {

        DateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id     = UUID.randomUUID().toString();
        this.SYS    = SYS;
        this.DIS    = DIS;
        this.Pulso  = pulso;
        this.sync   = sync;
        try {
            this.fecha  = fd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.id_on_Server = id_on_Server;
        getCat(SYS, DIS, pulso);

    }

    public void getCat(int SYS, int DIS, int pulso){
        HTAClassRange htaClassRange = new HTAClassRange();
        this.descripcion = String.valueOf(htaClassRange.getRango(SYS, DIS, pulso, "1")).toString();
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

    public int getSYS() {
        return SYS;
    }

    public void setSYS(int SYS) {
        this.SYS = SYS;
    }

    public int getDIS() {
        return DIS;
    }

    public void setDIS(int DIS) {
        this.DIS = DIS;
    }

    public int getPulso() {
        return Pulso;
    }

    public void setPulso(int pulso) {
        Pulso = pulso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public String getId_on_Server() {
        return id_on_Server;
    }

    public void setId_on_Server(String id_on_Server) {
        this.id_on_Server = id_on_Server;
    }

    public String getUpdateS() {
        return UpdateS;
    }

    public void setUpdateS(String updateS) {
        UpdateS = updateS;
    }

    public java.util.Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(java.util.Date update_at) {
        this.update_at = update_at;
    }
}
