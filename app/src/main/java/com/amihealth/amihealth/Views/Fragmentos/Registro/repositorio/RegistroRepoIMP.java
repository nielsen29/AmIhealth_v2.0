package com.amihealth.amihealth.Views.Fragmentos.Registro.repositorio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Login.View.LoginActivity;
import com.amihealth.amihealth.Models.Registro;
import com.amihealth.amihealth.Views.Fragmentos.Registro.iteractor.RegistroIteractorIMP;
import com.amihealth.amihealth.Views.Fragmentos.Registro.iteractor.RegistroIteractorINT;
import com.amihealth.amihealth.Views.Fragmentos.Registro.presenter.RegistroPresenterINT;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by amihealthmel on 09/12/17.
 */

public class RegistroRepoIMP implements RegistroRepoINT {
    private RegistroPresenterINT presenterINT;
    private RegistroIteractorINT iteractorINT;
    private RegistroIteractorIMP imp;
    private ProgressDialog d;

    public RegistroRepoIMP(RegistroPresenterINT presenterINT, RegistroIteractorINT iteractorINT) {
        this.presenterINT = presenterINT;
        this.iteractorINT = iteractorINT;
    }

    @Override
    public void getProvincia(final Context context) {

        StringRequest st = new StringRequest(Request.Method.GET, Configuracion.URL_GET_PROVINCIA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //getJSONProvincia(response);
                iteractorINT.getProvinciaResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No tiene Conexion", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(context);
        rq.add(st);
    }

    @Override
    public void getDistritos(final Context context, int id_provincia) {
        StringRequest st = new StringRequest(Request.Method.GET, Configuracion.URL_DISTRITO + String.valueOf(id_provincia), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //getJSONProvincia(response);
                iteractorINT.getDistritosResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No tiene Conexion", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(context);
        rq.add(st);

    }

    @Override
    public void getCorregimientos(final Context context, int id_distrito) {
        StringRequest st = new StringRequest(Request.Method.GET, Configuracion.URL_CORREGIMIENTO + String.valueOf(id_distrito), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //getJSONProvincia(response);
                iteractorINT.getCorregimientosResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No tiene Conexion", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(context);
        rq.add(st);
    }

    @Override
    public void getEtnias(final Context context) {
        StringRequest st = new StringRequest(Request.Method.GET, Configuracion.URL_ETNIAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //getJSONProvincia(response);
                iteractorINT.getEtniasResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No tiene Conexion", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(context);
        rq.add(st);
    }

    @Override
    public void newUser(final Context context, final Registro registro) {

        StringRequest srt = new StringRequest(Request.Method.POST, Configuracion.URL_REGISTRO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                registroCompleto(response,context);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                switch (error.networkResponse.statusCode){
                    case 422:

                        try{
                            String responseError = new String(error.networkResponse.data, "utf-8");

                        }catch (UnsupportedEncodingException e){

                        }

                        Toast.makeText(context,error.getLocalizedMessage(),LENGTH_LONG).show();
                        break;
                    default:
                        //Toast.makeText(context,"AKIIIII BUG",LENGTH_LONG).show();
                        break;
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cedula", registro.getCed()
                        +"-"+registro.getTomo()
                        +"-"+registro.getAsiento());
                params.put("nombre", registro.getNombre());
                params.put("apellido",registro.getApellido());
                params.put("email",registro.getEmail());
                params.put("password",registro.getPass());
                params.put("password_confirmation",registro.getPassConf());
                params.put("movil",registro.getMovil());
                params.put("direccion",registro.getDireccion());
                params.put("fecha_nacimiento",registro.getFecha());
                params.put("sexo", String.valueOf(registro.getId_sexo()));
                params.put("id_provincia", String.valueOf(registro.getId_provincia()));
                params.put("id_distrito",String.valueOf(registro.getId_distrito()));
                params.put("id_corregimiento",String.valueOf(registro.getId_corregimiento()));
                params.put("id_etnia",String.valueOf(registro.getId_etnia()));
                params.put("estatura",registro.getAltura());
                params.put("peso",registro.getPeso());
                return params;
            }
        };
        RequestQueue rd = Volley.newRequestQueue(context);
        rd.add(srt);
    }
    public void registroCompleto(String response, Context context){
        try{
            JSONObject JsonObj = new JSONObject(response);
            if(JsonObj.has("message")){
                Toast.makeText(context,JsonObj.toString(),LENGTH_LONG).show();
            }else{
                //Toast.makeText(context,"",LENGTH_LONG).show();
                presenterINT.responseNewUser();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
