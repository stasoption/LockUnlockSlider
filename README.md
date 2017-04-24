# LockUnlockSlider

**LockUnlockSlider** for android has two modes (true/false). 
Thus it has interface with two methods: onLock(), onUnlock().

![alt tag](https://68.media.tumblr.com/ff60864ce37188b346b08f25d35baed6/tumblr_inline_oizs58YQQs1u3v231_500.gif)

Also, you can add custom parameters such as background when lock, background when unlock, angle and gradient for the thumb, text for the slider etc.

![alt tag](https://68.media.tumblr.com/c5a7481a2931e015751cbcb9ec3c3978/tumblr_inline_oj040q7zBw1u3v231_500.gif)

![alt tag](https://68.media.tumblr.com/a7a973977bb49ab76cc43cc8df6c2931/tumblr_inline_oj03emAIt81u3v231_500.gif)


# How to use:

**First**<br />
In your XML file, add follow code:
```
      <com.stasoption.lockunlockslider.LockUnlockSlider
              android:id="@+id/lockUnlockSlider"
              android:layout_width="match_parent"
              android:layout_height="wrap_content" />
```        
**Next**<br />
In your activity, add LockUnlockSlider and override the follow interface

```
      LockUnlockSlider mLockUnlockSlider = (LockUnlockSlider)findViewById(R.id.lockUnlockSlider);
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
```


**!Next, vary impotant! You can init the slider**<br />
```
        mLockUnlockSlider.initialize();
```        
Before initialization you may to set some parameters (OPTIONAL) for slider
```
        mLockUnlockSlider.setSliderStatus(BOOLEAN_STATUS_FOR_SLIDER); //primary bool param (lock or unlock)
        
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
```      







