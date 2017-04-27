package com.example.administrator.viewexplosion.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.administrator.viewexplosion.Utils;
import com.example.administrator.viewexplosion.factory.FlyawayFactory;

import java.util.Random;


/**
 * Created by Administrator on 2015/11/29 0029.
 */
public class FlyawayParticle extends Particle {
    float mRadius = FlyawayFactory.PART_WH;//小球半径
    float mAlpha;
    Rect mBound;
    float ox, oy;
    float mOuterRadius;//距离中心的距离
    float mCenterX;
    float mCenterY;
    Utils.Polar mPolar;
    float mRandomAngle;
    float mStartRadius;
    float mMaxRadius;
    float mStartShowRadius;//圆点开始出现的半径
    Random mRandom = new Random();

    /**
     * @param color 颜色
     * @param x
     * @param y
     */
    public FlyawayParticle(int color, float x, float y, Rect bound) {
        super(color, x, y);
        ox = x;
        oy = y;
        mBound = bound;
    }

    public FlyawayParticle(int color, float x, float y, float radius,
                           float randomAngle, float startRadius,
                           Rect bound) {
        super(color, x, y);
        ox = x;
        oy = y;
        mBound = bound;
        mRadius = radius;
        mOuterRadius = startRadius;
        mStartRadius = startRadius;
        mCenterX = bound.centerX();
        mCenterY = bound.centerY();
        mRandomAngle = randomAngle;
        mMaxRadius = mBound.width() > mBound.height() ? mBound.height() : mBound.width();
        mAlpha = mRandom.nextFloat();
        mStartShowRadius = mBound.width() > mBound.height() ? mBound.height() / 2 : mBound.width() / 2;

        Log.d("FlyawayParticle", "x:" + x);
        Log.d("FlyawayParticle", "y:" + y);
        Log.d("FlyawayParticle", "mRadius:" + mRadius);
        Log.d("FlyawayParticle", "startRadius:" + startRadius);
        Log.d("FlyawayParticle", "mCenterX:" + mCenterX);
        Log.d("FlyawayParticle", "mCenterY:" + mCenterY);
        Log.d("FlyawayParticle", "mMaxRadius:" + mMaxRadius);
        Log.d("FlyawayParticle", "mRandomAngle:" + mRandomAngle);
    }


    @Override
    protected void draw(Canvas canvas, Paint paint) {
        drawPoint(canvas, paint);
    }

    private void drawPoint(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * mAlpha)); //这样透明颜色就不是黑色了
        canvas.save();
        canvas.translate(mCenterX, mCenterY);

        paint.setStyle(Paint.Style.FILL);
        if (mRandomAngle < 90 && mRandomAngle > 0) {
            paint.setColor(Color.RED);
            paint.setAlpha((int) (Color.alpha(color) * mAlpha)); //这样透明颜色就不是黑色了
        } else if (mRandomAngle < 180 && mRandomAngle >= 90) {
            paint.setColor(Color.GREEN);
            paint.setAlpha((int) (Color.alpha(color) * mAlpha)); //这样透明颜色就不是黑色了
        } else if (mRandomAngle < 270 && mRandomAngle >= 180) {
            paint.setColor(Color.BLUE);
            paint.setAlpha((int) (Color.alpha(color) * mAlpha)); //这样透明颜色就不是黑色了
        } else {
            paint.setColor(Color.BLACK);
            paint.setAlpha((int) (Color.alpha(color) * mAlpha)); //这样透明颜色就不是黑色了
        }
        if (mOuterRadius < mBound.width() / 2) {
            paint.setColor(Color.TRANSPARENT);
        }

        canvas.drawCircle(cx, cy, mRadius, paint);
        paint.setColor(Color.GRAY);
        paint.setAlpha((int) (Color.alpha(color) * mAlpha)); //这样透明颜色就不是黑色了
        canvas.drawCircle(0, 0, mRadius, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, mMaxRadius, paint);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(0, 0, mStartShowRadius, paint);
        canvas.restore();
    }

    float mFlashFrequency = 0.01f;
    float mMoveSpeed = 0.5f;
    float mFastSpeed = 1f;

    @Override
    protected void caculate(float factor) {
        long start = System.currentTimeMillis();
        if (mOuterRadius < mStartShowRadius) {
            mOuterRadius += mFastSpeed;
        } else {
            mOuterRadius += mMoveSpeed;
        }

        if (mOuterRadius > mMaxRadius) {
            mOuterRadius = mStartRadius;
        }

        if (mOuterRadius > mStartShowRadius) {
            cx = (float) (mOuterRadius * Math.sin(mRandomAngle));
            cy = (float) (mOuterRadius * Math.cos(mRandomAngle));
            if (mRandomAngle < 90 && mRandomAngle > 0) {
                cx = Math.abs(cx);
                cy = Math.abs(cy);
            } else if (mRandomAngle < 180 && mRandomAngle >= 90) {
                cx = -Math.abs(cx);
                cy = Math.abs(cy);
            } else if (mRandomAngle < 270 && mRandomAngle >= 180) {
                cx = -Math.abs(cx);
                cy = -Math.abs(cy);
            } else {
                cx = +Math.abs(cx);
                cy = -Math.abs(cy);
            }
        }

//        Log.d("FlyawayParticle", "mOuterRadius:" + mOuterRadius);

        long end = System.currentTimeMillis();

//        Log.d("FlyawayParticle", "end-start:" + (end - start));

//        Log.d("FlyawayParticle", "cx:" + cx);
//        Log.d("FlyawayParticle", "cy:" + cy);


//        mRadius = mRadius - factor * random.nextInt(2);
        mAlpha = 0.8f;
//        if (mAlpha < 0.2) {
//            mAlpha += mFlashFrequency;
//        } else if (mAlpha > 1) {
//            mAlpha -= mFlashFrequency;
//        } else {
//            if (mRandom.nextFloat() > 0.5) {
//                mAlpha -= mFlashFrequency;
//            } else {
//                mAlpha += mFlashFrequency;
//            }
//        }
//        mAlpha = (1f - factor) * (1 + random.nextFloat());
    }
}
