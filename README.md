# LockUnlockSlider for Android

## Demo

![alt tag](https://68.media.tumblr.com/ff60864ce37188b346b08f25d35baed6/tumblr_inline_oizs58YQQs1u3v231_500.gif)

You can add some custom parameters such as background when lock and unlock, angle, size and gradient of the thumb, text for the slider when status changed etc.

![alt tag](https://media.giphy.com/media/l4pTqudfC2LFB7wZ2/giphy.gif)

## Usage

**In your XML file**<br />

```
      <com.stasoption.lockunlockslider.LockUnlockSlider
              android:id="@+id/lockUnlockSlider"
              android:layout_width="match_parent"
              android:layout_height="wrap_content" />
```  

**In your activity**<br />

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
      
**Custom parameters**<br />

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

## How to add

**Gradle**<br />

```
      dependencies {
            implementation 'com.github.stasoption:lockUnlockSlider:1.0.9'
      }
```

**Maven**<br />

```
      <dependency>
            <groupId>com.github.stasoption</groupId>
            <artifactId>lockUnlockSlider</artifactId>
            <version>1.0.9</version>
            <type>pom</type>
      </dependency>
```

## License

      The MIT License (MIT)
      Copyright (c) 2018 Stas Averin

      Permission is hereby granted, free of charge, to any person obtaining a copy
      of this software and associated documentation files (the "Software"), to deal
      in the Software without restriction, including without limitation the rights
      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
      copies of the Software, and to permit persons to whom the Software is
      furnished to do so, subject to the following conditions:

      The above copyright notice and this permission notice shall be included in all
      copies or substantial portions of the Software.

      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
      SOFTWARE.

