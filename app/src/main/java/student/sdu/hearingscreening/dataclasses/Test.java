package student.sdu.hearingscreening.dataclasses;

/**
 * Created by Bogs on 17-04-2017.
 */

public class Test {

    private int testNo;
    private String date;

    public Test(int testNo, String date) {
        this.testNo = testNo;
        this.date = date;
    }
    public int getTestNo() {
        return testNo;
    }

    public String getDate() {
        return date;
    }

}
