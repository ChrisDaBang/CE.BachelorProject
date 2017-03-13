package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Test1Activity extends AppCompatActivity
{
    private TextView topInfoTv;
    private TextView bottomInfoTv;
    private Button yesBtn;
    private Button noBtn;
    private int[] files;
    private int dbhl;
    private int startDBHL = 40;
    private int testSoundNo = 0;
    private float phoneMaxDBOutput = 65.8f;
    private MediaPlayer mp;
    private Map<String, ArrayList<Boolean>> entries;


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

        dbhl = startDBHL;
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

    /**
     *
     */
    private void test()
    {
        topInfoTv.setText(R.string.test_tv_top_text_2);
        bottomInfoTv.setVisibility(View.VISIBLE);
        yesBtn.setEnabled(false);
        noBtn.setEnabled(false);

        //Wait 1 second before playing the sound
        try {Thread.sleep(1000);}
        catch (InterruptedException e) {e.printStackTrace();}
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

    /**
     * Takes a "yes/no" answer in the form of true or false.
     * Adds the response to the entry that corresponds the current DB Hearing Level,
     * then calls the checkThreeHits() method.
     * @param response
     */
    private void answer(Boolean response)
    {
        // Current DB level already has an entry in entries
        if(entries.get(Integer.toString(dbhl))!=null)
        {
            ArrayList<Boolean> list = entries.get(Integer.toString(dbhl));
            list.add(response);
            entries.put(Integer.toString(dbhl), list);
        }
        // Current DB level does NOT have an entry in entries
        else
        {
            ArrayList<Boolean> list = new ArrayList();
            list.add(response);
            entries.put(Integer.toString(dbhl), list);
        }

        checkThreeHits(response);
    }

    /**
     * Checks the entries for the current DB Hearing Level (dbhl) and counts the positive results.
     * If there are 3, then checks if all the frequencies have been tested.
     * If all frequencies have been tested, the test is ended.
     * Else the next frequency is tested.
     * If there is not 3 positive results, the test goes on at the same frequency at a new dbhl.
     * @param response
     */
    private void checkThreeHits(Boolean response)
    {
        //Get entries for current DB Hearing Level (dbhl), count the amount of positive responses
        List<Boolean> value = entries.get(Integer.toString(dbhl));
        int positiveResponses = 0;
        for(Boolean b : value)
        {
            if(b) {positiveResponses++;}
        }

        //If 3 positive responses at current level, chech if all frequencies have been tested.
        //if all are tested, end the test. Else continue the test at the next frequency.
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
                dbhl = startDBHL;
                entries = new HashMap<>();
                test();
            }
        }
        //If there is not 3 positive responses at the current DB Hearing Level (dbhl), the test
        // continues at the same frequency at a new dbhl.
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

    /**
     * Plays the sound used for testing.
     * When the sound is completed, the yes/no buttons are enabled.
     * The frequency is based on a class variable, used to keep the frequencies sequential in testing.
     * The volume is based on the wanted DB Hearing Level, and is found using the calculateAmplitude() method.
     */
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

    /**
     * Calculates the amplitude needed to hit the wanted DB.
     * Takes a float dbTarget as the wanted DB Hearing Level.
     * Takes a float maxPhoneOutput, which should be the DB value produced by the phone at max volume.
     * @param dbTarget
     * @param maxPhoneDbOutput
     * @return the amplitude as a float
     */
    private float calculateAmplitude(float dbTarget, float maxPhoneDbOutput)
    {
        float amplitude = (float) Math.pow(10,(dbTarget-maxPhoneDbOutput)/20);
        return amplitude;
    }

    /**
     * Instantiates the files[] array with the sound resources.
     */
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
