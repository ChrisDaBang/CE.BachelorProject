package student.sdu.hearingscreening.databasemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import student.sdu.hearingscreening.dataclasses.TestDTO;
import student.sdu.hearingscreening.OneUpTwoDownTest.TestEntry;

/**
 * Created by Bogs on 13-03-2017.
 */

public class TestDAO {
    DBHelper dbHelper;

    public TestDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void saveTest(TestDTO test) {
        int testId = getLatestTestId() + 1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", testId);
        Date current = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        contentValues.put("date", formatter.format(current));
        db.insert("TBLTEST", null, contentValues);

        //Put left ear testentries in db
        for(Integer freq : test.getLeftEarEntries().keySet()) {
            for(TestEntry entry : test.getLeftEarEntries().get(freq)) {
                contentValues = new ContentValues();
                contentValues.put("testid", testId);
                contentValues.put("decibel", entry.getDbhl());
                contentValues.put("answer", entry.getAnswer());
                contentValues.put("sequenceid", entry.getSequenceId());
                contentValues.put("ear", 0);
                contentValues.put("catchtrial", entry.isCatchTrial());
                db.insert("TBLTESTENTRIES", null, contentValues);
            }
        }
        //Put right ear testentries in db
        for(Integer freq : test.getRightEarEntries().keySet()) {
            for(TestEntry entry : test.getRightEarEntries().get(freq)) {
                contentValues = new ContentValues();
                contentValues.put("testid", testId);
                contentValues.put("decibel", entry.getDbhl());
                contentValues.put("answer", entry.getAnswer());
                contentValues.put("sequenceid", entry.getSequenceId());
                contentValues.put("ear", 1);
                contentValues.put("catchtrial", entry.isCatchTrial());
                db.insert("TBLTESTENTRIES", null, contentValues);
            }
        }
        //Put left ear results in db
        for(Integer freqid : test.getResultLeftEar().keySet()) {
            Float threshold = test.getResultLeftEar().get(freqid);
            contentValues = new ContentValues();
            contentValues.put("testid", testId);
            contentValues.put("threshold", threshold);
            contentValues.put("freqid", freqid);
            contentValues.put("ear", 0);
            db.insert("TBLRESULT", null, contentValues);
        }
        //Put right ear results in db
        for(Integer freqid : test.getResultRightEar().keySet()) {
            Float threshold = test.getResultRightEar().get(freqid);
            contentValues = new ContentValues();
            contentValues.put("testid", testId);
            contentValues.put("threshold", threshold);
            contentValues.put("freqid", freqid);
            contentValues.put("ear", 1);
            db.insert("TBLRESULT", null, contentValues);
        }
        System.out.println("test saved");
    }

    private int getLatestTestId() {
        int testid = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(testid) FROM TBLTEST", null);
        if(res.getCount() > 0) {
            res.moveToFirst();
            testid = res.getInt(res.getColumnIndex("MAX(testid)"));
        }
        return testid;
    }

    public Map<Integer, Float> getCalibrationValues()
    {
        Map<Integer, Float> calibrationValues = new HashMap<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM TBLCALIBRATION", null);
        if(res.getCount() > 0)
        {
            res.moveToFirst();
            while (!res.isAfterLast())
            {
                int freqID = res.getInt(res.getColumnIndex("freqid"));
                float decibelMax = res.getFloat(res.getColumnIndex("maxoutput"));
                calibrationValues.put(freqID, decibelMax);
                res.moveToNext();
            }
        }
        return calibrationValues;
    }
}
