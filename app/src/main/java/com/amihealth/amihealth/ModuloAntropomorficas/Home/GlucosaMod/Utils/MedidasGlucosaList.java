package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Utils;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by GITCE on 01/19/18.
 */

public class MedidasGlucosaList {
    private Realm realm;
    private String nombre;
    private RealmResults<Glucosa> realmResults;

    private int order;
    private ArrayList<MedidasGlucosaList> array;


    public MedidasGlucosaList() {
    }

    public MedidasGlucosaList(String nombre, RealmResults<Glucosa> realmResults, int order) {
        this.nombre = nombre;
        this.realmResults = realmResults;
        this.order = order;
    }

    public MedidasGlucosaList(RealmResults<Glucosa> realmResults) {
        this.realmResults = realmResults;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RealmResults<Glucosa> getRealmResults() {
        return realmResults;
    }

    public void setRealmResults(RealmResults<Glucosa> realmResults) {
        this.realmResults = realmResults;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return nombre + ": " + order;
    }

    public ArrayList<MedidasGlucosaList> getArray(int order){

        return array;
    }
}
