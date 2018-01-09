package com.amihealth.amihealth.Views.Fragmentos.Registro.repositorio;

import android.content.Context;

import com.amihealth.amihealth.Models.Registro;

/**
 * Created by amihealthmel on 09/12/17.
 */

public interface RegistroRepoINT {
    void getProvincia(Context context);
    void getDistritos(Context context, int id_provincia);
    void getCorregimientos(Context context, int id_distrito);
    void getEtnias(Context context);
    void newUser(Context context, Registro registro);

}
