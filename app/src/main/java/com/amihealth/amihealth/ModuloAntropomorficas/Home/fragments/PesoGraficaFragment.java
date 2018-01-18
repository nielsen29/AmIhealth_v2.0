package com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments;

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
import android.widget.TextView;

import com.amihealth.amihealth.Adaptadores.AdapterMedidasPeso;
import com.amihealth.amihealth.Adaptadores.AdapterPeso;
import com.amihealth.amihealth.Adaptadores.AdapterPesoGraficas;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.PesoViewInterface;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.Utils.MedidasPesoList;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresenterInterface;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.presenter.PesoPresentrerIMP;
import com.amihealth.amihealth.ModuloHTA.NuevaMedidaHTA;
import com.amihealth.amihealth.ModuloHTA.view.fragments.OrdenSelectorListener;
import com.amihealth.amihealth.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PesoGraficaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PesoGraficaFragment extends Fragment implements PesoViewInterface, OrdenSelectorListener.OrdenGraficaListener {

    private PesoListaFragment.OnFragmentInteractionListener mListener;
    private PesoPresenterInterface pesoPresenterInterface;




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
    private AlertDialog dialog;
    private Realm realm;
    private ArrayList<MedidasPesoList> medidasPesoList;
    private AdapterPesoGraficas adapter;
    private SessionManager sessionManager;
    private Intent intent;
    private RealmResults<Peso> realmResults;
    private int ORDEN = 0;
    private PesoViewInterface mListenerViewActivity;

    public PesoGraficaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pesoPresenterInterface = new PesoPresentrerIMP(getContext(),this);
        realm = Realm.getDefaultInstance();
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        medidasPesoList = new ArrayList<>();
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
        pesoPresenterInterface.RequestGetAll();
        setupRecyclerView();
        setupSwipeRefresh();

        return view;
    }

    private void setupSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                medidasPesoList.clear();
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
        adapter = new AdapterPesoGraficas(medidasPesoList,getContext());
        recyclerView.setAdapter(adapter);
        //presenterHta.getMedidas(0);


        //recyclerView.setAdapter(new AdapterMedidasList(getActivity(),this,medidasHTALists));

        //Toast.makeText(getContext(), realmResults.get(0).getFecha().toString(),Toast.LENGTH_LONG).show();

    }




    @Override
    public void OnGetAllResponse() {
        realm = Realm.getDefaultInstance();
        //Toast.makeText(getContext(), "RESPONDIOOOOOO",Toast.LENGTH_LONG).show();

        RealmResults<Peso> realmResults = realm.where(Peso.class).findAll();
        if(realmResults.isEmpty()){
            linearLayout_error_empty.setVisibility(View.VISIBLE);
        }else{
            linearLayout_error_empty.setVisibility(View.INVISIBLE);
        }
        //hiddenLoad();
        this.realmResults = realmResults;
        ordenarARRAY(ORDEN);
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
        medidasPesoList.clear();
        RealmResults<Peso> lol;
        switch (order){
            case 0:
                lol = realm.where(Peso.class).distinct("week").sort("week", Sort.DESCENDING);
                break;
            case 1:
                lol = realm.where(Peso.class).distinct("month").sort("month",Sort.DESCENDING);
                break;
            case 2:
                lol = realm.where(Peso.class).distinct("year").sort("year",Sort.DESCENDING);
                break;
            default:
                lol = realm.where(Peso.class).distinct("week").sort("week",Sort.DESCENDING);
                break;
        }

        for (int i = 0; i < lol.size() ; i++) {
            switch (order){
                case 0:
                    medidasPesoList.add(new MedidasPesoList(
                            "SEMANA",realm.where(Peso.class).equalTo("week",lol.get(i).getWeek()).findAll(),lol.get(i).getWeek()
                    ));
                    break;
                case 1:
                    medidasPesoList.add(new MedidasPesoList(
                            "MES",realm.where(Peso.class).equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
                case 2:
                    medidasPesoList.add(new MedidasPesoList(
                            "YEAR",realm.where(Peso.class).equalTo("year",lol.get(i).getYear()).findAll(),lol.get(i).getYear()
                    ));
                    break;
                default:
                    medidasPesoList.add(new MedidasPesoList(
                            "lol",realm.where(Peso.class).equalTo("month",lol.get(i).getMonth()).findAll(),lol.get(i).getMonth()
                    ));
                    break;
            }
        }
        recyclerView.setAdapter(new AdapterPesoGraficas(medidasPesoList,getContext()));
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
        if (context instanceof PesoListaFragment.OnFragmentInteractionListener) {
            mListener = (PesoListaFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof PesoViewInterface) {
            mListenerViewActivity = (PesoViewInterface) context;
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
