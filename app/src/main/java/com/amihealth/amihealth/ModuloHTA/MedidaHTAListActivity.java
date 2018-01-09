package com.amihealth.amihealth.ModuloHTA;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amihealth.amihealth.Adaptadores.AdapterMedidasHTA;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Contract.ContractHTA;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.adapter.AdapterMedHTA;
import com.amihealth.amihealth.ModuloHTA.presenter.ImplementPresenterHTA;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.ViewHTA;
import com.amihealth.amihealth.R;

import com.amihealth.amihealth.ModuloHTA.dummy.DummyContent;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * An activity representing a list of MedidasHTA. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MedidaHTADetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MedidaHTAListActivity extends AppCompatActivity implements ViewHTA {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private SessionManager sessionManager;
    private Realm realm;
    private RealmResults<MedidaHTA> realmResults;
    private RecyclerView recyclerView;
    private int where;
    private View v;
    private Intent intent;

    private AdapterMedHTA adapterMedidas;

    private ImplementPresenterHTA presenterHTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidahta_list);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        presenterHTA = new ImplementPresenterHTA(this,this);
        this.realm = Realm.getDefaultInstance();

        this.intent = new Intent(this,NuevaMedidaHTA.class);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goNuevaMedidad();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.medidahta_list);
        assert recyclerView != null;
        presenterHTA.getAll();

        if (findViewById(R.id.medidahta_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        setupRecyclerView();
        recyclerView.setAddStatesFromChildren(true);

        registerForContextMenu(recyclerView);

    }


    private void goNuevaMedidad(){

        this.intent.putExtra("ACTION",0);
        startActivity(intent);
    }

    private void setupRecyclerView() {

        //adapterMedidas = new AdapterMedHTA(realmResults,this,get)

        recyclerView.setAdapter(new AdapterMedidasHTA(realmResults.sort("fecha", Sort.DESCENDING),true,(InterfaceHta) this,this));

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {



    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void insert(MedidaHTA medidaHTA) {

    }

    @Override
    public void showbyId(MedidaHTA medidaHTA) {

    }

    @Override
    public void showall(RealmResults<MedidaHTA> realmResults) {
        this.realmResults = realmResults;
    }

    @Override
    public void edit(MedidaHTA medidaHTA) {

    }

    @Override
    public void drop(MedidaHTA medidaHTA) {

    }

    @Override
    public void mensaje(String mensaje) {

    }

    @Override
    public void acciones(int act) {
        this.where = act;
        this.intent.putExtra("ID",act);
        if(act == 1){

        }
    }

    @Override
    public void error(String error) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void menuOP(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_medida:
                this.intent.putExtra("ACTION",1);
                startActivity(intent);
                break;
            case R.id.drop_medida:
                Snackbar snackbar = Snackbar.make(this.recyclerView,getApplicationContext()
                        .getString(R.string.msj_borrar_medida),1500)
                        .setAction("Borrar", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                presenterHTA.dropbyId(where);
                            }
                        }).setActionTextColor(getApplicationContext().getColor(R.color.icons));
                snackbar.getView().setBackgroundColor(getApplicationContext().getColor(R.color.ms_black_87_opacity));
                snackbar.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void PopUpMenu(ImageButton boton) {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = getApplicationContext();

                PopupMenu popupMenu = new PopupMenu(context,view);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        presenterHTA.menuOP(item);
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.menu_medida);
                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mSignOut) {
            sessionManager.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
