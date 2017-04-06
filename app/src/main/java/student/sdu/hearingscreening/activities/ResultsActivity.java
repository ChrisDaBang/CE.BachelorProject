package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setupTextViews();
        setupButtons();
    }
    private void setupTextViews() {
        TextView t = (TextView) findViewById(R.id.tv_latest);
        String[] tests = getResources().getStringArray(R.array.tests);
        String[] result = tests[tests.length-1].split(";");
        t.setText("Seneste resultat: " + result[2]);
        t = (TextView) findViewById(R.id.tv_latest_suggest);
        t.setText("Ændring i hørelse: " + result[4]);
        t = (TextView) findViewById(R.id.tv_latest_change);
        t.setText("Forslag: " + result[3]);
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
