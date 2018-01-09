package com.amihealth.amihealth.Login.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amihealth.amihealth.ApiAmIHealth.TokenModel;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Home.HomeActivity;
import com.amihealth.amihealth.Login.Presenter.LoginPresenter;
import com.amihealth.amihealth.Login.Presenter.LoginPresenterIMP;
import com.amihealth.amihealth.Models.User;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.Views.RegistroActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity implements LoginInterfaceView{

    private LoginPresenter loginPresenter;

    private EditText etEmail;
    private EditText etPass;
    private Button login;
    private ProgressBar progressBar;
    private static final String TAG = "LoginRepositorioIMP";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private int ID_PACIENTE;
    private ProgressDialog progressDialog;

    SessionManager sessionManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager  = new SessionManager(getApplicationContext());

        loginPresenter  = new LoginPresenterIMP(this);

        etEmail         = (EditText) findViewById(R.id.user);
        etPass          = (EditText) findViewById(R.id.pass);
        login           = (Button) findViewById(R.id.btn_login);
        login           .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginbtn(v);
                    }
                });

        progressBar     = (ProgressBar) findViewById(R.id.lg_progressbar);
        hideProgressbar();

    }


    public void loginbtn(View view){
        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        etPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        if(!verificarPatronesText(etEmail) && !verificarPatronesText(etPass)){
            String mEmail   = etEmail.getText().toString();
            String mPass    = etPass.getText().toString();
            blockInputs();
            showProgressbar();
            loginPresenter.login(mEmail, mPass, this);
        }else{
            hideProgressbar();
            unblockInputs();
        }

    }


    public void btnCrearCuenta(View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    final private String PATRON_EMAIL = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@"+"[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*(\\.[a-z]{2,})$";
    final private String PATRON_NUMBER = "^[_0-9]";
    final private String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";

    public boolean verificarPatronesText(EditText editText){
        if(editText.getText().length()>0 ){
            switch (editText.getInputType()){

                case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                {   hideProgressbar();
                    unblockInputs();
                    Pattern pattern = Pattern.compile(PATRON_EMAIL);
                    Matcher matcher = pattern.matcher(editText.getText().toString());
                    if(matcher.matches()){
                        return false;
                    }else{
                        editText.setError(getResources().getString(R.string.error_email));
                        return true;
                    }
                }
                case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                {
                    hideProgressbar();
                    unblockInputs();
                    Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
                    Matcher matcher = pattern.matcher(editText.getText().toString());
                    if(matcher.matches()){
                        return false;
                    }else{
                        editText.setError(getResources().getString(R.string.error_pass));
                        return true;
                    }
                }
                default:
                    return false;
            }
        }else{
            hideProgressbar();
            unblockInputs();
            editText.setError(getResources().getString(R.string.error_txt_cedula));
            return true;
        }
    }

    @Override
    public void login(String mEmail, String mPass, Context context) {

    }

    @Override
    public void tokenReturn(TokenModel tokenModel) {
        sessionManager.setAuth(tokenModel.getAccess_token());
    }

    @Override
    public void islogin(boolean mlogin) {

        if(mlogin){
            hideProgressbar();
            //Toast.makeText(getApplicationContext(),"PASO X EL USER: "+String.valueOf(user.getId()),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            //intent.putExtra(Configuracion.ID_PACIENTE,String.valueOf(user.getId()));
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),"Error al Iniciar",Toast.LENGTH_LONG).show();
            hideProgressbar();
            unblockInputs();
        }

    }

    @Override
    public void error(String error) {
        unblockInputs();
        hideProgressbar();
        if(error.equals("401")){
            errorEmail(true);
            errorPass(true);
        }

        Snackbar.make(getCurrentFocus(),error,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void validarEmail(String mEmail) {

    }

    @Override
    public void validPass(String mPass) {

    }

    @Override
    public void getPaciente(String paciente) {

    }

    @Override
    public void getLoginUser(final User user) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(user);
            }
        });
        realm.close();

        sessionManager.crearSessionLogin(user.getId_InServer(),user.getNombre(),user.getEmail());
        this.islogin(true);
    }

    @Override
    public void blockInputs() {
        etEmail .setEnabled(false);
        etPass  .setEnabled(false);
        login   .setEnabled(false);
    }

    @Override
    public void unblockInputs() {
        etEmail .setEnabled(true);
        etPass  .setEnabled(true);
        login   .setEnabled(true);
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void errorEmail(boolean error) {
        if (error){
            etEmail.setError(getString(R.string.error_email_credencial));
        }

    }

    @Override
    public void errorPass(boolean error) {
        if(error){
            etPass.setError(getString(R.string.error_pass_credencial));
            etPass.setText("");
        }

    }


    /*@Override
    public void login(String mEmail, String mPass, Context context) {
    }

    @Override
    public void islogin(boolean mlogin) {
        if(mlogin){
            Toast.makeText(getApplicationContext(),"LOGUEADO", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MedidaHTAListActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"CREDENCIALES INCORRECTAS", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void error(String error) {
        hideProgressbar();
        unblockInputs();

       Volley.newRequestQueue(getApplicationContext()).cancelAll("VolleyLOGIN");
        if(error.equals("ERROR_TIMEOUT")){
            Toast.makeText(getApplicationContext(),"esto esta tardando mas de la cuenta",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void validarEmail(String mEmail) {

    }

    @Override
    public void validPass(String mPass) {

    }

    @Override
    public void getPaciente(String paciente) {
        try{
            JSONObject JsonObj = new JSONObject(paciente);
            JSONArray datos = JsonObj.getJSONArray("data");
            for (int i = 0; i < datos.length(); i++){
                JSONObject medida = datos.getJSONObject(i);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLoginUser(User user) {
        if(user != null){
            hideProgressbar();

            sessionManager.crearSessionLogin(user.getId_InServer(),user.getNombre(),user.getEmail());

            //Toast.makeText(getApplicationContext(),"PASO X EL USER: "+String.valueOf(user.getId()),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MedidaHTAListActivity.class);
            //intent.putExtra(Configuracion.ID_PACIENTE,String.valueOf(user.getId()));
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),"Error al Iniciar",Toast.LENGTH_LONG).show();

        }
    }

    public void launchProgress(){
        final ProgressDialog progressDialog = ProgressDialog.show(getApplicationContext(),"Por favor espere...","iniciando...",true);
        progressDialog.setCancelable(true);
    }

    public boolean verificarCamposNull(EditText editText){
        if (editText.getText().length() < 0 || editText.getText().toString() == " "){
            return true;
        }else {
            return  false;
        }
    }



    @Override
    public void errorEmail(boolean error) {
        if(error){
            hideProgressbar();
            unblockInputs();
            etEmail.setError("Email no registrado");
        }
    }

    @Override
    public void errorPass(boolean error) {
        if(error){
            hideProgressbar();
            unblockInputs();
            etPass.setError("Clave incorrecta 1");
        }
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

}
