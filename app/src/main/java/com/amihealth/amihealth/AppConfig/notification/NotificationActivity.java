package com.amihealth.amihealth.AppConfig.notification;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.amihealth.amihealth.Adaptadores.AdapterNotification;
import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.AmIHealthNotificacion;
import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.R;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity implements NotificationAction {


    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private AdapterNotification adapterNotification;
    private Realm realm;
    private RealmResults<AmIHealthNotificacion> realmResults;
    private ConstraintLayout constraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_notify);
        constraintLayout = (ConstraintLayout) findViewById(R.id.null_obj);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        if(sessionManager.isLoggedIn()){

            getAllNotification();
            realm = Realm.getDefaultInstance();
            cargarRealmResult();
            adapterNotification = new AdapterNotification(getApplicationContext(),this,realmResults.sort("created_at", Sort.DESCENDING),true);


        }
        showtoolbar("Notificaciones",true);

        Log.d("NOTIFICAION RESULT:", realmResults.toString());



        recyclerView.setAdapter(adapterNotification);

    }

    public void showtoolbar(String titulo, boolean mUpbtn){
        Toolbar toolbar         = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar()   .setTitle(titulo);
        getSupportActionBar()   .setDisplayHomeAsUpEnabled(mUpbtn);

    }

    private void cargarRealmResult(){
        realmResults = realm.where(AmIHealthNotificacion.class).findAll();
        if(realmResults.isEmpty()){
            constraintLayout.setVisibility(View.VISIBLE);
           // recyclerView.setVisibility(View.GONE);
        }else{
            constraintLayout.setVisibility(View.GONE);
            //recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void getAllNotification(){
        Observable<Response<ArrayList<AmIHealthNotificacion>>> observable = new RetrofitAdapter()
                .getClientService(sessionManager.getUserLogin().get(SessionManager.AUTH)).get_notification();

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(arrayListResponse -> {
                    if(arrayListResponse.isSuccessful()){
                        insertarREALM(arrayListResponse.body());
                        cargarRealmResult();
                        //adapterNotification.notifyDataSetChanged();

                        //realmResults = realm.where(AmIHealthNotificacion.class).findAll();
                        //adapterNotification = new AdapterNotification(this,realmResults,true);

                    }
                }, throwable -> {

                    Log.i("ERROR", "RxJava2, HTTP Error: " + throwable.getMessage());

                });


    }


    @Override
    public void aceptar(String id) {
        Toast.makeText(getApplicationContext(),"ACEPTO LA SOLICITUD: "+id,Toast.LENGTH_LONG).show();
    }

    @Override
    public void declinar(String id) {
        Toast.makeText(getApplicationContext(),"DECLINO LA SOLICITUD: "+id,Toast.LENGTH_LONG).show();
    }


    public void insertarREALM(final ArrayList<AmIHealthNotificacion> notificacions){

        int x = 0;
        final ArrayList<String> id = new ArrayList<>();
        final boolean encontrado = false;

        this.realm = Realm.getDefaultInstance();
        RealmResults<AmIHealthNotificacion> realmResults = realm.where(AmIHealthNotificacion.class).findAll();
        if(realmResults.isEmpty()){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(notificacions);
                }
            });
            realm.close();
        }else{

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(notificacions);
                }
            });
            realm.close();
            Realm realmTo = Realm.getDefaultInstance();
            RealmResults<AmIHealthNotificacion> result = realmTo.where(AmIHealthNotificacion.class).findAll();

            while (x == 0){
                for (int i = 0; i < result.size(); i++) {
                    String busco = result.get(i).getId().toString();

                    for (int j = 0; j < notificacions.size() ; j++) {
                        if(notificacions.get(j).getId().equals(busco)){
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
                            realm.where(AmIHealthNotificacion.class).equalTo("id",id.get(finalI)).findFirst().deleteFromRealm();
                        }
                    });
                    realmR.close();
                }
            }

        }


    }
}
