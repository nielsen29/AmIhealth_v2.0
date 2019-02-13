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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.amihealth.amihealth.Adaptadores.AdapterCinturaGraficas;
import com.amihealth.amihealth.Adaptadores.AdapterGlucosaGraficas;

import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Glucosa;

import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.GlucosaPresenterIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.InterfaceGlucosaPresenter;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Utils.MedidasGlucosaList;
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
public class GlucosaGraficaFragment extends Fragment implements InterfaceGlucosaView, OrdenSelectorListener.OrdenGraficaListener {

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
    private ArrayList<MedidasGlucosaList> cinturaList;
    private AdapterGlucosaGraficas adapter;
    private SessionManager sessionManager;
    private Intent intent;
    private RealmResults<Glucosa> realmResults;
    private int ORDEN = 0;
    private InterfaceGlucosaView mListenerViewActivity;

    public GlucosaGraficaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ALAVERGAPERRO: ","_________________________>>>>>>>>>> OngetALL()");
        this.cinturaPresenter = new GlucosaPresenterIMP(this,getContext());
        realm = Realm.getDefaultInstance();
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        cinturaList = new ArrayList<>();
        intent = new Intent(getActivity(),NuevaMedidaHTA.class);
        assert recyclerView != null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("ALAVERGAPERRO: ","_________________________>>>>>>>>>> OnCREATEVIEW()");
        View view = inflater.inflate(R.layout.fragment_htalist, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.htaList_Recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_hta);
        linearLayout_progress = (LinearLayout) view.findViewById(R.id.progress_layout_hta_frag);
        linearLayout_error_conection = (LinearLayout) view.findViewById(R.id.error_layout_hta);
        linearLayout_error_empty = (LinearLayout) view.findViewById(R.id.errorEmpty_layout_hta);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_hta_frag);
        cinturaList.clear();
        cinturaPresenter.RequestGetAll();
        setupRecyclerView();
        setupSwipeRefresh();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ALAVERGAPERRO: ","_________________________>>>>>>>>>> OnRESUME()");
        try {
            cinturaList.clear();
            recyclerView.getAdapter().notifyDataSetChanged();
            OnGetAllResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                cinturaList.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                OnGetAllResponse();
                //presenterHta.getMedidas(0);
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void setupRecyclerView() {
        Log.d("ALAVERGAPERRO: ","_________________________>>>>>>>>>> setUP RECYCLER()");
        //adapterMedidasList = new AdapterMedidasList(getContext(),this,medidasHTALists);
        recyclerView.setAddStatesFromChildren(true);
        registerForContextMenu(recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterGlucosaGraficas(cinturaList,getContext());
        recyclerView.setAdapter(adapter);
        //presenterHta.getMedidas(0);


        //recyclerView.setAdapter(new AdapterMedidasList(getActivity(),this,medidasHTALists));

        //Toast.makeText(getContext(), realmResults.get(0).getFecha().toString(),Toast.LENGTH_LONG).show();

    }




    @Override
    public void OnGetAllResponse() {
        Log.d("ALAVERGAPERRO: ","_________________________>>>>>>>>>> OngetALL()");
        
        realm = Realm.getDefaultInstance();
        //Toast.makeText(getContext(), "RESPONDIOOOOOO",Toast.LENGTH_LONG).show();

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
        Log.d("ALAVERGAPERRO: ","_________________________>>>>>>>>>> ORDENARRRRR()");
        realm = Realm.getDefaultInstance();
        cinturaList.clear();
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
                    cinturaList.add(new MedidasGlucosaList(
                            "SEMANA",realm.where(Glucosa.class).equalTo("week",lol.get(i).getWeek()).findAll(),lol.get(i).getWeek()
                    ));
                    break;
                case 1:
                    cinturaList.add(new MedidasGlucosaList(
                            "MES",realm.where(Glucosa.class).equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
                case 2:
                    cinturaList.add(new MedidasGlucosaList(
                            "YEAR",realm.where(Glucosa.class).equalTo("year",lol.get(i).getYear()).findAll(),lol.get(i).getYear()
                    ));
                    break;
                default:
                    cinturaList.add(new MedidasGlucosaList(
                            "lol",realm.where(Glucosa.class).equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
            }
        }


        recyclerView.setAdapter(new AdapterGlucosaGraficas(cinturaList,getContext()));
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
        if (context instanceof GlucosaListaFragment.OnFragmentInteractionListener) {
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void orderGraficListener(int order) {
        ORDEN = order;
        ordenarARRAY(order);
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
