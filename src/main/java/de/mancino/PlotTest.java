/*
 * PlotTest.java 25.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.Smooth;
import com.panayotis.gnuplot.style.Style;

public class PlotTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        JavaPlot p = new JavaPlot();
        int res[][] = {{-1,0},{0,2},{1,2},{2,3},{3,2}};
        DataSetPlot dataSet = new DataSetPlot(res);

        p.addPlot(dataSet);
        p.setTitle("'test'");
        //PlotStyle stl = ((AbstractPlot) p.getPlots().get(1)).getPlotStyle();
        dataSet.getPlotStyle().setStyle(Style.LINES);
        dataSet.setSmooth(Smooth.BEZIER);
        p.plot();

        /*
        double[][] plot = {{1, 1.1}, {2, 2.2}, {3, 3.3}, {4, 4.3}};
        DataSetPlot s = new DataSetPlot(plot);
        p.addPlot(s);
        p.addPlot("besj0(x)*0.12e1");
        PlotStyle stl = ((AbstractPlot) p.getPlots().get(1)).getPlotStyle();
        stl.setStyle(Style.POINTS);
        stl.setLineType(NamedPlotColor.GOLDENROD);
        stl.setPointType(5);
        stl.setPointSize(8);
        */
        System.err.println("done");
        if(true)return;
    }

}
