package com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.R;

import io.realm.Realm;

/**
 * Created by GITCE on 01/13/18.
 */

public class EditPesoDialogFragment extends DialogFragment {

    private static String datoPeso = "";
    private EditText peso;


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
        builder.setView(v);
        builder.setPositiveButton(getContext().getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogPositiveEdit(id,Double.valueOf(peso.getText().toString()));
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
