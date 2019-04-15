package com.example.abhijeetsingh.udid;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class HorizontalDottedProgress extends View {
    private int mDotRadius = 10;
    private int mBounceDotRadius =15;
    private int mDotAmount = 4;
    private int mDotPosition;

    public HorizontalDottedProgress(Context context) {
        super(context);
    }
    public HorizontalDottedProgress(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalDottedProgress(Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Method to draw your customized dot on the canvas
    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        android.graphics.Paint paint = new android.graphics.Paint();

        //set the color for the dot that you want to draw
        paint.setColor(getResources().getColor(R.color.colorPrimary));

        //function to create dot
        createDot(canvas,paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //Animation called when attaching to the window, i.e to your screen
        startAnimation();
    }

    private void createDot(android.graphics.Canvas canvas, android.graphics.Paint paint) {

        //here i have setted progress bar with 10 dots , so repeat and wnen i = mDotPosition  then increase the radius of dot i.e mBounceDotRadius
        for(int i = 0; i < mDotAmount; i++ ){
            if(i == mDotPosition){
                canvas.drawCircle(50+(i*70), mBounceDotRadius, mBounceDotRadius, paint); //SPACING BETWEEN DOTS
            }else {
                canvas.drawCircle(50+(i*70), mBounceDotRadius, mDotRadius, paint);
            }
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;

        //calculate the view width
        int calculatedWidth = (70*4);

        width = calculatedWidth;
        height = (mBounceDotRadius*2);
        setMeasuredDimension(width, height);
    }

    private void startAnimation() {
        BounceAnimation bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(100); //CHANGED FROM 100 TO 50
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation  animation) {
                mDotPosition++;
                //when mDotPosition == mDotAmount , then start again applying animation from 0th positon , i.e  mDotPosition = 0;
                if (mDotPosition == mDotAmount) {
                    mDotPosition = 0;
                }
                android.util.Log.d("INFOMETHOD","----On Animation Repeat----");

            }
        });
        startAnimation(bounceAnimation);
    }


    private class BounceAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, android.view.animation.Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //call invalidate to redraw your view again.
            invalidate();
        }
    }
}

