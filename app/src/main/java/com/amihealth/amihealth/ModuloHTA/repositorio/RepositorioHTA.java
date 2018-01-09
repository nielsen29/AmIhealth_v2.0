package com.amihealth.amihealth.ModuloHTA.repositorio;

import com.amihealth.amihealth.Models.MedidaHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 11/14/17.
 */

public interface RepositorioHTA {
    void insert(MedidaHTA medidaHTA);
    void showbyId(MedidaHTA medidaHTA);
    void showall(RealmResults<MedidaHTA> realmResults);
    void edit(MedidaHTA medidaHTA);
    void drop(MedidaHTA medidaHTA);
    void dropbyId(int id);
    void mensaje(String mensaje);
    void acciones(int act);
    void error(String error);

    void getById(int id);

    void getAll();

    void editMedida(int id, int sys, int dis, int pulso);
}
