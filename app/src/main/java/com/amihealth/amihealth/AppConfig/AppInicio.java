package com.amihealth.amihealth.AppConfig;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Clasificaciones;
import com.amihealth.amihealth.Models.Colors;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.repositorio.ImplementRespositorioHTA;
import com.amihealth.amihealth.Parsers.ParserClasificacion;
import com.amihealth.amihealth.Parsers.ParserColor;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static com.amihealth.amihealth.Configuraciones.Configuracion.URL_GETCAT;
import static com.amihealth.amihealth.Configuraciones.Configuracion.URL_GETCOLORS;
import static io.realm.RealmConfiguration.*;

/**
 * Created by amihealthmel on 11/12/17.
 */

public class  AppInicio extends MultiDexApplication {

    //Archivo para configurar la App Esta Clase se inicia antes que todas


    // se generan IDS desde 0 o desde el ultimo registro de la SQlite Realm
    public static AtomicInteger MedidasHTA_ID       = new AtomicInteger();
    public static AtomicInteger CATHTA_ID       = new AtomicInteger();
    public static AtomicInteger COLORHTA_ID       = new AtomicInteger();

    public SessionManager sessionManager;

    private static AppInicio mainApplication;

    @Override
    public void onCreate() {


        sessionManager = new SessionManager(getApplicationContext());

        Realm.init(getApplicationContext());

        //metodo para configurar la base de datos x defecto

        SetUpRealm();

        //instancia de Realm por defecto

        Realm realm     = Realm.getDefaultInstance();

        //metodo para generar IDS se envia el objeto de Realm y la clase que extiende
        //--RealObj.

        /*MedidasHTA_ID   = getIdTabla(realm, MedidaHTA.class);

        CATHTA_ID       = getIdTabla(realm, Clasificaciones.class);

        COLORHTA_ID     = getIdTabla(realm, Colors.class);
*/

        realm           .close();
        getConfigTables();
        super           .onCreate();
        mainApplication = this;
    }

    private void SetUpRealm(){

        RealmConfiguration realmConfiguration;


        //se configura Realm x defecto

        realmConfiguration      = new RealmConfiguration.Builder()
                .name("amihealth")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm                   .setDefaultConfiguration(realmConfiguration);


    }



    public void getConfigTables(){

        getCategoriasServer();
        getColorsServer();
        getDataFromServer();
    }

    public  void getDataFromServer(){

    }




    private void getCategoriasServer(){
        StringRequest str = new StringRequest(Request.Method.GET, URL_GETCAT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                getDataParser(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"No DTA",Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());

        rq.add(str);
    }



    private void getColorsServer(){
        StringRequest str = new StringRequest(Request.Method.GET, URL_GETCOLORS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                getDataParserColor(response);

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"No DTA",Toast.LENGTH_LONG).show();

            }

        });

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());

        rq.add(str);

    }



    private void getDataParser(final String response) {
        Realm realm = Realm.getDefaultInstance();

       if(!getTabla(realm, Clasificaciones.class)){

           realm.executeTransaction(new Realm.Transaction() {

               @Override
               public void execute(Realm realm) {

                   ParserClasificacion parserClasificacion = new ParserClasificacion(response);

                   realm.insert(parserClasificacion.getParser());

               }

           });

           Toast.makeText(getApplicationContext(),"GETDTA",Toast.LENGTH_LONG).show();

       }else{

           Toast.makeText(getApplicationContext(),"No DTA",Toast.LENGTH_LONG).show();

       }

       realm.close();
    }



    private void getDataParserColor(final String response) {

        Realm realm = Realm.getDefaultInstance();

        if(!getTabla(realm, Colors.class)){

            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    ParserColor parserColor = new ParserColor(response);

                    realm.insert(parserColor.getParser());

                }

            });

            Toast.makeText(getApplicationContext(),"GETDTA",Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(getApplicationContext(),"No DTA",Toast.LENGTH_LONG).show();

        }

        realm.close();
    }




    //Este metodo se crea con instancias <T> osea no se define la clase pero si la extencion
    //que se utilizara en el metodo.
   /* private <T extends RealmObject> AtomicInteger getIdTabla(Realm realm, Class<T> classT){
        RealmResults<T> realmResults = realm.where(classT).findAll();
        return
                (realmResults.size() > 0) ? new AtomicInteger(realmResults.max("id").intValue())
                        : new AtomicInteger();
    }*/



    private <T extends RealmObject> boolean getTabla(Realm realm, Class<T> classT){
        RealmResults<T> realmResults = realm.where(classT).findAll();
        return
                (realmResults.size() > 0) ? true:false;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    public static synchronized AppInicio getInstance() {
        return mainApplication;
    }





}
