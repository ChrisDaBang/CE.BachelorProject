package student.sdu.hearingscreening.calibration;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.databasemanagement.DBHelper;

/**
 * Created by Chris on 20-03-2017.
 */

public class CalibrationProcessor
{
    private int calibrationFreqNo;
    private boolean calibrationOver;
    private int[] files;
    private Map<Integer, Integer> frequencyAtMax;
    private DBHelper dbHelper;

    /**
     *
     */
    public CalibrationProcessor()
    {
        calibrationFreqNo = 0;
        frequencyAtMax = new HashMap<>();
        calibrationOver = false;
        setupTracks();
        dbHelper = new DBHelper(HearingScreeningApplication.getContext());
    }

    /**
     *
     * @param db
     * @return
     */
    public void calibrate(int db)
    {
        frequencyAtMax.put(calibrationFreqNo, db);

        if (calibrationFreqNo == 7)
        {
            saveDataToDataBase();
            calibrationOver = true;
        }
        else
        {
            calibrationFreqNo++;
        }
    }

    private boolean saveDataToDataBase()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < frequencyAtMax.size(); i++)
        {
            ContentValues cv = new ContentValues();
            cv.put("freqid", i);
            cv.put("maxoutput", frequencyAtMax.get(i));
            db.insert("TBLCALIBRATION", null, cv);
        }
        return true;
    }
    /**
     *
     * @return
     */
    public float initialCalibrationSettings()
    {
        return 1f;
    }

    public Boolean isCalibrationDone()
    {
        return calibrationOver;
    }

    /**
     * Instantiates the files[] array with the sound resources.
     */
    private void setupTracks()
    {
        files = new int[8];
        files[0] = R.raw.twohundredfifty;
        files[1] = R.raw.fivehundred;
        files[2] = R.raw.onek;
        files[3] = R.raw.twok;
        files[4] = R.raw.threek;
        files[5] = R.raw.fourk;
        files[6] = R.raw.sixk;
        files[7] = R.raw.eightk;
    }

    public int getSoundFile()
    {
        return files[calibrationFreqNo];
    }
}
