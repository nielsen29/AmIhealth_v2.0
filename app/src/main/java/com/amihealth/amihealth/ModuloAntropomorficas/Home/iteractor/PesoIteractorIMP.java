package com.amihealth.amihealth.ModuloAntropomorficas.Home.iteractor;

import android.content.Context;

import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresentrerIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.repo.PesoRepoIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.repo.PesoRepoInterface;

/**
 * Created by GITCE on 01/11/18.
 */

public class PesoIteractorIMP implements PesoIteractorInterface {

    private Context context;
    private PesoRepoInterface pesoRepoInterface;

    public PesoIteractorIMP(Context context, PesoPresentrerIMP pesoPresentrerIMP) {
        this.context = context;
        this.pesoRepoInterface = new PesoRepoIMP(context,pesoPresentrerIMP);
    }

    @Override
    public void RequestGetAll() {
        pesoRepoInterface.RequestGetAll();
    }

    @Override
    public void RequestInsertPeso(Peso peso) {
        pesoRepoInterface.RequestInsertPeso(peso);

    }

    @Override
    public void RequestUpdatePeso(Peso peso) {
        pesoRepoInterface.RequestUpdatePeso(peso);
    }

    @Override
    public void RequestDeletePeso(Peso peso) {
        pesoRepoInterface.RequestDeletePeso(peso);
    }
}
