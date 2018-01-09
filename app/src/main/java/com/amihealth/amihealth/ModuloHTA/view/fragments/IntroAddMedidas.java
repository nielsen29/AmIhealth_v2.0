package com.amihealth.amihealth.ModuloHTA.view.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.amihealth.amihealth.Contract.ContractHTA;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.NuevaMedidaHTA;
import com.amihealth.amihealth.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import io.realm.Realm;

/**
 * Created by amihealthmel on 12/21/17.
 */

public class IntroAddMedidas extends AppIntro {
    public static String ID;
    public boolean hasID = false;
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
                        "Consejos",
                        "Medir siempre a la misma hora. Puesto que las personas tienen aproximadamente 100.000 niveles diferentes de presión arterial cada día, las mediciones aisladas no son válidas. Únicamente las mediciones regulares a las mismas horas del día a lo largo de un periodo prolongado de tiempo permiten evaluar razonablemente la presión arterial.",
                        R.drawable.ic_reco_img1,
                        ContextCompat.getColor(getApplicationContext(),R.color.celesteBR)
                ));
        addSlide(AppIntroFragment
                .newInstance(
                        "Tiempo para Medidas",
                        "",
                        R.drawable.ic_reco_img_actividad_02,
                        ContextCompat.getColor(getApplicationContext(),R.color.dark_celesteBR)
                ));
        addSlide(AppIntroFragment
                .newInstance(
                        "Medidas",
                        "",
                        R.drawable.ic_reco_img1,
                        ContextCompat.getColor(getApplicationContext(),R.color.dark2_celesteBR)
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
            Intent i = new Intent(this, NuevaMedidaHTA.class);
            i.putExtra("ID",ID);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(this, NuevaMedidaHTA.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        if(hasID){
            Intent i = new Intent(this, NuevaMedidaHTA.class);
            i.putExtra("ID",ID);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(this, NuevaMedidaHTA.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
