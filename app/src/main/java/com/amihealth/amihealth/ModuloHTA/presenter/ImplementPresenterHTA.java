package com.amihealth.amihealth.ModuloHTA.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.iteractor.ImplementeIteractorHTA;
import com.amihealth.amihealth.ModuloHTA.iteractor.IteractorHTA;
import com.amihealth.amihealth.ModuloHTA.view.ViewHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 11/15/17.
 */

public class ImplementPresenterHTA implements PresenterHTA {

    private ViewHTA viewHTA;
    private ImplementeIteractorHTA iteractorHTA;
    private Context context;

    public ImplementPresenterHTA(ViewHTA viewHTA, Context context) {

        this.viewHTA = viewHTA;
        this.context = context;
        iteractorHTA = new ImplementeIteractorHTA(this,this.context);

    }

    @Override
    public void insert(MedidaHTA medidaHTA) {
        iteractorHTA.insert(medidaHTA);
    }

    @Override
    public void showbyId(MedidaHTA medidaHTA) {

    }

    @Override
    public void getById(int id) {
        iteractorHTA.getById(id);
    }

    @Override
    public void showall(RealmResults<MedidaHTA> realmResults) {
        viewHTA.showall(realmResults);
    }

    @Override
    public void edit(MedidaHTA medidaHTA) {
        iteractorHTA.edit(medidaHTA);
    }

    @Override
    public void dropbyId(int id) {
        iteractorHTA.drobById(id);
    }

    @Override
    public void drop(MedidaHTA medidaHTA) {
        iteractorHTA.drop(medidaHTA);
    }

    @Override
    public void mensaje(String mensaje) {

    }

    @Override
    public void acciones(int act) {
        viewHTA.acciones(act);
    }

    @Override
    public void error(String error) {

    }

    @Override
    public void getAll() {
        iteractorHTA.getAll();
    }

    @Override
    public void menuOP(MenuItem item) {
        viewHTA.menuOP(item);
    }

    @Override
    public void editMedida(int id, int sys, int dis, int pulso) {
        iteractorHTA.editMedida(id, sys, dis, pulso);
    }
}
