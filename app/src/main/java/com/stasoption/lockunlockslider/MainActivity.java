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

        //interface for our slider
        mLockUnlockSlider.setOnLockUnlockListener(new LockUnlockSlider.OnLockUnlockListener() {
            @Override
            public void onLock() {
//                Toast.makeText(MainActivity.this, "Lock", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnlock() {
//                Toast.makeText(MainActivity.this, "UnLock", Toast.LENGTH_SHORT).show();
            }
        });

        //here, we can set some parameters (OPTIONAL) for slider
        mLockUnlockSlider.setSliderStatus(INIT_STATUS_FOR_SLIDER); //primary bool param (true/false)
        //thumb parameters
        mLockUnlockSlider.setThumbAngle(5); // angle of the thumb
        mLockUnlockSlider.setThumbHeight(60); //thumb height
        mLockUnlockSlider.setThumbWidth(60); //thumb width
        mLockUnlockSlider.setGradientForThumb(Color.parseColor("#FFA500"), Color.YELLOW); //set background for thumb (gradient or monotone)
        mLockUnlockSlider.setImageThumbWhenLock(ResourcesCompat.getDrawable(getResources(),android.R.drawable.ic_lock_silent_mode, null)); //set icon for the thumb. param:--
        mLockUnlockSlider.setImageThumbWhenUnLock(ResourcesCompat.getDrawable(getResources(),android.R.drawable.ic_lock_silent_mode_off, null));//--()
        //background parameters
        mLockUnlockSlider.setStrokeWidth(5);
        mLockUnlockSlider.setStrokeColor(Color.parseColor("#FFA500"));
        mLockUnlockSlider.setBackgroundWhenLock(5, Color.parseColor("#4B0082")); // set background for screens when lock or unlock. parameters:
        mLockUnlockSlider.setBackgroundWhenUnLock(5, Color.parseColor("#8A2BE2")); // (angle of the shape, color of background, width of border, color of border)
        //text parameters
        mLockUnlockSlider.setTextWhenLock("On"); // text status of slider when lock
        mLockUnlockSlider.setTextWhenUnLock("Off"); // text status of slider when unlock
        mLockUnlockSlider.setTextSize(25); // text size on slider
        mLockUnlockSlider.setTextColor(Color.parseColor("#FFA500")); //text color on slider
        //init the slider
        mLockUnlockSlider.initialize(); //very important parameter!


    }


}
