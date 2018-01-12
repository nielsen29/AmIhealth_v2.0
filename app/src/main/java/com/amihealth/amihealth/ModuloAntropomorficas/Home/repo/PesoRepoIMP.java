package com.amihealth.amihealth.ModuloAntropomorficas.Home.repo;

import android.content.Context;

import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresenterInterface;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresentrerIMP;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GITCE on 01/11/18.
 */

public class PesoRepoIMP implements PesoRepoInterface {

    private Context context;
    private PesoPresenterInterface pesoPresenterInterface;
    private String token;
    RetrofitAdapter retrofitAdapter;
    private Realm realm;

    public PesoRepoIMP(Context context, PesoPresentrerIMP pesoPresentrerIMP) {
        this.context = context;
        this.pesoPresenterInterface = pesoPresentrerIMP;
        this.token= new SessionManager(this.context).getUserLogin().get(SessionManager.AUTH).toString();
        retrofitAdapter = new RetrofitAdapter();
    }


    /**********************************************************************************************
     *                  METODOS DE ACCESO A DATOS DEL SERVIDOR
     *********************************************************************************************/

    public void getAllmedidas(){
        Call<ArrayList<Peso>> call = retrofitAdapter.getClientService(this.token).getMedidas_Peso();
        call.enqueue(new Callback<ArrayList<Peso>>() {
            @Override
            public void onResponse(Call<ArrayList<Peso>> call, Response<ArrayList<Peso>> response) {
                if (response.isSuccessful()){
                    insertar_pesos_REALM(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Peso>> call, Throwable t) {

            }
        });
    }

    public void insert_Peso_server(Peso peso){
        Call<Peso> call = retrofitAdapter.getClientService(token).insert_Peso(peso);
        call.enqueue(new Callback<Peso>() {
            @Override
            public void onResponse(Call<Peso> call, Response<Peso> response) {
                if (response.isSuccessful()){
                    insertar_REALM(response.body());
                }
            }

            @Override
            public void onFailure(Call<Peso> call, Throwable t) {

            }
        });

    }

    @Override
    public void RequestGetAll() {
        getAllmedidas();
    }

    @Override
    public void RequestInsertPeso(Peso peso) {
        insert_Peso_server(peso);
    }

    @Override
    public void RequestUpdatePeso(Peso peso) {

    }

    @Override
    public void RequestDeletePeso(Peso peso) {

    }



    /**********************************************************************************************
     *                  METODOS DE ACCESO A DATOS para REALM
     *********************************************************************************************/

    //INSERT peso
    public void insertar_pesos_REALM(final ArrayList<Peso> medidas){

        int x = 0;
        final ArrayList<String> id = new ArrayList<>();
        final boolean encontrado = false;

        this.realm = Realm.getDefaultInstance();
        RealmResults<Peso> realmResults = realm.where(Peso.class).findAll();
        if(realmResults.isEmpty()){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(medidas);
                }
            });
            realm.close();
            pesoPresenterInterface.OnGetAllResponse();
        }else{

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(medidas);
                }
            });
            realm.close();
            Realm realmTo = Realm.getDefaultInstance();
            RealmResults<Peso> result = realmTo.where(Peso.class).findAll();


            while (x == 0){
                for (int i = 0; i < result.size(); i++) {
                    String busco = result.get(i).getId().toString();

                    for (int j = 0; j < medidas.size() ; j++) {
                        if(medidas.get(j).getId().equals(busco)){
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

                //COMUNICAR SE CARGARON LOS DATOS


            }else{

                //presenterHta.response("Sincronizando datos...!");

                for (int i = 0; i < id.size() ; i++) {
                    Realm realmR = Realm.getDefaultInstance();
                    final int finalI = i;
                    realmR.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.where(Peso.class).equalTo("id",id.get(finalI)).findFirst().deleteFromRealm();
                        }
                    });
                    realmR.close();
                }
            }
            pesoPresenterInterface.OnGetAllResponse();

        }


    }

    public void insertar_REALM(Peso peso){
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(peso);
            }
        });
        realm.close();
    }



}
