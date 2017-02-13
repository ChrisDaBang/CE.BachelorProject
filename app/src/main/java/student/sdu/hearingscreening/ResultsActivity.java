package student.sdu.hearingscreening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setupTextViews();
        setupButtons();
    }
    private void setupTextViews() {
        TextView t = (TextView) findViewById(R.id.results_latest_textview);
        String[] tests = getResources().getStringArray(R.array.tests);
        String[] result = tests[tests.length-1].split(";");
        t.setText("Seneste resultat: " + result[2]);
        t = (TextView) findViewById(R.id.results_latest_suggest_textview);
        t.setText("Ændring i hørelse: " + result[4]);
        t = (TextView) findViewById(R.id.results_latest_change_textview);
        t.setText("Forslag: " + result[3]);
    }
    private void setupButtons() {
        Button btn = (Button) findViewById(R.id.result_latest_earlier_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), EarlierResultsActivity.class);
                ResultsActivity.this.startActivity(mainIntent);
                ResultsActivity.this.finish();
            }
        });
    }
}
