package com.amihealth.amihealth.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

/**
 * Created by GITCE on 01/16/18.
 */

public class AmIHealthNotificacion {

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

    public AmIHealthNotificacion(String title, String body, String icon, String to, String from, String value) {
        this.title = title;
        this.body = body;
        this.icon = icon;
        this.to = to;
        this.from = from;
        this.value = value;
    }

    public AmIHealthNotificacion() {
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
}
