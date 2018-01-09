package com.amihealth.amihealth.Views.Fragmentos.Registro.view;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

import com.amihealth.amihealth.R;
import com.stepstone.stepper.VerificationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amihealthmel on 09/13/17.
 */

public class Validador {

    final private String PATRON_EMAIL = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@"+"[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*(\\.[a-z]{2,})$";
    final private String PATRON_NUMBER = "^[_0-9]";
    final private String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
    private boolean valido;

    public VerificationError VerificarCampo(Context context,EditText editText){
        if(editText.getText().length()>0 ){
            switch (editText.getInputType()){

                case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                {
                    Pattern pattern = Pattern.compile(PATRON_EMAIL);
                    Matcher matcher = pattern.matcher(editText.getText().toString());
                    if(matcher.matches()){
                        return null;
                    }else{
                        editText.setError(context.getResources().getString(R.string.error_email));
                        return new VerificationError("Para continuar por favor verifica los campos que marquen error");
                    }
                }
                case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                {
                    Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
                    Matcher matcher = pattern.matcher(editText.getText().toString());
                    if(matcher.matches()){
                        return null;
                    }else{
                        editText.setError(context.getResources().getString(R.string.error_pass));
                        return new VerificationError("Para continuar por favor verifica los campos que marquen error");
                    }
                }
                default:
                    return null;
            }
        }else{
            editText.setError(context.getResources().getString(R.string.error_txt_cedula));
            return new VerificationError("Para continuar por favor verifica los campos que marquen error");
        }

    }



}
