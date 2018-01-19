package com.amihealth.amihealth.ModuloAntropomorficas.Home;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.AddPesoDialogFragment;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.EditPesoDialogFragment;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.PesoGraficaFragment;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.PesoListaFragment;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresenterInterface;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresentrerIMP;
import com.amihealth.amihealth.ModuloHTA.view.fragments.OrdenSelectorListener;
import com.amihealth.amihealth.ModuloHTA.view.fragments.lolFragment;
import com.amihealth.amihealth.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Response;

public class MedAntroMainActivity extends AppCompatActivity implements PesoViewInterface, PesoListaFragment.OnFragmentInteractionListener, AddPesoDialogFragment.AddPesoDialogListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Spinner spinnerAction;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private SectionsPagerAdapter fragmentHTAadapter;
    private ViewPager viewPager;
    private int tabPos = 0;
    private PesoPresenterInterface pesoPresenterInterface;

    private LayoutInflater layoutInflater;
    private SessionManager sessionManager;

    private OrdenSelectorListener ordenSelectorListener;
    private PesoViewInterface pesoViewInterface;
    private OrdenSelectorListener.OrdenGraficaListener GrafOrderListener;
    private PesoViewInterface pesoGrafViewInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_antro_main);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        pesoPresenterInterface = new PesoPresentrerIMP(getApplicationContext(),this);

        layoutInflater = LayoutInflater.from(getApplicationContext());

        showtoolbar(getResources().getString(R.string.title_pesoActivity),true);
        setTabLayout();
        setViewPager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoticeDialog();
            }
        });

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
        this.viewPager = (ViewPager) findViewById(R.id.container);

        fragmentManager = getSupportFragmentManager();
        fragmentHTAadapter = new SectionsPagerAdapter(getSupportFragmentManager(), spinnerAction.getSelectedItem().toString() );
        //fragmentList.addAll(fragmentHTAadapter.arr());
        viewPager.setAdapter(fragmentHTAadapter);
        tabLayout.setupWithViewPager(this.viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabPos = tab.getPosition();
                switch (tabPos){
                    case 0:
                        ordenSelectorListener.orderListener(spinnerAction.getSelectedItemPosition());
                        break;
                    case 1:
                        GrafOrderListener.orderGraficListener(spinnerAction.getSelectedItemPosition());
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                switch (tabPos){
                    case 0:
                        ordenSelectorListener.orderListener(spinnerAction.getSelectedItemPosition());
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
                        ordenSelectorListener.orderListener(i);
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
                        ordenSelectorListener.orderListener(adapterView.getSelectedItemPosition());
                        break;
                    case 1:
                        GrafOrderListener.orderGraficListener(adapterView.getSelectedItemPosition());
                        break;

                }

            }



        });
    }


    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new AddPesoDialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }
    public void showNoticeDialog(String id) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = EditPesoDialogFragment.getInstance(id);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragmentEdit");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_med_antro_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onErrorMSG(String error) {
        Snackbar.make(viewPager,error,Snackbar.LENGTH_LONG).show();
        /*
        Snackbar bar = Snackbar.make(viewPager, error, Snackbar.LENGTH_INDEFINITE);

        ViewGroup contentLay = (ViewGroup) bar.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
        ProgressBar item = new ProgressBar(getApplicationContext());
        contentLay.addView(item);
        bar.show();
        */
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof OrdenSelectorListener){
            ordenSelectorListener = (OrdenSelectorListener) fragment;
            //listaOrdenArray.add((OrdenSelectorListener) fragment);
        }
        if(fragment instanceof OrdenSelectorListener.OrdenGraficaListener){
            GrafOrderListener = (OrdenSelectorListener.OrdenGraficaListener) fragment;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(),"ONPause", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(),"ON_RESTAR", Toast.LENGTH_LONG).show();
        //setOrderListener((OrdenSelectorListener) getSupportFragmentManager().getFragments().get(tabLayout.getSelectedTabPosition()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        pesoPresenterInterface.RequestGetAll();
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
        // Toast.makeText(getApplicationContext(),"ONSTAR", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, double value) {
        Peso peso = new Peso();
        peso.setPeso(String.valueOf(value));
       pesoPresenterInterface.RequestInsertPeso(peso);


         /*RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Observable<Response<Peso>> observable =retrofitAdapter.getClientService(sessionManager.getUserLogin().get(SessionManager.AUTH)).insert_Peso(peso);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(pesoResponse -> {
                    if(pesoResponse.isSuccessful()){
                        onErrorMSG(pesoResponse.toString());
                    }
                });
                */

    }

    @Override
    public void onDialogPositiveEdit(String id, double value) {
        Peso peso = new Peso();
        peso.setId(id);
        peso.setPeso(String.valueOf(value));
        pesoPresenterInterface.RequestUpdatePeso(peso);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void OnGetAllResponse() {
        pesoViewInterface.OnGetAllResponse();
        pesoGrafViewInterface.OnGetAllResponse();
        pesoViewInterface.RespuestaActivity(2);
    }

    @Override
    public void OnInsertResponse() {

    }

    @Override
    public void OnDeleteResponse() {

    }

    @Override
    public void OnUpdateResponse() {

    }

    @Override
    public void OnErrorResponse(String error) {
        onErrorMSG(error);
    }

    @Override
    public void RespuestaActivity(int cargar) {

    }

    @Override
    public void onClickMenuItem_EDIT(String id) {
        showNoticeDialog(id);
    }

    @Override
    public void onClickMenuItem_DELETE(String id) {
        Snackbar snackbar = Snackbar.make(viewPager,getResources()
                .getString(R.string.msj_borrar_medida),3500)
                .setAction("Borrar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //presenterHTA.dropbyId(where);
                        Peso peso = new Peso();
                        Realm realm = Realm.getDefaultInstance();
                        peso.setId(realm.where(Peso.class).equalTo("id",id).findFirst().getId());
                        pesoPresenterInterface.RequestDeletePeso(peso);
                    }
                }).setActionTextColor(getResources().getColor(R.color.ms_white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            snackbar.getView().setElevation(16);
        }
        snackbar.show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String title[] = new String[]{"Medidas", "Graficas", "Estado"};
        public final String orderData;

        private FragmentManager frag;

        public SectionsPagerAdapter(FragmentManager fm, String order) {
            super(fm);
            this.orderData = order;
            this.frag = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            switch (position){

                case 0:
                    f = new PesoListaFragment();
                    ordenSelectorListener = (OrdenSelectorListener) f;
                    pesoViewInterface = (PesoViewInterface) f;
                    break;
                case 1:
                    f = new PesoGraficaFragment();
                    GrafOrderListener = (OrdenSelectorListener.OrdenGraficaListener) f;
                    pesoGrafViewInterface = (PesoViewInterface) f;
                    break;
                default:
                    f = new lolFragment();
                    break;
            }
            return f;
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
