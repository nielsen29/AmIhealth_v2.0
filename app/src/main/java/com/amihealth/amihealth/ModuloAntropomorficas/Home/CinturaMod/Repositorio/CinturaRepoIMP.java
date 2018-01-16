package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Repositorio;

import android.content.Context;
import android.util.Log;

import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Presenter.CinturaPresenterIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Presenter.InterfaceCinturaPresenter;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;

/**
 * Created by GITCE on 01/15/18.
 */

public class CinturaRepoIMP implements InterfaceCinturaRepo {

    private Context context;
    private InterfaceCinturaPresenter cinturaPresenter;
    private String token;
    private RetrofitAdapter retrofitAdapter;
    private Realm realm;


    public CinturaRepoIMP(Context context, CinturaPresenterIMP cinturaPresenterIMP) {
        this.context = context;
        this.cinturaPresenter = cinturaPresenterIMP;
        this.token= new SessionManager(this.context).getUserLogin().get(SessionManager.AUTH).toString();
        retrofitAdapter = new RetrofitAdapter();
    }

    @Override
    public void RequestGetAll() {
        Observable<Response<ArrayList<Cintura>>> observable = retrofitAdapter.getClientService(token).getMedidas_cintura();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(arrayListResponse -> {
                    if(arrayListResponse.isSuccessful()){
                        insertar_Cintura_REALM(arrayListResponse.body());
                    }else{
                        cinturaPresenter.OnErrorResponse(arrayListResponse.errorBody().string());
                    }
                }, throwable -> {
                    Log.i("ERROR", "RxJava2, HTTP Error: " + throwable.getMessage());
                    cinturaPresenter.OnErrorResponse(throwable.getMessage());

                });

    }

    @Override
    public void RequestInsertPeso(Cintura cintura) {

        Observable<Response<Cintura>> observable =retrofitAdapter.getClientService(token).insert_Cintura(cintura);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        cinturaResponse -> {
                            if (cinturaResponse.isSuccessful()){
                                insertar_REALM(cinturaResponse.body());
                            }else {
                                cinturaPresenter.OnErrorResponse(cinturaResponse.errorBody().string());
                            }
                        },
                        t -> {
                            Log.i("ERROR", "RxJava2, HTTP Error: " + t.getMessage());
                            cinturaPresenter.OnErrorResponse(t.getMessage());
                        }
                );


    }

    @Override
    public void RequestUpdatePeso(Cintura cintura) {

        Observable<Response<Cintura>> observable =retrofitAdapter.getClientService(token).edit_Cintura(cintura);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        cinturaResponse -> {
                            if (cinturaResponse.isSuccessful()){
                                insertar_REALM(cinturaResponse.body());
                            }else {
                                cinturaPresenter.OnErrorResponse(cinturaResponse.errorBody().string());
                            }
                        },
                        t -> {
                            Log.i("ERROR", "RxJava2, HTTP Error: " + t.getMessage());
                            cinturaPresenter.OnErrorResponse(t.getMessage());
                        }
                );

    }

    @Override
    public void RequestDeletePeso(Cintura cintura) {

        Observable<Response<Cintura>> observable =retrofitAdapter.getClientService(token).delete_Cintura(cintura);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        cinturaResponse -> {
                            if (cinturaResponse.isSuccessful()){
                                realm = Realm.getDefaultInstance();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(Cintura.class).equalTo("id",cinturaResponse.body().getId()).findFirst().deleteFromRealm();
                                    }
                                });
                                realm.close();
                            }else {
                                cinturaPresenter.OnErrorResponse(cinturaResponse.errorBody().string());
                            }
                        },
                        t -> {
                            Log.i("ERROR", "RxJava2, HTTP Error: " + t.getMessage());
                            cinturaPresenter.OnErrorResponse(t.getMessage());
                        }
                );

    }

    /**********************************************************************************************
     *                  METODOS DE ACCESO A DATOS para REALM
     *********************************************************************************************/

    //INSERT peso
    public void insertar_Cintura_REALM(final ArrayList<Cintura> medidas){

        int x = 0;
        final ArrayList<String> id = new ArrayList<>();
        final boolean encontrado = false;

        this.realm = Realm.getDefaultInstance();
        RealmResults<Cintura> realmResults = realm.where(Cintura.class).findAll();
        if(realmResults.isEmpty()){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(medidas);
                }
            });
            realm.close();
            cinturaPresenter.OnGetAllResponse();
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
            RealmResults<Cintura> result = realmTo.where(Cintura.class).findAll();


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
                            realm.where(Cintura.class).equalTo("id",id.get(finalI)).findFirst().deleteFromRealm();
                        }
                    });
                    realmR.close();
                }
            }
            cinturaPresenter.OnGetAllResponse();

        }


    }

    public void insertar_REALM(Cintura cintura){
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(cintura);
            }
        });
        realm.close();
        cinturaPresenter.OnGetAllResponse();
    }



}
