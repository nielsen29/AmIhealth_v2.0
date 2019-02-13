package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter;

import android.content.Context;
import android.util.Log;

import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Fragments.InterfaceGlucosaView;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Iteractor.GlucosaIteractorIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Iteractor.InterfaceGlucosaIteractor;

/**
 * Created by GITCE on 01/15/18.
 */

public class GlucosaPresenterIMP implements InterfaceGlucosaPresenter {


    private InterfaceGlucosaView glucosaView;
    private InterfaceGlucosaIteractor glucosaIteractor;
    private Context context;

    public GlucosaPresenterIMP(InterfaceGlucosaView glucosaView, Context context) {
        this.glucosaView = glucosaView;
        this.context = context;
        this.glucosaIteractor = new GlucosaIteractorIMP(context,this);
    }

    @Override
    public void OnGetAllResponse() {
        glucosaView.OnGetAllResponse();
    }

    @Override
    public void OnInsertResponse() {

    }

    @Override
    public void OnDeleteResponse() {
        glucosaView.OnDeleteResponse();
    }

    @Override
    public void OnUpdateResponse() {

    }

    @Override
    public void OnErrorResponse(String error) {
        glucosaView.OnErrorResponse(error);
    }

    @Override
    public void OnErrorMedida(String error) { glucosaView.OnErrorMedida(error); }

    @Override
    public void RequestGetAll() {
        glucosaIteractor.RequestGetAll();
    }

    @Override
    public void RequestGetAllHbA1c() {
        Log.d("F-GEThba:","------------->>>>>>>>> PRESENTER GEThba");
        glucosaIteractor.RequestGetAllHbA1c();
    }

    @Override
    public void RequestInsert(Glucosa glucosa) {
        glucosaIteractor.RequestInsert(glucosa);
    }

    @Override
    public void RequestUpdate(Glucosa glucosa) {
        glucosaIteractor.RequestUpdate(glucosa);
    }

    @Override
    public void RequestDelete(Glucosa glucosa) {
        glucosaIteractor.RequestDelete(glucosa);
    }
}
