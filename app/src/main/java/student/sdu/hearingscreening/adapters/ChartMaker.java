package student.sdu.hearingscreening.adapters;

import android.graphics.Color;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.databasemanagement.ResultManager;
import student.sdu.hearingscreening.dataclasses.Result;

/**
 * Created by Bogs on 27-04-2017.
 */

public class ChartMaker {
    ResultManager rm = new ResultManager();

    public void getAudioMetricChartFromData(CombinedChart cc, int testno) {
        cc.clear();
        Result[] left = rm.getSpecificResultsForAnalysis(testno, 0);
        Result[] right = rm.getSpecificResultsForAnalysis(testno, 1);
        ArrayList<Entry> lineEntriesLeft = new ArrayList();
        ArrayList<Entry> lineEntriesRight = new ArrayList();
        ArrayList<Result> labelList = new ArrayList();
        for(Result r : left) {
            lineEntriesLeft.add(new Entry(r.getFrequencyId(), r.getThreshold()));
            labelList.add(r);
        }
        for(Result r : right) {
            lineEntriesRight.add(new Entry(r.getFrequencyId(), r.getThreshold()));
            labelList.add(r);
        }
        ScatterData sd = new ScatterData();
        sd.addDataSet(getScatterDataLeft(lineEntriesLeft));
        sd.addDataSet(getScatterDataRight(lineEntriesRight));
        LineData ld = new LineData();
        ld.addDataSet(getLineData(lineEntriesLeft));
        ld.addDataSet(getLineData(lineEntriesRight));
        initializeXLabels(labelList, cc);
        addReferenceLines(cc);
        CombinedData cd = new CombinedData();
        cd.setDrawValues(true);
        cd.setData(ld);
        cd.setData(sd);
        cc.setData(cd);
        cc.setScaleMinima(1f, 1f);
        cc.getLegend().setEnabled(false);
        cc.getDescription().setEnabled(false);
        cc.getAxisLeft().setAxisMaximum(110f);
        cc.getAxisLeft().setAxisMinimum(-10f);
        cc.getAxisLeft().setInverted(true);
        cc.getAxisRight().setDrawLabels(false);
    }

    private LineDataSet getLineData(ArrayList<Entry> lineEntries) {
        LineDataSet lds = new LineDataSet(lineEntries, "Venstre øre");
        lds.setColor(Color.BLACK);
        lds.setDrawCircles(false);
        lds.setDrawValues(false);
        return lds;
    }
    private ScatterDataSet getScatterDataLeft(ArrayList<Entry> lineEntries) {
        ScatterDataSet scd = new ScatterDataSet(lineEntries, "Venstre øre");
        scd.setColor(Color.BLACK);
        scd.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scd.setScatterShapeHoleRadius(5f);
        scd.setScatterShapeHoleColor(Color.WHITE);
        scd.setValueTextColor(Color.RED);
        return scd;
    }
    private ScatterDataSet getScatterDataRight(ArrayList<Entry> lineEntries) {
        ScatterDataSet scd = new ScatterDataSet(lineEntries, "Højre øre");
        scd.setScatterShape(ScatterChart.ScatterShape.X);
        scd.setColor(Color.BLACK);
        scd.setValueTextColor(Color.RED);
        return scd;
    }
    private void initializeXLabels(ArrayList<Result> results, CombinedChart cc) {
        final ArrayList<String> xAxisLabels = new ArrayList();
        for(Result r : results) {
            xAxisLabels.add(r.getFrequencyId(), r.getFrequency() + "hz");
        }
        XAxis xAxis = cc.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisLabels.get((int)value);
            }
        });
    }
    private void addReferenceLines(CombinedChart cc) {
        YAxis yAxis = cc.getAxisLeft();
        LimitLine l = new LimitLine(0f, "Normal hørelse");
        l.setLineWidth(1f);
        l.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        l.setTextSize(10f);
        l.setLineColor(Color.GREEN);
        yAxis.addLimitLine(l);
        l = new LimitLine(25f, "Mild høretab");
        l.setLineColor(Color.YELLOW);
        l.setLineWidth(1f);
        l.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        l.setTextSize(10f);
        yAxis.addLimitLine(l);
        l = new LimitLine(40f, "Moderat høretab");
        l.setLineColor(Color.BLACK);
        l.setLineWidth(1f);
        l.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        l.setTextSize(10f);
        yAxis.addLimitLine(l);
        l = new LimitLine(55f, "Moderat alvorligt høretab");
        l.setLineColor(Color.BLACK);
        l.setLineWidth(1f);
        l.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        l.setTextSize(10f);
        yAxis.addLimitLine(l);
        l = new LimitLine(70f, "Alvorligt høretab");
        l.setLineColor(Color.MAGENTA);
        l.setLineWidth(1f);
        l.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        l.setTextSize(10f);
        yAxis.addLimitLine(l);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawLimitLinesBehindData(true);
        yAxis.setGranularity(1f);
    }
}
