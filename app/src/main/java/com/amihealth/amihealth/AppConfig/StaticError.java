package com.amihealth.amihealth.AppConfig;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.amihealth.amihealth.R;

/**
 * Created by amihealthmel on 02/20/18.
 */

public class StaticError {

    public static final String CONEXION = "1";
    public static final String VACIO = "2";
    public static final String COINCIDENCIAS = "3";
    public static final String CAMPO_VACIO = "4";
    public static final String ESPERA = "5";
    public static final String ALARMA = "100";
    public static final String PASTILLA = "101";
    public static final String ALARMA_HTA = "HTA";


    private Context context;
    private Activity activity;
    private FragmentManager fragmentManager;

    private boolean isError;

    private OnDialogResponse dialogResponse;

    public StaticError() {
    }
    public StaticError(Activity activity){
        this.activity = activity;
    }

    public void getError(Context activity, String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if(activity instanceof OnDialogResponse) {
            dialogResponse = (OnDialogResponse) activity;

            switch (error) {
                case CONEXION:
                    builder.setTitle("Error de Conexion");
                    builder.setMessage("Ocurrio un error de conexion Asegurese de estar conectado a una red wifi o en su defecto que cuente con un plan de datos activos");
                    builder.setPositiveButton(R.string.reintentar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                case VACIO:
                    builder.setTitle(R.string.error_title_vacio);
                    builder.setMessage(R.string.msj_vacio);
                    builder.setNegativeButton(R.string.accept, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                case COINCIDENCIAS:

                    builder.setTitle("Error de Conexion");
                    builder.setMessage("Ocurrio un error de conexion Asegurese de estar conectado a una red wifi o en su defecto que cuente con un plan de datos activos");
                    builder.create();
                case CAMPO_VACIO:



            }
        }


    }
    public void getErrorD(Context context, String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if(activity instanceof OnDialogResponse) {
            dialogResponse = (OnDialogResponse) activity;

            switch (error) {
                case CONEXION:
                    builder.setTitle("Error de Conexion");
                    builder.setMessage("Ocurrio un error de conexion Asegurese de estar conectado a una red wifi o en su defecto que cuente con un plan de datos activos");
                    builder.setPositiveButton(R.string.reintentar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setCancelable(false);

                    builder.create().show();
                    break;
                case ALARMA_HTA:
                    builder.setTitle("ALERTA");
                    builder.setMessage(R.string.msj_alerta_hta);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setCancelable(false);

                    builder.create().show();
                    break;
                case VACIO:
                    builder.setTitle(R.string.error_title_vacio);
                    builder.setMessage(R.string.msj_vacio);
                    builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                    break;
                case ALARMA:
                    builder.setTitle(R.string.error_title_alarma);
                    builder.setMessage(R.string.msj_alerta);
                    builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                    break;
                case ESPERA:
                    builder.setTitle(R.string.error_title_espera);
                    builder.setMessage(R.string.procesando);
                    builder.setView(R.layout.progress_layout);
                    builder.create().show();
                    break;
                case COINCIDENCIAS:
                    builder.setTitle("Error de Conexion");
                    builder.setMessage("Ocurrio un error de conexion Asegurese de estar conectado a una red wifi o en su defecto que cuente con un plan de datos activos");
                    builder.create();
                case CAMPO_VACIO:
                    break;
                default:
                    break;

            }
        }



    }
    public AlertDialog getErrorDialogAlert(Context context, String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if(activity instanceof OnDialogResponse) {
            dialogResponse = (OnDialogResponse) activity;

            switch (error) {
                case CONEXION:
                    builder.setTitle("Error de Conexion");
                    builder.setMessage("Ocurrio un error de conexion Asegurese de estar conectado a una red wifi o en su defecto que cuente con un plan de datos activos");
                    builder.setPositiveButton(R.string.reintentar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                     return builder.create();

                case VACIO:
                    builder.setTitle(R.string.error_title_vacio);
                    builder.setMessage(R.string.msj_vacio);
                    builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    return builder.create();

                case ESPERA:
                    builder.setTitle(R.string.error_title_espera);
                    builder.setMessage(R.string.procesando);
                    builder.setView(R.layout.progress_layout);
                    return builder.create();

                case COINCIDENCIAS:
                    builder.setTitle("Error de Conexion");
                    builder.setMessage("Ocurrio un error de conexion Asegurese de estar conectado a una red wifi o en su defecto que cuente con un plan de datos activos");
                    return builder.create();
                case CAMPO_VACIO:
                    return  null;

                default:
                    return null;


            }
        }
        return  null;



    }
}
