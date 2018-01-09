package com.amihealth.amihealth.ModuloHTA.view.db;

import com.amihealth.amihealth.Models.MedidaHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 12/10/17.
 */

public interface RepositorioHta {


    //SERVER
    void getMedidas(int orden);
    void insertar(MedidaHTA medidaHTA);
    void borrar(String id);
    void actualizar(MedidaHTA medidaHTA);


    //UI
    void showAll(RealmResults<MedidaHTA> realmResults);
    void response(String string);

    void getMedidaById(String id);
}
