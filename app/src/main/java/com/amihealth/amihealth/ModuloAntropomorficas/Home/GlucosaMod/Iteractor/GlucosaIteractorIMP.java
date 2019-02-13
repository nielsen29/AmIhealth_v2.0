package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Iteractor;

import android.content.Context;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.GlucosaPresenterIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Repositorio.GlucosaRepoIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Repositorio.InterfaceGlucosaRepo;

/**
 * Created by GITCE on 01/15/18.
 */

public class GlucosaIteractorIMP implements InterfaceGlucosaIteractor {

    private Context context;
    private InterfaceGlucosaRepo cinturaRepo;

    public GlucosaIteractorIMP(Context context, GlucosaPresenterIMP cinturaPresenterIMP) {
        this.context =context;
        this.cinturaRepo = new GlucosaRepoIMP(context, cinturaPresenterIMP);
    }

    @Override
    public void RequestGetAll() {

        cinturaRepo.RequestGetAll();

    }

    @Override
    public void RequestGetAllHbA1c() {

        cinturaRepo.RequestGetAllHbA1c();

    }

    @Override
    public void RequestInsert(Glucosa glucosa) {
        cinturaRepo.RequestInsert(glucosa);
    }

    @Override
    public void RequestUpdate(Glucosa glucosa) {
        cinturaRepo.RequestUpdate(glucosa);
    }

    @Override
    public void RequestDelete(Glucosa glucosa) {
        cinturaRepo.RequestDelete(glucosa);
    }
}
