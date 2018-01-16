package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Presenter;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Peso;

/**
 * Created by GITCE on 01/15/18.
 */

public interface InterfaceCinturaPresenter {

    // UI INTERFACE
    void OnGetAllResponse();
    void OnInsertResponse();
    void OnDeleteResponse();
    void OnUpdateResponse();
    void OnErrorResponse(String error);

    // SERVERs INTERFACE
    void RequestGetAll();
    void RequestInsertPeso(Cintura cintura);
    void RequestUpdatePeso(Cintura cintura);
    void RequestDeletePeso(Cintura cintura);
}
