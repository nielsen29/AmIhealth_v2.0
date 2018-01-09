package com.amihealth.amihealth.ModuloHTA.repositorio;

import android.app.Activity;
import android.content.Context;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Contract.ContractHTA;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.iteractor.ImplementeIteractorHTA;
import com.amihealth.amihealth.ModuloHTA.iteractor.IteractorHTA;
import com.amihealth.amihealth.ModuloHTA.presenter.ImplementPresenterHTA;
import com.amihealth.amihealth.ModuloHTA.presenter.PresenterHTA;
import com.amihealth.amihealth.Parsers.ParserHTA;
import com.amihealth.amihealth.Parsers.ParserResponse;
import com.amihealth.amihealth.Volley.VolleySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Authenticator;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.SyncObjectServerFacade;
import io.realm.internal.network.AuthenticateRequest;

/**
 * Created by amihealthmel on 11/14/17.
 */

public class ImplementRespositorioHTA implements RepositorioHTA {

    private ImplementeIteractorHTA iteractorHTA;
    private ImplementPresenterHTA presenterHTA;
    private Realm realm;
    private SyncObjectServerFacade syncObjectServerFacade;
    private Activity contextd;
    private Context context;

    public ImplementRespositorioHTA(ImplementeIteractorHTA iteractorHTA, ImplementPresenterHTA presenterHTA, Context context) {
        this.iteractorHTA = iteractorHTA;
        this.presenterHTA = presenterHTA;
        this.context = context;
    }

    public ImplementRespositorioHTA(Context context){
        this.context = context;
    }

    private void insertarRealm(final MedidaHTA medidaHTA){

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(medidaHTA);
            }
        });
        realm.close();

        volleyREq(medidaHTA);

        presenterHTA.acciones(1);
    }

    private void evaluarRespuestaForUPDATE(String response, MedidaHTA medidaHTA){
        ParserResponse parserResponse = new ParserResponse(response);
        ArrayList<String> a = parserResponse.getParser();
        if (a.get(0).equals("OK") && a.get(1).equals("syncronized")){
            medidaHTA.setSync(1);
            updateRealm(medidaHTA);
        }
    }

    public void volleyREq(final MedidaHTA medidaHTA){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuracion.URL_NUEVA_MEDIDA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                evaluarRespuestaForUPDATE(response, medidaHTA);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ContractHTA.ID_PACIENTE, String.valueOf(medidaHTA.getId_paciente()));
                params.put(ContractHTA.SYS, String.valueOf(medidaHTA.getSYS()));
                params.put(ContractHTA.DIS, String.valueOf(medidaHTA.getDIS()));
                params.put(ContractHTA.PULSO, String.valueOf(medidaHTA.getPulso()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                params.put(ContractHTA.CREATED_AT, String.valueOf(dateFormat.format(medidaHTA.getFecha())));
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void getAllfromServer(String idUser){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuracion.URL_Lista_MEDIDA + idUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                insertTOrealm(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    private void updateRealm(final MedidaHTA medidaHTA){

        realm   = Realm.getDefaultInstance();
        realm   .executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.copyToRealmOrUpdate(medidaHTA);

            }
        });
        realm   .close();
    }


    private void updateVolley(final MedidaHTA medidaHTA){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuracion.URL_UpdateHTA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                evaluarRespuestaForUPDATE(response, medidaHTA);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ContractHTA._ID, String.valueOf(medidaHTA.getId_on_Server()));
                params.put(ContractHTA.ID_PACIENTE, String.valueOf(medidaHTA.getId_paciente()));
                params.put(ContractHTA.SYS, String.valueOf(medidaHTA.getSYS()));
                params.put(ContractHTA.DIS, String.valueOf(medidaHTA.getDIS()));
                params.put(ContractHTA.PULSO, String.valueOf(medidaHTA.getPulso()));
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void deleteRealm(final int id){

        realm   = Realm.getDefaultInstance();
        realm   .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        realm.where(MedidaHTA.class).equalTo("id",id).findFirst().deleteFromRealm();

                    }
                });
        realm   .close();
    }

    private void insertTOrealm(String datos){
        realm = Realm.getDefaultInstance();
        final ParserHTA parserHTA = new ParserHTA(datos);
        final ArrayList<MedidaHTA> medidaHTAs = parserHTA.getParserFROMserver();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //realm.where(MedidaHTA.class).equalTo(ContractHTA.SYNC,1).findAll().deleteAllFromRealm();
                realm.insert(medidaHTAs);
            }
        });
        realm.close();

    }

    private RealmResults<MedidaHTA> getAllFROMrealm(){
        getAllfromServer(new SessionManager(context).getUserLogin().get(SessionManager.KEY));
        realm       = Realm.getDefaultInstance();
        RealmResults<MedidaHTA> results;
        results     = realm.where(MedidaHTA.class).findAll();
        realm.close();
        return results;

    }

    private void forUpdate(MedidaHTA medidaHTA){

        //MedidaHTA medidaAux = getMedidaId(medidaHTA.getId());

        if(medidaHTA.getId_on_Server() != null){
            updateVolley(medidaHTA);

        }else{
            updateRealm(medidaHTA);
        }
    }

    public MedidaHTA getMedidaId(int id){
        realm = Realm.getDefaultInstance();
        MedidaHTA aux = realm.where(MedidaHTA.class).equalTo(ContractHTA._ID,id).findFirst();
        realm.close();

        return aux;
    }




    @Override
    public void insert(MedidaHTA medidaHTA) {
        insertarRealm(medidaHTA);
    }

    @Override
    public void showbyId(MedidaHTA medidaHTA) {
        presenterHTA.showbyId(medidaHTA);
    }

    @Override
    public void showall(RealmResults<MedidaHTA> realmResults) {

    }

    @Override
    public void edit(MedidaHTA medidaHTA) {
        forUpdate(medidaHTA);
    }

    @Override
    public void drop(MedidaHTA medidaHTA) {
        //deleteRealm(medidaHTA);
    }

    @Override
    public void dropbyId(int id) {
        deleteRealm(id);
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
    public void getById(int id) {
        realm = Realm.getDefaultInstance();
        this.showbyId(realm.where(MedidaHTA.class).equalTo(ContractHTA._ID,id).findFirst());
        realm.close();
    }

    @Override
    public void getAll() {
        presenterHTA.showall(getAllFROMrealm());
    }

    @Override
    public void editMedida(int id, int sys, int dis, int pulso) {

    }




}
