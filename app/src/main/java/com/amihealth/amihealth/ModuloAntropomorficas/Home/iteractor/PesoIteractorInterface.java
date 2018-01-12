package com.amihealth.amihealth.ModuloAntropomorficas.Home.iteractor;

import com.amihealth.amihealth.Models.Peso;

/**
 * Created by GITCE on 01/11/18.
 */

public interface PesoIteractorInterface {
    void RequestGetAll();
    void RequestInsertPeso(Peso peso);
    void RequestUpdatePeso(Peso peso);
    void RequestDeletePeso(Peso peso);
}
