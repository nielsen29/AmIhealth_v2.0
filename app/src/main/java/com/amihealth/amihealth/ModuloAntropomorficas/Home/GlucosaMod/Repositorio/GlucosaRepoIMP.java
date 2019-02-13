package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Repositorio;

import android.content.Context;
import android.util.Log;

import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.AppConfig.StaticError;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.Models.HbA1c;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.GlucosaPresenterIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.InterfaceGlucosaPresenter;
import com.amihealth.amihealth.Parsers.ParserError;
import com.google.gson.Gson;

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

public class GlucosaRepoIMP implements InterfaceGlucosaRepo {

    private Context context;
    private InterfaceGlucosaPresenter cinturaPresenter;
    private String token;
    private RetrofitAdapter retrofitAdapter;
    private Realm realm;
   // final RealmResults<HbA1c> results = realm.where(HbA1c.class).findAll();

    public GlucosaRepoIMP(Context context, GlucosaPresenterIMP cinturaPresenterIMP) {
        this.context = context;
        this.cinturaPresenter = cinturaPresenterIMP;
        this.token= new SessionManager(this.context).getUserLogin().get(SessionManager.AUTH).toString();
        retrofitAdapter = new RetrofitAdapter();
    }

    @Override
    public void RequestGetAll() {
        Observable<Response<ArrayList<Glucosa>>> observable = retrofitAdapter.getClientService(token).getMedidas_glucosa();
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
                    cinturaPresenter.OnErrorResponse(StaticError.CONEXION);
                });
    }

    @Override
    public void RequestGetAllHbA1c() {
        Log.d("F-GEThba:","------------->>>>>>>>> REPO GEThba");
        Observable<Response<ArrayList<HbA1c>>> observable = retrofitAdapter.getClientService(token).getMedidas_HbA1c();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(arrayListResponse -> {
                    if(arrayListResponse.isSuccessful()){
                        Log.d("F-GEThba:","------------->>>>>>>>> RESPONSE SUSS GEThba");
                        insertar_HbA1c_REALM(arrayListResponse.body());
                    }else{
                        Log.d("F-GEThba:","------------->>>>>>>>> RESPONSE ERROR GEThba");
                        EliminarFromHbA1c();
                        cinturaPresenter.OnErrorResponse(arrayListResponse.errorBody().string());
                    }
                }, throwable -> {
                    Log.i("ERROR", "RxJava2, HTTP Error: " + throwable.getMessage());
                    cinturaPresenter.OnErrorResponse(StaticError.CONEXION);
                });
    }


    @Override
    public void RequestInsert(Glucosa glucosa) {

        Observable<Response<Glucosa>> observable =retrofitAdapter.getClientService(token).insert_Glucosa(glucosa);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        cinturaResponse -> {
                            if (cinturaResponse.isSuccessful()){
                                insertar_REALM(cinturaResponse.body());
                            }else {
                                try{
                                    Gson gson = new Gson();
                                    ParserError error=new ParserError();
                                    error=gson.fromJson(cinturaResponse.errorBody().charStream(),ParserError.class);
                                    cinturaPresenter.OnErrorMedida(error.getMessage());

                                }catch(Exception error){

                                }

                            }
                        },
                        t -> {
                            Log.i("ERROR", "RxJava2, HTTP Error: " + t.getMessage());
                            cinturaPresenter.OnErrorResponse(t.getMessage());
                        }
                );


    }

    @Override
    public void RequestUpdate(Glucosa glucosa) {

        Observable<Response<Glucosa>> observable =retrofitAdapter.getClientService(token).edit_Glucosa(glucosa);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        cinturaResponse -> {
                            if (cinturaResponse.isSuccessful()){
                                insertar_REALM(cinturaResponse.body());
                            }else {
                                try{
                                    Gson gson = new Gson();
                                    ParserError error=new ParserError();
                                    error=gson.fromJson(cinturaResponse.errorBody().charStream(),ParserError.class);
                                    cinturaPresenter.OnErrorMedida(error.getMessage());

                                }catch(Exception error){

                                }
                            }
                        },
                        t -> {
                            Log.i("ERROR", "RxJava2, HTTP Error: " + t.getMessage());
                            cinturaPresenter.OnErrorResponse(t.getMessage());
                        }
                );

    }

    @Override
    public void RequestDelete(Glucosa glucosa) {

        Observable<Response<Glucosa>> observable =retrofitAdapter.getClientService(token).delete_Glucosa(glucosa);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        cinturaResponse -> {
                            if (cinturaResponse.isSuccessful()){
                                realm = Realm.getDefaultInstance();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(Glucosa.class).equalTo("id",cinturaResponse.body().getId()).findFirst().deleteFromRealm();
                                    }
                                });
                                realm.close();
                                EliminarFromHbA1c();
                                cinturaPresenter.OnDeleteResponse();
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
    public void insertar_Cintura_REALM(final ArrayList<Glucosa> medidas){

        int x = 0;
        final ArrayList<String> id = new ArrayList<>();
        final boolean encontrado = false;

        this.realm = Realm.getDefaultInstance();
        RealmResults<Glucosa> realmResults = realm.where(Glucosa.class).findAll();
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
            RealmResults<Glucosa> result = realmTo.where(Glucosa.class).findAll();


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
                            realm.where(Glucosa.class).equalTo("id",id.get(finalI)).findFirst().deleteFromRealm();
                        }
                    });
                    realmR.close();
                }
            }
            cinturaPresenter.OnGetAllResponse();

        }


    }

    public void insertar_REALM(Glucosa glucosa){
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(glucosa);
            }
        });
        realm.close();
        cinturaPresenter.OnGetAllResponse();
    }








    //////////////////////////////////////////////////////////////////////////////
    public void insertar_HbA1c_REALM(final ArrayList<HbA1c> medidas){

        int x = 0;
        final ArrayList<String> id = new ArrayList<>();
        final boolean encontrado = false;

        this.realm = Realm.getDefaultInstance();
        RealmResults<HbA1c> realmResults = realm.where(HbA1c.class).findAll();
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
            RealmResults<HbA1c> result = realmTo.where(HbA1c.class).findAll();


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
                            realm.where(HbA1c.class).equalTo("id",id.get(finalI)).findFirst().deleteFromRealm();
                        }
                    });
                    realmR.close();
                }
            }
            cinturaPresenter.OnGetAllResponse();

        }


    }


    public void EliminarFromHbA1c(){

        Log.d("F-GEThba:","------------->>>>>>>>> ELIMINAR REALM GEThba");
        this.realm = Realm.getDefaultInstance();

        RealmResults<HbA1c> realmResults = realm.where(HbA1c.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.deleteAllFromRealm();
                Log.d("F-HBA:","------------->>>>>>>>> ELIMINOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

            }
        });

        realm.close();
    }


}
