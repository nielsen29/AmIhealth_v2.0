package com.amihealth.amihealth.Views.Fragmentos.Registro.presenter;

import android.content.Context;

import com.amihealth.amihealth.Models.Corregimiento;
import com.amihealth.amihealth.Models.Distrito;
import com.amihealth.amihealth.Models.Etnia;
import com.amihealth.amihealth.Models.Provincia;
import com.amihealth.amihealth.Models.Registro;

import java.util.ArrayList;

/**
 * Created by amihealthmel on 09/12/17.
 */

public interface RegistroPresenterINT {


    /* --------------------- INTERACTOR ----------------------------*/
    void getProvincia(Context context);
    void getDistritos(Context context, int id_provincia);
    void getCorregimientos(Context context, int id_distrito);
    void getEtnias(Context context);
    void newUser(Context context, Registro registro);

    /* --------------------- VIEWS ----------------------------*/
    void returnProvincia(ArrayList<Provincia> provincias); // view
    void returnDistritos(ArrayList<Distrito> distritos); //view
    void returnCorregimientos(ArrayList<Corregimiento> corregimientos); //view
    void returnEtnias(ArrayList<Etnia> etnias); //view

    void responseNewUser();
}
