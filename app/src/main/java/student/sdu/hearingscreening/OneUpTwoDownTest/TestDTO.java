package student.sdu.hearingscreening.OneUpTwoDownTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 13-03-2017.
 */

public class TestDTO
{
    // Integer frequency
    private Map<Integer, ArrayList<TestEntry>> rightEarEntries;
    private Map<Integer, ArrayList<TestEntry>> leftEarEntries;
    // Integer frequency, Integer dbhl;
    private Map<Integer, Integer> resultRightEar;
    private Map<Integer, Integer> resultLeftEar;

    public TestDTO()
    {
        rightEarEntries = new HashMap<>();
        leftEarEntries = new HashMap<>();
        resultRightEar = new HashMap<>();
        resultLeftEar = new HashMap<>();
    }

    public void addEntryToRightEar(int frequency, ArrayList<TestEntry> entries)
    {
        addEntryToEar(true, frequency, entries);
    }

    public void addEntryToLeftEar(int frequency, ArrayList<TestEntry> entries)
    {
        addEntryToEar(false, frequency, entries);
    }

    private void addEntryToEar (Boolean ear, int frequency, ArrayList<TestEntry> entries)
    {
        if (ear)
        {
            rightEarEntries.put(frequency, entries);
        }
        else
        {
            leftEarEntries.put(frequency, entries);
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

    public Map<Integer, ArrayList<TestEntry>> getRightEarEntries() {
        return rightEarEntries;
    }

    public Map<Integer, ArrayList<TestEntry>> getLeftEarEntries() {
        return leftEarEntries;
    }

    public Map<Integer, Integer> getResultRightEar() {
        return resultRightEar;
    }

    public Map<Integer, Integer> getResultLeftEar() {
        return resultLeftEar;
    }
}
