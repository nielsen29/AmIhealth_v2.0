package com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.R;

import java.text.DecimalFormat;

import io.realm.Realm;

/**
 * Created by GITCE on 01/13/18.
 */

public class EditPesoDialogFragment extends DialogFragment {

    private static String datoPeso = "";
    private EditText peso;
    private Spinner sp_kg;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private double medida = 0;
    private int pos;


    public EditPesoDialogFragment() {
        super();
    }

    public static EditPesoDialogFragment getInstance(String id){
        datoPeso = id;
        EditPesoDialogFragment f = new EditPesoDialogFragment();
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
        View v = inflater.inflate(R.layout.nuevo_peso_dialogo,null);
        Realm realm = Realm.getDefaultInstance();
        Peso pes = realm.where(Peso.class).equalTo("id",id).findFirst();
        peso = (EditText) v.findViewById(R.id.nuevo_peso_txt);
        peso.setText(String.valueOf(pes.getPeso()));

        sp_kg = (Spinner) v.findViewById(R.id.sp_kg);
        String[] s = {"Kg","Lb"};
        sp_kg.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,s));


        builder.setView(v);

        sp_kg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                setTXT(medida);
                transformar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        peso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((s.length() > 0)){
                    if(!peso.getText().equals("") && peso != null){
                        if(sp_kg.getSelectedItemId() != 0){
                            if(Double.valueOf(peso.getText().toString()) > 0 ){
                                transformar();
                                //double a = Double.valueOf(peso.getText().toString())/2.20462;
                                peso.setError(String.valueOf(decimalFormat.format(medida)) + " en Kg");
                            }
                        }else{
                            if(Double.valueOf(peso.getText().toString()) > 0 ){
                                transformar();
                                //double a = Double.valueOf(peso.getText().toString())/0.453592;
                                peso.setError(String.valueOf(decimalFormat.format(medida)) + "en Lb");
                            }
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        builder.setPositiveButton(getContext().getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if(peso.getText().length() <= 0){
                    peso.setText("0");
                   // mListener.onDialogPositiveClick(AddPesoDialogFragment.this,Double.valueOf(peso.getText().toString()));

                    mListener.onDialogPositiveEdit(id,Double.valueOf(peso.getText().toString()));

                }else{
                    if(pos == 1){
                        peso.setText(String.valueOf(medida));
                    }
                    mListener.onDialogPositiveEdit(id,Double.valueOf(peso.getText().toString()));

                   // mListener.onDialogPositiveClick(AddPesoDialogFragment.this,Double.valueOf(peso.getText().toString()));
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

    public void transformar(){

        if(peso.getText().length() > 0){
            if(sp_kg.getSelectedItemId() != 0){

                if(Double.valueOf(peso.getText().toString()) > 0 ){
                    double a = Double.valueOf(peso.getText().toString())/2.20462;
                    medida = a;
                    peso.setError(String.valueOf(decimalFormat.format(medida)) + "|| Kg");
                }
            }else{
                if(Double.valueOf(peso.getText().toString()) > 0 ){
                    double a = Double.valueOf(peso.getText().toString())/0.453592;
                    medida = a;
                    peso.setError(String.valueOf(decimalFormat.format(medida)) + "|| Lb");
                }
            }
        }

    }
    public void setTXT(double a){
        if(medida != 0){
            peso.setText(String.valueOf(decimalFormat.format(medida)));
        }
    }

    // Use this instance of the interface to deliver action events
    AddPesoDialogFragment.AddPesoDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddPesoDialogFragment.AddPesoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }



}
