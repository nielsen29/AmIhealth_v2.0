package com.amihealth.amihealth.Login.Interactor;

import android.app.Activity;
import android.content.Context;

import com.amihealth.amihealth.Models.User;

/**
 * Created by amihealthmel on 08/16/17.
 */

public interface LoginInteractor {
    void login(String mEmail, String mPass, Context context);
    void getLoginUser(User user);
}
