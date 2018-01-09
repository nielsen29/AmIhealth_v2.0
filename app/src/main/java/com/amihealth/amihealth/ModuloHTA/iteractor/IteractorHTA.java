package com.amihealth.amihealth.ModuloHTA.iteractor;

import com.amihealth.amihealth.Models.MedidaHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 11/14/17.
 */

public interface IteractorHTA {
    void insert(MedidaHTA medidaHTA);
    void showbyId(MedidaHTA medidaHTA);
    void showall(RealmResults<MedidaHTA> realmResults);
    void edit(MedidaHTA medidaHTA);
    void drop(MedidaHTA medidaHTA);
    void mensaje(String mensaje);
    void acciones(int act);
    void error(String error);

    void getAll();

    void drobById(int id);

    void getById(int id);

    void editMedida(int id, int sys, int dis, int pulso);
}
