package com.xcore.remote.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.xcore.remote.ui.R


/**
 * 横向文本选择自定义控件
 * 特性：中间选中文本高亮放大，两侧文本缩小+降低透明度
 */
class HorizontalTextSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    // 自定义属性默认值
    private var selectedTextColor: Int = Color.RED
    private var unSelectedTextColor: Int = Color.BLACK
    private var maxScale: Float = 1.0f
    private var minScale: Float = 0.5f
    private var maxAlpha: Float = 1.0f
    private var minAlpha: Float = 0.5f
    private var viewHeight: Int = dp2px(50f)

    // 内部RecyclerView
    private lateinit var rvTextSelect: RecyclerView

    // 文本数据列表
    private var textList: List<String> = emptyList()

    // 选中回调
    private var onTextSelectedListener: OnTextSelectedListener? = null

    // 当前选中位置
    private var currentSelectedPos: Int = 0

    init {
        // 初始化布局
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.layout_horizontal_text_select, this, true)
        rvTextSelect = findViewById(R.id.rvTextSelect)

        // 解析自定义属性
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.HorizontalTextSelectView)
        selectedTextColor = typedArray.getColor(
            R.styleable.HorizontalTextSelectView_selectedTextColor,
            selectedTextColor
        )
        unSelectedTextColor = typedArray.getColor(
            R.styleable.HorizontalTextSelectView_unSelectedTextColor,
            unSelectedTextColor
        )
        maxScale = typedArray.getFloat(R.styleable.HorizontalTextSelectView_maxScale, maxScale)
        minScale = typedArray.getFloat(R.styleable.HorizontalTextSelectView_minScale, minScale)
        maxAlpha = typedArray.getFloat(R.styleable.HorizontalTextSelectView_maxAlpha, maxAlpha)
        minAlpha = typedArray.getFloat(R.styleable.HorizontalTextSelectView_minAlpha, minAlpha)
        viewHeight = typedArray.getDimensionPixelSize(
            R.styleable.HorizontalTextSelectView_viewHeight,
            viewHeight
        )
        typedArray.recycle()

        // 初始化RecyclerView
        initRecyclerView()
    }

    /**
     * 初始化RecyclerView配置
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun initRecyclerView() {
        // 设置控件高度
        rvTextSelect.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, viewHeight)
        // 横向LayoutManager
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTextSelect.layoutManager = layoutManager
        rvTextSelect.setHasFixedSize(true)
        // 左右padding+取消裁剪，保证中间项居中
        rvTextSelect.setPadding(dp2px(40f), 0, dp2px(40f), 0)
        rvTextSelect.clipToPadding = false
        rvTextSelect.setOnTouchListener(OnTouchListener { v, event ->
            // 根据你的需求判断是否应该处理滑动事件
            // 例如，只允许点击事件，滑动事件返回true阻止
            if (event.action == MotionEvent.ACTION_MOVE) {
                return@OnTouchListener true // 阻止滑动事件
            }
            true // 其他事件继续处理
        })
        // 滚动监听：核心渐变效果
        rvTextSelect.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateItemStyle(recyclerView)
            }
        })
    }

    /**
     * 核心：根据Item偏移更新缩放、透明度、颜色
     */
    private fun updateItemStyle(recyclerView: RecyclerView) {
        val centerX = recyclerView.width / 2 // RecyclerView中间X坐标
        val childCount = recyclerView.childCount

        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            val tvText = child.findViewById<TextView>(R.id.tvText)
            val childCenterX = (child.left + child.right) / 2
            val distance = Math.abs(childCenterX - centerX).toFloat()

            // 计算缩放比例（线性渐变）
            val scaleRatio = 1 - (distance / centerX) * (maxScale - minScale)
            val finalScale =
                if (scaleRatio < minScale) minScale else if (scaleRatio > maxScale) maxScale else scaleRatio
            child.scaleX = finalScale
            child.scaleY = finalScale

            // 计算透明度（线性渐变）
            val alphaRatio = 1 - (distance / centerX) * (maxAlpha - minAlpha)
            val finalAlpha =
                if (alphaRatio < minAlpha) minAlpha else if (alphaRatio > maxAlpha) maxAlpha else alphaRatio
            tvText.alpha = finalAlpha

            // 选中项高亮
            if (finalScale == maxScale) {
                tvText.setTextColor(selectedTextColor)
                // 记录选中位置并回调
                val selectedPos = recyclerView.getChildAdapterPosition(child)
                if (selectedPos != currentSelectedPos) {
                    currentSelectedPos = selectedPos
                    onTextSelectedListener?.onTextSelected(selectedPos, textList[selectedPos])
                }
            } else {
                tvText.setTextColor(unSelectedTextColor)
            }
        }
    }

    /**
     * 对外API：设置文本列表
     */
    fun setTextList(list: List<String>) {
        textList = list
        rvTextSelect.adapter = TextAdapter(list)
        // 默认选中中间项
        if (list.isNotEmpty()) {
            currentSelectedPos = list.size / 2
            scrollToPosition(currentSelectedPos)
        }
    }

    /**
     * 对外API：滚动到指定位置
     */
    fun scrollToPosition(position: Int) {
        if (position < 0 || position >= textList.size) return
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
        }
        smoothScroller.targetPosition = position
        (rvTextSelect.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
    }

    /**
     * 对外API：设置选中回调
     */
    fun setOnTextSelectedListener(listener: OnTextSelectedListener) {
        this.onTextSelectedListener = listener
    }

    /**
     * 适配器：内部复用，对外不可见
     */
    private inner class TextAdapter(private val data: List<String>) :
        RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

        inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvText: TextView = itemView.findViewById(R.id.tvText)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_text_select, parent, false)
            return TextViewHolder(view)
        }

        override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
            holder.tvText.text = data[position]
            // 初始样式
            holder.tvText.scaleX = minScale
            holder.tvText.scaleY = minScale
            holder.tvText.alpha = minAlpha
            holder.tvText.setTextColor(unSelectedTextColor)
        }

        override fun getItemCount(): Int = data.size
    }

    /**
     * 选中回调接口
     */
    interface OnTextSelectedListener {
        fun onTextSelected(position: Int, text: String)
    }

    /**
     * dp转px工具方法
     */
    private fun dp2px(dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }
}