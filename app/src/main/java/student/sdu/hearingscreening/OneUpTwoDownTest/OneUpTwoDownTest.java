package student.sdu.hearingscreening.OneUpTwoDownTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import student.sdu.hearingscreening.R;

/**
 * Created by Chris on 13-03-2017.
 */

public class OneUpTwoDownTest
{
    private int[] files;
    private int dbhl;
    private int startDBHL = 40;
    private int testFreqNo = 0;
    private float phoneMaxDBOutput = 65.8f;
    private Map<Integer, ArrayList<Boolean>> entries;
    private int ear; // 0 Left, 1 Right
    private TestDTO testDTO;

    public OneUpTwoDownTest()
    {
        entries = new HashMap<>();
        setupTracks();
        testDTO = new TestDTO();
        dbhl = startDBHL;
        ear = 0;
    }

    /**
     * Takes a "yes/no" answer in the form of true or false.
     * Adds the response to the entry that corresponds the current DB Hearing Level,
     * then calls the checkThreeHits() method.
     * @param response
     */
    public boolean answer(Boolean response)
    {
        // Current DB level already has an entry in entries
        if(entries.get(dbhl)!=null)
        {
            ArrayList<Boolean> list = entries.get(dbhl);
            list.add(response);
            entries.put(dbhl, list);
        }
        // Current DB level does NOT have an entry in entries
        else
        {
            ArrayList<Boolean> list = new ArrayList();
            list.add(response);
            entries.put(dbhl, list);
        }

        return checkThreeHits(response);
    }

    /**
     * Checks the entries for the current DB Hearing Level (dbhl) and counts the positive results.
     * If there are 3, then checks if all the frequencies have been tested.
     * If all frequencies have been tested, the test is ended.
     * Else the next frequency is tested.
     * If there is not 3 positive results, the test goes on at the same frequency at a new dbhl.
     * @param response
     */
    private boolean checkThreeHits(Boolean response)
    {
        boolean testOver = false;
        //Get entries for current DB Hearing Level (dbhl), count the amount of positive responses
        List<Boolean> value = entries.get(dbhl);
        int positiveResponses = 0;
        for(Boolean b : value)
        {
            if(b) {positiveResponses++;}
        }

        //If 3 positive responses at current level, chech if all frequencies have been tested.
        //if all are tested, end the test. Else continue the test at the next frequency.
        if(positiveResponses>=3)
        {
            if(ear == 1)
            {
                testDTO.addEntryToRightEar(testFreqNo, entries);
                testDTO.addResultRightEar(testFreqNo, dbhl);
                testFreqNo++;
                ear = 0;
            }
            else
            {
                testDTO.addEntryToLeftEar(testFreqNo, entries);
                testDTO.addResultLeftEar(testFreqNo, dbhl);
                ear = 1;
            }

            if(testFreqNo >=7 && ear == 1) //All testing done, save results
            {
                testOver = true;
            }
            else
            {
                dbhl = startDBHL;
                entries = new HashMap<>();
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
        }
        return testOver;
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

    /**
     * Calculates the amplitude needed to hit the wanted DB.
     * Takes a float dbTarget as the wanted DB Hearing Level.
     * Takes a float maxPhoneOutput, which should be the DB value produced by the phone at max volume.
     * @return the amplitude as a float
     */
    public float getAmplitude()
    {
        float amplitude = (float) Math.pow(10,(dbhl-phoneMaxDBOutput)/20);
        return amplitude;
    }

    public int getSoundFile()
    {
        return files[testFreqNo];
    }
    public int getEar() {
        return  ear;
    }
}
