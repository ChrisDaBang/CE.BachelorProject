package student.sdu.hearingscreening.resultAnalysis;

/**
 * Created by Chris on 10-04-2017.
 */

public class ComparativeAnalyzer
{
    private int same = 0;
    private int positive = 0;
    private int negative = 0;
    private int mediumNegative = 0;
    private int badNegative = 0;
    private String negativeRes = "";
    private String worrysomeNegativeRes = "";

    public ComparativeAnalyzer(float[] comparativeResultArray)
    {
        analyze(comparativeResultArray);
    }

    private void analyze(float[] comparativeResultArray)
    {
        for (int freqNo = 0; freqNo <= 7; freqNo++)
        {
            float res = comparativeResultArray[freqNo];
            if (res == 0)
            {
                same++;
            }
            else if (res > 0) //an improvement
            {
                //positive++;
                same++; // // TODO: 10-04-2017 Handling of "improved results" needs clarified on a requirement level to redo this implementation
            }
            else if (res < 0) //potential loss
            {
                negative++;
                negativeRes += "Frekvens " +freqNo+ " hørte du " + res + " db dårligere.\n";
                if (res == -10)
                {
                    mediumNegative++;
                    worrysomeNegativeRes += "Frekvens " +freqNo+ " hørte du dårligere med " + res + " db.\n";
                }
                if (res <= -15)
                {
                    badNegative++;
                    worrysomeNegativeRes += "Frekvens " +freqNo+ " hørte du betydeligt dårlige med " + res + " db.\n";
                }
            }
            else {} //We fucked up
        }
    }

    public int getSame() {
        return same;
    }

    public int getPositive() {
        return positive;
    }

    public int getNegative() {
        return negative;
    }

    public int getMediumNegative() {
        return mediumNegative;
    }

    public int getBadNegative() {
        return badNegative;
    }

    public String getNegativeRes() {
        return negativeRes;
    }

    public String getWorrysomeNegativeRes() {
        return worrysomeNegativeRes;
    }
}
