package student.sdu.hearingscreening.databasemanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.dataclasses.Result;

/**
 * Created by Bogs on 20-03-2017.
 */

public class ResultManager {
    private DBHelper dbh;

    public ResultManager() {
        dbh = new DBHelper(HearingScreeningApplication.getContext());
    }

    public ArrayList<Result> getLatestResults() {
        //returns three Strings regarding the result of the hearing screening
        //Latest result, Change compared to first test and suggestion
        SQLiteDatabase db = dbh.getReadableDatabase();
        ArrayList<Result> results = new ArrayList();
        Cursor res = db.rawQuery("SELECT re.threshold, re.ear, re.freqid, fr.value FROM TBLRESULT re\n" +
                "LEFT JOIN TBLFREQUENCY fr ON re.freqid = fr.freqid\n" +
                "WHERE testid = (SELECT MAX(testid) FROM TBLTEST\n)" +
                "AND re.ear = 0 \n" +
                "ORDER BY re.freqid", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                int threshold = res.getInt(res.getColumnIndex("threshold"));
                int ear = res.getInt(res.getColumnIndex("ear"));
                String frequency =""+ res.getInt(res.getColumnIndex("value"));
                int frequencyId = res.getInt(res.getColumnIndex("freqid"));
                Result r = new Result(threshold, ear, frequency, frequencyId);
                results.add(r);

                res.moveToNext();
            }
        }
        return results;
    }
}
