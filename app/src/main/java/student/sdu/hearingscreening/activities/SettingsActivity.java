package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import student.sdu.hearingscreening.R;

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
                Intent mainIntent = new Intent(getApplicationContext(), CalibrateCheckActivity.class);
                SettingsActivity.this.startActivity(mainIntent);
                SettingsActivity.this.finish();
            }
        });
        btn = (Button) findViewById(R.id.btn_volumetest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), VolumeTestActivity.class);
                SettingsActivity.this.startActivity(mainIntent);
                SettingsActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        SettingsActivity.this.startActivity(mainIntent);
        SettingsActivity.this.finish();

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        SettingsActivity.this.startActivity(mainIntent);
        SettingsActivity.this.finish();
    }
}
