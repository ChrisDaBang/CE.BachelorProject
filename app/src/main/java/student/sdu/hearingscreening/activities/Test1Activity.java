package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import student.sdu.hearingscreening.R;

public class Test1Activity extends AppCompatActivity {
    int presses = 0;
    int[] files;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTracks();
        setContentView(R.layout.activity_test1);
        Button but = (Button)findViewById(R.id.btn_yes);
        mp = MediaPlayer.create(getApplicationContext(), files[0]);
        but.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSound();
                }
            });
    }
    private void playSound() {
        if(!mp.isPlaying()) {
        if(presses<files.length) {
            mp = MediaPlayer.create(Test1Activity.this, files[presses]);
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
