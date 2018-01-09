package com.amihealth.amihealth.ModuloHTA.view.presenter;

import android.content.Context;
import android.view.MenuItem;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.view.iteractor.IteractorHta;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.iteractor.ImpIteractorHta;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 12/10/17.
 */

public class ImpPresenterHta implements PresenterHta {
    private InterfaceHta interfaceHta;
    private IteractorHta iteractorHTA;
    private Context context;

    public ImpPresenterHta(InterfaceHta interfaceHta, Context context) {
        this.interfaceHta = interfaceHta;
        this.iteractorHTA = new ImpIteractorHta(this, context);
        this.context = context;

    }

    @Override
    public void getMedidas(int order) {
        iteractorHTA.getMedidas(order);
    }

    @Override
    public void insertar(MedidaHTA medidaHTA) {
        iteractorHTA.insertar(medidaHTA);
    }

    @Override
    public void borrar(String id) {
        iteractorHTA.borrar(id);
    }

    @Override
    public void actualizar(MedidaHTA medidaHTA) {
        iteractorHTA.actualizar(medidaHTA);
    }

    @Override
    public void getMedidabyId(String id) {
        iteractorHTA.getMedidaById(id);
    }


    // RETORNO A UI
    @Override
    public void showAll(RealmResults<MedidaHTA> medidaHTAs) {
        interfaceHta.showAll(medidaHTAs);
    }

    @Override
    public void acciones(String act) {
        interfaceHta.acciones(act);
    }

    @Override
    public void menuOP(MenuItem item) {
        interfaceHta.menuOP(item);
    }

    @Override
    public void response(String string) {
        interfaceHta.mensaje(string);
    }

    @Override
    public void errorRetrofit(int code) {
        interfaceHta.errorRetrofit(code);
    }
}
