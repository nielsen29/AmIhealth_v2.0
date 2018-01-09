package com.amihealth.amihealth.ModuloHTA.view;

import android.view.MenuItem;

import com.amihealth.amihealth.Models.MedidaHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 12/10/17.
 */

public interface InterfaceHta {

    void getMedidas(int orden);
    void showAll(RealmResults<MedidaHTA> medidaHTAs);

    void acciones(String action);
    void menuOP(MenuItem item);

    void insertar(MedidaHTA medidaHTA);

    void showLoad();
    void hiddenLoad();
    void errorRetrofit(int code);


    void mensaje(String mensaje);
    public interface InterfaceHtaDetalle{
        void getMedida(String id);
    }

}
