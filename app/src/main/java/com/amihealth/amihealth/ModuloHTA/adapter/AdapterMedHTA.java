package com.amihealth.amihealth.ModuloHTA.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.MedidaHTADetailActivity;
import com.amihealth.amihealth.ModuloHTA.view.fragments.MedidaHTADetailFragment;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.PresenterHta;
import com.amihealth.amihealth.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by amihealthmel on 11/13/17.
 */

public class AdapterMedHTA extends RecyclerView.Adapter<AdapterMedHTA.ViewHolder> implements RealmChangeListener {

    private RealmResults<MedidaHTA> data;
    private PresenterHta presenterHTA ;

    public AdapterMedHTA(RealmResults<MedidaHTA> results, InterfaceHta interfaceHta, Context context) {
        //super();
        this.data = results;
        this.data.addChangeListener(this);
        this.presenterHTA = new ImpPresenterHta(interfaceHta,context);
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return null;
        View view   = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medidahta_list_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewHolder thisHolder = (ViewHolder) holder;



        thisHolder.mItem            = (MedidaHTA) data.get(position);



        thisHolder.medidahta          .setText(String.valueOf( data.get(position).getSYS())
                +" / "+ String.valueOf(data.get(position).getDIS()));

        thisHolder.pulso     .setText(String.valueOf(((MedidaHTA) data.get(position)).getPulso()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        thisHolder.fecha.setText(data.get(position).getDate());

        thisHolder.aSwitch.setChecked((thisHolder.mItem.getSync() == 1));




        thisHolder.mView            .setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                presenterHTA.acciones(thisHolder.mItem.getId());

                view.setOnCreateContextMenuListener(
                        new View.OnCreateContextMenuListener() {

                            @Override
                            public void onCreateContextMenu(ContextMenu contextMenu,
                                                            View view,
                                                            ContextMenu.ContextMenuInfo contextMenuInfo) {

                                AdapterView.AdapterContextMenuInfo menuInfo1
                                        = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;

                                String titulo
                                        = String.valueOf(thisHolder.mItem.getSYS()) +
                                        "/"+ String.valueOf(thisHolder.mItem.getDIS());

                                contextMenu.setHeaderTitle(titulo);
                            }

                        });
                return false;
            }

        });




        thisHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Context context     = v.getContext();

                Intent intent       = new Intent(context, MedidaHTADetailActivity.class);

                intent              .putExtra(MedidaHTADetailFragment.ARG_ITEM_ID,
                        String.valueOf(thisHolder.mItem.getId()));

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView medidahta;
        private TextView pulso;
        private TextView fecha;
        private LinearLayout color;
        private View mView;
        private CardView cardView;
        private MedidaHTA mItem;
        private LinearLayout menu;
        private ImageButton menuBoton;
        private Switch aSwitch;



        public ViewHolder(View view) {

            super(view);
            mView = view;
            //aSwitch = (Switch) mView.findViewById(R.id.syncSw);
            menuBoton = (ImageButton) mView.findViewById(R.id.menupopup1);
            menuBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(),view );
                    popup.inflate(R.menu.menu_medida);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(mItem.isLoaded()){
                                presenterHTA.acciones(mItem.getId());
                                presenterHTA.menuOP(item);
                            }
                            return  true;
                        }
                    });
                    popup.show();
                }
            });
            menu = (LinearLayout) mView.findViewById(R.id.menuItem);
            cardView = (CardView) mView.findViewById(R.id.medidasItemContent);
            medidahta = (TextView) mView.findViewById(R.id.txtMedidas);
            pulso = (TextView) mView.findViewById(R.id.txtMedidas_pls);
            fecha = (TextView) mView.findViewById(R.id.txtMedidas_fecha);
            color = (LinearLayout) mView.findViewById(R.id.medidasColor);

        }


        @Override
        public String toString() {

            return super.toString() + " '" + medidahta.getText() + "'";

        }

    }
}
