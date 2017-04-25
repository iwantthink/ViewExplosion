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
    static Random random = new Random();
    float mRadius = FlyawayFactory.PART_WH;//小球半径
    float alpha;
    Rect mBound;
    float ox, oy;
    float mBigRadius;//距离中心的距离
    float mCenterX;
    float mCenterY;
    Utils.Polar mPolar;
    float mRandomAngle;

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

    public FlyawayParticle(int color, float x, float y, float radius, Rect bound) {
        super(color, x, y);
        ox = x;
        oy = y;
        mBound = bound;
        mRadius = radius;
        mBigRadius = 0;
        mCenterX = bound.centerX();
        mCenterY = bound.centerY();
        mRandomAngle = random.nextInt(360);
    }


    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha)); //这样透明颜色就不是黑色了
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        canvas.drawCircle(cx, cy, mRadius, paint);
        canvas.restore();
    }

    @Override
    protected void caculate(float factor) {
//        cx = ox + factor * random.nextInt(mBound.width()) * (random.nextFloat() - 0.5f);
//        cy = oy + factor * random.nextInt(mBound.height()) * (random.nextFloat() - 0.5f);
        mBigRadius++;
        Log.d("FlyawayParticle", "mRandomAngle:" + mRandomAngle);
        if (mRandomAngle < 90 && mRandomAngle > 0) {
            cx = (float) (mBigRadius * Math.sin(mRandomAngle));
            cy = (float) (mBigRadius * Math.cos(mRandomAngle));
        }

        mPolar = Utils.RectToPolar(cx, cy);


//        mRadius = mRadius - factor * random.nextInt(2);
        alpha = 1;
//        alpha = (1f - factor) * (1 + random.nextFloat());
    }

    public double getLength(float x1, float y1, float x2, float y2) {
        double x = (x1 - x2) * (x1 - x2);
        double y = (y1 - y2) * (y1 - y2);
        return Math.sqrt(x + y);
    }

}
