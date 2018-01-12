package com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter;

import com.amihealth.amihealth.Models.Peso;

/**
 * Created by GITCE on 01/11/18.
 */

public interface PesoPresenterInterface {

    // UI INTERFACE
    void OnGetAllResponse();
    void OnInsertResponse();
    void OnDeleteResponse();
    void OnUpdateResponse();
    void OnErrorResponse(String error);

    // SERVERs INTERFACE
    void RequestGetAll();
    void RequestInsertPeso(Peso peso);
    void RequestUpdatePeso(Peso peso);
    void RequestDeletePeso(Peso peso);




}
