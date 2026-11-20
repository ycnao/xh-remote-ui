package com.xcore.remote.ui.widget.wheelview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xcore.remote.ui.R;

public class CircleProgressView extends View {

    private Paint mBackPaint, mProgPaint;   // 绘制画笔
    private RectF mRectF;       // 绘制区域
    private int[] mColorArray;  // 圆环渐变色
    private int mProgress;      // 圆环进度(0-100)

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView);

        // 初始化背景圆环画笔
        mBackPaint = new Paint();
        mBackPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
        mBackPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mBackPaint.setAntiAlias(true);              // 设置抗锯齿
        mBackPaint.setDither(true);                 // 设置抖动
        mBackPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircularProgressView_backWidth, 5));
        mBackPaint.setColor(typedArray.getColor(R.styleable.CircularProgressView_backColor, Color.LTGRAY));

        // 初始化进度圆环画笔
        mProgPaint = new Paint();
        mProgPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
        mProgPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mProgPaint.setAntiAlias(true);              // 设置抗锯齿
        mProgPaint.setDither(true);                 // 设置抖动
        mProgPaint.setStrokeWidth(typedArray.getDimension(R.styleable.CircularProgressView_progWidth, 10));
        mProgPaint.setColor(typedArray.getColor(R.styleable.CircularProgressView_progColor, Color.BLUE));

        // 初始化进度圆环渐变色
        int startColor = typedArray.getColor(R.styleable.CircularProgressView_progStartColor, -1);
        int firstColor = typedArray.getColor(R.styleable.CircularProgressView_progFirstColor, -1);
        if (startColor != -1 && firstColor != -1)
            mColorArray = new int[]{startColor, firstColor};
        else {
            mColorArray = null;
        }

        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.CircularProgressView_progress, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWide = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHigh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int mRectLength = (int) ((viewWide > viewHigh ? viewHigh : viewWide) - (mBackPaint.getStrokeWidth() >
                mProgPaint.getStrokeWidth() ? mBackPaint.getStrokeWidth() : mProgPaint.getStrokeWidth()));
        int mRectL = getPaddingLeft() + (viewWide - mRectLength) / 2;
        int mRectT = getPaddingTop() + (viewHigh - mRectLength) / 2;
        mRectF = new RectF(mRectL, mRectT, mRectL + mRectLength, mRectT + mRectLength);

        // 设置进度圆环渐变色
        if (mColorArray != null && mColorArray.length > 1) {
            mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF, 0, 360, false, mBackPaint);
        canvas.drawArc(mRectF, 275, 360 * mProgress / 100, false, mProgPaint);
    }

    // ---------------------------------------------------------------------------------------------

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setProgress(int progress, long animTime) {
        if (animTime <= 0) {
            setProgress(progress);
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress);
            animator.addUpdateListener(animation -> {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(animTime);
            animator.start();
        }
    }

    public void setBackWidth(int width) {
        mBackPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setBackColor(@ColorRes int color) {
        mBackPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }

    public void setProgressWidth(int width) {
        mProgPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setProgressColor(@ColorRes int color) {
        mProgPaint.setColor(ContextCompat.getColor(getContext(), color));
        mProgPaint.setShader(null);
        invalidate();
    }

    public void setProgressColor(@ColorRes int startColor, @ColorRes int firstColor) {
        mColorArray = new int[]{ContextCompat.getColor(getContext(), startColor), ContextCompat.getColor(getContext(), firstColor)};
        mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }

    public void setProgressColor(@ColorRes int[] colorArray) {
        if (colorArray == null || colorArray.length < 2) {
            return;
        }
        mColorArray = new int[colorArray.length];
        for (int index = 0; index < colorArray.length; index++) {
            mColorArray[index] = ContextCompat.getColor(getContext(), colorArray[index]);
        }
        mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }

}
