package com.stasoption.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ru.stasoption.lockunlockslider.LockUnlockSlider;

public class MainActivity extends AppCompatActivity implements LockUnlockSlider.OnLockUnlockListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LockUnlockSlider lockUnlockSlider = (LockUnlockSlider) findViewById(R.id.lockUnlockSlider);
        lockUnlockSlider.setOnLockUnlockListener(this);
    }

    @Override
    public void onLock() {
        Log.d("LockUnlockSlider", "Locked");
    }

    @Override
    public void onUnlock() {
        Log.d("LockUnlockSlider", "UnLocked");
    }
}
