package student.sdu.hearingscreening.OneUpTwoDownTest;

/**
 * Created by Bogs on 16-03-2017.
 */

public class TestEntry {
    private int dbhl;
    private boolean answer;
    private int sequenceId;

    public TestEntry(int dbhl, boolean answer, int sequenceId) {
        this.dbhl = dbhl;
        this.answer = answer;
        this.sequenceId = sequenceId;
    }

    public int getDbhl() {
        return dbhl;
    }

    public boolean getAnswer() {
        return answer;
    }

    public int getSequenceId() {
        return sequenceId;
    }

}
