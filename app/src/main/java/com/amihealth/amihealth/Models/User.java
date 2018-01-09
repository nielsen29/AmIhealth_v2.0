package com.amihealth.amihealth.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by amihealthmel on 07/24/17.
 */

public class User extends RealmObject{

    //@SerializedName("id_")
    private int id_onPhone;

    private String id;


    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id_InServer;

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

    @SerializedName("paciente")
    @Expose
    private Paciente paciente;

    //@SerializedName("created_at")
    private String created_at;

    //@SerializedName("updated_at")
    private String updated_at;

    public User() {
    }


    public int getId_onPhone() {
        return id_onPhone;
    }

    public void setId_onPhone(int id_onPhone) {
        this.id_onPhone = id_onPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_InServer() {
        return id_InServer;
    }

    public void setId_InServer(String id_InServer) {
        this.id_InServer = id_InServer;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public static Paciente parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Paciente paciente = gson.fromJson(response,Paciente.class);
        return paciente;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
