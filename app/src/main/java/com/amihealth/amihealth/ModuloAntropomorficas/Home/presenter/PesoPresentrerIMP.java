package com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter;

import android.content.Context;

import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.PesoViewInterface;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.iteractor.PesoIteractorIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.iteractor.PesoIteractorInterface;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by GITCE on 01/11/18.
 */

public class PesoPresentrerIMP implements PesoPresenterInterface {

    private PesoViewInterface pesoViewInterface;
    private PesoIteractorInterface pesoIteractorInterface;
    private Context context;
    private Realm realm;

    public PesoPresentrerIMP(Context context,PesoViewInterface pesoViewInterface) {
        this.context = context;
        this.pesoViewInterface = pesoViewInterface;
        this.pesoIteractorInterface = new PesoIteractorIMP(context, this);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void OnGetAllResponse() {
        pesoViewInterface.OnGetAllResponse();
    }

    @Override
    public void OnInsertResponse() {

    }

    @Override
    public void OnDeleteResponse() {

    }

    @Override
    public void OnUpdateResponse() {

    }

    @Override
    public void OnErrorResponse(String error) {

    }

    @Override
    public void RequestGetAll() {
        pesoIteractorInterface.RequestGetAll();
    }

    @Override
    public void RequestInsertPeso(Peso peso) {
        pesoIteractorInterface.RequestInsertPeso(peso);
    }

    @Override
    public void RequestUpdatePeso(Peso peso) {

    }

    @Override
    public void RequestDeletePeso(Peso peso) {

    }
}
