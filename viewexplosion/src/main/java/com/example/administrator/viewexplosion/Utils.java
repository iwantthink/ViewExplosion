/*
 * Copyright (C) 2015 tyrantgit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.administrator.viewexplosion;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Utils {

    private Utils() {
    }

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
    private static final Canvas sCanvas = new Canvas();

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (sCanvas) {
                Canvas canvas = sCanvas;
                canvas.setBitmap(bitmap);
                view.draw(canvas);
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }

    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }

    public static class Polar {

        public Polar(double radius, double angle) {
            mRadius = radius;
            mAngle = angle;
        }

        public double mRadius;

        public double mAngle;
    }

    public static Polar RectToPolar(double x, double y) {
        double radius = 0;// 极坐标半径
        double angle = -1;// 极坐标夹角
        radius = Math.hypot(x, y);
        if (y >= 0) {
            if (x == 0) {
                angle = Math.PI / 2;// 90°
            } else {
                angle = Math.asin(x / y);
            }
        } else if (y < 0) {
            if (x == 0) {
                angle = 3 * Math.PI / 2;// 270°
            } else {
                angle = Math.asin(x / y);
            }
        }
        return new Polar(radius, angle);
    }
}
