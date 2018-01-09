package com.amihealth.amihealth.Models;

import com.jjoe64.graphview.series.DataPoint;

import java.util.Date;

/**
 * Created by amihealthmel on 08/25/17.
 */

public class MedidasGraph extends DataPoint {
    private Date x;
    private int y;
    public MedidasGraph(Date x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }
}
