package com.amihealth.amihealth.ModuloHTA.view.db;

import android.app.Service;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.amihealth.amihealth.ApiAmIHealth.InternetConnection;
import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.view.iteractor.IteractorHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.PresenterHta;
import com.amihealth.amihealth.Parsers.ParserHTA;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amihealthmel on 12/10/17.
 */

public class ImpRepoHta implements RepositorioHta {

    private IteractorHta iteractorHta;
    private PresenterHta presenterHta;
    private Context context;
    private String token;



    public ImpRepoHta(IteractorHta iteractorHta, PresenterHta presenterHta, Context context) {
        this.iteractorHta = iteractorHta;
        this.presenterHta = presenterHta;
        this.context = context;
        this.token = new SessionManager(context).getUserLogin().get(SessionManager.AUTH);

    }

    @Override
    public void getMedidas(int orden) {


        //syncRealmToServ();

        //Toast.makeText(context, token,Toast.LENGTH_LONG).show();
        final ArrayList<MedidaHTA> medidaHTAArrayList = new ArrayList<>();
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<ArrayList<MedidaHTA>> call = retrofitAdapter.getClientService(token).getMedidas();
        call.enqueue(new Callback<ArrayList<MedidaHTA>>() {
            @Override
            public void onResponse(Call<ArrayList<MedidaHTA>> call, Response<ArrayList<MedidaHTA>> response) {
                if(response.isSuccessful()){
                    /*for(MedidaHTA medidaHTA: response.body()){
                        medidaHTAArrayList.add(medidaHTA);
                    }*/

                    insertTOrealmFromServer(response.body());
                }
                else{
                    presenterHta.errorRetrofit(response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MedidaHTA>> call, Throwable t) {
                if(!call.isCanceled()){
                    presenterHta.showAll(getAllFROMrealm());
                    presenterHta.response("Error de Conexion se usaran datos locales");
                }
                //Toast.makeText(context, (String) String.valueOf(t.hashCode()),Toast.LENGTH_LONG).show();
            }
        });

       /*
       *  Call<JsonArray> call = retrofitAdapter.getClientService(token).getMedidas();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Toast.makeText(context, response.body().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
       * */

    }

    @Override
    public void insertar(final MedidaHTA medidaHTA) {
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<MedidaHTA> call = retrofitAdapter.getClientService(token).nuevaHTA(medidaHTA);
        call.enqueue(new Callback<MedidaHTA>() {
            @Override
            public void onResponse(Call<MedidaHTA> call, Response<MedidaHTA> response) {
                if(response.isSuccessful()){
                    realmFromServer(response.body());
                }else{
                    realmFromServer(medidaHTA);
                }
            }

            @Override
            public void onFailure(Call<MedidaHTA> call, Throwable t) {

                //presenterHta.response("Sincronizando datos...!");

            }
        });
        presenterHta.showAll(getAllFROMrealm());
    }

    @Override
    public void borrar(final String id) {

        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<JsonObject> call = retrofitAdapter.getClientService(token).delete_hta(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    borrar_de_realm(id);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



    }
    public void borrar_de_realm(final String id){

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(MedidaHTA.class).equalTo("id",id).findFirst().deleteFromRealm();
            }
        });
        realm.close();
        presenterHta.showAll(getAllFROMrealm());
    }

    @Override
    public void actualizar(MedidaHTA medidaHTA) {

        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<MedidaHTA> call = retrofitAdapter.getClientService(token).updateHTA(medidaHTA);
        call.enqueue(new Callback<MedidaHTA>() {
            @Override
            public void onResponse(Call<MedidaHTA> call, Response<MedidaHTA> response) {
                if(response.isSuccessful()){
                    realmFromServer(response.body());
                }
            }
            @Override
            public void onFailure(Call<MedidaHTA> call, Throwable t) {

            }
        });
        presenterHta.showAll(getAllFROMrealm());
    }

    @Override
    public void showAll(RealmResults<MedidaHTA> realmResults) {
        presenterHta.showAll(realmResults);
    }

    @Override
    public void response(String string) {

    }

    @Override
    public void getMedidaById(String id) {
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<MedidaHTA> call = retrofitAdapter.getClientService(token).getMedidaHTAbyID(id);
        call.enqueue(new Callback<MedidaHTA>() {
            @Override
            public void onResponse(Call<MedidaHTA> call, Response<MedidaHTA> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"aki listoooooo",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,response.message(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<MedidaHTA> call, Throwable t) {

            }
        });
    }

    public void insertTOrealmFromServer(final ArrayList<MedidaHTA> medidaHTA){
        int x = 0;
        final ArrayList<String> id = new ArrayList<>();
        final boolean encontrado = false;
        if(medidaHTA.isEmpty()){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.where(MedidaHTA.class).findAll().deleteAllFromRealm();
                }
            });
            realm.close();
        }else{
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(medidaHTA);
                }
            });
            realm.close();
            Realm realmTo = Realm.getDefaultInstance();
            RealmResults<MedidaHTA> result = realmTo.where(MedidaHTA.class).findAll();

            while (x == 0){
                for (int i = 0; i < result.size(); i++) {
                    String busco = result.get(i).getId().toString();

                    for (int j = 0; j < medidaHTA.size() ; j++) {
                        if(medidaHTA.get(j).getId().equals(busco)){
                            x = 1;
                            //Toast.makeText(context,result.get(i).getId(),Toast.LENGTH_LONG).show();
                        }

                    }
                    if(x == 0){
                        id.add(busco);
                    }
                    x = 0;

                }
                x =1;
            }

            if(id.isEmpty()){
                presenterHta.response("Datos Listos!");
            }else{
                presenterHta.response("Sincronizando datos...!");
                for (int i = 0; i < id.size() ; i++) {
                    Realm realmR = Realm.getDefaultInstance();
                    final int finalI = i;
                    realmR.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.where(MedidaHTA.class).equalTo("id",id.get(finalI)).findFirst().deleteFromRealm();
                        }
                    });
                    realmR.close();
                }
            }

        }

        presenterHta.showAll(getAllFROMrealm());
    }
    public void realmFromServer(final MedidaHTA medidaHTA){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(medidaHTA);
            }
        });
        realm.close();
    }

    public RealmResults<MedidaHTA> getAllFROMrealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MedidaHTA> results;
        results = realm.where(MedidaHTA.class).findAll();
        return results;
    }

    public void syncRealmToServ(){
        final RealmResults<MedidaHTA> results;
        final ArrayList<MedidaHTA> medidaHTAs = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance();
        results = realm.where(MedidaHTA.class).equalTo("sync", 0).findAll();
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        for(int i = 0; i < results.size() - 1; i++){

            Toast.makeText(context,results.get(i).getId(),Toast.LENGTH_SHORT).show();

            Call<MedidaHTA> call = retrofitAdapter.getClientService(token).nuevaHTA(results.get(i));

            final int finalI = i;
            call.enqueue(new Callback<MedidaHTA>() {
                @Override
                public void onResponse(Call<MedidaHTA> call, final Response<MedidaHTA> response) {
                    if(response.isSuccessful()){
                        //medidaHTAs.add(response.body());
                        Toast.makeText(context,response.body().getId(),Toast.LENGTH_SHORT).show();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.insertOrUpdate(response.body());
                                }
                            });
                    }
                }

                @Override
                public void onFailure(Call<MedidaHTA> call, Throwable t) {

                }
            });
        }
    }

    public void deleteOneRealm(final String id){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(MedidaHTA.class).equalTo("id", id).findFirst();
            }
        });
        realm.close();
    }





}
