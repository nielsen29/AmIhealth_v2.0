package com.amihealth.amihealth.ModuloHTA.view.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.Adaptadores.CustomHTAMarkerView;
import com.amihealth.amihealth.Adaptadores.CustomXAxisRenderer;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.amihealth.amihealth.ModuloHTA.MedidaHTADetailActivity;
import com.amihealth.amihealth.ModuloHTA.MedidaHTAListActivity;
import com.amihealth.amihealth.ModuloHTA.view.InterfaceHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.ImpPresenterHta;
import com.amihealth.amihealth.ModuloHTA.view.presenter.PresenterHta;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.ModuloHTA.dummy.DummyContent;
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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A fragment representing a single MedidaHTA detail screen.
 * This fragment is either contained in a {@link MedidaHTAListActivity}
 * in two-pane mode (on tablets) or a {@link MedidaHTADetailActivity}
 * on handsets.
 */
public class MedidaHTADetailFragment extends Fragment implements InterfaceHta{


    /**********************************************
     *          VARIABLES UI
     **********************************************/
    private Toolbar toolbar;
    private TextView sys;
    private TextView dis;
    private TextView pls;
    private TextView fecha;
    private TextView hora;
    private TextView descrip;
    private CardView cardView;




    /***********************************************
     *
     *          VARIABLES DE ENTORNO
     *
     ***********************************************/

    private AppCompatActivity activity;
    private Realm realm;
    private PresenterHta presenterHta;
    private String ID;
    private MedidaHTA medidaHTAs;
    private ArrayList<String> fech = new ArrayList<>();





    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private LineChart graf;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MedidaHTADetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            presenterHta = new ImpPresenterHta(this,getContext());


            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            ID = getArguments().getString(ARG_ITEM_ID);
            //Toast.makeText(getContext(),getArguments().getString(ARG_ITEM_ID),Toast.LENGTH_LONG).show();
            presenterHta.getMedidabyId(ID);
            realm = Realm.getDefaultInstance();

            medidaHTAs = realm.where(MedidaHTA.class).equalTo("id",ID).findFirst();


            Activity activity = this.getActivity();
            this.activity = (AppCompatActivity) getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //appBarLayout.setTitle(mItem.content);
            }
        }


    }
    public void showtoolbar(String titulo, boolean mUpbtn){

        this.activity.setSupportActionBar(toolbar);
        this.activity.getSupportActionBar()   .setTitle(titulo);
        this.activity.getSupportActionBar()   .setDisplayHomeAsUpEnabled(mUpbtn);
    }




    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.medidahta_detail, container, false);
        toolbar         = (Toolbar) activity.findViewById(R.id.detail_toolbar);
        graf            = (LineChart) rootView.findViewById(R.id.medidas_graph);

        sys = (TextView) rootView.findViewById(R.id.sys_tool_detalle_descrip);
        dis = (TextView) rootView.findViewById(R.id.dis_tool_detalle_descrip);
        descrip = (TextView) rootView.findViewById(R.id.descrip_detalle_descrip);
        pls = (TextView) rootView.findViewById(R.id.pls_detalle);
        fecha = (TextView) rootView.findViewById(R.id.fecha_detalle);
        hora = (TextView) rootView.findViewById(R.id.hora_detalle);

        Date datoFecha = new Date();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            datoFecha = f.parse(medidaHTAs.getDate());
        }catch (ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        fecha.setText(sdf.format(datoFecha));
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        hora.setText(df.format(datoFecha));
        pls.setText(String.valueOf(medidaHTAs.getPulso()));




        cardView = (CardView) rootView.findViewById(R.id.card_detalle_descrip);

        cardView.setBackgroundColor(Color.parseColor(medidaHTAs.getRgb()));
        descrip.setText(medidaHTAs.getDescripcion());
        sys.setText(String.valueOf(medidaHTAs.getSYS()));
        dis.setText(String.valueOf(medidaHTAs.getDIS()));
        graf.setData(getDataGrafica(this.medidaHTAs));
        graf.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(context,resultados.get(position).getRealmResults().get((int) e.getX()).toString(),Toast.LENGTH_LONG).show();
                graf.setMarker(new CustomHTAMarkerView(getContext(),R.layout.graf_descrip,medidaHTAs));
            }

            @Override
            public void onNothingSelected() {

            }

        });




        XAxis xAxis = graf.getXAxis();




       graf.setXAxisRenderer(new CustomXAxisRenderer(
                graf.getViewPortHandler(),
                graf.getXAxis(),
               graf.getTransformer(YAxis.AxisDependency.LEFT),fech));


        XAxisRenderer aRender = graf.getRendererXAxis();


        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        //xAxis.setXOffset(-100f);


        //xAxis.setDrawLabels(true);



        //xAxis.setAxisMaximum(fechas.get(position).size());
        xAxis.setAxisMinimum(0f);
       // xAxis.setGranularity(1f);
        xAxis.setGridColor(Color.WHITE);
        //xAxis.setAvoidFirstLastClipping(true);

        xAxis.setCenterAxisLabels(false);
        //xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        //xAxis.setAxisMaximum(this.DatosGraf.get(position).getXMax()+0.50f);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //xAxis.setLabelRotationAngle(-90);
        xAxis.setTextColor(Color.WHITE);
        //xAxis.setYOffset(10);

        graf.getAxisLeft().setTextColor(ContextCompat.getColor(getContext(), R.color.grafIndicador_text));
        graf.setDrawGridBackground(false);
        graf.getAxisRight().setDrawLabels(false);
        graf.getAxisRight().setDrawGridLines(false);

        YAxis yAxis = graf.getAxis(YAxis.AxisDependency.LEFT);
        yAxis.setLabelCount(5,true);
        yAxis.setXOffset(-30f);
        yAxis.setYOffset(-5f);
        yAxis.setDrawZeroLine(true);
        yAxis.setGridColor(Color.WHITE);
        yAxis.setTextColor(Color.WHITE);






       /* holder.graf.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });
        */

        //holder.graf.setV

        //graf.setVisibleXRangeMaximum(3);
        graf.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        graf.setDrawGridBackground(false);

        graf.setScrollContainer(true);
        //graf.setBackgroundColor(Color.parseColor("#d12356"));
        graf.setPinchZoom(false);
        //graf.setScaleMinima(1,0);
        graf.setScaleEnabled(false);
        graf.setNoDataTextColor(Color.WHITE);
        graf.setBackgroundColor(Color.TRANSPARENT);
        graf.setClipValuesToContent(false);
        graf.setDragEnabled(true);


        //graf.setDragOffsetY(16);
        //graf.setVisibleXRangeMaximum(3);
        //graf.setVisibleXRangeMinimum(1);
        graf.setDrawBorders(false);
        //graf.getXAxis().setYOffset(16);


















        Legend legend = graf.getLegend();
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




        graf.invalidate();



        showtoolbar("Detalle",true);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
           // ((TextView) rootView.findViewById(R.id.medidahta_detail)).setText(mItem.details);
        }

        return rootView;
    }
    public void getData(){

    }


    @Override
    public void getMedidas(int orden) {

    }

    @Override
    public void showAll(RealmResults<MedidaHTA> medidaHTAs) {

    }

    @Override
    public void acciones(String action) {

    }

    @Override
    public void menuOP(MenuItem item) {

    }

    @Override
    public void insertar(MedidaHTA medidaHTA) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hiddenLoad() {

    }

    @Override
    public void errorRetrofit(int code) {

    }

    @Override
    public void mensaje(String mensaje) {

    }

    private LineData getDataGrafica(MedidaHTA medidaHTA){
        ArrayList<Entry> SYS = new ArrayList<>();
        ArrayList<Entry> DIS = new ArrayList<>();
        ArrayList<Entry> PLS = new ArrayList<>();

        ArrayList<BarEntry> fechaBar = new ArrayList<>();


            Date datoFecha = new Date();
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                datoFecha = f.parse(medidaHTA.getDate());
            }catch (ParseException e){
                e.printStackTrace();
            }



        SYS.add(new Entry((float) 0,120));
        SYS.add(new Entry((float) 1,medidaHTA.getSYS()));
        SYS.add(new Entry((float) 2,120));

        DIS.add(new Entry((float) 0, 80));
        DIS.add(new Entry((float) 1, medidaHTA.getDIS()));
        DIS.add(new Entry((float) 2, 80));

        PLS.add(new Entry((float) 0, 60));
        PLS.add(new Entry((float) 1, medidaHTA.getPulso()));
        PLS.add(new Entry((float) 2, 60));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd\nHH:mma");
        fech.add("");
        fech.add(sdf.format(datoFecha));
        fech.add("");


        LineDataSet lineSys = new LineDataSet(SYS,activity.getString(R.string.detalle_Sys));
        lineSys.setCircleColor(ContextCompat.getColor(activity,R.color.ms_white));
        lineSys.setColor(ContextCompat.getColor(activity,R.color.ms_white));
        lineSys.setFillColor(Color.parseColor("#ffffff"));
        //lineSys.setFillDrawable(ContextCompat.getDrawable(activity,R.drawable.sys_graf_detalle_gradient));
        lineSys.setFillAlpha(300);
        lineSys.setLineWidth(2f);
        lineSys.setCircleRadius(6f);
        lineSys.setDrawCircleHole(true);
        lineSys.setCircleHoleRadius(4f);
        lineSys.setCircleColorHole(ContextCompat.getColor(activity,R.color.sysGradientend));
        lineSys.setValueTextSize(12f);
        lineSys.setDrawFilled(true);
        lineSys.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineSys.setMode(LineDataSet.Mode.LINEAR);

        lineSys.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat mformat = new DecimalFormat("###,###,###");
                String x = mformat.format(value).toString();
                return x;
            }
        });


        LineDataSet lineDis = new LineDataSet(DIS,activity.getString(R.string.detalle_Dis));
        lineDis.setFillAlpha(100);
        lineDis.setCircleColor(ContextCompat.getColor(activity,R.color.ms_white));
        lineDis.setFillColor(Color.parseColor("#ffffff"));
        lineDis.setColor(Color.parseColor("#ffffff"));
        lineDis.setLineWidth(2f);
        //lineDis.setFillDrawable(ContextCompat.getDrawable(activity,R.drawable.dis_graf_detalle_gradient));

        lineDis.setCircleColorHole(ContextCompat.getColor(activity,R.color.sysGradientend));


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
                DecimalFormat mformat = new DecimalFormat("###,###,###");
                String x = mformat.format(value).toString();
                return x;
            }
        });


        LineDataSet linePls = new LineDataSet(PLS, activity.getString(R.string.detalle_pls));
        linePls.setCircleColor(Color.parseColor("#ffeb3b"));
        linePls.setDrawFilled(false);
        linePls.setColor(Color.parseColor("#ffeb3b"));
        linePls.setLineWidth(1f);
        linePls.setCircleRadius(3f);
        linePls.setDrawCircleHole(false);
        linePls.setValueTextSize(10f);



        linePls.setDrawValues(false);
        lineDis.setDrawValues(false);
        lineSys.setDrawValues(false);


        LineData a = new LineData();

        a.addDataSet(linePls);
        a.addDataSet(lineSys);
        a.addDataSet(lineDis);


        return a;

    }

}
