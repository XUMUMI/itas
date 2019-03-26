package com.xumumi.itas.animator

import android.content.Context

fun dp2px(context: Context, dpValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f)
}

fun px2dip(context: Context, pxValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f)
}
