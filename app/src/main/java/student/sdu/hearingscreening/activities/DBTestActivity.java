package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.databasemanagement.DBHelper;

public class DBTestActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
    DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        dbh = new DBHelper(HearingScreeningApplication.getContext());
        btn = (Button)findViewById(R.id.btn_dbtest);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDb();
            }
        });
        tv = (TextView)findViewById(R.id.tv_dbtest);
        tv.setMovementMethod(new ScrollingMovementMethod());

    }
    @Override
    public void onBackPressed()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        DBTestActivity.this.startActivity(mainIntent);
        DBTestActivity.this.finish();
    }

    private void testDb() {
        tv.setText("");
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM TBLTEST", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            String data = "";
            while (!res.isAfterLast()) {
                data += "TEST ID: " + res.getInt(res.getColumnIndex("testid")) + "\n";
                data += "DATE: " + res.getString(res.getColumnIndex("date")) + "\n";
                res.moveToNext();
            }
            tv.setText("TBLTEST\n" + data);
        }
        res = db.rawQuery("SELECT * FROM TBLSETTINGS", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            String data = "";
            while (!res.isAfterLast()) {
                data += "STARTDB: " + res.getInt(res.getColumnIndex("startdb")) + "\n";
                data += "FIRSTTIME: " + res.getString(res.getColumnIndex("firsttime")) + "\n";
                data += "DOCTOR: " + res.getString(res.getColumnIndex("doctor")) + "\n";
                res.moveToNext();
            }
            tv.setText(tv.getText() + "TBLSETTINGS\n" + data);
        }
        res = db.rawQuery("SELECT * FROM TBLTESTENTRIES", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            String data = "";
            while (!res.isAfterLast()) {
                data += "TEST ID: " + res.getInt(res.getColumnIndex("testid")) + "\n";
                data += "DECIBEL: " + res.getInt(res.getColumnIndex("decibel")) + "\n";
                data += "ANSWER: " + res.getString(res.getColumnIndex("answer")) + "\n";
                data += "SEQUENCEID: " + res.getInt(res.getColumnIndex("sequenceid")) + "\n";
                data += "ENTRYID: " + res.getInt(res.getColumnIndex("entryid")) + "\n";
                data += "EAR: " + res.getInt(res.getColumnIndex("ear")) + "\n";
                res.moveToNext();
            }
            tv.setText(tv.getText() + "TBLTESTENTRIES\n" + data);
        }
            res = db.rawQuery("SELECT * FROM TBLRESULT", null);
            if (res.getCount() > 0) {
                res.moveToFirst();
                String data = "";
                while (!res.isAfterLast()) {
                    data += "TEST ID: " + res.getInt(res.getColumnIndex("testid")) + "\n";
                    data += "RESULT ID: " + res.getInt(res.getColumnIndex("resultid")) + "\n";
                    data += "THRESHOLD: " + res.getInt(res.getColumnIndex("threshold")) + "\n";
                    data += "FREQ ID: " + res.getInt(res.getColumnIndex("freqid")) + "\n";
                    data += "EAR: " + res.getInt(res.getColumnIndex("ear")) + "\n";
                    res.moveToNext();
                }
                tv.setText(tv.getText() + "TBLRESULT\n" + data);
            }
        res = db.rawQuery("SELECT * FROM TBLFREQUENCY", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            String data = "";
            while (!res.isAfterLast()) {
                data += "FREQ ID: " + res.getInt(res.getColumnIndex("freqid")) + "\n";
                data += "VALUE: " + res.getInt(res.getColumnIndex("value")) + "\n";
                res.moveToNext();
            }
            tv.setText(tv.getText() + "TBLFREQUENCY\n" + data);
        }
        res = db.rawQuery("SELECT * FROM TBLUSER", null);
        if (res.getCount() > 0) {
            res.moveToFirst();
            String data = "";
            while (!res.isAfterLast()) {
                data += "FIRST NAME: " + res.getString(res.getColumnIndex("firstname")) + "\n";
                data += "LAST NAME: " + res.getString(res.getColumnIndex("lastname")) + "\n";
                res.moveToNext();
            }
            tv.setText(tv.getText() + "TBLUSER\n" + data);
        }
        }
    }
