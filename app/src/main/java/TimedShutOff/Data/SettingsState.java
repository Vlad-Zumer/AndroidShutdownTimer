package TimedShutOff.Data;

import android.content.Context;

import TimedShutOff.Utils.PrefDataUtils;

public class SettingsState
{
    private SettingsState(){}

    public static SettingsState Instance = null;
    public static SettingsState CreateInstance()
    {
        if(Instance == null)
        {
            Instance = new SettingsState();
        }
        return Instance;
    }

    private static int SetFlag(int inFlags, int flag)
    {
        return inFlags | flag;
    }

    private static int UnsetFlag(int inFlags, int flag)
    {
        // negate flag and bitwise and
        return  inFlags & (~flag);
    }

    private static boolean GetFlag(int fromFlags, int flag)
    {
        return ((fromFlags & flag) > 0);
    }

    private final int shutdownWifiFlag = 1;
    private final int shutdownBTFlag = 1 << 1;
    private final int changeRingerFlag = 1 << 2;

    private int setFlags = 0;

    // update, get

    public void UpdateShutdownWifiFlag(boolean newVal)
    {
        setFlags = newVal? SetFlag(setFlags,shutdownWifiFlag) : UnsetFlag(setFlags,shutdownWifiFlag);
    }

    public boolean GetShutdownWifiFlag()
    {
        return GetFlag(setFlags,shutdownWifiFlag);
    }

    public void UpdateShutdownBTFlag(boolean newVal)
    {
        setFlags = newVal? SetFlag(setFlags,shutdownBTFlag) : UnsetFlag(setFlags,shutdownBTFlag);
    }

    public boolean GetShutdownBTFlag()
    {
        return GetFlag(setFlags,shutdownBTFlag);
    }

    public void UpdateChangeRingerFlag(boolean newVal)
    {
        setFlags = newVal? SetFlag(setFlags,changeRingerFlag) : UnsetFlag(setFlags,changeRingerFlag);
    }

    public boolean GetChangeRingerFlag()
    {
        return GetFlag(setFlags,changeRingerFlag);
    }

    public void Save(Context context)
    {
        PrefDataUtils.StoreSettingsValue(context, setFlags);
    }

    public  void Load(Context context)
    {
        setFlags = PrefDataUtils.GetSettingsValue(context);
    }

}
