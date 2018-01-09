package com.amihealth.amihealth.Models;

import com.amihealth.amihealth.AppConfig.AppInicio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by amihealthmel on 11/12/17.
 */

public class Colors extends RealmObject {

    @PrimaryKey
    private int id;
    private String Color;

    public Colors() {
    }

    public Colors(String color) {
        Color = color;
        this.id = AppInicio.COLORHTA_ID.getAndIncrement();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
