package com.amihealth.amihealth.Models;

/**
 * Created by nielsen29 on 06/21/17.
 */

public class Distrito {
    private int Id;
    private String nombre;
    private int id_provincia;

    public Distrito(int id, String nombre) {
        Id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return Id;
    }

    public int getId_provincia() {
        return id_provincia;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
