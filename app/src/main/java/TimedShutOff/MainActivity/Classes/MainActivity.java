package TimedShutOff.MainActivity.Classes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

import TimedShutOff.Data.AppState;
import TimedShutOff.Utils.AlarmUtils;
import TimedShutOff.Utils.ICountDownTimerListener;
import TimedShutOff.Utils.TimerFinishedAggregator;
import TimedShutOff.Utils.TimerUtils;

public class MainActivity extends AppCompatActivity implements ICountDownTimerListener
{
    private HashMap<Integer, Integer> idToIncrement = new HashMap<>();

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppState.CreateInstance();
        SetUpIdToIncrementMap();
    }

    @Override
    protected void onPause ()
    {
        super.onPause();

        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }

        if (AppState.Instance.GetIsTimerRunning())
        {
            long timeForAlarm = AppState.Instance.GetRemainingTimeSec();
            timeForAlarm = AlarmUtils.SecsToMillis(timeForAlarm);
            AlarmUtils.SetAlarm(this, timeForAlarm);

            AppState.Instance.UpdateAlarmStartTimeSec(AlarmUtils.GetNowSecs());
        }

        AppState.Instance.SaveAll(this);
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        AppState.Instance.LoadAll(this);

        if (AppState.Instance.GetIsTimerRunning())
        {
            long remainingSecs = AppState.Instance.GetRemainingTimeSec();
            remainingSecs -= AlarmUtils.GetNowSecs() - AppState.Instance.GetAlarmStartTimeSec();
            if (remainingSecs > 0)
            {
                countDownTimer = TimerUtils.MakeTimer(AlarmUtils.SecsToMillis(remainingSecs), this);
                countDownTimer.start();
            }
            AlarmUtils.RemoveAlarm(this);
        }

        UpdateButtons();
        UpdateTextView();
    }

    private void SetUpIdToIncrementMap()
    {
        idToIncrement.put(R.id.p1Min,1);
        idToIncrement.put(R.id.p5Min,5);
        idToIncrement.put(R.id.p10Min,10);
        idToIncrement.put(R.id.p15Min,15);
        idToIncrement.put(R.id.p30Min,30);
        idToIncrement.put(R.id.m1Min,-1);
        idToIncrement.put(R.id.m5Min,-5);
        idToIncrement.put(R.id.m10Min,-10);
        idToIncrement.put(R.id.m15Min,-15);
        idToIncrement.put(R.id.m30Min,-30);
    }

    public void ModifyTime (View view)
    {
        if (!AppState.Instance.GetIsTimerRunning())
        {
            long timerValue = AppState.Instance.GetTimerValueMin();
            int dx = idToIncrement.getOrDefault(view.getId(),0);
            timerValue += dx;
            if(timerValue < 0)
            {
                timerValue = 0;
            }
            AppState.Instance.UpdateTimerValueMin(timerValue);
            UpdateTextView();
            UpdateButtons();
        }
    }

    public void StartButtonClicked (View view)
    {
        AppState.Instance.UpdateIsTimerRunning(true);

        long timerValue = AppState.Instance.GetTimerValueMin();
        timerValue = AlarmUtils.MinsToMillis(timerValue);

        UpdateButtons();
        countDownTimer = TimerUtils.MakeTimer(timerValue,this);
        countDownTimer.start();
    }

    public void StopButtonClicked (View view)
    {
        AppState.Instance.UpdateIsTimerRunning(false);
        AppState.Instance.UpdateRemainingTimeSec(0L);
        UpdateButtons();
        UpdateTextView();
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    private void UpdateTextView()
    {
        TextView textView = findViewById(R.id.TextView);
        if(!AppState.Instance.GetIsTimerRunning())
        {
            long timerValue = AppState.Instance.GetTimerValueMin();
            long hours = timerValue / 60L;
            long minutes = timerValue % 60L;
            String toShow = String.format("%02d:%02d:00", hours, minutes);
            textView.setText(toShow);
            ProgressBar progressBar = findViewById(R.id.ProgressBar);
            progressBar.setProgress(0);
        }
        else
        {
            long remainingTimeSec = AppState.Instance.GetRemainingTimeSec();

            long secs = remainingTimeSec % 60L;
            long mins = (remainingTimeSec / 60L) % 60L;
            long hrs  = (remainingTimeSec / 3600L);
            int progressValue = 60 - (int)secs;
            String toShow = String.format("%02d:%02d:%02d",hrs,mins,secs);
            textView.setText(toShow);
            ProgressBar progressBar = findViewById(R.id.ProgressBar);
            progressBar.setProgress(progressValue);
        }
    }

    private void UpdateButtons()
    {
        boolean isTimerRunning = AppState.Instance.GetIsTimerRunning();
        long timerValue = AppState.Instance.GetTimerValueMin();
        findViewById(R.id.p1Min).setEnabled(!isTimerRunning);
        findViewById(R.id.p5Min).setEnabled(!isTimerRunning);
        findViewById(R.id.p10Min).setEnabled(!isTimerRunning);
        findViewById(R.id.p15Min).setEnabled(!isTimerRunning);
        findViewById(R.id.p30Min).setEnabled(!isTimerRunning);
        findViewById(R.id.m1Min).setEnabled(!isTimerRunning);
        findViewById(R.id.m5Min).setEnabled(!isTimerRunning);
        findViewById(R.id.m10Min).setEnabled(!isTimerRunning);
        findViewById(R.id.m15Min).setEnabled(!isTimerRunning);
        findViewById(R.id.m30Min).setEnabled(!isTimerRunning);

        findViewById(R.id.buttonStart).setEnabled(!isTimerRunning && timerValue>0);
        findViewById(R.id.buttonStop).setEnabled(isTimerRunning);
    }

    @Override
    public void UpdateRemainingTimer (long remainingTimeSec)
    {
        AppState.Instance.UpdateRemainingTimeSec(remainingTimeSec);
        UpdateTextView();
    }

    @Override
    public void OnTimerCompleted ()
    {
        AppState.Instance.UpdateIsTimerRunning(false);
        AppState.Instance.UpdateRemainingTimeSec(0);
        ProgressBar progressBar = findViewById(R.id.ProgressBar);
        progressBar.setProgress(0);
        UpdateButtons();
        UpdateTextView();

        TimerFinishedAggregator.OnTimeElapsed(this);
    }
}