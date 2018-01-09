package com.amihealth.amihealth.Models;

/**
 * Created by amihealthmel on 08/07/17.
 */

public class UserGeneral {
    public String nombre;
    public String cedula;
    public String apellido;
    public String fecha_nacimiento;
    public int sexo;
    public int id_etnia;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getId_etnia() {
        return id_etnia;
    }

    public void setId_etnia(int id_etnia) {
        this.id_etnia = id_etnia;
    }
}
