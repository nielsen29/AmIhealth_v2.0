package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

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

public class IntroDieta extends AppIntro {
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
                        "Consejos de Alimentación",
                        "La mayoría de los carbohidratos proviene de los almidones, las frutas, la leche y los dulces.\n " +
                                "Trate de limitar los carbohidratos que tienen azúcares agregados o granos refinados, como el pan blanco y el arroz blanco. \n" +
                                "En lugar de ellos consuma los carbohidratos de las frutas, verduras, granos enteros, leguminosas y leche descremada.",
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
        addSlide(AppIntroFragment
                .newInstance(
                        "Alimentos de Origen Animal",
                        "El último cuarto del plato debe corresponder a los alimentos del grupo de Alimentos de Origen Animal como son la carne de res, pollo, pescado o los quesos bajos en grasa como el panela, el fresco, requesón o cottage. \n" +
                                " El consumo de estos alimentos nos proveerá de proteínas que nuestro cuerpo necesita.",
                        R.drawable.meats,
                        ContextCompat.getColor(getApplicationContext(),R.color.dark2_celesteBR)
                ));
        addSlide(AppIntroFragment
                .newInstance(
                        "Frutas",
                        "Agregue una porción de fruta, producto lácteo, u ambos si su plan se lo permite.",
                        R.drawable.fruits,
                        ContextCompat.getColor(getApplicationContext(),R.color.dark2_celesteBR)
                ));
        addSlide(AppIntroFragment
                .newInstance(
                        "Ejercicios",
                        "Hable con su equipo de atención médica antes de comenzar una nueva rutina de ejercicio, sobre todo si usted tiene otros problemas de salud. \n" +
                                "Su equipo de atención médica le dirá el intervalo los valores deseables para su nivel de glucosa en la sangre y le sugerirá cómo ejercitarse de manera segura.",
                        R.drawable.medicalcare,
                        ContextCompat.getColor(getApplicationContext(),R.color.dark2_celesteBR)
                ));

        addSlide(AppIntroFragment
                .newInstance(
                        "Ejercicios",
                        "El ejercicio aeróbico es una actividad que hace que el corazón lata más rápido y que le sea más difícil respirar.\n" +
                                " La meta es hacer ejercicio aeróbico durante 30 minutos al día, la mayoría de los días de la semana. " +
                                " Puede dividir estos minutos en unos cuantos periodos a lo largo del día.\n",
                        R.drawable.ic_reco_img_actividad_02,
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
            Intent i = new Intent(this, NuevaMedidaHTA.class);
            i.putExtra("ID",ID);
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
