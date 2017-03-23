package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Random;

import student.sdu.hearingscreening.dataclasses.Result;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.databasemanagement.ResultManager;

public class UserBarchartActivity extends AppCompatActivity {
    private  CombinedChart cc;
    private ResultManager rm;
    private ArrayList<String> xAxisLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_barchart);
        cc = (CombinedChart) findViewById(R.id.bc_user_barchart);
        rm = new ResultManager();
        testbc();
    }

    private void testbc() {
        ArrayList<Result> results = rm.getLatestResults();
        initializeXLabels(results);
        addReferenceLines();
        ArrayList<Entry> lineEntries = new ArrayList();
        for(Result r : results) {
            lineEntries.add(new Entry(r.getFrequencyId(), r.getThreshold()));
        }
        LineDataSet lds = new LineDataSet(lineEntries, "linetest");
        LineData ld = new LineData(lds);
        ld.setDrawValues(false);
        CombinedData cd = new CombinedData();
        cd.setData(ld);
        cc.setData(cd);
        cc.setScaleMinima(1f, 1f);

    }
    @Override
    public void onBackPressed()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        UserBarchartActivity.this.startActivity(mainIntent);
        UserBarchartActivity.this.finish();
    }

    private void initializeXLabels(ArrayList<Result> results) {
        xAxisLabels = new ArrayList();
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
    private void addReferenceLines() {
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
        yAxis.setGranularity(5f);
    }
}
