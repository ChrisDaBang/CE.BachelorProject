package student.sdu.hearingscreening.OneUpTwoDownTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 13-03-2017.
 */

public class TestDTO
{
    // Integer freqeuncy, Map<Integer dbhl, answers.
    private Map<Integer, Map<Integer, ArrayList<Boolean>>> rightEarEntries;
    private Map<Integer, Map<Integer, ArrayList<Boolean>>> leftEarEntries;
    // Integer freqeuncy, Integer dbhl;
    private Map<Integer, Integer> resultRightEar;
    private Map<Integer, Integer> resultLeftEar;

    public TestDTO()
    {
        rightEarEntries = new HashMap<>();
        leftEarEntries = new HashMap<>();
        resultRightEar = new HashMap<>();
        resultLeftEar = new HashMap<>();
    }

    public void addEntryToRightEar(int frequency, Map<Integer, ArrayList<Boolean>> entry)
    {
        addEntryToEar(true, frequency, entry);
    }

    public void addEntryToLeftEar(int frequency, Map<Integer, ArrayList<Boolean>> entry)
    {
        addEntryToEar(false, frequency, entry);
    }

    private void addEntryToEar (Boolean ear, int frequency, Map<Integer, ArrayList<Boolean>> entry)
    {
        if (ear)
        {
            rightEarEntries.put(frequency, entry);
        }
        else
        {
            leftEarEntries.put(frequency, entry);
        }
    }

    public void addResultRightEar(int frequency, int dbhl)
    {
        addResult(true, frequency, dbhl);
    }

    public void addResultLeftEar(int frequency, int dbhl)
    {
        addResult(false, frequency, dbhl);
    }

    private void addResult(boolean ear, int frequency, int dbhl)
    {
        if (ear)
        {
            resultRightEar.put(frequency, dbhl);
        }
        else
        {
            resultLeftEar.put(frequency, dbhl);
        }
    }

    public Map<Integer, Map<Integer, ArrayList<Boolean>>> getRightEarEntries() {
        return rightEarEntries;
    }

    public Map<Integer, Map<Integer, ArrayList<Boolean>>> getLeftEarEntries() {
        return leftEarEntries;
    }

    public Map<Integer, Integer> getResultRightEar() {
        return resultRightEar;
    }

    public Map<Integer, Integer> getResultLeftEar() {
        return resultLeftEar;
    }
}
