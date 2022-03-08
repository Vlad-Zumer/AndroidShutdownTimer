package TimedShutOff.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import TimedShutOff.Data.AppState;

public class AlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive (Context context, Intent intent)
    {
        AppState state = AppState.CreateInstance();

        state.UpdateRemainingTimeSec(0);
        state.UpdateIsTimerRunning(false);
        state.SaveIsTimerRunning(context);
        state.SaveRemainingTimeSec(context);

        TimerFinishedAggregator.OnTimeElapsed(context);
    }
}