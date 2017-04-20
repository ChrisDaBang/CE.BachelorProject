package student.sdu.hearingscreening.dataclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import student.sdu.hearingscreening.OneUpTwoDownTest.TestEntry;

/**
 * Created by Chris on 13-03-2017.
 */

public class TestDTO
{
    private Map<Integer, ArrayList<TestEntry>> rightEarEntries;
    private Map<Integer, ArrayList<TestEntry>> leftEarEntries;
    private Map<Integer, Float> resultRightEar;
    private Map<Integer, Float> resultLeftEar;

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

    public void addResultRightEar(int frequency, float dbhl)
    {
        addResult(true, frequency, dbhl);
    }

    public void addResultLeftEar(int frequency, float dbhl)
    {
        addResult(false, frequency, dbhl);
    }

    private void addResult(boolean ear, int frequency, float dbhl)
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

    public Map<Integer, Float> getResultRightEar() {
        return resultRightEar;
    }

    public Map<Integer, Float> getResultLeftEar() {
        return resultLeftEar;
    }
}
