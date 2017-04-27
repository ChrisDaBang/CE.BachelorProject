package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btn = (Button) findViewById(R.id.btn_howto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new HowToActivity(), MainMenuActivity.this, R.anim.push_right_in, R.anim.push_right_out2);
            }
        });

        btn = (Button) findViewById(R.id.btn_test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new Test1Activity(), MainMenuActivity.this);
            }
        });

        btn = (Button) findViewById(R.id.btn_results);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new ResultsActivity(), MainMenuActivity.this);
            }
        });

        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new SettingsActivity(), MainMenuActivity.this);
            }
        });
        btn = (Button) findViewById(R.id.btn_dbtest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new DBTestActivity(), MainMenuActivity.this);
            }
        });
    }
}
