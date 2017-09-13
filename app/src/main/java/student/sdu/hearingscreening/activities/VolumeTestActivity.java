package student.sdu.hearingscreening.activities;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

public class VolumeTestActivity extends AppCompatActivity {

    int[] files;
    int currentFile;
    MediaPlayer mp;
    EditText dbMax;
    EditText dbInput;
    Button playAt;
    Button playMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_test);
        setupTracks();
        setupFreqPicker();
        setupPlayButtons();
        dbMax =(EditText) findViewById(R.id.et_max);
        dbInput = (EditText) findViewById(R.id.et_inputdb);
    }


    private void setupTracks() {
        files = new int[16];
        files[0] = R.raw.tensectwohundredfifty;
        files[1] = R.raw.tensecfivehundred;
        files[2] = R.raw.tenseconek;
        files[3] = R.raw.tensectwok;
        files[4] = R.raw.tensecthreek;
        files[5] = R.raw.tensecfourk;
        files[6] = R.raw.tensecsixk;
        files[7] = R.raw.tenseceightk;
        files[8] = R.raw.twohundredfifty;
        files[9] = R.raw.fivehundred;
        files[10] = R.raw.onek;
        files[11] = R.raw.twok;
        files[12] = R.raw.threek;
        files[13] = R.raw.fourk;
        files[14] = R.raw.sixk;
        files[15] = R.raw.eightk;
    }


    private float getAmplitude()
    {
        float db;
        float max;
        if(dbInput.getText().toString().equals("")) {
            db = 0;
        }
        else {
            db = Float.parseFloat(dbInput.getText().toString());
        }
        if(dbMax.getText().toString().equals("")) {
            max = 0;
        }
        else {
            max = Float.parseFloat(dbMax.getText().toString());
        }
        float amplitude = (float) Math.pow(10,(db - max)/20);
        return amplitude;
    }

    private void playSound(int file, float amp)
    {
        playAt.setEnabled(false);
        playMax.setEnabled(false);
        mp = MediaPlayer.create(VolumeTestActivity.this, files[file]);
        mp.setVolume(amp, amp);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        playAt.setEnabled(true);
                        playMax.setEnabled(true);
                    }});
                mp.release();
            }
        });
        mp.start();
    }

    private void setupPlayButtons() {
        playAt = (Button)findViewById(R.id.btn_playat);
        playMax = (Button)findViewById(R.id.btn_playmax);

        playAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(currentFile, getAmplitude());
            }
        });

        playMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(currentFile, 1f);
            }
        });

    }
    private void setupFreqPicker() {
        String[] fileNames = new String[16];
        fileNames[0] = "tensec250hz";
        fileNames[1] = "tensec500hz";
        fileNames[2] = "tensec1000hz";
        fileNames[3] = "tensec2000hz";
        fileNames[4] = "tensec3000hz";
        fileNames[5] = "tensec4000hz";
        fileNames[6] = "tensec6000hz";
        fileNames[7] = "tensec8000hz";
        fileNames[8] = "250hz";
        fileNames[9] = "500hz";
        fileNames[10] = "1000hz";
        fileNames[11] = "2000hz";
        fileNames[12] = "3000hz";
        fileNames[13] = "4000hz";
        fileNames[14] = "6000hz";
        fileNames[15] = "8000hz";

        Spinner spin = (Spinner) findViewById(R.id.spn_freqpicker);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fileNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentFile = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Expand dong
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        HearingScreeningApplication.activityIntentSwitch(new MainMenuActivity(), this);
    }
}
