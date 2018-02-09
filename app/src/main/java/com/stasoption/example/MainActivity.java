package com.stasoption.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.stasoption.lockunlockslider.LockUnlockSlider;

public class MainActivity extends AppCompatActivity implements LockUnlockSlider.OnLockUnlockListener {

    private LockUnlockSlider mLockUnlockSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLockUnlockSlider = (LockUnlockSlider) findViewById(R.id.lockUnlockSlider);
        mLockUnlockSlider.setOnLockUnlockListener(this);
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
