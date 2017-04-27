package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by renbo on 2017/4/27.
 */

public class HelloView extends View {
    public HelloView(Context context) {
        super(context);
    }

    public HelloView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0, 0, 1000, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(100, 100);
//        Log.d("HelloView", "MeasureSpec.getMode(widthMeasureSpec):" + MeasureSpec.getMode(widthMeasureSpec));
//        Log.d("HelloView", "MeasureSpec.getSize(widthMeasureSpec):" + MeasureSpec.getSize(widthMeasureSpec));
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST ||
                MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            setMeasuredDimension(100, 100);
        } else if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            setMeasuredDimension(100, MeasureSpec.getSize(heightMeasureSpec));
        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 100);
        }
    }
}
