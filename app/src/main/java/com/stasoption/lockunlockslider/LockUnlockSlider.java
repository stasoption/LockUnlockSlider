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
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Stas on 21.12.2016.
 */

public class LockUnlockSlider extends RelativeLayout {
    //interface for listening of the slider status
    private OnLockUnlockListener listener = null;
    //for the slider animation when changing the status
    private TransitionDrawable mTransitionForBackGround;
    private ValueAnimator mAnimatorForThumb;
    //slider views
    private RelativeLayout mViewBackground;
    private SeekBar mSlider;
    private TextView mTextHint;
    //default global values for slider
    private static final int MAX_VALUE = 10000;
    private static final int ANIM_DURATION = 400;
    //status of the slider
    private boolean SLIDER_STATUS;
    //value for thumb animation
    private int SLIDER_PROGRESS;
    //thumb metrics
    private int THUMB_ANGLE, THUMB_HEIGHT, THUMB_WIDTH;
    //thumb a shape and a background (gradient)
    private Drawable THUMB_FORWARD, THUMB_BACK;
    private int THUMB_COLOR_1 ,THUMB_COLOR_2;
    //text view on the slider
    private String TEXT_FOR_SLIDER_WHEN_UNLOCK, TEXT_FOR_SLIDER_WHEN_LOCK;
    private int TEXT_SIZE, TEXT_COLOR;
    //for the slider background
    private Drawable BACKGROUND_WHEN_LOCK, BACKGROUND_WHEN_UNLOCK;
    private int BACKGROUND_ANGLE_WHEN_LOCK, BACKGROUND_COLOR_WHEN_LOCK, STROKE_WIDTH_WHEN_LOCK, STROKE_COLOR_WHEN_LOCK;
    private int BACKGROUND_ANGLE_WHEN_UNLOCK, BACKGROUND_COLOR_WHEN_UNLOCK, STROKE_WIDTH_WHEN_UNLOCK, STROKE_COLOR_WHEN_UNLOCK;

    /**
     * CONSTRUCTORS
     */
    public LockUnlockSlider(Context context) throws Exception {
        super(context);
        primaryInit(context, null);
    }
    public LockUnlockSlider(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        primaryInit(context, attrs);
    }
    public LockUnlockSlider(Context context, AttributeSet attrs, int defStyle) throws Exception {
        super(context, attrs, defStyle);
        primaryInit(context, attrs);
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
    private void setDefaultParametersForSlider(){
        // setting parameters for slider
        THUMB_HEIGHT = 60;
        THUMB_WIDTH = 60;
        //default form for shape
        THUMB_ANGLE = dpToPx(45);
        //default text on the slider
        TEXT_FOR_SLIDER_WHEN_UNLOCK = "Lock";
        TEXT_FOR_SLIDER_WHEN_LOCK = "Unlock";
        TEXT_SIZE = 12;
        TEXT_COLOR = Color.WHITE;

        //default background colors
        BACKGROUND_ANGLE_WHEN_LOCK = dpToPx(45);
        BACKGROUND_COLOR_WHEN_LOCK = Color.GRAY;
        STROKE_WIDTH_WHEN_LOCK = 1;
        STROKE_COLOR_WHEN_LOCK = Color.GRAY;
        BACKGROUND_WHEN_LOCK = createDrawableForBackground(BACKGROUND_ANGLE_WHEN_LOCK, BACKGROUND_COLOR_WHEN_LOCK, STROKE_WIDTH_WHEN_LOCK, STROKE_COLOR_WHEN_LOCK);

        BACKGROUND_ANGLE_WHEN_UNLOCK = dpToPx(45);
        BACKGROUND_COLOR_WHEN_UNLOCK = Color.GREEN;
        STROKE_WIDTH_WHEN_UNLOCK = 1;
        STROKE_COLOR_WHEN_UNLOCK = Color.GRAY;
        BACKGROUND_WHEN_UNLOCK = createDrawableForBackground(BACKGROUND_ANGLE_WHEN_UNLOCK, BACKGROUND_COLOR_WHEN_UNLOCK, STROKE_WIDTH_WHEN_UNLOCK, STROKE_COLOR_WHEN_UNLOCK);

        //default thumb
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            THUMB_FORWARD = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_open_black_24dp, null);;
        }else {
            THUMB_FORWARD = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
        }
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            THUMB_BACK = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_outline_black_24dp, null);
        }else {
            THUMB_BACK = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
        }
        THUMB_COLOR_1 = Color.WHITE;
        THUMB_COLOR_2 = Color.GRAY;
    }

    /**
     *  METHODS FOR THE CUSTOM USER PARAMETERS
     */
    public void setSliderStatus(boolean status) {
        SLIDER_STATUS = status;
        checkPrimarySliderProgress();
    }

    public void setThumbWidth(int width){
        THUMB_WIDTH = width;
    }

    public void setThumbHeight(int height){
        THUMB_HEIGHT = height;
    }

    public void setGradientForThumb(int color_from, int color_to){
        THUMB_COLOR_1 = color_from;
        THUMB_COLOR_2 = color_to;
    }

    public void setBackgroundWhenLock(int angle, int background_color, int stroke_weight, int stroke_color){
        BACKGROUND_ANGLE_WHEN_LOCK = dpToPx(angle);
        BACKGROUND_COLOR_WHEN_LOCK = background_color;
        STROKE_WIDTH_WHEN_LOCK = stroke_weight;
        STROKE_COLOR_WHEN_LOCK = stroke_color;
        BACKGROUND_WHEN_LOCK = createDrawableForBackground(
                dpToPx(BACKGROUND_ANGLE_WHEN_LOCK),
                BACKGROUND_COLOR_WHEN_LOCK,
                STROKE_WIDTH_WHEN_LOCK,
                STROKE_COLOR_WHEN_LOCK
        );
    }

    public void setBackgroundWhenUnLock(int angle, int background_color, int stroke_weight, int stroke_color){
        BACKGROUND_ANGLE_WHEN_UNLOCK = dpToPx(angle);
        BACKGROUND_COLOR_WHEN_UNLOCK = background_color;
        STROKE_WIDTH_WHEN_UNLOCK = stroke_weight;
        STROKE_COLOR_WHEN_UNLOCK = stroke_color;
        BACKGROUND_WHEN_UNLOCK = createDrawableForBackground(
                dpToPx(BACKGROUND_ANGLE_WHEN_UNLOCK),
                BACKGROUND_COLOR_WHEN_UNLOCK,
                STROKE_WIDTH_WHEN_UNLOCK,
                STROKE_COLOR_WHEN_UNLOCK
        );
    }

    public void setThumbAngle(int thumb_angle){
        THUMB_ANGLE = dpToPx(thumb_angle);
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
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            THUMB_BACK = img;
        }else {
            THUMB_BACK = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
        }
    }

    public void setImageThumbWhenUnLock(Drawable img){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            THUMB_BACK = img;
        }else {
            THUMB_BACK = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
        }
    }


    /**
     * INIT THE SLIDER
     * @param context
     * @param attrs
     */
    private void primaryInit(Context context, AttributeSet attrs) throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.lock_unlock_slider, this, true);

        // Retrieve layout elements
        mViewBackground = (RelativeLayout) findViewById(R.id.view_background);
        mViewBackground.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //slider primaryInit
        mSlider = (SeekBar) findViewById(R.id.seekBar);
        //set max value
        mSlider.setMax(MAX_VALUE);
        //set progress when firs time
        checkPrimarySliderProgress();
        //set the transparent background
        setSeekBarBackgroundTransparent();
        //default values for slider
        setDefaultParametersForSlider();
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
        upDateSlider();

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

                   if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                       upDateSlider();
                   }else {
                       setBackgroundWhenStatic();
                   }
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
                runThumbAnimation();
            }
        });
    }



    /**
     * CHANGE THE THUMB IMAGE WHEN THE PROGRESS 100% OR 0%
     */
    private void upDateSlider(){
        //set background when the slider progress 0 or 100
        setBackgroundWhenStatic();
        //set padding
        mSlider.setPadding(dpToPx(0),dpToPx(0),dpToPx(0),dpToPx(0));
        // /set the thumb to slider
        mSlider.setThumb(changeThumb());
        mSlider.setThumbOffset(0);
    }

    private LayerDrawable changeThumb(){
        //circle
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{ THUMB_COLOR_1, THUMB_COLOR_2 });
        // Set the GradientDrawable gradient type linear gradient
        gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        // Set GradientDrawable shape is a rectangle
        gd.setShape(GradientDrawable.RECTANGLE);
        //form of shape
        gd.setCornerRadius(THUMB_ANGLE);
        // Set N pixels width solid blue color border
        gd.setStroke(1, Color.GRAY);
        // Set GradientDrawable width and height in pixels
        gd.setSize(dpToPx(THUMB_WIDTH), dpToPx(THUMB_HEIGHT));
        // Set gradient radius
        gd.setGradientRadius(dpToPx(100));
        // Set gravity
        gd.setGradientCenter(0, 0);

        // set the thumb icon by bool status
        Bitmap mTHUMB_BITMAP;
        if(SLIDER_STATUS){
            mTHUMB_BITMAP = ((BitmapDrawable) THUMB_FORWARD).getBitmap();
        }else {
            mTHUMB_BITMAP = ((BitmapDrawable) THUMB_BACK).getBitmap();
        }

        //result thumb drawable
        Drawable thumb = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mTHUMB_BITMAP, dpToPx(24), dpToPx(24), true));
        InsetDrawable thumb_with_padding= new InsetDrawable(thumb,dpToPx(15),dpToPx(15),dpToPx(15),dpToPx(15));
        //show icon in shape. Just in the versoin of android > 4.1
        return new LayerDrawable(new Drawable[]{gd,thumb_with_padding});
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
        BACKGROUND_WHEN_LOCK = createDrawableForBackground(BACKGROUND_ANGLE_WHEN_LOCK, BACKGROUND_COLOR_WHEN_LOCK, STROKE_WIDTH_WHEN_LOCK, STROKE_COLOR_WHEN_LOCK);
        BACKGROUND_WHEN_UNLOCK = createDrawableForBackground(BACKGROUND_ANGLE_WHEN_UNLOCK, BACKGROUND_COLOR_WHEN_UNLOCK, STROKE_WIDTH_WHEN_UNLOCK, STROKE_COLOR_WHEN_UNLOCK);

        if(SLIDER_STATUS) {
            mTextHint.setText(TEXT_FOR_SLIDER_WHEN_UNLOCK);
            setBackgroundStatic(BACKGROUND_WHEN_UNLOCK);
        }else {
            mTextHint.setText(TEXT_FOR_SLIDER_WHEN_LOCK);
            setBackgroundStatic(BACKGROUND_WHEN_LOCK);
        }
    }

    /**
     * START ANIMATION WHEN THE PROGRESS >50%
     */
    private void startTransitionDrawableByStatus() {
        if (SLIDER_STATUS) {
            mTransitionForBackGround = new TransitionDrawable(new Drawable[]{BACKGROUND_WHEN_UNLOCK, BACKGROUND_WHEN_LOCK});
            mViewBackground.setBackground(mTransitionForBackGround);
            mTransitionForBackGround.startTransition(ANIM_DURATION);
        } else {
            mTransitionForBackGround = new TransitionDrawable(new Drawable[]{BACKGROUND_WHEN_LOCK, BACKGROUND_WHEN_UNLOCK});
            mViewBackground.setBackground(mTransitionForBackGround);
            mTransitionForBackGround.startTransition(ANIM_DURATION);
        }
    }

    /**
     * START ANIMATION WHEN THE PROGRESS < 50%
     */
    private void reverseTransitionDrawableByStatus(){
        if(!SLIDER_STATUS && mSlider.getProgress() < MAX_VALUE/2){
            mTransitionForBackGround.reverseTransition(ANIM_DURATION);
        }
        else if(SLIDER_STATUS && mSlider.getProgress() > MAX_VALUE/2){
            mTransitionForBackGround.reverseTransition(ANIM_DURATION);
        }
    }

    /**
     * SET THE ANIMATION PARAMETERS FOR THUMB
     */
    private void runThumbAnimation(){

        if (mAnimatorForThumb !=null) mAnimatorForThumb.cancel();

        int duration = ANIM_DURATION;
        SLIDER_PROGRESS = mSlider.getProgress();

        if(SLIDER_PROGRESS > MAX_VALUE/2){
            mAnimatorForThumb = ValueAnimator.ofInt(SLIDER_PROGRESS, mSlider.getMax());
            mAnimatorForThumb.setDuration(duration);
            mAnimatorForThumb.setInterpolator(new AccelerateInterpolator());
            mAnimatorForThumb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    SLIDER_PROGRESS = (Integer) animation.getAnimatedValue();
                    mSlider.setProgress(SLIDER_PROGRESS);
                }
            });
            mAnimatorForThumb.start();
        }else {
            mAnimatorForThumb = ValueAnimator.ofInt(SLIDER_PROGRESS,  0);
            mAnimatorForThumb.setDuration(duration);
            mAnimatorForThumb.setInterpolator(new AccelerateInterpolator());
            mAnimatorForThumb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    SLIDER_PROGRESS = (Integer) animation.getAnimatedValue();
                    mSlider.setProgress(SLIDER_PROGRESS);
                }
            });
            mAnimatorForThumb.start();
        }
    }

    /**
     * SET TRANSPARENT BACKGROUND FOR SEEKBAR
     */
    private void setSeekBarBackgroundTransparent(){
        //progress
        Drawable shape = createDrawableForBackground(THUMB_ANGLE, Color.TRANSPARENT, 0, Color.TRANSPARENT);
        ClipDrawable clip = new ClipDrawable(shape, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        //background
        Drawable shape1 = createDrawableForBackground(THUMB_ANGLE, Color.TRANSPARENT, 0, Color.TRANSPARENT);
        //ProgressDrawable
        LayerDrawable mylayer = new LayerDrawable(new Drawable[]{shape1,clip});
        mSlider.setProgressDrawable(mylayer);
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
//        shape.setSize(dpToPx(THUMB_WIDTH), dpToPx(THUMB_HEIGHT));
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


    public static interface OnLockUnlockListener {
        void onLock();
        void onUnlock();
    }

}
