package TimedShutOff.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmUtils
{
    public static final int ALARM_REQ_CODE=0;

    public static void SetAlarm(Context context, long offsetInMillis)
    {
        AlarmManager manager = context.getSystemService(AlarmManager.class);

        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQ_CODE, intent,0);

        long nowMillis = GetNowMillis();
        long alarmTime = nowMillis + offsetInMillis;

        manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    public static void RemoveAlarm(Context context)
    {
        AlarmManager manager = context.getSystemService(AlarmManager.class);

        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQ_CODE, intent,0);

        manager.cancel(pendingIntent);
    }

    public static long GetNowMillis()
    {
        return  Calendar.getInstance().getTimeInMillis();
    }

    public static long GetNowSecs()
    {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    public static long MinsToMillis(long mins)
    {
        return mins*60*1000;
    }

    public static long SecsToMillis(long secs)
    {
        return secs * 1000;
    }
}

