package student.sdu.hearingscreening.activities;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import student.sdu.hearingscreening.R;

public class VolumeTestActivity extends AppCompatActivity {

    int[] files;
    int currentFile;
    MediaPlayer mp;
    EditText dbMax;
    EditText dbInput;

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
        mp = MediaPlayer.create(VolumeTestActivity.this, files[file]);
        mp.setVolume(amp, amp);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        mp.start();
    }

    private void setupPlayButtons() {
        Button but = (Button)findViewById(R.id.btn_playat);
        Button but2 = (Button)findViewById(R.id.btn_playmax);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(currentFile, getAmplitude());
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(currentFile, 1f);
            }
        });

    }
    private void setupFreqPicker() {
        String[] fileNames = new String[8];
        fileNames[0] = "250hz";
        fileNames[1] = "500hz";
        fileNames[2] = "1000hz";
        fileNames[3] = "2000hz";
        fileNames[4] = "3000hz";
        fileNames[5] = "4000hz";
        fileNames[6] = "6000hz";
        fileNames[7] = "8000hz";

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
}
