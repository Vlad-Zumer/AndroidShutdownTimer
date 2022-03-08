package TimedShutOff.Utils;

public interface ICountDownTimerListener
{
    void UpdateRemainingTimer (long remainingTimeSec);
    void OnTimerCompleted ();
}
