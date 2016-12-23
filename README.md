# LockUnlockSlider

Lock Unlock Slider for android can have two modes (true/false). 

## initialization

LockUnlockSlider mLockUnlockSlider = (LockUnlockSlider)findViewById(R.id.lockUnlockSlider);

##Thus it has two interfaces:

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
 
 
 ##finaly we must to initializate the slider
 
 mLockUnlockSlider.initialize(); //very important parameter!
 


