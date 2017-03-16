package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import student.sdu.hearingscreening.R;

public class CalibrateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);
    }

    @Override
    public void onBackPressed()
    {
        Intent mainIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        CalibrateActivity.this.startActivity(mainIntent);
        CalibrateActivity.this.finish();
    }
}
