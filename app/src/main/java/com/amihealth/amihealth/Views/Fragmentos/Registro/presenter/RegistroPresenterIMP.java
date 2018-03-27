package com.amihealth.amihealth.Views.Fragmentos.Registro.presenter;

import android.content.Context;

import com.amihealth.amihealth.Models.Corregimiento;
import com.amihealth.amihealth.Models.Distrito;
import com.amihealth.amihealth.Models.Etnia;
import com.amihealth.amihealth.Models.Provincia;
import com.amihealth.amihealth.Models.Registro;
import com.amihealth.amihealth.Views.Fragmentos.Registro.iteractor.RegistroIteractorIMP;
import com.amihealth.amihealth.Views.Fragmentos.Registro.iteractor.RegistroIteractorINT;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.RegistroViewINT;

import java.util.ArrayList;

/**
 * Created by amihealthmel on 09/12/17.
 */

public class RegistroPresenterIMP implements RegistroPresenterINT {

    private RegistroViewINT viewINT;
    private RegistroIteractorINT iteractorINT;

    public RegistroPresenterIMP(RegistroViewINT viewINT) {
        this.viewINT = viewINT;
        this.iteractorINT = new RegistroIteractorIMP(this);
    }

    @Override
    public void getProvincia(Context context) {
        iteractorINT.getProvincia(context);
    }

    @Override
    public void getDistritos(Context context, int id_provincia) {
        iteractorINT.getDistritos(context, id_provincia);
    }

    @Override
    public void getCorregimientos(Context context ,int id_distrito) {

        iteractorINT.getCorregimientos(context, id_distrito);

    }

    @Override
    public void getEtnias(Context context) {
        iteractorINT.getEtnias(context);
    }

    @Override
    public void newUser(Context context, Registro registro) {
        iteractorINT.newUser(context, registro);
    }

    @Override
    public void returnProvincia(ArrayList<Provincia> provincias) {
        viewINT.getProvincia(provincias);
    }

    @Override
    public void returnDistritos(ArrayList<Distrito> distritos) {
        viewINT.getDistritos(distritos);
    }

    @Override
    public void returnCorregimientos(ArrayList<Corregimiento> corregimientos) {
        viewINT.getCorregimientos(corregimientos);
    }

    @Override
    public void returnEtnias(ArrayList<Etnia> etnias) {
        viewINT.getEtnias(etnias);

    }

    @Override
    public void responseNewUser() {
        viewINT.responseNewUser();
    }
}
