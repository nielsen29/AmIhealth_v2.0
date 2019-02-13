package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Iteractor;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;

/**
 * Created by GITCE on 01/15/18.
 */

public interface InterfaceGlucosaIteractor {

    void RequestGetAll();

    void RequestGetAllHbA1c();

    void RequestInsert(Glucosa glucosa);
    void RequestUpdate(Glucosa glucosa);
    void RequestDelete(Glucosa glucosa);
}
