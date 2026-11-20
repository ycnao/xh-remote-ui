package com.xcore.remote.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class PathView extends View {

    private Paint textPaint;
    private Paint readyPaint;
    private Paint plannedPaint;
    private Paint cleanedPaint;
    private Paint endPointPaint;
    private List<float[]> plannedPath;
    private List<float[]> cleanedPath;
    private Path readyPathObj = new Path();
    private Path plannedPathObj = new Path();
    private Path cleanedPathObj = new Path();
    private float[] currentEndPoint;
    private float paint_width = 5;
    private int readyTimeCnt = 0;
    private String display_text;

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(8);
        textPaint.setTextSize(45);
        textPaint.setTextAlign(Paint.Align.CENTER);

        readyPaint = new Paint();
        readyPaint.setColor(Color.RED);
        readyPaint.setStyle(Paint.Style.STROKE);
        readyPaint.setStrokeWidth(15);
        readyPaint.setAntiAlias(true);

        plannedPaint = new Paint();
        plannedPaint.setColor(Color.BLUE);
        plannedPaint.setStyle(Paint.Style.STROKE);
        plannedPaint.setStrokeWidth(paint_width);
        plannedPaint.setAntiAlias(true);

        cleanedPaint = new Paint();
        cleanedPaint.setColor(Color.GRAY);
        cleanedPaint.setStyle(Paint.Style.STROKE);
        cleanedPaint.setStrokeWidth(paint_width);
        cleanedPaint.setAntiAlias(true);

        endPointPaint = new Paint();
        endPointPaint.setColor(Color.RED);
        endPointPaint.setStyle(Paint.Style.FILL);
        endPointPaint.setAntiAlias(true);
    }

    public void updateTextInfo(String info_value) {
        display_text = info_value;
        invalidate();
    }

    public void updateReadyLengthPaths(String length_value) {
        final int SCREEN_WIDTH = 1920;
        final int SCREEN_HEIGHT = 950;

        final int MARGIN = 20;

        float centerY = SCREEN_HEIGHT / 2f;
        float startX = MARGIN;
        float endX = SCREEN_WIDTH - MARGIN;

        final int ARROW_SIZE = 60;
        final int TEXT_WIDTH = 700;
        final int GAP_WIDTH = 50;
        int LINE_WIDTH = (SCREEN_WIDTH - 2 * MARGIN - TEXT_WIDTH - 4 * GAP_WIDTH) / 6;

        display_text = length_value;

        int ready_line_state = (readyTimeCnt++) / 5;
        readyPathObj.reset();
        switch (ready_line_state) {
            case 0:
                readyPathObj.moveTo(startX + 2 * LINE_WIDTH + 2 * GAP_WIDTH, centerY);
                readyPathObj.lineTo(startX + 3 * LINE_WIDTH + 2 * GAP_WIDTH, centerY);

                readyPathObj.moveTo(endX - 2 * LINE_WIDTH - 2 * GAP_WIDTH, centerY);
                readyPathObj.lineTo(endX - 3 * LINE_WIDTH - 2 * GAP_WIDTH, centerY);
                break;
            case 1:
                readyPathObj.moveTo(startX + 2 * LINE_WIDTH + 2 * GAP_WIDTH, centerY);
                readyPathObj.lineTo(startX + 3 * LINE_WIDTH + 2 * GAP_WIDTH, centerY);

                readyPathObj.moveTo(endX - 2 * LINE_WIDTH - 2 * GAP_WIDTH, centerY);
                readyPathObj.lineTo(endX - 3 * LINE_WIDTH - 2 * GAP_WIDTH, centerY);

                readyPathObj.moveTo(startX + LINE_WIDTH + GAP_WIDTH, centerY);
                readyPathObj.lineTo(startX + 2 * LINE_WIDTH + GAP_WIDTH, centerY);

                readyPathObj.moveTo(endX - LINE_WIDTH - GAP_WIDTH, centerY);
                readyPathObj.lineTo(endX - 2 * LINE_WIDTH - GAP_WIDTH, centerY);
                break;
            case 2:
                readyPathObj.moveTo(startX + 2 * LINE_WIDTH + 2 * GAP_WIDTH, centerY);
                readyPathObj.lineTo(startX + 3 * LINE_WIDTH + 2 * GAP_WIDTH, centerY);

                readyPathObj.moveTo(endX - 2 * LINE_WIDTH - 2 * GAP_WIDTH, centerY);
                readyPathObj.lineTo(endX - 3 * LINE_WIDTH - 2 * GAP_WIDTH, centerY);

                readyPathObj.moveTo(startX + LINE_WIDTH + GAP_WIDTH, centerY);
                readyPathObj.lineTo(startX + 2 * LINE_WIDTH + GAP_WIDTH, centerY);

                readyPathObj.moveTo(endX - LINE_WIDTH - GAP_WIDTH, centerY);
                readyPathObj.lineTo(endX - 2 * LINE_WIDTH - GAP_WIDTH, centerY);

                readyPathObj.moveTo(startX, centerY);
                readyPathObj.lineTo(startX + LINE_WIDTH, centerY);

                readyPathObj.moveTo(endX, centerY);
                readyPathObj.lineTo(endX - LINE_WIDTH, centerY);

                readyPathObj.moveTo(startX + ARROW_SIZE, centerY - ARROW_SIZE);
                readyPathObj.lineTo(startX, centerY);
                readyPathObj.lineTo(startX + ARROW_SIZE, centerY + ARROW_SIZE);

                readyPathObj.moveTo(endX - ARROW_SIZE, centerY - ARROW_SIZE);
                readyPathObj.lineTo(endX, centerY);
                readyPathObj.lineTo(endX - ARROW_SIZE, centerY + ARROW_SIZE);
                break;
            case 3:
            default:
                readyTimeCnt = 0;
                break;
        }

        invalidate();
    }

    public void updateReadyWidthPaths(String width_value) {
        final int SCREEN_WIDTH = 1920;
        final int SCREEN_HEIGHT = 950;

        final int MARGIN = 20;

        float centerX = SCREEN_WIDTH / 2f;
        float startY = MARGIN;
        float endY = SCREEN_HEIGHT - MARGIN;

        final int ARROW_SIZE = 60;
        final int TEXT_WIDTH = 100;
        final int GAP_WIDTH = 30;
        int LINE_WIDTH = (SCREEN_HEIGHT - 2 * MARGIN - TEXT_WIDTH - 4 * GAP_WIDTH) / 6;

        display_text = width_value;

        int ready_line_state = (readyTimeCnt++) / 5;
        readyPathObj.reset();
        switch (ready_line_state) {
            case 0:
                readyPathObj.moveTo(centerX, startY + 2 * LINE_WIDTH + 2 * GAP_WIDTH);
                readyPathObj.lineTo(centerX, startY + 3 * LINE_WIDTH + 2 * GAP_WIDTH);

                readyPathObj.moveTo(centerX, endY - 2 * LINE_WIDTH - 2 * GAP_WIDTH);
                readyPathObj.lineTo(centerX, endY - 3 * LINE_WIDTH - 2 * GAP_WIDTH);
                break;
            case 1:
                readyPathObj.moveTo(centerX, startY + 2 * LINE_WIDTH + 2 * GAP_WIDTH);
                readyPathObj.lineTo(centerX, startY + 3 * LINE_WIDTH + 2 * GAP_WIDTH);

                readyPathObj.moveTo(centerX, endY - 2 * LINE_WIDTH - 2 * GAP_WIDTH);
                readyPathObj.lineTo(centerX, endY - 3 * LINE_WIDTH - 2 * GAP_WIDTH);

                readyPathObj.moveTo(centerX, startY + LINE_WIDTH + GAP_WIDTH);
                readyPathObj.lineTo(centerX, startY + 2 * LINE_WIDTH + GAP_WIDTH);

                readyPathObj.moveTo(centerX, endY - LINE_WIDTH - GAP_WIDTH);
                readyPathObj.lineTo(centerX, endY - 2 * LINE_WIDTH - GAP_WIDTH);
                break;
            case 2:
                readyPathObj.moveTo(centerX, startY + 2 * LINE_WIDTH + 2 * GAP_WIDTH);
                readyPathObj.lineTo(centerX, startY + 3 * LINE_WIDTH + 2 * GAP_WIDTH);

                readyPathObj.moveTo(centerX, endY - 2 * LINE_WIDTH - 2 * GAP_WIDTH);
                readyPathObj.lineTo(centerX, endY - 3 * LINE_WIDTH - 2 * GAP_WIDTH);

                readyPathObj.moveTo(centerX, startY + LINE_WIDTH + GAP_WIDTH);
                readyPathObj.lineTo(centerX, startY + 2 * LINE_WIDTH + GAP_WIDTH);

                readyPathObj.moveTo(centerX, endY - LINE_WIDTH - GAP_WIDTH);
                readyPathObj.lineTo(centerX, endY - 2 * LINE_WIDTH - GAP_WIDTH);

                readyPathObj.moveTo(centerX, startY);
                readyPathObj.lineTo(centerX, startY + LINE_WIDTH);

                readyPathObj.moveTo(centerX, endY);
                readyPathObj.lineTo(centerX, endY - LINE_WIDTH);

                readyPathObj.moveTo(centerX - ARROW_SIZE, startY + ARROW_SIZE);
                readyPathObj.lineTo(centerX, startY);
                readyPathObj.lineTo(centerX + ARROW_SIZE, startY + ARROW_SIZE);

                readyPathObj.moveTo(centerX - ARROW_SIZE, endY - ARROW_SIZE);
                readyPathObj.lineTo(centerX, endY);
                readyPathObj.lineTo(centerX + ARROW_SIZE, endY - ARROW_SIZE);
                break;
            case 3:
            default:
                readyTimeCnt = 0;
                break;
        }

        invalidate();
    }

    public void updateReadyFirsPaths(String width_value) {
        final int SCREEN_WIDTH = 1920;
        final int SCREEN_HEIGHT = 950;

        final int MARGIN = 50;

        float startX = MARGIN;
        float startY = MARGIN;
        float endX = SCREEN_WIDTH / 2f - MARGIN;
        float endY = SCREEN_HEIGHT / 2f - MARGIN;

        float X_LINE_WIDTH = (endX - startX);
        float Y_LINE_WIDTH = (endY - startY);
        final int ratio = 11;

        final int ARROW_SIZE = 60;

        display_text = width_value;

        int ready_line_state = (readyTimeCnt++) / 5;
        readyPathObj.reset();
        switch (ready_line_state) {
            case 0:
                readyPathObj.moveTo(endX, endY);
                readyPathObj.lineTo(endX - X_LINE_WIDTH * (3 / 11f), endY - Y_LINE_WIDTH * (3 / 11f));
                break;
            case 1:
                readyPathObj.moveTo(endX, endY);
                readyPathObj.lineTo(endX - X_LINE_WIDTH * (3 / 11f), endY - Y_LINE_WIDTH * (3 / 11f));

                readyPathObj.moveTo(endX - X_LINE_WIDTH * (4 / 11f), endY - Y_LINE_WIDTH * (4 / 11f));
                readyPathObj.lineTo(endX - X_LINE_WIDTH * (7 / 11f), endY - Y_LINE_WIDTH * (7 / 11f));
                break;
            case 2:
                readyPathObj.moveTo(endX, endY);
                readyPathObj.lineTo(endX - X_LINE_WIDTH * (3 / 11f), endY - Y_LINE_WIDTH * (3 / 11f));

                readyPathObj.moveTo(endX - X_LINE_WIDTH * (4 / 11f), endY - Y_LINE_WIDTH * (4 / 11f));
                readyPathObj.lineTo(endX - X_LINE_WIDTH * (7 / 11f), endY - Y_LINE_WIDTH * (7 / 11f));

                readyPathObj.moveTo(endX - X_LINE_WIDTH * (8 / 11f), endY - Y_LINE_WIDTH * (8 / 11f));
                readyPathObj.lineTo(endX - X_LINE_WIDTH * (11 / 11f), endY - Y_LINE_WIDTH * (11 / 11f));

                readyPathObj.moveTo(startX + ARROW_SIZE, startY);
                readyPathObj.lineTo(startX, startY);
                readyPathObj.lineTo(startX + ARROW_SIZE / 2f, startY + ARROW_SIZE);

                break;
            case 3:
            default:
                readyTimeCnt = 0;
                break;
        }

        invalidate();
    }

    public void updatePlannedPaths(int map_id, float lengthWidthRatio, int line_num) {
        plannedPathObj.reset();

        List<float[]> updatePath = null;
        switch (map_id) {
            case 1:
                updatePath = getPlannedPathList1(lengthWidthRatio, line_num);
                break;
            case 4:
                updatePath = getPlannedPathList4(lengthWidthRatio, line_num);
                break;
            default:
                break;
        }

        if (updatePath != null) {
            this.plannedPath = updatePath;
            if (!plannedPath.isEmpty()) {
                plannedPathObj.moveTo(plannedPath.get(0)[0], plannedPath.get(0)[1]);
                for (float[] point : plannedPath) {
                    plannedPathObj.lineTo(point[0], point[1]);
                }
                paint_width = line_num <= 10 ? 10 : (line_num <= 50 ? 5 : 1);
                plannedPaint.setStrokeWidth(paint_width);
                cleanedPaint.setStrokeWidth(paint_width);
            }

            invalidate();
        }
    }

    public void updateCleanedPaths(int map_id, int line_num, float line_percent) {
        cleanedPathObj.reset();

        List<float[]> updatePath = null;
        switch (map_id) {
            case 1:
            case 4:
            default:
                updatePath = getCleanedPathList(plannedPath, line_num, line_percent);
                break;
        }

        if (updatePath != null) {
            this.cleanedPath = updatePath;
            if (!cleanedPath.isEmpty()) {
                cleanedPathObj.moveTo(cleanedPath.get(0)[0], cleanedPath.get(0)[1]);
                for (float[] point : cleanedPath) {
                    cleanedPathObj.lineTo(point[0], point[1]);
                }
            }
            invalidate();
        }
    }

    public void updateResetReadyPaths() {
        readyPathObj.reset();
        display_text = null;
        readyTimeCnt = 0;
    }

    public void updateResetPathPaths() {
        plannedPathObj.reset();
        cleanedPathObj.reset();
        currentEndPoint = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(readyPathObj, readyPaint);

        if (display_text != null) {
            canvas.drawText(display_text, 960, 490, textPaint);
        }

        canvas.drawPath(plannedPathObj, plannedPaint);

        canvas.drawPath(cleanedPathObj, cleanedPaint);

        if (currentEndPoint != null) {
            float radius = Math.max(5, paint_width * 2);
            canvas.drawCircle(currentEndPoint[0], currentEndPoint[1], radius, endPointPaint);
        }
    }

    private List<float[]> getPlannedPathList1(float lengthWidthRatio, int line_num) {
        if (lengthWidthRatio == 0 || line_num == 0) {
            return null;
        }
        final int SCREEN_WIDTH = 1920;
        final int SCREEN_HEIGHT = 950;

        final int MARGIN = 20;

        int maxWidth = SCREEN_WIDTH - 2 * MARGIN;
        int maxHeight = SCREEN_HEIGHT - 2 * MARGIN;

        int drawWidth = maxWidth;
        int drawHeight = (int) (drawWidth / lengthWidthRatio);

        if (drawHeight > maxHeight) {
            drawHeight = maxHeight;
            drawWidth = (int) (drawHeight * lengthWidthRatio);
        }

        float lineSpacing = (float) drawHeight / (line_num - 1);

        List<float[]> lines = new ArrayList<>();
        float y = (float) (SCREEN_HEIGHT - drawHeight) / 2;

        for (int i = 0; i < line_num; i++) {
            float[] start = new float[]{(SCREEN_WIDTH - drawWidth) / 2, y};
            float[] end = new float[]{(SCREEN_WIDTH - drawWidth) / 2 + drawWidth, y};
            if (i % 2 == 0) {
                lines.add(start);
                lines.add(end);
            } else {
                lines.add(end);
                lines.add(start);
            }
            y += lineSpacing;
        }
        return lines;
    }

    private List<float[]> getPlannedPathList4(float lengthWidthRatio, int line_num) {
        if (lengthWidthRatio == 0 || line_num == 0) {
            return null;
        }
        final int SCREEN_WIDTH = 1920;
        final int SCREEN_HEIGHT = 950;

        final int MARGIN = 20;

        int maxWidth = SCREEN_WIDTH - 2 * MARGIN;
        int maxHeight = SCREEN_HEIGHT - 2 * MARGIN;

        int drawWidth = maxWidth;
        int drawHeight = (int) (drawWidth / lengthWidthRatio);

        if (drawHeight > maxHeight) {
            drawHeight = maxHeight;
            drawWidth = (int) (drawHeight * lengthWidthRatio);
        }

        float lineSpacing = (float) drawWidth / (line_num - 1);

        List<float[]> lines = new ArrayList<>();
        float x = (float) (SCREEN_WIDTH - drawWidth) / 2;

        for (int i = 0; i < line_num; i++) {
            float[] start = new float[]{x, (SCREEN_HEIGHT - drawHeight) / 2 + drawHeight};
            float[] end = new float[]{x, (SCREEN_HEIGHT - drawHeight) / 2};
            lines.add(start);
            lines.add(end);
            lines.add(start);

            x += lineSpacing;
        }
        return lines;
    }

    private List<float[]> getCleanedPathList(List<float[]> points, int line_num, float line_percent) {
        if (line_num <= 0 || line_num >= points.size()) {
            return null;
        }
        if (line_percent < 0 || line_percent > 1) {
            return null;
        }
        float[] p1 = points.get(line_num - 1);
        float[] p2 = points.get(line_num);
        float[] targetPos = new float[]{p1[0] + line_percent * (p2[0] - p1[0]), p1[1] + line_percent * (p2[1] - p1[1])};

        List<float[]> result = new ArrayList<>();
        for (int i = 0; i < line_num; i++) {
            result.add(points.get(i));
        }
        result.add(targetPos);

        currentEndPoint = targetPos;
        return result;
    }

}
