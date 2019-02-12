package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amihealthmel on 12/27/17.
 */

public class CustomPesoMarkerView extends MarkerView{
    private Peso peso;
    private TextView pesovalor;
    private TextView imc;
    private TextView fecha;
    private TextView hora;
    private TextView descrip;
    private LinearLayout color;
    private CardView card;


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomPesoMarkerView(final Context context, int layoutResource, final Peso peso) {
        super(context, layoutResource);
        pesovalor = (TextView) findViewById(R.id.peso_valor);
        imc = (TextView) findViewById(R.id.imc);
        fecha = (TextView) findViewById(R.id.fecha_graf_descrip);
        hora = (TextView) findViewById(R.id.hora_graf_descrip);
        descrip = (TextView) findViewById(R.id.desrip_graf_descrip);
        color = (LinearLayout) findViewById(R.id.color_graf_des);
        card = (CardView) findViewById(R.id.card_graf_descrip);

        this.peso = peso;
        color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(context,"AKI click",Toast.LENGTH_LONG).show();
            }
        });

        pesovalor.setText(String.valueOf(peso.getPeso())+"  "+"Kg");
        imc.setText(String.valueOf(peso.getImc()));

        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datoFecha = new Date();
        try {
            datoFecha = f.parse(peso.getDatetime());
        }catch (ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat fechaF = new SimpleDateFormat("MMM-dd");
        SimpleDateFormat horaF = new SimpleDateFormat("hh:mm:ss a");
        fecha.setText(fechaF.format(datoFecha));
        hora.setText(horaF.format(datoFecha));

        color.setBackgroundColor(Color.parseColor(peso.getRgb()));
        descrip.setText(peso.getDescrip());


    }



    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2),-getHeight());
    }


}
