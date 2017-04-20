package student.sdu.hearingscreening.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.resultAnalysis.TestResultAnalyser;

public class ResultsActivity extends AppCompatActivity {
    TestResultAnalyser analyser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyser = new TestResultAnalyser();
        setContentView(R.layout.activity_results);
        setupTextViews();
        setupButtons();
    }
    private void setupTextViews() {
        analyser.analyseResult();
        TextView t = (TextView) findViewById(R.id.tv_latest);
        t.setText(analyser.getNormativeResponse());
        t = (TextView) findViewById(R.id.tv_latest_suggest);
        t.setText(analyser.getRecommendation());
        t = (TextView) findViewById(R.id.tv_latest_change);
        t.setText(analyser.getComparativeResponse());
    }
    private void setupButtons() {
        Button btn = (Button) findViewById(R.id.btn_earlier_results);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new EarlierResultsActivity(), ResultsActivity.this);
            }
        });

        findViewById(R.id.btn_send_results).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new SendResultsActivity(), ResultsActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        HearingScreeningApplication.activityIntentSwitch(new MainMenuActivity(), this);
    }
}
