package com.example.administrator.viewexplosion.factory;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;

import com.example.administrator.viewexplosion.particle.FlyawayParticle;
import com.example.administrator.viewexplosion.particle.Particle;

import java.util.Random;


/**
 * Created by Administrator on 2015/11/29 0029.
 */
public class FlyawayFactory extends ParticleFactory {
    public static final int PART_WH = 8; //默认小球宽高
    private static Random sRandom = new Random();
    public static DecelerateInterpolator mInterpolator = new DecelerateInterpolator();

    /**
     * @param bitmap
     * @param bound  代表原来view空间的宽高信息
     * @return
     */
    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width(); //场景宽度
        int h = bound.height();//场景高度
        Log.d("FlyawayFactory", "w:" + w);
        Log.d("FlyawayFactory", "h:" + h);
        Log.d("FlyawayFactory", "bound.centerX():" + bound.centerX());
        Log.d("FlyawayFactory", "bound.centerY():" + bound.centerY());

        int partW_Count = w / PART_WH; //横向个数
        int partH_Count = h / PART_WH; //竖向个数
        Log.d("FlyawayFactory", "partH_Count:" + partH_Count);
        Log.d("FlyawayFactory", "partW_Count:" + partW_Count);
//        int bitmap_part_w = bitmap.getWidth() / partW_Count;
//        int bitmap_part_h = bitmap.getHeight() / partH_Count;
        int color = Color.argb(255, 0, 0, 0);
        Particle[][] particles = new Particle[partH_Count][partW_Count];

//        for (int i = 0; i < 1; i++) {
//            particles[0][i] = new FlyawayParticle(color, 0, 0,
//                    PART_WH - sRandom.nextInt(4),
//                    sRandom.nextInt(360),
//                    1,
//                    bound);
//        }

        generate(bound, partW_Count, partH_Count, color, particles);

        return particles;
    }

    private void generate(Rect bound, int partW_Count, int partH_Count, int color, Particle[][] particles) {

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < partH_Count; row++) { //行
            if (row % 3 != 0) {
                continue;
            }
            for (int column = 0; column < partW_Count; column++) { //列
                //取得当前粒子所在位置的颜色
//                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);
                if (column % 3 == 0) {
//                    float x = bound.left + FlyawayFactory.PART_WH * column;
//                    float y = bound.top + FlyawayFactory.PART_WH * row;
                    float radius = PART_WH - sRandom.nextInt(6);//小球大小
                    float randomAngle = sRandom.nextInt(360);
//                    float startRadius = bound.width() > bound.height() ? bound.height() / 5 * 2 : bound.width() / 5 * 2;
                    float startRadius = bound.width() > bound.height() ? bound.height() / 2 :
                            bound.width() / 2;
//                    float startRadius = 11;
                    startRadius -= sRandom.nextInt(bound.width() / 2);
                    sb.append("row = " + row + ",column = " + column);
                    sb.append(" randomAngle = " + randomAngle);
                    sb.append("\n");
                    particles[row][column] = new FlyawayParticle(color, 0, 0, radius,
                            randomAngle, startRadius, bound);
                }
            }
        }
    }

}
