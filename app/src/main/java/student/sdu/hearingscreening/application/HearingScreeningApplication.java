package student.sdu.hearingscreening.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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
    public static void setMusicStreamVolumeMax()
    {
        Context context = getContext();
        //Get an instance of AudioManager, could be done without using the specific context
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        float percent = 0.5f;
//        int fiftyVolume = (int) (maxVolume*percent);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
    }

    /**
     *
     * @param newIntent
     * @param oldIntent
     */
    public static void activityIntentSwitch(Activity newIntent, Activity oldIntent)
    {
        Intent mainIntent = new Intent(getContext(), newIntent.getClass());
        oldIntent.startActivity(mainIntent);
        oldIntent.finish();
    }
}
