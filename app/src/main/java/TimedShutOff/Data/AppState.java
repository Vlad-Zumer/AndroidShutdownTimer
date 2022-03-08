package TimedShutOff.Data;

import android.content.Context;

import TimedShutOff.Utils.PrefDataUtils;

public class AppState
{
    private AppState(){};

    public static AppState Instance = null;
    public static AppState CreateInstance()
    {
        if(Instance == null)
        {
            Instance = new AppState();
        }
        return Instance;
    }

    private boolean isTimerRunning = false;
    private long timerValueMin = 0L;
    private long remainingTimeSec = 0L;
    private long alarmStartTimeSec = 0L;

    // update, get, save, load

    public void UpdateIsTimerRunning(boolean newVal){ isTimerRunning = newVal;}
    public boolean GetIsTimerRunning(){ return isTimerRunning;}
    public void SaveIsTimerRunning(Context context){ PrefDataUtils.StoreTimerStatus(context, isTimerRunning); }
    public boolean LoadIsTimerRunning(Context context){  isTimerRunning = PrefDataUtils.GetTimerStatus(context); return isTimerRunning; }

    public void UpdateTimerValueMin(long newVal){ timerValueMin = newVal;}
    public long GetTimerValueMin(){ return timerValueMin;}
    public void SaveTimerValueMin(Context context){ PrefDataUtils.StoreTimerValue(context, timerValueMin); }
    public long LoadTimerValueMin(Context context){ timerValueMin = PrefDataUtils.GetTimerValue(context); return timerValueMin; }

    public void UpdateRemainingTimeSec(long newVal){ remainingTimeSec = newVal;}
    public long GetRemainingTimeSec(){ return remainingTimeSec;}
    public void SaveRemainingTimeSec(Context context){ PrefDataUtils.StoreRemainingTimeSecs(context, remainingTimeSec); }
    public long LoadRemainingTimeSec(Context context){  remainingTimeSec = PrefDataUtils.GetRemainingTimeSecs(context); return remainingTimeSec; }

    public void UpdateAlarmStartTimeSec(long newVal){ alarmStartTimeSec = newVal;}
    public long GetAlarmStartTimeSec(){ return alarmStartTimeSec;}
    public void SaveAlarmStartTimeSec(Context context){ PrefDataUtils.StoreAlarmStartTime(context, alarmStartTimeSec); }
    public long LoadAlarmStartTimeSec(Context context){  alarmStartTimeSec = PrefDataUtils.GetAlarmStartTime(context); return alarmStartTimeSec; }

    public void SaveAll(Context context)
    {
        SaveIsTimerRunning(context);
        SaveTimerValueMin(context);
        SaveRemainingTimeSec(context);
        SaveAlarmStartTimeSec(context);
    }

    public void  LoadAll(Context context)
    {
        LoadIsTimerRunning(context);
        LoadTimerValueMin(context);
        LoadRemainingTimeSec(context);
        LoadAlarmStartTimeSec(context);
    }

}
