package student.sdu.hearingscreening.OneUpTwoDownTest;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;
import student.sdu.hearingscreening.databasemanagement.TestDAO;
import student.sdu.hearingscreening.dataclasses.TestDTO;

/**
 * Created by Chris on 13-03-2017.
 */

public class OneUpTwoDownTest
{
    private int[] files;
    private int dbhl;
    private int startDBHL = 40;
    private int testFreqNo = 0;
    private Map<Integer, Float> phoneMaxDBOutput;
    private int sequenceTracker = 0;
    private ArrayList<TestEntry> entries;
    private int ear; // 0 Left, 1 Right
    private TestDAO testDAO;
    private TestDTO testDTO;
    private boolean currentlyInCatchTrial;

    public OneUpTwoDownTest()
    {
        testDAO = new TestDAO(HearingScreeningApplication.getContext());
        phoneMaxDBOutput = testDAO.getCalibrationValues();
        entries = new ArrayList();
        setupTracks();
        testDTO = new TestDTO();
        dbhl = startDBHL;
        ear = 0;
        currentlyInCatchTrial = false;
    }

    /**
     * Takes a "yes/no" answer in the form of true or false.
     * Creates a TestEntry object with the response, and adds it to the entries list,
     * then calls the checkThreeHits() method.
     * @param response
     */
    public boolean answer(Boolean response)
    {
        //1 in 25 chance to enter catchtrial
            Random rand = new Random();
            currentlyInCatchTrial = rand.nextInt(25) == 24;

            sequenceTracker++;
            TestEntry entry = new TestEntry(dbhl, response, sequenceTracker, currentlyInCatchTrial);
            entries.add(entry);

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
        int positiveResponses = 0;
        for(TestEntry entry : entries)
        {
            if(entry.getDbhl() == this.dbhl) {
                if(entry.getAnswer() && !entry.isCatchTrial()) {
                    positiveResponses++;
                }
            }
        }

        //If 3 positive responses at current level, check if all frequencies have been tested.
        //if all are tested, end the test. Else continue the test at the next frequency.
        if(positiveResponses>=3)
        {
            if(ear == 1) //After a right ear test the next frequency is used, and we go back to the left ear.
            {
                testDTO.addEntryToRightEar(testFreqNo, entries);
                testDTO.addResultRightEar(testFreqNo, dbhl);
                testFreqNo++;
                ear = 0;
                currentlyInCatchTrial = false;
            }
            else //after a left ear test, go to right ear.
            {
                testDTO.addEntryToLeftEar(testFreqNo, entries);
                testDTO.addResultLeftEar(testFreqNo, dbhl);
                ear = 1;
                currentlyInCatchTrial = false;
            }

            if(testFreqNo > 7 && ear == 0) //All testing done, save results
            {
                testOver = true;
                TestDAO dao = new TestDAO(HearingScreeningApplication.getContext());
                dao.saveTest(testDTO);
            }
            else //If not, continue testing
            {
                dbhl = startDBHL;
                entries = new ArrayList();
                sequenceTracker = 0;
                currentlyInCatchTrial = false;
            }
        }
        //If there are not 3 positive responses at the current DB Hearing Level (dbhl), the test
        // continues at the same frequency at a new dbhl.
        else
        {
            if(currentlyInCatchTrial) {
                //no change
            }
            else if(response)
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
     * Uses a float dbTarget as the wanted DB Hearing Level.
     * Uses a float maxPhoneOutput, which should be the DB value produced by the phone at max volume.
     * @return the amplitude as a float
     */
    public float getAmplitude()
    {
        float amplitude = (float) Math.pow(10,(dbhl-phoneMaxDBOutput.get(testFreqNo))/20);
        return amplitude;
    }

    public int getSoundFile()
    {
        return files[testFreqNo];
    }
    public int getEar() {
        return  ear;
    }

    /**
     * For quick database testing only
     */
    public void testTest() {
        for(int i = 0; i<16; i++) {
            answer(true);
            answer(false);
            answer(false);
            answer(true);
            answer(false);
            answer(false);
            answer(true);
        }
        testDAO.saveTest(testDTO);

    }

    /**
     * Will be empty if no calibration data could be found in the database.
     * Should this be true, then there is no grounds for a proper screening,
     * and one should not be performed.
     * @return
     */
    public boolean isCalibrationEmpty()
    {
        return phoneMaxDBOutput.isEmpty();
    }
}
