package com.xcore.remote.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo

/**
 * 以中点为零点的有符号进度条，显示范围固定为 -1000～1000。
 *
 * 整条背景表示完整范围，左右各占一半；填充始终从零点出发：
 * -1000 填满左半段，-500 填充左半段的一半，0 不填充，1000 填满右半段。
 * 中央刻度始终绘制，便于在零值或很小的数值下识别正负分界。
 * 控件仅展示数值，编辑仍通过动作参数弹窗完成。
 */
class SignedProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val MIN_VALUE = -1000
        const val MAX_VALUE = 1000
    }

    private val density = resources.displayMetrics.density

    // 绘图对象复用，避免列表反复重绘时创建临时对象。
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val track = RectF()
    private val trackClip = Path()

    /**
     * 接收实际有符号参数，不取绝对值，也不除以十。
     * 超出量程时只将绘图值限制在端点，不修改列表模型中的真实参数。
     * 列表复用时每次绑定都设置此属性，包括零值，以清除上一行的填充。
     */
    var progress: Int = 0
        set(value) {
            val bounded = value.coerceIn(MIN_VALUE, MAX_VALUE)
            if (field == bounded) return
            field = bounded
            invalidate()
        }

    /** XML 未给出明确尺寸时，提供与动作参数列表一致的默认大小。 */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            resolveSize(
                maxOf(
                    suggestedMinimumWidth,
                    (140 * density).toInt() + paddingLeft + paddingRight
                ), widthMeasureSpec
            ),
            resolveSize(
                maxOf(
                    suggestedMinimumHeight,
                    (7 * density).toInt() + paddingTop + paddingBottom
                ), heightMeasureSpec
            )
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        track.set(
            paddingLeft.toFloat(), paddingTop.toFloat(),
            (width - paddingRight).toFloat(), (height - paddingBottom).toFloat()
        )
        if (track.width() <= 0f || track.height() <= 0f) return

        val radius = minOf(track.height(), track.width()) / 2f
        val centerX = track.centerX()
        paint.color = Color.rgb(61, 66, 85)
        canvas.drawRoundRect(track, radius, radius, paint)

        // 正负两侧使用相同的比例尺，方向固定为左负右正，不随系统 RTL 布局反转。
        // 例如 -500 对应 25% 宽度处，0 对应 50%，500 对应 75%。
        val endX = centerX + progress.toFloat() / MAX_VALUE * track.width() / 2f
        if (progress != 0) {
            trackClip.reset()
            trackClip.addRoundRect(track, radius, radius, Path.Direction.CW)
            val checkpoint = canvas.save()
            canvas.clipPath(trackClip)
            paint.color = Color.rgb(19, 121, 214)
            canvas.drawRect(
                minOf(centerX, endX),
                track.top,
                maxOf(centerX, endX),
                track.bottom,
                paint
            )
            canvas.restoreToCount(checkpoint)
        }

        paint.color = Color.rgb(190, 197, 215)
        paint.strokeWidth = density
        canvas.drawLine(centerX, track.top, centerX, track.bottom, paint)
    }

    /** 为辅助服务提供真实的负数、零值和正数范围，避免按普通 0～100 进度解释。 */
    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        info.className = android.widget.ProgressBar::class.java.name
        info.rangeInfo = AccessibilityNodeInfo.RangeInfo.obtain(
            AccessibilityNodeInfo.RangeInfo.RANGE_TYPE_INT,
            MIN_VALUE.toFloat(), MAX_VALUE.toFloat(), progress.toFloat()
        )
    }
}
