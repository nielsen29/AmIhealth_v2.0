package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.Utils.MedidasPesoList;
import com.amihealth.amihealth.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by GITCE on 01/13/18.
 */

public class AdapterPesoGraficas extends RecyclerView.Adapter<AdapterPesoGraficas.ViewGrafHolder> {

    private ArrayList<MedidasPesoList> resultados = new ArrayList<>();
    private Context context;
    private ArrayList<LineData> DatosGraf = new ArrayList<>();
    private ArrayList<ArrayList<String>> fechas = new ArrayList<>();

    public AdapterPesoGraficas(ArrayList<MedidasPesoList> resultados, Context context) {
        this.resultados = resultados;
        this.context = context;
        if(!this.resultados.isEmpty()){
            this.DatosGraf = getEntrys(resultados);
        }
    }

    @Override
    public AdapterPesoGraficas.ViewGrafHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.graficas_rec_hta,parent,false);
        return new AdapterPesoGraficas.ViewGrafHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterPesoGraficas.ViewGrafHolder holder, final int position) {


        String titulo = "";
        String label = "";


        switch(resultados.get(position).getNombre().toString()){

            case "SEMANA":
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date= new Date();
                try {
                    date = df.parse(resultados.get(position).getRealmResults().get(0).getDatetime());
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
                label = context.getString(R.string.semena) + ":"
                ;
                break;
            case "MES":
                DateFormat md = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date mDate= new Date();
                try {
                    mDate = md.parse(resultados.get(position).getRealmResults().get(0).getDatetime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cd = Calendar.getInstance();
                cd.setTime(mDate);
                //int weekofmonth = calendar.getWeekYear();
                SimpleDateFormat m = new SimpleDateFormat("MMMM, ''yy");


                titulo = m.format(cd.getTime());
                label = context.getString(R.string.mes) + ":";
                break;
            default:
                titulo = String.valueOf(resultados.get(position).getRealmResults().get(0).getYear());
                label = context.getString(R.string.year) + ":";



        }


        holder.titulo.setText(titulo);
        holder.label.setText(label);

        //final String titulo = resultados.get(position).toString();
        XAxis xAxis = holder.graf.getXAxis();




        holder.graf.setXAxisRenderer(new CustomXAxisRenderer(
                holder.graf.getViewPortHandler(),
                holder.graf.getXAxis(),
                holder.graf.getTransformer(YAxis.AxisDependency.LEFT),
                fechas.get(position)));


        XAxisRenderer aRender = holder.graf.getRendererXAxis();


        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        //xAxis.setXOffset(-100f);


        //xAxis.setDrawLabels(true);



        xAxis.setAxisMaximum(fechas.get(position).size());
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setGridColor(Color.GRAY);
        //xAxis.setAvoidFirstLastClipping(true);

        xAxis.setCenterAxisLabels(false);
        //xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        //xAxis.setAxisMaximum(this.DatosGraf.get(position).getXMax()+0.50f);
        xAxis.setSpaceMax(1f);
        xAxis.setSpaceMin(0.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //xAxis.setLabelRotationAngle(-90);
        xAxis.setTextColor(Color.GRAY);
        //xAxis.setYOffset(10);

        holder.graf.getAxisLeft().setTextColor(ContextCompat.getColor(context, R.color.grafIndicador_text));
        holder.graf.setDrawGridBackground(false);
        holder.graf.getAxisRight().setDrawLabels(false);
        holder.graf.getAxisRight().setDrawGridLines(false);

        YAxis yAxis = holder.graf.getAxis(YAxis.AxisDependency.LEFT);
        yAxis.setLabelCount(5,true);
        yAxis.setXOffset(-30f);
        yAxis.setYOffset(-5f);
        yAxis.setDrawZeroLine(true);






       /* holder.graf.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });
        */

        //holder.graf.setV

        holder.graf.setVisibleXRangeMaximum(4);
        holder.graf.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);

        holder.graf.setData(this.DatosGraf.get(position));







        holder.graf.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(context,resultados.get(position).getRealmResults().get((int) e.getX()).toString(),Toast.LENGTH_LONG).show();
                //holder.graf.setMarker(new CustomHTAMarkerView(context,R.layout.graf_descrip,resultados.get(position).getRealmResults().sort("Date", Sort.DESCENDING).get((int) e.getX())));
            }

            @Override
            public void onNothingSelected() {

            }

        });


        holder.graf.invalidate();

    }

    @Override
    public int getItemCount() {
        return  (null != resultados ? resultados.size(): 0);
    }


    /*******
     *  obtener los dataset para la grafica
     */
    public ArrayList<LineData> getEntrys(ArrayList<MedidasPesoList> resultados){

        ArrayList<LineData> dataArr = new ArrayList<>();



        for (int i = 0; i < resultados.size() ; i++) {


            ArrayList<Entry> SYS = new ArrayList<>();
            ArrayList<Entry> DIS = new ArrayList<>();

            ArrayList<String> fech = new ArrayList<>();
            ArrayList<BarEntry> fechaBar = new ArrayList<>();
            RealmResults<Peso> realmResult;
            realmResult = resultados.get(i).getRealmResults().sort("datetime", Sort.DESCENDING);
            for (int j = 0; j <realmResult.size() ; j++) {
                Date datoFecha = new Date();
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    datoFecha = f.parse(realmResult.get(j).getDatetime());
                }catch (ParseException e){
                    e.printStackTrace();
                }


                SYS.add(new Entry((float) j,(float) realmResult.get(j).getPeso()));
                DIS.add(new Entry((float) j, (float) realmResult.get(j).getImc()));


                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd\nHH:mma");
                fech.add(sdf.format(datoFecha));
            }


            LineDataSet lineSys = new LineDataSet(SYS,"Peso");
            lineSys.setCircleColor(ContextCompat.getColor(context,R.color.sysGradient));
            lineSys.setColor(ContextCompat.getColor(context,R.color.sysGradient));
            //lineSys.setFillColor(Color.parseColor("#c2bbff"));
            lineSys.setFillDrawable(ContextCompat.getDrawable(context,R.drawable.sys_gradient));
            lineSys.setFillAlpha(200);
            lineSys.setLineWidth(2f);
            lineSys.setCircleRadius(6f);
            lineSys.setDrawCircleHole(true);
            lineSys.setCircleHoleRadius(4f);
            lineSys.setValueTextSize(12f);
            lineSys.setDrawFilled(true);
            lineSys.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineSys.setMode(LineDataSet.Mode.LINEAR);

            lineSys.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    DecimalFormat mformat = new DecimalFormat("###,###.##");
                    String x = mformat.format(value).toString();
                    return x;
                }
            });


            LineDataSet lineDis = new LineDataSet(DIS,"IMC");
            //lineDis.setFillAlpha(200);
            lineDis.setCircleColor(ContextCompat.getColor(context,R.color.disgradient));
            //lineDis.setFillColor(Color.parseColor("#9eb4fe"));
            //lineDis.setColor(Color.parseColor("#ffffff"));
            //lineDis.setLineWidth(2f);
            lineDis.setFillDrawable(ContextCompat.getDrawable(context,R.drawable.dis_gradient));

            lineDis.setCircleRadius(6f);
            lineDis.setDrawCircleHole(true);
            lineDis.setCircleHoleRadius(3f);
            lineDis.setValueTextSize(12f);
            lineDis.setDrawFilled(true);
            lineDis.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDis.setMode(LineDataSet.Mode.LINEAR);
            lineDis.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    DecimalFormat mformat = new DecimalFormat("###,###.##");
                    String x = mformat.format(value).toString();
                    return x;
                }
            });





            BarDataSet barfechas = new BarDataSet(fechaBar,"fecha");
            barfechas.setValueTextSize(10f);
            barfechas.setAxisDependency(YAxis.AxisDependency.LEFT);
            barfechas.setDrawValues(false);
            barfechas.setColor(Color.TRANSPARENT);



            BarData bar = new BarData(barfechas);
            bar.setBarWidth(1f);
            bar.setDrawValues(false);



            LineData a = new LineData();
            fechas.add(fech);

            a.addDataSet(lineSys);
            a.addDataSet(lineDis);


            CombinedData combinados = new CombinedData();
            combinados.setData(bar);
            combinados.setData(a);
            dataArr.add(a);

        }
        return dataArr;
    }

    public class ViewGrafHolder extends RecyclerView.ViewHolder implements OnChartValueSelectedListener {

        private TextView titulo;


        private LineChart graf;
        private ArrayList<Entry> SYS;
        private ArrayList<Entry> DIS;
        private ArrayList<Entry> PLS;

        private LineDataSet lineDataSet;
        private LineDataSet lineDataSet1;
        private LineDataSet lineDataSet2;

        private ArrayList<String> fechas;
        private Legend legend;

        private ArrayList<ILineDataSet> dataSets;
        private LineData data;

        private ArrayList<Peso> listadoMedidias;
        private TextView label;


        public ViewGrafHolder(View v) {
            super(v);
            graf            = (LineChart) v.findViewById(R.id.graphMPchar);
            titulo = (TextView) v.findViewById(R.id.text_graficas_orden);
            label = (TextView) v.findViewById(R.id.medidas_label);



            graf.setDrawGridBackground(false);

            graf.setScrollContainer(true);
            //graf.setBackgroundColor(Color.parseColor("#d12356"));
            graf.setPinchZoom(false);
            //graf.setScaleMinima(1,0);
            graf.setScaleEnabled(false);
            graf.setNoDataTextColor(Color.GRAY);
            graf.setBackgroundColor(Color.TRANSPARENT);
            graf.setClipValuesToContent(false);
            graf.setDragEnabled(true);

            graf.setDragOffsetX(100);
            //graf.setDragOffsetY(16);
            graf.setVisibleXRangeMaximum(3);
            graf.setVisibleXRangeMinimum(1);
            graf.setDrawBorders(false);
            //graf.getXAxis().setYOffset(16);

            graf.moveViewToX(-2);












            legend = graf.getLegend();
            legend.setWordWrapEnabled(false);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);

            legend.setXOffset(0f);
            legend.setTextSize(14f);

            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
            legend.setForm(Legend.LegendForm.CIRCLE);
            //legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {

        }

        @Override
        public void onNothingSelected() {

        }
    }
}
