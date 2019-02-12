package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter;

import android.content.Context;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Fragments.InterfaceGlucosaView;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Iteractor.GlucosaIteractorIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Iteractor.InterfaceGlucosaIteractor;

/**
 * Created by GITCE on 01/15/18.
 */

public class GlucosaPresenterIMP implements InterfaceGlucosaPresenter {


    private InterfaceGlucosaView cinturaView;
    private InterfaceGlucosaIteractor cinturaIteractor;
    private Context context;

    public GlucosaPresenterIMP(InterfaceGlucosaView cinturaView, Context context) {
        this.cinturaView = cinturaView;
        this.context = context;
        this.cinturaIteractor = new GlucosaIteractorIMP(context,this);
    }

    @Override
    public void OnGetAllResponse() {
        cinturaView.OnGetAllResponse();
    }

    @Override
    public void OnInsertResponse() {

    }

    @Override
    public void OnDeleteResponse() {
        cinturaView.OnDeleteResponse();
    }

    @Override
    public void OnUpdateResponse() {

    }

    @Override
    public void OnErrorResponse(String error) {
        cinturaView.OnErrorResponse(error);
    }

    @Override
    public void OnErrorMedida(String error) { cinturaView.OnErrorMedida(error); }

    @Override
    public void RequestGetAll() {
        cinturaIteractor.RequestGetAll();
    }

    @Override
    public void RequestInsert(Glucosa glucosa) {
        cinturaIteractor.RequestInsert(glucosa);
    }

    @Override
    public void RequestUpdate(Glucosa glucosa) {
        cinturaIteractor.RequestUpdate(glucosa);
    }

    @Override
    public void RequestDelete(Glucosa glucosa) {
        cinturaIteractor.RequestDelete(glucosa);
    }
}
