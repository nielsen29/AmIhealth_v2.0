package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Repositorio;

import com.amihealth.amihealth.Models.Cintura;

/**
 * Created by GITCE on 01/15/18.
 */

public interface InterfaceCinturaRepo {

    void RequestGetAll();
    void RequestInsertPeso(Cintura cintura);
    void RequestUpdatePeso(Cintura cintura);
    void RequestDeletePeso(Cintura cintura);
}
