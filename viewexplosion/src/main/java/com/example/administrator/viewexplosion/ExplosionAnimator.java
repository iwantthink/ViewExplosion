package com.example.administrator.viewexplosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.administrator.viewexplosion.factory.ParticleFactory;
import com.example.administrator.viewexplosion.particle.Particle;


/**
 * Created by Administrator on 2015/11/28 0028.
 */
public class ExplosionAnimator extends ValueAnimator {
    public static final int DEFAULT_DURATION = 0x400;
    private Particle[][] mParticles;
    private Paint mPaint;
    private View mContainer;
    private ParticleFactory mParticleFactory;
    private Rect mRectBound;

    public ExplosionAnimator(View view, Bitmap bitmap, Rect bound, ParticleFactory particleFactory) {
        mParticleFactory = particleFactory;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContainer = view;
        mRectBound = bound;
        setFloatValues(0.0f, 1f);
//        setDuration(DEFAULT_DURATION);
//        setDuration(INFINITE);
        setRepeatCount(INFINITE);
        setRepeatMode(REVERSE);
        mParticles = mParticleFactory.generateParticles(bitmap, bound);
    }

    public void draw(Canvas canvas) {
        if (!isStarted()) { //动画结束时停止
            return;
        }
        //所有粒子运动
        for (Particle[] particle : mParticles) {
            for (Particle p : particle) {
                if (null != p) {
                    p.advance(canvas, mPaint, (Float) getAnimatedValue());
                }
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
