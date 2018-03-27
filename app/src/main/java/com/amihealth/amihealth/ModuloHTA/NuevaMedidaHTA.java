package com.amihealth.amihealth.ModuloHTA;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Contract.ContractHTA;
import com.amihealth.amihealth.Models.Clasificaciones;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.presenter.ImplementPresenterHTA;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.ViewHTA;
import com.amihealth.amihealth.ModuloHTA.view.fragments.IntroAddMedidas;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.PresenterHta;
import com.amihealth.amihealth.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class NuevaMedidaHTA extends AppCompatActivity implements InterfaceHta {

    private Button btn;
    private Button btn_cancel;
    private EditText txtSys;
    private EditText txtDis;
    private EditText txtPls;
    private NumberPicker nSys;
    private NumberPicker nDis;
    private NumberPicker nPls;
    private MedidaHTA medidaHTA;
    private SessionManager sessionManager;
    private int aux;
    private int ID_MEDIDA;

    private PresenterHta presenterHta;

    public static String ID;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inputs_hta);
        sessionManager = new SessionManager(getApplicationContext());

        presenterHta = new ImpPresenterHta(this,getApplicationContext());

        aux = 0;


        if(getIntent().hasExtra("ID")){
            //se inicia la instancia del toolbar TITULO/TRUE para mostrar la flecha de back
            showtoolbar(getString(R.string.title_nuevamedida_edit).toString(), true);
            Realm realm = Realm.getDefaultInstance();
            ID = getIntent().getExtras().getString("ID");
            this.medidaHTA = realm.where(MedidaHTA.class).equalTo(ContractHTA._ID,ID).findFirst();
            instanciaCamposTOedit();
        }else{

            Intent i = new Intent(this, IntroAddMedidas.class);
            startActivity(i);
            //se inicia la instancia del toolbar TITULO/TRUE para mostrar la flecha de back
            showtoolbar(getString(R.string.title_nuevamedida).toString(), true);
            instanciaCampos();

        }



        AlertDialog.Builder builder = new AlertDialog.Builder(NuevaMedidaHTA.this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle(R.string.dialog_title_edit);

// 3. Get the AlertDialog from create()
         dialog = builder.create();





    }

    public void showtoolbar(String titulo, boolean mUpbtn){
        Toolbar toolbar         = (Toolbar) findViewById(R.id.toolbarInsert);
        setSupportActionBar(toolbar);
        getSupportActionBar()   .setTitle(titulo);
        getSupportActionBar()   .setDisplayHomeAsUpEnabled(false);

    }


    public void instanciaCampos(){

        //Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            //appBarLayout.setTitle(mItem.content);
        }


        btn         = (Button) findViewById(R.id.btn_insert_HTA);
        btn_cancel         = (Button) findViewById(R.id.cancelar);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAll(null);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
            }
        });


        nSys        = (NumberPicker) findViewById(R.id.nSys);
        nSys        .setMaxValue(250);
        nSys        .setMinValue(60);
        nSys        .setValue(120);

        nDis        = (NumberPicker) findViewById(R.id.nDis);
        nDis        .setMaxValue(250);
        nDis        .setMinValue(60);
        nDis        .setValue(80);

        nPls        = (NumberPicker) findViewById(R.id.nPls);
        nPls        .setMaxValue(250);
        nPls        .setMinValue(60);
    }

    public void instanciaCamposTOedit(){
        btn         = (Button) findViewById(R.id.btn_insert_HTA);
        btn.setText("Editar");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                dialog.show();
                getValuestoEdit();
            }
        });


        nSys        = (NumberPicker) findViewById(R.id.nSys);
        nSys        .setMaxValue(250);
        nSys        .setMinValue(60);
        nSys        .setValue(medidaHTA.getSYS());

        nDis        = (NumberPicker) findViewById(R.id.nDis);
        nDis        .setMaxValue(250);
        nDis        .setMinValue(60);
        nDis        .setValue(medidaHTA.getDIS());

        nPls        = (NumberPicker) findViewById(R.id.nPls);
        nPls        .setMaxValue(250);
        nPls        .setMinValue(60);
        nPls.setValue(medidaHTA.getPulso());
    }

    public void getValues(){

            medidaHTA = new MedidaHTA(nSys.getValue(),
                    nDis.getValue(),
                    nPls.getValue());
            HashMap<String,String> paciente = sessionManager.getUserLogin();
            medidaHTA.setId_paciente(paciente.get(sessionManager.KEY));
            medidaHTA.setSync(0);
            presenterHta.insertar(medidaHTA);

        //sessionManager.logoutUser()

    }
    public void getValuestoEdit(){
        MedidaHTA toEdit = new MedidaHTA();
        toEdit.setId(this.medidaHTA.getId());
        toEdit.setId_paciente(this.medidaHTA.getId_paciente());
        toEdit.setSYS(nSys.getValue());
        toEdit.setDIS(nDis.getValue());
        toEdit.setPulso(nPls.getValue());
        presenterHta.actualizar(toEdit);
        //sessionManager.logoutUser()

    }

    @Override
    public void getMedidas(int orden) {

    }

    @Override
    public void showAll(RealmResults<MedidaHTA> medidaHTAs) {
        dialog.cancel();
        Intent previousScreen = new Intent(getApplicationContext(), HTAhomeActivity.class);
        //Sending the data to Activity_A
        previousScreen.putExtra("alerta","lol");
        setResult(1, previousScreen);
        finish();
    }

    @Override
    public void acciones(String action) {

    }

    @Override
    public void menuOP(MenuItem item) {

    }

    @Override
    public void insertar(MedidaHTA medidaHTA) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hiddenLoad() {

    }

    @Override
    public void errorRetrofit(int code) {

    }

    @Override
    public void mensaje(String mensaje) {
        Intent previousScreen = new Intent(getApplicationContext(), HTAhomeActivity.class);
        //Sending the data to Activity_A
        previousScreen.putExtra("alerta",mensaje);
        setResult(Activity.RESULT_OK, previousScreen);
        finish();
        //setResult(1,new Intent(getApplicationContext(),HTAhomeActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAll(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent previousScreen = new Intent(getApplicationContext(), HTAhomeActivity.class);
        //Sending the data to Activity_A
        previousScreen.putExtra("alerta","lol");
        setResult(1, previousScreen);
    }
}
