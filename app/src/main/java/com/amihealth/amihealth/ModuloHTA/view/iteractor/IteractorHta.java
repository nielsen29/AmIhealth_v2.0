package com.amihealth.amihealth.ModuloHTA.view.iteractor;

import com.amihealth.amihealth.Models.MedidaHTA;

/**
 * Created by amihealthmel on 12/10/17.
 */

public interface IteractorHta {

    void getMedidas(int orden);
    void insertar(MedidaHTA medidaHTA);
    void borrar(String id);
    void actualizar(MedidaHTA medidaHTA);

    void getMedidaById(String id);
}
