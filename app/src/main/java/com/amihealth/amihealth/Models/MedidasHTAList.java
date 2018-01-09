package com.amihealth.amihealth.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by amihealthmel on 12/18/17.
 */

public class MedidasHTAList {
    private Realm realm;
    private String nombre;
    private RealmResults<MedidaHTA> realmResults;
    private int order;
    private ArrayList<MedidasHTAList> array;

    public MedidasHTAList() {
    }

    public MedidasHTAList(String nombre, RealmResults<MedidaHTA> realmResults, int order) {
        this.nombre = nombre;
        this.realmResults = realmResults;
        this.order = order;
    }

    public MedidasHTAList(RealmResults<MedidaHTA> realmResults) {
        this.realmResults = realmResults;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RealmResults<MedidaHTA> getRealmResults() {
        return realmResults;
    }

    public void setRealmResults(RealmResults<MedidaHTA> realmResults) {
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

    public ArrayList<MedidasHTAList> getArray(int order){
        this.array = new ArrayList<>();
        this.realm = Realm.getDefaultInstance();
        RealmResults<MedidaHTA> lol;
        switch (order){
            case 0:
                lol = realmResults.where().distinct("week").sort("week", Sort.DESCENDING);
                break;
            case 1:
                lol = realmResults.where().distinct("mes").sort("mes",Sort.DESCENDING);
                break;
            case 2:
                lol = realmResults.where().distinct("year").sort("year",Sort.DESCENDING);
                break;
            default:
                lol = realmResults.where().distinct("week").sort("week",Sort.DESCENDING);
                break;
        }


        for (int i = 0; i < lol.size() ; i++) {
            switch (order){
                case 0:
                    this.array.add(new MedidasHTAList(
                            "SEMANA",realmResults.where().equalTo("week",lol.get(i).getWeek()).findAll(),lol.get(i).getWeek()
                    ));
                    break;
                case 1:
                    this.array.add(new MedidasHTAList(
                            "MES",realmResults.where().equalTo("mes",lol.get(i).getMes()).findAll(),lol.get(i).getMes()
                    ));
                    break;
                case 2:
                    this.array.add(new MedidasHTAList(
                            "YEAR",realmResults.where().equalTo("year",lol.get(i).getYear()).findAll(),lol.get(i).getYear()
                    ));
                    break;
                default:
                    this.array.add(new MedidasHTAList(
                            "lol",realmResults.where().equalTo("mes",lol.get(i).getMes()).findAll(),lol.get(i).getMes()
                    ));
                    break;
            }
        }

        return array;
    }
}
