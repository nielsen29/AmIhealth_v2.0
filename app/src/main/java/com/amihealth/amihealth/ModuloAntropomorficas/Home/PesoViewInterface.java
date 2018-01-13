package com.amihealth.amihealth.ModuloAntropomorficas.Home;

/**
 * Created by GITCE on 01/11/18.
 */

public interface PesoViewInterface {
    void OnGetAllResponse();
    void OnInsertResponse();
    void OnDeleteResponse();
    void OnUpdateResponse();
    void OnErrorResponse(String error);
    void RespuestaActivity(int cargar);
    void onClickMenuItem_EDIT(String id);
    void onClickMenuItem_DELETE(String id);

}
