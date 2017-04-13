package student.sdu.hearingscreening.dataclasses;

/**
 * Created by Bogs on 20-03-2017.
 */

public class Result {

    private float threshold;
    private int ear;
    private String frequency; // Make int?
    private int frequencyId;

    public Result(float threshold, int ear, String frequency, int frequencyId) {
        this.threshold = threshold;
        this.ear = ear;
        this.frequency = frequency;
        this.frequencyId = frequencyId;
    }

    public float getThreshold() {
        return threshold;
    }

    public int getEar() {
        return ear;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getFrequencyId() {
        return frequencyId;
    }
}
