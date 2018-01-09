package com.amihealth.amihealth.Configuraciones;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.amihealth.amihealth.AppConfig.AppInicio;
import com.amihealth.amihealth.Login.View.LoginActivity;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.repositorio.ImplementRespositorioHTA;

import java.util.HashMap;

import io.realm.Realm;

/**
 * Created by amihealthmel on 09/14/17.
 */

public class SessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE                        = 0;
    private static final String PREF_NAME   = "AmIHEALTHPrefLOGIN";
    private static final String IS_LOGIN    = "IsLoggedIn";
    public  static final String KEY_NAME    = "nombre";
    public static  final String KEY_EMAIL   = "email";
    public static final String KEY          = "ID_USER";
    public static final String TABLE_RANGE  = "TABLE_RANGE";
    public static final String USERS_EMAIL  = "EMAILS_USERS";
    public  static  final  String AUTH      = "AUTH";

    public SessionManager(Context context){

        this._context   = context;
        preferences     = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor          = preferences.edit();

    }

    public void crearSessionLogin(String id,String nombre, String email){

        editor  .putBoolean(IS_LOGIN,true);

        editor  .putString(KEY,id);

        editor  .putString(KEY_NAME,nombre);

        editor  .putString(KEY_EMAIL,email);

        editor  .putString(TABLE_RANGE,"1");


        editor  .commit();

    }

    public void setAuth(String auth){
        editor  .putBoolean(IS_LOGIN,true);
        editor.putString(AUTH, auth);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){

            Intent  i   = new Intent(_context, LoginActivity.class);
                    i   .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i   .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context    .startActivity(i);

        }else{

        }
    }

    public HashMap<String,String> getUserLogin(){

        HashMap<String,String> user     = new HashMap<String, String>();

        user    .put(KEY,preferences.getString(KEY,null));
        user    .put(KEY_NAME,preferences.getString(KEY_NAME,null));
        user    .put(KEY_EMAIL,preferences.getString(KEY_EMAIL,null));
        user    .put(AUTH, preferences.getString(AUTH,null));
        return user;
    }

    public void logoutUser(){

        editor          .clear();
        editor          .commit();
        Realm realm     = Realm.getDefaultInstance();
        realm           .executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.deleteAll();
                            }
                        });
        realm           .close();


        Intent i    = new Intent(_context, LoginActivity.class);
        i           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i           .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context    .startActivity(i);
    }



    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN,false);
    }
}
