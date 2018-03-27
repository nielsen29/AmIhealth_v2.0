package com.amihealth.amihealth.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amihealth.amihealth.Adaptadores.AdapterStepRegistro;
import com.amihealth.amihealth.AppConfig.OnDialogResponse;
import com.amihealth.amihealth.AppConfig.StaticError;
import com.amihealth.amihealth.Login.View.LoginActivity;
import com.amihealth.amihealth.Models.Corregimiento;
import com.amihealth.amihealth.Models.Distrito;
import com.amihealth.amihealth.Models.Etnia;
import com.amihealth.amihealth.Models.Provincia;
import com.amihealth.amihealth.Models.Registro;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.Views.Fragmentos.Registro.presenter.RegistroPresenterIMP;
import com.amihealth.amihealth.Views.Fragmentos.Registro.presenter.RegistroPresenterINT;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.RegistroViewINT;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.UserRegistroFragment;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity implements StepperLayout.StepperListener, UserRegistroFragment.Textos, RegistroViewINTR, RegistroViewINT, OnDialogResponse {
    private StepperLayout mStepperLayout;
    private EditText et;
    private static Registro registro;
    private RegistroPresenterINT registroPresenterINT;
    private StaticError staticError;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        showtoolbar(getResources().getString(R.string.toolbar_titulo),true);
        mStepperLayout          = (StepperLayout) findViewById(R.id.step_registro);
        mStepperLayout          .setAdapter(new AdapterStepRegistro(getSupportFragmentManager(),this));
        mStepperLayout          .setListener(this);
        registro                = new Registro();
        registroPresenterINT = new RegistroPresenterIMP(this);

        staticError = new StaticError(this);
        alertDialog = staticError.getErrorDialogAlert(this,StaticError.ESPERA);
        alertDialog.setCancelable(false);


    }

    public void showtoolbar(String titulo, boolean mUpbtn){
        Toolbar toolbar         = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar()   .setTitle(titulo);
        getSupportActionBar()   .setDisplayHomeAsUpEnabled(mUpbtn);
    }

    @Override
    public void onCompleted(View completeButton) {
        alertDialog.show();
        Context contextNew = this.getApplicationContext();



        registroPresenterINT.newUser(getApplication().getBaseContext(),registro);

        //Toast.makeText(this, "onComplete = ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError"+ verificationError.getErrorMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStepSelected(int newStepPosition) {

        //Toast.makeText(this, "onStepSelected = "+ newStepPosition,Toast.LENGTH_LONG).show();


    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void getArrayEditText(ArrayList<EditText> arrayList) {
        for(int i = 0; i < arrayList.size() ; i++){
            //Toast.makeText(this,arrayList.get(i).getText().toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getDataUser(String mEmail, String mPass, String mPassConf) {
        this.registro.setEmail(mEmail);
        this.registro.setPass(mPass);
        this.registro.setPassConf(mPassConf);
        //Toast.makeText(getApplicationContext(),"LLEGI  LA DATA "+ this.registro.getEmail(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void getDataPersonal(String mCed, String mTomo, String mAsiento, String mNombre, String mApellido, String mFecha, int mSexo, int mEtnia) {
        this.registro.setCed(mCed);
        this.registro.setTomo(mTomo);
        this.registro.setAsiento(mAsiento);
        this.registro.setNombre(mNombre);
        this.registro.setApellido(mApellido);
        this.registro.setFecha(mFecha);
        this.registro.setId_sexo(mSexo);
        this.registro.setId_etnia(mEtnia);
        //Toast.makeText(getApplicationContext(),"LLEGI  LA DATA "+ this.registro.getApellido() + this.registro.getEmail(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataLocation(int mProvincia, int mDistrito, int mCorregimiento, String mdireccion, String mMovil) {
        this.registro.setId_provincia(mProvincia);
        this.registro.setId_distrito(mDistrito);
        this.registro.setId_corregimiento(mCorregimiento);
        this.registro.setDireccion(mdireccion);
        this.registro.setMovil(mMovil);
        //Toast.makeText(getApplicationContext(),"LLEGI  LA DATA "+ this.registro.getApellido() + this.registro.getEmail() + this.registro.getMovil(),Toast.LENGTH_LONG).show();


    }

    @Override
    public void getDataMedica(String mAltura, String mPeso) {
        this.registro.setAltura(mAltura);
        this.registro.setPeso(mPeso);

    }

    @Override
    public void getError(String responseError) {

    }

    @Override
    public void responseNewUser() {
        alertDialog.cancel();
        AlertDialog alertDialogr = staticError.getErrorDialogAlert(getApplicationContext(), StaticError.NEW_USER);
        alertDialogr.setCancelable(false);
        alertDialogr.show();

    }


    @Override
    public void getProvincia(ArrayList<Provincia> provincias) {

    }

    @Override
    public void getDistritos(ArrayList<Distrito> distritos) {

    }

    @Override
    public void getCorregimientos(ArrayList<Corregimiento> corregimientos) {

    }

    @Override
    public void getEtnias(ArrayList<Etnia> etnias) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void retryConection() {

    }

    @Override
    public void retryBusqueda() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(i);
    }

    @Override
    public void declineBusqueda() {

    }
}
