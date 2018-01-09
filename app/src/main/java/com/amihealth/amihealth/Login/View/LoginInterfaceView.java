package com.amihealth.amihealth.Login.View;

import android.content.Context;

import com.amihealth.amihealth.ApiAmIHealth.TokenModel;
import com.amihealth.amihealth.Models.User;

/**
 * Created by amihealthmel on 08/16/17.
 */

public interface LoginInterfaceView {
    void login(String mEmail, String mPass, Context context);
    void tokenReturn(TokenModel tokenModel);
    void islogin(boolean mlogin);
    void error(String error);
    void validarEmail(String mEmail);
    void validPass(String mPass);
    void getPaciente(String paciente);
    void getLoginUser(User user);

    void blockInputs();
    void unblockInputs();
    void showProgressbar();
    void hideProgressbar();

    void errorEmail(boolean error);
    void errorPass(boolean error);

}
