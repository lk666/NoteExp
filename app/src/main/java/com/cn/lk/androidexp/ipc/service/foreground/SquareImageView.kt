package com.cn.lk.androidexp.ipc.service.foreground

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * 宽高相等ImageView（高固定）
 * Created by lk on 2018/3/23.
 */
class SquareImageView : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec))
        val a = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
        super.onMeasure(a, a)
    }
}