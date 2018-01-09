package com.amihealth.amihealth.Views.Fragmentos.Registro.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.Views.RegistroIMP;
import com.amihealth.amihealth.Views.RegistroInterface;
import com.amihealth.amihealth.Views.RegistroViewINTR;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegistroFragment extends Fragment implements Step{
    private Configuracion CON;

    private EditText mEmail;
    private EditText mPass;
    private EditText mPassConf;

    private Textos textos;

    ArrayList<EditText> editTextArrayList;
    private RegistroInterface registroInterface;

    public UserRegistroFragment() {
        // Required empty public constructor
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
       /* mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StringRequest St = new StringRequest(Request.Method.POST, CON.URL_GET_EMAIL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        }); */


        return v;
    }





    @Override
    public VerificationError verifyStep() {

        ArrayList<VerificationError> verificationErrors = new ArrayList<>();
        verificationErrors.add(new Validador().VerificarCampo(getContext(),mEmail));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),mPass));
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
