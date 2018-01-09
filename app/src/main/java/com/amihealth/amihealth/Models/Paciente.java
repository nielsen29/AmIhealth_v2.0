package com.amihealth.amihealth.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by amihealthmel on 01/04/18.
 */

public class Paciente extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("movil")
    @Expose
    private String movil;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("fecha_nacimiento")
    @Expose
    private String fecha_nacimiento;

    @SerializedName("sexo")
    @Expose
    private int sexo;

    @SerializedName("id_provincia")
    @Expose
    private int id_provincia;

    @SerializedName("id_distrito")
    @Expose
    private int id_distrito;

    @SerializedName("id_corregimiento")
    @Expose
    private int id_corregimiento;

    @SerializedName("id_etnia")
    @Expose
    private int id_etnia;

    @SerializedName("estatura")
    @Expose
    private int estatura;

    public Paciente() {
    }

    public Paciente(String id, String movil, String direccion, String fecha_nacimiento, int id_provincia, int id_distrito, int id_corregimiento, int id_etnia) {
        this.id = id;
        this.movil = movil;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.id_provincia = id_provincia;
        this.id_distrito = id_distrito;
        this.id_corregimiento = id_corregimiento;
        this.id_etnia = id_etnia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }

    public int getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(int id_distrito) {
        this.id_distrito = id_distrito;
    }

    public int getId_corregimiento() {
        return id_corregimiento;
    }

    public void setId_corregimiento(int id_corregimiento) {
        this.id_corregimiento = id_corregimiento;
    }

    public int getId_etnia() {
        return id_etnia;
    }

    public void setId_etnia(int id_etnia) {
        this.id_etnia = id_etnia;
    }

    public int getEstatura() {
        return estatura;
    }

    public void setEstatura(int estatura) {
        this.estatura = estatura;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
