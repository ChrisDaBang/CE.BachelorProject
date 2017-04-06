package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class CalibrateCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate_check);

        Button btn = (Button) findViewById(R.id.btn_calibrate_yes);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new CalibrateActivity(), CalibrateCheckActivity.this);
            }
        });

        btn = (Button) findViewById(R.id.btn_calibrate_no);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HearingScreeningApplication.activityIntentSwitch(new MainMenuActivity(), CalibrateCheckActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        HearingScreeningApplication.activityIntentSwitch(new SettingsActivity(), this);
    }
}
