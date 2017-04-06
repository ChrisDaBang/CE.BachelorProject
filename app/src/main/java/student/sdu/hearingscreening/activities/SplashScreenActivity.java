package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class SplashScreenActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setSplashScreenTime(5);
    }

    /**
     * sets the display time of the splashscreen to time given.
     * Input should be in seconds.
     * @param time
     */
    private void setSplashScreenTime (int time)
    {
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                HearingScreeningApplication.activityIntentSwitch(new MainMenuActivity(), SplashScreenActivity.this);
            }
        }, (time * 1000));
    }
}
