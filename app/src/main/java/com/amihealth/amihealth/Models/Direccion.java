package com.amihealth.amihealth.Models;

/**
 * Created by amihealthmel on 08/04/17.
 */

public class Direccion {
    public int id_provincia;
    public int id_distrito;
    public int id_corregimiento;
    public String direccion;
    public String telefono;

    public Direccion(){

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
