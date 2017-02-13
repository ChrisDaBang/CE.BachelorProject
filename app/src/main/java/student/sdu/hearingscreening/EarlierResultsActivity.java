package student.sdu.hearingscreening;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EarlierResultsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earlier_results);
        setupListView();
    }

    private void setupListView() {
        ExpandableListView lv = (ExpandableListView) findViewById(R.id.earlier_results_exp);
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
        ExpandableListAdapterTest la  = new ExpandableListAdapterTest(this, contents, titles);
        lv.setAdapter(la);
    }

}