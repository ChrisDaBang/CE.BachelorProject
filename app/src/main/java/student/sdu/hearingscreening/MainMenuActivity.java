package student.sdu.hearingscreening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btn = (Button) findViewById(R.id.button_HowTo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), HowToActivity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish(); //Should you finish? Who knows
            }
        });

        btn = (Button) findViewById(R.id.button_StartTest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), Test1Activity.class);
                MainMenuActivity.this.startActivity(mainIntent);
                MainMenuActivity.this.finish(); //Should you finish? Who knows
            }
        });
    }
}
