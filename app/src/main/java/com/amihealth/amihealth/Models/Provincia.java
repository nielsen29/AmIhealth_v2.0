package com.amihealth.amihealth.Models;

/**
 * Created by amihealthmel on 08/08/17.
 */

public class Provincia {
    public Provincia(){

    }
    public Provincia(int id, String nombre){
        this.id = id;
        this.nombre = nombre;

    }
    private int id;
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
