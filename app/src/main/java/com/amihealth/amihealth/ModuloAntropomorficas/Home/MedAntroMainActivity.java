package com.amihealth.amihealth.ModuloAntropomorficas.Home;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.PesoListaFragment;
import com.amihealth.amihealth.R;

import java.util.ArrayList;
import java.util.List;

public class MedAntroMainActivity extends AppCompatActivity implements PesoListaFragment.OnFragmentInteractionListener {

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

    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_antro_main);

        layoutInflater = LayoutInflater.from(getApplicationContext());

        showtoolbar(getResources().getString(R.string.title_pesoActivity),true);
        setTabLayout();
        setViewPager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                        //orderListener.orderListener(spinnerAction.getSelectedItemPosition());
                        break;
                    case 1:
                        //GrafOrderListener.orderGraficListener(spinnerAction.getSelectedItemPosition());
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
                        //orderListener.orderListener(spinnerAction.getSelectedItemPosition());
                        break;
                    case 1:
                        //GrafOrderListener.orderGraficListener(spinnerAction.getSelectedItemPosition());
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
                        //orderListener.orderListener(i);
                        break;
                    case 1:
                        //GrafOrderListener.orderGraficListener(i);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                switch (tabPos){
                    case 0:
                        //orderListener.orderListener(adapterView.getSelectedItemPosition());
                        break;
                    case 1:
                        //GrafOrderListener.orderGraficListener(adapterView.getSelectedItemPosition());
                        break;

                }

            }



        });
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
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
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
                    break;
                case 1:
                    f = new PesoListaFragment();
                    break;
                default:
                    f = new PesoListaFragment();
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
