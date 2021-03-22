package com.lenganngoh.digiwallet.util

import android.content.res.Resources

class PixelUtil {
    companion object {
        fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
    }
}