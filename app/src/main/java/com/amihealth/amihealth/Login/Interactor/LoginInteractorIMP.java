package com.amihealth.amihealth.Login.Interactor;

import android.app.Activity;
import android.content.Context;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Login.Presenter.LoginPresenter;
import com.amihealth.amihealth.Login.Repositorio.LoginRepositorio;
import com.amihealth.amihealth.Login.Repositorio.LoginRepositorioIMP;
import com.amihealth.amihealth.Models.User;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amihealthmel on 08/16/17.
 */

public class LoginInteractorIMP implements LoginInteractor {

    private Configuracion CON;
    private LoginPresenter loginPresenter;
    private LoginRepositorio loginRepositorioIMP;

    public LoginInteractorIMP(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        this.loginRepositorioIMP = new LoginRepositorioIMP(loginPresenter);
        CON = new Configuracion();
    }

    public void tryLogin(final String email, String pass, Activity activity){
        final String mEmail = email;
        final String mPass = pass;
        final StringRequest st = new StringRequest(Request.Method.POST, CON.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof TimeoutError){

                }else if( error instanceof NetworkError){
                    loginPresenter.error("ERROR_TIMEOUT");

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("email", mEmail);
                parametros.put("password", mPass);

                return parametros;
            }
        };
        st.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        st.setTag("VolleyLOGIN");
        RequestQueue rq = Volley.newRequestQueue(activity);
        rq.add(st);

    }


    public void getJSON(String response){
        try{
            JSONObject login = new JSONObject(response);
            if (login.has("data")){
                JSONObject users = new JSONObject(login.getJSONObject("data").toString());
                User user  = new User();
                user.setId_InServer(users.getString("id"));
                user.setNombre(users.getString("nombre"));
                user.setApellido(users.getString("apellido"));
                user.setEmail(users.getString("email"));
                user.setImg(users.getString("img"));
                loginPresenter.getLoginUser(user);
            }else{
                if (login.get("error").equals("101")){
                    loginPresenter.errorEmail();
                }else{
                    loginPresenter.errorPass();
                }
            }

            /*if (JsonObj.has("data")){
                loginPresenter.getPaciente(response);
                loginPresenter.isLogin(true);

            }else{

                loginPresenter.isLogin(false);

            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void login(String mEmail, String mPass, Context context) {
       loginRepositorioIMP.login(mEmail,mPass,context);
    }

    @Override
    public void getLoginUser(User user) {

    }


}
