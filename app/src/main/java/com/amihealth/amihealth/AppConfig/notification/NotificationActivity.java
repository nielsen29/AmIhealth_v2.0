package com.amihealth.amihealth.AppConfig.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.amihealth.amihealth.Adaptadores.AdapterNotification;
import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.AmIHealthNotificacion;
import com.amihealth.amihealth.R;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity implements NotificationAction {


    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private AdapterNotification adapterNotification;
    private Realm realm;
    private RealmResults<AmIHealthNotificacion> realmResults;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        if(sessionManager.isLoggedIn()){
            realm = Realm.getDefaultInstance();
            realmResults = realm.where(AmIHealthNotificacion.class).findAll();
            getAllNotification();
        }

        adapterNotification = new AdapterNotification(this,realmResults,true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_notify);


    }

    public void getAllNotification(){
        Observable<Response<ArrayList<AmIHealthNotificacion>>> observable = new RetrofitAdapter()
                .getClientService(sessionManager.getUserLogin().get(SessionManager.AUTH)).get_notification();

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(arrayListResponse -> {
                    if(arrayListResponse.isSuccessful()){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.insertOrUpdate(arrayListResponse.body());
                            }
                        });
                        realm.close();

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
}
