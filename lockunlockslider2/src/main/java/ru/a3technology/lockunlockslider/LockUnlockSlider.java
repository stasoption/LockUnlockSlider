package ru.a3technology.lockunlockslider;

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
    //default global values for slider
    private static final int MAX_VALUE = 10000;
    private static final int ANIM_DURATION = 400;

    //interface for listening of the slider status
    private OnLockUnlockListener listener = null;
    //for the slider animation when changing the status
    private TransitionDrawable mTransitionForBackGround;
    private ValueAnimator mAnimatorForThumb;
    //slider views
    private RelativeLayout mViewBackground;
    private SeekBar mSlider;
    private TextView mTextHint;
    //status of the slider
    private boolean mSliderStatus;
    //value for thumb animation
    private int mSliderProgress;
    //thumb metrics
    private int mThumbAngle, mThumbHeight, mThumbWidth;
    //thumb a shape and a background (gradient)
    private Drawable mThumbForward, mThumbBack;
    private int mThumbColor1, mThumbColor2;
    //text view on the slider
    private String mTextForSliderWhenUnlock, mTextForSliderWhenLock;
    private int mTextSize, mTextColor;
    //for the slider background
    private Drawable mBackgroundWhenLock, mBackgroundWhenUnlock;
    private int mBackgroundAngleWhenLock, mBackgroundColorWhenLock;
    private int mBackgroundAngleWhenUnlock, mBackgroundColorWhenUnlock;
    //stroke width
    private int mBorderWidth, mBorderColor;

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
        mThumbHeight = 60;
        mThumbWidth = 60;
        //default form for shape
        mThumbAngle = dpToPx(45);
        //default text on the slider
        mTextForSliderWhenUnlock = "Lock";
        mTextForSliderWhenLock = "Unlock";
        mTextSize = 12;
        mTextColor = Color.WHITE;

        //default background colors
        mBackgroundAngleWhenLock = dpToPx(45);
        mBackgroundColorWhenLock = Color.GRAY;
        mBorderWidth = 1;
        mBorderColor = Color.GRAY;
        mBackgroundWhenLock = createDrawableForBackground(
                mBackgroundAngleWhenLock,
                mBackgroundColorWhenLock,
                mBorderWidth,
                mBorderColor);

        mBackgroundAngleWhenUnlock = dpToPx(45);
        mBackgroundColorWhenUnlock = Color.GREEN;
        mBorderWidth = 1;
        mBorderColor = Color.GRAY;
        mBackgroundWhenUnlock = createDrawableForBackground(
                mBackgroundAngleWhenUnlock,
                mBackgroundColorWhenUnlock,
                mBorderWidth,
                mBorderColor);

        //default thumb
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            mThumbForward = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_open_black_24dp, null);;
        }else {
            mThumbForward = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
        }
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            mThumbBack = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_outline_black_24dp, null);
        }else {
            mThumbBack = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
        }
        mThumbColor1 = Color.WHITE;
        mThumbColor2 = Color.GRAY;
    }

    /**
     *  METHODS FOR THE CUSTOM USER PARAMETERS
     */
    public void setSliderStatus(boolean status) {
        mSliderStatus = status;
        checkPrimarySliderProgress();
    }

    public void setThumbWidth(int width){
        mThumbWidth = width;
    }

    public void setThumbHeight(int height){
        mThumbHeight = height;
    }

    public void setGradientForThumb(int color_from, int color_to){
        mThumbColor1 = color_from;
        mThumbColor2 = color_to;
    }

    public void setBorderWidth(int width){
        mBorderWidth = width;
    }
    private int getBorderWidth(){
        return mBorderWidth;
    }

    public void setBorderColor(int color){
        mBorderColor = color;
    }
    private int getBorderColor(){
        return mBorderColor;
    }

    public void setBackgroundWhenLock(int angle, int background_color){
        mBackgroundAngleWhenLock = dpToPx(angle);
        mBackgroundColorWhenLock = background_color;
        mBackgroundWhenLock = createDrawableForBackground(
                mBackgroundAngleWhenLock,
                mBackgroundColorWhenLock,
                getBorderWidth(),
                getBorderColor()
        );
    }

    public void setBackgroundWhenUnLock(int angle, int background_color){
        mBackgroundAngleWhenUnlock = dpToPx(angle);
        mBackgroundColorWhenUnlock = background_color;
        mBackgroundWhenUnlock = createDrawableForBackground(
                mBackgroundAngleWhenUnlock,
                mBackgroundColorWhenUnlock,
                getBorderWidth(),
                getBorderColor()
        );
    }

    public void setThumbAngle(int thumb_angle){
        mThumbAngle = dpToPx(thumb_angle);
    }

    public void setTextWhenLock(String text){
        mTextForSliderWhenLock = text;
    }

    public void setTextWhenUnLock(String text){
        mTextForSliderWhenUnlock = text;
    }

    public void setTextSize(int size){
        mTextSize = size;
    }

    public void setTextColor(int color){
        mTextColor = color;
    }

    public void setImageThumbWhenLock(Drawable img){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            mThumbBack = img;
        }else {
            mThumbBack = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
        }
    }

    public void setImageThumbWhenUnLock(Drawable img){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            mThumbForward = img;
        }else {
            mThumbForward = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_lock_lock, null);
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
        mTextHint.setTextSize(mTextSize);
        mTextHint.setTextColor(mTextColor);
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
                            mSliderStatus = false;
                            if (listener != null) listener.onLock();
                            break;

                        case MAX_VALUE:
                            mSliderStatus = true;
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
        mSlider.setPadding(mBorderWidth, mBorderWidth, mBorderWidth, mBorderWidth);
        // /set the thumb to slider
        mSlider.setThumb(changeThumb());
        mSlider.setThumbOffset(0);
    }

    private LayerDrawable changeThumb(){
        //circle
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{mThumbColor1, mThumbColor2});
        // Set the GradientDrawable gradient type linear gradient
        gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        // Set GradientDrawable shape is a rectangle
        gd.setShape(GradientDrawable.RECTANGLE);
        //form of shape
        gd.setCornerRadius(mThumbAngle);
        // Set N pixels width solid blue color border
        gd.setStroke(1, Color.GRAY);
        // Set GradientDrawable width and height in pixels
        gd.setSize(dpToPx(mThumbWidth), dpToPx(mThumbHeight));
        // Set gradient radius
        gd.setGradientRadius(dpToPx(70));

        // set the thumb icon by bool status
        Bitmap mTHUMB_BITMAP;
        if(mSliderStatus){
            mTHUMB_BITMAP = ((BitmapDrawable) mThumbForward).getBitmap();
        }else {
            mTHUMB_BITMAP = ((BitmapDrawable) mThumbBack).getBitmap();
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
        if(mSliderStatus) {
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
        mBackgroundWhenLock = createDrawableForBackground(mBackgroundAngleWhenLock, mBackgroundColorWhenLock, mBorderWidth, mBorderColor);
        mBackgroundWhenUnlock = createDrawableForBackground(mBackgroundAngleWhenUnlock, mBackgroundColorWhenUnlock, mBorderWidth, mBorderColor);

        if(mSliderStatus) {
            mTextHint.setText(mTextForSliderWhenUnlock);
            setBackgroundStatic(mBackgroundWhenUnlock);
        }else {
            mTextHint.setText(mTextForSliderWhenLock);
            setBackgroundStatic(mBackgroundWhenLock);
        }
    }

    /**
     * START ANIMATION WHEN THE PROGRESS >50%
     */
    private void startTransitionDrawableByStatus() {
        if (mSliderStatus) {
            mTransitionForBackGround = new TransitionDrawable(new Drawable[]{mBackgroundWhenUnlock, mBackgroundWhenLock});
            mViewBackground.setBackground(mTransitionForBackGround);
            mTransitionForBackGround.startTransition(ANIM_DURATION);
        } else {
            mTransitionForBackGround = new TransitionDrawable(new Drawable[]{mBackgroundWhenLock, mBackgroundWhenUnlock});
            mViewBackground.setBackground(mTransitionForBackGround);
            mTransitionForBackGround.startTransition(ANIM_DURATION);
        }
    }

    /**
     * START ANIMATION WHEN THE PROGRESS < 50%
     */
    private void reverseTransitionDrawableByStatus(){
        if(!mSliderStatus && mSlider.getProgress() < MAX_VALUE/2){
            mTransitionForBackGround.reverseTransition(ANIM_DURATION);
        }
        else if(mSliderStatus && mSlider.getProgress() > MAX_VALUE/2){
            mTransitionForBackGround.reverseTransition(ANIM_DURATION);
        }
    }

    /**
     * SET THE ANIMATION PARAMETERS FOR THUMB
     */
    private void runThumbAnimation(){

        if (mAnimatorForThumb !=null) mAnimatorForThumb.cancel();

        int duration = ANIM_DURATION;
        mSliderProgress = mSlider.getProgress();

        if(mSliderProgress > MAX_VALUE/2){
            mAnimatorForThumb = ValueAnimator.ofInt(mSliderProgress, mSlider.getMax());
            mAnimatorForThumb.setDuration(duration);
            mAnimatorForThumb.setInterpolator(new AccelerateInterpolator());
            mAnimatorForThumb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mSliderProgress = (Integer) animation.getAnimatedValue();
                    mSlider.setProgress(mSliderProgress);
                }
            });
            mAnimatorForThumb.start();
        }else {
            mAnimatorForThumb = ValueAnimator.ofInt(mSliderProgress,  0);
            mAnimatorForThumb.setDuration(duration);
            mAnimatorForThumb.setInterpolator(new AccelerateInterpolator());
            mAnimatorForThumb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mSliderProgress = (Integer) animation.getAnimatedValue();
                    mSlider.setProgress(mSliderProgress);
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
        Drawable shape = createDrawableForBackground(mThumbAngle, Color.TRANSPARENT, 0, Color.TRANSPARENT);
        ClipDrawable clip = new ClipDrawable(shape, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        //background
        Drawable shape1 = createDrawableForBackground(mThumbAngle, Color.TRANSPARENT, 0, Color.TRANSPARENT);
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
