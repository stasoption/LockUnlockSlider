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
            app:status="true"
            app:thumbAngle="10"
            app:thumbSize="50"
            app:colorThumb1="#FFEB3B"
            app:colorThumb2="#FFEB3B"
            app:imageThumbWhenLock="@drawable/ic_volume_off_black_24dp"
            app:imageThumbWhenUnLock="@drawable/ic_volume_on_black_24dp"
            app:borderWidth="25"
            app:borderColor="#795548"
            app:angle="10"
            app:backgroundWhenLock="#F44336"
            app:backgroundWhenUnLock="#4CAF50"
            app:textWhenLock="on"
            app:textWhenUnLock="off"
            app:textSize="10"
            app:textColor="#ffffff"/>
```      

![alt tag](https://68.media.tumblr.com/c5a7481a2931e015751cbcb9ec3c3978/tumblr_inline_oj040q7zBw1u3v231_500.gif)

![alt tag](https://68.media.tumblr.com/a7a973977bb49ab76cc43cc8df6c2931/tumblr_inline_oj03emAIt81u3v231_500.gif)







