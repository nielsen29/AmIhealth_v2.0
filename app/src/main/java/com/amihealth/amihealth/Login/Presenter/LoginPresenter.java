package com.amihealth.amihealth.Login.Presenter;

import android.app.Activity;

import com.amihealth.amihealth.ApiAmIHealth.TokenModel;
import com.amihealth.amihealth.Models.User;

/**
 * Created by amihealthmel on 08/16/17.
 */

public interface LoginPresenter {
    void login(String mEmail, String mPass, Activity activity); //interactor
    void tokenReturn(TokenModel tokenModel);
    void isLogin(boolean isLogin); //interactor
    void error(String error);                   // view
    void validarEmail(String mEmail);           // view
    void validPass(String mPass);               // view
    void getPaciente(String response);             // view
    void getLoginUser(User user); // view
    void errorEmail();
    void errorPass();

}
