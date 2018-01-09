package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.amihealth.amihealth.R;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.MedidasAntropometricasFragment;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.UserInformationFragment;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.UserLocationFragment;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.UserRegistroFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;


/**
 * Created by amihealthmel on 07/26/17.
 */

public class AdapterStepRegistro extends AbstractFragmentStepAdapter {

    private FragmentManager fragmentManager;
    private Context mContext;

    public AdapterStepRegistro(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
        this.fragmentManager = fm;
        this.mContext = context;
    }

    @Override
    public Step createStep(@IntRange(from = 0L) int position) {

        switch (position){
            case 0:{
                UserRegistroFragment a = new UserRegistroFragment();
                Bundle b = new Bundle();
                b.putInt("position",position);
                a.setArguments(b);
                return a;
            }
            case 1:{
                UserInformationFragment a = new UserInformationFragment();
                Bundle b = new Bundle();
                b.putInt("position",position);
                a.setArguments(b);
                return a;
            }
            case 2:{
                UserLocationFragment a = new UserLocationFragment();
                Bundle b = new Bundle();
                b.putInt("position",position);
                a.setArguments(b);
                return a;
            }
            case 3:{
                MedidasAntropometricasFragment a = new MedidasAntropometricasFragment();
                Bundle b = new Bundle();
                b.putInt("position",position);
                a.setArguments(b);
                return a;
            }
            default:{
                UserRegistroFragment a = new UserRegistroFragment();
                Bundle b = new Bundle();
                b.putInt("position",position);
                a.setArguments(b);
                return a;
            }
        }


    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0L) int position) {

        switch (position){
            case 0:{
                return new StepViewModel.Builder(context).setTitle(context.getResources().getString(R.string.rg_user_titulo)).create();
            }
            case 1:{
                return new StepViewModel.Builder(context).setTitle(context.getResources().getString(R.string.rg_user_generales_titulo)).create();
            }
            case 2:{
                return new StepViewModel.Builder(context).setTitle(context.getResources().getString(R.string.rg_user_dir_titulo)).create();
            }
            case 3:{
                return new StepViewModel.Builder(context).setTitle(context.getResources().getString(R.string.rg_user_medidas_titulo)).create();
            }
            default:{
                return new StepViewModel.Builder(context).setTitle("").create();
            }
        }

    }
}
