package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.Models.MedidasHTAList;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by amihealthmel on 12/18/17.
 */

public class AdapterMedidasList extends RecyclerView.Adapter<AdapterMedidasList.ViewListHolder> {

    private Context activity;
    private InterfaceHta interfaceHta;
    private ArrayList<MedidasHTAList> listItem;

    public AdapterMedidasList(Context activity, InterfaceHta interfaceHta, ArrayList<MedidasHTAList> listItem) {
        this.activity = activity;
        this.interfaceHta = interfaceHta;
        this.listItem = listItem;
    }

    @Override
    public ViewListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_hta,parent,false);

        return new ViewListHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewListHolder holder, int position) {
        String titulo = "";
        String label = "";


        switch(listItem.get(position).getNombre().toString()){

            case "SEMANA":
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date= new Date();
                try {
                    date = df.parse(listItem.get(position).getRealmResults().get(0).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                //int weekofmonth = calendar.getWeekYear();
                SimpleDateFormat f = new SimpleDateFormat("E d, MMM, ''yy");

                Calendar diaSemana = Calendar.getInstance();
                diaSemana.setTime(date);
                diaSemana.set(Calendar.DAY_OF_WEEK,1);
                diaSemana.clear(Calendar.HOUR);
                diaSemana.clear(Calendar.MINUTE);
                diaSemana.clear(Calendar.SECOND);
                diaSemana.clear(Calendar.MILLISECOND);
                diaSemana.clear(Calendar.HOUR_OF_DAY);

                Calendar endSemena = Calendar.getInstance();
                endSemena.setTime(diaSemana.getTime());
                endSemena.set(Calendar.DATE,diaSemana.get(Calendar.DAY_OF_MONTH)+6);

                titulo = f.format(diaSemana.getTime())
                +" al "+ f.format(endSemena.getTime());
                label = activity.getString(R.string.semena) + ":"
                ;
                break;
            case "MES":
               DateFormat md = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date mDate= new Date();
                try {
                    mDate = md.parse(listItem.get(position).getRealmResults().get(0).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cd = Calendar.getInstance();
                cd.setTime(mDate);
                //int weekofmonth = calendar.getWeekYear();
                SimpleDateFormat m = new SimpleDateFormat("MMMM, ''yy");


                titulo = m.format(cd.getTime());
                label = activity.getString(R.string.mes) + ":";
                break;
            default:
                titulo = String.valueOf(listItem.get(position).getRealmResults().get(0).getYear());
                label = activity.getString(R.string.year) + ":";



        }

        final RealmResults<MedidaHTA> results = listItem.get(position).getRealmResults();
        AdapterMedidasHTA adapterMedidasHTA = new AdapterMedidasHTA(results.sort("Date", Sort.DESCENDING),true,interfaceHta,activity);
        holder.textView.setText(titulo);
        holder.label.setText(label);
        holder.recyclerView.setHasFixedSize(false);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        holder.recyclerView.setAdapter(adapterMedidasHTA);
    }

    @Override
    public int getItemCount() {
        return (null != listItem ? listItem.size(): 0);
    }

    public ArrayList<MedidasHTAList> getListItem() {
        return listItem;
    }

    public void addItem(MedidasHTAList medidasHTAList) {
        this.listItem.add(medidasHTAList);
        this.notifyDataSetChanged();
    }



    public class ViewListHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private RecyclerView recyclerView;
        private TextView textView;
        private TextView label;


        public ViewListHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_order_list);
            cardView = (CardView) itemView.findViewById(R.id.card_list_c);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.htaRecycler_medidas);
            label = (TextView) itemView.findViewById(R.id.medidas_label);
        }
    }


}
