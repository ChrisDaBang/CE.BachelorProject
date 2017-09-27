package student.sdu.hearingscreening.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.databasemanagement.DBHelper;
import student.sdu.hearingscreening.databasemanagement.TestDAO;

public class CalibrationDataActivity extends AppCompatActivity {

    private TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration_data);
        tvData = (TextView) findViewById(R.id.tv_dataView);
        getData();
    }

    private void getData()
    {
        TestDAO tDAO = new TestDAO(HearingScreeningApplication.getContext());
        Map<Integer, Float> calibrationData = new HashMap<>();
        calibrationData = tDAO.getCalibrationValues();
        tvData.setText("0, 250: " + calibrationData.get(0) + "\n" +
                "1, 500: " + calibrationData.get(1) + "\n" +
                "2, 1000: " + calibrationData.get(2) + "\n" +
                "3, 2000: " + calibrationData.get(3) + "\n" +
                "4, 3000: " + calibrationData.get(4) + "\n" +
                "5, 4000: " + calibrationData.get(5) + "\n" +
                "6, 6000: " + calibrationData.get(6) + "\n" +
                "7, 8000: " + calibrationData.get(7) + "\n");
    }

    @Override
    public void onBackPressed()
    {
        HearingScreeningApplication.activityIntentSwitch(new SettingsActivity(),this);
    }
}
