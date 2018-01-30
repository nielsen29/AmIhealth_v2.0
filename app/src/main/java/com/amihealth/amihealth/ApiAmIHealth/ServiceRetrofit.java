package com.amihealth.amihealth.ApiAmIHealth;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Models.AmIHealthNotificacion;
import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.Models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by amihealthmel on 11/27/17.
 */

public interface ServiceRetrofit {

    @POST(Configuracion.URL_LOGIN)
    Call<TokenModel> login(@Body ApiModelToken apiModelToken);
    // ANALIZAR EL BODY PARA SOLICITAR TOKENNNN


    @POST(Configuracion.URL_NUEVA_MEDIDA)
    Call<MedidaHTA> nuevaHTA(@Body MedidaHTA medidaHTA);

    @GET(Configuracion.URL_USER)
    Call<User> getUser();

    @GET(Configuracion.URL_GETMEDIDAS_HTA)
    Call<ArrayList<MedidaHTA>> getMedidas();

    @GET(Configuracion.URL_GETMEDIDAS_HTA_ORDER)
    Call<ArrayList<MedidaHTA>> getMedidasInOrder(@Path("order") int order);

    @POST(Configuracion.URL_UpdateHTA)
    Call<MedidaHTA> updateHTA(@Body MedidaHTA medidaHTA);

    @POST(Configuracion.URL_DELETE_HTA)
    Call<JsonObject> delete_hta(@Query("id") String id);

    @GET(Configuracion.URL_GETMEDIDAS_HTA_ID)
    Call<MedidaHTA> getMedidaHTAbyID(@Query("id") String id);

    @Multipart
    @POST(Configuracion.URL_UpdateProfile)
    Call<User> update_profile(@Part MultipartBody.Part img, @PartMap Map<String, RequestBody> map);

    @POST(Configuracion.URL_verificaremail)
   Observable<Response<JsonObject>> getEmail(@Query("email")  String email);


    /********************************************************************
     *          METODOS DE ACCESO A DATOS DE PESO
     ********************************************************************/

    @GET(Configuracion.URL_GETMEDIDAS_PESO)
    Observable<Response<ArrayList<Peso>>> getMedidas_Peso();

    @POST(Configuracion.URL_INSERT_PESO)
    Observable<Response<Peso>> insert_Peso(@Body Peso peso);

    @POST(Configuracion.URL_EDIT_PESO)
    Observable<Response<Peso>> edit_Peso(@Body Peso peso);

    @POST(Configuracion.URL_DELETE_PESO)
    Observable<Response<Peso>> delete_Peso(@Body Peso peso);



    /********************************************************************
     *          METODOS DE ACCESO A DATOS DE PESO
     ********************************************************************/

    @GET(Configuracion.URL_GET_CINTURA)
    Observable<Response<ArrayList<Cintura>>> getMedidas_cintura();

    @POST(Configuracion.URL_INSERT_CINTURA)
    Observable<Response<Cintura>> insert_Cintura(@Body Cintura cintura);

    @POST(Configuracion.URL_EDIT_CINTURA)
    Observable<Response<Cintura>> edit_Cintura(@Body Cintura cintura);

    @POST(Configuracion.URL_DELETE_CINTURA)
    Observable<Response<Cintura>> delete_Cintura(@Body Cintura cintura);


    /********************************************************************
     *          METODOS DE ACCESO A DATOS DE NOTIFICACIONES
     ********************************************************************/

    @GET(Configuracion.URL_GET_NOTIFICATION)
    Observable<Response<ArrayList<AmIHealthNotificacion>>> get_notification();





}
