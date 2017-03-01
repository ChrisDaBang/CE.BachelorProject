package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import student.sdu.hearingscreening.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        SettingsActivity.this.startActivity(mainIntent);
        SettingsActivity.this.finish();

        return super.onTouchEvent(event);
    }

}
