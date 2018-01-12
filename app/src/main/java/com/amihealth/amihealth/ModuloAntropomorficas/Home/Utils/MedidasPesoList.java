package com.amihealth.amihealth.ModuloAntropomorficas.Home.Utils;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.Models.MedidasHTAList;
import com.amihealth.amihealth.Models.Peso;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by GITCE on 01/11/18.
 */

public class MedidasPesoList {
    private Realm realm;
    private String nombre;
    private RealmResults<Peso> realmResults;

    private int order;
    private ArrayList<MedidasPesoList> array;


    public MedidasPesoList() {
    }

    public MedidasPesoList(String nombre, RealmResults<Peso> realmResults, int order) {
        this.nombre = nombre;
        this.realmResults = realmResults;
        this.order = order;
    }

    public MedidasPesoList(RealmResults<Peso> realmResults) {
        this.realmResults = realmResults;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RealmResults<Peso> getRealmResults() {
        return realmResults;
    }

    public void setRealmResults(RealmResults<Peso> realmResults) {
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

    public ArrayList<MedidasPesoList> getArray(int order){
        this.array = new ArrayList<>();
        this.realm = Realm.getDefaultInstance();
        RealmResults<Peso> lol;
        switch (order){
            case 0:
                lol = realmResults.where().distinct("week").sort("week", Sort.DESCENDING);
                break;
            case 1:
                lol = realmResults.where().distinct("mes").sort("month",Sort.DESCENDING);
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
                    this.array.add(new MedidasPesoList(
                            "SEMANA",realmResults.where().equalTo("week",lol.get(i).getWeek()).findAll(),lol.get(i).getWeek()
                    ));
                    break;
                case 1:
                    this.array.add(new MedidasPesoList(
                            "MES",realmResults.where().equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
                case 2:
                    this.array.add(new MedidasPesoList(
                            "YEAR",realmResults.where().equalTo("year",lol.get(i).getYear()).findAll(),lol.get(i).getYear()
                    ));
                    break;
                default:
                    this.array.add(new MedidasPesoList(
                            "lol",realmResults.where().equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
            }
        }

        return array;
    }
}
