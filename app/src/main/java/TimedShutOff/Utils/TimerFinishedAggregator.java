package TimedShutOff.Utils;

import android.content.Context;

import TimedShutOff.MainActivity.Classes.PhoneSettings;

public class TimerFinishedAggregator
{
    public static void OnTimeElapsed(Context context)
    {
        PhoneSettings.ChangeSettingsBasedOnPref(context);
    }
}
