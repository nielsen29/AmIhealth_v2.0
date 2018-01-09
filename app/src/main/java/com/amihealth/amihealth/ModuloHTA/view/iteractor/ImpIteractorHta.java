package com.amihealth.amihealth.ModuloHTA.view.iteractor;

import android.content.Context;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.view.db.ImpRepoHta;
import com.amihealth.amihealth.ModuloHTA.view.db.RepositorioHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.PresenterHta;

/**
 * Created by amihealthmel on 12/10/17.
 */

public class ImpIteractorHta implements IteractorHta {

    private PresenterHta presenterHta;
    private RepositorioHta repositorioHta;
    private Context context;

    public ImpIteractorHta(PresenterHta presenterHta, Context context) {
        this.presenterHta = presenterHta;
        repositorioHta = new ImpRepoHta(this,presenterHta, context);
        this.context = context;
    }

    @Override
    public void getMedidas(int orden) {
        repositorioHta.getMedidas(orden);
    }

    @Override
    public void insertar(MedidaHTA medidaHTA) {
        repositorioHta.insertar(medidaHTA);
    }

    @Override
    public void borrar(String id) {
        repositorioHta.borrar(id);
    }

    @Override
    public void actualizar(MedidaHTA medidaHTA) {
        repositorioHta.actualizar(medidaHTA);
    }

    @Override
    public void getMedidaById(String id) {
        repositorioHta.getMedidaById(id);
    }
}
