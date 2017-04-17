package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.calibration.CalibrationProcessor;

public class CalibrateActivity extends AppCompatActivity {
    private CalibrationProcessor cp;
    private MediaPlayer mp;
    private float calibrationVolume;
    private float cSteps = 0f;
    private float pSteps = 8f;

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

        HearingScreeningApplication.setMusicStreamVolumeMax();
    }

    /**
     * Called when the user presses the "Enter" button.
     * Gives the decibel value that the user has set in the input field to the CalibrationProcessor,
     * and calls the updateProgressBar method. If the calibration is then done, the GUI is changed to
     * reflect this state and give the user the option to move on to another activity.
     * @param db
     */
    private void calibrate(float db)
    {
        cp.calibrate(db);
        updateProgressBar();

        if (cp.isCalibrationDone()) //Then finish calibration
        {
            btnEnter.setEnabled(false);
            btnPlaySound.setEnabled(false);

            tvTop.setText("Kalibreringen er fuldført! Din telefon med det brugte headset " +
                    "er nu klar til at foretage høre test når det passer dig");
            tvInput.setText("Tryk på \"Afslut\" for at færdiggøre kalibreringen");
            btnEnter.setText("Afslut");
            btnEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                    CalibrateActivity.this.startActivity(mainIntent);
                    CalibrateActivity.this.finish();
                }
            });

            //Delay "Afslut" button to stop the user from miss clicking away from the activity
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    btnEnter.setEnabled(true);
                }
            }, (1000));

        }
    }

    /**
     * Plays the sound used for testing.
     * When the sound is completed, the necessary GUI elements unlocked.
     * The sound to be played is gotten from the CalibrationProcessor,
     * and is played at the MediaPlayer volume given as a parameter.
     */
    private void playSound(float volume) //todo handle float volumes over 1?
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

    /**
     * todo JavaDoc
     */
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
                if (!etNrInput.getText().toString().equals("")) //Check if input is empty, else Integer.valueOf gives exception and breaks
                {
                    btnEnter.setEnabled(false);
                    etNrInput.setEnabled(false);
                    calibrate(Float.parseFloat(etNrInput.getText().toString()));
                    etNrInput.getText().clear();
                }
                else
                {
                    Toast.makeText(HearingScreeningApplication.getContext(),
                            "Venligst angiv en decibel værdi inden du kan gå videre",
                            Toast.LENGTH_SHORT).show();
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

    /**
     * Updates the progress bar as well as the text in the text view above it.
     */
    private void updateProgressBar()
    {
        cSteps++;
        int progress = Math.round((cSteps/pSteps)*100f);
        progressBar.setProgress(progress);
        tvProgressBar.setText("Kalibreringen er " + progress + "% gennemført");
    }
}
