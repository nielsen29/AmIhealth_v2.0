package com.amihealth.amihealth.Login.Presenter;

import android.app.Activity;

import com.amihealth.amihealth.ApiAmIHealth.TokenModel;
import com.amihealth.amihealth.Login.Interactor.LoginInteractorIMP;
import com.amihealth.amihealth.Login.View.LoginInterfaceView;
import com.amihealth.amihealth.Models.User;

/**
 * Created by amihealthmel on 08/16/17.
 */

public class LoginPresenterIMP implements LoginPresenter {

    private LoginInterfaceView loginInterfaceView;
    private LoginInteractorIMP interactorIMP;

    public LoginPresenterIMP(LoginInterfaceView loginInterfaceView) {
        this.loginInterfaceView = loginInterfaceView;
        interactorIMP = new LoginInteractorIMP(this);
    }

    @Override
    public void login(String mEmail, String mPass, Activity activity) {
        interactorIMP.login(mEmail,mPass,activity);
    }

    @Override
    public void tokenReturn(TokenModel tokenModel) {
        loginInterfaceView.tokenReturn(tokenModel);
    }

    @Override
    public void isLogin(boolean isLogin) {
        loginInterfaceView.islogin(isLogin);
    }


    @Override
    public void error(String error) {
        loginInterfaceView.error(error);

    }

    @Override
    public void validarEmail(String mEmail) {
        loginInterfaceView.validarEmail(mEmail);
    }

    @Override
    public void validPass(String mPass) {
        loginInterfaceView.validPass(mPass);

    }

    @Override
    public void getPaciente(String response) {
        loginInterfaceView.getPaciente(response);
    }

    @Override
    public void getLoginUser(User user) {
        loginInterfaceView.getLoginUser(user);
    }

    @Override
    public void errorEmail() {
        loginInterfaceView.errorEmail(true);
    }

    @Override
    public void errorPass() {
        loginInterfaceView.errorPass(true);
    }
}
