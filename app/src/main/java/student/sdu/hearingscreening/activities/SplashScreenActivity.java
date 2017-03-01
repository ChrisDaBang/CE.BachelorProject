package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import student.sdu.hearingscreening.R;

public class SplashScreenActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //TextView tw = (TextView) findViewById(R.id.textView2);
        //tw.setText("Hej");
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
                Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, (time * 1000));
    }
}
