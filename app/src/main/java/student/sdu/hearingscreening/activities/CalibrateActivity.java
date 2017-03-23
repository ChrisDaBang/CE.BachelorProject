package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Map;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.calibration.CalibrationProcessor;

public class CalibrateActivity extends AppCompatActivity {
    private CalibrationProcessor cp;
    private MediaPlayer mp;
    private Boolean calibrationOver;
    private Map<Integer, Float> calibrationSettings;
    private float calibrationVolume;

    private Button btnPlaySound;
    private Button btnEnter;
    private TextView tvTop;
    private TextView tvInput;
    private TextView tvProgressBar;
    private ProgressBar progressBar;
    private EditText etNrInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        instantiateGUIFields();
        cp = new CalibrationProcessor();
        calibrationVolume = cp.initialCalibrationSettings();
    }

    private void calibrate(int db)
    {
        calibrationVolume = cp.calibrate(db);

        if (cp.isCalibrationDone()) //Then finish calibration
        {
            btnEnter.setEnabled(false);
            btnPlaySound.setEnabled(false);
            
            btnEnter.setText("Afslut");
            btnEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                    CalibrateActivity.this.startActivity(mainIntent);
                    CalibrateActivity.this.finish();
                }
            });
            
            //// TODO: 20-03-2017 Set the different tvs 
            // Maybe wait 1 sec before unlocking btn?
            btnEnter.setEnabled(true);
        }
    }

    /**
     * Plays the sound used for testing.
     * When the sound is completed, the yes/no buttons are enabled.
     * The frequency is based on a class variable, used to keep the frequencies sequential in testing.
     * The volume is based on the wanted DB Hearing Level, and is found using the calculateAmplitude() method.
     */
    private void playSound(float volume)
    {
        mp = MediaPlayer.create(CalibrateActivity.this, cp.getSoundFile());
        mp.setVolume(volume, volume);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnPlaySound.setEnabled(true);
                btnEnter.setEnabled(true);
                etNrInput.setEnabled(true);
                mp.release();
            }
        });
        mp.start();
    }

    @Override
    public void onBackPressed()
    {
        Intent mainIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        CalibrateActivity.this.startActivity(mainIntent);
        CalibrateActivity.this.finish();
    }

    private void instantiateGUIFields()
    {
        btnPlaySound = (Button) findViewById(R.id.btn_calibrate_playsound);
        btnPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(calibrationVolume);
                btnEnter.setEnabled(false);
                btnPlaySound.setEnabled(false);
            }
        });

        btnEnter = (Button) findViewById(R.id.btn_calibrate_enter);
        btnEnter.setEnabled(false);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!etNrInput.getText().toString().equals("")) //// TODO: 22-03-2017 Fix handling of empty input
                {
                    btnEnter.setEnabled(false);
                    etNrInput.setEnabled(false);
                    calibrate(Integer.valueOf(etNrInput.getText().toString())); // java.lang.NumberFormatException: For input string: ""
                    etNrInput.getText().clear();
                }
                else
                {
                    //Make toast
                }
            }
        });

        tvTop = (TextView) findViewById(R.id.tv_calibrate_top);
        tvInput = (TextView) findViewById(R.id.tv_calibrate_input);

        tvProgressBar = (TextView) findViewById(R.id.tv_calibrate_progress_bar_info);
        progressBar = (ProgressBar) findViewById(R.id.pb_calibrate);

        etNrInput = (EditText) findViewById(R.id.et_calibrate_number_input);
        etNrInput.setEnabled(false);
    }
}
