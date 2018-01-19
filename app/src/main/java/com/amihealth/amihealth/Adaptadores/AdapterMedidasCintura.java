package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Fragments.InterfaceCinturaView;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.Utils.MedidasCinturaList;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.Utils.MedidasPesoList;
import com.amihealth.amihealth.R;

import java.util.ArrayList;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by GITCE on 01/19/18.
 */

public class AdapterMedidasCintura extends RecyclerView.Adapter<AdapterMedidasCintura.ViewListHolder>{

    private Context activity;
    //private InterfaceHta interfaceHta;
    private ArrayList<MedidasCinturaList> listItem;
    private InterfaceCinturaView viewInterface;

    public AdapterMedidasCintura(Context activity, InterfaceCinturaView viewInterface, ArrayList<MedidasCinturaList> listItem){
        super();
        this.activity=activity;
        this.listItem=listItem;
        this.viewInterface = viewInterface;
    }

    @Override
    public ViewListHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_hta,parent,false);

        return new ViewListHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewListHolder holder, int position){
        final String titulo=listItem.get(position).toString();
        final RealmResults<Cintura> results=listItem.get(position).getRealmResults();
        //AdapterMedidasHTA adapterMedidasHTA=new AdapterMedidasHTA(results.sort("Date", Sort.DESCENDING),true,activity);
        AdapterCintura adapterPeso = new AdapterCintura(this.viewInterface,results.sort("datetime", Sort.DESCENDING), true, activity );
        holder.textView.setText(titulo);
        holder.recyclerView.setHasFixedSize(false);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        holder.recyclerView.setAdapter(adapterPeso);
        holder.recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return(null!=listItem?listItem.size():0);
    }

    public ArrayList<MedidasCinturaList> getListItem(){
        return listItem;
    }

    public void addItem(MedidasCinturaList medidasHTAList){
        this.listItem.add(medidasHTAList);
        this.notifyDataSetChanged();
    }




    public class ViewListHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private RecyclerView recyclerView;
        private TextView textView;


        public ViewListHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_order_list);
            cardView = (CardView) itemView.findViewById(R.id.card_list_c);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.htaRecycler_medidas);
        }
    }
}
