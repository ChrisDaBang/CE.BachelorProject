package student.sdu.hearingscreening;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Random;

import static android.media.AudioManager.STREAM_MUSIC;

public class Test1Activity extends AppCompatActivity {
    int presses = 0;
    int[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTracks();
        setContentView(R.layout.activity_test1);
        Button but = (Button)findViewById(R.id.test1buttonyes);
        but.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSound();
                }
            });
    }
    private void playSound() {
        if(presses<files.length) {
            MediaPlayer mp = MediaPlayer.create(Test1Activity.this, files[presses]);
            Random rand = new Random();
            if(rand.nextBoolean()) {
                mp.setVolume(0, 1);
            } else {
                mp.setVolume(1, 0);
            }
            mp.start();
            presses++;
        } else {
            Intent mainIntent = new Intent(getApplicationContext(), Test2Activity.class);
            Test1Activity.this.startActivity(mainIntent);
            Test1Activity.this.finish(); //Should you finish? Who knows
        }
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
    }
