package com.amihealth.amihealth.ModuloHTA.view;

import android.view.MenuItem;
import android.widget.ImageButton;

import com.amihealth.amihealth.Models.MedidaHTA;

import io.realm.RealmResults;

/**
 * Created by amihealthmel on 11/14/17.
 */

public interface ViewHTA {
    void insert(MedidaHTA medidaHTA);
    void showbyId(MedidaHTA medidaHTA);
    void showall(RealmResults<MedidaHTA> realmResults);
    void edit(MedidaHTA medidaHTA);
    void drop(MedidaHTA medidaHTA);


    void mensaje(String mensaje);
    void acciones(int act);
    void error(String error);

    void menuOP(MenuItem item);
    void PopUpMenu(ImageButton boton);
}
