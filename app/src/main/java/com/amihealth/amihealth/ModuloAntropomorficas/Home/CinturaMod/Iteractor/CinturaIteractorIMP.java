package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Iteractor;

import android.content.Context;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Presenter.CinturaPresenterIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Repositorio.CinturaRepoIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Repositorio.InterfaceCinturaRepo;

/**
 * Created by GITCE on 01/15/18.
 */

public class CinturaIteractorIMP implements InterfaceCinturaIteractor {

    private Context context;
    private InterfaceCinturaRepo cinturaRepo;

    public CinturaIteractorIMP(Context context, CinturaPresenterIMP cinturaPresenterIMP) {
        this.context =context;
        this.cinturaRepo = new CinturaRepoIMP(context, cinturaPresenterIMP);
    }

    @Override
    public void RequestGetAll() {

        cinturaRepo.RequestGetAll();

    }

    @Override
    public void RequestInsert(Cintura cintura) {
        cinturaRepo.RequestInsert(cintura);
    }

    @Override
    public void RequestUpdate(Cintura cintura) {
        cinturaRepo.RequestUpdate(cintura);
    }

    @Override
    public void RequestDelete(Cintura cintura) {
        cinturaRepo.RequestDelete(cintura);
    }
}
