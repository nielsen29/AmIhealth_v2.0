package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Utils;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.Utils.MedidasPesoList;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by GITCE on 01/19/18.
 */

public class MedidasCinturaList {
    private Realm realm;
    private String nombre;
    private RealmResults<Cintura> realmResults;

    private int order;
    private ArrayList<MedidasCinturaList> array;


    public MedidasCinturaList() {
    }

    public MedidasCinturaList(String nombre, RealmResults<Cintura> realmResults, int order) {
        this.nombre = nombre;
        this.realmResults = realmResults;
        this.order = order;
    }

    public MedidasCinturaList(RealmResults<Cintura> realmResults) {
        this.realmResults = realmResults;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RealmResults<Cintura> getRealmResults() {
        return realmResults;
    }

    public void setRealmResults(RealmResults<Cintura> realmResults) {
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

    public ArrayList<MedidasCinturaList> getArray(int order){

        return array;
    }
}
