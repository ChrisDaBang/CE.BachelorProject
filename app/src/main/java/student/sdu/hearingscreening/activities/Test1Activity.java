package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import student.sdu.hearingscreening.OneUpTwoDownTest.OneUpTwoDownTest;
import student.sdu.hearingscreening.OneUpTwoDownTest.TestDAO;
import student.sdu.hearingscreening.R;

public class Test1Activity extends AppCompatActivity
{
    private TextView topInfoTv;
    private TextView bottomInfoTv;
    private Button yesBtn;
    private Button noBtn;
    private OneUpTwoDownTest test;
    private boolean testOver = false;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        topInfoTv = (TextView) findViewById(R.id.tv_test_info_top);
        bottomInfoTv = (TextView) findViewById(R.id.tv_test_info_bot);
        yesBtn = (Button)findViewById(R.id.btn_yes);
        noBtn = (Button)findViewById(R.id.btn_no);

        test = new OneUpTwoDownTest();

        bottomInfoTv.setVisibility(View.INVISIBLE);
        yesBtn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test();
                }
            });
        Button testBtn = (Button)findViewById(R.id.btn_test_test);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.testTest();
            }
        });
    }

    /**
     * //todo
     */
    private void test()
    {
        if(testOver) {
            //end
            //// TODO: 13-03-2017
        } else {
            topInfoTv.setText(R.string.test_tv_top_text_2);
            bottomInfoTv.setVisibility(View.VISIBLE);
            yesBtn.setEnabled(false);
            noBtn.setEnabled(false);

            //Wait 1 second before playing the sound
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSound();
                }
            }, 1000);

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testOver = test.answer(true);
                    test();
                }
            });
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testOver = test.answer(false);
                    test();
                }
            });
        }
    }

    /**
     * Plays the sound used for testing.
     * When the sound is completed, the yes/no buttons are enabled.
     * The frequency is based on a class variable, used to keep the frequencies sequential in testing.
     * The volume is based on the wanted DB Hearing Level, and is found using the calculateAmplitude() method.
     */
    private void playSound()
    {
            mp = MediaPlayer.create(Test1Activity.this, test.getSoundFile());
            float amplitude = test.getAmplitude();
            if(test.getEar() == 0) {
                mp.setVolume(amplitude, 0f);
            } else {
                mp.setVolume(0f, amplitude);
            }
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    yesBtn.setEnabled(true);
                    noBtn.setEnabled(true);
                    mp.release();
                }
            });
            mp.start();
    }

    @Override
    public void onBackPressed()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        Test1Activity.this.startActivity(mainIntent);
        Test1Activity.this.finish();
    }
}
