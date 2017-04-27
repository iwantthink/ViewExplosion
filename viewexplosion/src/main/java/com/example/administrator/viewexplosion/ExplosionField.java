package com.example.administrator.viewexplosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.administrator.viewexplosion.factory.ParticleFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/11/28 0028.
 */
public class ExplosionField extends View {
    private static final String TAG = ExplosionField.class.getSimpleName();
    private ArrayList<ExplosionAnimator> explosionAnimators;
    private HashMap<View, ExplosionAnimator> explosionAnimatorsMap;
    private OnClickListener onClickListener;
    private ParticleFactory mParticleFactory;

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        init(particleFactory);
    }

    public ExplosionField(Context context, AttributeSet attrs, ParticleFactory particleFactory) {
        super(context, attrs);
        init(particleFactory);
    }

    private void init(ParticleFactory particleFactory) {
        explosionAnimators = new ArrayList<ExplosionAnimator>();
        explosionAnimatorsMap = new HashMap<View, ExplosionAnimator>();
        mParticleFactory = particleFactory;
        attach2Activity((Activity) getContext());
    }

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        for (ExplosionAnimator animator : explosionAnimators) {
            animator.draw(canvas);
        }
        if (null != mRect && null != mBitmap) {
            canvas.translate(mRect.centerX(), mRect.centerY());
            canvas.drawBitmap(mBitmap, -mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2, mPaint);
        }
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private Rect mRect;
    private Bitmap mBitmap;

    /**
     * 爆破
     *
     * @param view 使得该view爆破
     */
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
        int contentTop = ((ViewGroup) getParent()).getTop();
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        mRect.offset(0, -contentTop - statusBarHeight);//去掉状态栏高度和标题栏高度
        if (mRect.width() == 0 || mRect.height() == 0) {
            return;
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
                    ExplosionField.this.explode(v);
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
