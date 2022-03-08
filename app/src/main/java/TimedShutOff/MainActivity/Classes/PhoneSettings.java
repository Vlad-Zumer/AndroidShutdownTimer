package TimedShutOff.MainActivity.Classes;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class PhoneSettings
{
    public static void ChangeSettingsBasedOnPref(Context context)
    {
        try
        {
            // TODO make this better
            WifiManager wifiManager = context.getSystemService(WifiManager.class);
            wifiManager.setWifiEnabled(false);
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.disable();
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        catch (Exception e)
        {
            new AlertDialog.Builder(context)
                    .setTitle("Permission Error")
                    .setMessage(e.toString())
                    .setPositiveButton("Ok", null).show();
        }
    }
}
