package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.databasemanagement.ResultManager;
import student.sdu.hearingscreening.dataclasses.Test;

public class SendResultsActivity extends AppCompatActivity {

    ListView lv;
    ResultManager rm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_results);
        lv = (ListView) findViewById(R.id.lv_results);
        rm = new ResultManager();
        setupListView();
        setupButtons();
    }

    private void setupListView()
    {
        ArrayList<Test> tests = rm.getTests();
        String[] titles = new String[tests.size()];
        for(Test t : tests) {
            titles[t.getTestNo()-1] = "Test #" + t.getTestNo() + " " + t.getDate();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,
                titles);

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);
    }

    private void setupButtons()
    {
        Button sendResultsBtn = (Button) findViewById(R.id.btn_send_results);
        sendResultsBtn.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String selected = "";
                int cntChoice = lv.getCount();

                SparseBooleanArray sparseBooleanArray = lv.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){

                    if(sparseBooleanArray.get(i)) {

                        selected += lv.getItemAtPosition(i).toString() + "\n";
                    }
                }

                selected += "will be sent";
                Toast.makeText(SendResultsActivity.this,

                        selected,

                        Toast.LENGTH_LONG).show();
            }});

        Button markAllBtn = (Button) findViewById(R.id.btn_mark_all);
        markAllBtn.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < lv.getCount(); i++)
                {
                    lv.setItemChecked(i,true);
                }
            }
        });

        Button demarkAllBtn = (Button) findViewById(R.id.btn_demark_all);
        demarkAllBtn.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < lv.getCount(); i++)
                {
                    lv.setItemChecked(i,false);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        HearingScreeningApplication.activityIntentSwitch(new ResultsActivity(), this);
    }
}
