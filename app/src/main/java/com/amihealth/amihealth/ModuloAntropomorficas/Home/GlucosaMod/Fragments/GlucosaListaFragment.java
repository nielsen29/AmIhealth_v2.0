package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.amihealth.amihealth.Adaptadores.AdapterMedidasCintura;
import com.amihealth.amihealth.Adaptadores.AdapterMedidasGlucosa;

import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.GlucosaPresenterIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.InterfaceGlucosaPresenter;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Utils.MedidasGlucosaList;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.OnStaticErrorAlarm;
import com.amihealth.amihealth.ModuloHTA.NuevaMedidaHTA;
import com.amihealth.amihealth.ModuloHTA.view.fragments.OrdenSelectorListener;
import com.amihealth.amihealth.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlucosaListaFragment extends Fragment implements InterfaceGlucosaView, OrdenSelectorListener {

    private GlucosaListaFragment.OnFragmentInteractionListener mListener;
    private InterfaceGlucosaPresenter cinturaPresenter;




    /********************************************************************
     *          DEF OBJETOS DE VISTA                                    *
     ********************************************************************/

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String              where;
    private LinearLayout linearLayout_progress;
    private ProgressBar progressBar;
    private LinearLayout        linearLayout_error_conection;
    private LinearLayout        linearLayout_error_empty;
    private AlertDialog dialog;
    private Realm realm;
    private ArrayList<MedidasGlucosaList> medidasGlucosaLists;
    private AdapterMedidasGlucosa adapter;
    private SessionManager sessionManager;
    private Intent intent;
    private RealmResults<Glucosa> realmResults;
    private int ORDEN = 0;
    private InterfaceGlucosaView mListenerViewActivity;
    private OnStaticErrorAlarm mListenerErrorActivity;


    public GlucosaListaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cinturaPresenter = new GlucosaPresenterIMP(this, getContext());

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        medidasGlucosaLists = new ArrayList<>();
        intent = new Intent(getActivity(),NuevaMedidaHTA.class);

        assert recyclerView != null;
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
        cinturaPresenter.RequestGetAll();
        setupRecyclerView();
        setupSwipeRefresh();

        return view;
    }

    private void setupSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                medidasGlucosaLists.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                OnGetAllResponse();
                //presenterHta.getMedidas(0);
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
        adapter = new AdapterMedidasGlucosa(getActivity(),this, medidasGlucosaLists);
        recyclerView.setAdapter(adapter);
        //presenterHta.getMedidas(0);


        //recyclerView.setAdapter(new AdapterMedidasList(getActivity(),this,medidasHTALists));

        //Toast.makeText(getContext(), realmResults.get(0).getFecha().toString(),Toast.LENGTH_LONG).show();

    }




    @Override
    public void OnGetAllResponse() {
        mListenerErrorActivity.OnProgressOn();
        //Toast.makeText(getContext(), "RESPONDIOOOOOO",Toast.LENGTH_LONG).show();
        realm = Realm.getDefaultInstance();

        RealmResults<Glucosa> realmResults = realm.where(Glucosa.class).findAll();
        if(realmResults.isEmpty()){
            linearLayout_error_empty.setVisibility(View.VISIBLE);
        }else{
            linearLayout_error_empty.setVisibility(View.INVISIBLE);
            this.realmResults = realmResults;
            ordenarARRAY(ORDEN);
        }
        //hiddenLoad();

        //listo = 1;
        swipeRefreshLayout.setRefreshing(false);
        mListenerErrorActivity.OnProgressOff();

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
        mListenerViewActivity.OnErrorResponse(error);


    }

    @Override
    public void OnErrorMedida(String error) {

    }

    @Override
    public void RespuestaActivity(int cargar) {
        //ordenarARRAY(0);
    }

    @Override
    public void onClickMenuItem_EDIT(String id) {
        mListenerViewActivity.onClickMenuItem_EDIT(id);
    }

    @Override
    public void onClickMenuItem_DELETE(String id) {
        mListenerViewActivity.onClickMenuItem_DELETE(id);
    }


    public void ordenarARRAY(int order){
        realm = Realm.getDefaultInstance();
        medidasGlucosaLists.clear();
        RealmResults<Glucosa> lol;
        switch (order){
            case 0:
                lol = realm.where(Glucosa.class).distinct("week").sort("week", Sort.DESCENDING);
                break;
            case 1:
                lol = realm.where(Glucosa.class).distinct("month").sort("month",Sort.DESCENDING);
                break;
            case 2:
                lol = realm.where(Glucosa.class).distinct("year").sort("year",Sort.DESCENDING);
                break;
            default:
                lol = realm.where(Glucosa.class).distinct("year").sort("year",Sort.DESCENDING);
                break;
        }
        lol = lol.sort("year",Sort.DESCENDING);

        for (int i = 0; i < lol.size() ; i++) {
            switch (order){
                case 0:
                    medidasGlucosaLists.add(new MedidasGlucosaList(
                            "SEMANA",realm.where(Glucosa.class).equalTo("week",lol.get(i).getWeek()).findAll(),lol.get(i).getWeek()
                    ));
                    break;
                case 1:
                    medidasGlucosaLists.add(new MedidasGlucosaList(
                            "MES",realm.where(Glucosa.class).equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
                case 2:
                    medidasGlucosaLists.add(new MedidasGlucosaList(
                            "YEAR",realm.where(Glucosa.class).equalTo("year",lol.get(i).getYear()).findAll(),lol.get(i).getYear()
                    ));
                    break;
                default:
                    medidasGlucosaLists.add(new MedidasGlucosaList(
                            "lol",realm.where(Glucosa.class).equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
            }
        }
        recyclerView.setAdapter(new AdapterMedidasGlucosa(getActivity(),this, medidasGlucosaLists));
        recyclerView.getAdapter().notifyDataSetChanged();
        realm.close();
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
            mListener = (GlucosaListaFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof InterfaceGlucosaView) {
            mListenerViewActivity = (InterfaceGlucosaView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof OnStaticErrorAlarm) {
            mListenerErrorActivity = (OnStaticErrorAlarm) context;
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
        ordenarARRAY(order);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
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
        void onErrorMSG(String error);
    }







}
