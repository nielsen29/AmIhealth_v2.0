package com.amihealth.amihealth.ModuloHTA.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amihealth.amihealth.Adaptadores.AdapterMedidasHTAgrafica;
import com.amihealth.amihealth.Adaptadores.AdapterMedidasList;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.Models.MedidasHTAList;
import com.amihealth.amihealth.ModuloHTA.NuevaMedidaHTA;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.PresenterHta;
import com.amihealth.amihealth.R;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HTAGraficasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HTAGraficasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HTAGraficasFragment extends Fragment implements OrdenSelectorListener.OrdenGraficaListener, InterfaceHta {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Realm realm;
    private ArrayList<MedidasHTAList> medidasHTALists;
    private RecyclerView recyclerView;
    private int listo;
    private int ORDEN;

    private AdapterMedidasHTAgrafica adapter;
    private PresenterHta presenterHta;
    private SessionManager sessionManager;
    private RealmResults<MedidaHTA> realmResults;

    public HTAGraficasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HTAGraficasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HTAGraficasFragment newInstance(String param1, String param2) {
        HTAGraficasFragment fragment = new HTAGraficasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        realm = Realm.getDefaultInstance();
        presenterHta = new ImpPresenterHta(this,getContext());
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        medidasHTALists = new ArrayList<>();
        presenterHta.getMedidas(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_htagraficas, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.medidas_Recycler_grafica);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        //adapterMedidasList = new AdapterMedidasList(getContext(),this,medidasHTALists);
        recyclerView.setAddStatesFromChildren(true);
        registerForContextMenu(recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterMedidasHTAgrafica(medidasHTALists,getContext());
        recyclerView.setAdapter(adapter);
        //presenterHta.getMedidas(0);


        //recyclerView.setAdapter(new AdapterMedidasList(getActivity(),this,medidasHTALists));

        //Toast.makeText(getContext(), realmResults.get(0).getFecha().toString(),Toast.LENGTH_LONG).show();

    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentGraficaInteraction(uri);
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
    public void orderGraficListener(int order) {
        ORDEN = order;

        if (listo == 1){
            ordenarARRAY(order);
        }
        //Toast.makeText(getActivity(),String.valueOf(order).toString(),Toast.LENGTH_LONG).show();
    }





    public void ordenarARRAY(int order){
        medidasHTALists.clear();
        this.realm = Realm.getDefaultInstance();
        RealmResults<MedidaHTA> lol;

        switch (order){
            case 0:
                lol = realm.where(MedidaHTA.class).distinct("week").sort("week", Sort.DESCENDING);
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
        recyclerView.setAdapter(new AdapterMedidasHTAgrafica(medidasHTALists,getContext()));
        recyclerView.getAdapter().notifyDataSetChanged();
        realm.close();
    }





    @Override
    public void acciones(String act) {


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void menuOP(MenuItem item) {

    }

    @Override
    public void insertar(MedidaHTA medidaHTA) {

    }

    @Override
    public void showLoad() {
        /*
        linearLayout_progress.setVisibility(View.VISIBLE);
        */

    }

    @Override
    public void hiddenLoad() {
        /*
        linearLayout_progress.setVisibility(View.INVISIBLE);
        */
    }

    @Override
    public void errorRetrofit(int code) {
       /* switch (code){
            case 44:
                linearLayout_error_empty.setVisibility(View.VISIBLE);
                break;
            default:
                linearLayout_error_empty.setVisibility(View.INVISIBLE);
                linearLayout_error_conection.setVisibility(View.VISIBLE);
                linearLayout_progress.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
        }
        */
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
        /*
        if(medidaHTAs.isEmpty()){

            linearLayout_error_empty.setVisibility(View.VISIBLE);
        }else{
            linearLayout_error_empty.setVisibility(View.INVISIBLE);
        }
        hiddenLoad();*/
        this.realmResults = medidaHTAs;
        ordenarARRAY(ORDEN);
        listo = 1;





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
    public interface OnFragmentInteractionListener{
        // TODO: Update argument type and name
        void onFragmentGraficaInteraction(Uri uri);
    }
}
