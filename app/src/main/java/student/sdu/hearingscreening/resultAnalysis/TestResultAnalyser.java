package student.sdu.hearingscreening.resultAnalysis;

import student.sdu.hearingscreening.databasemanagement.ResultManager;
import student.sdu.hearingscreening.dataclasses.Result;
import student.sdu.hearingscreening.resultAnalysis.ComparativeAnalyzer;

/**
 * Created by Chris on 10-04-2017.
 */

public class TestResultAnalyser
{
    private Result[] baseLeftEarResult;
    private Result[] baseRightEarResult;
    private Result[] newLeftEarResult;
    private Result[] newRightEarResult;
    private int freqNo;

    private float[] comparativeResultLeft;
    private float[] comparativeResultRight;
    private String comparativeResponse;
    private String recommendation;
    private String normativeResponse;

    /**
     * Creates a TestResultAnalyser which will analyze the newest test result with the oldest and normative threshold values.
     */
    public TestResultAnalyser()
    {
        freqNo = 0;
        comparativeResultLeft = new float[8];
        comparativeResultRight = new float[8];
        comparativeResponse = "No comparison done";
        normativeResponse = "";
        initBaseEarResults();
        initNewestEarResults();
    }

    /**
     * Creates Creates a TestResultAnalyser which will analyze the test with the given test number,
     * compare its result with the oldest and normative threshold values.
     * @param testNo
     */
    public TestResultAnalyser(int testNo) {
        freqNo = 0;
        comparativeResultLeft = new float[8];
        comparativeResultRight = new float[8];
        comparativeResponse = "No comparison done";
        normativeResponse = "";
        initBaseEarResults();
        initEarResults(testNo);
    }

    /**
     * Uses the ResultManager class to get test results for the oldest test in the database.
     */
    private void initBaseEarResults()
    {
        ResultManager rm = new ResultManager();
        baseLeftEarResult = rm.getBaseResultsForAnalysis(0);
        baseRightEarResult = rm.getBaseResultsForAnalysis(1);
    }

    /**
     * Uses the ResultManager class to get test results for the newest test in the database.
     */
    private void initNewestEarResults()
    {
        ResultManager rm = new ResultManager();
        newLeftEarResult = rm.getLatestResultsForAnalysis(0);
        newRightEarResult = rm.getLatestResultsForAnalysis(1);
    }

    /**
     * Uses the ResultManager class to get the test results corresponding to the given test number
     * @param testNo
     */
    private void initEarResults(int testNo)
    {
        ResultManager rm = new ResultManager();
        newLeftEarResult = rm.getSpecificResultsForAnalysis(testNo, 0);
        newRightEarResult = rm.getSpecificResultsForAnalysis(testNo, 1);
    }

    /**
     * Analyse the test results, creating a response to a comparison on the users test results (comparativeResponse),
     * a comparison against the norm (normativeResponse), and a recommendation for the user, that can be gotten with
     * the corresponding get methods().
     */
    public void analyseResult()
    {
        ResultManager rm = new ResultManager();
        System.out.println("I will now check if comparison is possible");
        if(rm.checkIfComparisonPossible()) {
            System.out.println("Comparison was possible, will now enter for loop");
            for (freqNo = 0; freqNo < 7; freqNo++) {
                System.out.println("In the for loop at freq: " + freqNo);
                comparativeResultLeft[freqNo] = baseLeftEarResult[freqNo].getThreshold() - newLeftEarResult[freqNo].getThreshold();
                comparativeResultRight[freqNo] = baseRightEarResult[freqNo].getThreshold() - newRightEarResult[freqNo].getThreshold();
                System.out.println(comparativeResultLeft[freqNo]);
                System.out.println(comparativeResultRight[freqNo]);
            }
            System.out.println("Now calling make comparativeResponse and Recommendation");
            makeComparativeResponseAndRecommendation();
        } else {
            comparativeResponse = "Ingen sammenligning mulig...";
            recommendation = "Ingen forslag...";
        }
        if(rm.checkIfAtleastOne()) {
            makeNormativeComparison();
        } else {
            normativeResponse = "Tag en test for at få en normativ evaluering...";
        }
    }

    /**
     *
     */
    private void makeComparativeResponseAndRecommendation()
    {
        ComparativeAnalyzer leftAnalysis = new ComparativeAnalyzer(comparativeResultLeft);
        ComparativeAnalyzer rightAnalysis = new ComparativeAnalyzer(comparativeResultRight);

        // TODO: 10-04-2017 See ComparativeAnalyzer class
        if (leftAnalysis.getSame() == 8 && rightAnalysis.getSame() == 8)
        {
            comparativeResponse = "Der er ingen ændring i din hørelse";
        }
        else if((leftAnalysis.getNegative() > 0 || rightAnalysis.getNegative() > 0)
                && (leftAnalysis.getMediumNegative() == 0 && rightAnalysis.getMediumNegative() == 0)
                && (leftAnalysis.getBadNegative() == 0 && rightAnalysis.getBadNegative() == 0))
        {
            comparativeResponse = "Der er en lille ændring i dine nye resultater på:\n";
            if (leftAnalysis.getNegative() > 0)
            {
                comparativeResponse += "Venstre øret:\n" + leftAnalysis.getNegativeRes();
            }
            if (rightAnalysis.getNegative() > 0)
            {
                comparativeResponse += "Højre øret:\n" + rightAnalysis.getNegativeRes();
            }

            recommendation = "Denne ændring er lille, og kan skyldes andre ting der påvirker din tagen af testen.\n" +
                    "Ændringen er ikke grund til alarm, hvis det er første gang den er observeret.\n" +
                    "Hvis ændringen observeres flere gange i fremtidige test, kan læge kontaktes om dette.";
        }
        else if ((leftAnalysis.getMediumNegative() > 0 || rightAnalysis.getMediumNegative() > 0)
                && (leftAnalysis.getBadNegative() == 0 && rightAnalysis.getBadNegative() == 0))
            {
            comparativeResponse = "Der er en ændring i dine nye resultater på:\n";
            if (leftAnalysis.getMediumNegative() > 0)
            {
                comparativeResponse += "Venstre øret:\n" + leftAnalysis.getWorrysomeNegativeRes();
            }
            if (rightAnalysis.getMediumNegative() > 0)
            {
                comparativeResponse += "Højre øret:\n" + rightAnalysis.getWorrysomeNegativeRes();
            }

            recommendation = "Hvis der har været forstyrelser under testen, bedes du tage den igen.\n" +
                    "Hvis denne samme ændring observeres flere gange, bør din læge kontaktes om dette.";
        }
        else if (leftAnalysis.getBadNegative() > 0 || rightAnalysis.getBadNegative() > 0)
        {
            comparativeResponse = "Der er en betydelig ændring i dine nye resultater på:\n";
            if (leftAnalysis.getBadNegative() > 0)
            {
                comparativeResponse += "Venstre øret:\n" + leftAnalysis.getWorrysomeNegativeRes();
            }
            if (rightAnalysis.getBadNegative() > 0)
            {
                comparativeResponse += "Højre øret:\n" + rightAnalysis.getWorrysomeNegativeRes();
            }

            recommendation = "Hvis du ikke med sikkerhed ved, at dette kunne være forudsaget af en " +
                    "forstyrrelse under din tagen af testen, og det ikke er første gang denne ændring er der, " +
                    "bør du kontakte din læge snarest." +
                    "\nHvis dette er første gang du ser denne ændring, bedes du gentage testen.";
        }
        else if (leftAnalysis.getNegative() > 0 || rightAnalysis.getNegative() > 0)
        {
            comparativeResponse = "Der er en lille ændring i dine nye resultater på:\n";
            if (leftAnalysis.getNegative() > 0)
            {
                comparativeResponse += "Venstre øret:\n" + leftAnalysis.getNegativeRes();
            }
            if (rightAnalysis.getNegative() > 0)
            {
                comparativeResponse += "Højre øret:\n" + rightAnalysis.getNegativeRes();
            }
        }
        else
        {
            comparativeResponse = "Jeg har forsøgt en sammenligning, men faldt uden for antagede cases";
        }
    }

    /**
     *
     */
    private void makeNormativeComparison()
    {
        int leftScore = 0;
        int rightScore = 0;
        String leftResult = "";
        String rightResult = "";
        for(Result r : newLeftEarResult) {
            float threshold = r.getThreshold();
            if(threshold < 25) {
                //do nothing
            } else if (threshold >= 25 && threshold < 40) {
                leftScore += 1;
                leftResult += "\nPå frekvens: " + r.getFrequency() + " har du mild høretab";
            } else if (threshold >= 40 && threshold < 55) {
                leftScore += 2;
                leftResult += "\nPå frekvens: " + r.getFrequency() + " har du moderat høretab";
            } else if (threshold >= 55 && threshold < 70) {
                leftScore += 3;
                leftResult += "\nPå frekvens: " + r.getFrequency() + " har du moderat alvorligt høretab";
            } else if (threshold >= 70 && threshold < 90) {
                leftScore += 4;
                leftResult += "\nPå frekvens: " + r.getFrequency() + " har du alvorligt høretab";
            } else if (threshold >= 90) {
                leftScore += 5;
                leftResult += "\nPå frekvens: " + r.getFrequency() + " har du slemt høretab";
            }
        }
        for(Result r : newRightEarResult) {
            float threshold = r.getThreshold();
            if(threshold < 25) {
                //do nothing
            } else if (threshold >= 25 && threshold < 40) {
                rightScore += 1;
                rightResult += "\nPå frekvens: " + r.getFrequency() + " har du mild høretab";
            } else if (threshold >= 40 && threshold < 55) {
                rightScore += 2;
                rightResult += "\nPå frekvens: " + r.getFrequency() + " har du moderat høretab";
            } else if (threshold >= 55 && threshold < 70) {
                rightScore += 3;
                rightResult += "\nPå frekvens: " + r.getFrequency() + " har du moderat alvorligt høretab";
            } else if (threshold >= 70 && threshold < 90) {
                rightScore += 4;
                rightResult += "\nPå frekvens: " + r.getFrequency() + " har du alvorligt høretab";
            } else if (threshold >= 90) {
                rightScore += 5;
                rightResult += "\nPå frekvens: " + r.getFrequency() + " har du slemt høretab";
            }
        }
        if(leftScore > 0) {
            leftResult = "Ventre øre: " + leftResult;
        }
        if(rightScore > 0) {
            rightResult = "Højre øre: " + rightResult;
        }
        int totalScore = leftScore + rightScore;
        if(totalScore == 0) {
            normativeResponse = "Du har perfekt hørelse";
        } else if(totalScore > 0 && totalScore <= 16) {
            normativeResponse = "Du har mild høretab";
        } else if(totalScore > 16 && totalScore <= 32) {
            normativeResponse = "Du har moderat høretab";
        } else if(totalScore > 32 && totalScore <= 48) {
            normativeResponse = "Du har moderat alvorlig høretab";
        } else if(totalScore > 48 && totalScore <= 64) {
            normativeResponse = "Du har alvorlig høretab";
        } else if(totalScore > 64) {
            normativeResponse ="Du har slemt høretab";
        } else {
            normativeResponse = "fejl";
        }
        normativeResponse += "\n" + leftResult;
        normativeResponse += "\n" + rightResult;
    }

    public String getComparativeResponse() {
        return comparativeResponse;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public String getNormativeResponse() {
        return normativeResponse;
    }
}
