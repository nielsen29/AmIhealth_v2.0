package com.amihealth.amihealth.Views.Fragmentos.Registro.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.Views.RegistroIMP;
import com.amihealth.amihealth.Views.RegistroInterface;
import com.amihealth.amihealth.Views.RegistroViewINTR;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegistroFragment extends Fragment implements Step{
    private Configuracion CON;

    private EditText mEmail;
    private EditText mPass;
    private EditText mPassConf;
    private boolean enUso = false;

    private Textos textos;

    ArrayList<EditText> editTextArrayList;
    private RegistroInterface registroInterface;

    public UserRegistroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        CON = new Configuracion();
        View v = inflater.inflate(R.layout.fragment_user_registro, container, false);
        mEmail = (EditText) v.findViewById(R.id.rg_email);
        mEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mPass = (EditText) v.findViewById(R.id.rg_pass);
        mPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mPassConf = (EditText)  v.findViewById(R.id.rg_pass_confirm);
        editTextArrayList = new ArrayList<EditText>();
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
                Observable<Response<JsonObject>> observable = retrofitAdapter.getOUTauth().getEmail(mEmail.getText().toString());
                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe( stringResponse -> {
                            Log.i("BUSCANDO", "RxJava2, HTTP Error:  "+stringResponse.body().toString());
                            if (stringResponse.isSuccessful()){
                                //JSONObject jsonObject = new JSONObject(stringResponse.body());
                                String respuesta = stringResponse.body().get("data").getAsString();
                                if(respuesta.equals("OK")){
                                    enUso = true;
                                    mEmail.setError("Email en uso");
                                }else{
                                    enUso = false;
                                }
                            }
                        }, throwable -> {
                            Log.i("ERROR", "RxJava2, HTTP Error: " + throwable.getMessage());


                        });
            }
        });
        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
                    Observable<Response<JsonObject>> observable = retrofitAdapter.getOUTauth().getEmail(mEmail.getText().toString());
                    observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe( stringResponse -> {
                                Log.i("BUSCANDO", "RxJava2, HTTP Error:  "+stringResponse.body().toString());
                                if (stringResponse.isSuccessful()){
                                    //JSONObject jsonObject = new JSONObject(stringResponse.body());
                                    String respuesta = stringResponse.body().get("data").getAsString();
                                    if(respuesta.equals("OK")){
                                        enUso = true;
                                        mEmail.setError("Email en uso");
                                    }else{
                                        enUso = false;
                                    }
                                }
                            }, throwable -> {
                                Log.i("ERROR", "RxJava2, HTTP Error: " + throwable.getMessage());


                            });
                }

            }
        });


        return v;
    }





    @Override
    public VerificationError verifyStep() {

        ArrayList<VerificationError> verificationErrors = new ArrayList<>();
        verificationErrors.add(new Validador().VerificarCampo(getContext(),mEmail));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),mPass));

        if(enUso == true){
            mEmail.setError("Email en uso");
            return new VerificationError("Email en Uso");
        }

        for( int i = 0; i < verificationErrors.size(); i++){
            if(verificationErrors.get(i) != null){
                return verificationErrors.get(i);
            }
        }
        if(mPass.getText().toString().equals(mPassConf.getText().toString())){
            registroInterface.getDataUser(mEmail.getText().toString(),mPass.getText().toString(),mPassConf.getText().toString());
            return null;
        }else{
            mPassConf.setError(getContext().getResources().getString(R.string.error_pass_noMatch));
            return new VerificationError("Para continuar por favor verifica los campos que marquen error");
        }
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {


    }


    public interface Textos{
        public void getArrayEditText(ArrayList<EditText> arrayList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{

            registroInterface = new RegistroIMP((RegistroViewINTR) context);

        }catch (ClassCastException e){
            throw new IllegalStateException("IMPLEMENTAR LA INTERFAZ "+ UserRegistroFragment.Textos.class.getName());
        }
    }
}
