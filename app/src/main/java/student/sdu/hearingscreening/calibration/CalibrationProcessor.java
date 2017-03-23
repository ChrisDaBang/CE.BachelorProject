package student.sdu.hearingscreening.calibration;

import java.util.HashMap;
import java.util.Map;

import student.sdu.hearingscreening.R;

/**
 * Created by Chris on 20-03-2017.
 */

public class CalibrationProcessor
{
    private int calibrationFreqNo;
    private float calibrationVolume;
    private boolean calibrationOver;
    private int[] files;
    private Map<Float, Map<Integer, Integer>> getFreqMapAtVolume;

    // Maps holding the DB value gotten from playing the different frequencies at different volumes.
    private Map<Integer, Integer> frequencyAtMax;
    private Map<Integer, Integer> frequencyAtSeventyFive;
    private Map<Integer, Integer> frequencyAtFifty;
    private Map<Integer, Integer> frequencyAtTwentyFive;

    /**
     *
     */
    public CalibrationProcessor()
    {
        calibrationFreqNo = 0;
        calibrationVolume = initialCalibrationSettings();
        calibrationOver = false;
        setupFrequencyMaps();
        initFreqAtVolumeGetter();
        setupTracks();
    }

    /**
     *
     * @param db
     * @return
     */
    public float calibrate(int db)
    {
        getFreqMapAtVolume.get(calibrationVolume).put(calibrationFreqNo, db);

        if (calibrationVolume == 0.25f)
        {
            if (calibrationFreqNo == 7)
            {
                calibrationOver = true;
                soutMapData();
            }
            calibrationFreqNo++;
            calibrationVolume = 1f;
        }
        else if (calibrationVolume == 0.50f)
        {
            calibrationVolume = 0.25f;
        }
        else if (calibrationVolume == 0.75f)
        {
            calibrationVolume = 0.50f;
        }
        else if (calibrationVolume == 1f)
        {
            calibrationVolume = 0.75f;
        }
        else
        {
            //If we get here, then something went wrong in the code.
            System.out.println("Du har lavet en fejl");
        }


        return calibrationVolume;
    }

    /**
     *
     * @return
     */
    public float initialCalibrationSettings()
    {
        return 1f;
    }


    public Boolean isCalibrationDone()
    {
        return calibrationOver;
    }

    /**
     *
     */
    private void setupFrequencyMaps()
    {
        frequencyAtMax = new HashMap<>();
        frequencyAtSeventyFive = new HashMap<>();
        frequencyAtFifty = new HashMap<>();
        frequencyAtTwentyFive = new HashMap<>();
    }


    /**
     * Instantiates the files[] array with the sound resources.
     */
    private void setupTracks()
    {
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

    public int getSoundFile()
    {
        return files[calibrationFreqNo];
    }

    private void initFreqAtVolumeGetter()
    {
        getFreqMapAtVolume = new HashMap<>();
        getFreqMapAtVolume.put(1f, frequencyAtMax);
        getFreqMapAtVolume.put(0.75f, frequencyAtSeventyFive);
        getFreqMapAtVolume.put(0.5f, frequencyAtFifty);
        getFreqMapAtVolume.put(0.25f, frequencyAtTwentyFive);
    }

    private void soutMapData()
    {
        System.out.println("AtMax:\n");
        System.out.println(frequencyAtMax);
        System.out.println("At75:\n");
        System.out.println(frequencyAtSeventyFive);
        System.out.println("At50:\n");
        System.out.println(frequencyAtFifty);
        System.out.println("At25:\n");
        System.out.println(frequencyAtTwentyFive);
    }
}
