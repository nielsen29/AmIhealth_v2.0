package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloHTA.MedidaHTADetailActivity;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.fragments.MedidaHTADetailFragment;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by GITCE on 01/11/18.
 */

public class AdapterPeso extends RealmRecyclerViewAdapter {

    private Context context;
    private ImpPresenterHta presenterHTA;

    private boolean mTwoPane;

    private int position;

    public AdapterPeso(@Nullable OrderedRealmCollection<Peso> data, boolean autoUpdate, Context context) {

        super(data, autoUpdate);


        this.context = context;

        this.mTwoPane = mTwoPane;

        //presenterHTA = new ImpPresenterHta(viewHTA,activity);
    }

    @Override
    public AdapterPeso.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view   = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_peso, parent, false);

        return new AdapterPeso.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder view = (ViewHolder) holder;
        Peso peso = (Peso) getData().get(position);
        view.peso.setText(String.valueOf(peso.getPeso()));
        view.view.setBackgroundColor(Color.parseColor(peso.getRgb()));
        view.imc.setText(String.valueOf(peso.getImc()));
        view.date.setText(peso.getDatetime());
        int color = Color.parseColor(peso.getRgb());

        view.imcColor.setBackgroundColor(color);

    }

    @Override
    public long getItemId(int index) {
        return super.getItemId(index);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Nullable
    @Override
    public RealmModel getItem(int index) {
        return super.getItem(index);
    }


    @Nullable
    @Override
    public OrderedRealmCollection getData() {
        return super.getData();
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void updateData(@Nullable OrderedRealmCollection data) {
        super.updateData(data);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView peso;
        private TextView imc;
        private TextView date;
        private LinearLayout imcColor;

        public ViewHolder(View v) {
            super(v);
            view = (View) v.findViewById(R.id.peso_color_item);
            peso = (TextView) v.findViewById(R.id.peso_txt_item);
            imc = (TextView) v.findViewById(R.id.peso_imc_item);
            date = (TextView) v.findViewById(R.id.peso_fecha_item);
            imcColor = (LinearLayout) v.findViewById(R.id.lin_imc_color);
            imcColor.getBackground().setAlpha(50);
        }


        @Override
        public String toString() {

            return super.toString();

        }

    }
}