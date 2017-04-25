package com.example.administrator.viewexplosion.factory;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.example.administrator.viewexplosion.particle.FlyawayParticle;
import com.example.administrator.viewexplosion.particle.Particle;

import java.util.Random;


/**
 * Created by Administrator on 2015/11/29 0029.
 */
public class FlyawayFactory extends ParticleFactory {
    public static final int PART_WH = 8; //默认小球宽高
    private static Random sRandom = new Random();

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
        StringBuilder sb = new StringBuilder();
        int color = Color.argb(255, 0, 0, 0);
        Particle[][] particles = new Particle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row++) { //行
            if (row % 5 != 0) {
                continue;
            }
            for (int column = 0; column < partW_Count; column++) { //列
                //取得当前粒子所在位置的颜色
//                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);
                if (column % 5 == 0) {
                    float centerX = bound.centerX();
                    float centerY = bound.centerY();

                    float x = bound.left + FlyawayFactory.PART_WH * column;
                    float y = bound.top + FlyawayFactory.PART_WH * row;
                    float radius = PART_WH - sRandom.nextInt(4);
//                    sb.append("row = " + row + ",column = " + column);
//                    sb.append("   x = " + x + ",y = " + y);
//                    sb.append("\n");

                    particles[row][column] = new FlyawayParticle(color, 0, 0, radius, bound);


                }
            }
        }

//        Log.d("FlyawayFactory", sb.toString());
        return particles;
    }


    public double getLength(float x1, float y1, float x2, float y2) {
        double x = (x1 - x2) * (x1 - x2);
        double y = (y1 - y2) * (y1 - y2);
        return Math.sqrt(x + y);
    }


}
