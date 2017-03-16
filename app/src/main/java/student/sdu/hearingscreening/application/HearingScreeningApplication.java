package student.sdu.hearingscreening.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bogs on 16-03-2017.
 */

public class HearingScreeningApplication extends Application {

    private static HearingScreeningApplication  instance;
    public HearingScreeningApplication()
    {
        instance = this;
    }
    public static Context getContext()
    {
        return instance;
    }
}
