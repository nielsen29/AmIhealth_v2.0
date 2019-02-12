package com.amihealth.amihealth.ModuloHTA.view.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.amihealth.amihealth.Adaptadores.DeviceListActivity;
import com.amihealth.amihealth.AppConfig.OnDialogResponse;
import com.amihealth.amihealth.Contract.ContractHTA;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.NuevaMedidaHTA;
import com.amihealth.amihealth.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;

import io.realm.Realm;

/**
 * Created by amihealthmel on 12/21/17.
 */

public class IntroFrecuenciaCardiaca extends AppIntro {
    public static String ID;
    public boolean hasID = false;
    private OnDialogResponse startDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(getIntent().hasExtra("ID")){
            ID = getIntent().getExtras().getString("ID");
            hasID = true;
        }

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment
                .newInstance(
                        "Frecuencia Cardiaca",
                        "A continuación se leera su frecuencia cardiaca, mediante una grafica en tiempo real.",
                        R.drawable.myplate_slider,
                        ContextCompat.getColor(getApplicationContext(),R.color.celesteBR)
                ));
        addSlide(AppIntroFragment
                .newInstance(
                        "Verduras",
                        "La mitad del plato debe corresponder a alimentos del grupo de las verduras en cualquier tipo de preparación exceptuando aquellas en las les que se adicione grasa o altas cantidades de sodio (fritas, con mantequillas, capeadas, etc).\n"
                        ,R.drawable.vegetable,
                        ContextCompat.getColor(getApplicationContext(),R.color.dark_celesteBR)
                ));
        addSlide(AppIntroFragment
                .newInstance(
                        "Cereales",
                        "Un cuarto del plato debe corresponder a alimentos del grupo de los cereales y tubérculos, esto incluye sopa de pasta o arroz, tortilla, papa, elote, pan y cualquier otro derivado de los cereales." +
                                "Esto quiere decir, que si se comen 2 alimentos de este grupo en el mismo tiempo de comida (Ejemplo: arroz y tortilla), la ración de cada uno debe ser menor.\n" +
                                "Es importante no excederse ya que los niveles de glucosa en sangre se podrían ver afectados.",
                        R.drawable.cereals,
                        ContextCompat.getColor(getApplicationContext(),R.color.dark_celesteBR)
                ));





        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        if(hasID){
            Intent i = new Intent(this, DeviceListActivity.class);
            i.putExtra("pulso",ID);
            startActivity(i);
            finish();
        }else{
            if(getParent() instanceof OnDialogResponse){
                startDialog = (OnDialogResponse) getParent();
                startDialog.retryConection();
                finish();
            }else{
                //Intent i = new Intent(this, NuevaMedidaHTA.class);
                //startActivity(i);
                finish();
            }


        }

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        if(hasID){
            Intent i = new Intent(this, DeviceListActivity.class);
            i.putExtra("pulso",ID);
            startActivity(i);
            finish();
        }else{
            if(this.getParent() instanceof OnDialogResponse){
                startDialog = (OnDialogResponse) getParent();
                startDialog.retryConection();
                finish();
            }else{
                //Intent i = new Intent(this, NuevaMedidaHTA.class);
                //startActivity(i);
                finish();
            }
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
