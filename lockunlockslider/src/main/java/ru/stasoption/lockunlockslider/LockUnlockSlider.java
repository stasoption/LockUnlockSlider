package ru.stasoption.lockunlockslider;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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

    private OnLockUnlockListener mOnLockUnlockListener;

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
    private int mThumbAngle, mThumbSize;
    //thumb a shape and a background (gradient)
    private Drawable mThumbForward, mThumbBack;
    private int mThumbColor1, mThumbColor2;
    //text view on the slider
    private String mTextForSliderWhenUnlock, mTextForSliderWhenLock;
    private int mTextSize, mTextColor;
    //for the slider background
    private Drawable mBackgroundWhenLock, mBackgroundWhenUnlock;
    private int mAngle;
    private int mBackgroundColorWhenLock;
    private int mBackgroundColorWhenUnlock;
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

    public void setOnLockUnlockListener(OnLockUnlockListener listener)    {
        this.mOnLockUnlockListener = listener;
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
        //default values for slider
        setDefaultParametersForSlider();

        //user parameters
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.lockUnlockSlider, 0, 0);
        if(a != null){
            mSliderStatus = a.getBoolean(R.styleable.lockUnlockSlider_status, false);
            mThumbAngle = checkIntValue(mThumbAngle, a.getInteger(R.styleable.lockUnlockSlider_thumbAngle, 0));
            mThumbSize = checkIntValue(mThumbSize, a.getInteger(R.styleable.lockUnlockSlider_thumbSize, 0));
            mThumbColor1 = checkIntValue(mThumbColor1, a.getInteger(R.styleable.lockUnlockSlider_colorThumb1, 0));
            mThumbColor2 = checkIntValue(mThumbColor2, a.getInteger(R.styleable.lockUnlockSlider_colorThumb2, 0));
            mBorderWidth = checkIntValue(mBorderWidth, a.getInteger(R.styleable.lockUnlockSlider_borderWidth, 0));
            mBorderColor = checkIntValue(mBorderColor, a.getInteger(R.styleable.lockUnlockSlider_borderColor, 0));
            mAngle = checkIntValue(mAngle, a.getInteger(R.styleable.lockUnlockSlider_angle, 0));
            mBackgroundColorWhenLock = checkIntValue(mBackgroundColorWhenLock, a.getInteger(R.styleable.lockUnlockSlider_backgroundWhenLock, 0));
            mBackgroundColorWhenUnlock = checkIntValue(mBackgroundColorWhenUnlock, a.getInteger(R.styleable.lockUnlockSlider_backgroundWhenUnLock, 0));
            mTextForSliderWhenLock = checkText(a.getString(R.styleable.lockUnlockSlider_textWhenLock));
            mTextForSliderWhenUnlock = checkText(a.getString(R.styleable.lockUnlockSlider_textWhenUnLock));
            mTextSize = checkIntValue(mTextSize, a.getInteger(R.styleable.lockUnlockSlider_textSize, 0));
            mTextColor = checkIntValue(mTextColor, a.getInteger(R.styleable.lockUnlockSlider_textColor, 0));

            if(a.getDrawable(R.styleable.lockUnlockSlider_imageThumbWhenUnLock)!=null)
                mThumbForward = a.getDrawable(R.styleable.lockUnlockSlider_imageThumbWhenUnLock);
            if(a.getDrawable(R.styleable.lockUnlockSlider_imageThumbWhenLock)!=null)
                mThumbBack = a.getDrawable(R.styleable.lockUnlockSlider_imageThumbWhenLock);
        }



        mSliderStatusDescription.setTypeface(null, Typeface.BOLD);
        mSliderStatusDescription.setTextSize(mTextSize);
        mSliderStatusDescription.setTextColor(mTextColor);
        mSliderStatusDescription.setText(mSliderStatus ? mTextForSliderWhenUnlock : mTextForSliderWhenLock);

        mSlider.setProgress(mSliderStatus ? MAX_VALUE : MIN_VALUE);

        initializedSlider();
    }

    /*default slider parameters when init first time*/
    private void setDefaultParametersForSlider(){
        //set the transparent background
        setSeekBarBackgroundTransparent();
        // setting parameters for slider
        mThumbSize = 60;
        //default form for shape
        mThumbAngle = dpToPx(45);
        mTextSize = 12;
        mTextColor = Color.WHITE;

        //default background colors
        mAngle = dpToPx(45);
        mBackgroundColorWhenLock = Color.GRAY;
        mBorderWidth = 1;
        mBorderColor = Color.GRAY;
        mBackgroundWhenLock = createDrawableForBackground(
                mAngle,
                mBackgroundColorWhenLock,
                mBorderWidth,
                mBorderColor);

        mAngle = dpToPx(45);
        mBackgroundColorWhenUnlock = Color.GREEN;
        mBorderWidth = 1;
        mBorderColor = Color.GRAY;
        mBackgroundWhenUnlock = createDrawableForBackground(
                mAngle,
                mBackgroundColorWhenUnlock,
                mBorderWidth,
                mBorderColor);

        //default thumb
        mThumbForward = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lock_open_black_24dp, null);
        mThumbBack = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_lock_outline_black_24dp, null);

        mThumbColor1 = Color.WHITE;
        mThumbColor2 = Color.GRAY;
    }

    private void initializedSlider(){
        updateSlider();

        mSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(progress == MIN_VALUE || progress == MAX_VALUE){
                    switch (progress){
                        case MIN_VALUE:
                            mSliderStatus = false;
                            if (mOnLockUnlockListener != null) mOnLockUnlockListener.onLock();
                            break;

                        case MAX_VALUE:
                            mSliderStatus = true;
                            if (mOnLockUnlockListener != null) mOnLockUnlockListener.onUnlock();
                            break;
                    }
                   updateSlider();
               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSliderStatusDescription.setVisibility(View.INVISIBLE);
                //initializedSlider animation when the progress > 50%
                startTransitionDrawableByStatus();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //initializedSlider reverse animation when the progress < 50%
                reverseTransitionDrawableByStatus();
                //initializedSlider/stop the thumb animation
                runThumbAnimation();
            }
        });
    }

    /*set visual parameters after status changing*/
    private void updateSlider(){
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
        gd.setSize(dpToPx(mThumbSize), dpToPx(mThumbSize));
        // Set gradient radius
        gd.setGradientRadius(dpToPx(70));
        // set the thumb icon by bool status
        Bitmap bitmap = mSliderStatus ?
                ((BitmapDrawable) mThumbForward).getBitmap() :
                ((BitmapDrawable) mThumbBack).getBitmap();

        //result thumb drawable
        Drawable thumb = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, dpToPx(24), dpToPx(24), true));
        InsetDrawable thumb_with_padding= new InsetDrawable(thumb,dpToPx(15),dpToPx(15),dpToPx(15),dpToPx(15));
        //show icon in shape. Just in the versoin of android > 4.1
        return new LayerDrawable(new Drawable[]{gd,thumb_with_padding});
    }

    /*visual parameters when slider progress 100% or 0%*/
    private void setSliderParamWhenStatic(){
        mSliderStatusDescription.setVisibility(View.VISIBLE);
        mBackgroundWhenLock = createDrawableForBackground(mAngle, mBackgroundColorWhenLock, mBorderWidth, mBorderColor);
        mBackgroundWhenUnlock = createDrawableForBackground(mAngle, mBackgroundColorWhenUnlock, mBorderWidth, mBorderColor);

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

    private int checkIntValue(int source, int value){
        if(value != 0){
            source = value;
        }
        return source;
    }

    private String checkText(String value){
        if(value == null){
            return "";
        }
        return value;
    }


    public interface OnLockUnlockListener {

        void onLock();

        void onUnlock();
    }
}
