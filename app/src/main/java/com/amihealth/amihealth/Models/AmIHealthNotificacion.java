package com.amihealth.amihealth.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GITCE on 01/16/18.
 */

public class AmIHealthNotificacion extends RealmObject{


    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private  String body;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("medico")
    @Expose
    private Medico medico;




    public AmIHealthNotificacion() {
    }

    public AmIHealthNotificacion(String id, String title, String body, String icon, String to, String from, String value, String created_at, int count) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.icon = icon;
        this.to = to;
        this.from = from;
        this.value = value;
        this.created_at = created_at;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public static Medico parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Medico medico = gson.fromJson(response,Medico.class);
        return medico;
    }
}
