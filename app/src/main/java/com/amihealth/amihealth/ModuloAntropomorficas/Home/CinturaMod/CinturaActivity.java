package com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.MedAntroMainActivity;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.PesoViewInterface;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.PesoGraficaFragment;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.PesoListaFragment;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresenterInterface;
import com.amihealth.amihealth.ModuloHTA.view.fragments.OrdenSelectorListener;
import com.amihealth.amihealth.ModuloHTA.view.fragments.lolFragment;
import com.amihealth.amihealth.R;

public class CinturaActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


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

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cintura);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cintura, menu);
        return true;
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
            return 3;
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
