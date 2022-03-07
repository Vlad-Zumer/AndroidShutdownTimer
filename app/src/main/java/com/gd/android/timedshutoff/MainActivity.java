package com.gd.android.timedshutoff;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private HashMap<Integer, Integer> idToIncrement = new HashMap<Integer, Integer>();
    private boolean isWorking = false;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetUpIdToIncrementMap();
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
        if (!isWorking)
        {
            int x = idToIncrement.getOrDefault(view.getId(),0);
            ProgressBar pb = findViewById(R.id.ProgressBar);
            pb.incrementProgressBy(x);
        }
    }

    public void StartButtonClicked (View view)
    {
        isWorking = true;
    }

    public void StopButtonClicked (View view)
    {
        isWorking = false;
    }
}