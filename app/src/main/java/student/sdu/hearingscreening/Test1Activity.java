package student.sdu.hearingscreening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class Test1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Intent mainIntent = new Intent(getApplicationContext(), Test2Activity.class);
        Test1Activity.this.startActivity(mainIntent);
        Test1Activity.this.finish(); //Should you finish? Who knows

        return super.onTouchEvent(event);
    }
}
