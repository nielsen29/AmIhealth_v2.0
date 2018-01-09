package com.amihealth.amihealth.ModuloHTA.iteractor;

import android.app.Activity;
import android.content.Context;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.presenter.ImplementPresenterHTA;
import com.amihealth.amihealth.ModuloHTA.presenter.PresenterHTA;
import com.amihealth.amihealth.ModuloHTA.repositorio.ImplementRespositorioHTA;
import com.amihealth.amihealth.ModuloHTA.repositorio.RepositorioHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 11/15/17.
 */

public class ImplementeIteractorHTA implements IteractorHTA {

    private ImplementPresenterHTA       presenterHTA;
    private ImplementRespositorioHTA    repositorioHTA;
    private Context                    context;

    public ImplementeIteractorHTA(ImplementPresenterHTA presenterHTA, Context context) {

        this.presenterHTA   = presenterHTA;
        this.context        = context;
        this.repositorioHTA = new ImplementRespositorioHTA(this,this.presenterHTA,this.context);
    }

    @Override
    public void insert(MedidaHTA medidaHTA) {
        repositorioHTA.insert(medidaHTA);
    }

    @Override
    public void showbyId(MedidaHTA medidaHTA) {

    }

    @Override
    public void showall(RealmResults<MedidaHTA> realmResults) {

    }

    @Override
    public void edit(MedidaHTA medidaHTA) {
        repositorioHTA.edit(medidaHTA);
    }

    @Override
    public void drop(MedidaHTA medidaHTA) {
        repositorioHTA.drop(medidaHTA);
    }

    @Override
    public void mensaje(String mensaje) {

    }

    @Override
    public void acciones(int act) {

    }

    @Override
    public void error(String error) {

    }

    @Override
    public void getAll() {
        repositorioHTA.getAll();
    }

    @Override
    public void drobById(int id) {
        repositorioHTA.dropbyId(id);
    }

    @Override
    public void getById(int id) {
        repositorioHTA.getById(id);
    }

    @Override
    public void editMedida(int id, int sys, int dis, int pulso) {
        repositorioHTA.editMedida(id, sys, dis, pulso);
    }
}
