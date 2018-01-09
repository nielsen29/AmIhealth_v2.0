package com.amihealth.amihealth.Models;

/**
 * Created by nielsen29 on 06/21/17.
 */

public class Corregimiento {
    private int Id;
    private String nombre;
    private  int id_distrito;

    public Corregimiento(int id, String nombre) {
        Id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return Id;
    }

    public int getId_distrito() {
        return id_distrito;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setId_distrito(int id_distrito) {
        this.id_distrito = id_distrito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
