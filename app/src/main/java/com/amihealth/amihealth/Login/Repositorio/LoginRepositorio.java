package com.amihealth.amihealth.Login.Repositorio;

import android.app.Activity;
import android.content.Context;

/**
 * Created by amihealthmel on 08/17/17.
 */

public interface LoginRepositorio {
    void login(String mEmail, String mPass, Context context);
    void validarCredenciales(String mEmail, String mPass);
    void message();
}
