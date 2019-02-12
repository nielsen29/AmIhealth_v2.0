package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;

/**
 * Created by GITCE on 01/15/18.
 */

public interface InterfaceGlucosaPresenter {

    // UI INTERFACE
    void OnGetAllResponse();
    void OnInsertResponse();
    void OnDeleteResponse();
    void OnUpdateResponse();
    void OnErrorResponse(String error);

    void OnErrorMedida(String error);

    // SERVERs INTERFACE
    void RequestGetAll();
    void RequestInsert(Glucosa glucosa);
    void RequestUpdate(Glucosa glucosa);
    void RequestDelete(Glucosa glucosa);
}
