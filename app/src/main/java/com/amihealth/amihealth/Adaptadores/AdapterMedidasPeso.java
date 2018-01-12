package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.Utils.MedidasPesoList;
import com.amihealth.amihealth.R;

import java.util.ArrayList;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by GITCE on 01/11/18.
 */

public class AdapterMedidasPeso extends RecyclerView.Adapter<AdapterMedidasPeso.ViewListHolder>{

    private Context activity;
    //private InterfaceHta interfaceHta;
    private ArrayList<MedidasPesoList> listItem;

    public AdapterMedidasPeso(Context activity,ArrayList<MedidasPesoList> listItem){
            super();
            this.activity=activity;
            this.listItem=listItem;
            }

    @Override
    public ViewListHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_hta,parent,false);

            return new ViewListHolder(v);
            }

    @Override
    public void onBindViewHolder(ViewListHolder holder,int position){
        final String titulo=listItem.get(position).toString();
        final RealmResults<Peso> results=listItem.get(position).getRealmResults();
        //AdapterMedidasHTA adapterMedidasHTA=new AdapterMedidasHTA(results.sort("Date", Sort.DESCENDING),true,activity);
        AdapterPeso adapterPeso = new AdapterPeso(results.sort("datetime",Sort.DESCENDING), true, activity );
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

    public ArrayList<MedidasPesoList> getListItem(){
            return listItem;
            }

    public void addItem(MedidasPesoList medidasHTAList){
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