package student.sdu.hearingscreening.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.CombinedChart;

import java.util.ArrayList;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.adapters.ChartMaker;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class AudiometricChartActivity extends AppCompatActivity {
    private ChartMaker cm;
    private int index;
    private CombinedChart cc;
    private ArrayList<Integer> tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cm = new ChartMaker();
        this.index = 0;
        this.tests = HearingScreeningApplication.testsChosen;
        setContentView(R.layout.activity_audiometric_chart);
        this.cc = (CombinedChart) findViewById(R.id.chart);
        setupButtons();
        drawChart();
    }

    private void drawChart() {
        cm.getAudioMetricChartFromData(cc, tests.get(index));
    }

    private void setupButtons() {
        Button btn = (Button)findViewById(R.id.btn_chart_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index + 1 < tests.size()) {
                    index++;
                    drawChart();
                } else {
                    HearingScreeningApplication.activityIntentSwitch(new SendResultsActivity(), AudiometricChartActivity.this);
                }
            }
        });
        btn = (Button) findViewById(R.id.btn_chart_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index - 1 >= 0) {
                    index--;
                    drawChart();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        HearingScreeningApplication.activityIntentSwitch(new SendResultsActivity(), AudiometricChartActivity.this);
    }
}
