package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import student.sdu.hearingscreening.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btn = (Button) findViewById(R.id.btn_howto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), HowToActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                MainMenuActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out2);
                MainMenuActivity.this.finish();
            }
        });

        btn = (Button) findViewById(R.id.btn_test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), Test1Activity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish(); //Should you finish? Who knows
            }
        });

        btn = (Button) findViewById(R.id.btn_results);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), ResultsActivity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish(); //Should you finish? Who knows
            }
        });

        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish(); //Should you finish? Who knows
            }
        });
        btn = (Button) findViewById(R.id.btn_dbtest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), DBTestActivity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish(); //Should you finish? Who knows
            }
        });

        btn = (Button) findViewById(R.id.btn_barchart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), UserBarchartActivity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish(); //Should you finish? Who knows
            }
        });
    }
}
