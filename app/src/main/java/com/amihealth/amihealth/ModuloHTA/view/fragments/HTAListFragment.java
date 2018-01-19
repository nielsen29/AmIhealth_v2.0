package com.amihealth.amihealth.ModuloHTA.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.Adaptadores.AdapterMedidasHTA;
import com.amihealth.amihealth.Adaptadores.AdapterMedidasList;
import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.Models.MedidasHTAList;
import com.amihealth.amihealth.ModuloHTA.NuevaMedidaHTA;
import com.amihealth.amihealth.ModuloHTA.adapter.AdapterMedHTA;
import com.amihealth.amihealth.ModuloHTA.presenter.ImplementPresenterHTA;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.ViewHTA;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.PresenterHta;
import com.amihealth.amihealth.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HTAListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.

 */
public class HTAListFragment extends Fragment implements OrdenSelectorListener, InterfaceHta{


    /********************************************************************
     *          DEF OBJETOS DE INTERFACES                               *
     ********************************************************************/

    private OnFragmentInteractionListener mListener;
    private OrdenSelectorListener ordenSelectorListener;
    private PresenterHta presenterHta;



    /********************************************************************
     *          DEF OBJETOS DE VISTA                                    *
     ********************************************************************/

    private RecyclerView        recyclerView;
    private SwipeRefreshLayout  swipeRefreshLayout;
    private String              where;
    private LinearLayout        linearLayout_progress;
    private ProgressBar         progressBar;
    private LinearLayout        linearLayout_error_conection;
    private LinearLayout        linearLayout_error_empty;
    private AlertDialog         dialog;



    /********************************************************************
     *          DEF VARIABLES DE ENTORNO                                *
     ********************************************************************/

    private SessionManager              sessionManager;
    private Realm                       realm;

    private RealmResults<MedidaHTA>     realmResults;
    private ArrayList<MedidasHTAList>   medidasHTALists;
    private AdapterMedidasList          adapter;

    private View v;
    private Intent intent;
    private int listo = 0;
    private int ORDEN;







    public HTAListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        presenterHta = new ImpPresenterHta(this,getContext());
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        medidasHTALists = new ArrayList<>();
        intent = new Intent(getActivity(),NuevaMedidaHTA.class);







       // presenterHTA = new ImplementPresenterHTA(this,getContext());
        /*layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/



        //this.intent = new Intent(getActivity(),NuevaMedidaHTA.class);

        assert recyclerView != null;
        //presenterHTA.getAll();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_htalist, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.htaList_Recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_hta);
        linearLayout_progress = (LinearLayout) view.findViewById(R.id.progress_layout_hta_frag);
        linearLayout_error_conection = (LinearLayout) view.findViewById(R.id.error_layout_hta);
        linearLayout_error_empty = (LinearLayout) view.findViewById(R.id.errorEmpty_layout_hta);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_hta_frag);
        showLoad();
        setupRecyclerView();
        setupSwipeRefresh();
        //presenterHta.getMedidas(0);


        return  view;
    }


    private void setupSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                medidasHTALists.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                presenterHta.getMedidas(0);
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void setupRecyclerView() {
        //adapterMedidasList = new AdapterMedidasList(getContext(),this,medidasHTALists);
        recyclerView.setAddStatesFromChildren(true);
        registerForContextMenu(recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterMedidasList(getActivity(),this,medidasHTALists);
        recyclerView.setAdapter(adapter);
        //presenterHta.getMedidas(0);


        //recyclerView.setAdapter(new AdapterMedidasList(getActivity(),this,medidasHTALists));

        //Toast.makeText(getContext(), realmResults.get(0).getFecha().toString(),Toast.LENGTH_LONG).show();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void orderListener(int order) {

        ORDEN = order;

        if (listo == 1){
            ordenarARRAY(order);
        }

    }



    public void ordenarARRAY(int order){
        medidasHTALists.clear();
        this.realm = Realm.getDefaultInstance();
        RealmResults<MedidaHTA> lol;
        switch (order){
            case 0:
               lol = realm.where(MedidaHTA.class).distinct("week").sort("week",Sort.DESCENDING);
                break;
            case 1:
                lol = realm.where(MedidaHTA.class).distinct("mes").sort("mes",Sort.DESCENDING);
                break;
            case 2:
                lol = realm.where(MedidaHTA.class).distinct("year").sort("year",Sort.DESCENDING);
                break;
            default:
                lol = realm.where(MedidaHTA.class).distinct("year").sort("year",Sort.DESCENDING);
                break;
        }

        lol = lol.sort("year",Sort.DESCENDING);

        for (int i = 0; i < lol.size() ; i++) {
            switch (order){
                case 0:
                    medidasHTALists.add(new MedidasHTAList(
                            "SEMANA",realm.where(MedidaHTA.class).equalTo("week",lol.get(i).getWeek()).findAll(),lol.get(i).getWeek()
                    ));
                    break;
                case 1:
                    medidasHTALists.add(new MedidasHTAList(
                            "MES",realm.where(MedidaHTA.class).equalTo("mes",lol.get(i).getMes()).findAll(),lol.get(i).getMes()
                    ));
                    break;
                case 2:
                    medidasHTALists.add(new MedidasHTAList(
                            "YEAR",realm.where(MedidaHTA.class).equalTo("year",lol.get(i).getYear()).findAll(),lol.get(i).getYear()
                    ));
                    break;
                default:
                    medidasHTALists.add(new MedidasHTAList(
                            "lol",realm.where(MedidaHTA.class).equalTo("mes",lol.get(i).getMes()).findAll(),lol.get(i).getMes()
                    ));
                    break;
            }
        }
        recyclerView.setAdapter(new AdapterMedidasList(getActivity(),this,medidasHTALists));
        recyclerView.getAdapter().notifyDataSetChanged();
        realm.close();
    }



    @Override
    public void acciones(String act) {
        this.where = act;
        this.intent.putExtra("ID",act);

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
                /*Snackbar snackbar = Snackbar.make(this.recyclerView,getContext()
                        .getString(R.string.msj_borrar_medida),1500)
                        .setAction("Borrar", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //presenterHTA.dropbyId(where);
                            }
                        }).setActionTextColor(getContext().getColor(R.color.icons));
                snackbar.getView().setBackgroundColor(getContext().getColor(R.color.ms_black_87_opacity));
                snackbar.show();
                break;*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
                builder.setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenterHta.borrar(where);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
// Set other dialog properties

// Create the AlertDialog

                dialog = builder.create();
                dialog.setIcon(ContextCompat.getDrawable(getActivity(),android.R.drawable.ic_dialog_alert));
                dialog.setTitle(getString(R.string.dialog_title_eliminar));
                dialog.setMessage(getString(R.string.dialog_msg_eliminar));
                dialog.show();

            default:
                break;
        }
    }

    @Override
    public void insertar(MedidaHTA medidaHTA) {

    }

    @Override
    public void showLoad() {
        linearLayout_progress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hiddenLoad() {
        linearLayout_progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void errorRetrofit(int code) {
        switch (code){
            case 44:
                linearLayout_error_empty.setVisibility(View.VISIBLE);
                break;
            default:
                linearLayout_error_empty.setVisibility(View.INVISIBLE);
                linearLayout_error_conection.setVisibility(View.VISIBLE);
                linearLayout_progress.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void mensaje(String mensaje) {
        Snackbar.make(getView(), mensaje, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    @Override
    public void getMedidas(int orden) {

    }

    @Override
    public void showAll(RealmResults<MedidaHTA> medidaHTAs) {
        if(medidaHTAs.isEmpty()){
            linearLayout_error_empty.setVisibility(View.VISIBLE);
        }else{
            linearLayout_error_empty.setVisibility(View.INVISIBLE);
        }
        hiddenLoad();
        this.realmResults = medidaHTAs;
        ordenarARRAY(ORDEN);
        listo = 1;
        swipeRefreshLayout.setRefreshing(false);



        // this.medidasHTALists = new MedidasHTAList(medidaHTAs).getArray(0);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterHta.getMedidas(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
