package com.stasoption.lockunlockslider;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //set primary bollean status for the slider
    private boolean INIT_STATUS_FOR_SLIDER = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init the slider
        LockUnlockSlider mLockUnlockSlider = (LockUnlockSlider)findViewById(R.id.lockUnlockSlider);

        //here, we can set some parameters (OPTIONAL) for slider
        mLockUnlockSlider.setSliderStatus(INIT_STATUS_FOR_SLIDER); //primary bool param (true/false)
        mLockUnlockSlider.setThumbHeight(60); //thumb height
        mLockUnlockSlider.setThumbWidth(60); //thumb width
        mLockUnlockSlider.setGradientForThumb(Color.GRAY, Color.WHITE); //set background for thumb (gradient or monotone)
        mLockUnlockSlider.setBackgroundWhenLock(45, Color.parseColor("#929292"), 1, Color.GRAY); // set background for screens when lock or unlock. parameters:
        mLockUnlockSlider.setBackgroundWhenUnLock(45, Color.parseColor("#7ed321"), 1, Color.GRAY); // (angle of the shape, color of background, width of border, color of border)
        mLockUnlockSlider.setThumbAngle(45); // angle of the thumb
        mLockUnlockSlider.setTextWhenLock("Unolck"); // text status of slider when lock
        mLockUnlockSlider.setTextWhenUnLock("Lock"); // text status of slider when unlock
        mLockUnlockSlider.setTextSize(16); // text size on slider
        mLockUnlockSlider.setTextColor(Color.parseColor("#FAF0E6")); //text color on slider
        mLockUnlockSlider.setImageThumbWhenLock(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_open_black_24dp, null)); //set icon for the thumb. param:--
        mLockUnlockSlider.setImageThumbWhenUnLock(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_outline_black_24dp, null));//--()
        //start the slider
        mLockUnlockSlider.initialize(); //very important parameter!

        //interface for our slider
        mLockUnlockSlider.setOnLockUnlockListener(new LockUnlockSlider.OnLockUnlockListener() {
            @Override
            public void onLock() {
                Toast.makeText(MainActivity.this, "Lock", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnlock() {
                Toast.makeText(MainActivity.this, "UnLock", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
