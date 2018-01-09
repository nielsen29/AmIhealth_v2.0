package com.amihealth.amihealth.ApiAmIHealth;

import android.content.Context;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.realm.rx.RxObservableFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amihealthmel on 11/27/17.
 */

public class RetrofitAdapter {

    public ServiceRetrofit getClientService(final String token){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Accept","application/json")
                            .addHeader("Authorization", "Bearer "+token)
                            .build();
                    return chain.proceed(newRequest);

            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuracion.SERVER)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                ))
                .build();

        return  retrofit.create(ServiceRetrofit.class);
    }

    public ServiceRetrofit getService(final String token){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request newRequest = chain.request().newBuilder()
                        .addHeader("Accept","application/json")
                        .addHeader("Authorization", "Bearer "+token)
                        .build();
                return chain.proceed(newRequest);

            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuracion.SERVER)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit.create(ServiceRetrofit.class);
    }



    public  ServiceRetrofit getClienteLogin(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuracion.ROOT_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ServiceRetrofit.class);
    }
}
