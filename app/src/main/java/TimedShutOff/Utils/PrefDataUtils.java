package TimedShutOff.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefDataUtils
{
    public static final String TIMER_STATUS_ID = "TimedShutOff.TimerStatus";
    public static final String TIMER_VALUE_ID = "TimedShutOff.TimerValue";
    public static final String TIME_REMAINING_ID = "TimedShutOff.TimeRemaining";
    public static final String ALARM_START_TIME_ID = "TimedShutOff.AlarmStartTime";

    public static void StoreTimerStatus(Context context, boolean isRunning)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(TIMER_STATUS_ID, isRunning);
        editor.apply();
    }

    public static boolean GetTimerStatus(Context context)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(TIMER_STATUS_ID,false);
    }

    public static void StoreTimerValue(Context context, long timerValue)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(TIMER_VALUE_ID, timerValue);
        editor.apply();
    }

    public static long GetTimerValue(Context context)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getLong(TIMER_VALUE_ID,0L);
    }

    public static void StoreRemainingTimeSecs(Context context, long timeValue)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(TIME_REMAINING_ID, timeValue);
        editor.apply();
    }

    public static long GetRemainingTimeSecs(Context context)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getLong(TIME_REMAINING_ID,0L);
    }

    public static void StoreAlarmStartTime (Context context, long alarmStartTime)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(ALARM_START_TIME_ID, alarmStartTime);
        editor.apply();
    }

    public static long GetAlarmStartTime (Context context)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getLong(ALARM_START_TIME_ID,0L);
    }
}
