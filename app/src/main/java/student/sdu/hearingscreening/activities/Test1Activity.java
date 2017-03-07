package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import student.sdu.hearingscreening.R;

import static android.R.id.list;

public class Test1Activity extends AppCompatActivity
{
    private TextView topInfoTv;
    private TextView bottomInfoTv;
    private Button yesBtn;
    private Button noBtn;
    private int testSoundNo = 0;
    private int[] files;
    private MediaPlayer mp;
    private int dbhl = 40;
    private Map<String, ArrayList<Boolean>> entries;
    private float phoneMaxDBOutput = 65.8f;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupTracks();
        setContentView(R.layout.activity_test1);
        entries = new HashMap<>();
        topInfoTv = (TextView) findViewById(R.id.tv_test_info_top);
        bottomInfoTv = (TextView) findViewById(R.id.tv_test_info_bot);
        yesBtn = (Button)findViewById(R.id.btn_yes);
        noBtn = (Button)findViewById(R.id.btn_no);

        mp = MediaPlayer.create(getApplicationContext(), files[0]);

        bottomInfoTv.setVisibility(View.INVISIBLE);
        yesBtn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test();
                }
            });
    }

    private void test()
    {
        topInfoTv.setText(R.string.test_tv_top_text_2);
        bottomInfoTv.setVisibility(View.VISIBLE);
        yesBtn.setEnabled(false);
        noBtn.setEnabled(false);
        playSound();
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(true);
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(false);
            }
        });
    }

    private void answer(Boolean response) {
        if(entries.get(Integer.toString(dbhl))!=null) {
            ArrayList<Boolean> list = entries.get(Integer.toString(dbhl));
            list.add(response);
            entries.put(Integer.toString(dbhl), list);
        } else {
            ArrayList<Boolean> list = new ArrayList();
            list.add(response);
            entries.put(Integer.toString(dbhl), list);
        }
        checkThreeHits(response);
    }

    private void checkThreeHits(Boolean response)
    {
        List<Boolean> value = entries.get(Integer.toString(dbhl));
        int positiveResponses = 0;
        for(Boolean b : value)
        {
            if(b) {positiveResponses++;}
        }
        if(positiveResponses>=3)
        {
            if(testSoundNo>=7) //All testing done, save results
            {
                yesBtn.setVisibility(View.INVISIBLE);
                noBtn.setVisibility(View.INVISIBLE);
                topInfoTv.setText("Du er nu færdig!");
                bottomInfoTv.setText("Tryk hvor som helst for at afslutte testen");
                Toast.makeText(this, "Du har færdiggjort testen!", Toast.LENGTH_SHORT).show();
                System.out.println("Du har færdiggjort testen!");
            }
            else
            {
                testSoundNo++;
                dbhl = 0;
                entries = new HashMap<>();
                test();
            }
        }
        else
        {
            if(response)
            {
                dbhl-=10;
            }
            else
            {
                dbhl+=5;
            }
            test();
        }
    }

    private void playSound()
    {
        if(testSoundNo<files.length)
        {
            mp = MediaPlayer.create(Test1Activity.this, files[testSoundNo]);
            /*Random rand = new Random();
            if(rand.nextBoolean()) {
                mp.setVolume(0, 1);
            } else {
                mp.setVolume(1, 0);
            }*/
            float amplitude = calculateAmplitude(dbhl, phoneMaxDBOutput);
            mp.setVolume(amplitude, amplitude);
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
    }

    private float calculateAmplitude(float dbTarget, float maxPhoneDbOutput)
    {
        float amplitude = (float) Math.pow(10,(dbTarget-maxPhoneDbOutput)/20);
        return amplitude;
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

    @Override
    public void onBackPressed()
    {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        Test1Activity.this.startActivity(mainIntent);
        Test1Activity.this.finish();
    }
}
