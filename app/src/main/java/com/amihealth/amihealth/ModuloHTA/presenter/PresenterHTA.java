package com.amihealth.amihealth.ModuloHTA.presenter;

import android.view.MenuItem;

import com.amihealth.amihealth.Models.MedidaHTA;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 11/14/17.
 */

public interface PresenterHTA {
    void insert(MedidaHTA medidaHTA);
    void showbyId(MedidaHTA medidaHTA);

    void getById(int id);

    void showall(RealmResults<MedidaHTA> realmResults);
    void edit(MedidaHTA medidaHTA);
    void dropbyId(int id);
    void drop(MedidaHTA medidaHTA);
    void mensaje(String mensaje);
    void acciones(int act);
    void error(String error);
    void getAll();

    void menuOP(MenuItem item);

    void editMedida(int id, int sys, int dis, int pulso);


}
