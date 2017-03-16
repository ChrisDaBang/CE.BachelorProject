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

    boolean isCreating = false;
    SQLiteDatabase currentDB = null;

    private static String DB_NAME = "HEARINGSCREENING";
    private static int DB_VERSION = 1;
    private static String DB_CREATION_QUERY = "CREATE TABLE IF NOT EXISTS `TBLTEST` (\n" +
            "  `testid` INTEGER PRIMARY KEY,\n" +
            "  `date` TEXT\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS `TBLSETTINGS` (\n" +
            "  `startdb` INTEGER,\n" +
            "  `firsttime` BOOLEAN,\n" +
            "  `doctor` VARCHAR(255)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS `TBLTESTENTRIES` (\n" +
            "  `testid` INTEGER,\n" +
            "  `decibel` INTEGER,\n" +
            "  `answer` BOOLEAN,\n" +
            "  `sequenceid` INTEGER,\n" +
            "  `entryid` INTEGER PRIMARY KEY,\n" +
            "  `ear` INTEGER\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS `TBLRESULT` (\n" +
            "  `resultid` integer PRIMARY KEY,\n" +
            "  `testid` integer,\n" +
            "  `threshold` integer,\n" +
            "  `freqid` integer\n" +
            "  `ear` integer\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS `TBLFREQUENCY` (\n" +
            "  `freqid` integer PRIMARY KEY,\n" +
            "  `value` integer\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS `TBLUSER` (\n" +
            "  `firstname` varchar,\n" +
            "  `lastname` varchar\n" +
            ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        currentDB = db;
        currentDB.execSQL(DB_CREATION_QUERY);
        if(!checkIfInitialized()) {
            initializeValues();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        // TODO Auto-generated method stub
        if(currentDB != null){
            return currentDB;
        }
        return super.getWritableDatabase();
    }
    @Override
    public SQLiteDatabase getReadableDatabase() {
        // TODO Auto-generated method stub
        if(currentDB != null){
            return currentDB;
        }
        return super.getReadableDatabase();
    }



    public boolean insertDummy() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", new Date().toString());
        db.insert("TBLTEST", null, contentValues);
        return true;
    }

    public String getDummy() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM TBLTEST", null);
        res.moveToFirst();
        String result = res.getString(res.getColumnIndex("testid"));
        return result += res.getString(res.getColumnIndex("date"));

    }

    //checks if initial values are set up
    private boolean checkIfInitialized() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM TBLFREQUENCY", null);
        return res.getCount() == 0;
    }

    //sets up initial and static values for tables
    private void initializeValues() {

        //setup frequencies
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", 250);
        contentValues.put("value", 500);
        contentValues.put("value", 1000);
        contentValues.put("value", 2000);
        contentValues.put("value", 3000);
        contentValues.put("value", 4000);
        contentValues.put("value", 6000);
        contentValues.put("value", 8000);
        db.insert("TBLFREQUENCY", null, contentValues);

        //setup initial settings
        contentValues = new ContentValues();
        contentValues.put("startdb", 40);
        contentValues.put("firsttime", true);
        contentValues.put("doctor", "Hans Hansen");
        db.insert("TBLSETTINGS", null, contentValues);
    }
}
