package com.xcore.remote.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.xcore.remote.ui.R;

/**
 * 渐变圆环升级控件
 * <p>
 * 状态表现：
 * - STATE_IDLE    : 0 % + 蓝青渐变「立即升级」按钮
 * - STATE_RUNNING : 百分比 + 副标题（如“设备升级中...”）
 * - STATE_SUCCESS : 满环渐变 + 「升级完成」
 * - STATE_FAILED  : 红色渐变环 + 「升级失败」+ 红色「重新升级」按钮
 */
public class JxCircleProgressView extends View {

    public static final int STATE_IDLE = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_SUCCESS = 2;
    public static final int STATE_FAILED = 3;

    private static final int DEF_SIZE_DP = 240; // wrap_content 时的默认尺寸

    // ---- Paint / 几何 ----
    private final Paint trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint capPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint subPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint buttonTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF arcRect = new RectF();
    private final RectF buttonRect = new RectF();
    private final Matrix shaderMatrix = new Matrix();

    // ---- 可配置属性 ----
    private int startColor = Color.parseColor("#2A55E5"); // 深蓝
    private int endColor = Color.parseColor("#16E0CE"); // 青色
    private int errorStartColor = Color.parseColor("#FF5F52"); // 失败：红渐变
    private int errorEndColor = Color.parseColor("#E43B34");
    private int trackColor = Color.parseColor("#E9EDF6"); // 浅灰底环
    private int numberColor = Color.parseColor("#FF141414");
    private int subTextColor = Color.parseColor("#FF3C3C3C");
    private float startAngle = -90f;  // -90 = 从 12 点钟方向开始
    private float strokeWidth = -1f;   // -1 = 自动（按尺寸比例）
    private float numberTextSize = -1f;   // -1 = 自动
    private float subTextSize = -1f;   // -1 = 自动
    private String subText = "设备升级中...";
    private String buttonText = "立即升级";
    private String retryText = "重新升级";
    private String successText = "升级完成";
    private String failedText = "升级失败";
    private boolean showPercent = true;
    private long animDuration = 900;

    // ---- 状态 ----
    private int state = STATE_IDLE;
    private float progress;    // 目标进度 0~100
    private float displayed;   // 当前动画显示值
    private boolean pressed;   // 按钮按下态
    private ValueAnimator animator;
    private boolean suppressEndCallback; // 取消动画时不触发完成回调

    // ---- 缓存 ----
    private SweepGradient shader;
    private float shaderSweep = -1f;
    private LinearGradient buttonShader;       // 蓝青按钮渐变
    private LinearGradient errorButtonShader;  // 红色按钮渐变

    private OnUpgradeStartListener upgradeListener;

    /**
     * 点击「立即升级」/「重新升级」按钮的回调（两种按钮共用）
     */
    public interface OnUpgradeStartListener {
        void onUpgradeStart();
    }

    // ==================== 构造 ====================

    public JxCircleProgressView(Context context) {
        this(context, null);
    }

    public JxCircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JxCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientCircleProgressView);
            progress = displayed = clamp(a.getFloat(R.styleable.GradientCircleProgressView_gcp_progress, 0f));
            startColor = a.getColor(R.styleable.GradientCircleProgressView_gcp_startColor, startColor);
            endColor = a.getColor(R.styleable.GradientCircleProgressView_gcp_endColor, endColor);
            errorStartColor = a.getColor(R.styleable.GradientCircleProgressView_gcp_errorStartColor, errorStartColor);
            errorEndColor = a.getColor(R.styleable.GradientCircleProgressView_gcp_errorEndColor, errorEndColor);
            trackColor = a.getColor(R.styleable.GradientCircleProgressView_gcp_trackColor, trackColor);
            numberColor = a.getColor(R.styleable.GradientCircleProgressView_gcp_numberColor, numberColor);
            subTextColor = a.getColor(R.styleable.GradientCircleProgressView_gcp_subTextColor, subTextColor);
            strokeWidth = a.getDimension(R.styleable.GradientCircleProgressView_gcp_strokeWidth, -1f);
            numberTextSize = a.getDimension(R.styleable.GradientCircleProgressView_gcp_numberTextSize, -1f);
            subTextSize = a.getDimension(R.styleable.GradientCircleProgressView_gcp_subTextSize, -1f);
            startAngle = a.getFloat(R.styleable.GradientCircleProgressView_gcp_startAngle, -90f);
            subText = orDefault(a.getString(R.styleable.GradientCircleProgressView_gcp_subText), subText);
            buttonText = orDefault(a.getString(R.styleable.GradientCircleProgressView_gcp_buttonText), buttonText);
            retryText = orDefault(a.getString(R.styleable.GradientCircleProgressView_gcp_retryText), retryText);
            successText = orDefault(a.getString(R.styleable.GradientCircleProgressView_gcp_successText), successText);
            failedText = orDefault(a.getString(R.styleable.GradientCircleProgressView_gcp_failedText), failedText);
            showPercent = a.getBoolean(R.styleable.GradientCircleProgressView_gcp_showPercent, true);
            animDuration = a.getInteger(R.styleable.GradientCircleProgressView_gcp_animDuration, 900);
            a.recycle();
        }

        trackPaint.setStyle(Paint.Style.STROKE);
        trackPaint.setStrokeCap(Paint.Cap.ROUND);
        trackPaint.setColor(trackColor);

        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        capPaint.setStyle(Paint.Style.FILL);

        numberPaint.setColor(numberColor);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        numberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        subPaint.setColor(subTextColor);
        subPaint.setTextAlign(Paint.Align.CENTER);

        buttonPaint.setStyle(Paint.Style.FILL);

        buttonTextPaint.setColor(Color.WHITE);
        buttonTextPaint.setTextAlign(Paint.Align.CENTER);
        buttonTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        buttonTextPaint.setFakeBoldText(true);
    }

    private static String orDefault(String v, String def) {
        return v == null || v.length() == 0 ? def : v;
    }

    // ==================== 测量 / 尺寸 ====================

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int def = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, DEF_SIZE_DP, getResources().getDisplayMetrics());
        int w = resolveSize(def, widthMeasureSpec);
        int h = resolveSize(def, heightMeasureSpec);
        int size = Math.min(w, h);   // 强制正方形
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float cx = w / 2f, cy = h / 2f;

        if (strokeWidth < 0) strokeWidth = w * 0.09f;   // 环粗细
        if (numberTextSize < 0) numberTextSize = w * 0.16f;   // “30 %”字号
        if (subTextSize < 0) subTextSize = w * 0.064f;  // 副标题字号

        trackPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeWidth(strokeWidth);
        numberPaint.setTextSize(numberTextSize);
        subPaint.setTextSize(subTextSize);

        // 按钮区域：IDLE / FAILED 态显示在圆心下方
        float bh = w * 0.15f;
        float bw = w * 0.46f;
        buttonRect.set(cx - bw / 2f, cy + w * 0.055f, cx + bw / 2f, cy + w * 0.055f + bh);
        buttonTextPaint.setTextSize(bh * 0.42f);
        buttonShader = null;
        errorButtonShader = null;

        shader = null;
        shaderSweep = -1f;
    }

    // ==================== 绘制 ====================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float radius = (Math.min(getWidth(), getHeight()) - strokeWidth) / 2f;
        arcRect.set(cx - radius, cy - radius, cx + radius, cy + radius);

        // 1. 底环
        canvas.drawArc(arcRect, 0f, 360f, false, trackPaint);

        // 2. 进度弧（失败态自动用红色渐变）
        float sweep = displayed * 360f / 100f;
        if (sweep > 0f) {
            progressPaint.setShader(obtainShader(cx, cy, sweep));
            canvas.drawArc(arcRect, startAngle, sweep, false, progressPaint);

            // 起点圆帽颜色修正（SweepGradient 环形回绕会串色）
            if (sweep < 359.9f) {
                double rad = Math.toRadians(startAngle);
                float sx = cx + radius * (float) Math.cos(rad);
                float sy = cy + radius * (float) Math.sin(rad);
                capPaint.setColor(arcStartColor());
                canvas.drawCircle(sx, sy, strokeWidth / 2f, capPaint);
            }
        }

        // 3. 中间内容（按状态）
        float size = Math.min(getWidth(), getHeight());
        switch (state) {
            case STATE_IDLE:
                drawNumberBlock(canvas, cx, cy - size * 0.10f, false); // “0 %”
                drawUpgradeButton(canvas, cx, buttonText, false);      // 蓝青「立即升级」
                break;
            case STATE_RUNNING:
                drawNumberBlock(canvas, cx, cy, true);                 // 百分比 + 副标题
                break;
            case STATE_SUCCESS:
                drawFittedBoldText(canvas, cx, cy, successText);       // 「升级完成」
                break;
            case STATE_FAILED:
                drawFittedBoldText(canvas, cx, cy - size * 0.10f, failedText); // 「升级失败」上移
                drawUpgradeButton(canvas, cx, retryText, true);                // 红色「重新升级」
                break;
        }
    }

    private int arcStartColor() {
        return state == STATE_FAILED ? errorStartColor : startColor;
    }

    private int arcEndColor() {
        return state == STATE_FAILED ? errorEndColor : endColor;
    }

    /**
     * 渐变随当前扫过角度压缩：保证无论进度多少，弧尾总是到达“青色/红色”
     * （若想固定为整圈渐变，把 colors/positions 换成 new int[]{start,end}, null 即可）
     */
    private SweepGradient obtainShader(float cx, float cy, float sweepDegrees) {
        if (shader != null && Math.abs(sweepDegrees - shaderSweep) < 0.01f) return shader;

        SweepGradient g;
        if (sweepDegrees >= 359.9f) {
            g = new SweepGradient(cx, cy, new int[]{arcStartColor(), arcEndColor()}, null);
        } else {
            float frac = sweepDegrees / 360f;
            g = new SweepGradient(cx, cy,
                    new int[]{arcStartColor(), arcEndColor(), arcEndColor()},
                    new float[]{0f, frac, 1f});
        }
        // SweepGradient 默认 0 点在 3 点钟方向，旋转到起点方向
        shaderMatrix.reset();
        shaderMatrix.setRotate(startAngle, cx, cy);
        g.setLocalMatrix(shaderMatrix);

        shader = g;
        shaderSweep = sweepDegrees;
        return g;
    }

    /**
     * 百分比(+副标题) 两行整体垂直居中
     */
    private void drawNumberBlock(Canvas canvas, float cx, float cyCenter, boolean withSub) {
        String number = Math.round(displayed) + (showPercent ? " %" : "");

        Paint.FontMetrics nfm = numberPaint.getFontMetrics();
        float nh = nfm.descent - nfm.ascent;

        float sh = 0f, gap = 0f;
        Paint.FontMetrics sfm = null;
        if (withSub && subText != null && subText.length() > 0) {
            sfm = subPaint.getFontMetrics();
            sh = sfm.descent - sfm.ascent;
            gap = getHeight() * 0.035f;
        }

        float top = cyCenter - (nh + gap + sh) / 2f;
        canvas.drawText(number, cx, top - nfm.ascent, numberPaint);
        if (sfm != null) {
            canvas.drawText(subText, cx, top + nh + gap - sfm.ascent, subPaint);
        }
    }

    /**
     * 长文本（升级完成/升级失败）自动缩放到圆环内
     */
    private void drawFittedBoldText(Canvas canvas, float cx, float cy, String text) {
        if (text == null || text.length() == 0) return;
        float innerRadius = (Math.min(getWidth(), getHeight()) - strokeWidth) / 2f - strokeWidth / 2f;
        float maxWidth = innerRadius * 2f * 0.8f;

        float oldSize = numberPaint.getTextSize();
        float newSize = oldSize;
        float tw = numberPaint.measureText(text);
        if (tw > maxWidth && tw > 0) newSize = oldSize * maxWidth / tw;

        numberPaint.setTextSize(newSize);
        Paint.FontMetrics fm = numberPaint.getFontMetrics();
        float baseline = cy - (fm.ascent + fm.descent) / 2f;
        canvas.drawText(text, cx, baseline, numberPaint);
        numberPaint.setTextSize(oldSize);
    }

    /**
     * 渐变胶囊按钮：failed=false 蓝青渐变「立即升级」/ failed=true 红色渐变「重新升级」
     */
    private void drawUpgradeButton(Canvas canvas, float cx, String text, boolean failed) {
        if (failed) {
            if (errorButtonShader == null) {
                errorButtonShader = new LinearGradient(buttonRect.left, 0, buttonRect.right, 0,
                        errorStartColor, errorEndColor, Shader.TileMode.CLAMP);
            }
            buttonPaint.setShader(errorButtonShader);
        } else {
            if (buttonShader == null) {
                buttonShader = new LinearGradient(buttonRect.left, 0, buttonRect.right, 0,
                        startColor, endColor, Shader.TileMode.CLAMP);
            }
            buttonPaint.setShader(buttonShader);
        }
        buttonPaint.setAlpha(pressed ? 200 : 255);
        float r = buttonRect.height() / 2f;
        canvas.drawRoundRect(buttonRect, r, r, buttonPaint);

        Paint.FontMetrics fm = buttonTextPaint.getFontMetrics();
        float baseline = buttonRect.centerY() - (fm.ascent + fm.descent) / 2f;
        canvas.drawText(text, cx, baseline, buttonTextPaint);
    }

    // ==================== 触摸：按钮点击 ====================

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // IDLE(立即升级) 和 FAILED(重新升级) 两种状态下按钮可点
        if (state != STATE_IDLE && state != STATE_FAILED) return super.onTouchEvent(event);

        float x = event.getX(), y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                pressed = buttonRect.contains(x, y);
                if (pressed) invalidate();
                return pressed; // 只有按在按钮上才消费事件
            case MotionEvent.ACTION_MOVE:
                boolean inside = buttonRect.contains(x, y);
                if (inside != pressed) {
                    pressed = inside;
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                boolean fire = pressed && buttonRect.contains(x, y)
                        && event.getActionMasked() == MotionEvent.ACTION_UP;
                pressed = false;
                invalidate();
                if (fire) {
                    playSoundEffect(SoundEffectConstants.CLICK);
                    if (upgradeListener != null) upgradeListener.onUpgradeStart();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        boolean handled = super.performClick();
        // 无障碍（键盘/talkback）触发入口
        if ((state == STATE_IDLE || state == STATE_FAILED) && upgradeListener != null) {
            upgradeListener.onUpgradeStart();
            handled = true;
        }
        return handled;
    }

    // ==================== 对外 API ====================

    /**
     * 点击「立即升级」/「重新升级」按钮都会回调这里
     */
    public void setOnUpgradeStartListener(OnUpgradeStartListener l) {
        this.upgradeListener = l;
    }

    /**
     * 设置进度（带动画）。>0 自动进入 RUNNING 态；到 100 自动变 SUCCESS 态；失败重试自动恢复渐变色
     */
    public void setProgress(float target) {
        setProgressInternal(clamp(target), true);
    }

    public void setProgress(float target, boolean animate) {
        setProgressInternal(clamp(target), animate);
    }

    private void setProgressInternal(float target, boolean animate) {
        this.progress = target;
        if (target > 0 && state != STATE_RUNNING && state != STATE_SUCCESS) {
            state = STATE_RUNNING; // IDLE / FAILED -> RUNNING（颜色恢复蓝青渐变）
        }
        cancelAnimator();
        if (!animate || animDuration <= 0) {
            displayed = target;
            if (target >= 100f && state == STATE_RUNNING) state = STATE_SUCCESS;
            invalidate();
            return;
        }
        animator = buildAnimator(displayed, target);
        animator.start();
    }

    private ValueAnimator buildAnimator(float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.setDuration(animDuration);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                displayed = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (suppressEndCallback) return;
                if (state == STATE_RUNNING && progress >= 100f) {
                    state = STATE_SUCCESS; // 到 100% 自动切“升级完成”
                }
                invalidate();
            }
        });
        return anim;
    }

    /**
     * 直接切换状态
     */
    public void setState(int targetState) {
        cancelAnimator();
        state = targetState;
        switch (targetState) {
            case STATE_IDLE:
                progress = 0f;
                if (animDuration > 0) {
                    animator = buildAnimator(displayed, 0f);
                    animator.start();
                } else {
                    displayed = 0f;
                }
                break;
            case STATE_SUCCESS:
                progress = 100f;
                if (animDuration > 0) {
                    animator = buildAnimator(displayed, 100f);
                    animator.start();
                } else {
                    displayed = 100f;
                }
                break;
            case STATE_FAILED:
                // 保持当前进度，圆环变红，显示“升级失败”+“重新升级”按钮
                break;
            case STATE_RUNNING:
            default:
                break;
        }
        invalidate();
    }

    public int getState() {
        return state;
    }

    /**
     * 标记升级失败（红环 + “升级失败” + “重新升级”按钮）
     */
    public void setFailed() {
        setState(STATE_FAILED);
    }

    /**
     * 回到初始状态（0% + 立即升级按钮）
     */
    public void reset() {
        setState(STATE_IDLE);
    }

    public float getProgress() {
        return progress;
    }

    public void setGradientColors(int start, int end) {
        this.startColor = start;
        this.endColor = end;
        shader = null;
        buttonShader = null;
        invalidate();
    }

    public void setErrorColors(int start, int end) {
        this.errorStartColor = start;
        this.errorEndColor = end;
        shader = null;
        errorButtonShader = null;
        invalidate();
    }

    public void setTrackColor(int color) {
        this.trackColor = color;
        trackPaint.setColor(color);
        invalidate();
    }

    public void setSubText(String text) {
        this.subText = text == null ? "" : text;
        invalidate();
    }

    public void setButtonText(String text) {
        this.buttonText = text == null ? "" : text;
        invalidate();
    }

    public void setRetryText(String text) {
        this.retryText = text == null ? "" : text;
        invalidate();
    }

    public void setAnimDuration(long durationMs) {
        this.animDuration = durationMs;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator();
    }

    private void cancelAnimator() {
        if (animator != null) {
            suppressEndCallback = true;
            animator.cancel();
            animator = null;
            suppressEndCallback = false;
        }
    }

    private static float clamp(float v) {
        return v < 0f ? 0f : (v > 100f ? 100f : v);
    }
}
