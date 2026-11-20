package com.xcore.remote.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {

    //    public enum DrawType {
    //        BUTT    (0),
    //        ROUND   (1),
    //        SQUARE  (2);
    //
    //        private Cap(int nativeInt) {
    //            this.nativeInt = nativeInt;
    //        }
    //        final int nativeInt;
    //    }

    public DrawView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制规划路径节点
        Paint mPaint_node = new Paint();
        mPaint_node.setStrokeWidth(15);
        mPaint_node.setColor(Color.RED);
        mPaint_node.setStrokeCap(Paint.Cap.ROUND);
        float[] node_points = new float[] {10, 100, 480, 350, 330, 500, 510, 650, 780, 800, 450, 900, 450, 1200, 550, 1400, 600, 1500};
        canvas.drawPoints(node_points, mPaint_node);

        // 绘制规划路径线条
        Paint mPaint_path = new Paint();
        mPaint_path.setStrokeWidth(10);
        mPaint_path.setColor(Color.BLUE);
        mPaint_path.setAntiAlias(true);
        mPaint_path.setTextSize(36);
        float[] path_points = new float[] {450, 100, 480, 350, 480, 350, 330, 500, 330, 500, 510, 650, 510, 650, 780, 800, 780, 800, 450, 900, 450, 900, 450, 1200, 450, 1200, 550, 1400, 550, 1400, 600, 1500}; //至少4个值，即能够绘制一条直线
        canvas.drawLines(path_points, mPaint_path);
    }

    public void draw_quadrate_map(int map_w, int map_h, float[] path_points) {
        //画布边框
        //Paint mPaint_border = new Paint();
        //mPaint_border.setColor(Color.RED);
        //canvas.drawRect();

        //绘制规划路径线条
        Paint mPaint_path = new Paint();
        mPaint_path.setStrokeWidth(10);
        mPaint_path.setColor(Color.BLUE);
        mPaint_path.setAntiAlias(true);
        mPaint_path.setTextSize(36);

        float scale = 1;
        float[] path_points_scale = new float[path_points.length];
        for (int i = 0; i < path_points.length; i++) {
            path_points_scale[i] = scale * path_points[i];
        }

        // 请求重绘
        invalidate();
    }
}
