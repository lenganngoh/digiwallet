package com.lenganngoh.digiwallet.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

class ActivityUtil {
    companion object {
        fun renderAsFullscreenAdjustResize(
            activity: AppCompatActivity,
            rootView: View
        ) {
            renderFullScreen(activity)
            fixAdjustResize(rootView)
        }

        private fun renderFullScreen(activity: AppCompatActivity) {
            val window = activity.window
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        private fun fixAdjustResize(rootView: View) {
            rootView.fitsSystemWindows = true
            ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
                ViewCompat.onApplyWindowInsets(
                    view,
                    insets.replaceSystemWindowInsets(
                        0, 0, 0,
                        insets.systemWindowInsetBottom
                    )
                )
            }
        }
    }
}