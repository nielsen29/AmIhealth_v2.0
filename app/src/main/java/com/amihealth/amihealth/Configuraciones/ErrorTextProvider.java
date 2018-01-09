package com.amihealth.amihealth.Configuraciones;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.amihealth.amihealth.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by amihealthmel on 08/07/17.
 */

public class ErrorTextProvider implements TextWatcher, View.OnFocusChangeListener {
    private Context context;
    private EditText editText;
    private String caso;

    final private String PATRON_EMAIL = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@"+"[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*(\\.[a-z]{2,})$";


    public ErrorTextProvider(Context context, final EditText editText, String caso){
        this.caso = caso;
        this.editText = editText;
        this.context = context;
        editText.setOnFocusChangeListener(this);
    }

    private boolean isValid(String value, String patron){
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (caso){
            case "email":{
                if(s.toString() == ""){
                    editText.setError(context.getResources().getString(R.string.error_email_vacio),context.getResources().getDrawable(R.drawable.ms_animated_vector_circle_to_warning_24dp));
                }

                if (!isValid(s.toString(),PATRON_EMAIL)){
                    editText.setError(context.getResources().getString(R.string.error_email),context.getResources().getDrawable(R.drawable.ms_animated_vector_circle_to_warning_24dp));
                    editText.setHintTextColor(Color.RED);
                }


            }
            case "pass":{
                if(s.toString() == ""){
                    editText.setError(context.getResources().getString(R.string.error_email_vacio),context.getResources().getDrawable(R.drawable.ms_animated_vector_circle_to_warning_24dp));
                }

                if (!isValid(s.toString(),PATRON_EMAIL)){
                    editText.setError(context.getResources().getString(R.string.error_email),context.getResources().getDrawable(R.drawable.ms_animated_vector_circle_to_warning_24dp));
                }
            }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus){
            if(TextUtils.isEmpty(editText.getText().toString())){
                editText.setError(v.getResources().getString(R.string.error_email_vacio), Drawable.createFromPath(context.getResources().getResourceName(R.drawable.ms_animated_vector_circle_to_warning_24dp)));
            }
        }
    }
}
