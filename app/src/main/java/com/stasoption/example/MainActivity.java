package com.stasoption.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.a3technology.lockunlockslider.LockUnlockSlider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LockUnlockSlider lockUnlockSlider = (LockUnlockSlider) findViewById(R.id.lockUnlockSlider);
        lockUnlockSlider.setOnLockUnlockListener(new LockUnlockSlider.OnLockUnlockListener() {
            @Override
            public void onLock() {
                Toast.makeText(MainActivity.this, "Locked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnlock() {
                Toast.makeText(MainActivity.this, "UnLocked", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
