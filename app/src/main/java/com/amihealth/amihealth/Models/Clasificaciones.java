package com.amihealth.amihealth.Models;

import com.amihealth.amihealth.AppConfig.AppInicio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by amihealthmel on 11/12/17.
 */

public class Clasificaciones extends RealmObject {

    @PrimaryKey
    private int id;
    private String descrip;

    public Clasificaciones() {
    }

    public Clasificaciones(String descrip) {
        this.id = AppInicio.CATHTA_ID.incrementAndGet();
        this.descrip = descrip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
