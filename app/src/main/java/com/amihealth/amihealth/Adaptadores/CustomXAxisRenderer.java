package com.amihealth.amihealth.Adaptadores;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * Created by amihealthmel on 12/26/17.
 */

public class CustomXAxisRenderer extends XAxisRenderer {

    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans, ArrayList<String> strings) {
        super(viewPortHandler, xAxis, trans);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));


    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        String line[] = formattedLabel.split("\n");

        try{
            Utils.drawXAxisValue(c,line[0],x+100,y,mAxisLabelPaint,anchor,angleDegrees);
            Utils.drawXAxisValue(c,line[1],x+100,y + mAxisLabelPaint.getTextSize(),mAxisLabelPaint,anchor,angleDegrees);

        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }
}
