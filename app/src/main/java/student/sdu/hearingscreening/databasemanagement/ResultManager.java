package student.sdu.hearingscreening.databasemanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.dataclasses.Result;
import student.sdu.hearingscreening.translators.ITranslator;
import student.sdu.hearingscreening.translators.SennheiserHDA200Translator;

/**
 * Created by Bogs on 20-03-2017.
 */

public class ResultManager {
    private DBHelper dbh;
    private ITranslator translator;

    public ResultManager() {
        dbh = new DBHelper(HearingScreeningApplication.getContext());
        translator = new SennheiserHDA200Translator();
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
                Result r = new Result(translator.translate(threshold, frequencyId), ear, frequency, frequencyId);
                results.add(r);

                res.moveToNext();
            }
        }
        return results;
    }

    public Result[] getLatestResultsForAnalysis(int ear) {
        Result[] latest = new Result[8];
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT re.threshold, re.ear, re.freqid, fr.value FROM TBLRESULT re\n" +
                "LEFT JOIN TBLFREQUENCY fr ON re.freqid = fr.freqid\n" +
                "WHERE testid = (SELECT MAX(testid) FROM TBLTEST\n)" +
                "AND re.ear = " + ear + "\n" +
                "ORDER BY re.freqid", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                int threshold = res.getInt(res.getColumnIndex("threshold"));
                int testEar = res.getInt(res.getColumnIndex("ear"));
                String frequency = "" + res.getInt(res.getColumnIndex("value"));
                int frequencyId = res.getInt(res.getColumnIndex("freqid"));
                Result r = new Result(translator.translate(threshold, frequencyId), ear, frequency, frequencyId);
                latest[r.getFrequencyId()] = r;

                res.moveToNext();
            }
        }
        return latest;
    }

    public boolean checkIfComparisonPossible() {
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT testid FROM TBLTEST", null);
        if(res.getCount()>=2) {
            return true;
        }
        return false;
    }
    public boolean checkIfAtleastOne() {
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT testid FROM TBLTEST", null);
        if(res.getCount()<=0) {
            return false;
        }
        return true;
    }
    public Result[] getBaseResultsForAnalysis(int ear) {
        Result[] base = new Result[8];
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT re.threshold, re.ear, re.freqid, fr.value FROM TBLRESULT re\n" +
                "LEFT JOIN TBLFREQUENCY fr ON re.freqid = fr.freqid\n" +
                "WHERE testid = (SELECT MIN(testid) FROM TBLTEST\n)" +
                "AND re.ear = " + ear + "\n" +
                "ORDER BY re.freqid", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                int threshold = res.getInt(res.getColumnIndex("threshold"));
                int testEar = res.getInt(res.getColumnIndex("ear"));
                String frequency = "" + res.getInt(res.getColumnIndex("value"));
                int frequencyId = res.getInt(res.getColumnIndex("freqid"));
                Result r = new Result(translator.translate(threshold, frequencyId), ear, frequency, frequencyId);
                base[r.getFrequencyId()] = r;

                res.moveToNext();
            }
        }
        return base;
    }
}
