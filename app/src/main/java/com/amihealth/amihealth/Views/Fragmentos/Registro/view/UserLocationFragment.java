package com.amihealth.amihealth.Views.Fragmentos.Registro.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amihealth.amihealth.Models.Corregimiento;
import com.amihealth.amihealth.Models.Direccion;
import com.amihealth.amihealth.Models.Distrito;
import com.amihealth.amihealth.Models.Etnia;
import com.amihealth.amihealth.Models.Provincia;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.Views.Fragmentos.Registro.presenter.RegistroPresenterIMP;
import com.amihealth.amihealth.Views.Fragmentos.Registro.presenter.RegistroPresenterINT;
import com.amihealth.amihealth.Views.RegistroIMP;
import com.amihealth.amihealth.Views.RegistroInterface;
import com.amihealth.amihealth.Views.RegistroViewINTR;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserLocationFragment extends Fragment implements Step, RegistroViewINT {

    private Spinner                         sp_provincia;
    private Spinner                         sp_distrito;
    private Spinner                         sp_corregimiento;
    private TextView                        text_provincia;
    private TextView                        text_distrito;
    private TextView                        text_corregimiento;

    private EditText                        direccion;
    private EditText                        numero;
    private Direccion                       direccion_POJO;


    private ArrayList<Provincia>            provincias;
    private ArrayAdapter<Provincia>         adapterProvincia;

    private ArrayList<Distrito>             distritos;
    private ArrayAdapter<Distrito>          adapterDistrito;

    private ArrayList<Corregimiento>        corregimientos;
    private ArrayAdapter<Corregimiento>     adapterCorregimiento;

    private RegistroPresenterINT registroPresenterINT;
    private RegistroInterface registroInterface;


    public UserLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_location, container, false);

        provincias              = new ArrayList<>();
        distritos               = new ArrayList<>();
        corregimientos          = new ArrayList<>();

        registroPresenterINT    = new RegistroPresenterIMP(this);

        sp_corregimiento        = (Spinner) view.findViewById(R.id.rg_corregimiento);
        sp_distrito             = (Spinner) view.findViewById(R.id.rg_distrito);
        sp_provincia            = (Spinner) view.findViewById(R.id.rg_provincia);
        adapterProvincia        = new ArrayAdapter<Provincia>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,provincias);
        adapterDistrito         = new ArrayAdapter<Distrito>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,distritos);
        adapterCorregimiento    = new ArrayAdapter<Corregimiento>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item,corregimientos);

        sp_provincia            .setAdapter(adapterProvincia);
        sp_distrito             .setAdapter(adapterDistrito);
        sp_corregimiento        .setAdapter(adapterCorregimiento);

        registroPresenterINT    .getProvincia(getContext());
        sp_provincia            .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                registroPresenterINT    .getDistritos(getContext(), ((Provincia)parent.getSelectedItem()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_distrito              .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                registroPresenterINT    .getCorregimientos(getContext(),((Distrito)adapterView.getSelectedItem()).getId());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        text_corregimiento  = (TextView) view.findViewById(R.id.rg_corregimiento_text);
        text_distrito       = (TextView) view.findViewById(R.id.rg_distrito_text);
        direccion           = (EditText) view.findViewById(R.id.rg_direccion);
        numero              = (EditText) view.findViewById(R.id.rg_telefono);
        return view;
    }

    @Override
    public VerificationError verifyStep() {

        ArrayList<VerificationError> verificationErrors = new ArrayList<>();
        verificationErrors.add(new Validador().VerificarCampo(getContext(),direccion));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),numero));
        if (numero.getText().length() < 8){
            verificationErrors.add(new VerificationError("Error numero de Teléfono"));
            numero.setError("Error numero de Teléfono");
        }
        for( int i = 0; i < verificationErrors.size(); i++){
            if(verificationErrors.get(i) != null){
                return verificationErrors.get(i);
            }
        }

        try{

            registroInterface       .getDataLocation(
                                    ((Provincia)sp_provincia.getSelectedItem()).getId(),
                                    ((Distrito)sp_distrito.getSelectedItem()).getId(),
                                    ((Corregimiento)sp_corregimiento.getSelectedItem()).getId(),
                                    direccion.getText().toString(),
                                    numero.getText().toString());

        }catch (ClassCastException e){
            throw new IllegalStateException("IMPLEMENTAR LA INTERFAZ "+ UserRegistroFragment.Textos.class.getName());
        }

        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void getProvincia(ArrayList<Provincia> provincias) {
        this.provincias         .clear();
        this.provincias         .addAll(provincias);
        this.adapterProvincia   .notifyDataSetChanged();
        registroPresenterINT    .getDistritos(getContext(), provincias.get(0).getId());
        sp_distrito             .setSelection(0);
        sp_corregimiento        .setSelection(0);


    }

    @Override
    public void getDistritos(ArrayList<Distrito> distritos) {
        this.distritos          .clear();
        this.distritos          .addAll(distritos);
        this.adapterDistrito    .notifyDataSetChanged();
        registroPresenterINT    .getCorregimientos(getContext(),this.distritos.get(0).getId());
        sp_distrito             .setSelection(0);

    }

    @Override
    public void getCorregimientos(ArrayList<Corregimiento> corregimientos) {
        this.corregimientos         .clear();
        this.corregimientos         .addAll(corregimientos);
        this.adapterCorregimiento   .notifyDataSetChanged();
        sp_corregimiento            .setSelection(0);

    }

    @Override
    public void getEtnias(ArrayList<Etnia> etnias) {

    }

    @Override
    public void responseNewUser() {

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
