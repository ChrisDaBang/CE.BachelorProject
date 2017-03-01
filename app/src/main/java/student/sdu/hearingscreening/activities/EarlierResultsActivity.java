package student.sdu.hearingscreening.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.adapters.EarlierResultsListAdapter;

public class EarlierResultsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earlier_results);
        setupListView();
    }

    private void setupListView() {
        ExpandableListView lv = (ExpandableListView) findViewById(R.id.elv_results);
        lv.setGroupIndicator(null);
        lv.setChildIndicator(null);
        String[] tests = getResources().getStringArray(R.array.tests);
        String[] titles = new String[tests.length];
        String[][] contents = new String[tests.length][0];
        for(int i = 0; i<tests.length; i++) {
            String temp = tests[i];
            String[] details = temp.split(";");
            String title = "Test #" + details[0] + " " + details[1];
            titles[i] = title;
            String[] temp2 = new String[3];
            for(int k = 0; k<3; k++) {
                if(k==0) {
                    temp2[k] = "Resultat: " + details[k+2];
                } else if (k==1) {
                    temp2[k] = "Ændring i hørelse: " + details[k+2];
                } else {
                    temp2[k] = "Forslag: " + details[k+2];
                }
            }
            contents[i] = temp2;
        }
        EarlierResultsListAdapter la  = new EarlierResultsListAdapter(this, contents, titles);
        lv.setAdapter(la);
    }

}