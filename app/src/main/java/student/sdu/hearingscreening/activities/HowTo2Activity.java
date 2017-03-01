package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import student.sdu.hearingscreening.R;

public class HowTo2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_2);

        Button btn = (Button) findViewById(R.id.btn_back2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), HowToActivity.class);
                HowTo2Activity.this.startActivity(mainIntent);
                HowTo2Activity.this.finish();
            }
        });

        btn = (Button) findViewById(R.id.btn_next2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                HowTo2Activity.this.startActivity(mainIntent);
                HowTo2Activity.this.finish();
            }
        });
    }
}
