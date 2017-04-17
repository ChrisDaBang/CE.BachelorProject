package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.adapters.EarlierResultsListAdapter;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.databasemanagement.ResultManager;
import student.sdu.hearingscreening.dataclasses.Test;
import student.sdu.hearingscreening.dataclasses.TestResultAnalyser;

public class EarlierResultsActivity extends AppCompatActivity {
    ResultManager rm = new ResultManager();

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
        ArrayList<Test> tests = rm.getTests();
        String[] titles = new String[tests.size()];
        String[][] contents = new String[tests.size()][0];
        for(Test t : tests) {
            System.out.println("TESTSTSTS " + t.getTestNo());
            TestResultAnalyser tr = new TestResultAnalyser(t.getTestNo());
            tr.analyseResult();
            titles[t.getTestNo()-1] = "Test #" + t.getTestNo() + " " + t.getDate();
            String[] temp = new String[3];
            temp[0] = tr.getNormativeResponse();
            temp[1] = tr.getComparativeResponse();
            temp[2] = tr.getRecommendation();
            contents[t.getTestNo()-1] = temp;
        }
        EarlierResultsListAdapter la  = new EarlierResultsListAdapter(this, contents, titles);
        lv.setAdapter(la);
    }

    @Override
    public void onBackPressed()
    {
        HearingScreeningApplication.activityIntentSwitch(new ResultsActivity(), EarlierResultsActivity.this);
    }
}