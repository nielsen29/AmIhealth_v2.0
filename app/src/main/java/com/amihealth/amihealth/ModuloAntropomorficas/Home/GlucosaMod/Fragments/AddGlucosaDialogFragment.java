package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amihealth.amihealth.Adaptadores.DeviceListActivity;
import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.R;

import io.realm.Realm;

/**
 * Created by GITCE on 01/12/18.
 */

public class AddGlucosaDialogFragment extends DialogFragment {

    private EditText glucosa;
    private Button amicheck;
    private Spinner sp_kg;
    private int pos;
    public static String ID;

    public AddGlucosaDialogFragment() {
        super();
    }




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       super.onCreateDialog(savedInstanceState);

           return crearDialogo();


    }

    public AlertDialog crearDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.nuevo_glucosa_dialogo,null);

        glucosa = (EditText) v.findViewById(R.id.nuevo_glucosa_txt);
        glucosa.setHint("(mg/dl)");
        amicheck=(Button)v.findViewById(R.id.usar_amicheck);
        //mensaje = (TextView)  v.findViewById(R.id.add_mensaje);
       /* mensaje.setText("Esta medición se debería tomar:\n" +
                "• al final de una espiración normal,\n" +
                "• con los brazos relajados a cada lado,\n" +
                "• a la altura de la mitad de la axila, en el punto que se encuentra entre la parte\n" +
                "inferior de la última costilla y la parte más alta de la cadera.");*/
        sp_kg = (Spinner) v.findViewById(R.id.sp_kg);
        String[] s = {"Antes de comer","2 horas poscomida"};
        sp_kg.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,s));
        sp_kg.setEnabled(true);
        sp_kg.setVisibility(View.VISIBLE);
        builder.setView(v);

        sp_kg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
               // Toast.makeText(getContext(),String.valueOf(pos),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        amicheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DeviceListActivity.class);
                i.putExtra("glucosa", ID);
                startActivity(i);
            }
        });
        glucosa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(glucosa.getText().length() <= 0){
                    glucosa.setError("Este campo no puede estar vacio");

                }
            }
        });


        builder.setPositiveButton(getContext().getString(R.string.btn_guardar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(glucosa.getText().length() <= 0){
                    glucosa.setError("Este campo no puede estar vacio");
                    glucosa.setText("0");
                    mListener.onDialogPositiveClick(AddGlucosaDialogFragment.this,Double.valueOf(glucosa.getText().toString()),0);
                    dialogInterface.cancel();

                }else{
                    mListener.onDialogPositiveClick(AddGlucosaDialogFragment.this,Double.valueOf(glucosa.getText().toString()),pos+1);
                    dismiss();

                }

            }
        });
        builder.setNegativeButton(getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setTitle(getString(R.string.nuevo_GLUCOSA_titulo));
        builder.setMessage(getString(R.string.info_nueva_medida_glucosa));

        return builder.create();

    }
    public AlertDialog crearDialogo(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.nuevo_peso_dialogo,null);
        Realm realm = Realm.getDefaultInstance();
        Glucosa pes = realm.where(Glucosa.class).equalTo("id",id).findFirst();
        glucosa = (EditText) v.findViewById(R.id.nuevo_peso_txt);
        glucosa.setText(String.valueOf(pes.getGlucosa()));
        builder.setView(v);
        builder.setPositiveButton(getContext().getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(glucosa.getText().length() <= 0){
                    glucosa.setError("Este campo no puede estar vacio");
                }else{
                    mListener.onDialogPositiveEdit(id,Double.valueOf(glucosa.getText().toString()),1);
                }
            }
        });
        builder.setNegativeButton(getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setTitle(getString(R.string.editar_peso_title));

        return builder.create();

    }


    public interface AddPesoDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, double value,int pos);
        public void onDialogPositiveEdit(String id, double value,int pos);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    AddPesoDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddPesoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
