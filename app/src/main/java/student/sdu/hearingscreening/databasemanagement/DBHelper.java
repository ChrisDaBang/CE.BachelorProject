package student.sdu.hearingscreening.databasemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

import student.sdu.hearingscreening.R;

/**
 * Created by Bogs on 13-03-2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase currentDB = null;

    private static String DB_NAME = "HEARINGSCREENING";
    private static int DB_VERSION = 1;
    private static String DB_TABLETEST_CREATION = "CREATE TABLE IF NOT EXISTS `TBLTEST` (\n" +
            "  `testid` INTEGER PRIMARY KEY,\n" +
            "  `date` TEXT\n" +
            ");";
    private static String DB_TABLESETTINGS_CREATION = "CREATE TABLE IF NOT EXISTS `TBLSETTINGS` (\n" +
            "  `startdb` INTEGER,\n" +
            "  `firsttime` BOOLEAN,\n" +
            "  `doctor` VARCHAR(255)\n" +
            ");";
    private static String DB_TABLETESTENTRIES_CREATION = "CREATE TABLE IF NOT EXISTS `TBLTESTENTRIES` (\n" +
            "  `testid` INTEGER,\n" +
            "  `decibel` FLOAT,\n" +
            "  `answer` BOOLEAN,\n" +
            "  `sequenceid` INTEGER,\n" +
            "  `entryid` INTEGER PRIMARY KEY,\n" +
            "  `ear` INTEGER,\n" +
            "  `catchtrial` BOOLEAN \n" +
            ");";
    private static String DB_TABLERESULT_CREATION = "CREATE TABLE IF NOT EXISTS `TBLRESULT` (\n" +
            "  `resultid` integer PRIMARY KEY,\n" +
            "  `testid` integer,\n" +
            "  `threshold` FLOAT,\n" +
            "  `freqid` integer,\n" +
            "  `ear` integer\n" +
            ");";
    private static String DB_TABLEFREQUENCY_CREATION = "CREATE TABLE IF NOT EXISTS `TBLFREQUENCY` (\n" +
            "  `freqid` integer PRIMARY KEY,\n" +
            "  `value` integer\n" +
            ");";
    private static String DB_TABLEUSER_CREATION = "CREATE TABLE IF NOT EXISTS `TBLUSER` (\n" +
            "  `firstname` varchar,\n" +
            "  `lastname` varchar\n" +
            ");";
    private static String DB_TABLECALIBRATION_CREATION = "CREATE TABLE IF NOT EXISTS `TBLCALIBRATION` (\n" +
            "   `freqid` integer PRIMARY KEY, \n" +
            "   `maxoutput` FLOAT\n" +
            ");";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        currentDB = db;
        currentDB.execSQL(DB_TABLETEST_CREATION);
        currentDB.execSQL(DB_TABLESETTINGS_CREATION);
        currentDB.execSQL(DB_TABLETESTENTRIES_CREATION);
        currentDB.execSQL(DB_TABLERESULT_CREATION);
        currentDB.execSQL(DB_TABLEFREQUENCY_CREATION);
        currentDB.execSQL(DB_TABLEUSER_CREATION);
        currentDB.execSQL(DB_TABLECALIBRATION_CREATION);
        if(!checkIfInitialized()) {
            initializeValues();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo
    }

    @Override
    public SQLiteDatabase getWritableDatabase()
    {
        if(currentDB != null){
            return currentDB;
        }
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase()
    {
        if(currentDB != null){
            return currentDB;
        }
        return super.getReadableDatabase();
    }

    /**
     * checks if initial values are set up
     */
    private boolean checkIfInitialized() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM TBLFREQUENCY", null);
        return res.getCount() > 0;
    }

    /**
     * sets up initial and static values for tables
     */
    private void initializeValues() {

        //setup frequencies
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", 250);
        contentValues.put("freqid", 0);
        db.insert("TBLFREQUENCY", null, contentValues);
        contentValues.put("value", 500);
        contentValues.put("freqid", 1);
        db.insert("TBLFREQUENCY", null, contentValues);
        contentValues.put("value", 1000);
        contentValues.put("freqid", 2);
        db.insert("TBLFREQUENCY", null, contentValues);
        contentValues.put("value", 2000);
        contentValues.put("freqid", 3);
        db.insert("TBLFREQUENCY", null, contentValues);
        contentValues.put("value", 3000);
        contentValues.put("freqid", 4);
        db.insert("TBLFREQUENCY", null, contentValues);
        contentValues.put("value", 4000);
        contentValues.put("freqid", 5);
        db.insert("TBLFREQUENCY", null, contentValues);
        contentValues.put("value", 6000);
        contentValues.put("freqid", 6);
        db.insert("TBLFREQUENCY", null, contentValues);
        contentValues.put("value", 8000);
        contentValues.put("freqid", 7);
        db.insert("TBLFREQUENCY", null, contentValues);
        //setup initial settings
        contentValues = new ContentValues();
        contentValues.put("startdb", 40);
        contentValues.put("firsttime", true);
        contentValues.put("doctor", "Hans Hansen");
        db.insert("TBLSETTINGS", null, contentValues);
    }
}
