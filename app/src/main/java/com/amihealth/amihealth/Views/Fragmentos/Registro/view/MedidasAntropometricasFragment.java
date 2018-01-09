package com.amihealth.amihealth.Views.Fragmentos.Registro.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
}
