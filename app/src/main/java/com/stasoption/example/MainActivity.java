package com.stasoption.example;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.a3technology.lockunlockslider.LockUnlockSlider;

public class MainActivity extends AppCompatActivity {

    private final static String LOCKED = "Locked";
    private final static String UNLOCKED = "UnLocked";

    //set primary boolean status for the slider
    private boolean mPrimaryStatusForSlider = true;
    private LockUnlockSlider mLockUnlockSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLockUnlockSlider = (LockUnlockSlider)findViewById(R.id.lockUnlockSlider);
        mLockUnlockSlider.setOnLockUnlockListener(new LockUnlockSlider.OnLockUnlockListener() {
            @Override
            public void onLock() {
                Toast.makeText(MainActivity.this, LOCKED, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnlock() {
                Toast.makeText(MainActivity.this, UNLOCKED, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
