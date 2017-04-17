package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class HowTo3Activity extends AppCompatActivity {
    private float x1;
    private float x2;
    private static final int DISTANCE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_3);

        ImageButton btn = (ImageButton) findViewById(R.id.ib_swipe_right3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new HowTo4Activity(), HowTo3Activity.this, R.anim.push_right_in, R.anim.push_right_out2);
            }
        });

        btn = (ImageButton) findViewById(R.id.ib_swipe_left3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                HearingScreeningApplication.activityIntentSwitch(new HowTo2Activity(), HowTo3Activity.this, R.anim.push_right_out, R.anim.push_right_in2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        HearingScreeningApplication.activityIntentSwitch(new HowTo2Activity(), HowTo3Activity.this, R.anim.push_right_out, R.anim.push_right_in2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        HearingScreeningApplication.activityIntentSwitch(new HowTo2Activity(), HowTo3Activity.this, R.anim.push_right_out, R.anim.push_right_in2);

                    }

                    // Right to left swipe action
                    else
                    {
                        HearingScreeningApplication.activityIntentSwitch(new HowTo4Activity(), HowTo3Activity.this, R.anim.push_right_in, R.anim.push_right_out2);
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
