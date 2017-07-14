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

    private static final int MIN_VALUE = 0;
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
    private TextView mSliderStatusDescription;
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
     * @param listener when status will change
     */
    public void setOnLockUnlockListener(OnLockUnlockListener listener)    {
        this.listener = listener;
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

    public void build(){
        setSliderParameters();
    }


    private void init(Context context, AttributeSet attrs) throws Exception{
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.lock_unlock_slider, this, true);

        // layout for slider background
        mViewBackground = (RelativeLayout) findViewById(R.id.view_background);
        //slider
        mSlider = (SeekBar) findViewById(R.id.seekBar);
        //status when locked (unlocked)
        mSliderStatusDescription = (TextView) findViewById(R.id.seekBar_hint);
        //set max value
        mSlider.setMax(MAX_VALUE);
        //set progress when firs time
        checkPrimarySliderProgress();
        //default values for slider
        setDefaultParametersForSlider();
    }

    /*default slider parameters when init first time*/
    private void setDefaultParametersForSlider(){
        //set the transparent background
        setSeekBarBackgroundTransparent();
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
        mThumbForward = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_open_black_24dp, null);
        mThumbBack = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_lock_outline_black_24dp, null);

        mThumbColor1 = Color.WHITE;
        mThumbColor2 = Color.GRAY;
    }

    /**
     * SET MAIN THE SLIDER LOGIC
     */
    private void setSliderParameters(){
        //parameters for the status field
        initTextFieldForSlider();

        upDateSlider();

        mSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(progress == MIN_VALUE || progress == MAX_VALUE){
                    switch (progress){
                        case MIN_VALUE:
                            mSliderStatus = false;
                            if (listener != null) listener.onLock();
                            break;

                        case MAX_VALUE:
                            mSliderStatus = true;
                            if (listener != null) listener.onUnlock();
                            break;
                    }
                   upDateSlider();
               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSliderStatusDescription.setVisibility(View.INVISIBLE);
                //start animation when the progress > 50%
                startTransitionDrawableByStatus();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //start reverse animation when the progress < 50%
                reverseTransitionDrawableByStatus();
                //start/stop the thumb animation
                runThumbAnimation();
            }
        });
    }


    /*field with a status when locked (unlocked)*/
    private void initTextFieldForSlider(){
        mSliderStatusDescription.setTypeface(null, Typeface.BOLD);
        mSliderStatusDescription.setTextSize(mTextSize);
        mSliderStatusDescription.setTextColor(mTextColor);
    }

    /*set visual parameters after status changing*/
    private void upDateSlider(){
        //set background when the slider progress 0 or 100
        setSliderParamWhenStatic();
        //set padding
        mSlider.setPadding(mBorderWidth, mBorderWidth, mBorderWidth, mBorderWidth);
        // /set the thumb to slider
        mSlider.setThumb(changeThumb());
        mSlider.setThumbOffset(0);
    }

    /*overdrawing the thumb after updating*/
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


    /*set lock or unlock status when running first time*/
    private void checkPrimarySliderProgress(){
        if(mSliderStatus) {
            mSlider.setProgress(MAX_VALUE);
        }else {
            mSlider.setProgress(MIN_VALUE);
        }
    }

    /*visual parameters when slider progress 100% or 0%*/
    private void setSliderParamWhenStatic(){
        mSliderStatusDescription.setVisibility(View.VISIBLE);
        mBackgroundWhenLock = createDrawableForBackground(mBackgroundAngleWhenLock, mBackgroundColorWhenLock, mBorderWidth, mBorderColor);
        mBackgroundWhenUnlock = createDrawableForBackground(mBackgroundAngleWhenUnlock, mBackgroundColorWhenUnlock, mBorderWidth, mBorderColor);

        if(mSliderStatus) {
            mSliderStatusDescription.setText(mTextForSliderWhenLock);
            mViewBackground.setBackground(mBackgroundWhenUnlock);
        }else {
            mSliderStatusDescription.setText(mTextForSliderWhenUnlock);
            mViewBackground.setBackground(mBackgroundWhenLock);
        }
    }

    /*animation when > progress 50%*/
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

    /*animation when < progress 50%*/
    private void reverseTransitionDrawableByStatus(){
        if(!mSliderStatus && mSlider.getProgress() < MAX_VALUE /2){
            mTransitionForBackGround.reverseTransition(ANIM_DURATION);
        }
        else if(mSliderStatus && mSlider.getProgress() > MAX_VALUE /2){
            mTransitionForBackGround.reverseTransition(ANIM_DURATION);
        }
    }

    /*animation for completing of the slider progress
    * 50% > progress < 100% return to right
    * 0% > progress < 50% return to left*/
    private void runThumbAnimation(){
        if (mAnimatorForThumb !=null) mAnimatorForThumb.cancel();
        mSliderProgress = mSlider.getProgress();

        if(mSliderProgress > MAX_VALUE /2){
            mAnimatorForThumb = ValueAnimator.ofInt(mSliderProgress, mSlider.getMax());
            mAnimatorForThumb.setDuration(ANIM_DURATION);
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
            mAnimatorForThumb = ValueAnimator.ofInt(mSliderProgress,  MIN_VALUE);
            mAnimatorForThumb.setDuration(ANIM_DURATION);
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
     * Create the shape for the slider background when status changed
     * @param radius an angle of ste shape (default 45)
     * @param body_color background of the shape
     * @param stroke_weight weight of the stroke
     * @param stroke_color color if the stroke
     * @return (Drawable) shape
     */
    private Drawable createDrawableForBackground(int radius, int body_color, int stroke_weight, int stroke_color){
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);
        shape.setColor(body_color);
        shape.setStroke(stroke_weight, stroke_color);
        return shape;
    }


    private int dpToPx(int dp)  {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }


    public interface OnLockUnlockListener {
        void onLock();
        void onUnlock();
    }
}
