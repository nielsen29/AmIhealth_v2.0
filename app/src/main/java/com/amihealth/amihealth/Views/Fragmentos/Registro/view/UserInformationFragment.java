package com.amihealth.amihealth.Views.Fragmentos.Registro.view;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amihealth.amihealth.Models.Corregimiento;
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
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInformationFragment extends Fragment implements Step, GetFecha, RegistroViewINT {

    private EditText nombre;
    private EditText apellido;
    private EditText cedula;
    private EditText tomo;
    private EditText asiento;
    private TextView etnia;
    private TextView sexo;
    private EditText fecha;

    private Spinner sp_etnia;
    private Spinner sp_sexo;
    private Spinner sp_fecha;

    private ArrayList<String> arraySexo;
    private ArrayAdapter<String> adapterSexo;


    private ArrayList<Etnia> etnias;
    private ArrayAdapter<Etnia> adapterEtnias;
    private RegistroPresenterINT presenterINT;

    private Button btn_fecha_rg;

    private RegistroInterface registroInterface;

    private  int year,mes,dia;

    public UserInformationFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view       = inflater.inflate(R.layout.fragment_user_information, container, false);
        nombre          = (EditText) view.findViewById(R.id.rg_nombre);
        apellido        = (EditText) view.findViewById(R.id.rg_apellido);
        cedula          = (EditText) view.findViewById(R.id.rg_cedula);
        tomo          = (EditText) view.findViewById(R.id.rg_tomo);
        asiento          = (EditText) view.findViewById(R.id.rg_asiento);
        fecha           = (EditText) view.findViewById(R.id.rg_fecha_nacimiento);
        sexo            = (TextView) view.findViewById(R.id.rg_sexo);
        etnia           = (TextView) view.findViewById(R.id.rg_etnia_text);
        sp_etnia        = (Spinner) view.findViewById(R.id.rg_etnia);
        sp_sexo         = (Spinner) view.findViewById(R.id.rg_sp_sexo);

        presenterINT    = new RegistroPresenterIMP(this);
        etnias          = new ArrayList<>();
        adapterEtnias   = new ArrayAdapter<Etnia>(getContext(), R.layout.support_simple_spinner_dropdown_item,etnias);

        arraySexo       = new ArrayList<>();
        arraySexo       .add("Mujer");
        arraySexo       .add("Hombre");
        adapterSexo     = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,arraySexo);
        sp_sexo         .setAdapter(adapterSexo);

        sp_etnia        .setAdapter(adapterEtnias);
        presenterINT    .getEtnias(getContext());

        btn_fecha_rg    = (Button) view.findViewById(R.id.btn_fecha_rg);

        btn_fecha_rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFechaDialogo(view);
            }
        });

        return view;
    }

    public void showFechaDialogo(View v){
        final Calendar calendar = Calendar.getInstance(new Locale("PA"));
        int year = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        new FechaFragment(getContext(),fechaListener,year,mes,dia).show();
    }

    private DatePickerDialog.OnDateSetListener fechaListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            fecha.setText(String.valueOf(i)+"/"+String.valueOf(i1 + 1)+"/"+String.valueOf(i2));
        }
    };

    @Override
    public VerificationError verifyStep() {
        ArrayList<VerificationError> verificationErrors = new ArrayList<>();
        verificationErrors.add(new Validador().VerificarCampo(getContext(),cedula));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),tomo));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),asiento));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),nombre));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),apellido));
        verificationErrors.add(new Validador().VerificarCampo(getContext(),fecha));
        for( int i = 0; i < verificationErrors.size(); i++){
            if(verificationErrors.get(i) != null){
                return verificationErrors.get(i);
            }
        }

        try{

            registroInterface.getDataPersonal(
                    cedula.getText().toString(),
                    tomo.getText().toString(),
                    asiento.getText().toString(),
                    nombre.getText().toString(),
                    apellido.getText().toString(),fecha.getText().toString(),sp_sexo.getSelectedItemPosition(),1);
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
    public void getfecha(int yyyy, int mm, int dd) {

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
        this.etnias.clear();
        this.etnias.addAll(etnias);
        this.adapterEtnias.notifyDataSetChanged();

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
