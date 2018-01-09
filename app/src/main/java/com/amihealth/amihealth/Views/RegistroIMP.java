package com.amihealth.amihealth.Views;

/**
 * Created by amihealthmel on 09/14/17.
 */

public class RegistroIMP implements RegistroInterface {
    private RegistroInterface registroInterface;
    private RegistroViewINTR viewINTR;

    public RegistroIMP(RegistroViewINTR viewINTR) {
        this.viewINTR = viewINTR;
    }

    @Override
    public void getDataUser(String mEmail, String mPass, String mPassConf) {
        viewINTR.getDataUser(mEmail,mPass,mPassConf);
    }

    @Override
    public void getDataPersonal(String mCed, String mTomo, String mAsiento, String mNombre, String mApellido, String mFecha, int mSexo, int mEtnia) {
        viewINTR.getDataPersonal(mCed, mTomo, mAsiento, mNombre, mApellido, mFecha, mSexo, mEtnia);
    }

    @Override
    public void getDataLocation(int mProvincia, int mDistrito, int mCorregimiento, String mdireccion, String mMovil) {
        viewINTR.getDataLocation(mProvincia, mDistrito, mCorregimiento, mdireccion, mMovil);
    }

    @Override
    public void getDataMedica(String mAltura, String mPeso) {
        viewINTR.getDataMedica(mAltura, mPeso);
    }
}
