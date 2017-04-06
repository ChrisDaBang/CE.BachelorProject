package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btn = (Button) findViewById(R.id.btn_calibrate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new CalibrateActivity(), SettingsActivity.this);
            }
        });
        btn = (Button) findViewById(R.id.btn_volumetest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new VolumeTestActivity(), SettingsActivity.this);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        HearingScreeningApplication.activityIntentSwitch(new MainMenuActivity(), this);

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed()
    {
        HearingScreeningApplication.activityIntentSwitch(new MainMenuActivity(), this);
    }
}
