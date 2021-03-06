package TimedShutOff.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import TimedShutOff.Data.AppState;
import TimedShutOff.Data.SettingsState;
import TimedShutOff.MainActivity.Classes.PhoneSettings;
import TimedShutOff.MainActivity.Classes.R;

public class SettingsActivity extends AppCompatActivity
{

    private static final int PERMISSION_REQ_CODE = 122;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SettingsState.CreateInstance();
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        SettingsState.Instance.Load(this);
        UpdateSwitches();
    }

    @Override
    protected void onPause ()
    {
        super.onPause();
        SettingsState.Instance.Save(this);
    }

    public void OnWifiSwitchClicked (View view)
    {
        SwitchCompat switchCompat = (SwitchCompat) view;
        boolean isChecked = switchCompat.isChecked();

        // set setting to value
        SettingsState.Instance.UpdateShutdownWifiFlag(isChecked);

        // if setting is being set to true ->
        //      if no permission -> ask permission
        //      if permission -> update checked

        if(isChecked)
        {
            if(!PhoneSettings.HasWifiPermissions(this))
            {
                // TODO ask perms
                new AlertDialog.Builder(this)
                        .setTitle("No Wifi Permission")
                        .setMessage("The app requires admin wifi permission in order to turn off the wifi.")
                        .setNeutralButton("Close", (dialog, which) ->
                        {
                            String[] permissions = new String[] {Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE};
                            requestPermissions(permissions,PERMISSION_REQ_CODE);
                        }).show();
            }
            else
            {
                UpdateSwitches();
            }
        }

    }

    public void OnBTSwitchClicked (View view)
    {
        SwitchCompat switchCompat = (SwitchCompat) view;
        boolean isChecked = switchCompat.isChecked();

        // set setting to value
        SettingsState.Instance.UpdateShutdownBTFlag(isChecked);

        // if setting is being set to true ->
        //      if no permission -> ask permission
        //      if permission -> update checked
        if(isChecked)
        {
            if(!PhoneSettings.HasBTPermissions(this))
            {
                new AlertDialog.Builder(this)
                        .setTitle("No Bluetooth Permission")
                        .setMessage("The app requires admin bluetooth permission in order to turn off the bluetooth.")
                        .setNeutralButton("Close", (dialog, which) ->
                        {
                            String[] permissions;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                            {
                                permissions = new String[] {Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_ADMIN};
                            }
                            else
                            {
                                permissions = new String[] {Manifest.permission.BLUETOOTH_ADMIN};
                            }
                            requestPermissions(permissions,PERMISSION_REQ_CODE);
                        }).show();
            }
            else
            {
                UpdateSwitches();
            }
        }
    }

    public void OnRingModeSwitchClicked (View view)
    {
        SwitchCompat switchCompat = (SwitchCompat) view;
        boolean isChecked = switchCompat.isChecked();

        // set setting to value
        SettingsState.Instance.UpdateChangeRingerFlag(isChecked);

        // if setting is being set to true ->
        //      if no permission -> ask permission
        //      if permission -> update checked

        if(isChecked)
        {
            if(!PhoneSettings.HasRingerPermissions(this))
            {
                new AlertDialog.Builder(this)
                        .setTitle("Do Not Disturb Permission Needed")
                        .setMessage("In order to be able to switch the phone to the normal ringer mode, it must be granted \"Do Not Disturb\" access.")
                        .setNeutralButton("Close", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                            startActivity(intent);
                        })
                        .show();
            }
            else
            {
                UpdateSwitches();
            }
        }
    }

    private void UpdateSwitches()
    {

        if (PhoneSettings.HasWifiPermissions(this))
        {
            SwitchCompat swtch = findViewById(R.id.wifiSwitch);
            swtch.setChecked(SettingsState.Instance.GetShutdownWifiFlag());
        }
        else
        {
            SwitchCompat swtch = findViewById(R.id.wifiSwitch);
            swtch.setChecked(false);
        }


        if (PhoneSettings.HasBTPermissions(this))
        {
            SwitchCompat swtch = findViewById(R.id.bluetoothSwitch);
            swtch.setChecked(SettingsState.Instance.GetShutdownBTFlag());
        }
        else
        {
            SwitchCompat swtch = findViewById(R.id.bluetoothSwitch);
            swtch.setChecked(false);
        }


        if (PhoneSettings.HasRingerPermissions(this))
        {
            SwitchCompat swtch = findViewById(R.id.ringerMode);
            swtch.setChecked(SettingsState.Instance.GetChangeRingerFlag());
        }
        else
        {
            SwitchCompat swtch = findViewById(R.id.ringerMode);
            swtch.setChecked(false);
        }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE)
        {
            UpdateSwitches();
        }
    }
}