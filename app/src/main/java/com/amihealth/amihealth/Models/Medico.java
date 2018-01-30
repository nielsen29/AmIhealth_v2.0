package com.amihealth.amihealth.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GITCE on 01/30/18.
 */

public class Medico extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("cedula")
    @Expose
    private String cedula;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("estado")
    @Expose
    private String estado;

    @SerializedName("idoneidad")
    @Expose
    private String idoneidad;


    public Medico() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdoneidad() {
        return idoneidad;
    }

    public void setIdoneidad(String idoneidad) {
        this.idoneidad = idoneidad;
    }


}
