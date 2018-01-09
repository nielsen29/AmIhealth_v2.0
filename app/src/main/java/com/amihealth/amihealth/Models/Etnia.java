package com.amihealth.amihealth.Models;

/**
 * Created by nielsen29 on 06/21/17.
 */

public class Etnia {
    private int Id;
    private String nombre;


    public Etnia(int id, String nombre) {
        Id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
