package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import student.sdu.hearingscreening.R;

public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        Test2Activity.this.startActivity(mainIntent);
        Test2Activity.this.finish(); //Should you finish? Who knows

        return super.onTouchEvent(event);
    }
}
