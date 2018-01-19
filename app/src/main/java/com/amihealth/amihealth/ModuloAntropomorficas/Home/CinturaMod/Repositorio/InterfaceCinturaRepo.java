package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Repositorio;

import com.amihealth.amihealth.Models.Cintura;

/**
 * Created by GITCE on 01/15/18.
 */

public interface InterfaceCinturaRepo {

    void RequestGetAll();
    void RequestInsert(Cintura cintura);
    void RequestUpdate(Cintura cintura);
    void RequestDelete(Cintura cintura);
}
