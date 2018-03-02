package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.AddPesoDialogFragment;
import com.amihealth.amihealth.R;

import io.realm.Realm;

/**
 * Created by GITCE on 01/13/18.
 */

public class EditCinturaDialogFragment extends DialogFragment {

    private static String datoPeso = "";
    private EditText peso;
    private TextView mensaje;
    private Spinner sp_kg;


    public EditCinturaDialogFragment() {
        super();
    }

    public static EditCinturaDialogFragment getInstance(String id){
        datoPeso = id;
        EditCinturaDialogFragment f = new EditCinturaDialogFragment();
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
        Cintura pes = realm.where(Cintura.class).equalTo("id",id).findFirst();
        peso = (EditText) v.findViewById(R.id.nuevo_peso_txt);
        peso.setHint("Cintura (cm)");
        mensaje = (TextView)  v.findViewById(R.id.add_mensaje);
        mensaje.setText("Esta medición se debería tomar:\n" +
                "• al final de una espiración normal,\n" +
                "• con los brazos relajados a cada lado,\n" +
                "• a la altura de la mitad de la axila, en el punto que se encuentra entre la parte\n" +
                "inferior de la última costilla y la parte más alta de la cadera.");
        sp_kg = (Spinner) v.findViewById(R.id.sp_kg);
        String[] s = {"Kg","Lb"};
        sp_kg.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,s));
        sp_kg.setEnabled(false);
        sp_kg.setVisibility(View.INVISIBLE);
        peso.setText(String.valueOf(pes.getCintura()));
        builder.setView(v);
        builder.setPositiveButton(getContext().getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(peso.getText().length() <= 0){
                    peso.setError("Este campo no puede estar vacio");
                    peso.setText("0");
                    mListener.onDialogPositiveEdit(id,Double.valueOf(peso.getText().toString()));

                    //mListener.onDialogPositiveClick(AddCinturaDialogFragment.this,Double.valueOf(peso.getText().toString()));
                    dialogInterface.cancel();

                }else{
                    mListener.onDialogPositiveEdit(id,Double.valueOf(peso.getText().toString()));

                    //mListener.onDialogPositiveClick(AddCinturaDialogFragment.this,Double.valueOf(peso.getText().toString()));
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
        builder.setTitle(getString(R.string.editar_Cintura_title));

        return builder.create();

    }

    // Use this instance of the interface to deliver action events
    AddCinturaDialogFragment.AddPesoDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddCinturaDialogFragment.AddPesoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }



}
