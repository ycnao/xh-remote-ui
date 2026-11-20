package com.xcore.remote.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Locale;
import com.xcore.remote.ui.R;

/**
 * 半圆仪表盘进度控件
 * 进度弧颜色：绿 -> 黄 -> 红 渐变；底部显示百分比文字；支持动画
 */
public class SemiCircleProgressView extends View {

    private static final float START_ANGLE = 180f; // 180° = 9点钟方向
    private static final float TOTAL_SWEEP = 180f; // 半圆

    private float max = 100f;
    private float progress = 0f;

    private int startColor  = 0xFF61A83B; // 绿
    private int middleColor = 0xFFEFD426; // 黄
    private int endColor    = 0xFFE53935; // 红
    private int trackColor  = 0xFFE9E9E9; // 灰色轨道
    private int textColor   = 0xFF61A83B;

    /** 黄色在整个半圆中的位置(0~1) */
    private float middlePosition = 0.6f;

    private float strokeWidth;
    private float textSize;
    private float textGap;

    private boolean textVisible = true;
    private boolean animateOnAttach = true;
    private boolean hasAnimatedOnAttach = false;

    private final Paint trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    private final RectF arcRect = new RectF();
    private final Matrix shaderMatrix = new Matrix();

    private float cx, cy, radius;

    private ValueAnimator animator;

    /** 文字格式化器，默认显示成 69.5% 这种形式 */
    public interface TextFormatter {
        String format(float progress, float max);
    }

    private TextFormatter textFormatter = new TextFormatter() {
        @Override
        public String format(float progress, float max) {
            float percent = max <= 0 ? 0f : progress * 100f / max;
            float rounded = Math.round(percent * 10f) / 10f;
            if (rounded == Math.rint(rounded)) {
                return String.format(Locale.US, "%.0f%%", rounded);
            }
            return String.format(Locale.US, "%.1f%%", rounded);
        }
    };

    public SemiCircleProgressView(Context context) {
        this(context, null);
    }

    public SemiCircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemiCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        float density = getResources().getDisplayMetrics().density;
        strokeWidth = 16f * density;
        textSize = 26f * density;
        textGap = 10f * density;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SemiCircleProgressView);
            max = a.getFloat(R.styleable.SemiCircleProgressView_scpv_max, max);
            progress = clampProgress(a.getFloat(R.styleable.SemiCircleProgressView_scpv_progress, progress));
            strokeWidth = a.getDimension(R.styleable.SemiCircleProgressView_scpv_strokeWidth, strokeWidth);
            startColor = a.getColor(R.styleable.SemiCircleProgressView_scpv_startColor, startColor);
            middleColor = a.getColor(R.styleable.SemiCircleProgressView_scpv_middleColor, middleColor);
            endColor = a.getColor(R.styleable.SemiCircleProgressView_scpv_endColor, endColor);
            trackColor = a.getColor(R.styleable.SemiCircleProgressView_scpv_trackColor, trackColor);
            middlePosition = clamp(a.getFloat(R.styleable.SemiCircleProgressView_scpv_middlePosition, middlePosition), 0.05f, 0.95f);
            textSize = a.getDimension(R.styleable.SemiCircleProgressView_scpv_textSize, textSize);
            textColor = a.getColor(R.styleable.SemiCircleProgressView_scpv_textColor, startColor);
            textGap = a.getDimension(R.styleable.SemiCircleProgressView_scpv_textGap, textGap);
            textVisible = a.getBoolean(R.styleable.SemiCircleProgressView_scpv_textVisible, true);
            animateOnAttach = a.getBoolean(R.styleable.SemiCircleProgressView_scpv_animateOnAttach, true);
            a.recycle();
        }

        trackPaint.setStyle(Paint.Style.STROKE);
        trackPaint.setStrokeWidth(strokeWidth);
        trackPaint.setStrokeCap(Paint.Cap.ROUND);
        trackPaint.setColor(trackColor);

        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(startColor);

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setFakeBoldText(true);
    }

    // ==================== 测量 ====================

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        int padH = getPaddingLeft() + getPaddingRight();
        int padV = getPaddingTop() + getPaddingBottom();

        // 未指定宽度时的默认大小：半径 110dp
        int desiredWidth = (int) Math.ceil(2 * (dp2px(110) + strokeWidth / 2f)) + padH;

        int width;
        if (wMode == MeasureSpec.EXACTLY) {
            width = wSize;
        } else if (wMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, wSize);
        } else {
            width = desiredWidth;
        }
        width = Math.max(width, getSuggestedMinimumWidth());

        float contentW = Math.max(0f, width - padH);
        float r = Math.max(0f, (contentW - strokeWidth) / 2f);
        int desiredHeight = (int) Math.ceil(contentHeight(r)) + padV;

        int height;
        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else if (hMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, hSize);
        } else {
            height = desiredHeight;
        }
        height = Math.max(height, getSuggestedMinimumHeight());

        setMeasuredDimension(width, height);
    }

    /** 内容需要的总高度：弧的直径 + 文字间距 + 文字高度 */
    private float contentHeight(float r) {
        float h = r + strokeWidth;
        if (textVisible) {
            Paint.FontMetrics fm = textPaint.getFontMetrics();
            h += textGap + (fm.descent - fm.ascent);
        }
        return h;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padH = getPaddingLeft() + getPaddingRight();
        float padV = getPaddingTop() + getPaddingBottom();

        float contentW = Math.max(0f, w - padH);
        radius = Math.max(0f, (contentW - strokeWidth) / 2f);

        // 高度有富余时整体垂直居中
        float need = contentHeight(radius);
        float offsetY = Math.max(0f, (h - padV - need) / 2f);

        cx = getPaddingLeft() + contentW / 2f;
        cy = getPaddingTop() + offsetY + radius + strokeWidth / 2f;

        arcRect.set(cx - radius, cy - radius, cx + radius, cy + radius);
        updateShader();
    }

    // ==================== 绘制 ====================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (radius <= 0f) return;

        float capAngle = capAngle();

        // 1. 灰色背景轨道（两端同样内收 capAngle，让圆头与进度弧对齐）
        float trackSweep = TOTAL_SWEEP - 2 * capAngle;
        if (trackSweep > 0f) {
            canvas.drawArc(arcRect, START_ANGLE + capAngle, trackSweep, false, trackPaint);
        }

        // 2. 彩色渐变进度弧
        float fraction = max <= 0f ? 0f : clamp(progress / max, 0f, 1f);
        float sweep = TOTAL_SWEEP * fraction;
        if (sweep > 0f) {
            // 弧长小于两个圆头时画出一个圆点
            float drawn = Math.max(sweep - 2 * capAngle, 0.01f);
            canvas.drawArc(arcRect, START_ANGLE + capAngle, drawn, false, progressPaint);
        }

        // 3. 百分比文字
        if (textVisible) {
            Paint.FontMetrics fm = textPaint.getFontMetrics();
//            float baseline = cy + strokeWidth / 2f + textGap - fm.ascent;
            float baseline = cy + strokeWidth / 2f + textGap ;
            canvas.drawText(textFormatter.format(progress, max), cx, baseline, textPaint);
        }
    }

    /** 圆头在圆弧上对应的角度：把弧线两端向内收这么多，防止圆头超出起止点 */
    private float capAngle() {
        if (radius <= 0f || strokeWidth <= 0f) return 0f;
        double half = Math.min(strokeWidth / (2.0 * radius), 1.0);
        return (float) Math.toDegrees(2.0 * Math.asin(half));
    }

    /**
     * 渐变着色器：
     * SweepGradient 默认从 3 点钟方向顺时针展开，旋转 180° 对齐到 9 点钟方向（半圆起点）。
     * 整个半圆上：0 -> 绿，middlePosition -> 黄，1 -> 红。
     * 由于圆头会采样到 0 和 1 之外的位置，首尾都补上绿色保证衔接自然。
     */
    private void updateShader() {
        if (radius <= 0f) {
            progressPaint.setShader(null);
            return;
        }
        int[] colors = {startColor, middleColor, endColor, startColor};
        float[] positions = {0f, middlePosition / 2f, 0.5f, 1f};
        SweepGradient shader = new SweepGradient(cx, cy, colors, positions);
        shaderMatrix.reset();
        shaderMatrix.setRotate(START_ANGLE, cx, cy);
        shader.setLocalMatrix(shaderMatrix);
        progressPaint.setShader(shader);
    }

    // ==================== 对外 API ====================

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        cancelAnimator();
        this.progress = clampProgress(progress);
        invalidate();
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = Math.max(max, 0f);
        this.progress = clampProgress(this.progress);
        invalidate();
    }

    /** 带动画设置进度（默认 1 秒） */
    public void setProgressWithAnimation(float toProgress) {
        setProgressWithAnimation(toProgress, 1000L);
    }

    public void setProgressWithAnimation(float toProgress, long duration) {
        if (duration <= 0) {
            setProgress(toProgress);
            return;
        }
        cancelAnimator();
        animator = ValueAnimator.ofFloat(this.progress, clampProgress(toProgress));
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    /** 设置三个渐变色（绿、黄、红） */
    public void setColors(int start, int middle, int end) {
        setColors(start, middle, end, middlePosition);
    }

    public void setColors(int start, int middle, int end, float middlePos) {
        this.startColor = start;
        this.middleColor = middle;
        this.endColor = end;
        this.middlePosition = clamp(middlePos, 0.05f, 0.95f);
        updateShader();
        invalidate();
    }

    public void setTrackColor(int color) {
        trackPaint.setColor(color);
        invalidate();
    }

    public void setTextVisible(boolean visible) {
        if (textVisible != visible) {
            textVisible = visible;
            requestLayout();
            invalidate();
        }
    }

    public void setTextFormatter(TextFormatter formatter) {
        if (formatter != null) {
            this.textFormatter = formatter;
            invalidate();
        }
    }

    public void setTextTypeface(Typeface typeface) {
        textPaint.setTypeface(typeface);
        requestLayout();
        invalidate();
    }

    // ==================== 生命周期 / 状态 ====================

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 首次显示时从 0 动画到初始进度
        if (animateOnAttach && !hasAnimatedOnAttach && progress > 0f) {
            hasAnimatedOnAttach = true;
            float target = progress;
            progress = 0f;
            setProgressWithAnimation(target, 1000L);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelAnimator();
        super.onDetachedFromWindow();
    }

    private void cancelAnimator() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("super_state", super.onSaveInstanceState());
        bundle.putFloat("progress", progress);
        bundle.putFloat("max", max);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            progress = bundle.getFloat("progress");
            max = bundle.getFloat("max", 100f);
            super.onRestoreInstanceState(bundle.getParcelable("super_state"));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    // ==================== 工具 ====================

    private float clampProgress(float p) {
        return clamp(p, 0f, max);
    }

    private static float clamp(float v, float min, float max) {
        return v < min ? min : Math.min(v, max);
    }

    private float dp2px(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}