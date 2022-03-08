package TimedShutOff.MainActivity.Classes;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class PhoneSettings
{
    public static void ChangeSettingsBasedOnPref(Context context)
    {
        try
        {
            // TODO make this better
            if (context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED)
            {
                WifiManager wifiManager = context.getSystemService(WifiManager.class);
                wifiManager.setWifiEnabled(false);
            }

            if (context.checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED)
            {
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                bluetoothAdapter.disable();
            }

            if(context.checkSelfPermission(Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED)
            {
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
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
