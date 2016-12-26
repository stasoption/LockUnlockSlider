package com.stasoption.lockunlockslider;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Stas on 21.12.2016.
 */

public class LockUnlockSlider extends RelativeLayout {

    private OnLockUnlockListener listener = null;

    //status of the slider
    private boolean SLIDER_STATUS;
    //metrics
    private int SLIDER_HEIGHT;
    private int SLIDER_WIDTH;

    private int ANGLE;

    private String TEXT_FOR_SLIDER_WHEN_UNLOCK;
    private String TEXT_FOR_SLIDER_WHEN_LOCK;
    private int TEXT_SIZE;
    private int TEXT_COLOR;

    private Drawable BACKGROUN_WHEN_LOCK;
    private Drawable BACKGROUN_WHEN_UNLOCK;


    private Drawable THUMB_FORWARD;
    private Drawable THUMB_BACK;
    private int THUMB_COLOR_1;
    private int THUMB_COLOR_2;



    private TransitionDrawable transition;
    private View mViewBackground;
    private SeekBar mSlider;
    private TextView mTextHint;
    private int int_slider_progress;

    private static final int MAX_VALUE = 1000;
    private static final int ANIM_DURATION = 400;


    /**
     * CONSTRUCTORS
     */
    public LockUnlockSlider(Context context) throws Exception {
        super(context);
        init(context, null);
    }
    public LockUnlockSlider(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        init(context, attrs);
    }
    public LockUnlockSlider(Context context, AttributeSet attrs, int defStyle) throws Exception {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * INTERFACE FOR USER
     * @param listener for listening status of the slider
     */
    public void setOnLockUnlockListener(OnLockUnlockListener listener)    {
        this.listener = listener;
    }

    /**
     * DEFAULT INITIALIZED VIEWS
     */
    private void setDefaultParameters(){
        // setting parameters for slider
        SLIDER_HEIGHT = 60;
        SLIDER_WIDTH = 60;
        //default form for shape
        ANGLE = dpToPx(45);
        //default text on the slider
        TEXT_FOR_SLIDER_WHEN_UNLOCK = "Lock";
        TEXT_FOR_SLIDER_WHEN_LOCK = "Unlock";
        TEXT_SIZE = 12;
        TEXT_COLOR = Color.WHITE;
        //default background colors
        int mCOLOR_BACKGROUND_LOCK = Color.GRAY;
        BACKGROUN_WHEN_LOCK = createDrawableForBackground(ANGLE, mCOLOR_BACKGROUND_LOCK, 1, Color.GRAY);
        int mCOLOR_BACKGROUND_UNLOCK = Color.GREEN;
        BACKGROUN_WHEN_UNLOCK = createDrawableForBackground(ANGLE, mCOLOR_BACKGROUND_UNLOCK, 1, Color.GRAY);
        //default thumb
        THUMB_FORWARD =  ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_open_black_24dp, null);
        THUMB_BACK =  ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_outline_black_24dp, null);
        THUMB_COLOR_1 = Color.WHITE;
        THUMB_COLOR_2 = Color.GRAY;
    }

    /**
     *  METHODS FOR THE CUSTOM USER PARAMETERS
     */
    public void setSliderStatus(boolean status) {
        SLIDER_STATUS = status;
    }
    public void setThumbWidth(int width){
        SLIDER_WIDTH = width;
    }
    public void setThumbHeight(int height){
        SLIDER_HEIGHT = height;
    }
    public void setGradientForThumb(int color_from, int color_to){
        THUMB_COLOR_1 = color_from;
        THUMB_COLOR_2 = color_to;
    }
    public void setBackgroundWhenLock(int angle, int background_color, int stroke_weight, int stroke_color){
        BACKGROUN_WHEN_LOCK = createDrawableForBackground(dpToPx(angle), background_color, stroke_weight, stroke_color);
    }
    public void setBackgroundWhenUnLock(int angle, int background_color, int stroke_weight, int stroke_color){
        BACKGROUN_WHEN_UNLOCK = createDrawableForBackground(dpToPx(angle), background_color, stroke_weight, stroke_color);
    }
    public void setThumbAngle(int thumb_angle){
        ANGLE = dpToPx(thumb_angle);
    }
    public void setTextWhenLock(String text){
        TEXT_FOR_SLIDER_WHEN_LOCK = text;
    }
    public void setTextWhenUnLock(String text){
        TEXT_FOR_SLIDER_WHEN_UNLOCK = text;
    }
    public void setTextSize(int size){
        TEXT_SIZE = size;
    }
    public void setTextColor(int color){
        TEXT_COLOR = color;
    }
    public int getTextColor(){
        return TEXT_COLOR;
    }
    public void setImageThumbWhenLock(Drawable img){
        THUMB_BACK = img;
    }
    public void setImageThumbWhenUnLock(Drawable img){
        THUMB_FORWARD = img;
    }

    /**
     * INIT THE SLIDER FOR CHECKNG BY USERS PARAMETERS
     */
    public void initialize(){
        //parameters for the text on the slider
        initTextOnSliderParam();
        //parameters for slider
        setSliderParameters();
    }

    /**
     * INIT THE SLIDER
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.lock_unlock_slider, this, true);
        //default values for slider
        setDefaultParameters();
    }

    /**
     * TEXT ON SLIDER (LOCK / UNLOCK)
     */
    private void initTextOnSliderParam(){
        //text
        mTextHint = (TextView) findViewById(R.id.seekBar_hint);
        mTextHint.setTypeface(null, Typeface.BOLD);
        mTextHint.setTextSize(TEXT_SIZE);
        mTextHint.setTextColor(TEXT_COLOR);
    }

    /**
     * SET MAIN THE SLIDER LOGIC
     */
    private void setSliderParameters(){
        // Retrieve layout elements
        mViewBackground = (RelativeLayout)findViewById(R.id.view_background);
        mViewBackground.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //slider init
        mSlider = (SeekBar) findViewById(R.id.seekBar);
        //set max value
        mSlider.setMax(MAX_VALUE);
        //set padding (default 0,0,0,0)
        setPaddingInSlider();
        //set the transparent background
        setSeekBarBackgroundTransparent();
        //set the slider thumb
        changeSeekBarThumb();
        //set background when the slider progress 0 or 100
        setBackgroundWhenStatic();
        //set progress when firs time
        checkPrimarySliderProgress();
        mSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

               if(progress == 0 || progress == MAX_VALUE){
                    switch (progress){
                        case 0:
                            SLIDER_STATUS = false;
                            if (listener != null) listener.onLock();
                            break;
                        case MAX_VALUE:
                            SLIDER_STATUS = true;
                            if (listener != null) listener.onUnlock();
                            break;
                    }
                   //update slider(recursion)
                   setSliderParameters();
                   setBackgroundWhenStatic();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mTextHint.setVisibility(View.INVISIBLE);
                //set animation for changing background, when the progress > 50%
                startTransitionDrawableByStatus();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //set animation for changing background, when the progress < 50%
                reverseTransitionDrawableByStatus();
                //start/stop the animation
                setAnimation();
            }
        });
    }

    /**
     * SET PROGRESS WHEN THE SLIDER INITS FIRST TIME
     */
    private void checkPrimarySliderProgress(){
        if(SLIDER_STATUS) {
            mSlider.setProgress(MAX_VALUE);
        }else {
            mSlider.setProgress(0);
        }
    }

    /**
     * BACKGROUND FOR THE STATIC MODE (0 OR 100%)
     */
    private void setBackgroundWhenStatic(){
        mTextHint.setVisibility(View.VISIBLE);

        if(SLIDER_STATUS) {
            mTextHint.setText(TEXT_FOR_SLIDER_WHEN_UNLOCK);
            setBackgroundStatic(BACKGROUN_WHEN_UNLOCK);
        }else {
            mTextHint.setText(TEXT_FOR_SLIDER_WHEN_LOCK);
            setBackgroundStatic(BACKGROUN_WHEN_LOCK);
        }
    }

    /**
     * START ANIMATION WHEN THE PROGRESS >50%
     */
    private void startTransitionDrawableByStatus() {
        if (SLIDER_STATUS) {
            transition = new TransitionDrawable(new Drawable[]{BACKGROUN_WHEN_UNLOCK, BACKGROUN_WHEN_LOCK});
            mViewBackground.setBackground(transition);
            transition.startTransition(ANIM_DURATION);
        } else {
            transition = new TransitionDrawable(new Drawable[]{BACKGROUN_WHEN_LOCK, BACKGROUN_WHEN_UNLOCK});
            mViewBackground.setBackground(transition);
            transition.startTransition(ANIM_DURATION);
        }
    }

    /**
     * START ANIMATION WHEN THE PROGRESS < 50%
     */
    private void reverseTransitionDrawableByStatus(){
        if(!SLIDER_STATUS && mSlider.getProgress() < MAX_VALUE/2){
            transition.reverseTransition(ANIM_DURATION);
        }
        else if(SLIDER_STATUS && mSlider.getProgress() > MAX_VALUE/2){
            transition.reverseTransition(ANIM_DURATION);
        }
    }

    /**
     * SET THE ANIMATION PARAMETERS FOR THUMB
     */
    private void setAnimation(){
        int duration = ANIM_DURATION;
        int_slider_progress = mSlider.getProgress();

        if(int_slider_progress > MAX_VALUE/2){
            ValueAnimator anim = ValueAnimator.ofInt(int_slider_progress, mSlider.getMax());
            anim.setDuration(duration);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int_slider_progress = (Integer) animation.getAnimatedValue();
                    mSlider.setProgress(int_slider_progress);
                }
            });
            anim.start();
        }else {
            ValueAnimator anim = ValueAnimator.ofInt(int_slider_progress,  0);
            anim.setDuration(duration);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int_slider_progress = (Integer) animation.getAnimatedValue();
                    mSlider.setProgress(int_slider_progress);
                }
            });
            anim.start();
        }
    }

    /**
     * SET TRANSPARENT BACKGROUND FOR SEEKBAR
     */
    private void setSeekBarBackgroundTransparent(){
        //progress
        Drawable shape = createDrawableForBackground(ANGLE, Color.TRANSPARENT, 0, Color.TRANSPARENT);
        ClipDrawable clip = new ClipDrawable(shape, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        //background
        Drawable shape1 = createDrawableForBackground(ANGLE, Color.TRANSPARENT, 0, Color.TRANSPARENT);
        //ProgressDrawable
        LayerDrawable mylayer = new LayerDrawable(new Drawable[]{shape1,clip});
        mSlider.setProgressDrawable(mylayer);
    }

    /**
     * CHANGE THE THUMB IMAGE WHEN THE PROGRESS 100% OR 0%
     */
    private void changeSeekBarThumb(){
        //circle
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{
                THUMB_COLOR_1, THUMB_COLOR_2
        });
        // Set the GradientDrawable gradient type linear gradient
        gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        // Set GradientDrawable shape is a rectangle
        gd.setShape(GradientDrawable.RECTANGLE);
        //form of shape
        gd.setCornerRadius(ANGLE);
        // Set N pixels width solid blue color border
        gd.setStroke(1, Color.GRAY);
        // Set GradientDrawable width and height in pixels
        gd.setSize(dpToPx(SLIDER_WIDTH), dpToPx(SLIDER_HEIGHT));
        // Set gradient radius
        gd.setGradientRadius(dpToPx(100));
        //Set gravity
        gd.setGradientCenter(0, 0);
        //set the thumb icon by bool status
        Bitmap mTHUMB_BITMAP;
        if(SLIDER_STATUS){
            mTHUMB_BITMAP = ((BitmapDrawable) THUMB_FORWARD).getBitmap();
        }else {
            mTHUMB_BITMAP = ((BitmapDrawable) THUMB_BACK).getBitmap();
        }
        //result thumb drawable
        Drawable thumb = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mTHUMB_BITMAP, dpToPx(24), dpToPx(24), true));
        InsetDrawable thumb_with_padding= new InsetDrawable(thumb,dpToPx(15),dpToPx(15),dpToPx(15),dpToPx(15));
        //layer
        LayerDrawable mylayer = new LayerDrawable(new Drawable[]{gd,thumb_with_padding});
        mylayer.setBounds(0, 0, mylayer.getIntrinsicWidth(), mylayer.getIntrinsicHeight());
        //set the thumb to slider
        mSlider.setThumb(mylayer);
        mSlider.setThumbOffset(0);
    }

    /**
     * Create the shape for setting a backgrounds
     * @param radius an angle of ste shape (default 45)
     * @param body_color background of the shape
     * @param stroke_weight background of the stroke
     * @param stroke_color color if the stroke
     * @return custom shape
     */
    private Drawable createDrawableForBackground(int radius, int body_color, int stroke_weight, int stroke_color){
        GradientDrawable shape = new GradientDrawable();
        shape.setSize(dpToPx(SLIDER_WIDTH), dpToPx(SLIDER_HEIGHT));
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);
        shape.setColor(body_color);
        shape.setStroke(stroke_weight, stroke_color);
        return shape;
    }

    /**
     * SET BACKGROUND FOR STATIC SHAPES
     * @param mBackgroundStatic
     */
    private void setBackgroundStatic(Drawable mBackgroundStatic){
        mViewBackground.setBackground(mBackgroundStatic);
    }

    /**
     * TRANSFORMATE FROM PIXELS TO DP
     * @param dp
     * @return
     */
    private int dpToPx(int dp)  {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    /**
     * SET PADDING
     */
    private void setPaddingInSlider(){
        mSlider.setPadding(dpToPx(0),dpToPx(0),dpToPx(0),dpToPx(0));
    }


    public static interface OnLockUnlockListener {
        void onLock();
        void onUnlock();
    }

}
