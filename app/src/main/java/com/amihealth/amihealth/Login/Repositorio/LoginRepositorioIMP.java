package com.amihealth.amihealth.Login.Repositorio;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.amihealth.amihealth.ApiAmIHealth.ApiModelToken;
import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.ApiAmIHealth.TokenModel;
import com.amihealth.amihealth.BuildConfig;
import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Login.Presenter.LoginPresenter;
import com.amihealth.amihealth.Models.User;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amihealthmel on 08/17/17.
 */

public class LoginRepositorioIMP implements LoginRepositorio {
    LoginPresenter presenter;
    //private RetrofitAdapter retrofitAdapter;
    private Context context;

    public LoginRepositorioIMP(LoginPresenter presenter) {
        this.presenter = presenter;
        //this.retrofitAdapter = new RetrofitAdapter();
    }


    @Override
    public void login(String mEmail, String mPass, final Context context) {
        this.context = context;
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<TokenModel> call = retrofitAdapter.getClienteLogin().login(new ApiModelToken(mEmail,mPass));
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if (response.isSuccessful()){

                    presenter.tokenReturn(response.body());
                    getUserSERVER(response.body().getAccess_token());
                    //Toast.makeText(context,response.body().getAccess_token(),Toast.LENGTH_LONG).show();
                }else{
                    presenter.error(String.valueOf(response.code()).toString());
                    //Toast.makeText(context,"Error CREDENCIAL",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                presenter.error(String.valueOf(t.getMessage()));
            }
        });
    }

    public void getUserSERVER(String token){
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();

        Call<User> call = retrofitAdapter.getClientService(token).getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()){
                    presenter.getLoginUser(response.body());
                }else
                {
                    presenter.error(String.valueOf(response.code()).toString());

                }
                //Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                presenter.error(String.valueOf(t.getMessage()));
            }
        });
    }

    @Override
    public void validarCredenciales(String mEmail, String mPass) {

    }

    @Override
    public void message() {

    }
}
