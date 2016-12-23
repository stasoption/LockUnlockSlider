package a3technology.ru.customslider;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {


    private boolean INIT_STATUS_FOR_SLIDER = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LockUnlockSlider mLockUnlockSlider = (LockUnlockSlider)findViewById(R.id.lockUnlockSlider);

//        mLockUnlockSlider.setSliderStatus(INIT_STATUS_FOR_SLIDER);
        mLockUnlockSlider.setThumbHeight(60);
        mLockUnlockSlider.setThumbWidth(60);
//        mLockUnlockSlider.setGradientForThumb(Color.parseColor("#ADFF2F"), Color.parseColor("#008000"));
        mLockUnlockSlider.setBackgroundWhenLock(45, Color.parseColor("#929292"), 1, Color.GRAY);
        mLockUnlockSlider.setBackgroundWhenUnLock(45, Color.parseColor("#7ed321"), 1, Color.GRAY);
        mLockUnlockSlider.setThumbAngle(45);
        mLockUnlockSlider.setTextWhenLock("Работать");
        mLockUnlockSlider.setTextWhenUnLock("Отдыхать");
        mLockUnlockSlider.setTextSize(16);
//        mLockUnlockSlider.setTextColor(Color.parseColor("#FAF0E6"));
//        mLockUnlockSlider.setImageThumbWhenLock(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_open_black_24dp, null));
//        mLockUnlockSlider.setImageThumbWhenUnLock(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_outline_black_24dp, null));
        mLockUnlockSlider.initialize();

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
