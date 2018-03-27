package com.amihealth.amihealth.ModuloHTA;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.amihealth.amihealth.AppConfig.OnDialogResponse;
import com.amihealth.amihealth.AppConfig.StaticError;
import com.amihealth.amihealth.AppConfig.notification.NewMessageNotification;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Home.HomeActivity;
import com.amihealth.amihealth.Models.AmIHealthNotificacion;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.OnStaticErrorAlarm;
import com.amihealth.amihealth.ModuloHTA.view.fragments.HTAGraficasFragment;
import com.amihealth.amihealth.ModuloHTA.view.fragments.HTAListFragment;
import com.amihealth.amihealth.ModuloHTA.view.fragments.IntroAddMedidas;
import com.amihealth.amihealth.ModuloHTA.view.fragments.OrdenSelectorListener;
import com.amihealth.amihealth.ModuloHTA.view.fragments.lolFragment;
import com.amihealth.amihealth.R;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class HTAhomeActivity extends AppCompatActivity implements HTAListFragment.OnFragmentInteractionListener, HTAGraficasFragment.OnFragmentInteractionListener, OnDialogResponse, OnStaticErrorAlarm {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Spinner spinnerAction;
    private SessionManager sessionManager;
    private FragmentHTAadapter fragmentHTAadapter;

    private FragmentManager fragmentManager;
    private List<Fragment> list;
    public String orderT;
    private HTAListFragment htaListFragment;
    private int tabPos = 0;
    public static OrdenSelectorListener orderListener = new OrdenSelectorListener() {
        @Override
        public void orderListener(int order) {

        }
    };
    public static OrdenSelectorListener.OrdenGraficaListener GrafOrderListener = new OrdenSelectorListener.OrdenGraficaListener() {
        @Override
        public void orderGraficListener(int order) {

        }
    };
    private static ArrayList<OrdenSelectorListener> listaOrdenArray;

    public void setOrderListener(OrdenSelectorListener orderListener) {
        this.orderListener = orderListener;
    }

    public static void setGrafOrderListener(OrdenSelectorListener.OrdenGraficaListener grafOrderListener) {
        GrafOrderListener = grafOrderListener;
    }



    private StaticError staticError;
    private AlertDialog alertDialog;

    private static final String EXTRA_ALERTA = "alerta";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htahome);


        listaOrdenArray = new ArrayList<>();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        list = new ArrayList<>();
        htaListFragment = new HTAListFragment();
        staticError = new StaticError(this);
        alertDialog = staticError.getErrorDialogAlert(this,StaticError.ESPERA);
        alertDialog.setCancelable(false);
        //setOrderListener((OrdenSelectorListener) htaListFragment);
        showtoolbar(getResources().getString(R.string.title_presionarterial),true);
        setTabLayout();
        setViewPager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_nuevamedida2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goNuevaMedidad();

            }
        });

        alertDialog.show();
        if(getIntent() != null){

        }





    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("tabPos",tabPos);
        outState.putInt("spPos",spinnerAction.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //listaOrdenArray.get(savedInstanceState.getInt("tabPos")).orderListener(savedInstanceState.getInt("spPos"));
    }

    private void setAlarmCase(){

        this.runOnUiThread(new Runnable() {
            public void run() {
                StaticError staticError = new StaticError();
                staticError.getError(getApplicationContext(),StaticError.CONEXION);
            }
        });

    }

    private void goNuevaMedidad(){
        Intent intent = new Intent(this,NuevaMedidaHTA.class);
        startActivityForResult(intent, 1000);
    }


    public void showtoolbar(String titulo, boolean mUpbtn){
        Toolbar toolbar         = (Toolbar) findViewById(R.id.toolbarAction);
        setSupportActionBar(toolbar);
        getSupportActionBar()   .setTitle(titulo);
        getSupportActionBar()   .setDisplayHomeAsUpEnabled(mUpbtn);
        spinnerAction   = (Spinner) findViewById(R.id.sp_actionbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),android.R.layout.simple_spinner_item, new String[]{"Semanal","Mensual","Anual"});
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerAction.setAdapter(adapter);

    }

    public void setTabLayout(){
        this.tabLayout = (TabLayout) findViewById(R.id.tabsLayout);


    }

    public void setViewPager(){
        this.viewPager = (ViewPager) findViewById(R.id.viewpagerHTA);

        fragmentManager = getSupportFragmentManager();
        fragmentHTAadapter = new FragmentHTAadapter(fragmentManager, spinnerAction.getSelectedItem().toString() );
        //fragmentList.addAll(fragmentHTAadapter.arr());
        viewPager.setAdapter(fragmentHTAadapter);
        tabLayout.setupWithViewPager(this.viewPager);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabPos = tab.getPosition();
                switch (tabPos){
                    case 0:
                        orderListener.orderListener(spinnerAction.getSelectedItemPosition());
                        break;
                    case 1:
                        GrafOrderListener.orderGraficListener(spinnerAction.getSelectedItemPosition());
                        break;

                }


                //((OrdenSelectorListener) fragmentHTAadapter.getItem(tab.getPosition())).orderListener(spinnerAction.getSelectedItemPosition());



                //listaOrdenArray.get(tab.getPosition()).orderListener(spinnerAction.getSelectedItemPosition());


                //((OrdenSelectorListener)list.get(tab.getPosition())).orderListener(2,spinnerAction.getSelectedItem().toString());

                //Toast.makeText(getApplicationContext(),"MIERRRRRDAAAA",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                switch (tabPos){
                    case 0:
                        orderListener.orderListener(spinnerAction.getSelectedItemPosition());
                        break;
                    case 1:
                        GrafOrderListener.orderGraficListener(spinnerAction.getSelectedItemPosition());
                        break;

                }

                //orderListener.orderListener(spinnerAction.getSelectedItemPosition());

            }
        });
        spinnerAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                switch (tabPos){
                    case 0:
                        orderListener.orderListener(i);
                        break;
                    case 1:
                        GrafOrderListener.orderGraficListener(i);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                switch (tabPos){
                    case 0:
                        orderListener.orderListener(adapterView.getSelectedItemPosition());
                        break;
                    case 1:
                        GrafOrderListener.orderGraficListener(adapterView.getSelectedItemPosition());
                        break;

                }

                }



        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*@Override
    public void orderListener(String order) {

        //((OrdenSelectorListener) getFragmentManager().findFragmentById(R.id.fragmenthtaMedidas)).orderListener(order);
        //((OrdenSelectorListener)  fragmentHTAadapter.arr().get(tabLayout.getSelectedTabPosition())).orderListener(order);
        //((OrdenSelectorListener) getSupportFragmentManager().getFragments().get(tabLayout.getSelectedTabPosition())).orderListener(order);


    }*/

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof OrdenSelectorListener){
            this.setOrderListener((OrdenSelectorListener) fragment);
            //listaOrdenArray.add((OrdenSelectorListener) fragment);
        }
        if(fragment instanceof OrdenSelectorListener.OrdenGraficaListener){
            this.setGrafOrderListener((OrdenSelectorListener.OrdenGraficaListener) fragment);
        }
        //Toast.makeText(getApplicationContext(),"ON_ATTACH FRAG", Toast.LENGTH_LONG).show();

        //((OrdenSelectorListener) fragment).orderListener(spinnerAction.getSelectedItem().toString());
    }


    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(),"ONPause", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        OnProgressOn();
        //Toast.makeText(getApplicationContext(),"ON_RESTAR", Toast.LENGTH_LONG).show();
        //setOrderListener((OrdenSelectorListener) getSupportFragmentManager().getFragments().get(tabLayout.getSelectedTabPosition()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //listaOrdenArray = new ArrayList<>();
        //Toast.makeText(getApplicationContext(),"ONRESUME", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        //Toast.makeText(getApplicationContext(),"ONRESUMEFRAG", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
       //Toast.makeText(getApplicationContext(),"ONSTAR", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentGraficaInteraction(Uri uri) {

    }

    @Override
    public void retryConection() {

        staticError.getErrorD(this,StaticError.CONEXION);

    }

    @Override
    public void retryBusqueda() {

    }

    @Override
    public void declineBusqueda() {

    }

    @Override
    public void OnProgressOn() {
        alertDialog.show();
    }

    @Override
    public void OnProgressOff() {
        alertDialog.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
                if(resultCode == Activity.RESULT_OK) {
                    staticError.getErrorD(this, StaticError.ALARMA_HTA);
                }

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public  class FragmentHTAadapter extends FragmentPagerAdapter {

        private String title[] = new String[]{"Medidas", "Graficas", "Estado"};
        public final String orderData;

        private FragmentManager frag;

        public FragmentHTAadapter(FragmentManager fm, String order) {
            super(fm);
            this.orderData = order;
            list = new ArrayList<>();
            this.frag = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            switch (position){

                case 0:
                    f = new HTAListFragment();
                    //setOrderListener((OrdenSelectorListener) f);

                    listaOrdenArray.add(position,(OrdenSelectorListener) f);

                    break;
                case 1:
                    f = new HTAGraficasFragment();
                    //setOrderListener((OrdenSelectorListener) f);
                    setGrafOrderListener((OrdenSelectorListener.OrdenGraficaListener) f);
                    break;
                default:
                    f = new lolFragment();


                    break;
            }
            return f;
        }

        public List<Fragment> arr(){

            return list;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        public FragmentManager getFrag() {
            return this.frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

    }




}
