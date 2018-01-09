package com.amihealth.amihealth.ModuloHTA.view.presenter;

import android.view.MenuItem;

import com.amihealth.amihealth.Models.MedidaHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 12/10/17.
 */

public interface PresenterHta {

    //SERVER
    void getMedidas(int order);
    void insertar(MedidaHTA medidaHTA);
    void borrar(String id);
    void actualizar(MedidaHTA medidaHTA);
    void getMedidabyId(String id);

    //UI
    void showAll(RealmResults<MedidaHTA> medidaHTAs);
    void acciones(String act);
    void menuOP(MenuItem item);
    void response(String string);
    void errorRetrofit(int code);
}
