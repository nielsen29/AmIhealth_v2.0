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
import android.widget.Toast;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.MedidaHTADetailActivity;
import com.amihealth.amihealth.ModuloHTA.view.fragments.MedidaHTADetailFragment;
//import com.amihealth.amihealth.ModuloHTA.presenter.ImplementPresenterHTA;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by amihealthmel on 11/14/17.
 */

public class AdapterMedidasHTA extends RealmRecyclerViewAdapter{

    private Context activity;
    private ImpPresenterHta presenterHTA;

    private boolean mTwoPane;

    private int position;

    public AdapterMedidasHTA(@Nullable OrderedRealmCollection<MedidaHTA> data, boolean autoUpdate, InterfaceHta viewHTA, Context activity) {

        super(data, autoUpdate);


        this.activity = activity;

        this.mTwoPane = mTwoPane;

        presenterHTA = new ImpPresenterHta(viewHTA,activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view   = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.medidahta_list_content, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder thisHolder = (ViewHolder) holder;



        thisHolder.mItem            = (MedidaHTA) getData().get(position);



        thisHolder.medidahta          .setText(String.valueOf(((MedidaHTA) getData().get(position)).getSYS())
                +" / "+ String.valueOf(((MedidaHTA) getData().get(position)).getDIS()));

        thisHolder.pulso     .setText(String.valueOf(((MedidaHTA) getData().get(position)).getPulso()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        thisHolder.fecha.setText(((MedidaHTA)getData().get(position)).getDate());

        //thisHolder.aSwitch.setChecked((thisHolder.mItem.getSync() == 1));

        thisHolder.color.setBackgroundColor(Color.parseColor(((MedidaHTA)getData().get(position)).getRgb().substring(0)));







        thisHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(v.getContext(),thisHolder.mItem.getId(),Toast.LENGTH_LONG).show();


                    Context context     = v.getContext();

                    Intent intent       = new Intent(context, MedidaHTADetailActivity.class);

                    intent              .putExtra(MedidaHTADetailFragment.ARG_ITEM_ID,
                                        String.valueOf(thisHolder.mItem.getId()));

                    context.startActivity(intent);

            }
        });
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
