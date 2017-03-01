package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import student.sdu.hearingscreening.R;

public class HowToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        Button btn = (Button) findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), HowTo2Activity.class);
                HowToActivity.this.startActivity(mainIntent);
                HowToActivity.this.finish();
            }
        });

        btn = (Button) findViewById(R.id.btn_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                HowToActivity.this.startActivity(mainIntent);
                HowToActivity.this.finish();
            }
        });
    }
}
