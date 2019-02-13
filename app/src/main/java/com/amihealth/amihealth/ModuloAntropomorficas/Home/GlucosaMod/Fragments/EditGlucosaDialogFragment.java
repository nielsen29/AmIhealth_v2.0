package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.R;

import io.realm.Realm;

/**
 * Created by GITCE on 01/13/18.
 */

public class EditGlucosaDialogFragment extends DialogFragment {

    private static String datoPeso = "";
    private EditText peso;
    private TextView mensaje;
    private Spinner sp_kg;
    private int pos;
    private Button botonamicheck;
    public EditGlucosaDialogFragment() {
        super();
    }

    public static EditGlucosaDialogFragment getInstance(String id){
        datoPeso = id;
        EditGlucosaDialogFragment f = new EditGlucosaDialogFragment();
        return f;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

            return crearDialogo(datoPeso);



    }

    public AlertDialog crearDialogo(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.nuevo_glucosa_dialogo,null);
        Realm realm = Realm.getDefaultInstance();
        Glucosa pes = realm.where(Glucosa.class).equalTo("id",id).findFirst();
        peso = (EditText) v.findViewById(R.id.nuevo_glucosa_txt);
        peso.setHint("(mg/dl)");
        botonamicheck=(Button)v.findViewById(R.id.usar_amicheck);
        botonamicheck.setVisibility(View.INVISIBLE);
        sp_kg = (Spinner) v.findViewById(R.id.sp_kg);
        String[] s = {"Antes de comer","2 horas poscomida"};
        sp_kg.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,s));
        sp_kg.setEnabled(true);
        sp_kg.setVisibility(View.INVISIBLE);
        peso.setText(String.valueOf(pes.getGlucosa()));
        builder.setView(v);
        sp_kg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.setPositiveButton(getContext().getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(peso.getText().length() <= 0){
                    peso.setError("Este campo no puede estar vacio");
                    peso.setText("0");
                    mListener.onDialogPositiveEdit(id,Double.valueOf(peso.getText().toString()),0);
                    dialogInterface.cancel();

                }else{
                    mListener.onDialogPositiveEdit(id,Double.valueOf(peso.getText().toString()),pos+1);
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
        builder.setTitle(getString(R.string.editar_Glucosa_title));

        return builder.create();

    }
    public interface AddPesoDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, double value,int pos);
        public void onDialogPositiveEdit(String id, double value,int pos);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    // Use this instance of the interface to deliver action events
    AddGlucosaDialogFragment.AddPesoDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddGlucosaDialogFragment.AddPesoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }



}
