package com.amihealth.amihealth.Models;

/**
 * Created by nielsen29 on 07/09/17.
 */

public class Estatura {

    private int Id;
    private int Id_paciente;
    private double estatura;

    public int getId_paciente() {
        return Id_paciente;
    }

    public double getEstatura() {
        return estatura;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setId_paciente(int id_paciente) {
        Id_paciente = id_paciente;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }
}
