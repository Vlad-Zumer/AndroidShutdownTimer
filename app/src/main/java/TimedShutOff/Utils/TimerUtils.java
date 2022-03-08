package TimedShutOff.Utils;

import android.os.CountDownTimer;

public class TimerUtils
{
    public static CountDownTimer MakeTimer(long durationInMS, ICountDownTimerListener listener)
    {
        return new CountDownTimer(durationInMS,1000)
        {
            @Override
            public void onTick (long millisUntilFinished)
            {
                // update remaining time
                listener.UpdateRemainingTimer(millisUntilFinished/1000L);
            }

            @Override
            public void onFinish ()
            {
                // finish timer
                listener.OnTimerCompleted();
            }
        };
    }
}
