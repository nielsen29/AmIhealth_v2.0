package com.amihealth.amihealth.Configuraciones;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amihealthmel on 09/15/17.
 */

public class Buscar{

    private boolean valido;

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public Buscar() {
    }

    public void tryLogin(final String email, Context activity){
        final String mEmail = email;
        StringRequest st = new StringRequest(Request.Method.POST, Configuracion.URL_GETEMAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("email", mEmail);
                return parametros;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(activity);
        rq.add(st);

    }


    public void getJSON(String response){
        try{
            JSONObject datos = new JSONObject(response);
            if(datos.getJSONArray("data") != null){
                setValido(false);
            }else{
                setValido(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
