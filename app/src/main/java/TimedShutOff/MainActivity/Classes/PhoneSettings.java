package TimedShutOff.MainActivity.Classes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import TimedShutOff.Data.SettingsState;

public class PhoneSettings
{
    public static void ChangeSettingsBasedOnPref (Context context)
    {
        try
        {
            SettingsState.CreateInstance();
            SettingsState.Instance.Load(context);

            if (HasWifiPermissions(context) && SettingsState.Instance.GetShutdownWifiFlag())
            {
                WifiManager wifiManager = context.getSystemService(WifiManager.class);
                wifiManager.setWifiEnabled(false);
            }

            if (HasBTPermissions(context) && SettingsState.Instance.GetShutdownBTFlag())
            {
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                @SuppressLint("MissingPermission") boolean b = bluetoothAdapter.disable();
            }

            if(HasRingerPermissions(context) && SettingsState.Instance.GetChangeRingerFlag())
            {
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }
        catch (Exception e)
        {
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage(e.toString())
                    .setPositiveButton("Ok", null).show();
        }
    }

    public static boolean HasWifiPermissions(Context context)
    {
        return  context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean HasBTPermissions(Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            return  context.checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
        }
        else
        {
            return context.checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static boolean HasRingerPermissions(Context context)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        return notificationManager.isNotificationPolicyAccessGranted();
    }
}
