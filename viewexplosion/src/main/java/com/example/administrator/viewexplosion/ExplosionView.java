package com.example.administrator.viewexplosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.administrator.viewexplosion.factory.ParticleFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 2015/11/28 0028.
 */
public class ExplosionView extends View {
    private static final String TAG = ExplosionView.class.getSimpleName();
    private ArrayList<ExplosionAnimator> explosionAnimators;
    private HashMap<View, ExplosionAnimator> explosionAnimatorsMap;
    private OnClickListener onClickListener;
    private ParticleFactory mParticleFactory;
    private Paint mPaint;
    private Random mRandom;
    private MODE mMode = MODE.ANNULUS;

    public enum MODE {EXPLOSION, ANNULUS}


    public ExplosionView(Context context, ParticleFactory particleFactory) {
        super(context);
        init(particleFactory);
    }

    public ExplosionView(Context context, AttributeSet attrs, ParticleFactory particleFactory) {
        super(context, attrs);
        init(particleFactory);
    }

    private void init(ParticleFactory particleFactory) {
        explosionAnimators = new ArrayList<ExplosionAnimator>();
        explosionAnimatorsMap = new HashMap<View, ExplosionAnimator>();
        mParticleFactory = particleFactory;
        attach2Activity((Activity) getContext());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mRandom = new Random();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (mMode == MODE.ANNULUS) {
//            drawAnnulus(canvas);
//        } else {
//            drawPoint(canvas);
//        }
        drawBitmap(canvas);
        drawLine(canvas);
    }

    private void drawAnnulus(Canvas canvas) {
        if (null != mRect) {
            calculateAnnulusRadius();
            canvas.save();
            canvas.translate(mRect.centerX(), mRect.centerY());
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.BLUE);
            for (float annulusRadius : mAnnulusRadius) {
                float percent = 1 - annulusRadius / ((float) mRect.width() / 4 * 3);
                mPaint.setAlpha((int) (percent * 255));
                canvas.drawCircle(0, 0, annulusRadius, mPaint);
            }
            canvas.restore();
            invalidate();
        }
    }

    private float mAnnulusSpeed = 0.5f;

    private void calculateAnnulusRadius() {
        for (int i = 0; i < mAnnulusRadius.size(); i++) {
            float radius = mAnnulusRadius.get(i) + mAnnulusSpeed;
            mAnnulusRadius.set(i, radius);
            if (radius > mRect.width() / 4 * 3) {
                mAnnulusRadius.remove(i);
            }
        }
        if (mAnnulusRadius.get(0) > mAnnulusGap) {
            mAnnulusRadius.add(0, 0f);
        }

    }

    private void drawLine(Canvas canvas) {
        if (null != mRect) {
            canvas.save();
            canvas.translate(mRect.centerX(), mRect.centerY());
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(Utils.dp2Px(2));
            LinearGradient linearGradient =
                    new LinearGradient(0, -mRect.width() + mRect.width() / 8,
                            0, -mRect.width() - 100,
                            Color.TRANSPARENT, Color.BLACK, Shader.TileMode.REPEAT);
            mPaint.setShader(linearGradient);
            mPaint.setColor(Color.RED);
            for (int i = 0; i < 36; i++) {
                canvas.rotate(10);
                canvas.drawLine(0, -mRect.width() + mRect.width() / 8,
                        0, mOuterLineLth[i], mPaint);
            }
            mPaint.setShader(null);
            canvas.restore();
        }
    }

    private void drawBitmap(Canvas canvas) {
        if (null != mRect && null != mBitmap) {
            canvas.save();
            canvas.translate(mRect.centerX(), mRect.centerY());
            mPaint.setColor(Color.argb(125, 255, 0, 0));
//            mPaint.setMaskFilter(new BlurMaskFilter(Utils.dp2Px(1), BlurMaskFilter.Blur.SOLID));
            mPaint.setShadowLayer(Utils.dp2Px(5), 10, 10, Color.BLACK);
            canvas.drawRect(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, mPaint);
//            mPaint.setMaskFilter(null);
            mPaint.clearShadowLayer();
            mPaint.setColor(Color.BLACK);
            canvas.drawBitmap(mBitmap, -mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2, mPaint);
            canvas.restore();
        }
    }

    private void drawPoint(Canvas canvas) {
        for (ExplosionAnimator animator : explosionAnimators) {
            animator.draw(canvas);
        }
    }

    public void setMode(MODE mode) {
        mMode = mode;
    }

    private ArrayList<Float> mAnnulusRadius = new ArrayList<>();
    private Rect mRect;
    private Bitmap mBitmap;
    private int[] mOuterLineLth = new int[36];
    private float mAnnulusGap = Utils.dp2Px(15);

    public void explode(final View view) {
        //防止重复点击
        if (explosionAnimatorsMap.get(view) != null && explosionAnimatorsMap.get(view).isStarted()) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE || view.getAlpha() == 0) {
            return;
        }
        if (mSrcId != -1) {
            mBitmap = BitmapFactory.decodeResource(getResources(), mSrcId);
        }

        mRect = new Rect();
        view.getGlobalVisibleRect(mRect); //得到view相对于整个屏幕的坐标
        if (getParent() == null) {
            attach2Activity((Activity) getContext());
        }
        int contentTop = ((ViewGroup) getParent()).getTop();
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        mRect.offset(0, -contentTop - statusBarHeight);//去掉状态栏高度和标题栏高度
        if (mRect.width() == 0 || mRect.height() == 0) {
            return;
        }
        Log.d(TAG, "mRect.left:" + mRect.left);
        Log.d(TAG, "mRect.right:" + mRect.right);
        Log.d(TAG, "mRect.top:" + mRect.top);
        Log.d(TAG, "mRect.bottom:" + mRect.bottom);

        for (int i = 0; i < mOuterLineLth.length; i++) {
            mOuterLineLth[i] = mRandom.nextInt(Utils.dp2Px(20)) -
                    mRect.width() - mRect.width() / 3;
        }

        if (mRect != null) {
            for (int i = 0; i < 3; i++) {
                mAnnulusRadius.add(mAnnulusGap * i);
            }
        }


        explode(view, mRect);

        //震动动画
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            Random random = new Random();
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
////                view.setTranslationX((random.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
////                view.setTranslationY((random.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
//            }
//        });
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                explode(view, rect);
//            }
//        });
//        animator.start();
    }


    private void explode(final View view, Rect rect) {
        final ExplosionAnimator animator = new ExplosionAnimator(this, Utils.createBitmapFromView(view), rect, mParticleFactory);
        explosionAnimators.add(animator);
        explosionAnimatorsMap.put(view, animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //缩小,透明动画
//                view.animate().setDuration(400).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(400).start();

                //动画结束时从动画集中移除
                explosionAnimators.remove(animation);
                explosionAnimatorsMap.remove(view);
                animation = null;
            }
        });
        animator.start();
    }

    public void stopAnim(Activity activity, View target) {
        ExplosionAnimator animator = explosionAnimators.remove(0);
        animator.cancel();
        explosionAnimatorsMap.remove(target);
        mAnnulusRadius.clear();
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        rootView.removeView(this);
    }

    public boolean isRunning() {
        boolean isRunning = false;
        if (explosionAnimators.size() > 0) {
            ExplosionAnimator animator = explosionAnimators.get(0);
            isRunning = animator.isRunning();
        }
        return isRunning;
    }

    /**
     * 给Activity加上全屏覆盖的ExplosionField
     */
    private void attach2Activity(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this, lp);
    }


    /**
     * 希望谁有破碎效果，就给谁加Listener
     *
     * @param view 可以是ViewGroup
     */
    public void addListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }


    private OnClickListener getOnClickListener() {
        if (null == onClickListener) {
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExplosionView.this.explode(v);
                }
            };
        }
        return onClickListener;
    }

    private int mSrcId = -1;

    public void setSrc(int resourceId) {
        mSrcId = resourceId;
    }
}
