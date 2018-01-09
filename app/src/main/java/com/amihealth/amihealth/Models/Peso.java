package com.amihealth.amihealth.Models;

import java.util.Date;

/**
 * Created by nielsen29 on 07/09/17.
 */

public class Peso {
    private int Id;
    private  int Id_paciente;
    private double peso;
    private double imc;
    private String descrip;
    private String rgb;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_paciente() {
        return Id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        Id_paciente = id_paciente;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
