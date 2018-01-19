package com.amihealth.amihealth.Views.Fragmentos.Registro.view;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.amihealth.amihealth.AppConfig.WebDialog;
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
public class MedidasAntropometricasFragment extends Fragment implements Step{

    private EditText altura;
    private EditText peso;
    private Spinner sp_altura;
    private Spinner sp_peso;
    private ArrayList<String> arrayListpeso;
    private ArrayList<String> arrayListaltura;
    private RegistroInterface registroInterface;
    private CheckBox chk_term;


    public MedidasAntropometricasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_medidas_antropometricas, container, false);
        // Inflate the layout for this fragment

        altura          = (EditText) v.findViewById(R.id.rg_altura);
        peso            = (EditText) v.findViewById(R.id.rg_peso);
        sp_altura       = (Spinner) v.findViewById(R.id.rg_sp_altura);
        sp_peso         = (Spinner) v.findViewById(R.id.rg_sp_peso);
        arrayListpeso   = new ArrayList<>();
        arrayListpeso   .add("Kg");
        arrayListaltura = new ArrayList<>();
        arrayListaltura .add("cm");
        sp_peso         .setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,arrayListpeso));
        sp_altura       .setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,arrayListaltura));
        chk_term = (CheckBox) v.findViewById(R.id.chk_term);
        chk_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTerminos();
            }
        });



        return v;
    }

    @Override
    public VerificationError verifyStep() {
        ArrayList<VerificationError> verificationErrors = new ArrayList<>();
        verificationErrors.add(new Validador().VerificarCampo(getContext(),altura));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),peso));
        for( int i = 0; i < verificationErrors.size(); i++){
            if(verificationErrors.get(i) != null){
                return verificationErrors.get(i);
            }
        }
        if(!chk_term.isChecked()){
            return new VerificationError("Es necesario que leea los terminos y condiciones de uso de la apliaccion");
        }
        registroInterface.getDataMedica(altura.getText().toString(),peso.getText().toString());
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

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

    public void showTerminos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.web_view, null);
        WebView webView = (WebView) v.findViewById(R.id.webview);
        webView.loadUrl("https://saludmovil.utp.ac.pa/terms-and-conditions");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        builder.setView(v);

        builder.setPositiveButton(getActivity().getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               chk_term.setChecked(true);
            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chk_term.setChecked(false);
                chk_term.setError("Es necesario que leea los terminos y condiciones de uso de la apliaccion");
            }
        });

        builder.setTitle(getString(R.string.nuevo_WebTerminos_titulo));
        builder.create();
        builder.show();
    }
}
