# LockUnlockSlider for Android

![alt tag](https://68.media.tumblr.com/ff60864ce37188b346b08f25d35baed6/tumblr_inline_oizs58YQQs1u3v231_500.gif)

You can add some custom parameters such as background when lock and unlock, angle, size and gradient of the thumb, text for the slider when status changed etc.

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
In your activity:

```
      LockUnlockSlider lockUnlockSlider = (LockUnlockSlider) findViewById(R.id.lockUnlockSlider);
      lockUnlockSlider.setOnLockUnlockListener(new LockUnlockSlider.OnLockUnlockListener() {
            @Override
            public void onLock() {
                Log.d("LockUnlockSlider", "Locked");
            }

            @Override
            public void onUnlock() {
                Log.d("LockUnlockSlider", "UnLocked");
            }
     });
```
      
Custom parameters:
```
      <com.stasoption.lockunlockslider.LockUnlockSlider
              android:id="@+id/lockUnlockSlider"
              android:layout_width="200dp"
              android:layout_height="wrap_content"
              app:sliderStatus="false"
              app:sliderThumbAngle="10"
              app:sliderThumbSize="50"
              app:sliderColorThumb1="#FFEB3B"
              app:sliderColorThumb2="#FFEB3B"
              app:imageThumbWhenLock="@drawable/ic_volume_off_black_24dp"
              app:imageThumbWhenUnLock="@drawable/ic_volume_on_black_24dp"
              app:sliderBorderWidth="25"
              app:sliderBorderColor="#795548"
              app:sliderAngle="10"
              app:backgroundWhenLock="#F44336"
              app:backgroundWhenUnLock="#4CAF50"
              app:textWhenLock="on"
              app:textWhenUnLock="off"
              app:sliderTextSize="10"
              app:sliderTextColor="#ffffff"/>
```      

![alt tag](https://media.giphy.com/media/l4pTqudfC2LFB7wZ2/giphy.gif)








