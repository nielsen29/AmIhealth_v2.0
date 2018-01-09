package com.amihealth.amihealth.Views;

/**
 * Created by amihealthmel on 09/13/17.
 */

public interface RegistroInterface {
    void getDataUser(String mEmail, String mPass, String mPassConf);
    void getDataPersonal(String mCed, String mTomo, String mAsiento, String mNombre, String mApellido, String mFecha, int mSexo, int mEtnia);
    void getDataLocation(int mProvincia, int mDistrito, int mCorregimiento, String mdireccion, String mMovil);
    void getDataMedica(String mAltura, String mPeso);
}
