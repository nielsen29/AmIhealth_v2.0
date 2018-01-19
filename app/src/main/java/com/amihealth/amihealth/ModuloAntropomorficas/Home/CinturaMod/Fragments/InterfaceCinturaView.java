package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Fragments;

/**
 * Created by GITCE on 01/15/18.
 */

public interface InterfaceCinturaView {
    void OnGetAllResponse();
    void OnInsertResponse();
    void OnDeleteResponse();
    void OnUpdateResponse();
    void OnErrorResponse(String error);
    void RespuestaActivity(int cargar);
    void onClickMenuItem_EDIT(String id);
    void onClickMenuItem_DELETE(String id);
}
