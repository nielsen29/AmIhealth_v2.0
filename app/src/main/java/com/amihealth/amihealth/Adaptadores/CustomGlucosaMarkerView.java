package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.Models.Glucosa;

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

public class CustomGlucosaMarkerView extends MarkerView{
    private Glucosa glucosa;
    private TextView glucosavalor;
    private TextView tipolectura;
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
    public CustomGlucosaMarkerView(final Context context, int layoutResource, final Glucosa glucosa) {
        super(context, layoutResource);
        glucosavalor = (TextView) findViewById(R.id.glucosa_valor);
        tipolectura = (TextView) findViewById(R.id.tipo_lectura);
        fecha = (TextView) findViewById(R.id.fecha_graf_descrip);
        hora = (TextView) findViewById(R.id.hora_graf_descrip);
        descrip = (TextView) findViewById(R.id.desrip_graf_descrip);
        color = (LinearLayout) findViewById(R.id.color_graf_des);
        card = (CardView) findViewById(R.id.card_graf_descrip);

        this.glucosa = glucosa;
        color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(context,"AKI click",Toast.LENGTH_LONG).show();
            }
        });

        glucosavalor.setText(String.valueOf(glucosa.getGlucosa())+"  "+"mg/dl");
        tipolectura.setText(String.valueOf(glucosa.getTipolectura()));

        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datoFecha = new Date();
        try {
            datoFecha = f.parse(glucosa.getDatetime());
        }catch (ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat fechaF = new SimpleDateFormat("MMM-dd");
        SimpleDateFormat horaF = new SimpleDateFormat("hh:mm:ss a");
        fecha.setText(fechaF.format(datoFecha));
        hora.setText(horaF.format(datoFecha));

        color.setBackgroundColor(Color.parseColor(glucosa.getRgb()));
        descrip.setText(glucosa.getDescrip());


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
