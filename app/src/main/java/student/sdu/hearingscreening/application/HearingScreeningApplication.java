package student.sdu.hearingscreening.application;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;

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
    public static void setMusicStreamVolumeFifty()
    {
        Context context = getContext();
        //Get an instance of AudioManager, could be done without using the specific context
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = 0.5f;
        int fiftyVolume = (int) (maxVolume*percent);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, fiftyVolume, 0);
    }
}
